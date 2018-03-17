package com.connxun.elinetv.util;

/**
 * Created by connxun-16 on 2017/12/21.
 */

/**
 *
 *
 */
public class URLFactory {

    //正式环境
    public static final String SEVER_LOCAL = "http://cx.connxun.com/cx-api/";

    //网易上传图片DNS查询
    public static final String IMAGE_DNS = "http://lbs-eastchina1.126.net/";

    //测试环境
//    public static final String SEVER_LOCAL = "http://192.168.31.98:8080/cx-api/";


    /// 网易云Api
    String musicBaseUrl = "http://123.56.14.98:3000";
    /// 榜单
    String musicListUrl = musicBaseUrl + "/top/list?offset=0&limit=30&idx=";

    String musicInfoUrl = musicBaseUrl + "/music/url?id=";
    /// 搜索
    String searchMusicUrl = musicBaseUrl + "/search?offset=0&limit=30&keywords=";







}
