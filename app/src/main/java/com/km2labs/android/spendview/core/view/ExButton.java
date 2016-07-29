package com.km2labs.android.spendview.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.TextView;

import com.km2labs.shubh.expensemanager.R;

/**
 * Created by Subham Tyagi,
 * on 21/Jul/2015,
 * 5:30 PM
 * TODO:Add class comment.
 */
public class ExButton extends AppCompatButton {
    public static final int FONT_STYLE_DEFAULT = 4;

    public ExButton(Context context) {
        super(context);
        setFontStyle(this,context, null, FONT_STYLE_DEFAULT);

    }

    public ExButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle(this,context, attrs, FONT_STYLE_DEFAULT);
    }

    public ExButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFontStyle(this,context, attrs, FONT_STYLE_DEFAULT);
    }


    private void setFontStyle(TextView textView ,Context context, AttributeSet attrs, int defStyle) {

        TypedArray a = null;

        if (attrs != null) {
            a = context.obtainStyledAttributes(attrs, R.styleable.ExTextView, defStyle, 0);

            int fontStyleId = a.getInt(R.styleable.ExTextView_fontStyle, FONT_STYLE_DEFAULT);
            setTypeface(textView,context, fontStyleId);

            a.recycle();
        } else {
            setTypeface(textView,context, FONT_STYLE_DEFAULT);
        }

    }


    /**
     * Sets the font style of the TextView based on the passed fontId.
     *
     * @param ctx : Application/activity context
     * @return true if font style is set else false.
     */
    public boolean setTypeface(TextView textView ,Context ctx, int fontId) {
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

        textView.setTypeface(tf);
        return true;
    }

}
