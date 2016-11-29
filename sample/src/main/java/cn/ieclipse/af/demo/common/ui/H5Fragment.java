/*
 * Copyright (C) 2015-2016 QuickAF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ieclipse.af.demo.common.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import cn.ieclipse.af.demo.R;

/**
 * WebView in fragment is special.
 *
 * @author Jamling
 * @see android.webkit.WebViewFragment
 */
public class H5Fragment extends BaseFragment {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SHARE = "share";

    private ProgressBar mPb;
    private WebView mWebView;

    private String mUrl = "";
    private String mTitle;
    private ImageView mShareIv;

    private static final int PB_FAKE_MAX = 85;

    private Handler mPbHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mPb.getProgress() < PB_FAKE_MAX) {
                mPb.setProgress(mPb.getProgress() + 1);
                mPbHandler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.main_activity_h5;
    }

    @Override
    protected void initHeaderView() {
        super.initHeaderView();
        mTitleTextView.setText(mTitle);
        mTitleTextView.setSingleLine(true);
        mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        if (mWebView != null) {
            mWebView.destroy();
        }
        mPb = (ProgressBar) view.findViewById(android.R.id.progress);
        mWebView = (WebView) view.findViewById(android.R.id.content);
    }

    @Override
    protected void initData() {
        super.initData();
        initWebViewSettings();
        load();
    }

    protected void initWebViewSettings() {
        // 能使用JavaScript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new H5WebViewClient());

        // 设置setWebChromeClient对象
        mWebView.setWebChromeClient(new H5WebChromeClient());

        // 优先使用缓存
        // 不是用缓存（webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);）
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        mWebView.getSettings().setDomStorageEnabled(true);
    }

    protected void load() {
        // WebView加载web资源
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * @param url   请求URL（以http://开头的完整URL）
     * @param title 默认初始title
     */
    public static Fragment create(String url, String title) {
        H5Fragment fragment = new H5Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_URL, url);
        bundle.putString(EXTRA_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initIntent(Bundle bundle) {
        super.initIntent(bundle);
        mUrl = bundle.getString(EXTRA_URL);
        mTitle = bundle.getString(EXTRA_TITLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_URL, mUrl);
        outState.putString(EXTRA_TITLE, mTitle);
        super.onSaveInstanceState(outState);
    }

    protected void onUpdateTitle(WebView webView, String title) {
        mTitleTextView.setText(title);
    }

    protected boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return true;
    }

    protected class H5WebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            onUpdateTitle(webView, s);
        }

        @Override
        public void onProgressChanged(WebView webView, int i) {
            mPb.setProgress(i);
            if (i >= 100) {
                mPb.setVisibility(View.GONE);
            }
            else {
                mPb.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(webView, i);
        }
    }

    private class H5WebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            return H5Fragment.this.shouldOverrideUrlLoading(webView, s);
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
