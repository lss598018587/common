package com.pf.commons.converter;

import com.alibaba.fastjson.JSON;
import com.pf.commons.api.model.JSONPObject;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 用法
 * <bean id="fastJsonHttpMessageConverter" class="com.tongbanjie.claims.utils.FastJsonHttpMessageConverter">
 *       <property name="supportedMediaTypes">
 *          <list>
 *               <value>application/json;charset=UTF-8</value>
 *               <value>text/html;charset=UTF-8</value>
 *           </list>
 *       </property>
 *       <property name="features">
 *          <list>
 *               <value>WriteNullBooleanAsFalse</value>
 *               <value>QuoteFieldNames</value>
 *               <value>WriteDateUseDateFormat</value>
 *               <value>WriteNullStringAsEmpty</value>
 *           </list>
 *       </property>
 * </bean>
 *
 *   <mvc:annotation-driven
 *       content-negotiation-manager="contentNegotiationManager">
 *       <mvc:message-converters register-defaults="true">
 *           <ref bean="fastJsonHttpMessageConverter" />
 *       </mvc:message-converters>
 *   </mvc:annotation-driven>
 *
 * 支持JSONP的Fastjson的消息转换器
 *
 * @author ding.yudong
 * @author sanfeng 修复高版本fastjson转化string的bug
 */
public class FastJsonHttpMessageConverter extends com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter {

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        if (obj instanceof JSONPObject) {
            JSONPObject jsonp = (JSONPObject) obj;
            OutputStream out = outputMessage.getBody();
            String text = jsonp.getFunction() + "(" + JSON.toJSONString(jsonp.getJson(), getFeatures()) + ")";
            byte[] bytes = text.getBytes(getCharset());
            out.write(bytes);

        } else if (obj instanceof String) {
            String text = (String)obj;
            byte[] bytes = text.getBytes(getCharset());
            OutputStream out = outputMessage.getBody();
            out.write(bytes);

        } else {
            super.writeInternal(obj, outputMessage);
        }
    }
}
