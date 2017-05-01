package com.bjpowernode.cms.sys.param.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
// 希望在需要做参数初始化的地方做上这个标记
public @interface InitParam {

}
