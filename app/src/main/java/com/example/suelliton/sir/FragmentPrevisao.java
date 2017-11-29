package com.example.suelliton.sir;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by suelliton on 29/11/2017.
 */

public class FragmentPrevisao extends Fragment{
    View view;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_previsao,container,false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.loadUrl("https://weather.com/pt-BR/clima/hoje/l/-5.88,-35.36");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView v, String url, Bitmap icon) {
                progressBar.setVisibility(WebView.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView v, String url){
                progressBar.setVisibility(WebView.INVISIBLE);
            }

        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);


        return view;
    }
}
