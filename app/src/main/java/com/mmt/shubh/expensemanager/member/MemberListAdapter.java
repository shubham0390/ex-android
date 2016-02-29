package com.mmt.shubh.expensemanager.member;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.Member;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Subham Tyagi,
 * on 23/Jun/2015,
 * 12:22 AM
 * TODO:Add class comment.
 */
public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {

    private List<Member> mMembers = new ArrayList<>();

    private boolean mCanDelete;

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_member, parent, false);
        return new MemberViewHolder(view, mCanDelete);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.bindView(mMembers.get(position));
        holder.mDeleteImageView.setVisibility(mCanDelete ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    @Override
    public long getItemId(int position) {
        return mMembers.get(position).getId();
    }

    public Member getItem(int position) {
        return mMembers.get(position);
    }

    public boolean isCanDelete() {
        return mCanDelete;
    }

    public void setCanDelete(boolean canDelete) {
        mCanDelete = canDelete;
    }

    public List<Member> getMembers() {
        return mMembers;
    }

    @UiThread
    public void setMembers(List<Member> members) {
        mMembers.addAll(members);
        notifyDataSetChanged();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.member_name)
        TextView mMemberName;

        @Bind(R.id.member_email)
        TextView mMemberEmail;

        @Bind(R.id.list_image_icon)
        ImageView mProfileImage;

        @Bind(R.id.delete_member)
        ImageView mDeleteImageView;

        private View mParent;

        private boolean mCanDelete;

        public MemberViewHolder(View itemView, boolean canDelete) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mParent = itemView;
            mCanDelete = canDelete;
        }

        public void bindView(Member member) {
            mMemberName.setText(member.getMemberName());
            mMemberEmail.setText(member.getMemberEmail());
            String imageUrl = member.getProfilePhotoUrl();
            /*mDeleteImageView.setVisibility(mCanDelete ? View.VISIBLE : View.INVISIBLE);*/
            Animation anim = AnimationUtils.loadAnimation(mProfileImage.getContext(), android.R.anim.fade_in);

            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(mProfileImage.getContext())
                        .load(imageUrl).animate(anim)
                        .centerCrop().fitCenter()
                        .into(mProfileImage);
            }
        }

    }
}
