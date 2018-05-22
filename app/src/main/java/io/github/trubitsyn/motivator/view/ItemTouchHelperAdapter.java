package io.github.trubitsyn.motivator.view;

import android.support.v7.widget.RecyclerView;

public interface ItemTouchHelperAdapter {
    int allowedDirections(RecyclerView.ViewHolder holder);
    void onSwiped(RecyclerView.ViewHolder holder, int direction);
}
