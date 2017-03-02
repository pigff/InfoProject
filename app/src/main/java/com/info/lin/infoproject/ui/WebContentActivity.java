package com.info.lin.infoproject.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.ui.base.BaseActivity;
import com.info.lin.infoproject.utils.Constants;

public class WebContentActivity extends BaseActivity {

    private FrameLayout mLayout;
    private ProgressBar mProgressBar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_content);

        init();
    }

    private void init() {
        initActionBar();

        Intent intent = getIntent();
        GankItemBean bean = (GankItemBean) intent.getSerializableExtra(Constants.DATA_INTENT);

        if (bean != null) {
            setTitle(bean.getDesc());
        }

        mProgressBar = (ProgressBar) findViewById(R.id.pg_web_content);
        mLayout = (FrameLayout) findViewById(R.id.web_content_container);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLayout.addView(mWebView);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.loadUrl(bean.getUrl());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    public static Intent newInstance(Context context, GankItemBean bean) {
        Intent intent = new Intent(context, WebContentActivity.class);
        intent.putExtra(Constants.DATA_INTENT, bean);
        return intent;
    }

    //Web视图
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }
            mWebView.stopLoading();
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        mLayout.removeAllViews();
        super.onDestroy();
    }
}