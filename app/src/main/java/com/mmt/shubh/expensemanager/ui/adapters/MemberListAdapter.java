package com.mmt.shubh.expensemanager.ui.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;

/**
 * Created by Subham Tyagi,
 * on 23/Jun/2015,
 * 12:22 AM
 * TODO:Add class comment.
 */
public class MemberListAdapter extends CursorRecyclerAdapter<MemberListAdapter.MemberViewHolder> {

    private OnMemberItemClickListener mItemClickListener;
    private TextView mEmptyText;
    private ProgressBar mProgressBar;

    public MemberListAdapter(Cursor c, OnMemberItemClickListener listener) {
        super(c);
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, final Cursor cursor) {
        holder.bindView(cursor);
        final long id = cursor.getLong(cursor.getColumnIndex(MemberContract._ID));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mItemClickListener.onMemberItemClick(id);
            }
        });
    }


    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_member, parent, false);
        return new MemberViewHolder(view);
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        private TextView mMemberName;
        private TextView mMemberEmail;
        private TextView mMemberPhoneNumber;
        private ImageView mProfileImage;
        private View mParent;

        public MemberViewHolder(View itemView) {
            super(itemView);
            mMemberName = (TextView) itemView.findViewById(R.id.member_name);
            mMemberEmail = (TextView) itemView.findViewById(R.id.member_email);
            mMemberPhoneNumber = (TextView) itemView.findViewById(R.id.member_phone_number);
            mProfileImage = (ImageView) itemView.findViewById(R.id.list_image_icon);
            mParent = itemView;
        }


        public void bindView(Cursor cursor) {
            mMemberName.setText(cursor.getString(cursor.getColumnIndex(MemberContract.MEMBER_NAME)));
            mMemberEmail.setText(cursor.getString(cursor.getColumnIndex(MemberContract.MEMBER_EMAIL)));
            mMemberPhoneNumber.setText(cursor.getString(cursor.getColumnIndex(MemberContract
                    .MEMBER_PHONE_NUMBER)));
            String imageUrl = cursor.getString(cursor.getColumnIndex(MemberContract.MEMBER_IMAGE_URI));
            Animation anim = AnimationUtils.loadAnimation(mProfileImage.getContext(), android.R.anim.fade_in);
            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(mProfileImage.getContext())
                        .load(imageUrl)
                        .animate(anim)
                        .centerCrop()
                        .fitCenter()
                        .into(mProfileImage);
            }
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mParent.setOnClickListener(listener);
        }
    }

    public interface OnMemberItemClickListener {
        void onMemberItemClick(long id);
    }
}
