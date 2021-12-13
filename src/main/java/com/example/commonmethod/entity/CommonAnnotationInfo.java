package com.example.commonmethod.entity;

import lombok.Data;

import java.util.List;

/**
 * @author : yj zhang
 * @since : 2021/12/9 17:12
 */
@Data
public class CommonAnnotationInfo {
    /**
     * 类名称
     */
    private String className;

    /**
     * 方法功能
     */
    private String methodDesc;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法返回类型
     */
    private Class<?> returnType;

    /**
     * 方法参数
     */
    private List<MethodParam> args;

}
