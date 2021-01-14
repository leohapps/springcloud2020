/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.provider.payment8001;

import lombok.Builder;
import lombok.Data;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.PlanPayment
 * @description
 * @date 2021/1/14 4:07 下午
 */
@Data
@Builder
public class PlanPayment {
    private Long spuid;
    private Long ordercnt;
    private Long oriPrice;
    private Long actPrice;
}
