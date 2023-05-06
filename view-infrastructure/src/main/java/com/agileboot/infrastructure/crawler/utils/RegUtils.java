package com.agileboot.infrastructure.crawler.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtils {

    public final static Pattern LINK = Pattern.compile("magnet:\\?xt=urn:btih:(\\w+)");

    public final static Pattern TAG = Pattern.compile("^(?:第\\d+?頁 - )?(.+?) - ");

    public final  static Pattern GID = Pattern.compile("var gid = (\\d+);");

    public final  static Pattern UC = Pattern.compile("var uc = (\\d+);");


    public static String regTag(String text){
        Matcher matcher = TAG.matcher(text);
        String tag = "";
        // 判断是否可以找到匹配正则表达式的字符
        if (matcher.find()) {
            // 将匹配当前正则表达式的字符串即文件名称进行赋值
            tag = matcher.group();
        }
        return tag;
    }

    public static String regLink(String text){
        Matcher matcher = LINK.matcher(text);
        String tag = "";
        // 判断是否可以找到匹配正则表达式的字符
        if (matcher.find()) {
            // 将匹配当前正则表达式的字符串即文件名称进行赋值
            tag = matcher.group(1);
        }
        return tag;
    }

    public static String gid(String text){
        Matcher matcher = GID.matcher(text);
        String tag = "";
        // 判断是否可以找到匹配正则表达式的字符
        if (matcher.find()) {
            // 将匹配当前正则表达式的字符串即文件名称进行赋值
            tag = matcher.group(1);
        }
        return tag;
    }

    public static String uc(String text){
        Matcher matcher = UC.matcher(text);
        String tag = "";
        // 判断是否可以找到匹配正则表达式的字符
        if (matcher.find()) {
            // 将匹配当前正则表达式的字符串即文件名称进行赋值
            tag = matcher.group(1);
        }
        return tag;
    }

}
