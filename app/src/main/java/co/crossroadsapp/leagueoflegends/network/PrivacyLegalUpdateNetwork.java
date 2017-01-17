package co.crossroadsapp.leagueoflegends.network;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import co.crossroadsapp.leagueoflegends.ControlManager;
import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.Util;
import cz.msebera.android.httpclient.Header;

/**
 * Created by sharmha on 8/3/16.
 */
public class PrivacyLegalUpdateNetwork extends Observable {
    private ControlManager mManager;
    private Context mContext;
    private NetworkEngine ntwrk;
    private String url = Constants.ACCEPT_LEGAL_URL;

    public PrivacyLegalUpdateNetwork(Context c) {
        mContext = c;
        mManager = ControlManager.getmInstance();
        ntwrk = NetworkEngine.getmInstance(c);
    }

    public void postTermsPrivacyDone() throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
            ntwrk.post(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    parseUser(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    mManager.showErrorDialogue(Util.getErrorMessage(errorResponse));
                }
            });
        }else {
            Util.createNoNetworkDialogue(mContext);
        }
    }

    private void parseUser(JSONObject response) {
        UserData user = new UserData();
        user.toJson(response);
        if(user!=null) {
            if(user.getUserId()!=null) {
                mManager.setUserdata(user);
            }
        }
        setChanged();
        notifyObservers(user);
    }
}
