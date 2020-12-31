/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudzkconsumer.service;

import com.sohu.dubbo.bean.jcDataCenter.vo.match.MatchInfo;
import com.sohu.dubbo.rpc.jcDataCenter.IJcMatchInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudzkconsumer.service.IJcTestService
 * @description
 * @date 2020/12/29 6:40 下午
 */
@Slf4j
@Service
public class IJcTestService {

    @Reference(version = "1.0.0", cluster = "")
    private IJcMatchInterface iJcMatchInterface;

    public MatchInfo testJcService() {
        Long matchId = 495702L;
        MatchInfo matchInfoByMatchId = iJcMatchInterface.getMatchInfoByMatchId(matchId);
        log.info("{}", matchInfoByMatchId);
        return matchInfoByMatchId;
    }


}
