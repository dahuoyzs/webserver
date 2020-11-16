package cn.bigfire.webserver.browser;

import cn.bigfire.webserver.util.StrUtils;
import cn.bigfire.webserver.util.StreamUtil;
import cn.bigfire.webserver.util.UrlUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Map;
import java.util.List;

import cn.bigfire.webserver.util.Console;
import cn.hutool.core.util.StrUtil;

/**
 * @ IDE    ：IntelliJ IDEA.
 * @ Date   ：2019/12/23  9:16
 * @ Desc   ：极简的浏览器
 */
public class Browser{
    JFrame jFrame;
    JTextField jtfUrl;
    JButton goTo;
    JEditorPane jEditorPane;//java自带的一个不怎么好用的网页浏览面板,可以直接展示网页，但为便于理解http响应头，还是IO读取HTTP响应头后，用JLabel做展示
    JLabel  jLabel;
    public static void main(String[] args) throws Exception {
        new Browser();
    }

    public Browser(){
        jFrame = new JFrame("浏览器1.0");
        jtfUrl = new JTextField("http://www.baidu.com");
        jtfUrl.setColumns(28);
        jtfUrl.addKeyListener(new UrlListener());
        goTo = new JButton("转到");
        goTo.addActionListener(new goToAction());
        JPanel jPanel = new JPanel();
        jPanel.add(jtfUrl);
        jPanel.add(goTo);
        jFrame.add(jPanel, BorderLayout.NORTH);
        jLabel = new JLabel("AAA");
        jFrame.add(jLabel,BorderLayout.CENTER);
        jFrame.setBounds(600, 100, 400, 200);//设置窗口大小
        jFrame.setDefaultCloseOperation(3);//设置窗口点击右上角可以关闭
        jFrame.setVisible(true);            //设置窗口可见性
    }

    class goToAction implements ActionListener        //按钮监听使劲按类
    {
        @Override
        public void actionPerformed(ActionEvent e)        //按钮响应事件
        {
            String url = jtfUrl.getText().trim();
            String res = doRequest(url);
            String resHeader = res.substring(0,res.indexOf("\r\n\r\n"));
            String resBody = res.substring(res.indexOf("\r\n\r\n")+1);
            Console.log(resHeader);
            String html = resBody.trim();
            System.out.println(html);

           jLabel.setText("<html>"+html+"</html>");

        }
    }
    public static String doRequest(String url){
        try{

            String host = UrlUtil.getHost(url);                     //根据url获取真实host
            String ip = UrlUtil.getIp(url);                         //根据url获取真实Ip地址
            Integer port = UrlUtil.getPort(url);                    //根据url获取port,如果没有端口则为80
            String path = UrlUtil.getPathFromUrl(url);              //根据url获取path
            String contentType = UrlUtil.getContentType(url);       //根据url获取contentType
            Socket socket = new Socket(ip,port);

            Date now = new Date();
            if (StrUtil.isEmpty(path))
                path="/";
            String header = "GET "+path+" HTTP/1.1\n" +
//                    "Host: "+host+"\n"+
                    "Connection: keep-alive\n" +
                    "Pragma: no-cache\n" +
                    "Cache-Control: no-cache\n" +
                    "User-Agent: 我的Web浏览器\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
//                    "Accept-Encoding: gzip, deflate, br\n" +
                    "Accept-Language: zh-CN,zh;q=0.9\n"+
                    "\r\n\r\n";
            System.out.println(header);
            OutputStream os = socket.getOutputStream();
            os.write(header.getBytes());
            os.flush();
            InputStream is = socket.getInputStream();
            byte[] data = StreamUtil.readStream(is);
            String inputStr = new String(data);
            is.close();
            return inputStr;
        }catch (Exception e){e.printStackTrace();}
        return "";
    }
    public static String getHeader(String url){
        URLConnection conn;
        try {
            URL obj = new URL(url);
            conn = obj.openConnection();
            Console.log(conn.getContentType());
            Map<String, List<String>> map = conn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                Console.log(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    class UrlListener implements KeyListener        //按钮监听使劲按类
    {
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) goTo.doClick();
        }
        @Override
        public void keyReleased(KeyEvent e) {}
    }
}

/**
 * 浏览器原理：
 * http协议请求服务器，获取到服务器返回的数据
 * 解析头信息
 *
 * */
