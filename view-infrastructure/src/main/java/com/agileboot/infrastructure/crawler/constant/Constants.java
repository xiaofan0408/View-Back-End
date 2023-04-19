package com.agileboot.infrastructure.crawler.constant;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static String JAVBUS  = "https://www.seejav.zone";

    public static String MovieType_N = "normal";

    public static String MovieType_u = "uncensored";

    public static String MagnetType_a = "all";

    public static String MagnetType_e = "exist";

    public static String USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36";

    public static Map<String,String> starInfoMap = new HashMap<>();

    static {
        starInfoMap.put("birthday","生日: ");
        starInfoMap.put("age","年齡: ");
        starInfoMap.put("height","身高: ");
        starInfoMap.put("bust","胸圍: ");
        starInfoMap.put("waistline","腰圍: ");
        starInfoMap.put("hipline","臀圍: ");
        starInfoMap.put("birthplace","出生地: ");
        starInfoMap.put("hobby","愛好: ");
        starInfoMap.put("cupsize","罩杯: ");
    }

}
