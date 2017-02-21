package co.crossroadsapp.leagueoflegends.core;

/**
 * Created by karagdi on 2/2/17.
 */

public class LeagueLoginException extends TrimbleException {
    private String _userTag;
    public String getUserTag()
    {
        return this._userTag;
    }

    public void setUserTag( String userTag )
    {
        this._userTag = userTag;
    }

    public LeagueLoginException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public LeagueLoginException(int errorCode, String msg, Object customData) {
        super(errorCode, msg, customData);
    }

    public LeagueLoginException(int errorCode, Exception inner) {
        super(errorCode, inner);
    }

    public LeagueLoginException(int errorCode) {
        super(errorCode);
    }
}
