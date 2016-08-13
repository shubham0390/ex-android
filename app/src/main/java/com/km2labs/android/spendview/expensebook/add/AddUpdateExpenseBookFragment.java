/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.expensebook.add;


import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.km2labs.android.spendview.core.mvp.MVPFragment;
import com.km2labs.android.spendview.core.view.CircleImageView;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.android.spendview.utils.Utilities;
import com.km2labs.android.spendview.core.dagger.module.FragmentModule;
import com.km2labs.android.spendview.IFragmentDataSharer;
import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Umang Chamaria
 *         TODO : save image uri and expense book name in database
 */
public class AddUpdateExpenseBookFragment extends MVPFragment<ExpenseBookFragmentPresenter>
        implements IExpenseBookFragmentView {

    private final String TAG = AddUpdateExpenseBookFragment.class.getSimpleName();

    private final int SELECT_IMAGE = 1;

    @BindView(R.id.new_expense_book_name)
    EditText mExpenseName;

    @BindView(R.id.new_expense_book_description)
    EditText mExpenseDescription;

    @BindView(R.id.expense_book_image)
    CircleImageView mExpenseImage;

    private Uri mOutputFileUri;

    private boolean isUpdate;

    private IFragmentDataSharer mFragmentDataSharer;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_expense_book;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            ExpenseBook expenseBook = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_EXPENSE_BOOK));
            mExpenseName.setText(expenseBook.getName());
            mExpenseDescription.setText(expenseBook.getDescription());
        }

    }

    @OnClick(R.id.expense_book_image)
    void onImageClickListener() {
        openImageIntent();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFragmentDataSharer = (IFragmentDataSharer) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IFragmentDataSharer");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_new_expense_book, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = mOutputFileUri;
                } else {
                    selectedImageUri = data.getData();
                }

                final InputStream imageStream;
                try {
                    imageStream = getActivity().getContentResolver()
                            .openInputStream(selectedImageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    mExpenseImage.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    Log.e(TAG, "file not found" + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem update = menu.findItem(R.id.action_update);
        update.setVisible(isUpdate);
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_next:
                Utilities.hideKeyboard(getActivity());
                mPresenter.validateExpenseNameAndProceed(mExpenseName.getText().toString(),
                        mOutputFileUri != null ? mOutputFileUri.toString() : null, mExpenseDescription.getText().toString(), isUpdate);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openImageIntent() {

        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator +
                "ExpenseManager" + File.separator);
        root.mkdirs();
        final String expenseIconName = "expense_icon" + System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, expenseIconName);
        mOutputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo
                    .name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new
                Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, SELECT_IMAGE);
    }

    @Override
    public void showEmptyError() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                        .error_empty_expense_book_name),
                Toast.LENGTH_LONG).show();
        mExpenseName.requestFocus();
    }

    @Override
    public void showDuplicateExpenseBook() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                        .error_expense_book_already_exists),
                Toast.LENGTH_LONG).show();
        mExpenseName.requestFocus();
    }

    @Override
    public void addMemberFragment(Bundle expenseBookInfo) {
        mFragmentDataSharer.passData(expenseBookInfo);
    }

    @Override
    public void showError(String payload) {

    }

    @Override
    public void showCreatingExpenseBookProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void exit() {
        getActivity().runOnUiThread(() -> AddUpdateExpenseBookFragment.this.getActivity().finish());
    }

    @Override
    public void onExpenseBookUpdate() {
        exit();
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        mainComponent.fragmentComponent(new FragmentModule()).inject(this);
    }
}
