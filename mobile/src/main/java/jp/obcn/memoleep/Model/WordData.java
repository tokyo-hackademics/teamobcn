package jp.obcn.memoleep.Model;

import java.io.Serializable;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class WordData implements Serializable {

    public WordData(String word , String phoneric, String mean , String example) {
        this.word = word;
        this.phoneric = phoneric;
        this.mean = mean;
        this.example = example;
    }



    public String word;
    public String phoneric;
    public String mean;
    public String example;
}
