package ch.chtool.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.chtool.R;
import ch.chtool.utils.RecyclerViewHolder;

/**
 * Created by CH
 * on 2018/11/27 0027 09:27
 */
public abstract class PopupListAdapter<T> extends BaseAdapter {
    private List<T> mData;

    public PopupListAdapter(List<T> data) {
        this.mData = data;
    }

    public PopupListAdapter() {

    }

    public int getCount() {
        return this.mData.size();
    }

    public Object getItem(int position) {
        return this.mData.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popwindow_item, (ViewGroup) null);
            holder = new ViewHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convert(mData.get(position), position);
        return convertView;
    }

    class ViewHolder {
        TextView titleTv;

        ViewHolder() {
        }
    }

    public abstract void convert(T t, int position);
}