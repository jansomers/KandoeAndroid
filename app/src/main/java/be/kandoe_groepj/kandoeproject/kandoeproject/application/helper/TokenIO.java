package be.kandoe_groepj.kandoeproject.kandoeproject.application.helper;

import android.content.SharedPreferences;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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

    public static String getUserId() {
        try {
            String token = loadToken();
            byte[] data = Base64.decode(token.split("\\.")[1], Base64.DEFAULT);
            String rawTokenBody = new String(data, "UTF-8");
            JSONObject tokenBody = new JSONObject(rawTokenBody);
            return tokenBody.getString("_id");
        } catch (UnsupportedEncodingException | JSONException  e) {
            e.printStackTrace();
            return "ERROR";
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
