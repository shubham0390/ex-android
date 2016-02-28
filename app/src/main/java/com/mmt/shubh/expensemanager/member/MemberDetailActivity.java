package com.mmt.shubh.expensemanager.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.base.ToolBarActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 5:55 PM
 * TODO:Add class comment.
 */
public class MemberDetailActivity extends ToolBarActivity {

    @Bind(R.id.backdrop)
    ImageView mImageView;

    @Inject
    MemberModel mMemberModel;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);
        mMemberModel = new MemberModel(getApplicationContext(), new MemberSQLDataAdapter(getApplicationContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        long id = getIntent().getLongExtra(Constants.KEY_MEMBER_ID, 1);
        mMemberModel.getMemberDetails(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mMemberObserver);
    }

    private Observer<Member> mMemberObserver = new Observer<Member>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Member member) {
            Glide.with(getApplicationContext())
                    .load(member.getCoverPhotoUrl())
                    .centerCrop()
                    .fitCenter()
                    .into(mImageView);
        }
    };
}
