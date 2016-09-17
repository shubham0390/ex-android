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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.km2labs.expenseview.android.R;

/**
 * Created by Subham Tyagi,
 * on 19/Jul/2015,
 * 9:16 AM
 * TODO:Add class comment.
 */
public class ExTextView extends AppCompatTextView {
    public static final int FONT_STYLE_DEFAULT = 4;

    public ExTextView(Context context) {
        super(context);
        setFontStyle(context, null, FONT_STYLE_DEFAULT);

    }

    public ExTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle(context, attrs, FONT_STYLE_DEFAULT);
    }

    public ExTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFontStyle(context, attrs, FONT_STYLE_DEFAULT);
    }


    private void setFontStyle(Context context, AttributeSet attrs, int defStyle) {

        TypedArray a = null;

        if (attrs != null) {
            a = context.obtainStyledAttributes(attrs, R.styleable.ExTextView, defStyle, 0);

            int fontStyleId = a.getInt(R.styleable.ExTextView_fontStyle, FONT_STYLE_DEFAULT);
            setTypeface(context, fontStyleId);

            a.recycle();
        } else {
            setTypeface(context, FONT_STYLE_DEFAULT);
        }

    }


    /**
     * Sets the font style of the TextView based on the passed fontId.
     *
     * @param ctx : Application/activity context
     * @return true if font style is set else false.
     */
    public boolean setTypeface(Context ctx, int fontId) {
        Typeface tf = null;

        String asset = "";
        switch (fontId) {

            case 1:
                asset = "LobsterTwo-Bold.ttf";
                break;
            case 2:
                asset = "LobsterTwo-BoldItalic.ttf";
                break;
            case 3:
                asset = "LobsterTwo-Italic.ttf";
                break;
            case 4:
                asset = "LobsterTwo-Regular.ttf";
                break;
            case 5:
                asset = "Pacifico.ttf";
                break;
            case 6:
                asset = "PlayfairDisplay-Black.ttf";
                break;
            case 7:
                asset = "PlayfairDisplay-BlackItalic.ttf";
                break;
            case 8:
                asset = "PlayfairDisplay-Bold.ttf";
                break;
            case 9:
                asset = "PlayfairDisplay-BoldItalic.ttf";
                break;
            case 10:
                asset = "PlayfairDisplay-Italic.ttf";
                break;
            case 11:
                asset = "PlayfairDisplay-Regular.ttf";
                break;
            default:
                asset = "PlayfairDisplay-Regular.ttf";
                break;
        }

        try {
            tf = Typefaces.get(ctx, asset);
        } catch (Exception e) {
            return false;
        }

        setTypeface(tf);
        return true;
    }

}
