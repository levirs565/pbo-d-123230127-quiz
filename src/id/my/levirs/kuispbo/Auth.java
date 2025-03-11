package id.my.levirs.kuispbo;

public class Auth {
    private String mLoggedAs;

    private Auth() {

    }

    public boolean login(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty() || !password.equals("pbo-d"))
            return false;

        mLoggedAs = username;
        return true;
    }

    public String getLoggedAs() {
        return mLoggedAs;
    }

    static private Auth sInstance = new Auth();

    static public Auth getInstance() {
        return sInstance;
    }
}
