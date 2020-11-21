package com.pengheng.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
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
        InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(in);
            List<Element> beanList = document.selectNodes("//bean");
            for (Element element : beanList) {
                String id = element.attributeValue("id");
                String classPath = element.attributeValue("class");
                Class<?> clazz = Class.forName(classPath);
                Object obj = clazz.newInstance();
                map.put(id,obj);
            }
        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String id){
        return map.get(id);
    }
}
