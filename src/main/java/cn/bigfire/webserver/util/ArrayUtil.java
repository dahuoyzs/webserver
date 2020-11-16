package cn.bigfire.webserver.util;

/**
 * @ IDE    ：IntelliJ IDEA.
 * @ Date   ：2019/12/26  15:24
 * @ Desc   ：
 */
public class ArrayUtil {
    /**
     * 合并字节数组
     *
     * @param bt1               字节数组1
     * @param bt2               字节数组2
     * @return byte[]           返回合并后的字节数组
     */
    public static byte[] byteMerger(byte[] bt1, byte[] bt2){

        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }
}
