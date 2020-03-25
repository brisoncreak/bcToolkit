package com.bcToolkit.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPconverter {
    /**
     * 将一个IP地址保存为一个int整数
     * @return
     */
    public static int IP2Int(String ip){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(ip);

        int res = 0;

        for(int i=0;i<4;i++){
            if(matcher.find())
            res = Integer.parseInt(matcher.group()) << (24-8*i) | res;
        }
        return res;
    }
    public static String IP2BinStr(String ip){
        String res = Integer.toBinaryString(IP2Int("192.168.0.1"));
        StringBuffer buffer = new StringBuffer(res);

        buffer.insert(8, ' ');
        buffer.insert(17, ' ');
        buffer.insert(26, ' ');
        return buffer.toString();
    }
}
