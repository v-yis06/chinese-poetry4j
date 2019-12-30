package com.ruoyi.generator.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */
public class StringUtil {

    public static String str2hump(String str) {
        str = str.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        if (str != null && str.length() > 0) {
            if (str.contains("_")) {
                String[] chars = str.split("_");
                int size = chars.length;
                if (size > 0) {
                    List<String> list = Lists.newArrayList();
                    String[] var8 = chars;
                    int var7 = chars.length;

                    for(int var6 = 0; var6 < var7; ++var6) {
                        String s = var8[var6];
                        if (s != null && s.trim().length() > 0) {
                            list.add(s);
                        }
                    }

                    size = list.size();
                    if (size > 0) {
                        buffer.append((String)list.get(0));

                        for(int i = 1; i < size; ++i) {
                            String s = (String)list.get(i);
                            buffer.append(s.substring(0, 1).toUpperCase());
                            if (s.length() > 1) {
                                buffer.append(s.substring(1));
                            }
                        }
                    }
                }
            } else {
                buffer.append(str);
            }
        }

        return buffer.toString();
    }
}
