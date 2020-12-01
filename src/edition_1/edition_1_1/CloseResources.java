package edition_1.edition_1_1;


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
