package jp.obcn.memoleep.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.obcn.memoleep.Model.WordData;
import jp.obcn.memoleep.R;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class DetailsFragment extends Fragment {
    public static final String KEY_WORD_DATA = "key_word_data";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        WordData data = (WordData) args.getSerializable(KEY_WORD_DATA);

        View v = inflater.inflate(R.layout.fragment_details_item, null);

        TextView textExample = (TextView) v.findViewById(R.id.TextExample);
        TextView textMean = (TextView) v.findViewById(R.id.TextMean);
        TextView textWord = (TextView) v.findViewById(R.id.TextWord);
        TextView textPhonenic = (TextView) v.findViewById(R.id.TextPhonenic);


        textExample.setText(Html.fromHtml(data.example));
        textMean.setText(data.mean);
        textWord.setText(data.word);
        textPhonenic.setText(data.phoneric);

        return v;
    }
}
