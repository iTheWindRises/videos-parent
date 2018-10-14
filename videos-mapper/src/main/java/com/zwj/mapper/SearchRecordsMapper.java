package com.zwj.mapper;


import com.zwj.pojo.SearchRecords;
import com.zwj.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    List<String> getHotword();
}