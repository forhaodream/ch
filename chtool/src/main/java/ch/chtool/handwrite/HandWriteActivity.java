package ch.chtool.handwrite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import ch.chtool.R;

/**
 * Created by CH
 * at 2019-09-17  09:18
 * 手写签名
 */
public class HandWriteActivity extends AppCompatActivity {

    private static String SingPath;

    private HandWriteView hand_write_view;

    private Button btn_ok;
    private Button btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_write);

        Intent intent = getIntent();
        SingPath = intent.getStringExtra("path");

        hand_write_view = findViewById(R.id.hand_write_view);
        btn_ok = findViewById(R.id.btn_ok);
        btn_clear = findViewById(R.id.btn_clear);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hand_write_view.clear();
                if (hand_write_view.isSign()) {
                    try {
                        hand_write_view.save(SingPath);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();   //返回存储的图片路径显示图片
                        bundle.putSerializable("result_list_photo", SingPath);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hand_write_view.clear();
            }
        });
    }
}