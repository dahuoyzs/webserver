package cn.bigfire.webserver.server;

import cn.bigfire.webserver.util.ArrayUtil;
import cn.bigfire.webserver.util.StreamUtil;
import cn.bigfire.webserver.util.UrlUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import java.io.*;
import cn.bigfire.webserver.util.Console;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @ IDE    ：IntelliJ IDEA.
 * @ Date   ：2019/12/20  10:31
 * @ Desc   ：
 */
public class ServerThread extends Thread {
    private String rootPath;
    private Integer port;
    private Server server;

    private ServerSocket serverSocket;
    public ServerThread(Server server) {
        this.server = server;
        this.rootPath = server.jtfPath.getText().trim();
        this.port = Integer.valueOf(server.jtfPort.getText().trim());
    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            server.echo(port + "端口已开启成功");
            server.startStatus();

            while (true) {
                Socket socket = serverSocket.accept();
                Console.log("接收到请求");
                OutputStream os = socket.getOutputStream();                //服务端一创建就获取到客户端的输出流管道
                String inputStr = StreamUtil.getInputStr(socket.getInputStream());
//                byte[] data = StreamUtil.readStream(socket.getInputStream());
//                String inputStr = new String(data);
                System.out.println("data------------------------------------------------------------------------------------------------------------------------------start");
                System.out.println(inputStr);
                System.out.println("data------------------------------------------------------------------------------------------------------------------------------end");
                String path = UrlUtil.getPathInputStr(inputStr);
                Console.log(path);
                String contentType = UrlUtil.getContentType(path);
                if (path.endsWith("/")){
                    path+="index.html";
                }
                String fileName = rootPath + path;
                fileName = URLDecoder.decode(fileName,"UTF-8");
                File file = new File(fileName);
                byte[] body ;
                if (file.exists()&&file.isFile()){
                    if (contentType.equals(UrlUtil.customSuffix)){
                        String text = FileUtil.readString(file,"UTF-8");
                        String[] splits = text.split("\r\n");
                        StringBuilder sb = new StringBuilder();
                        try{
                            for (int i = 0; i < splits.length; i++) {
                                String line = splits[i];
                                Console.log(line);
                                try{
                                    Integer count = Integer.valueOf(StrUtil.subBetween(line,"输出","行"));
                                    Integer how = Integer.valueOf(StrUtil.subBetween(line,"行","级标题"));
                                    String innerText = StrUtil.subBetween(line,"内容为","字体颜色是");
                                    String color = StrUtil.subAfter(line,"字体颜色是",true);
                                    for (int j = 0; j < count; j++) {
                                        sb.append( "<h"+how+" style=\"color:"+color+"\">"+innerText+"</h"+how+">");
                                    }
                                }catch (Exception e){e.printStackTrace();
                                    throw new RuntimeException("文件第"+(i+1)+"行存在格式问题，解析失败");
                                }
                            }
                        }catch (RuntimeException e){
                            sb.append("<br><hr><h2 style=\"color:red;\">!!!ParserError:    "+e.getMessage()+"</h2>");
                        }
                        String fStr = sb.toString();
                        body = fStr.getBytes("UTF-8");
                        contentType = "text/html;charset=UTF-8";
                    }else body = FileUtil.readBytes(file);
                }else{
                    body = "<h1 style=\"color:red;\">404</h1>".getBytes();
                    contentType="text/html;charset=UTF-8";
                }
                Date now = new Date();
                String header = "HTTP/1.1 200 OK\n" +
                        "Server: 我的Web服务器\n" +
                        "Date: " + now + "\n" +
                        "Content-Type: "+contentType+"\n" +
                        "Access-Control-Allow-Origin: *\n" +
                        "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type,token, Accept, client_id, u_id, Authorization\n" +
                        "Access-Control-Allow-Methods: POST, GET, OPTIONS, DELETE\n" +
                        "Access-Control-Max-Age: 3600\n" +
                        "Cache-Control: no-cache, no-store, must-revalidate\n" +
                        "cache-control: no-cache\r\n\r\n";

                byte[] res = ArrayUtil.byteMerger(header.getBytes(),body);
                IoUtil.write(os, true, res);
                socket.close();
            }
        } catch (Exception e) {
            if (e.toString().contains("Address already in use")) {
                server.echo(port + "端口已被其他应用占用");
            }
            server.stopStatus();
            Boolean isSocketException = e instanceof SocketException;
            if (!isSocketException)
                e.printStackTrace();
        }
    }


    public void close() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
