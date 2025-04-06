package core;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerConfiguration {
    private String base_dir;
    private int port;

    public String getBase_dir() {
        return base_dir;
    }

    public void setBase_dir(String base_dir) {
        this.base_dir = base_dir;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
