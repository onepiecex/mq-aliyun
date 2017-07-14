package com.mq.aliyun.core.scan; /**
 * Created by wangziqing on 16/3/3.
 */

import com.mq.aliyun.core.scan.util.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 扫描所有的指定包下面的class
 */
public class ClassScanner {
    private static final Logger LOG = LoggerFactory.getLogger(ClassScanner.class);


    public static Set<Class<?>> scan(String... packageNames) {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        if (packageNames != null && packageNames.length > 0) {
            try {
                for (String one : packageNames) {
                    Set<ClassPath.ClassInfo> classInfos = null;

                    classInfos = ClassPath.from(
                            Thread.currentThread().getContextClassLoader())
                            .getTopLevelClassesRecursive(one);

                    for (ClassPath.ClassInfo classInfo : classInfos) {
                        classes.add(classInfo.load());
                    }
                }
            } catch (IOException e) {
                LOG.error("scan class error . package:{" + Arrays.toString(packageNames) + "}");
            }
        }
        return classes;
    }

    private void generateClasses(Set<Class<?>> classes, String packageName) {
        Set<ClassPath.ClassInfo> classInfos = null;

        try {
            classInfos = ClassPath.from(
                    Thread.currentThread().getContextClassLoader())
                    .getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo classInfo : classInfos) {
                classes.add(classInfo.load());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static <T> Set<Class<T>> scan(Class<T> c) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        try {

            Set<ClassPath.ClassInfo> classInfos;

            classInfos = ClassPath.from(
                    Thread.currentThread().getContextClassLoader())
                    .getTopLevelClasses();

            for (ClassPath.ClassInfo classInfo : classInfos) {
                classes.add(classInfo.load());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<Class<T>> results = new LinkedHashSet<>();
        for (Class cla : classes) {
            if (c == cla.getSuperclass()) {
                results.add(cla);
                continue;
            }
            Class[] cxs = cla.getInterfaces();
            for (Class cx : cxs) {
                if (cx == c) {
                    results.add(cla);
                    break;
                }
            }


        }
        return results;
    }

    public static <T> Set<Class<T>> scan(Class<T> c, String... packageNames) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        if (packageNames != null && packageNames.length > 0) {
            try {
                for (String one : packageNames) {
                    Set<ClassPath.ClassInfo> classInfos;

                    classInfos = ClassPath.from(
                            Thread.currentThread().getContextClassLoader())
                            .getTopLevelClassesRecursive(one);

                    for (ClassPath.ClassInfo classInfo : classInfos) {
                        classes.add(classInfo.load());
                    }
                }
            } catch (IOException e) {
                LOG.error("scan class error . package:{" + Arrays.toString(packageNames) + "}");
            }
        }
        Set<Class<T>> results;
        results = new LinkedHashSet<>();
        for (Class cla : classes) {
            if (c == cla.getSuperclass()) {
                results.add(cla);
                continue;
            }
            Class[] cxs = cla.getInterfaces();
            for (Class cx : cxs) {
                if (cx == c) {
                    results.add(cla);
                    break;
                }
            }


        }
        return results;
    }

}