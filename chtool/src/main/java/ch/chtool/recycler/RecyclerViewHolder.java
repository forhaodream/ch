package ch.chtool.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by CH
 * on 2018/11/26 0026 15:05
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views = new SparseArray();

    private RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public static RecyclerViewHolder getViewHolder(View itemView) {
        return new RecyclerViewHolder(itemView);
    }

    public <T extends View> T getView(int viewId) {
        View view = (View) this.views.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            this.views.put(viewId, view);
        }

        return (T) view;
    }

    public SparseArray<View> getViews() {
        return this.views;
    }
}