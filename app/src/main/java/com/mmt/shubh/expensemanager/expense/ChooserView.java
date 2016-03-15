package com.mmt.shubh.expensemanager.expense;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.recyclerviewlib.GridRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by shubham on 11/23/15.
 */
public class ChooserView extends FrameLayout {

    @Bind(R.id.title)
    TextView mTitleTextView;

    @Bind(R.id.selected_text_view)
    TextView mTextView;

    @Bind(R.id.image_view)
    ImageView mImageView;

    @Bind(R.id.filter_data_list)
    GridRecyclerView mFilterDataList;

    RecyclerView.Adapter mAdapter;

    private OnSelectListener mOnSelectListener;
    private boolean hidden;

    public ChooserView(Context context) {
        super(context);
        init(context);
        setupAttribute(context, null, 0, 0);
    }

    public ChooserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setupAttribute(context, attrs, 0, 0);
    }

    public ChooserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setupAttribute(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChooserView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        setupAttribute(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setupAttribute(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (isInEditMode()) {
            return;
        }
        if (attrs != null) {
            TypedArray ta = null;
            try {
                ta = context.obtainStyledAttributes(attrs, R.styleable.ChooserView, defStyleAttr, defStyleRes);
                String title = ta.getString(R.styleable.ChooserView_chooser_view_title);
                mTitleTextView.setText(title);
            } finally {
                if (ta != null) {
                    ta.recycle();
                }
            }
        }

    }


    private void init(Context context) {
       View rootView =  LayoutInflater.from(context).inflate(R.layout.chooser_view, this, true);
        ButterKnife.bind(rootView);
        if (isInEditMode()) {
            return;
        }
        mFilterDataList.setOnItemClickListener((parent, view, position, id) -> {
            if (mOnSelectListener != null) {
                mOnSelectListener.onSelect(id, position);
            }
            return true;
        });
    }

    @OnClick(R.id.chooserView)
    protected void onViewClicked() {

        int cx = (mFilterDataList.getLeft() + mFilterDataList.getRight());
//                int cy = (mRevealView.getTop() + mRevealView.getBottom())/2;
        int cy = mFilterDataList.getTop();

        int radius = Math.max(mFilterDataList.getWidth(), mFilterDataList.getHeight());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator =
                    ViewAnimationUtils.createCircularReveal(mFilterDataList, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(800);

            SupportAnimator animator_reverse = animator.reverse();
            if (hidden) {
                mFilterDataList.setVisibility(View.VISIBLE);
                animator.start();
                hidden = false;
            } else {
                animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mFilterDataList.setVisibility(View.GONE);
                        hidden = true;

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                animator_reverse.start();

            }
        } else {
            if (hidden) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mFilterDataList, cx, cy, 0, radius);
                mFilterDataList.setVisibility(View.VISIBLE);
                anim.start();
                hidden = false;

            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mFilterDataList, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mFilterDataList.setVisibility(View.GONE);
                        hidden = true;
                    }
                });
                anim.start();
            }
        }
    }

    public void setOnChooseListener(OnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mAdapter != null) {
            mAdapter = adapter;
            mFilterDataList.setAdapter(mAdapter);
        }
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    public interface OnSelectListener {
        boolean onSelect(long id, int position);
    }
}
