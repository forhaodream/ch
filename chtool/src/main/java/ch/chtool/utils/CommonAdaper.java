package ch.chtool.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Ch on 2018/11/7
 */
public abstract class CommonAdaper<T> extends BaseAdapter {

    private Context mContext;//上下文
    private int mLayoutId;//构造传递进来的布局
    private List<T> mDatas;//数据集合

    public CommonAdaper(Context mContext, int mLayoutId, List<T> mDatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(view, mContext, mLayoutId, viewGroup);
        if (mDatas != null && mDatas.size() > 0) {
            convert(viewHolder, getItem(position), position);
        }
        return viewHolder.getConvertView();
    }

    /**
     * 利用抽象方法回传出去
     *
     * @param itemData 当前的数据
     */
    public abstract void convert(CommonViewHolder holder, T itemData, int position);
}