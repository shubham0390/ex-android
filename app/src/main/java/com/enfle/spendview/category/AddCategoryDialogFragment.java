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

package com.enfle.spendview.category;


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

import com.enfle.spendview.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Add a custom category to be a part of second version
 * @author Umang Chamaria
 */
public class AddCategoryDialogFragment extends DialogFragment {

    //@BindView(R.id.category_image)
    ImageView mCategoryImage;
    //@BindView(R.id.category_name)
    EditText mCategoryName;
    //@BindView(R.id.ok_button)
    TextView mOk;
    //@BindView(R.id.cancel_button)
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

    //@OnClick(R.id.ok_button)
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

    //@OnClick(R.id.cancel_button)
    public void onCancelButtonClick() {

    }
}
