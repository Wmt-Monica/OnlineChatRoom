package edition_0.edition_0_4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 在线聊天室版本0.4
 *
 * 实现：创建一个服务端，实现客户端和服务端的网络连接
 *
 */
public class CharServer {

    private static int clientNum = 0;  // 服务器所连接的客户端的个数

    private static boolean ON_OFF = true;  // 客户端启动的布尔开关

    public static void main(String[] args) {
        // 创建一个端口号为 8080 的服务器
        try {
            ServerSocket server = new ServerSocket(8080);

            while (ON_OFF){  // 在服务器开启的状态下连接同一个端口的客户端
                Socket client = server.accept();
                System.out.println("The"+(++clientNum)+" customer service side established the connection");
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
