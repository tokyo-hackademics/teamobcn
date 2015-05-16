package jp.obcn.memoleep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import jp.obcn.memoleep.MainActivity;
import jp.obcn.memoleep.R;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class TopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        Handler hdl = new Handler();
        hdl.postDelayed(new splashHandler(), 2000);
    }

    class splashHandler implements Runnable {
        public void run() {
            // スプラッシュ完了後に実行するActivityを指定します。
            Intent intent = new Intent(getApplication(), LessonListActivity.class);
            startActivity(intent);
            // SplashActivityを終了させます。
            TopActivity.this.finish();
        }
    }
}
