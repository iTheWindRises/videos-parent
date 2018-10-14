package com.zwj.mapper;


import com.zwj.pojo.Videos;
import com.zwj.pojo.vo.VideosVO;
import com.zwj.utils.MyMapper;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {
    public List<VideosVO> queryAllVideos();
}