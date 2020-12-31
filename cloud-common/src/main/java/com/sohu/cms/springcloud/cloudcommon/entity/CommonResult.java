/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.entity.CommonResult
 * @description
 * @date 2020/12/31 3:54 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

    private Integer code;

    private String message;

    private String port;

    private T data;


    public CommonResult(Integer code, String message) {
        this(code, message, null, null);
    }
}
