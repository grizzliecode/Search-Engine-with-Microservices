package worker;

public class WorkerConfiguration {
    private Integer port;
    private String base_directory;

    public WorkerConfiguration(){
        this.port = 8080;
        this.base_directory = "";
    }
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getBase_directory() {
        return base_directory;
    }

    public void setBase_directory(String base_directory) {
        this.base_directory = base_directory;
    }
}
