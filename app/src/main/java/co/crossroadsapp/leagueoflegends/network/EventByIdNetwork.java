package co.crossroadsapp.leagueoflegends.network;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import co.crossroadsapp.leagueoflegends.ControlManager;
import co.crossroadsapp.leagueoflegends.data.EventData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.Util;
import cz.msebera.android.httpclient.Header;

/**
 * Created by sharmha on 6/9/16.
 */
public class EventByIdNetwork extends Observable {

    private Context mContext;
    private NetworkEngine ntwrk;
    private String url = Constants.EVENT_BY_ID_URL;

    private ControlManager mManager;

    public EventByIdNetwork(Context c) {
        mContext = c;
        mManager = ControlManager.getmInstance();
        ntwrk = NetworkEngine.getmInstance(c);
    }

    public void getEventById(RequestParams params) throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
            ntwrk.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        parseEvent(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

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

    private void parseEvent(JSONObject response) throws JSONException{
        EventData eData = new EventData();
        eData.toJson(response);
        setChanged();
        notifyObservers(eData);
    }
}
