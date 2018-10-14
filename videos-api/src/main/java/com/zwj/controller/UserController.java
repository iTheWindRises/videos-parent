package com.zwj.controller;

import com.zwj.pojo.Users;
import com.zwj.pojo.vo.UsersVO;
import com.zwj.service.UserService;
import com.zwj.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@Api(value = "用户相关业务接口")
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户上传头像")
    @ApiImplicitParam(name = "userId" ,value = "用户id",required = true,
            dataType = "String" ,paramType = "query")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户名id为空");
        }

        //保存到数据库的相对路径
        String uploadPathDB = "/" + userId +"/face";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files !=  null && files.length >0) {


                String filename = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径
                    String finalFacePath = FILE_SPACE + uploadPathDB + "/" + filename;
                    //设置数据库保存路径
                    uploadPathDB += ("/" + filename);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
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


        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);

        return JSONResult.ok(uploadPathDB);
    }

    @ApiOperation(value = "用户查询信息")
    @ApiImplicitParam(name = "userId" ,value = "用户id",required = true,
            dataType = "String" ,paramType = "query")
    @PostMapping("/query")
    public JSONResult query(String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户名id为空");
        }

        Users userInfo = userService.queryUserInfo(userId);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userInfo,usersVO);

        return JSONResult.ok(usersVO);
    }
}
