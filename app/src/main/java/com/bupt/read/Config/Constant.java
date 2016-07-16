package com.bupt.read.Config;

/**
 * Created by xs on 2016/5/28.
 */
public class Constant {
    public static final String BASE_URL = "http://api.huceo.com";



    public static final String KEY = "304c5d55ee3813d0d09a6a32f7793f4d";
    //http://api.huceo.com/meinv/?key=304c5d55ee3813d0d09a6a32f7793f4d&num=2&page=1
    //appSecret:0d7e782620224968b3261e57bb9e2a24
    //appId:20017

    public static final String SHOWAPI_JOKE = "http://route.showapi.com";
    public static final String APP_SECRET = "0d7e782620224968b3261e57bb9e2a24";
    public static final String APP_ID = "20017";

    //http://route.showapi.com/109-34?showapi_appid=20017&showapi_sign=0d7e782620224968b3261e57bb9e2a24

    /**
     *  这是Bmob的ApplicationId,用于初始化操作
     */
    public static String applicationId = "f8185149613d956dd730001fc698bd50";


    public static final String FILE_NAME = "daoge";    //保存在手机中的文件名

    public static final String DB_NAME = "read_db";     //数据库名称


    /**
     * 头像保存目录
     */
    public static String MyAvatarDir = "/sdcard/LifeHelper/avatar/";
    /**
     * 选取本地图片的回调
     */
    public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像

    public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
    public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
}
