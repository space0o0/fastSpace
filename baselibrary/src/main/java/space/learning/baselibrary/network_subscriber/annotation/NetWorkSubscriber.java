package space.learning.baselibrary.network_subscriber.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import space.learning.baselibrary.network_subscriber.enums.NetType;


/**
 * @author space
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NetWorkSubscriber {

    /**
     * 只想在切换为该网络下触发
     * 用做过滤 可不填
     *
     * @return
     */
    NetType netType() default NetType.NONE;
}
