package cn.bigfire.webserver.server;
import cn.bigfire.webserver.util.Console;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
/**
 * @ IDE    ：IntelliJ IDEA.
 * @ Date   ：2019/12/20  10:18
 * @ Desc   ：
 */
public class Server {
    JFrame jFrame;
    JTextField jtfPath;
    JTextField jtfPort;
    JButton switchBtn;
    JTextArea console;
    boolean isOpen = false;        //当前开关状态
    Server server;
    ServerThread serverThread;
    public static void main(String[] args) throws Exception {
        new Server();
    }
    public Server() {
        jFrame = new JFrame("Web服务器1.0");

        jtfPath = new JTextField(System.getProperty("user.dir")+"/Tools/");
        jtfPath.setColumns(20);
        jtfPort = new JTextField("80");
        jtfPort.setColumns(5);
        switchBtn = new JButton("启动");            //初始化开关按钮
        switchBtn.addActionListener(new OpenAction());//给按钮设置监听事件
        JPanel jPanel = new JPanel();
        jPanel.add(jtfPath);
        jPanel.add(jtfPort);
        jPanel.add(switchBtn);
        jFrame.add(jPanel,BorderLayout.NORTH);
        console = new JTextArea();            //初始化文本框
        jFrame.add(console, BorderLayout.CENTER);//把文本框添加到窗体上
        jFrame.setBounds(600, 100, 400, 200);//设置窗口大小
        jFrame.setDefaultCloseOperation(3);//设置窗口点击右上角可以关闭
        jFrame.setVisible(true);            //设置窗口可见性
        server = this;
    }
    public void startStatus(){
        isOpen = true;
        switchBtn.setText("关闭");
        jFrame.setTitle("Running...");
    }
    public void stopStatus(){
        isOpen = false;
        switchBtn.setText("启动");
        jFrame.setTitle("Web服务器1.0");
    }
    public void echo(Object o){
        Console.log(o);
        server.console.append(o+"\n");
    }
    class OpenAction implements ActionListener        //按钮监听使劲按类
    {
        @Override
        public void actionPerformed(ActionEvent e)        //按钮响应事件
        {

            if (!isOpen)//当前状态关闭->要开启
            {
                if (!checkInput()){
                    return;//不满足直接退出
                }
                serverThread = new ServerThread(server);
                serverThread.start();
            } else {//当前状态开启->要关闭
                if (serverThread!=null){
                    serverThread.close();
                    serverThread.interrupt();
                    serverThread = null;
                }
                echo("服务器关闭成功");
                stopStatus();
            }
        }
        private boolean checkInput(){
            try{
                Integer.valueOf(jtfPort.getText().trim());//检测端口
                String path = jtfPath.getText().trim();
                File file = new File(path);
                boolean b = file.exists() && file.isDirectory();
                return b;
            }catch (Exception e){
                Console.log(e.getMessage());
                console.append("输入框错误:"+e.getMessage());
                return false;
            }
        }
    }
}
