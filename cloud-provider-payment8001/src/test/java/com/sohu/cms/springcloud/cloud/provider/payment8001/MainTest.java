/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.provider.payment8001;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.MainTest
 * @description
 * @date 2021/1/14 3:51 下午
 */
@Slf4j
public class MainTest {

    public static final String PLAN_PAYMENT_FILE = "/Users/leo/Desktop/fangan.xlsx";

    public static final String PLAN_LEAGUE_FILE = "/Users/leo/Desktop/league.xlsx";

    public static final String OUT_FILE = "/Users/leo/Desktop/league1.csv";

    public static void main(String[] args) {
        processInfo();
    }

    public static void processInfo() {

        List<PlanPayment> paymentFromExcel = getPaymentFromExcel(PLAN_PAYMENT_FILE);
        List<PlanLeague> planLeagueFromExcel = getPlanLeagueFromExcel(PLAN_LEAGUE_FILE);
        Map<Long, PlanPayment> paymentMap = paymentFromExcel.stream()
                .collect(Collectors.toMap(PlanPayment::getSpuid, Function.identity()));
        Map<String, List<PlanLeague>> collect = planLeagueFromExcel.stream()
                .collect(Collectors.groupingBy(PlanLeague::getLeagueName));
        List<PlanLeague> planLeagues = new ArrayList<>();
        collect.forEach((k,v)-> {

            long orderCnt = v.stream().map(planLeague -> paymentMap.get(planLeague.getSpuid()))
                    .mapToLong(PlanPayment::getOrdercnt).sum();
            long oriSum = v.stream().map(planLeague -> paymentMap.get(planLeague.getSpuid()))
                    .mapToLong(PlanPayment::getOriPrice).sum();
            long actSum = v.stream().map(planLeague -> paymentMap.get(planLeague.getSpuid()))
                    .mapToLong(PlanPayment::getActPrice).sum();
            long matchCount = v.stream().map(PlanLeague::getMatchId)
                    .distinct().count();
            PlanLeague build = PlanLeague.builder()
                    .leagueId(v.get(0).getLeagueId())
                    .leagueName(k)
                    .actPayment(actSum)
                    .oriPayment(oriSum)
                    .orderCnt(orderCnt)
                    .matchCount(matchCount)
                    .build();
            planLeagues.add(build);
        });

        String outStr = "leagueName,leagueId,orderCnt,oriPayment,actPayment,matchCnt\r\n";
        File file = FileUtil.appendUtf8String(outStr, OUT_FILE);
        for (PlanLeague planLeague : planLeagues) {
            String line = String.format("%s,%s,%s,%s,%s,%s\r\n", planLeague.getLeagueName(),
                    planLeague.getLeagueId(), planLeague.getOrderCnt(), planLeague.getOriPayment()
                    , planLeague.getActPayment(),planLeague.getMatchCount());
            FileUtil.appendUtf8String(line, file);
        }
    }

    private static List<PlanLeague> getPlanLeagueFromExcel(String fileName) {
        BufferedInputStream inputStream = FileUtil.getInputStream(fileName);
        // 1.获取上传文件输入流

// 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        ExcelReader excelReader = ExcelUtil.getReader(inputStream, "Sheet1");
// 可以加上表头验证
// 3.读取第二行到最后一行数据
        List<List<Object>> read = excelReader.read(2, excelReader.getRowCount());
        List<PlanLeague> planLeagues = new ArrayList<>();
        int countIndex = 2;
        for (List<Object> objects : read) {
// objects.get(0),读取某行第一列数据
// objects.get(1),读取某行第二列数据

            PlanLeague planLeague = PlanLeague.builder()
                    .spuid(getNum(objects.get(0)))
                    .planId(getNum(objects.get(1)))
                    .matchId(getNum(objects.get(2)))
                    .leagueId(getNum(objects.get(3)))
                    .leagueName((String) objects.get(4))
                    .build();
            planLeagues.add(planLeague);
            log.info("the count: {}", countIndex);
            countIndex++;
        }
        log.info("{}", planLeagues);
        return planLeagues;
    }

    private static List<PlanPayment> getPaymentFromExcel(String fileName) {
        BufferedInputStream inputStream = FileUtil.getInputStream(fileName);
        // 1.获取上传文件输入流

// 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        ExcelReader excelReader = ExcelUtil.getReader(inputStream, "Sheet1");
// 可以加上表头验证
// 3.读取第二行到最后一行数据
        List<List<Object>> read = excelReader.read(2, excelReader.getRowCount());
        List<PlanPayment> planPayments = new ArrayList<>();
        int countIndex = 2;
        for (List<Object> objects : read) {
// objects.get(0),读取某行第一列数据
// objects.get(1),读取某行第二列数据

            PlanPayment payment = PlanPayment.builder()
                    .spuid(getNum(objects.get(0)))
                    .ordercnt(getNum(objects.get(1)))
                    .oriPrice(getNum(objects.get(2)))
                    .actPrice(getNum(objects.get(3)))
                    .build();
            planPayments.add(payment);
            log.info("the count: {}", countIndex);
            countIndex++;
        }
        log.info("{}", planPayments);
        return planPayments;
    }

    private static Long getNum(Object num) {
        if (num instanceof String) {
            return Long.parseLong((String)num);
        } else if (num instanceof Integer) {
            return ((Integer)num).longValue();
        } else if (num instanceof Long) {
            return (Long)num;
        }
        log.warn("non match type ,the num is {}", num);
        return null;
    }
}
