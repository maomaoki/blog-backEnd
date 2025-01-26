package com.ym.blogBackEnd.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 默认 请求 包装类
 * @author YunMao
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
