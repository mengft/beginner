package com.mengft.mengft_ui.Utils;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.Response;

public interface OKHttpCallback {
    void success(JSONObject successJson);
    void failed(JSONObject failedJson);
}
