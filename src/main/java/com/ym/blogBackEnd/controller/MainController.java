package com.ym.blogBackEnd.controller;


import com.ym.blogBackEnd.common.response.BaseResponse;
import com.ym.blogBackEnd.exception.ErrorCode;
import com.ym.blogBackEnd.manager.picture.PictureManager;
import com.ym.blogBackEnd.utils.ResultUtils;
import com.ym.blogBackEnd.utils.ThrowUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class MainController {

    @Resource
    private PictureManager pictureManager;

    @PostMapping("/file")
    public BaseResponse<String> fileTest(@RequestParam(value = "file",required = false) MultipartFile multipartFile) {

            ThrowUtils.throwIf(multipartFile == null,
                    ErrorCode.ERROR_PARAMS,"文件不能为空");



        String dir = "D:/test/";
        String originalFilename = multipartFile.getOriginalFilename();

        File temporaryFile = null;
        try {
            temporaryFile = File.createTempFile("test/image/" + originalFilename,null);
            multipartFile.transferTo(temporaryFile);
            String result = pictureManager.uploadPicture(
                    temporaryFile,
                    dir,
                    originalFilename

            );
            return ResultUtils.success(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            temporaryFile.delete();
        }

    }

}
