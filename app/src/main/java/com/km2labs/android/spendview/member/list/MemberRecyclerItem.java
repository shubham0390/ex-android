package com.km2labs.android.spendview.member.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.km2labs.android.spendview.core.base.RecyclerItemView;
import com.km2labs.android.spendview.core.view.CircleImageView;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.expenseview.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by : Subham Tyagi
 * Created on :  18/09/16.
 */

public class MemberRecyclerItem implements RecyclerItemView {

    private boolean mCanDelete;
    private boolean mIsGrid;

    private Member mMember;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder) {
        MemberViewHolder memberViewHolder = (MemberViewHolder) holder;
        memberViewHolder.bindView(mMember);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.member_name)
        TextView mMemberName;

        @Nullable
        @BindView(R.id.member_email)
        TextView mMemberEmail;

        @BindView(R.id.list_image_icon)
        CircleImageView mProfileImage;

        @Nullable
        @BindView(R.id.delete_member)
        ImageView mDeleteImageView;


        public MemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Member member) {
            mMemberName.setText(member.getMemberName());
            String imageUrl = member.getProfilePhotoUrl();

            if (!TextUtils.isEmpty(imageUrl)) {
                mProfileImage.loadImage(imageUrl);
            }

            if (mMemberEmail != null) {
                mMemberEmail.setText(member.getMemberEmail());
            }
            if (mDeleteImageView != null && mCanDelete) {
                mDeleteImageView.setVisibility(View.VISIBLE);
                mDeleteImageView.setOnClickListener(v -> {
                });
            }
        }

    }
}
