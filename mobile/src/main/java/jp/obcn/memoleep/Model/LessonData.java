package jp.obcn.memoleep.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class LessonData implements Serializable{

    public class TYPE {
        public static final int INCOMPLETE = 0;
        public static final int COMPLETE = 1;
        public static final int NEXT = 2;
    }

    public static final int MAX_SIZE = 10;
    public String title;
    public int type = TYPE.INCOMPLETE;
    public List<WordData> Words;


    public boolean isNext = false;


}
