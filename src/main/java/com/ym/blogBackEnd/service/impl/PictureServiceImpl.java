package com.ym.blogBackEnd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.blogBackEnd.model.domain.Picture;
import com.ym.blogBackEnd.service.PictureService;
import com.ym.blogBackEnd.mapper.PictureMapper;
import org.springframework.stereotype.Service;

/**
* @author 54621
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-01-28 22:52:52
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

}




