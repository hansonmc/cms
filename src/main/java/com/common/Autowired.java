package com.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 注解是用注解定义的
// 用来定义注解的注解叫做元注解 一共有四个
// @Target目标注解的作用域
// @Retention目标注解的生命周期
// @Documented目标注解会不会出现在标准文档
// @Inherited目标注解能不能被继承

// Autowired
// A class
// B extend A
//
// B 是不是有Autowired注解

// 注解本身就是标记
public @interface Autowired {

	boolean required() default true;

}
