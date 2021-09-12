package com.lmxdawn.common.converter;

import com.sun.deploy.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Long 类型的 List 转为字符串
 */
public class LongList2StringConverter {

    /**
     * Long 类型的 List 转为字符串
     * @param longList
     * @param regex
     * @return
     */
    public static String convert(List<Long> longList, String regex) {

        if (longList.isEmpty()) {
            return null;
        }
        Set<String> stringSet = new HashSet<>();
        for (Long value: longList){
            stringSet.add(value.toString());
        }
        if (stringSet.isEmpty()) {
            return null;
        }
        return StringUtils.join(stringSet, regex);
    }
}
