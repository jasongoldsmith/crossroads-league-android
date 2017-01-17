package co.crossroadsapp.leagueoflegends.network;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import co.crossroadsapp.leagueoflegends.ControlManager;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.Util;
import cz.msebera.android.httpclient.Header;

/**
 * Created by sharmha on 1/12/17.
 */
public class ReviewCardUpdate extends Observable {
    private final ControlManager mManager;
    private NetworkEngine ntwrk;
    private String url = Constants.UPDATE_REVIEW_CARD;

    public ReviewCardUpdate(Context c) {
        ntwrk = NetworkEngine.getmInstance(c);
        mManager = ControlManager.getmInstance();
    }

    public void postReviewUpdate(RequestParams params) throws JSONException {
        if(mManager!=null && mManager.getCurrentActivity()!=null) {
            if (Util.isNetworkAvailable(mManager.getCurrentActivity().getApplicationContext())) {
                ntwrk.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        //mManager.showErrorDialogue(null);
                    }
                });
            } else {
                Util.createNoNetworkDialogue(mManager.getCurrentActivity());
            }
        }
    }
}
