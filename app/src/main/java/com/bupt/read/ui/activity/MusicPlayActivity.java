package com.bupt.read.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bupt.read.R;
import com.bupt.read.base.BaseActivity;
import com.bupt.read.service.MusicPlayService;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.utils.MusicPlayer;
import com.bupt.read.utils.ToastUtil;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xs on 2016/7/6.
 */
public class MusicPlayActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    private String music_url = "";
    private String album_url = "";
    private SeekBar mSeekBar ;
    private MusicPlayer musicPlayer;
    private ImageView iv_like,iv_download,iv_comment;
    private ImageButton ib_play_pre,ib_play,ib_play_next;
    private CircleImageView iv_album;
    private TextView tv_curTime;
    private TextView tv_totalTime;
    private MusicPlayService mplayService;
    private PlayReceiver receiver;
    private boolean isPlaying;
    private ServiceConnection serviceConnection;
    private Intent service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        LogUtil.i("onCreate()");
        service = new Intent(this,MusicPlayService.class);
        service.setAction("com.bupt.read.MUSIC_SERVICE");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mplayService.unbindService(serviceConnection);
    }

    @Override
    public void initViews() {
        mSeekBar = (SeekBar) findViewById(R.id.musics_player_seekbar);
        iv_album = (CircleImageView) findViewById(R.id.album_image);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        iv_comment = (ImageView) findViewById(R.id.iv_comment);
        ib_play = (ImageButton) findViewById(R.id.ib_play);
        ib_play_next = (ImageButton) findViewById(R.id.ib_play_next);
        ib_play_pre = (ImageButton) findViewById(R.id.ib_play_pre);
        tv_curTime = (TextView) findViewById(R.id.tv_current_time);
        tv_curTime = (TextView) findViewById(R.id.tv_total_time);
    }

    private void initPlay() {
        {

            music_url = "http://tsmusic24.tc.qq.com/107298249.mp3";
            album_url = "http://i.gtimg.cn/music/photo/mid_album_300/m/3/003wBBXe1dSLm3.jpg";
            Intent service = new Intent(this,MusicPlayService.class);
            service.putExtra("music_url",music_url);
            service.putExtra("album_url",album_url);
            service.setAction("play");
            startService(service);


        }
    }

    //更换歌曲刷新UI
    private void updateUI() {
        int duration = mSeekBar.getMax();

        if(duration != MusicPlayService.playState.getDuration()){
            //System.out.println("更新总时长");
            mSeekBar.setMax((int) MusicPlayService.playState.getDuration());
            tv_totalTime.setText(new SimpleDateFormat("mm:ss").format((MusicPlayService.playState.getDuration())));

        }

        //刷新控制按钮
        boolean isPlaying = MusicPlayService.playState.isPlaying();
        //如果状态不一致

        if(isPlaying){

            //刷新播放的进度
            long progress = MusicPlayService.playState.getProgress();
            mSeekBar.setProgress((int)progress );
            tv_curTime.setText(new SimpleDateFormat("mm:ss").format(progress));
            //System.out.println("刷新当前进度");
            //lf.changeCurrent(progress);

            //状态不一致需刷新
            if(isPlaying != (boolean)ib_play.getTag()){

                if(isPlaying){

                    ib_play.setImageResource(R.drawable.btn_pause_selector);

                }else{

                    ib_play.setImageResource(R.drawable.btn_play_selector);
                }
                //System.out.println("刷新播放按钮");

            }


        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        ib_play.setOnClickListener(this);
        iv_download.setOnClickListener(this);
        iv_like.setOnClickListener(this);
    }

    class PlayReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.i("PlayActivity接收到广播");
                updateUI();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_playmusic;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ib_play:
                if(MusicPlayService.playState.isPlaying()){
                    mplayService.pause();
                    mplayService.playState.setIsPlaying(false);
                    ib_play.setImageResource(R.drawable.btn_play_selector);
                }else{

                    mplayService.start();
                    mplayService.playState.setIsPlaying(true);
                    ib_play.setImageResource(R.drawable.btn_pause_selector);

                }

                break;
            case R.id.iv_download:
                this.startService(service);
                break;
            case R.id.iv_like:
                iv_like.setImageResource(R.drawable.play_icn_loved);
                iv_like.setTag(true);
                ToastUtil.Short("收藏成功");
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //停止时刷新进度
        //当前的进度变化是
        if(mplayService!=null){
            mplayService.seekTo(seekBar.getProgress());
        }


    }
}
