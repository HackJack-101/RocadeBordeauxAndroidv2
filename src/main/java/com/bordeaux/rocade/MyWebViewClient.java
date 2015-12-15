package com.bordeaux.rocade;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Jean on 15/12/2015.
 */
public class MyWebViewClient extends WebViewClient {
    private ProgressDialog pDialog;
    private int state = 0;
    private Context context;
    private String loadingMessage;

    MyWebViewClient(Context context, String loadingMessage)
    {
        this.context = context;
        this.loadingMessage = loadingMessage;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (state == 0) {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage(loadingMessage);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            state = 1;
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        pDialog.dismiss();
        pDialog.cancel();
        state = 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        view.loadUrl("file:///android_asset/error.html");
    }

    @TargetApi(android.os.Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
        // Redirect to deprecated method, so you can use it in all SDK versions
        onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url != null && !url.startsWith("http://hackjack.info/rocade") && !url.startsWith("file:///android_asset/")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else
            return false;
    }
}
