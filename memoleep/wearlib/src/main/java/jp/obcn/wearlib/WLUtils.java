package jp.obcn.wearlib;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class WLUtils {
    public static byte[] fromFloat(float value) {
        int arraySize = Float.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
        return buffer.putFloat(value).array();
    }

    public static float readFloat(byte[] bytes) {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new ByteArrayInputStream(bytes));

            return in.readFloat();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(in);
        }
        return 0f;
    }


    public static void close(Closeable closeable) {
        try {
            if(closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
