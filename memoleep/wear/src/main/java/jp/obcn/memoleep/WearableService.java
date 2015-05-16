package jp.obcn.memoleep;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class WearableService extends WearableListenerService {
    private static final String TAG = WearableService.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "onMessageReceived");
        super.onMessageReceived(messageEvent);

        SensorManager sm = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        sm.registerListener(WSensorListener.getInstance(getApplicationContext())
                , sensor
                , SensorManager.SENSOR_DELAY_NORMAL);

    }


    private static class WSensorListener implements SensorEventListener {

        private Context mContext;
        private static WSensorListener sInstance;

        public static WSensorListener getInstance(Context context) {
            if(sInstance == null) {
                sInstance = new WSensorListener(context);
            }
            return sInstance;
        }


        private WSensorListener(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float heartRate = sensorEvent.values[0];
            Log.d(TAG,"HeartRate:" + heartRate);

            Intent intent = new Intent(mContext,SendMessageService.class);
            intent.putExtra(SendMessageService.KEY_HEART_RATE,heartRate);
            mContext.startService(intent);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}
