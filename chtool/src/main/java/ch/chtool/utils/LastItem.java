package ch.chtool.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by CH
 * on 2018/11/7 16:30
 */
public class LastItem {
    public OnScrollListener mClickListener;
    public RecyclerView mRecyclerView;

    public LastItem(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void OnLastItem(OnScrollListener onScrollListener) {
        this.mClickListener = onScrollListener;

    }

    public interface OnScrollListener {
        void isLast();
    }

    public void onScrollLast() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        if (mClickListener != null)
                            mClickListener.isLast();
                    } else {
                        Log.d("防止", "崩");
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });

    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


}
