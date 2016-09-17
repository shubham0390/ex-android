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

package com.km2labs.android.spendview.core.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.km2labs.expenseview.android.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shubham on 11/19/15.
 */
public class KeyboardView extends GridLayout {

    int mCurrentOperation;

    private String mFirstTextValue = "";

    private String mSecondTextValue = "";

    private EditText amountEditText;

    public KeyboardView(Context context) {
        super(context);
        init(context);
    }


    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        ButterKnife.bind(view);
    }

    @OnClick(R.id.button_1)
    public void clickButtonOne() {
        appendText("1", 0);
    }

    @OnClick(R.id.button_2)
    public void clickButtonTwo() {
        appendText("2", 0);
    }

    @OnClick(R.id.button_3)
    public void clickButtonThree() {
        appendText("3", 0);
    }

    @OnClick(R.id.button_4)
    public void clickButtonFour() {
        appendText("4", 0);
    }

    @OnClick(R.id.button_5)
    public void clickButtonFive() {
        appendText("5", 0);
    }

    @OnClick(R.id.button_6)
    public void clickButtonSix() {
        appendText("6", 0);
    }

    @OnClick(R.id.button_7)
    public void clickButtonSeven() {
        appendText("7", 0);
    }

    @OnClick(R.id.button_8)
    public void clickButtonEight() {
        appendText("8", 0);
    }

    @OnClick(R.id.button_9)
    public void clickButtonNine() {
        appendText("9", 0);
    }

    @OnClick(R.id.button_0)
    public void clickButtonZero() {
        appendText("0", 0);
    }

    @OnClick(R.id.button_point)
    public void clickButtonDot() {
        appendText(".", 0);
    }

    @OnClick(R.id.button_plus)
    public void clickButtonPlus() {
        appendText(" + ", 1);
    }

    @OnClick(R.id.button_minus)
    public void clickButtonMinus() {
        appendText(" - ", 4);
    }

    @OnClick(R.id.button_devide)
    public void clickButtonDiv() {
        appendText(" / ", 4);
    }

    @OnClick(R.id.button_mul)
    public void clickButtonMul() {
        appendText(" * ", 3);
        String text = amountEditText.getText().toString();
        text = text.substring(0, text.length() - 2);
        amountEditText.setText(text);
    }

    @OnClick(R.id.button_back)
    public void clickBackButton() {

    }


    private void appendText(String text, int operation) {
        String finalTextForEditText = "";
        String currentEditTextValue = amountEditText.getText().toString();
        if (operation <= 0) {
            // Simple Digit press
            if (mCurrentOperation > 0) {
                mSecondTextValue += text;
            }
            finalTextForEditText = currentEditTextValue + text;
        } else {
            if (TextUtils.isEmpty(currentEditTextValue)) {
                return;
            }
            // pressing some operation
            if (mCurrentOperation > 0 && TextUtils.isEmpty(mSecondTextValue)) {
                //adding second opration with adding any value replace old operation with new
                finalTextForEditText.replace(getOprationString(mCurrentOperation), text);
                mCurrentOperation = operation;
            } else if (mCurrentOperation > 0 && !TextUtils.isEmpty(mSecondTextValue)) {
                // adding another opration with second value . perfome opration and add updated value to final string
                finalTextForEditText = String.valueOf(doOperation()) + text;
                mCurrentOperation = operation;
            } else if (mCurrentOperation == 0 && !TextUtils.isEmpty(mFirstTextValue)) {
                // adding first opration after adding first value
                mFirstTextValue = amountEditText.getText().toString();
                finalTextForEditText = currentEditTextValue + text;
                mCurrentOperation = operation;
            } else if (mCurrentOperation == 0 && TextUtils.isEmpty(mFirstTextValue)) {
                mFirstTextValue = amountEditText.getText().toString();
                finalTextForEditText = currentEditTextValue + text;
                mCurrentOperation = operation;
            }
        }

        if (currentEditTextValue.equals("0")) {
            amountEditText.setText(text);
        } else {
            amountEditText.setText(finalTextForEditText);
        }

    }

    private String getOprationString(int mCurrentOperation) {
        switch (mCurrentOperation) {
            case 1:
                return " + ";
            case 2:
                return " - ";
            case 3:
                return " * ";
            case 4:
                return " / ";
        }
        return "";
    }

    private double doOperation() {

        double finalValue = 0;

        switch (mCurrentOperation) {
            case 1:
                finalValue = Double.parseDouble(mFirstTextValue) + Double.parseDouble(mSecondTextValue);
                break;
            case 2:
                finalValue = Double.parseDouble(mFirstTextValue) - Double.parseDouble(mSecondTextValue);
                break;
            case 3:
                finalValue = Double.parseDouble(mFirstTextValue) * Double.parseDouble(mSecondTextValue);
                break;
            case 4:
                finalValue = Double.parseDouble(mFirstTextValue) / Double.parseDouble(mSecondTextValue);
                break;
        }

        return finalValue;
    }

    public void setupEditText(AutoResizeEditText amountEditText) {
        this.amountEditText = amountEditText;
    }
}
