package com.ssf.smart;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebFragment extends Fragment {
    private static final String ARG_PARAM1 = "url";
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.click)
    View click;

    private String url;
    private WebView web;
    private MainActivity _activity;
    private WebMusicInterface music;


    public static WebFragment newInstance(String param1) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public WebFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        web = (WebView) view.findViewById(R.id.webview);
        initWeb();
    }

    @OnClick(R.id.click)
    void onclick() {
        _activity.showDrawer();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        click.requestFocus();
        click.setFocusable(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = (MainActivity) activity;
        _activity.setShow(true);
    }

    private void initWeb() {
        boolean is = url.startsWith("http://");
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.loadUrl(is ? url : "http://" + url);
        music = new WebMusicInterface(getActivity(), web);
        web.addJavascriptInterface(music, "android");
//        web.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        music.release();
        clearWebViewCache();
        ButterKnife.unbind(this);
    }

    public void clearWebViewCache() {
    // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(getActivity());
        CookieManager.getInstance().removeAllCookie();
    }

    public void reload() {
        web.reload();
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.d(WebFragment.class.getSimpleName(), cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId());
            return true;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}

