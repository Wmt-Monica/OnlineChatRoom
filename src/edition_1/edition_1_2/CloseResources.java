package edition_1.edition_1_2;

/**
 * 工具类：用于释放资源。
 * 例如：客户端 socket 关闭，断开连接，
 *      DataInputStream,DataOutputStream输入输出流的资源释放
 */

import java.io.Closeable;
import java.io.IOException;

public class CloseResources {
    public static void Close(Closeable... resources){
        for (Closeable c : resources){
            if (c != null){
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
