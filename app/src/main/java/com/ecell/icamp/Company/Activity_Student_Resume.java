package com.ecell.icamp.Company;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ecell.icamp.R;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Activity_Student_Resume extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_resume);

        WebView webview = (WebView)findViewById(R.id.webview);
        webview.setWebViewClient(new Callback());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(false);
        webview.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //webview.loadUrl("https://drive.google.com/file/d/1llLU-Fpo-K3W0i2bnA-1lYNPdFGeZ9wM/view");
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + "http://kiitecell.hol.es/ShopStalk_Privacy_Policy.doc");
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
    }
}