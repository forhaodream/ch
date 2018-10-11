package ch.chtool.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;

import ch.chtool.R;

/**
 * Created by CH
 * on 2018/10/11 13:08
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 获取TAG的activity名称
     **/
    protected final String TAG = this.getClass().getSimpleName();

    protected Intent intent;

    protected Activity oThis;

    protected Context context;

    protected AlertDialog alertDialog;

    protected BaseActivity mActivity;

    protected abstract int setView();

    protected abstract void init(View view);

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(setView(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }



    /**
     * 显示LoadingDialog
     */
    protected void showLoadingDialog() {
        alertDialog = new AlertDialog.Builder(oThis).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        alertDialog.show();
        alertDialog.setContentView(R.layout.dialog_loading);
        alertDialog.setCancelable(true); // 是否可以按“返回键”消失
        alertDialog.setCanceledOnTouchOutside(true);// 点击加载框以外的区域
    }

    /**
     * 取消LoadingDialog显示
     */
    protected void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    /**
     * 显示长toast
     *
     * @param msg
     */
    protected void toastLong(String msg) {
        Toast.makeText(oThis, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示短toast
     *
     * @param msg
     */
    protected void toastShort(String msg) {
        Toast.makeText(oThis, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * [不携带数据的 startActivity]
     *
     * @param mClass
     */
    protected void startActivity(Class<?> mClass) {
        Intent intent = new Intent(oThis, mClass);
        startActivity(intent);
    }

    /**
     * [携带数据的 startActivity]
     *
     * @param mClass
     * @param bundle
     */
    protected void startActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(oThis, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [不携带数据的 startActivityForResult]
     *
     * @param mClass
     * @param requestCode
     */
    protected void startActivityForResult(Class<?> mClass, int requestCode) {
        Intent intent = new Intent(oThis, mClass);
        startActivityForResult(intent, requestCode);
    }

    /**
     * [携带数据的 startActivityForResult]
     *
     * @param mClass
     * @param bundle
     * @param requestCode
     */
    protected void startActivityForResult(Class<?> mClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(oThis, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected String getBundleToString(String key) {
        Bundle bundle = intent.getBundleExtra("bundle");
        return bundle.getString(key);
    }

    protected int getBundleToInt(String key) {
        Bundle bundle = intent.getBundleExtra("bundle");
        return bundle.getInt(key);
    }

    protected Double getBundleToDouble(String key) {
        Bundle bundle = intent.getBundleExtra("bundle");
        return bundle.getDouble(key);
    }

    protected Float getBundleToFloat(String key) {
        Bundle bundle = intent.getBundleExtra("bundle");
        return bundle.getFloat(key);
    }

    protected Boolean getBundleToBoolean(String key) {
        Bundle bundle = intent.getBundleExtra("bundle");
        return bundle.getBoolean(key);
    }

    protected Serializable getBundleToSerializable(String key) {
        Bundle bundle = intent.getBundleExtra("bundle");
        return bundle.getSerializable(key);
    }
}