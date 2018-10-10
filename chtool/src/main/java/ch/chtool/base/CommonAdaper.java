package ch.chtool.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * Created by CH
 * on 2018/10/10 16:13
 */
public abstract class CommonAdaper<T> extends BaseAdapter {
    private Context context;
    private List<T> list;
    private int mItemLayout;


    public CommonAdaper(Context context, List<T> list, int itemLayout) {
        this.context = context;
        this.list = list;
        this.mItemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(view, viewGroup, mItemLayout, i);
        convert(holder, getItem(i));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T item);

}