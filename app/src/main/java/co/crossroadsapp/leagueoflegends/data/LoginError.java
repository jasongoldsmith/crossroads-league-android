package co.crossroadsapp.leagueoflegends.data;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginError {
    private GeneralServerError _error;
    private String _description = null;
    private String _responseType;
    private ErrorMessageType _messageType;

    public String getDescription()
    {
        return this._description;
    }

    public String getResponseType()
    {
        return this._responseType;
    }

    public GeneralServerError getGeneralServerError()
    {
        return this._error;
    }

    public void toJson(JSONObject json) {
        try {
            if (json.has("errorHandling") && !json.isNull("errorHandling")) {
                JSONObject jsonError = json.optJSONObject("errorHandling");
                if (jsonError != null) {
                    this._error = new GeneralServerError();
                    this._error.toJson(jsonError);
                    if (jsonError.has("description") && !jsonError.isNull("description")) {
                        this._description = jsonError.getString("description");
                    }
                }
            }
            if (json.has("message") && !json.isNull("message")) {
                int message = json.getInt("message");
            }
            if (json.has("responseType") && !json.isNull("responseType")) {
                this._responseType = json.getString("responseType");
            }
            if (json.has("message") && !json.isNull("message")) {
                JSONObject jsonError = json.optJSONObject("message");
                this._messageType = new ErrorMessageType();
                this._messageType.toJson(jsonError);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}