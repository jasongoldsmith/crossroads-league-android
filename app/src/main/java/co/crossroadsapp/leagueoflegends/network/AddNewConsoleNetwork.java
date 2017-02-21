package co.crossroadsapp.leagueoflegends.network;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import co.crossroadsapp.leagueoflegends.ControlManager;
import co.crossroadsapp.leagueoflegends.core.BattletagAlreadyTakenException;
import co.crossroadsapp.leagueoflegends.core.InvalidEmailProvided;
import co.crossroadsapp.leagueoflegends.core.LeagueLoginException;
import co.crossroadsapp.leagueoflegends.core.NoUserFoundException;
import co.crossroadsapp.leagueoflegends.core.TrimbleException;
import co.crossroadsapp.leagueoflegends.data.GeneralServerError;
import co.crossroadsapp.leagueoflegends.data.LoginError;
import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.TravellerLog;
import co.crossroadsapp.leagueoflegends.utils.Util;
import cz.msebera.android.httpclient.Header;

/**
 * Created by sharmha on 8/2/16.
 */
public class AddNewConsoleNetwork extends Observable {

    private Context mContext;
    private NetworkEngine ntwrk;
    private String url = Constants.ADD_CONSOLE_URL;
    private ControlManager mManager;

    public AddNewConsoleNetwork(Context c) {
        mContext = c;
        mManager = ControlManager.getmInstance();
        ntwrk = NetworkEngine.getmInstance(c);
    }

    public void doAddConsole(RequestParams params, final String username) throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
            ntwrk.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //post gcm token
                    postGcm();
                    parseConsoleResponse(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //new error data structure
                    TravellerLog.w(this, "onFailure errorResponse: " + errorResponse);
                    dispatchError(statusCode, username, errorResponse);
                    //mManager.showErrorDialogue(Util.getErrorMessage(errorResponse));
                }
            });
        }else {
            Util.createNoNetworkDialogue(mContext);
        }
    }

    private void dispatchError(int statusCode, String email, JSONObject errorResponse) {
        LeagueLoginException exception = null;
        LoginError error = new LoginError();
        error.toJson(errorResponse);
            GeneralServerError er = error.getGeneralServerError();
            if (er != null && er.getCode() == GeneralServerError.NO_USER_FOUND_WITH_THE_EMAIL) {
                exception = new NoUserFoundException(statusCode, error.getDescription(), error.getGeneralServerError());
            } else if (er != null && er.getCode() == GeneralServerError.ALREADY_TAKEN){
                exception = new BattletagAlreadyTakenException(statusCode, error.getDescription(), error.getGeneralServerError());
            } else {
                mManager.showErrorDialogue(Util.getErrorMessage(errorResponse));
                return;
            }
            exception.setUserTag(email);
        setChanged();
        notifyObservers(exception);
    }

    private void postGcm() {
        //post gcm token
        Util.getGCMToken(mContext, mManager);
    }

    private void parseConsoleResponse(JSONObject response) {
            //parse response
            UserData user = new UserData();
            if(response!=null) {
                user.toJson(response);
            }
        if(user!=null) {
            if((user.getClanId()!=null) && (!user.getClanId().isEmpty())) {
                mManager.setUserdata(user);
            }
            setChanged();
            notifyObservers(user);
        }
    }

}
