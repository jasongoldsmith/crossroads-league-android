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
 * Created by sharmha on 9/29/16.
 */
public class ReportCommentNetwork extends Observable{

    private final ControlManager mManager;
    private NetworkEngine ntwrk;
    private String url = Constants.REPORT_COMMENT_URL;
    private Context mContext;

    public ReportCommentNetwork(Context c) {
        mContext = c;
        mManager = ControlManager.getmInstance();
        ntwrk = NetworkEngine.getmInstance(c);
    }

    public void doCommentReporting(RequestParams params) throws JSONException {
        if (Util.isNetworkAvailable(mContext)) {
//            String localUrl = url;
//            if(err == Constants.REPORT_COMMENT_NEXT) {
//                localUrl = urlSendForm;
//            }
            ntwrk.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    parseEventObject(response);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    // If the response is JSONObject instead of expected JSONArray
//                    Toast.makeText(mContext, "List server call Success",
//                            Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                    Toast.makeText(mContext, "List error from server  - " + statusCode + " ",
//                            Toast.LENGTH_LONG).show();
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

    private void parseEventObject(JSONObject obj) {
        EventData eData = new EventData();
        eData.toJson(obj);
        setChanged();
        notifyObservers(eData);
    }
}
