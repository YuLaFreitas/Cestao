package com.mercados.cestao.ui.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.mercados.cestao.ComunicacaoWeb;

public class CatologoViewModel extends ViewModel {

    public CatologoViewModel() { }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void webView(String empresa, String categoria,
                               WebView categoriaPagina, Context c){

        String url =
                "http://listadecomprasylfreitas.xyz/" + empresa +
                        "/" + categoria + ".html";

        categoriaPagina.getSettings().setJavaScriptEnabled(true);
        categoriaPagina.getSettings().getBlockNetworkLoads();
        categoriaPagina.showContextMenu();
        categoriaPagina.getSettings().setTextZoom(categoriaPagina.getSettings().getTextZoom());
        categoriaPagina.getSettings().setBuiltInZoomControls(false);
        categoriaPagina.addJavascriptInterface(
                new ComunicacaoWeb(c), "Ponte");
        categoriaPagina.getSettings().setDefaultFontSize(30);
        categoriaPagina.getSettings().setDomStorageEnabled(true);
        categoriaPagina.getSettings().setLoadsImagesAutomatically(true);
        categoriaPagina.getSettings().setLoadWithOverviewMode(false);
        categoriaPagina.getSettings().setUseWideViewPort(true);
        categoriaPagina.getSettings().setAllowContentAccess(true);
        categoriaPagina.getSettings().setAllowFileAccessFromFileURLs(true);
        categoriaPagina.getSettings().setAllowUniversalAccessFromFileURLs(true);
        categoriaPagina.getSettings().setNeedInitialFocus(true);
        categoriaPagina.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        categoriaPagina.getSettings().setGeolocationEnabled(true);
        categoriaPagina.getSettings().setOffscreenPreRaster(true);
        categoriaPagina.getSettings().setSupportMultipleWindows(true);
        categoriaPagina.clearCache(true);

        categoriaPagina.loadUrl(url);
    }
}