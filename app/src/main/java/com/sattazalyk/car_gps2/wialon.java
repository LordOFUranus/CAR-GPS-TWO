package com.sattazalyk.car_gps2;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class wialon extends Fragment {
    WebView wv_wialon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wialon, container, false);

        wv_wialon = view.findViewById(R.id.wv_wialon);
        wv_wialon.getSettings().setJavaScriptEnabled(true);
        wv_wialon.getSettings().setAllowContentAccess(true);
        wv_wialon.getSettings().setGeolocationEnabled(true);
        wv_wialon.getSettings().setDomStorageEnabled(true);
        wv_wialon.getSettings().setBuiltInZoomControls(true);
        wv_wialon.getSettings().setUseWideViewPort(true);
        wv_wialon.getSettings().setLoadWithOverviewMode(true);


        wv_wialon.loadUrl("https://hosting.wialon.com");

        WebViewClient webViewClient = new WebViewClient(){
            @SuppressWarnings("deprecation") @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
            @TargetApi(Build.VERSION_CODES.N) @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        };

        wv_wialon.setWebViewClient(webViewClient);
        return view;
    }
}