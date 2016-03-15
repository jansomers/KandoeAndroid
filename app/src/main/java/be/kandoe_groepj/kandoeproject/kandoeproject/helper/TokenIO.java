package be.kandoe_groepj.kandoeproject.kandoeproject.helper;

import android.content.SharedPreferences;
import android.media.session.MediaSession;

public class TokenIO {

    private static SharedPreferences sharedPreferences;

    public static void initSharedPreferences(SharedPreferences sharedPreferences) {
        TokenIO.sharedPreferences = sharedPreferences;
    }

    public static void saveToken(String token) {
        if (TokenIO.sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("token", token);
            edit.apply();
            System.out.println("ok");
        } else {
            throw new RuntimeException("Shared preferences not yet injected");
        }
    }

    public static String loadToken() {
        if (TokenIO.sharedPreferences != null) {
            return sharedPreferences.getString("token", "");
        } else {
            throw new RuntimeException("Shared preferences not yet injected");
        }
    }

    public static void removeToken() {
        if (TokenIO.sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("token", "");
            edit.apply();
            System.out.println("removed token");
        } else {
            throw new RuntimeException("Shared preferences not yet injected");
        }
    }
}
