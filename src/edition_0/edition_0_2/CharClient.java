package edition_0.edition_0_2;
/**
 * 在线聊天室版本0.2
 *
 * 实现：将客户端的窗口添加输入框(TextField)和显示的文本框(TextArea)组件
 *      最后使用pack()方法来根据组件的大小来自动适应调整主窗口的宽高
 *
 */

import javax.swing.*;
import java.awt.*;

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
        setVisible(true);  // 设置该客户端口的可见性为 true
        setResizable(false);  // 设置不可以更改窗口大小
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // 设置客户端口的关闭方式：EXIT_ON_CLOSE 关闭就将程序停止运行
    }
}
