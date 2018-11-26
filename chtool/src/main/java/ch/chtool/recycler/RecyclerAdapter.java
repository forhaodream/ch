package ch.chtool.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.chtool.utils.RecyclerViewHolder;

/**
 * Created by CH
 * on 2018/11/26 0026 15:04
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<T> mDatas;

    public RecyclerAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = datas;
    }

    public int getItemCount() {
        return this.mDatas == null ? 0 : this.mDatas.size();
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.mContext).inflate(this.mLayoutId, parent, false);
        return RecyclerViewHolder.getViewHolder(itemView);
    }

    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (this.mDatas != null && this.mDatas.size() > 0) {
            this.convert(holder, this.mDatas.get(position), position);
        }

    }

    public abstract void convert(RecyclerViewHolder var1, T var2, int var3);
}
