package com.zwj.controller;

import com.zwj.enums.VideoStatusEnum;
import com.zwj.pojo.Users;
import com.zwj.pojo.Videos;
import com.zwj.service.BgmService;
import com.zwj.service.VideoService;
import com.zwj.utils.FetchVideoCover;
import com.zwj.utils.JSONResult;
import com.zwj.utils.PagedResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@RestController
@Api(value = "短视频接口")
@RequestMapping("/video")
public class VideoController extends BasicController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "用户上传视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId" ,value = "用户id",required = true,
                    dataType = "String" ,paramType = "from"),
            @ApiImplicitParam(name = "bgmId" ,value = "背景音乐id",required = false,
                    dataType = "String" ,paramType = "from"),
            @ApiImplicitParam(name = "videoSeconds" ,value = "背景音乐播放长度",required = false,
                    dataType = "Double" ,paramType = "from"),
            @ApiImplicitParam(name = "videoWidth" ,value = "视频宽度",required = true,
                    dataType = "Integer" ,paramType = "from"),
            @ApiImplicitParam(name = "videoHeight" ,value = "视频高度",required = true,
                    dataType = "Integer" ,paramType = "from"),
            @ApiImplicitParam(name = "desc" ,value = "视频描述",required = false,
                    dataType = "String" ,paramType = "from")
    })

    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public JSONResult upload(@ApiParam String userId,@ApiParam  String bgmId,@ApiParam  Double videoSeconds,
                             @ApiParam Integer videoWidth, @ApiParam  Integer videoHeight,@ApiParam  String desc,
                             @ApiParam(value = "短视频",required = true) MultipartFile file) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户名id为空");
        }

        //保存到数据库的相对路径
        String uploadPathDB = "/" + userId +"/video";
        String coverPathDB = "/" + userId +"/video";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String finalPath="";
        try {
            if (file !=  null ) {


                String filename = file.getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径
                    finalPath = FILE_SPACE + uploadPathDB + "/" + filename;
                    //设置数据库保存路径
                    uploadPathDB += ("/" + filename);
                    coverPathDB +=  ("/" + filename+".jpg");

                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } else {
                    return JSONResult.errorMsg("上传出错");
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错");
        }finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        //对视频进行截图
        FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
        videoInfo.convertor(finalPath,FILE_SPACE+coverPathDB);

        //保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds(videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        String videoId = videoService.saveVideo(video);

        return JSONResult.ok(videoId);
    }

    @ApiOperation(value = "用户上传视频封面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="form")
})

    @PostMapping(value = "/uploadCover",headers = "content-type=multipart/form-data")
    public JSONResult uploadCover(@ApiParam String userId,@ApiParam String videoId,
                             @ApiParam(value = "短视频封面",required = true) MultipartFile file) throws Exception {
        if (StringUtils.isBlank(videoId)||StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("视频主键id或用户id为空");
        }
        //保存到数据库的相对路径
        String uploadPathDB = "/" + userId +"/video";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (file !=  null ) {


                String filename = file.getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径
                    String finalPath = FILE_SPACE + uploadPathDB + "/" + filename;
                    //设置数据库保存路径
                    uploadPathDB += ("/" + filename);

                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } else {
                    return JSONResult.errorMsg("上传出错");
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错");
        }finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        //修改视频信息到数据库
        videoService.updateVideo(videoId,uploadPathDB);

        return JSONResult.ok();
    }


    @ApiOperation(value = "用户上传视频封面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="form")
    })

    @PostMapping(value = "/showAll")
    public JSONResult showAll(Integer page) throws Exception {
        if (page == null) {
            page=1;
        }

        PagedResult result = videoService.getAllVideos(page, PAGE_SIZE);

        return JSONResult.ok(result);
    }
}
