package ch.chtool.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.chtool.R;

/**
 * Created by CH
 * at 2019-09-16  17:44
 * 下拉框
 */
public class SpannerDropDownSingleDialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context mContext;
    private AlertDialog mDialog;
    public final static int COMPLEXLIST = 3;
    private int mTheme = -1;
    private int mLayoutResId;
    private List<String> mList = new ArrayList<String>();
    private OnItemClickListener mOnItemTextClickListener;

    private ListView mListView;
    private TextView mTitleText;
    private TextView mResettingTv;
    private SpannerGroupsSingleDialogAdapter mAdapter;
    private int mResIdForListView;
    private ListViewOnItemCLickListener mListOnItemClickListener;
    private CoSponsorListItemClickListener mCoSponsorListOnItemClickListener;
    private int mCurrentSelectionWithTheme3;

    private String mSelectionCode;
    private String mSelectionName;

    /**
     * 显示代码
     */
    private boolean mShowCode = false;

    public SpannerDropDownSingleDialog(Context context, int theme) {
        this.mContext = context;
        this.mTheme = theme;

        switch (mTheme) {
            case COMPLEXLIST:
                mLayoutResId = R.layout.dialog_spin_single_list;
                break;

            default:
                break;
        }
        initialize();

    }

    private void initialize() {
        mDialog = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.common_dialog)).create();
        show();
        dismiss();
    }

    public void create() {
        if (mDialog != null) {
            Window window = mDialog.getWindow();
            mDialog.setCanceledOnTouchOutside(true);
            if (window != null) {
                window.setContentView(mLayoutResId);
                window.setGravity(Gravity.BOTTOM);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(null);
                if (mTheme == COMPLEXLIST) {
                    mAdapter = new SpannerGroupsSingleDialogAdapter(mContext, mList, COMPLEXLIST);
                    mListView = window.findViewById(R.id.common_dialog_multiple_listview);
                    mTitleText = window.findViewById(R.id.common_dialog_title);
                    mListView.setOnItemClickListener(this);
                    mListView.setAdapter(mAdapter);
                    mListView.setSelection(mCurrentSelectionWithTheme3);
                    mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            mListView.setSelection(mCurrentSelectionWithTheme3);
                            mAdapter.changeSelected(mCurrentSelectionWithTheme3);
                        }
                    });
                    window.findViewById(R.id.sure).setVisibility(View.GONE);
                }
                window.findViewById(R.id.common_dialog_spin_cancel).setOnClickListener(this);

            }
        }
    }

    /**
     * 设置重置按钮可见
     */
    public void setResetVisible() {
        Window window = mDialog.getWindow();
        mResettingTv = window.findViewById(R.id.tv_resetting);
        mResettingTv.setVisibility(View.VISIBLE);
        mResettingTv.setOnClickListener(this);
    }

    /**
     * 设置确定按钮不可见
     */
    public void setSureGone() {
        Window window = mDialog.getWindow();
        TextView tvSure = window.findViewById(R.id.sure);
        tvSure.setVisibility(View.GONE);
    }


    /**
     * 设置重置按钮不可见
     */
    public void setResetGone() {
        Window window = mDialog.getWindow();
        mResettingTv = window.findViewById(R.id.tv_resetting);
        mResettingTv.setVisibility(View.GONE);
        mResettingTv.setOnClickListener(this);
    }

    public void setDataList(ArrayList<String> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
        }
    }

    public void notifyDataSetAllChanged(List<String> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
            if (mAdapter != null) {
                mAdapter.notifyDataSetAllChanged(mList);
            }
        }
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void show(int resId) {

        if (mDialog != null) {
            mDialog.show();
        }
        if (mListView == null) {
            return;
        }
        if (mTheme == COMPLEXLIST) {
            mListView.requestFocus();
            mListView.setItemChecked(mCurrentSelectionWithTheme3, true);
            mAdapter.changeSelected(mCurrentSelectionWithTheme3);
        }
        mResIdForListView = resId;
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public void setOnItemTextCLickListener(OnItemClickListener listener) {
        this.mOnItemTextClickListener = listener;
    }

    public void setOnListItemClickListener(ListViewOnItemCLickListener listener) {
        this.mListOnItemClickListener = listener;
    }

    public void setCoSponsorListItemClickListener(CoSponsorListItemClickListener listener) {
        this.mCoSponsorListOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_dialog_spin_cancel) {
            dismiss();
            return;
        } else if (id == R.id.tv_resetting) {
            if (mTheme == COMPLEXLIST) {
                mListOnItemClickListener.onListItemClickListener("", mResIdForListView, -1);
                dismiss();
            }
        }

    }

    public void setTitleText(String text) {
        if (mTitleText != null) {
            mTitleText.setText(text);
        }
    }

    public void listViewClickSelf(int resId) {
        mResIdForListView = resId;
        if (mListView != null) {
            mListView.performItemClick(null, 0, 0);
        }
    }

    public void listViewClickSelfWithArgs(int resId, int posi) {
        mResIdForListView = resId;
        if (mListView != null) {
            mListView.performItemClick(null, posi, posi);
        }
    }

    public int getCurrentSelectionWithTheme3() {
        return mCurrentSelectionWithTheme3;
    }


    public void setCurrentSelectionWithTheme3(int mCurrentSelectionWithTheme3) {
        this.mCurrentSelectionWithTheme3 = mCurrentSelectionWithTheme3;
        if (mListView != null) {
            mListView.setSelection(mCurrentSelectionWithTheme3);
            mAdapter.changeSelected(mCurrentSelectionWithTheme3);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mTheme == 3) {
            if (mListOnItemClickListener != null && mList != null && mList.size() > 0) {
                mListOnItemClickListener.onListItemClickListener(mList.get(position), mResIdForListView, position);
            } else {
                if (mListOnItemClickListener != null && mList != null) {
                    mListOnItemClickListener.onListItemClickListener("", mResIdForListView, position);
                }
            }
            dismiss();
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mDialog.setOnDismissListener(onDismissListener);
    }

    public interface OnItemClickListener {
        void onTextItemClickListener(String text, int position);
    }

    public interface ListViewOnItemCLickListener {
        void onListItemClickListener(String text, int resId, int position);
    }

    public interface CoSponsorListItemClickListener {
        void onCoSponsorListItemClickListener(String text, int resId, int position);
    }

    public String getSelectionCode() {
        return mSelectionCode;
    }

    public void setSelectionCode(String mSelectionCode) {
        this.mSelectionCode = mSelectionCode;
    }

    public String getSelectionName() {
        return mSelectionName;
    }

    public void setSelectionName(String mSelectionName) {
        this.mSelectionName = mSelectionName;
    }

    public boolean isShowCode() {
        return mShowCode;
    }

    public void setShowCode(boolean mShowCode) {
        this.mShowCode = mShowCode;
    }


}