package com.bupt.read.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bupt.read.Config.PlayState;
import com.bupt.read.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xs on 2016/7/7.
 */
public class MusicPlayService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    public static PlayState playState =  new PlayState();;

    public static final int MODE_IN_ORDER = 0x00;
    public static final int MODE_IN_RANDOM = 0x01;

    public MusicPlayService(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("onCreate()");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.i("onStart()");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("onStartCommand");
        if(intent.getAction().equals("play")){
            String url = intent.getStringExtra("music_url");
            playMusic(url);
        }

        return super.onStartCommand(intent, flags, startId);
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class PlayerBinder extends Binder{
        public MusicPlayService getMusicPlayerService(){
            return MusicPlayService.this;

        }

    }

    //创建单个线程池
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    //开始播放
    public void start(){

        if(mMediaPlayer!=null && !mMediaPlayer.isPlaying()){

            playState.setIsPlaying(true);
            mMediaPlayer.start();
        }


    }

    public void playMusic(String url){
        LogUtil.i("play music");
        try {
            if (mMediaPlayer == null) {
                // 新建一个mediaPalyer对象
                mMediaPlayer = new MediaPlayer();
            }

            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    //准备好后开始更新状态

                    playState.setDuration(mp.getDuration());

                    mp.start();

                }
            });
           // mMediaPlayer.start();



        } catch (IOException e) {
            System.out.println("音乐地址解析错误");
            e.printStackTrace();
        }
    }


    //获取总时长    方便网络音乐进度的显示
    public int getDuration(){

        if(mMediaPlayer!=null){

            return mMediaPlayer.getDuration();
        }else{

            return 0;
        }


    }


    public void seekTo(int postion){

        if(mMediaPlayer!=null){

            mMediaPlayer.seekTo(postion);
        }

    }

    public int getCurrentProgress(){

        if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){

            return mMediaPlayer.getCurrentPosition();

        }else{

            return 0;
        }

    }

    //暂停播放
    public void pause(){

        if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){


            playState.setIsPlaying(false);
            mMediaPlayer.pause();
            System.out.println("暂停时的进度：" + getCurrentProgress());

        }


    }

}
