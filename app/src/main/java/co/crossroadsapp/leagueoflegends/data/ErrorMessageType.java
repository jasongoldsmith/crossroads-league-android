package co.crossroadsapp.leagueoflegends.data;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorMessageType {
    private String _type = null;

    public String getType()
    {
        return this._type;
    }

    public void toJson(JSONObject json) throws JSONException {
        if (json.has("type") && !json.isNull("type")) {
            this._type = json.getString("title");
        }
    }
}