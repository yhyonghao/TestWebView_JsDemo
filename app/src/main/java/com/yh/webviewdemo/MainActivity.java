package com.yh.webviewdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar= (ProgressBar) findViewById(R.id.progressId);
        webView= (WebView) findViewById(R.id.webviewId);
        //给webview设置一个客户端
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //网页开始加载
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //网页加载结束
            }
        });
        //使用webchrome来监听网页加载进度
        webView.setWebChromeClient(new WebChromeClient(){
            //监听webview的alert弹出

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //改变html的弹出框风格
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("我是android的弹出框")
                        .setMessage("白骨精跑了")
                        .setCancelable(true)
                        .create()
                        .show();
                result.confirm();//确认结果
                return true;//将这个alert的反应交给onJsAlert来处理
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);


                //监听网页的加载进度
                if (newProgress==100){
                    //进度条在加载完后消失
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setProgress(newProgress);//设置进度值
            }
        });

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//是否可以运行javascript脚本
        webSettings.setSupportZoom(true);//支持缩放功能
        webSettings.setDisplayZoomControls(true);//缩放控制
        webSettings.setSupportMultipleWindows(true);//多窗口

        webSettings.getAllowContentAccess();

        //webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("file:///android_asset/welcome.html");
        //将javascript的注解添加给webview  参数1---注解方法的类对象
        // 参数2---在html的javascript里面调用的关键字
        webView.addJavascriptInterface(new ScriptUtils(this),"android");
    }

    public void onClick(View view){

        setTitle("我是白骨精");
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("我是android的弹出框")
                .setMessage("白骨精跑了")
                .setCancelable(true)
                .create()
                .show();


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();//如果有上一页，就返回上一页
            }else{
                MainActivity.this.finish();
            }
        }
        return true;//意思是这个按键事件我来消费
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(1,1,1,"编辑信息");//添加一个menu来处理html的text

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==1){
            //将android变量以参数方式传给html的text来显示
            //调用javaScript的方法
//            webView.loadUrl("javascript:addUserInfo('yh"+ System.currentTimeMillis()+"')");
            webView.loadUrl("javascript:showTitle()");
        }
        return super.onOptionsItemSelected(item);
    }
}