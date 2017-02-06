package co.crossroadsapp.leagueoflegends.network;

import android.content.Context;

import co.crossroadsapp.leagueoflegends.ControlManager;
import co.crossroadsapp.leagueoflegends.MainActivity;
import co.crossroadsapp.leagueoflegends.utils.Util;
import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sharmha on 3/17/16.
 */
public class LoginNetwork extends Observable {

    private Context mContext;
    private NetworkEngine ntwrk;
    //private String url = Constants.VALIDATE_USERLOGIN_URL;
    private String url = Constants.LOGIN_URL;
    private String url_reg = Constants.REGISTRATION_URL;
    private String urlUser = Constants.USER_BY_ID_URL;
    private UserData user;
    private ControlManager mManager;

    public LoginNetwork(Context c) {
        mContext = c;
        mManager = ControlManager.getmInstance();
        ntwrk = NetworkEngine.getmInstance(c);
    }

    public void doSignup(RequestParams params) throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
            ntwrk.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //post gcm token
                    postGcm();
                    parseSignInUser(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    Toast.makeText(mContext, "Signup error from server  - " + statusCode,
//                            Toast.LENGTH_LONG).show();
                    if(errorResponse.has("errorType")) {
                        try {
                            ((MainActivity)mContext).showError(Util.getErrorMessage(errorResponse), errorResponse.getString("errorType"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mManager.showErrorDialogue(Util.getErrorMessage(errorResponse));
                    }
                }
            });
        }else {
            Util.createNoNetworkDialogue(mContext);
        }
    }

    public void getUser(RequestParams params) throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
            ntwrk.post(urlUser, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    parseSignInUser(response);
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

    public void doRegister(RequestParams params) throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
            ntwrk.post(url_reg, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    parseSignUpUser(response);
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

    private void postGcm() {
        //post gcm token
        Util.getGCMToken(mContext, mManager);
    }

    private void parseSignInUser(JSONObject response) {
        user = new UserData();
        user.toJson(response);
        user.setAuthenticationId(Constants.LOGIN);
        setChanged();
        notifyObservers(user);
    }

    private void parseSignUpUser(JSONObject response) {
        user = new UserData();
        user.toJson(response);
        user.setAuthenticationId(Constants.REGISTER);
        setChanged();
        notifyObservers(user);
    }
}
