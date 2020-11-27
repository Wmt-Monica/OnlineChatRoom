package edition_0.edition_0_1;
/**
 * 在线聊天室版本0.1
 *
 * 实现：创建出简易版的客户端窗口
 */

import javax.swing.*;

public class CharClient extends JFrame {
    public static void main(String[] args) {
        new CharClient().JFWindow();
    }

    /**
     * 功能：创建客户端窗口
     */
    public void JFWindow(){
        setBounds(600,300,300,300);  // 将客户端窗口的 x,y 坐标显示位置和设置 width,height 窗口的宽度和高度
        setVisible(true);  // 设置该客户端口的可见性为 true
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // 设置客户端口的关闭方式：EXIT_ON_CLOSE 关闭就将程序停止运行
    }
}
