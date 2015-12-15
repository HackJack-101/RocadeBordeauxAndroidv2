package com.bordeaux.rocade;

import android.content.Context;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Jean on 15/12/2015.
 */


public class MyWebChromeClient extends WebChromeClient {
    private Context context;

    MyWebChromeClient(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
        Log.d("alert", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        result.confirm();
        return true;
    }
}
