package com.zwj.controller;

import com.zwj.pojo.Users;
import com.zwj.pojo.vo.UsersVO;
import com.zwj.service.BgmService;
import com.zwj.service.UserService;
import com.zwj.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@Api(value = "背景音乐接口")
@RequestMapping("/bgm")
public class BgmController extends BasicController {

    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "获取bgm列表")
    @PostMapping("/list")
    public JSONResult List() throws Exception {
        return JSONResult.ok(bgmService.queryBgmList());
    }
}
