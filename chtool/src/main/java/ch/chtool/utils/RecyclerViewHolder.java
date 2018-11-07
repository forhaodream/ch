package ch.chtool.utils;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by CH
 * on 2018/11/7 16:47
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;//稀疏数组,存放传递的布局中所有view

    private RecyclerViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static RecyclerViewHolder getViewHolder(View itemView) {
        return new RecyclerViewHolder(itemView);
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则直接到布局里面找,并加入到views中
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public SparseArray<View> getViews() {
        return this.views;
    }

}