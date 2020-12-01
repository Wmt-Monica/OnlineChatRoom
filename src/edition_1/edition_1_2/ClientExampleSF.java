package edition_1.edition_1_2;

/**
 * 客户端实例：SF对象
 */
public class ClientExampleSF {
    public static void main(String[] args) {
        int clientPort = 7788;
        new ChatClient(clientPort,"SF");
    }
}
