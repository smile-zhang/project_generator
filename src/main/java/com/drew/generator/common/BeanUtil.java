package com.drew.generator.common;

import com.jfinal.plugin.activerecord.Model;
//import org.apache.commons.beanutils.BeanUtils;


/**
 * @author zhangTao
 * @Date:2019/3/25 18:19
 * 用于将数据库查询结果转换成javaBean
 */

public class BeanUtil extends Model {
//
//    public static <T> T mapParseBean(Map<String, Object> orig, T dest) {
//
//        //处理Map key
//        Map<String, Object> newMap = new HashMap<>();
//        for (Map.Entry<String, Object> entry : orig.entrySet()) {
//            String key = entry.getKey();
//            String newKey = mapKey2Bean(key, true);
//            newMap.put(newKey, entry.getValue());
//
//        }
//        //调用 apache的beanUtil
//        try {
//            BeanUtils.copyProperties(dest, newMap);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return (T) dest;
//    }
//
//    public static <T> List<T> listToBean(List<Map<String, Object>> mapList, T dest) {
//        List<T> list = new ArrayList<>();
//        for (Map<String, Object> map : mapList) {
//            T o = mapParseBean(map, dest);
//            list.add(o);
//        }
//        return list;
//    }
//
//    private static String mapKey2Bean(String oldStr, Boolean beginUp) {
//        StringBuffer sbf = new StringBuffer();
//
//        String[] strArr = oldStr.split("_");
//        boolean flag = true;
//        for (String s : strArr) {
//            char[] ch = s.toCharArray();
//            for (int i = 0; i < ch.length; i++) {
//                if (flag) {
//                    flag = false;
//                    sbf.append(charToLowerCase(ch[i]));
//                    continue;
//                }
//                if (i == 0 && beginUp) {//如果首字母需大写
//                    sbf.append(charToUpperCase(ch[i]));
//                } else {
//                    sbf.append(charToLowerCase(ch[i]));
//                }
//            }
//        }
//        return sbf.toString();
//    }
//
//
//    /**
//     * 转大写
//     **/
//    private static char charToUpperCase(char ch) {
//        if (ch <= 122 && ch >= 97) {
//            ch -= 32;
//        }
//        return ch;
//    }
//
//    /***转小写**/
//    private static char charToLowerCase(char ch) {
//        if (ch <= 90 && ch >= 65) {
//            ch += 32;
//        }
//        return ch;
//    }

}
