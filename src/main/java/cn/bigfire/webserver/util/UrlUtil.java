package cn.bigfire.webserver.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;

/**
 * @ IDE    ：IntelliJ IDEA.
 * @ Date   ：2019/12/23  11:33
 * @ Desc   ：
 */
public class UrlUtil {
    public static final String customSuffix = "中文";

    /**
     * 根据url获取ip
     *
     * @param   url                输入一个path或者url
     * @return String              返回一个真实的IP地址
     * */
    public static String getIp(String url){
        url = url.replace("http://","").replace("https://","");
        if (url.contains(":")){
            url = url.substring(0,url.indexOf(":"));
        }
        if (url.contains("/")){
            url = url.substring(0,url.indexOf("/"));
        }
        Console.log(url);
        try {
            String ip = InetAddress.getByName(url).getHostAddress();
            Console.log(ip);
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 根据url获取host
     *
     * @param   url                输入一个path或者url
     * @return String              返回一个host
     * */
    public static String getHost(String url){
        url = url.replace("http://","").replace("https://","");
        if (url.contains(":")){
            return url.substring(0,url.indexOf(":"));
        }
        if (url.contains("/")){
            return url.substring(0,url.indexOf("/"));
        }
        return url;
    }
    /**
     * 根据url获取端口号
     *
     * @param   url                输入一个path或者url
     * @return String              返回一个端口号
     * */
    public static Integer getPort(String url){
        url = url.replace("http://","").replace("https://","");
        if (url.contains("/")){//如果路径有/,说明url是有path段的，所以去掉path段，只要ip和端口段
            url = url.substring(0,url.indexOf("/"));
            Console.log(url);
        }
        if (url.contains(":")){//判断是否有端口，如果有就取出来转换成Integer返回 没有就返回80
            int index = url.indexOf(":");
            String port = url.substring(index+1);
            return Integer.valueOf(port);
        }
        return 80;
    }
    /**
     * 根据url获取端口号后面的path路径
     *
     * @param   url                输入一个path或者url
     * @return String              返回一个端口号后面的path路径
     * */
    public static String getPathFromUrl(String url){
        url = url.replace("http://","").replace("https://","");
        if (url.contains("/")){
            return url.substring(url.indexOf("/"));
        }
        return "";
    }
    /**
     * 从HTTP请求头中获取path
     *
     * @param   inputStr           输入一个http请求头
     * @return String              返回一个path路径
     * */
    public static String getPathInputStr(String inputStr){
        String line =  inputStr.split("\r\n")[0];
        if (line.contains("/")&&line.toLowerCase().contains("http")){
            int start = line.indexOf("/");
            int end = line.indexOf("HTTP");
            String path = line.substring(start, end-1);
            return path;
        }else return "";
    }
    /**
     * 获取文件类型
     *
     * @param   path               输入一个path或者url
     * @return String              根据文件后缀返回类型信息
     */
    public static String getContentType(String path){
        try {
            path = URLDecoder.decode(path,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (path.endsWith(".html")){
            return "text/html;charset=UTF-8";
        }else if (path.endsWith(".png")||path.endsWith(".jpg")||path.endsWith("jpeg")||path.endsWith(".ico")){
            return "image/"+path.substring(path.lastIndexOf(".")+1);
        }else if (path.endsWith(".js")){
            return "application/x-javascript";
        }else if (path.endsWith(".css")){
            return "text/css";
        }else if (path.endsWith(".mp3")){
            return "audio/mpeg";
        }else if (path.endsWith(".mp4")){
            return "video/mp4";
        }else if (path.endsWith("."+customSuffix)){
            return customSuffix;
        }else return "text/html;charset=UTF-8";
    }

}
