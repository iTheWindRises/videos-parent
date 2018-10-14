package com.zwj.service;

import com.zwj.pojo.Videos;
import com.zwj.utils.PagedResult;

import java.util.List;

public interface VideoService {
    String saveVideo(Videos video);

    void updateVideo(String videoId,String coverPath);

    /**
     * 分页查询视频列表
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult getAllVideos(Videos video, Integer isSaveRecord ,Integer page,Integer pageSize);

    List<String> getHotwords();
}
