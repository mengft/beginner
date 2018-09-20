package com.mengft.mengft_ui.Utils;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static org.json.JSONObject.wrap;

/**
 * Created by mengft on 2018/6/24.
 */

public class ObjectConvert {

    /**
     * 将Bundle 转化为 String
     *
     * @param bundle
     * @return
     */
    public String convertBundleToJson(Bundle bundle) {

        JSONObject jsonObject = new JSONObject();

        for (String key : bundle.keySet()) {
            Object obj = bundle.get(key);
            try {
                jsonObject.put(key, wrap(bundle.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject.toString();
    }


    /**
     * mapToJsonString
     *
     * @param map
     * @return
     * @throws JSONException
     */
    public String mapToJsonString(Map<String, String> map) throws JSONException {
        JSONObject info = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            info.put(entry.getKey(), entry.getValue());
        }
        return info.toString();
    }

}
