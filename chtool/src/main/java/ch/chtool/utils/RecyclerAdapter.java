package ch.chtool.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by CH
 * on 2018/11/7 16:47
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;//上下文
    private int mLayoutId;//构造传递进来的布局
    private List<T> mDatas;//数据集合

    public RecyclerAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = datas;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //先inflate数据
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        //返回ViewHolder
        return RecyclerViewHolder.getViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        //绑定怎么办？回传出去
        if (mDatas != null && mDatas.size() > 0) {
            convert(holder, mDatas.get(position), position);
        }
    }

    /**
     * 利用抽象方法回传出去
     *
     * @param itemData 当前的数据
     */
    public abstract void convert(RecyclerViewHolder holder, T itemData, int position);

}