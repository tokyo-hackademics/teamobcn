package jp.obcn.memoleep.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import jp.obcn.memoleep.Model.LessonData;
import jp.obcn.memoleep.Model.LessonUtils;
import jp.obcn.memoleep.R;
import jp.obcn.memoleep.pref.GlobalConfig;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private GridView mGridView;
    private LessonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        mGridView = (GridView) findViewById(R.id.GrdiView);

        mAdapter = new LessonAdapter(this,0);
        mAdapter.addAll(LessonUtils.createLessonData());

        mGridView.setAdapter(mAdapter);
        mGridView.setSelector(android.R.color.transparent);
        mGridView.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("Select Lessons");
        toolbar.setPopupTheme(R.style.PopThemeStyle);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // いつものUPナビゲーションの処理
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(getApplication(), SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        LessonData data = mAdapter.getItem(i);
        Intent intent = new Intent(this,LessonDetailsActivity.class);
        intent.putExtra(LessonDetailsActivity.KEY_DATA,data);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private class LessonAdapter extends ArrayAdapter<LessonData> {

        public LessonAdapter(Context context, int resource) {
            super(context, resource);
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.grid_item , null);
                vh = new ViewHolder();
                vh.title = (TextView) view.findViewById(R.id.TextItem);
                vh.frame = view.findViewById(R.id.Body);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            LessonData data = getItem(i);

            vh.title.setText(data.title);

            boolean bRet = GlobalConfig.getInstance(getApplicationContext()).readCompletedLesson(data.title);

            if(bRet) {
                vh.frame.setBackgroundResource(R.drawable.grid_item_frame_complete_tap);
                vh.title.setTextColor(getResources().getColor(R.color.sub_color_3_common));
            } else if(mFirstInComplteted) {
                vh.frame.setBackgroundResource(R.drawable.grid_item_frame_next_tap);
                vh.title.setTextColor(getResources().getColor(R.color.sub_color_1_lesson));
                data.isNext = true;
                mFirstInComplteted = false;
            } else if(data.isNext) {
                vh.frame.setBackgroundResource(R.drawable.grid_item_frame_next_tap);
                vh.title.setTextColor(getResources().getColor(R.color.sub_color_1_lesson));
                data.isNext = true;
                mFirstInComplteted = false;
            } else {
                vh.frame.setBackgroundResource(R.drawable.grid_item_frame_incomplete_tap);
                vh.title.setTextColor(getResources().getColor(R.color.text_color_3_common));
            }


//            switch (data.type) {
//                case LessonData.TYPE.COMPLETE:
//                    vh.frame.setBackgroundResource(R.drawable.grid_item_frame_complete_tap);
//                    break;
//                case LessonData.TYPE.INCOMPLETE:
//                    vh.frame.setBackgroundResource(R.drawable.grid_item_frame_incomplete_tap);
//                    break;
//                case LessonData.TYPE.NEXT:
//                    vh.frame.setBackgroundResource(R.drawable.grid_item_frame_next_tap);
//                    break;
//            }



            return view;
        }

        private class ViewHolder {
            public TextView title;
            public View frame;
            public View underline;

        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            mFirstInComplteted = true;
        }

        private boolean mFirstInComplteted = true;
    }

}
