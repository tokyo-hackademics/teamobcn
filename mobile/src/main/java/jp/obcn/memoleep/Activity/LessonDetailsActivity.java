package jp.obcn.memoleep.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jp.obcn.memoleep.Model.LessonData;
import jp.obcn.memoleep.Model.WordData;
import jp.obcn.memoleep.R;
import jp.obcn.memoleep.fragment.DetailsFragment;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String KEY_DATA = "key_data";


    private int mCount = 0;

    private LessonData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);
        findViewById(R.id.BtnKnown).setOnClickListener(this);
        findViewById(R.id.BtnDontknow).setOnClickListener(this);

        mData = (LessonData) getIntent().getSerializableExtra(KEY_DATA);
        nextData();
    }

    private void nextData() {

        if(mData.Words.size() > mCount) {
            addFragment(mData.Words.get(mCount++));
        } else {

        }

    }


    private void addFragment(WordData data) {

        Fragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailsFragment.KEY_WORD_DATA, data);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Frame,fragment)
                .commit();


    }




    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.BtnDontknow) {

            nextData();

        } else if (id == R.id.BtnKnown) {

            nextData();

        }

    }
}
