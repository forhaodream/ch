package ch.chtool.handwrite;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * Created by CH
 * at 2019-09-17  10:36
 */
public class HandWriteTest extends Activity {
    private void toHandWrite() {
        // 跳转到电子签名界面，进行签名，传入一个图片存储路径
        Intent intent = new Intent(this, HandWriteActivity.class);
        intent.putExtra("path", "图片存储路径");
        startActivityForResult(intent, 4000);
    }
}
