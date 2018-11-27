package ch.chtool.recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import ch.chtool.R;
import ch.chtool.view.PopupWindowView;

/**
 * Created by CH
 * on 2018/11/27 0027 09:36
 */
public class AAAA extends Activity {
    PopupWindowView<String> popupWindowView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popupWindowView = new PopupWindowView<String>(R.layout.aaaaaaaaaa, this) {
            @Override
            public void convert(String o, int position) {

            }
        };
        TextView textView;
        textView = findViewById(R.id.id_tag_autolayout_margin);
        popupWindowView.showAsDropDown(textView, Gravity.BOTTOM, 0, 0);
    }
}
