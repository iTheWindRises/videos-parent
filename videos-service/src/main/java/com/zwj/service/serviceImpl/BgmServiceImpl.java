package com.zwj.service.serviceImpl;

import com.zwj.mapper.BgmMapper;
import com.zwj.pojo.Bgm;
import com.zwj.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }
}
