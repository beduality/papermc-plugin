package io.github.beduality.core.utils;

public class ClassUtils {
    
    public static <T> String getCleanName(T o, String suffix) {
        var className = o.getClass().getSimpleName();
        return StringUtils.removeSuffix(className.isEmpty() ? "Anonymous" : className, suffix);
    }
}
