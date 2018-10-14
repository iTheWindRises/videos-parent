package com.zwj.mapper;


import com.zwj.pojo.Videos;
import com.zwj.pojo.vo.VideosVO;
import com.zwj.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {
    public List<VideosVO> queryAllVideos(@Param(value = "videoDesc")String videoDesc);
}