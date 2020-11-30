package edition_0.edition_0_9;

/**
 * 在线聊天室版本0.9
 * 实现：将释放资源包装成一个类,
 */

import java.io.Closeable;
import java.io.IOException;

/**
 *
 */
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
