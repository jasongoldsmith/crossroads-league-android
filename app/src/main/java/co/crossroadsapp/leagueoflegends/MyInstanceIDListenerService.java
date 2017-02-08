package co.crossroadsapp.leagueoflegends;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import co.crossroadsapp.leagueoflegends.utils.Util;

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {
    private static final String TAG = "Firebase_Token";
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is also called
     * when the InstanceID token is initially generated, so this is where
     * you retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        ControlManager cm = ControlManager.getmInstance();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        Util.setDefaults("token", refreshedToken, cm.getCurrentActivity());
//        Util.getGCMToken(cm.getCurrentActivity(),cm);
//        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

    }

}
