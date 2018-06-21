package cn.popo.news.common.utils;



import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class StatisticsInfoGetUtil {
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
        {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
        {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1)
            {
                return ip.substring(0, index);
            }
            else
            {
                return ip;
            }
        }
        else
        {
            return request.getRemoteAddr();
        }
    }

    public static void getSystemAndBrowser(HttpServletRequest request){
        StringBuilder userAgent = new StringBuilder("[");
        userAgent.append(request.getHeader("User-Agent"));
        userAgent.append("]");
        int indexOfMac = userAgent.indexOf("Mac OS X");
        int indexOfWindows = userAgent.indexOf("Windows NT");
        int indexOfIE = userAgent.indexOf("MSIE");
        int indexOfIE11 = userAgent.indexOf("rv:");
        int indexOfFF = userAgent.indexOf("Firefox");
        int indexOfSogou = userAgent.indexOf("MetaSr");
        int indexOfChrome = userAgent.indexOf("Chrome");
        int indexOfSafari = userAgent.indexOf("Safari");
        boolean isMac = indexOfMac > 0;
        boolean isWindows = indexOfWindows > 0;
        boolean isLinux = userAgent.indexOf("Linux") > 0;
        boolean containIE = indexOfIE > 0 || (isWindows && (indexOfIE11 > 0));
        boolean containFF = indexOfFF > 0;
        boolean containSogou = indexOfSogou > 0;
        boolean containChrome = indexOfChrome > 0;
        boolean containSafari = indexOfSafari > 0;
        String browser = "";
        if (containSogou) {
            if (containIE) {
                browser = "搜狗" + userAgent.substring(indexOfIE, indexOfIE + "MSIE x.x".length());
            } else if (containChrome) {
                browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
            }
        } else if (containChrome) {
            browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
        } else if (containSafari) {
            int indexOfSafariVersion = userAgent.indexOf("Version");
            browser = "Safari "
                    + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x".length());
        } else if (containFF) {
            browser = userAgent.substring(indexOfFF, indexOfFF + "Firefox/xx".length());
        } else if (containIE) {
            if (indexOfIE11 > 0) {
                browser = "MSIE 11";
            } else {
                browser = userAgent.substring(indexOfIE, indexOfIE + "MSIE x.x".length());
            }
        }
        String os = "";
        if (isMac) {
            os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxx".length());
        } else if (isLinux) {
            os = "Linux";
        } else if (isWindows) {
            os = "Windows ";
            String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
                    + "Windows NTx.x".length());
            if ("5.0".equals(version.trim())) {
                os += "2000";
            } else if ("5.1".equals(version.trim())) {
                os += "XP";
            } else if ("5.2".equals(version.trim())) {
                os += "2003";
            } else if ("6.0".equals(version.trim())) {
                os += "Vista";
            } else if ("6.1".equals(version.trim())) {
                os += "7";
            } else if ("6.2".equals(version.trim())) {
                os += "8";
            } else if ("6.3".equals(version.trim())) {
                os += "8.1";
            }
        }
        System.out.println("系统：" + os + ",浏览器：" + browser);
    }

    public static void getVisitUitl(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent");
        String visitUitl = null;
        if(userAgent.indexOf("Android") != -1){
            visitUitl = "Android";
            //安卓
        }else if(userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1){
            //苹果
            visitUitl = "iPhone";
        }else{
            //电脑
            visitUitl = "computer";
        }
        System.out.println(visitUitl);
    }

}
