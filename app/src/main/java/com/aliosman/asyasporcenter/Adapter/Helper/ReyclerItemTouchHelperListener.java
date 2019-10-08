package com.aliosman.asyasporcenter.Adapter.Helper;

import android.support.v7.widget.RecyclerView;

public interface ReyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);

}
