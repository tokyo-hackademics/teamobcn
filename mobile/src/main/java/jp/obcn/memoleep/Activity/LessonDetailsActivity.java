package jp.obcn.memoleep.Activity;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import jp.obcn.memoleep.Model.LessonData;
import jp.obcn.memoleep.Model.WordData;
import jp.obcn.memoleep.R;
import jp.obcn.memoleep.fragment.DetailsCompletedFragment;
import jp.obcn.memoleep.fragment.DetailsFragment;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonDetailsActivity extends AppCompatActivity implements View.OnClickListener
        , TextToSpeech.OnInitListener {
    private static final String TAG = LessonDetailsActivity.class.getSimpleName();
    public static final String KEY_DATA = "key_data";


    private int mCount = 0;

    private LessonData mData;

    private View mFooter;
    private View mFrameProgress;

    private ProgressBar mProgressBar;
    private TextView mTextProgressTime;

    private static final int MAX_DURATION = 3000;
    private static final int DURATION = 10;

    private Handler mHandler = new Handler();

    private TextToSpeech mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);
        findViewById(R.id.TextKnown).setOnClickListener(this);
        findViewById(R.id.TextDontknow).setOnClickListener(this);
        mFooter = findViewById(R.id.Footer);
        mFrameProgress = findViewById(R.id.FrameProgress);
        mProgressBar = (ProgressBar) findViewById(R.id.Progress);
        mProgressBar.setMax(MAX_DURATION);

        mTextProgressTime = (TextView) findViewById(R.id.TextProgressTime);

        mData = (LessonData) getIntent().getSerializableExtra(KEY_DATA);

        mTts = new TextToSpeech(this,this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("Lessons");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                LessonDetailsActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()) {
            mTts.stop();
            mTts.shutdown();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void nextData() {
        if (isFinishing()) {
            return;
        }

        if(mTts.isSpeaking()) {
            mTts.stop();
        }


        if (mData.Words.size() > mCount) {
            addFragment(mData.Words.get(mCount++));

            cancelTimer();

            mProgressBar.setProgress(MAX_DURATION);

            mTimer = new Timer(true);
            mTimer.schedule(new TimerTask() {
                private int mDuration = MAX_DURATION;

                @Override
                public void run() {
                    mDuration -= DURATION;


                    if(mDuration <= 0) {

                        cancelTimer();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinishing()) {
                                    return;
                                }

                                mProgressBar.setProgress(0);
                                mTextProgressTime.setText("0");

                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        nextData();

                                    }
                                }, 1000);
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mDuration);
                                mTextProgressTime.setText(String.valueOf(mDuration));
                            }
                        });

                    }

                }
            }, DURATION, DURATION);


        } else {
            mFooter.setVisibility(View.GONE);
            mFrameProgress.setVisibility(View.GONE);
            Fragment fragment = new DetailsCompletedFragment();
            Bundle args = new Bundle();
            args.putSerializable(DetailsCompletedFragment.KEY_DATA, getIntent().getSerializableExtra(KEY_DATA));
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Frame, fragment)
                    .commit();

        }

    }


    private void speech(String message) {

        if(Build.VERSION.SDK_INT < LOLLIPOP) {
            HashMap<String, String> ttsparam = new HashMap<String, String>();
            ttsparam.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, message);

            mTts.speak(message, TextToSpeech.QUEUE_FLUSH, ttsparam);
        } else {
            mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null, message);

        }
    }

    private void addFragment(WordData data) {

        speech(data.word);

        Fragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailsFragment.KEY_WORD_DATA, data);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Frame, fragment)
                .commit();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.TextDontknow) {

            nextData();

        } else if (id == R.id.TextKnown) {

            nextData();

        }

    }

    private Timer mTimer = null;

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    @Override
    public void onInit(int status) {
        Log.d(TAG, "status:" + status);
        if(status == TextToSpeech.SUCCESS) {
            Locale locale = Locale.US;
            int available = mTts.isLanguageAvailable(locale);
            if (available >= TextToSpeech.LANG_AVAILABLE) {
                mTts.setLanguage(locale);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nextData();
                    }
                });
            } else {
                Toast.makeText(this, "Error: isLanguageAvailable:" + available, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
