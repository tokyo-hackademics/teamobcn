package jp.obcn.memoleep.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import jp.obcn.memoleep.Model.LessonData;
import jp.obcn.memoleep.Model.WordData;
import jp.obcn.memoleep.R;
import jp.obcn.memoleep.fragment.DetailsCompletedFragment;
import jp.obcn.memoleep.fragment.DetailsFragment;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonDetailsActivity extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);
        findViewById(R.id.BtnKnown).setOnClickListener(this);
        findViewById(R.id.BtnDontknow).setOnClickListener(this);
        mFooter = findViewById(R.id.Footer);
        mFrameProgress = findViewById(R.id.FrameProgress);
        mProgressBar = (ProgressBar) findViewById(R.id.Progress);
        mProgressBar.setMax(MAX_DURATION);

        mTextProgressTime = (TextView) findViewById(R.id.TextProgressTime);

        mData = (LessonData) getIntent().getSerializableExtra(KEY_DATA);
        nextData();
    }

    private void nextData() {

        if (mData.Words.size() > mCount) {
            addFragment(mData.Words.get(mCount++));

            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            mProgressBar.setProgress(MAX_DURATION);

            mTimer = new Timer(true);
            mTimer.schedule(new TimerTask() {
                private int mDuration = MAX_DURATION;

                @Override
                public void run() {
                    mDuration -= DURATION;


                    if(mDuration <= 0) {

                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
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
                                        if (isFinishing()) {
                                            return;
                                        }

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


    private void addFragment(WordData data) {

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

        if (id == R.id.BtnDontknow) {

            nextData();

        } else if (id == R.id.BtnKnown) {

            nextData();

        }

    }

    private Timer mTimer = null;


}
