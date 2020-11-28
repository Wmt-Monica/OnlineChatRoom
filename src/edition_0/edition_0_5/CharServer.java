package edition_0.edition_0_5;

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
 * 实现：创建一个服务端，实现客户端和服务端的网络连接
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
        // 创建一个端口号为 8080 的服务器
        try {
            server = new ServerSocket(8080);

            while (ON_OFF){  // 在服务器开启的状态下连接同一个端口的客户端

                // 创建一个线程用于连接向服务器请求连接的客户端
                Socket client =  server.accept();

                clients.add(client);

                System.out.println("The"+(++clientNum)+" customer service side established the connection");

                // 创建一个线程用于客户端间数据中转传送
                new Thread(new dataSwitchingCenter()).start();

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

            // 遍历客户端套接字容器的所有客户端套接字
            for (Socket client : clients){

                try {
                    // 创建对应的客户端的输入流
                    input = new DataInputStream(client.getInputStream());

                    // 将获取到的客户端发送的数据
                    String data = input.readUTF();
                    System.out.println("客户端发送服务器的数据："+data);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
