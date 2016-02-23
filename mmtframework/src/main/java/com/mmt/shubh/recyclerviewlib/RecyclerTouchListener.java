package com.mmt.shubh.recyclerviewlib;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Used for listening to RecyclerView item clicks. You can either implement an OnItemClickListener
 * or extend SimpleOnItemClickListener and override its methods.
 * <p/>
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private static final String LOGTAG = "ClickItemTouchListener";

    private ListRecyclerView.OnItemClickListener mItemClickListener;

    private ListRecyclerView.OnItemLongClickListener mItemLongClickListener;

    private final GestureDetectorCompat mGestureDetector;

    RecyclerTouchListener(RecyclerView hostView) {
        mGestureDetector = new ItemClickGestureDetector(hostView.getContext(), new ItemClickGestureListener(hostView));
    }

    private boolean isAttachedToWindow(RecyclerView hostView) {
        if (Build.VERSION.SDK_INT >= 19) {
            return hostView.isAttachedToWindow();
        } else {
            return (hostView.getHandler() != null);
        }
    }

    private boolean hasAdapter(RecyclerView hostView) {
        return (hostView.getAdapter() != null);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
        if (!isAttachedToWindow(recyclerView) || !hasAdapter(recyclerView)) {
            return false;
        }

        mGestureDetector.onTouchEvent(event);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent event) {
        // We can silently track tap and and long presses by silently
        // intercepting touch events in the host RecyclerView.
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemClickGestureDetector extends GestureDetectorCompat {
        private final ItemClickGestureListener mGestureListener;

        public ItemClickGestureDetector(Context context, ItemClickGestureListener listener) {
            super(context, listener);
            mGestureListener = listener;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final boolean handled = super.onTouchEvent(event);

            final int action = event.getAction() & MotionEventCompat.ACTION_MASK;
            if (action == MotionEvent.ACTION_UP) {
                mGestureListener.dispatchSingleTapUpIfNeeded(event);
            }

            return handled;
        }
    }

    private class ItemClickGestureListener extends GestureDetector.SimpleOnGestureListener {
        private final RecyclerView mHostView;
        private View mTargetChild;

        public ItemClickGestureListener(RecyclerView hostView) {
            mHostView = hostView;
        }

        public void dispatchSingleTapUpIfNeeded(MotionEvent event) {
            // When the long press hook is called but the long press listener
            // returns false, the target child will be left around to be
            // handled later. In this case, we should still treat the gesture
            // as potential item click.
            if (mTargetChild != null) {
                onSingleTapUp(event);
            }
        }

        @Override
        public boolean onDown(MotionEvent event) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            mTargetChild = mHostView.findChildViewUnder(x, y);
            return (mTargetChild != null);
        }

        @Override
        public void onShowPress(MotionEvent event) {
            if (mTargetChild != null) {
                mTargetChild.setPressed(true);
            }
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            boolean handled = false;

            if (mTargetChild != null) {
                mTargetChild.setPressed(false);

                final int position = mHostView.getChildAdapterPosition(mTargetChild);
                final long id = mHostView.getAdapter().getItemId(position);
                if (mItemClickListener != null) {
                    handled = mItemClickListener.onItemClick(mHostView, mTargetChild, position, id);
                }
                mTargetChild = null;
            }

            return handled;
        }

        @Override
        public boolean onScroll(MotionEvent event, MotionEvent event2, float v, float v2) {
            if (mTargetChild != null) {
                mTargetChild.setPressed(false);
                mTargetChild = null;

                return true;
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            boolean handled = false;
            if (mTargetChild == null) {
                return;
            }

            final int position = mHostView.getChildAdapterPosition(mTargetChild);
            final long id = mHostView.getAdapter().getItemId(position);

            if(mItemLongClickListener!=null) {
                handled = mItemLongClickListener.onItemLongClick(mHostView, mTargetChild, position, id);
            }
            if (handled) {
                mTargetChild.setPressed(false);
                mTargetChild = null;
            }
        }
    }

    public void setOnItemClickListener(ListRecyclerView.OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(ListRecyclerView.OnItemLongClickListener longClickListener) {
        mItemLongClickListener = longClickListener;
    }
}