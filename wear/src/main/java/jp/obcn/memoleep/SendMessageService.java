package jp.obcn.memoleep;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import jp.obcn.wearlib.WLConst;
import jp.obcn.wearlib.WLUtils;

/**
 * Created by iwsbrfts on 2015/05/16.
 */
public class SendMessageService extends IntentService {
    private static final String TAG = SendMessageService.class.getSimpleName();

    public static final String KEY_HEART_RATE = "key_heart_rate";

    private GoogleApiClient mGoogleAppiClient;

    public SendMessageService() {
        super("SendMessageService");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mGoogleAppiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);

                        Wearable.MessageApi.addListener(mGoogleAppiClient, mMessageListener);

                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();

        mGoogleAppiClient.connect();

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        float heartRate = intent.getFloatExtra(KEY_HEART_RATE,0f);

        sendMessage(WLConst.ACTION_HEART_RATE_SEND, WLUtils.fromFloat(heartRate));


    }

    private MessageApi.MessageListener mMessageListener = new MessageApi.MessageListener() {
        @Override
        public void onMessageReceived(MessageEvent messageEvent) {
            Log.d(TAG, "onMessageReceived:" + messageEvent.getPath());
        }
    };


    private void sendMessage(final String path , final byte[] bytes) {
        Wearable.NodeApi.getConnectedNodes(mGoogleAppiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult results) {
                        List<Node> nodes = results.getNodes();
                        for (Node node : nodes) {
                            String id = node.getId();
                            String name = node.getDisplayName();
                            Log.d(TAG, "id:" + id + " name:" + name);
                            Wearable.MessageApi.sendMessage(mGoogleAppiClient
                                    , id
                                    , path
                                    , bytes)
                                    .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                                        @Override
                                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                            Log.d(TAG,"result:" + sendMessageResult.getRequestId());
                                        }
                                    });
                        }
                    }
                });
    }

}
