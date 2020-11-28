package com.pengheng.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author pengheng
 */
@Component("result")
public class Result implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    //  第一步
    @Override
    public void setBeanName(String name) {
        System.out.println("注册我为bean时候定义的名称为：" + name);
    }

    //  第二步
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("管理我的BeanFactory对象为：" + beanFactory);
    }

    //  第三步
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("高级容器接口：" + applicationContext);
    }

    public void initMethod(){
        System.out.println("bean init-method");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("进入PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("进入 afterPropertiesSet");
    }

    @PreDestroy
    public void preDestory(){
        System.out.println("PreDestory 方法");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("调用 destory方法");
    }

    public void destoryMethod(){
        System.out.println("destory-method方法");
    }
}
