package edition_1.edition_1_0;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线聊天室版本1.0
 * 一次性将聊天室的基本功能实现
 */

public class CharServer {

    private static ServerSocket server = null;  // 服务器 SeverSocket 对象

    private static int clientNum = 0;  // 服务器所连接的客户端的个数

    private static boolean ON_OFF = true;  // 客户端启动的布尔开关

    private static List<clientAggregate> clients = new ArrayList<>();  // 服务器中所有所连接的客户端的套接字 List 集合

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
                Socket clientSocket = null;
                try {
                    clientSocket = server.accept();  // 建立起客户端和服务器的连接

                    // 根据服务器所连接的客户端的socket端口来创建 clientAggregate 对象
                    clientAggregate client = new clientAggregate(clientSocket);

                    clients.add(client);  // 将创建好的 clientAggregate 对象添加进客户端集合中 clients

                    new Thread(client).start();  // 启动每一个客户端对象在服务器中执行的线程程序

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    class clientAggregate implements Runnable{

        Socket client;  // 该客户端的 socket 端口号

        DataInputStream input = null;

        DataOutputStream output = null;

        public clientAggregate(Socket client) {
            ++clientNum;  // 没创建一个clientAggregate 对象，服务器所连接的一个总客户端的数量加一
            this.client = client;
            System.out.println("The"+(clientNum)+" customer service side established the connection");
        }

        @Override
        public void run() {

            boolean flag = true;  // 设置一个布尔值为 true ,循环遍历客户端 List 集合，获取客户端发来的信息

            while (flag) {
                String data = receive();  // 在死循环中持续让服务器接受该客户端发来的信息
                if (data == null){
                    System.out.println("客户端下线了....");

                    clients.remove(client);  // 将该于服务器断开连接的客户端移除客户端 List 集合中

                    // 当接受的数据为 null 时，说明客户端断开了和服务器的连接，将该客户端资源释放
                    new CloseResources().Close(client);

                    break;  // 将客户端关闭之后退出循环不在接收该客户端的信息
                }else {
                    System.out.println(data);  // 如果该客户端和服务器未断开连接就将接收的数据打印输出
                    send(data);  // 并将该数据发送给其他的客户端
                }

            }
        }

        /**
         * 创建一个方法专门针对该 clientAggregate 对象来获取该客户端对象向服务器传来的信息
         */
        private String  receive(){
            String data = null;
            try {
                // 使用 client 客户端的 socket 对象来实例化 input 输入流
                input = new DataInputStream(client.getInputStream());
                data = input.readUTF();
            } catch (IOException e) {
                new CloseResources().Close(input);  // 如果该输入流发生 IOException 异常就将该留释放
            }
            return data;  // 返回读取到的从客户端发来的信息
        }

        /**
         * 创建将从一方客户端接收的数据发送给 clients 该服务器所连接的客户端集合中的所有客户端发送该数据
         *
         * @param data 服务器要发送给所有客户端的数据
         */
        private void send(String data){
            try {
                for (clientAggregate c : clients){
                    // 根据要传给数据的属于接收方的客户端的 socket 实例化该输出流对象
                    output = new DataOutputStream(c.client.getOutputStream());
                    output.writeUTF(data);
                }
            } catch (IOException e) {
                new CloseResources().Close(output);  // 如果输出流出现 IOException 异常就是释放器输出流资源
            }
        }
    }

}
