package ch.chtool.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.chtool.R;

/**
 * Created by CH
 * at 2019-09-16  17:46
 */
public class SpannerGroupsSingleDialogAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mList = new ArrayList<String>();
    private int mtheme;

    public SpannerGroupsSingleDialogAdapter(Context context, List<String> list, int theme) {
        this.mContext = context;
        mList.addAll(list);
        this.mtheme = theme;
    }

    int mSelect = 0;   //选中项

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    public ArrayList<String> getRemainData() {
        return mList;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_single_spindrop_listview, null);
            holder = new ViewHolder();
            holder.mTextView = convertView.findViewById(R.id.common_dialog_item_tv);
            holder.mArrowIv = convertView.findViewById(R.id.tv_state);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(mList.get(position));

        if (mtheme == 3) {
            if (mSelect == position) {
                holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.dialog_spin_item_text_pressed));
                holder.mArrowIv.setVisibility(View.VISIBLE);
            } else {
                holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.colorDeliveryText));
                holder.mArrowIv.setVisibility(View.GONE);
            }
        } else {
        }
        return convertView;
    }

    public void changeSelected(int positon) { //刷新方法
        if (mtheme == 3) {
            if (positon != mSelect) {
                mSelect = positon;
                notifyDataSetChanged();
            }
        }
    }

    public void notifyDataSetAllChanged(List<String> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView mTextView;
        ImageView mArrowIv;
    }
}