package com.pengheng.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工厂类，生产对象（使用反射技术类）
 */
public class BeanFactory {

    /**
     * 任务一：读取解析XML，通过反射技术实例化对象并存储到map集合中待用
     * 任务二：对外提供通过id获取对象接口
     */
    private static Map<String,Object> map = new HashMap<>();

    static{
        InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml");
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(in);

            //解析bean属性解决强耦合问题
            List<Element> beanList = document.selectNodes("//bean");
            for (Element element : beanList) {
                String id = element.attributeValue("id");
                String classPath = element.attributeValue("class");
                Class<?> clazz = Class.forName(classPath);
                Object obj = clazz.newInstance();
                map.put(id,obj);
            }
            //解析property属性维护注入关系
            List<Element> propertyList = document.selectNodes("//property");
            for (Element element : propertyList) {
                //获取方法名
                String name = element.attributeValue("name");
                //获取引用对象ID
                String ref = element.attributeValue("ref");
                //获取父级对象ID
                String id = element.getParent().attributeValue("id");
                Object parentObject = map.get(id);
                //获取父级的setXXX方法，将依赖属性注入
                Method[] methods = parentObject.getClass().getMethods();
                for (Method method : methods) {
                    if (method.getName().equals("set"+name)) {
                        method.invoke(parentObject,map.get(ref));
                    }
                }
                //将属性变更后的对象重新存入到map中
                map.put(id,parentObject);

            }

        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String id){
        return map.get(id);
    }
}
