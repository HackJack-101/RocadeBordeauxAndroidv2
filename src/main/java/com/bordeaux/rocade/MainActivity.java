package com.bordeaux.rocade;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null)
                sb.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Activity activity = this;

        // Configuration de la WebView :
        WebSettings settings = getWebView().getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        final WebView webview = (WebView) getWebView();
        webview.setWebViewClient(new MyWebViewClient(MainActivity.this, "Chargement de la carte"));
        webview.setWebChromeClient(new MyWebChromeClient(MainActivity.this));

        // Chargement de la page
        webview.loadUrl("file:///android_asset/index.html");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (getWebView().canGoBack() == true) {
                        getWebView().goBack();
                    } else {
                        finish();
                    }
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

    public String getScriptContent(String filename) {
        try {
            AssetManager mngr = getBaseContext().getAssets();
            return getStringFromInputStream(mngr.open(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        final WebView webview = (WebView) getWebView();
        switch (item.getItemId()) {
            case R.id.home:
                webview.loadUrl("file:///android_asset/index.html");
                return true;
            case R.id.quit:
                finish();
                return true;
            case R.id.news:
                Intent news = new Intent(this, NewsActivity.class);
                startActivity(news);
                return true;
            default:
                return false;
        }
    }
}
