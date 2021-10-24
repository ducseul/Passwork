package com.ducseul.passwork.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.ducseul.passwork.entity.DataObject;
import com.google.gson.Gson;

import java.util.HashMap;

public class KEY {
    public class CONFIG{
        public static final String DO_CONFIG_OK = "is-do-config-ok";
    }

    public class INTENT_EXTRA {

    }

    public static String APP_DATA_KEY = "app-data-key";

    public static class CACHE_DATA_OBJECT_MANAGER {
        private static DataObject DATA_OBJECT;
        public static DataObject getDataInstance(){
            if(DATA_OBJECT == null){
                DATA_OBJECT = new DataObject();
            }
            return DATA_OBJECT;
        }

        public static void setDataObject(DataObject dataObject){
            getDataInstance().setPIN_resolve(dataObject.getPIN_resolve());
            getDataInstance().setRecoveryComb(dataObject.getRecoveryComb());
            getDataInstance().setListData(dataObject.getListData());
        }

        public static void saveChangeUpdate(Context context){
            SharedPreferenceManager instance_appData = SharedPreferenceManager.getInstance_AppData(context);
            SharedPreferences.Editor editor = instance_appData.getmEditor();
            editor.putString(KEY.APP_DATA_KEY, new Gson().toJson(getDataInstance()));
            editor.commit();
            Toast.makeText(context, "Save success", Toast.LENGTH_SHORT).show();
        }
    }

    public static class CACHE_MANAGER{
        public static HashMap<String, String> AUTO_COMPLETE_DICT;
    }
}
