package com.mmt.shubh.expensemanager.ui.fragment;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmt.shubh.expensemanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Add a custom category to be a part of second version
 * @author Umang Chamaria
 */
public class AddCategoryDialogFragment extends DialogFragment {

    @Bind(R.id.category_image)
    ImageView mCategoryImage;
    @Bind(R.id.category_name)
    EditText mCategoryName;
    @Bind(R.id.ok_button)
    TextView mOk;
    @Bind(R.id.cancel_button)
    TextView mCancel;

    public AddCategoryDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_category_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.ok_button)
    public void onOkButtonClick() {
        String categoryName = mCategoryName.getText().toString();
        if (!TextUtils.isEmpty(categoryName)) {
            if (checkIfCategoryExist()) {
                // TODO: 04-Oct-15 save category to database
            } else {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                        .category_name_exists), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                    .category_name_empty), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkIfCategoryExist() {
        // TODO: 04-Oct-15 check if category exists
        return false;
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButtonClick() {

    }
}
