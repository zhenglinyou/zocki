package com.zocki.db.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kaisheng3 on 2017/8/22.
 * 列的属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    // 列名
    String columnName() default "";

    // 是否唯一
    boolean unique() default false;

    // 是否不为空
    boolean notNull() default false;

    String defaultValue() default "";
}
