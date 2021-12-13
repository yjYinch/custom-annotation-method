package com.example.commonmethod.utils;

import com.example.commonmethod.annotation.CommonAnnotation;
import com.example.commonmethod.entity.CommonAnnotationInfo;
import com.example.commonmethod.entity.MethodParam;
import com.example.commonmethod.exception.CommonAnnotationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: zhangyijun
 * @date: created in 下午8:31 9/12/2021
 * @description
 */
@Slf4j
@Component
public class CommonAnnotationUtils {

    @Autowired
    private ApplicationContext ac;


    /**
     * 获取所有公共方法
     *
     * @return
     * @throws CommonAnnotationException
     */
    public Map<String, List<CommonAnnotationInfo>> getAllCommonMethods() throws CommonAnnotationException {
        // 1. 获取spring容器所有beanNames
        // get all beanNames from spring container
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        if (beanDefinitionNames.length == 0) {
            throw new CommonAnnotationException("beanDefinitionNames is null");
        }

        final List<Class<?>> beanClazz = new ArrayList<>(128);
        // 2. 根据beanNames从Spring容器获取对应的class
        // get class from Spring container according to beanNames
        Stream.of(beanDefinitionNames).forEach(beanName -> beanClazz.add(ac.getBean(beanName).getClass()));

        // 3. 遍历beanClazz，筛选方法中包含CommonAnnotation注解的class
        // get class which methods contain CommonAnnotation
        List<Class<?>> commonAnnotations = beanClazz.stream()
                .filter(beanClazz1 -> ((Stream.of(beanClazz1.getDeclaredMethods())
                        .anyMatch(method -> method.isAnnotationPresent(CommonAnnotation.class)))))
                .collect(Collectors.toList());

        final List<CommonAnnotationInfo> commonAnnotationInfos = new ArrayList<>();
        for (Class<?> aClass : commonAnnotations) {
            // 获取类中的所有方法
            Method[] declaredMethods = aClass.getDeclaredMethods();
            String className = aClass.getName();
            for (Method declaredMethod : declaredMethods) {
                Annotation[] annotations = declaredMethod.getAnnotations();
                if (annotations == null) {
                    continue;
                }
                // 判断方法上是否有@CommonAnnotation注解
                if (!declaredMethod.isAnnotationPresent(CommonAnnotation.class)) {
                    continue;
                }
                CommonAnnotationInfo commonAnnotationInfo = new CommonAnnotationInfo();
                // 获取@CommonAnnotation注解
                CommonAnnotation targetAnnotation = declaredMethod.getAnnotation(CommonAnnotation.class);

                // 实体类赋值
                commonAnnotationInfo.setReturnType(declaredMethod.getReturnType());
                commonAnnotationInfo.setMethodName(declaredMethod.getName());
                commonAnnotationInfo.setArgs(packMethodParam(declaredMethod));
                commonAnnotationInfo.setClassName(className);
                commonAnnotationInfo.setMethodDesc(targetAnnotation.methodDesc());
                commonAnnotationInfos.add(commonAnnotationInfo);
            }
        }
        Map<String, List<CommonAnnotationInfo>> finalCommonMethods =
                commonAnnotationInfos.stream().collect(Collectors.groupingBy(CommonAnnotationInfo::getClassName));
        log.info("custom methods：{}", finalCommonMethods);

        return finalCommonMethods;
    }

    /**
     * 封装方法参数
     * @param method
     * @return
     */
    private List<MethodParam> packMethodParam(Method method) {
        final List<MethodParam> methodParams = new ArrayList<>(8);
        Parameter[] parameters = method.getParameters();
        // 参数名称
        for (Parameter parameter : parameters) {
            MethodParam mp = new MethodParam();
            mp.setParamName(parameter.getName());
            methodParams.add(mp);
        }

        Type[] genericParameterTypes = method.getGenericParameterTypes();
        // 参数类型
        for (int i = 0; i < genericParameterTypes.length; i++) {
            log.info(genericParameterTypes[i].getTypeName());
            methodParams.get(i).setParamType(genericParameterTypes[i].getTypeName());
        }
        return methodParams;
    }
}
