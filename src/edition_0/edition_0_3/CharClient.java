package edition_0.edition_0_3;
/**
 * 在线聊天室版本0.3
 *
 * 实现：客户端的窗口下面的输入框组件输入一串字符串回车就将其发送到上面的显示输入面板中
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharClient extends JFrame {
    private TextField textField = new TextField();
    private TextArea textArea = new TextArea();

    public static void main(String[] args) {
        new CharClient().JFWindow();
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
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // 设置客户端口的关闭方式：EXIT_ON_CLOSE 关闭就将程序停止运行
    }

    /**
     * 功能：使用内部类实现客户端输入框的动作监听
     */
    private class SendListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String dataString = textField.getText().trim();  // 获取去除两头空格的字符串数据
            textField.setText("");  // 在发送数据之后，将输入框中的字符串数据删除
            textArea.setText(dataString);
        }
    }
}
