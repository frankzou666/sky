package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    OrdersMapper ordersMapper;


    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {

        //循环计划日期中间的天数
        List<LocalDate> localDateList = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();
        localDateList.add(begin);
        doubleList.add(0D);
        while (!begin.equals(end)){
            begin = begin.plusDays(1L);
            localDateList.add(begin);

            //从数据库中查
//            Map map = new HashMap();
//            map.put("startDate",begin);
//            map.put("status", Orders.COMPLETED);
            doubleList.add(ordersMapper.sumByDateAndStatus(begin,Orders.COMPLETED));





        }


//        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
//        turnoverReportVO.setDateList(StringUtils.join(localDateList,","));
//        turnoverReportVO.setTurnoverList(StringUtils.join(doubleList,","));

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(localDateList,","))
                .turnoverList(StringUtils.join(doubleList,","))
                .build();
    }
}
