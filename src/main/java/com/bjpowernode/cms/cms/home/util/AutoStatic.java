package com.bjpowernode.cms.cms.home.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
// 希望在需要做首页静态化的方法做上这个标记
public @interface AutoStatic {

}
