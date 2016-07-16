package com.bupt.read.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bupt.read.R;
import com.bupt.read.model.entity.AnimationJokeItem;
import com.bupt.read.model.entity.BeautyPicItem;
import com.bupt.read.utils.FileUtil;
import com.bupt.read.utils.ImageUtils;
import com.bupt.read.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by xs on 2016/6/20.
 */
public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private WebView webView;
    private PhotoView photoView;
    private RelativeLayout rl_topbar;
    private LinearLayout ll_bottombar;
    private ImageButton img_back,img_share,img_download;
    private String url_path;
    private String from;
    private boolean isGif;
    private boolean isBarShow = true;
    public static final int ANIMATION_DURATION = 400;
    private  AnimationJokeItem animaJoke;
    private BeautyPicItem beautyPicItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_detail);
        initViews();
        initDatas();
    }


    private void initViews() {
        webView = (WebView) findViewById(R.id.web_gif);
        photoView = (PhotoView) findViewById(R.id.img);
        rl_topbar = (RelativeLayout) findViewById(R.id.rl_top_bar);
        ll_bottombar = (LinearLayout) findViewById(R.id.ll_bottom_bar);
        img_back = (ImageButton) findViewById(R.id.img_back);
        img_share = (ImageButton) findViewById(R.id.img_share);
        img_download = (ImageButton) findViewById(R.id.img_download);
        img_back.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_download.setOnClickListener(this);
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                toggleBar();
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        toggleBar();
    }

    private void initDatas() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if(from.equals("beauty")){
            beautyPicItem = (BeautyPicItem) intent.getSerializableExtra("beauty_info");
            url_path = beautyPicItem.getPicUrl();
        }else{
            animaJoke = (AnimationJokeItem) intent.getSerializableExtra("img_jokes");
            url_path = animaJoke.getImg();
        }

        isGif = intent.getBooleanExtra("isGif", false);
        if(isGif){
            ll_bottombar.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            showInCenter(url_path);
            webView.addJavascriptInterface(this, "external");
            webView.setWebViewClient(
           new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    webView.loadUrl(url);
                    return true;
                }

            });

            webView.setWebChromeClient(new WebChromeClient());
            webView.setBackgroundColor(Color.BLACK);
            photoView.setVisibility(View.GONE);
        }else{
            photoView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(url_path, photoView, ImageUtils.getOptions());

        }

    }

    private void showInCenter(final String s) {
        if (webView != null) {
            webView.loadDataWithBaseURL("", "<!doctype html> <html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <title></title><style type=\"text/css\"> html,body{width:100%;height:100%;margin:0;padding:0;background-color:black;} *{ -webkit-tap-highlight-color: rgba(0, 0, 0, 0);}#box{ width:100%;height:100%; display:table; text-align:center; background-color:black;} body{-webkit-user-select: none;user-select: none;-khtml-user-select: none;}#box span{ display:table-cell; vertical-align:middle;} #box img{  width:100%;} </style> </head> <body> <div id=\"box\"><span><img src=\"img_url\" alt=\"\"></span></div> <script type=\"text/javascript\" >document.body.onclick=function(e){window.external.onClick();e.preventDefault(); };function load_img(){var url=document.getElementsByTagName(\"img\")[0];url=url.getAttribute(\"src\");var img=new Image();img.src=url;if(img.complete){\twindow.external.img_has_loaded();\treturn;};img.onload=function(){window.external.img_has_loaded();};img.onerror=function(){\twindow.external.img_loaded_error();};};load_img();</script></body> </html>".replace("img_url", s), "text/html", "utf-8", "");
        }
    }

    @JavascriptInterface
    public void onClick() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleBar();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.img_back:
                finish();
                break;
            case R.id.img_download:
                downloadPic(url_path);
                break;
            case R.id.img_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                String shareTitle = from.equals("beauty") ? beautyPicItem.getTitle(): animaJoke.getTitle();
                String url = from.equals("beauty") ? beautyPicItem.getPicUrl() : animaJoke.getImg();
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareTitle + " " + url + "(分享自随阅，看你想看的!)");
                shareIntent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
        }

    }

    private void downloadPic(String fileName) {
        photoView.setDrawingCacheEnabled(true);
        if(FileUtil.saveToSDcard(fileName,photoView.getDrawingCache())){
            ToastUtil.Short("保存成功");

        }else {
            ToastUtil.Short("保存失败");
        }
        photoView.setDrawingCacheEnabled(false);

    }

    private void toggleBar() {
        //隐藏
        if (isBarShow) {
            isBarShow = false;

                ObjectAnimator
                        .ofFloat(rl_topbar, "translationY", 0, -rl_topbar.getHeight())
                        .setDuration(ANIMATION_DURATION)
                        .start();
                ObjectAnimator
                        .ofFloat(ll_bottombar, "translationY", 0, ll_bottombar.getHeight())
                        .setDuration(ANIMATION_DURATION)
                        .start();



        } else {
            //显示
            isBarShow = true;

                ObjectAnimator
                        .ofFloat(rl_topbar, "translationY", -rl_topbar.getHeight(), 0)
                        .setDuration(ANIMATION_DURATION)
                        .start();
                ObjectAnimator
                        .ofFloat(ll_bottombar, "translationY", ll_bottombar.getHeight(), 0)
                        .setDuration(ANIMATION_DURATION)
                        .start();



        }
    }
}
