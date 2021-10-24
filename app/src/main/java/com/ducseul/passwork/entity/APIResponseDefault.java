package com.ducseul.passwork.entity;

import com.android.volley.VolleyError;

public interface APIResponseDefault {
    default void onAPIResponseRaw(String string) {

    }

    default void onAPIRequestFail(VolleyError error) {
        try {
            String body = new String(error.networkResponse.data, "UTF-8");
            System.out.println(body);
        } catch (Exception ignore) {

        }
    }
}
