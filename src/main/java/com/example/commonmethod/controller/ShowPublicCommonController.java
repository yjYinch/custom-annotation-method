package com.example.commonmethod.controller;

import com.example.commonmethod.entity.CommonAnnotationInfo;
import com.example.commonmethod.utils.CommonAnnotationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : yj zhang
 * @since : 2021/12/9 17:24
 */
@Slf4j
@RestController
@RequestMapping("/show")
public class ShowPublicCommonController {

    @Autowired
    private CommonAnnotationUtils commonAnnotationUtils;

    @GetMapping("/common/methods")
    public Map<String, List<CommonAnnotationInfo>> showPublicCommonMethods() {

        try {
            return commonAnnotationUtils.getAllCommonMethods();
        } catch (Exception e) {
            log.info("",e);
            return null;
        }
    }
}
