package jp.obcn.memoleep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import jp.obcn.wearlib.WLUtils;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    private AppCompatButton mButton;
    private AppCompatTextView mTextHeartRate;

    private GoogleApiClient mGoogleAppiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextHeartRate = (AppCompatTextView) findViewById(R.id.TextHeatRate);
        mButton = (AppCompatButton) findViewById(R.id.BtnSend);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                            , "/test"
                                            , null)
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
        });

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
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            Wearable.MessageApi.removeListener(mGoogleAppiClient, mMessageListener);
            mGoogleAppiClient.disconnect();
        }
    }

    private MessageApi.MessageListener mMessageListener = new MessageApi.MessageListener() {
        @Override
        public void onMessageReceived(MessageEvent messageEvent) {
            Log.d(TAG, "onMessageReceived:" + messageEvent.getPath());

            float heartRate = WLUtils.readFloat(messageEvent.getData());

            Log.d(TAG,"heartRate:" + heartRate);

            mTextHeartRate.setText(String.valueOf(heartRate));

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
