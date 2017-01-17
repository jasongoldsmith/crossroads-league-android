package co.crossroadsapp.leagueoflegends.network;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import co.crossroadsapp.leagueoflegends.ControlManager;
import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
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

    public void doAddConsole(RequestParams params) throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
            ntwrk.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    parseConsoleResponse(response);
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
