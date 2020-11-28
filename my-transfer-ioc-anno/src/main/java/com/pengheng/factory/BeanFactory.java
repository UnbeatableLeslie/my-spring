package com.pengheng.factory;

import com.pengheng.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工厂类，生产对象（使用反射技术类）
 */
public class BeanFactory {

    public static void main(String[] args) {
        System.out.println();
    }

    /**
     * 获取所有文件列表 * @param rootFile * @param fileList * @throws IOException
     */
    public static List<String> listFiles(File rootFile, List<String> fileList) throws IOException {
        File[] allFiles = rootFile.listFiles();
        for (File file : allFiles) {
            if (file.isDirectory()) {
                listFiles(file, fileList);
            } else {
                String path = file.getCanonicalPath();
                String clazz = path.substring(path.indexOf("classes") + 8);
                if (!clazz.contains("TransferServlet"))
                    fileList.add(clazz.replace("/", ".").substring(0, clazz.lastIndexOf(".")));
            }
        }
        return fileList;
    }


    /**
     * 任务一：读取解析XML，通过反射技术实例化对象并存储到map集合中待用
     * 任务二：对外提供通过id获取对象接口
     */
    private static Map<String, Object> map = new HashMap<>();

    static {
        //1.加载 com.pengheng 下面的所有Class类
        System.out.println();
        String rootPath = BeanFactory.class.getResource("../../../").getPath();
        File rootFile = new File(rootPath);
        try {
            //2.扫描类上面是否有@Service @Repository @Component标签 存在的话创建bean对象并放入到map容器中
            List<String> files = listFiles(rootFile, new ArrayList<>());
            for (String clazzStr : files) {
                Class<?> clazz = Class.forName(clazzStr);
                String id = getClazzId(clazz);
                if (id != null && id != "") {
                    Object obj = clazz.newInstance();
                    map.put(id, obj);
                }
            }
            //3.循环上述类中的属性，如果属性上面标注了@Autowired设置属性
            for (String clazzStr : files) {
                Class<?> clazz = Class.forName(clazzStr);
                String id = getClazzId(clazz);
                Object classObject = map.get(id);
                for (Field field : clazz.getDeclaredFields()) {
                    Autowired annotation = field.getAnnotation(Autowired.class);
                    if (annotation != null) {
                        String value = annotation.value();
                        Object o = map.get(value);
                        field.setAccessible(true);
                        field.set(classObject, o);
                    }
                }
            }

            System.out.println(map.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取map中的bean对象，如果对象的方法或者类上标注了@Transactional注解 返回代理对象
    public static Object getBean(String id) {
        Object o = map.get(id);
        Boolean needProxy = false;
        ProxyFactory proxyFactory = (ProxyFactory) map.get("proxyFactory");
        Transactional classTransaction = o.getClass().getAnnotation(Transactional.class);
        if (classTransaction != null) {
            needProxy = true;
        } else{
            for (Method method : o.getClass().getMethods()) {
                Transactional methodTransaction = method.getAnnotation(Transactional.class);
                if (methodTransaction != null) {
                    needProxy = true;
                    break;
                }
            }
        }
        return needProxy ? proxyFactory.getJDKProxy(o) : o;
    }

    public static String getClazzId(Class<?> clazz) {
        Repository repository = clazz.getAnnotation(Repository.class);
        Component component = clazz.getAnnotation(Component.class);
        Service service = clazz.getAnnotation(Service.class);
        if (repository != null || component != null || service != null) {
            return repository == null ? component == null ? service == null ? clazz.getName() : service.value() : component.value() : repository.value();
        }
        return null;
    }
}
