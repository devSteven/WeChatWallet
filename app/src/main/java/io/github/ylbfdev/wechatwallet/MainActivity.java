package io.github.ylbfdev.wechatwallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import im.fir.sdk.FIR;
import io.github.ylbfdev.wechatwallet.callback.VersionCheckCallbackImpl;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RiseNumberTextView textView2;
    private Button button2;
    private Toolbar toolbar;

    /**
     * Called when activity start-up is complete (after onStart() and onRestoreInstanceState(Bundle) have been called).
     *
     * @param savedInstanceState
     */
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Toolbar 必须在onCreate()之后设置标题文本，否则默认标签将覆盖我们的设置
        if (toolbar != null) {
            toolbar.setTitle("零钱");
            toolbar.setSubtitle("微信安全支付");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //检测更新
        FIR.checkForUpdateInFIR("b88e8d1428640c52312e6fcd7a187b80", new VersionCheckCallbackImpl(this));
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);

        textView2 = (RiseNumberTextView) findViewById(R.id.textView2);

        try {
            // 设置数据
            //noinspection ConstantConditions
            textView2.withNumber(104062.05f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置动画播放时间
        textView2.setDuration(10000);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setText(String.format("￥%s", 0.00));
            }
        });
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    textView2.start();
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Random random = new Random();
            float f = random.nextFloat() * 100000;
            textView2.withNumber(f);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
