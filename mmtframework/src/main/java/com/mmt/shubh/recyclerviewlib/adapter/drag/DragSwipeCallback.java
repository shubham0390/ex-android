package com.mmt.shubh.recyclerviewlib.adapter.drag;

/**
 * Created by shubham on 11/16/15.
 */
public interface DragSwipeCallback {


   void onSwipe(int position);

    boolean onDrag(int fromPosition, int toPosition);
}
