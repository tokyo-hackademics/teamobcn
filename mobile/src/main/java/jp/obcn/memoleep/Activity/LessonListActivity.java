package jp.obcn.memoleep.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import jp.obcn.memoleep.Model.LessonData;
import jp.obcn.memoleep.Model.LessonUtils;
import jp.obcn.memoleep.R;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonListActivity extends AppCompatActivity {


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
                vh.body = view.findViewById(R.id.Body);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            LessonData data = getItem(i);

            vh.title.setText(data.title);
            switch (data.type) {
                case LessonData.TYPE.COMPLETE:
                    break;
                case LessonData.TYPE.INCOMPLETE:
                    break;
                case LessonData.TYPE.NEXT:
                    break;
            }



            return view;
        }

        private class ViewHolder {
            public TextView title;
            public View body;

        }

    }



}
