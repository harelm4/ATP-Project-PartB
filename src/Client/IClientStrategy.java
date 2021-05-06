package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer) throws IOException, ClassNotFoundException;
}
