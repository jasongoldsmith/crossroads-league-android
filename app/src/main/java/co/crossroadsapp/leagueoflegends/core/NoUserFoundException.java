package co.crossroadsapp.leagueoflegends.core;

/**
 * Created by karagdi on 2/3/17.
 */

public class NoUserFoundException extends LeagueLoginException {
    public NoUserFoundException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public NoUserFoundException(int errorCode, String msg, Object customData) {
        super(errorCode, msg, customData);
    }

    public NoUserFoundException(int errorCode, Exception inner) {
        super(errorCode, inner);
    }

    public NoUserFoundException(int errorCode) {
        super(errorCode);
    }
}
