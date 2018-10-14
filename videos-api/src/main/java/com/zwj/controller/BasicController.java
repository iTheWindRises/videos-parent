package com.zwj.controller;

import com.zwj.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {
    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    //文件保存的命名空间
    public static final String FILE_SPACE = "/Users/thewindrises/server/qnd_videos";
    //ffmpeg所在目录
    public static final String FFMPEG_EXE ="/Users/thewindrises/utils/tools/ffmpeg-2018/bin/./ffmpeg";
    //每页分页的视频条数
    public static final Integer PAGE_SIZE = 5;
}
