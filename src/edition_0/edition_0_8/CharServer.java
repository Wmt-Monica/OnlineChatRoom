package edition_0.edition_0_8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线聊天室版本0.8
 *
 * 实现：服务端的静态类使用非静态方法调用，客户端功能窗口包装成一个类
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
        new CharServer().start();
    }

    /**
     * dataSwitchingCenter 类是非静态的,main()方法是静态的不能直接调用，
     * 所以此处创建一个方法来实现间接使用该类
     */
    public void start(){
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
        }catch(BindException e){
            System.out.println("Port in use......");  // 端口使用中......
            System.exit(0);
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * 功能：使用内部类继承 Thread，将客户端发送来的数据向所有客户端送该数据，实现数据的中转站
     */
    class dataSwitchingCenter extends Thread{
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
                }catch (IOException e) {
                    // 与客户端的连接如果遇到异常，就将该客户端关闭
                    if (s != null){
                        try {
                            s.close();
                        } catch (IOException ex) {
                            System.out.println("一个客户端关闭");
                        }
                    }
                }finally {
                    // 无论是否捕捉到异常，最后都要将其输入输出流关闭
                    try {
                        if (input != null){
                            input.close();
                        }
                        if (output != null){
                            output.close();
                        }
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
