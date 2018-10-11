package ch.chtool.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.transition.Visibility;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by CH
 * on 2018/10/10 16:11
 */
public class ViewHolder {
    private final SparseArray<View> views;
    private int position;
    private View convertView;
    private Context context;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.context = context;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static ViewHolder get(View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(parent.getContext(), parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 设置字符串
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        Glide.with(context).load(url).into((ImageView) getView(viewId));
        return this;
    }

    public int getPosition() {
        return position;
    }

    // 绑定View
    public ViewHolder findView(int viewId, int visibility) {
        View view = getView(viewId);
        if (visibility == 0) {
            view.setVisibility(View.VISIBLE);
        } else if (visibility == 1) {
            view.setVisibility(View.GONE);
        }
        return this;
    }

}
