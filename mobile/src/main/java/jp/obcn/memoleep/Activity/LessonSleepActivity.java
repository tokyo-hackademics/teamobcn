package jp.obcn.memoleep.Activity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

import jp.obcn.memoleep.Model.LessonData;
import jp.obcn.memoleep.Model.WordData;
import jp.obcn.memoleep.R;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonSleepActivity  extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final String TAG = LessonSleepActivity.class.getSimpleName();
    public static final String KEY_DATA = "key_data";

    private LessonData mData;

    private TextToSpeech mTts;

    private int mCount = 0;
    private int mType = 0;

    private TextView mTextWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_sleep);

        mTextWord = (TextView) findViewById(R.id.TextWord);

        mData = (LessonData) getIntent().getSerializableExtra(KEY_DATA);

        mTts = new TextToSpeech(this,this);
        mTts.setOnUtteranceProgressListener(new SleepUtteranceProgressListener());
        mTts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
            @Override
            public void onUtteranceCompleted(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nextSpeech();
                    }
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTts.shutdown();
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
                        nextSpeech();
                    }
                });
            } else {
                Toast.makeText(this,"Error: isLanguageAvailable:" + available,Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void nextSpeech() {

        if(mType == 0 ){
            WordData data = mData.Words.get(mCount);

            speech(data.word);



            mType = 1;
        } else if(mType == 1) {
            WordData data = mData.Words.get(mCount++);

            speech(data.example);

            mType = 0;

        }


    }

    private void speech(String message) {
        mTextWord.setText(message);


        if(Build.VERSION.SDK_INT < LOLLIPOP) {
            HashMap<String, String> ttsparam = new HashMap<String, String>();
            ttsparam.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, message);

            mTts.speak(message , TextToSpeech.QUEUE_FLUSH,ttsparam);
        } else {
            mTts.speak(message , TextToSpeech.QUEUE_FLUSH,null,message);

        }
    }


    private class SleepUtteranceProgressListener extends UtteranceProgressListener {

        @Override
        public void onStart(String s) {
            Log.d(TAG,"onStart:" + s);


        }

        @Override
        public void onDone(String s) {
            Log.d(TAG,"onDone:" + s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nextSpeech();
                }
            });
        }

        @Override
        public void onError(String s) {
            Log.d(TAG,"onError:" + s);

        }
    }

}
