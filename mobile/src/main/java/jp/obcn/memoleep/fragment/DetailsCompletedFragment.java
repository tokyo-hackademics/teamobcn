package jp.obcn.memoleep.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.obcn.memoleep.Activity.LessonSleepActivity;
import jp.obcn.memoleep.Model.LessonData;
import jp.obcn.memoleep.R;
import jp.obcn.memoleep.pref.GlobalConfig;

/**
 * Created by iwsbrfts on 2015/05/17.
 */
public class DetailsCompletedFragment extends Fragment {
    public static final String KEY_DATA = "key_data";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details_complieted,null);
        v.findViewById(R.id.BtnCompleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LessonData data = (LessonData) getArguments().getSerializable(KEY_DATA);
                Intent intent = new Intent(getActivity(), LessonSleepActivity.class);
                intent.putExtra(LessonSleepActivity.KEY_DATA, data);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        LessonData data = (LessonData) getArguments().getSerializable(KEY_DATA);

        TextView textLesson = (TextView) v.findViewById(R.id.TextLesson);
        textLesson.setText(data.title);

        TextView textMessage = (TextView) v.findViewById(R.id.TextMessage);
        textMessage.setText("You have completed the " +  data.title + "." );

        GlobalConfig.getInstance(getActivity()).writeCompletedLesson(data.title);

        return v;
    }
}
