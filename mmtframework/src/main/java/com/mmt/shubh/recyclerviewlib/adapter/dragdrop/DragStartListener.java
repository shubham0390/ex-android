package com.mmt.shubh.recyclerviewlib.adapter.dragdrop;

import android.support.v7.widget.RecyclerView;

/**
 * Created by shubham on 11/17/15.
 */
public interface DragStartListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
