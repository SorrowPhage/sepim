package com.sepim.springboot.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: SorrowPhage
 * @date: 2023/9/19
 */
public class CollectionUtil {

    /**
     * 获取List中指定属性重复的对象列表
     * @param dataList
     * @param keyExtractors
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> List<T> repeatList(List<T> dataList, Function<? super T, ?>... keyExtractors) {
        return dataList.stream().collect(Collectors.groupingBy(combinationKey(keyExtractors)))
                .values()
                .stream()
                .filter(list -> list.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    /**
     * 判断List中对象的指定属性是否重复
     * @param keyExtractors
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> Function<T, List<?>> combinationKey(Function<? super T, ?>... keyExtractors) {
        return t -> Arrays.stream(keyExtractors)
                .map(key -> key.apply(t))
                .collect(Collectors.toList());
    }

}
