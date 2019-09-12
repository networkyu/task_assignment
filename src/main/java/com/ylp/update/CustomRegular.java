package com.ylp.update;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通过正则表达式匹配字符串，将匹配到的字符串返回。
 */
public class CustomRegular {
    // 注意该方法会过滤掉重复字符串，截取字符串。
    public static Set<String> interceptStringsByString(String originalStr,String regularStr){
        Set<String> strings = new HashSet<String>();
        Pattern pattern = Pattern.compile(regularStr);
        Matcher m = pattern.matcher(originalStr);
        while (m.find()){
            int i = 0;
            strings.add(m.group(i));
            i++;
        }
        return strings;
    }
}
