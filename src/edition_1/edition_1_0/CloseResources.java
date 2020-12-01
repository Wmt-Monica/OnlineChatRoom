package edition_1.edition_1_0;

/**
 * 在线聊天室版本1.0
 * 一次性将聊天室的基本功能实现
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
