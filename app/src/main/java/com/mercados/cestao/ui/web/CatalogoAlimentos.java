package com.mercados.cestao.ui.web;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.mercados.cestao.R;

public class CatalogoAlimentos extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater
                .inflate(R.layout.fragment_web, container, false);

        final WebView categoriaPagina = root.findViewById(R.id.web);

        CatologoViewModel.webView(
                "cestao",
                "alimentos",
                categoriaPagina, this.getContext());

        return root;
    }
}
