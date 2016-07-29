package com.km2labs.android.spendview.member;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.km2labs.shubh.expensemanager.R;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.core.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Subham Tyagi,
 * on 23/Jun/2015,
 * 12:22 AM
 * TODO:Add class comment.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<Member> mMembers = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private boolean mCanDelete;
    private boolean mIsGrid;
    // Start with first item selected
    private int selectedItem = 0;


    public MemberAdapter() {
        mMembers = new ArrayList<>();
    }

    public MemberAdapter(boolean isGrid) {
        super();
        this.mIsGrid = isGrid;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener((v, keyCode, event) -> {
            RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

            // Return false if scrolled to the bounds and allow focus to move off the list
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    return tryMoveSelection(lm, 1);
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    return tryMoveSelection(lm, -1);
                }
            }

            return false;
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int nextSelectItem = selectedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (nextSelectItem > 0 && nextSelectItem < getItemCount()) {
            notifyItemChanged(selectedItem);
            selectedItem = nextSelectItem;
            notifyItemChanged(selectedItem);
            lm.scrollToPosition(selectedItem);
            return true;
        }

        return false;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mIsGrid) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_member, parent, false);
        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_member, parent, false);
        return new MemberViewHolder(view, mCanDelete);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.bindView(mMembers.get(position));
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

    public boolean canDelete() {
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


        private boolean mCanDelete;
        private boolean mIsGrid;

        public MemberViewHolder(View itemView, boolean canDelete) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // Redraw the old selection and the new
            itemView.setOnClickListener(v -> {
                notifyItemChanged(selectedItem);
                selectedItem = mRecyclerView.getChildAdapterPosition(itemView);
                notifyItemChanged(selectedItem);
            });

            mCanDelete = canDelete;
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
            if (mDeleteImageView != null && canDelete()) {
                mDeleteImageView.setVisibility(mCanDelete ? View.VISIBLE : View.GONE);
               /* mDeleteImageView.setOnClickListener(v -> EventManager.getAnyBus()
                        .post(new MemberDeleteEvent(member.getId())));*/
            }
        }

    }
}
