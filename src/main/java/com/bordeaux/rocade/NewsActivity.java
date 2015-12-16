package com.bordeaux.rocade;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        NewsActivity.this.setTitle("Informations");

        final Context myApp = this;
        final Activity activity = this;

        // Configuration de la WebView :
        WebSettings settings = getWebView().getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        final WebView webview = (WebView) getWebView();
        webview.setWebViewClient(new MyWebViewClient(NewsActivity.this, "Chargement des alertes"));
        webview.setWebChromeClient(new MyWebChromeClient(NewsActivity.this));

        // Chargement de la page
        webview.loadUrl("http://hackjack.info/rocade/bordeaux/news.php");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private WebView getWebView() {
        WebView wvSite = (WebView) findViewById(R.id.webview);
        wvSite.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        return wvSite;
    }
}
