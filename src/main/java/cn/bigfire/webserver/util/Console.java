package cn.bigfire.webserver.util;

import java.util.stream.Stream;


/**
 * @ IDE    ：IntelliJ IDEA.
 * @ Date   ：2019/9/27  16:44
 * @ Desc   ：控制台工具类
 */
public class Console {
    /**
     * 输出log信息，并显示行号
     *
     * @param object            打印信息
     */
    public static void log(Object object)
    {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        String where = System.getProperties().getProperty("os.name").toLowerCase().contains("linux") ? ste.getMethodName()+"(" +ste.getFileName()+":"+ ste.getLineNumber()+")" : ste.getClassName()+"."+ste.getMethodName()+"(" +ste.getFileName()+":"+ ste.getLineNumber()+")";
        System.out.print("["+where+"]");
        System.out.println(object);
    }
    /**
     * 输出log信息，并显示行号
     *
     * @param object            打印信息
     */
    public static void debug(Object object)
    {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        String where = ste.getClassName()+"."+ste.getMethodName()+"(" +ste.getFileName()+":"+ ste.getLineNumber()+")";
        System.out.print("["+where+"]");
        System.out.println(object);
    }
    /**
     * 返回当前位置, eg:[cn.bigfire.webserver.server.Server.echo(Server.java:59)]
     */
    public static String where()
    {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        String where = ste.getClassName()+"."+ste.getMethodName()+"(" +ste.getFileName()+":"+ ste.getLineNumber()+")";
        return where;
    }

    public static String strOf(Object ...s){
        StringBuilder sb = new StringBuilder();
        Stream.of(s).forEach(str->sb.append(str));
        return sb.toString();
    }

}
