package jp.obcn.memoleep.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonUtils {

    public static final int MAX_SIZE = 21;

    public static List<LessonData> createLessonData() {

        ArrayList<LessonData> list = new ArrayList<>();
        for(int i = 0;i<MAX_SIZE;i++) {
            LessonData data = new LessonData();
            List<WordData> words = new ArrayList<>();
            for(int l = 0;l< LessonData.MAX_SIZE;l++) {
                WordData word = new WordData();
                word.word = "Hello";
                word.phoneric = "Hello";
                word.example = "Hello Everywhere";
                words.add(word);
            }
            data.title = "Lesson " + (i+1);
            if(i < 2) {
                data.type = LessonData.TYPE.COMPLETE;
            } else if(i < 3) {
                data.type = LessonData.TYPE.NEXT;
            }
            data.Words = words;
            list.add(data);
        }
        return list;
    }
}
