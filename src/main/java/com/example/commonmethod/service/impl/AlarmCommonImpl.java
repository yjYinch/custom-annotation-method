package com.example.commonmethod.service.impl;

import com.example.commonmethod.annotation.CommonAnnotation;
import com.example.commonmethod.service.IAlarmCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : yj zhang
 * @since : 2021/12/9 17:05
 */
@Slf4j
@Service
public class AlarmCommonImpl implements IAlarmCommon {
    @CommonAnnotation(methodDesc = "alarmCommon")
    @Override
    public void alarmCommon(List<String> list, List<Object> list2, String str) {
        log.info("do something");
    }
}
