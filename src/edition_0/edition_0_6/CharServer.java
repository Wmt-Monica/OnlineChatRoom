package edition_0.edition_0_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线聊天室版本0.5
 *
 * 实现：客户端和服务器连接之后，客户端关闭之后，服务器将其对应的客户端关闭并且释放其资源
 *
 */
public class CharServer {

    private static ServerSocket server = null;  // 服务器 SeverSocket 对象

    private static int clientNum = 0;  // 服务器所连接的客户端的个数

    private static boolean ON_OFF = true;  // 客户端启动的布尔开关

    private static DataInputStream input = null;  // 服务器的接收客户端发来的数据的输入流

    private static DataOutputStream output = null;  // 服务器向客户端发送数据的输出流

    private static List<Socket> clients = new ArrayList<>();  // 服务器中所有所连接的客户端的套接字 List 集合

    public static void main(String[] args) {
        // 创建一个端口号为 7788 的服务器
        try {
            server = new ServerSocket(7788);

            while (ON_OFF){  // 在服务器开启的状态下连接同一个端口的客户端

                // 创建一个线程用于连接向服务器请求连接的客户端
                Socket client = null;
                try {
                    client = server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                clients.add(client);

                System.out.println("The"+(++clientNum)+" customer service side established the connection");

                new Thread(new dataSwitchingCenter()).start();  // 创建一个线程调用该线程

            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * 功能：使用内部类继承 Thread，将客户端发送来的数据向所有客户端送该数据，实现数据的中转站
     */
    static class dataSwitchingCenter extends Thread{
        @Override
        public void run() {

            boolean flag = true;  // 设置一个布尔值为 true ,循环遍历客户端 List 集合，获取客户端发来的信息

            while (flag) {

                Socket s = null;

                // 遍历客户端套接字容器的所有客户端套接字
                try {
                    for (Socket client : clients) {
                        s = client;
                        // 创建对应的客户端的输入流
                        input = new DataInputStream(client.getInputStream());

                        // 将获取到的客户端发送的数据
                        while (flag){
                            String data = input.readUTF();
                            System.out.println("客户端发送服务器的数据：" + data);
                        }

                    }
                } catch (Exception e) {  // 捕捉到任何的异常都将这个服务器关闭掉，同时将其客户端从客户端集合中移除，最后释放其中的资源

                    try {
                        clients.remove(s);
                        input.close();
                        s.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }

        }
    }

    /**
     * 功能：当服务端关闭时释放资源
     */
    private void release(){
        try {
            output.close();
            input.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
