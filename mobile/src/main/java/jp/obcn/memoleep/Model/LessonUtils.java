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

            switch(i % 3) {
                case 0:
                    words.add(new WordData("achievement","ətʃíːvmənt","業績","He was praised for that <b><i>achievement</i></b> and became famous."));
                    words.add(new WordData("bias","bάɪəs","偏見","The villagers had a <b><i>bias</i></b> against any newcomer."));
                    words.add(new WordData("charge","tʃάɚdʒ","料金","The person in <b><i>charge</i></b> of the company."));
                    words.add(new WordData("duty","d(j)úːṭi","義務","You have forgotten your <b><i>duty</i></b> to your parents."));
                    words.add(new WordData("enthusiasm","ɪnθ(j)úːzi`æzm","熱情","They have lost most of their initial <b><i>enthusiasm</i></b>."));
                    words.add(new WordData("feature","fíːtʃɚ","特性","The difference between smart phones and <b><i>feature</i></b> phones."));
                    words.add(new WordData("geography","dʒiάgrəfi","地理","I don't know much about the <b><i>geography</i></b> of this area."));
                    words.add(new WordData("harbor","hάɚbɚ","港","Formerly this <b><i>harbor</i></b> was prosperous."));
                    words.add(new WordData("incident","ínsədnt","事件","That is an <b><i>incident</i></b> from approximately 5 years ago."));
                    words.add(new WordData("justice","dʒˈʌstɪs","公正","They are demanding equal rights and <b><i>justice</i></b>."));
                    break;
                case 1:
                    words.add(new WordData("knowledge","nάlɪdʒ","知識","Experience is subservient to <b><i>knowledge</i></b>."));
                    words.add(new WordData("literature","líṭərətʃ`ʊɚ","文学","He is well read in English <b><i>literature</i></b>."));
                    words.add(new WordData("material","mətí(ə)riəl","材料","This <b><i>material</i></b> withstands hard wear."));
                    words.add(new WordData("nerve","nˈɚːv","神経","That was very <b><i>nerve</i></b>-racking work."));
                    words.add(new WordData("origin","ˈɔːrədʒɪn","起源","The <b><i>origin</i></b> of this move was a picture book."));
                    words.add(new WordData("personality","p`ɚːsənˈæləṭi","個性","Noticing <b><i>personality</i></b> differences."));
                    words.add(new WordData("quarrel","kwˈɔːrəl","口論","The lovers began to <b><i>quarrel</i></b> with each other."));
                    words.add(new WordData("region","ríːdʒən","地域","Seasoning also differs from <b><i>region</i></b> to region."));
                    words.add(new WordData("seed","síːd","種","What on earth is this <b><i>seed</i></b>?"));
                    words.add(new WordData("tendency","téndənsi","傾向","He has a <b><i>tendency</i></b> to talk too much."));

                    break;
                case 2:
                    words.add(new WordData("usage","júːsɪdʒ","語法","This instrument will not stand rough <b><i>usage</i></b>."));
                    words.add(new WordData("virus","vάɪ(ə)rəs","ウイルス","That looks like the work of a <b><i>virus</i></b>."));
                    words.add(new WordData("welfare","wélfèɚ","福祉","You're in charge of pupil <b><i>welfare</i></b>."));
                    words.add(new WordData("world","wˈɚːld","世界","I find out what the <b><i>world</i></b> needs.  Then, I go ahead and invent it."));
                    words.add(new WordData("youth","júːθ","青春時代","He has all the appearance of <b><i>youth</i></b>."));
                    words.add(new WordData("acquire","əkwάɪɚ","得る","They thought that they wanted to <b><i>acquire</i></b> new customers."));
                    words.add(new WordData("breathe","bríːð","呼吸する","I can't <b><i>breathe</i></b> through my nose."));
                    words.add(new WordData("combine","ˈkɒm.baɪn","結びつける","Let's <b><i>combine</i></b> our efforts."));
                    words.add(new WordData("decrease","dɪkríːs","減少する","I want to <b><i>decrease</i></b> the tanks."));
                    words.add(new WordData("encounter","ɪnkάʊnṭɚ","遭遇する","We <b><i>encountered</i></b> a number of difficulties in the first week."));

                    break;

            }


//            for(int l = 0;l< LessonData.MAX_SIZE;l++) {
//                WordData word = new WordData();
//                word.word = "Good Morning";
//                word.phoneric =  "Hello i=" + i + " l=" + l;
//                word.mean = "";
//                word.example = "Hello Everywhere";
//                words.add(word);
//            }
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
