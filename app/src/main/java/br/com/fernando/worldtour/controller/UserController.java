package br.com.fernando.worldtour.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import br.com.fernando.worldtour.model.domain.User;

/**
 * Created by fernando on 04/09/16.
 */
public class UserController {

    private Context context;
    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private final String USER_LOGGED = "userLogged";


    public UserController(Context context) {
        this.context = context;
    }

    public User populateUserByJson(JSONObject object) {
        User usuario = new User();

        try {

            if (object.has("id")) {
                usuario.setIdFacebook(object.getString("id"));
            }

            if (object.has("name")) {
                usuario.setName(object.getString("name"));
            }

            if (object.has("email")) {
                usuario.setEmail(object.getString("email"));
            }

            if (object.has("picture")) {
                usuario.setProfilePicUrl(object.getJSONObject("picture").getJSONObject("data").getString("url"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public void saveUserPreferences(User user) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsPrivateEditor.putString(USER_LOGGED, json);
        prefsPrivateEditor.commit();

    }

    public User getUserPreferences() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(USER_LOGGED, "");
        return gson.fromJson(json, User.class);

    }

}





