package com.mmt.shubh.expensemanager.ui.fragment.expensebook;


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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.IFragmentDataSharer;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.ui.fragment.base.IFragmentSwitcher;
import com.mmt.shubh.expensemanager.ui.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.ui.presenters.ExpenseBookFragmentPresenter;
import com.mmt.shubh.expensemanager.ui.views.IExpenseBookFragmentView;
import com.mmt.shubh.expensemanager.utils.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Umang Chamaria
 *         TODO : save image uri and expense book name in database
 */
public class AddExpenseBookFragment extends MVPFragment<IExpenseBookFragmentView, ExpenseBookFragmentPresenter>
        implements IExpenseBookFragmentView {

    private final String TAG = AddExpenseBookFragment.class.getSimpleName();

    private final int SELECT_IMAGE = 1;

    @Bind(R.id.new_expense_book_name)
    EditText mExpenseName;

    @Bind(R.id.new_expense_book_description)
    EditText mExpenseDescription;

    @Bind(R.id.expense_book_image)
    CircleImageView mExpenseImage;

    private Uri mOutputFileUri;

    private IFragmentDataSharer mFragmentDataSharer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense_book, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.expense_book_image)
    void onImageClickListener() {
        openImageIntent();
    }

    @Override
    protected ExpenseBookFragmentPresenter getPresenter() {
        return new ExpenseBookFragmentPresenter(mContext);
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
    public String getTitle() {
        return null;
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
                    selectedImageUri = data == null ? null : data.getData();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_next) {
            Utilities.hideKeyboard(getActivity());
            mPresenter.validateExpenseNameAndProceed(mExpenseName.getText().toString(),
                    mOutputFileUri != null ? mOutputFileUri.toString() : null, mExpenseDescription.getText().toString());
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
                .error_empty_expense_book_name), Toast.LENGTH_LONG).show();
        mExpenseName.requestFocus();
    }

    @Override
    public void showDuplicateExpenseBook() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                .error_expense_book_already_exists), Toast.LENGTH_LONG).show();
        mExpenseName.requestFocus();
    }

    @Override
    public void addMemberFragment(Bundle expenseBookInfo) {
        mFragmentDataSharer.passData(expenseBookInfo);
        ((IFragmentSwitcher) getActivity()).replaceFragment(Constants.ADDING_MEMBER_FRAGMENT, null);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(ExpenseBook data) {
        mExpenseName.setText(data.getName());
        mExpenseImage.setImageURI(new Uri.Builder().encodedPath(data.getProfileImagePath()).build());
        mExpenseDescription.setText(data.getDescription());
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
