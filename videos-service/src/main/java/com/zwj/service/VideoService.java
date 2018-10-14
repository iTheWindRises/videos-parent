package com.zwj.service;

import com.zwj.pojo.Videos;
import com.zwj.utils.PagedResult;

public interface VideoService {
    String saveVideo(Videos video);

    void updateVideo(String videoId,String coverPath);

    /**
     * 分页查询视频列表
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult getAllVideos(Integer page,Integer pageSize);
}
