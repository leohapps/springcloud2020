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
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.PlanLeague
 * @description
 * @date 2021/1/14 4:08 下午
 */
@Data
@Builder
public class PlanLeague {

    private Long spuid;
    private Long planId;
    private Long matchId;
    private Long leagueId;
    private String leagueName;

    private Long orderCnt;
    private Long oriPayment;
    private Long actPayment;
}
