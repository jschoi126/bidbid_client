package com.aze51.bidbid_client.service;

import android.content.Context;
import android.hardware.camera2.params.Face;

import com.aze51.bidbid_client.Network.User;

/**
 * Created by jeon3029 on 16. 7. 7..
 */
public class PrefUtils {

    public static void setCurrentUser(FaceBookUser currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("current_user_value", currentUser);
        complexPreferences.commit();
    }

    public static FaceBookUser getCurrentUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        FaceBookUser currentUser = complexPreferences.getObject("current_user_value", FaceBookUser.class);
        return currentUser;
    }

    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }


}