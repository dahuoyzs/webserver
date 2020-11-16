package cn.bigfire.webserver.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @ IDE    ：IntelliJ IDEA.
 * @ Date   ：2019/12/26  15:26
 * @ Desc   ：
 */
public class StreamUtil {
    /**
     * 根据InputStream获取Str
     *
     * @param  is                   InputStream
     * @return String               返回管道中的字符串
     * */
    public static String getInputStr(InputStream is){
        try{
            int count = 0;
            while (count == 0) {
                count = is.available(); //这是一个BUG  要么Sleep.要么死循环。详细  https://www.cnblogs.com/CandiceW/p/5486112.html
            }
            byte[] buf = new byte[count];
            int len = is.read(buf);
            return new String(buf, 0, len);
        }catch (Exception e){e.printStackTrace();}
        return "";
    }
    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }



}
