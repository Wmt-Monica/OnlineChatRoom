package edition_1.edition_1_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends JFrame {

    private TextField textField = new TextField();  // 客户端的输入发送框

    private TextArea textArea = new TextArea();  // 聊天内容显示文本面板

    private DataOutputStream output = null;  // 客户端发送数据使用的输出流

    private Socket client = null;  // 客户端的套字节 Socket 对象

    private int clientPort;  // 客户端连接服务器的指定端口号

    private String name;


    /**
     * 客户端类的构造器
     *
     * @param clientPort 客户端连接服务器指定的端口号
     */
    public ChatClient(int clientPort, String name) {
        this.clientPort = clientPort;
        this.name = name;
        try {
            JFWindow();  // 创建客户端窗口的方法

            // 创建客户端的套接字连接主机
            client = new Socket("127.0.0.1",this.clientPort);  // 使用本机电脑的IP地址 127.0.0.1

            System.out.println("Client connection successful");

            output = new DataOutputStream(client.getOutputStream());  // 初始化客户端的输出流为该套接字的输出流对象

            // 调用 receive 实现了Runnable接收的类来接收服务器向客户端发来的信息
            new Thread(new receive(client)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：创建客户端窗口
     */
    public void JFWindow(){
        setBounds(600,300,300,300);  // 将客户端窗口的 x,y 坐标显示位置和设置 width,height 窗口的宽度和高度

        add(textArea,BorderLayout.NORTH);   // 将 textArea 组件使用边框布局的方式放置在窗口的北边
        add(textField,BorderLayout.SOUTH);  // 将 textField 组件使用边框布局的方式放置在窗口的南边

        pack();  // 根据窗口的组件元素的大小自适应调整主窗口的宽高

        textField.addActionListener(new SendListener());  // 为输入框实现动作监听

        setVisible(true);  // 设置该客户端口的可见性为 true

        setResizable(false);  // 设置不可以更改窗口大小

        this.addWindowListener(new WindowAdapter() {  // 创造窗口关闭的动作监听
            @Override
            public void windowClosed(WindowEvent e) {
                new CloseResources().Close(output,client);  // 关闭窗口释放客户端所有的资源
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // 设置客户端口的关闭方式：EXIT_ON_CLOSE 关闭就将程序停止运行

    }

    /**
     * 功能：使用内部类实现客户端输入框的动作监听
     */
    private class SendListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!textField.getText().trim().equals("") && textField.getText() != null){

                String dataString = textField.getText().trim();  // 获取去除两头空格的字符串数据

                textField.setText("");  // 在发送数据之后，将输入框中的字符串数据删除

                // 调用 sendDataToSever 实现了Runnable接收的类来将该字符串数据发送给服务器
                new Thread(new send(dataString)).start();

            }
        }

        /**
         * 功能：将在客户端输入框发送的字符串数据发送给服务器
         */

       class send implements Runnable{
           String data;

           public send(String data) {
               this.data = data;
           }

           @Override
           public void run() {
               try {
                   output.writeUTF(name+" : "+data);  // 将要发送字符串的参数数据发送给服务器

                   output.flush();  // 刷新输出流对象，防止数据滞留

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
    }

    /**
     * 创建一个接收服务器发来的信息的类
     */
    class receive implements Runnable{

        DataInputStream input;
        String data = null;

        public receive(Socket client) {
            try {
                this.input = new DataInputStream(client.getInputStream());
            } catch (IOException e) {
                new CloseResources().Close(input);  // 如果输入流出现 IOException 就释放其输入流对象
            }
        }

        @Override
        public void run() {
            while (true){

                try {
                    data = input.readUTF();
                    if (data != null || !data.equals("")){
                        // 在客户端的聊天显示文本面板中输入该该字符串
                        if (textArea.getText() == null || textArea.getText().equals("")){
                            textArea.setText(data);
                        }else {
                            textArea.append("\n"+data);
                        }
                    }
                } catch (IOException e) {
                    new CloseResources().Close(input);  // 如果输入流出现 IOException 就释放其输入流对象
                }
            }
        }
    }
}
