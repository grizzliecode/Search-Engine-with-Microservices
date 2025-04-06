package worker;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
public class WorkerApplication {

    private static WorkerConfiguration workerConfiguration;

    public static void main(String[] args) throws RuntimeException {
        initialize(args);
        SpringApplication app = new SpringApplication(WorkerApplication.class);
        ConfigurableApplicationContext context = app.run(args);
    }


    public static void initialize (String[] args) throws RuntimeException {
        workerConfiguration = new WorkerConfiguration();
        if (args.length < 2) {
            throw new RuntimeException("Too few command line arguments");
        }
        try {
            Integer port_no = Integer.decode(args[0]);
			workerConfiguration.setPort(port_no);
        } catch (NumberFormatException e) {
			throw new RuntimeException("First cli argument has to be a valid port number");
        }
		String base_dir = args[1];
		workerConfiguration.setBase_directory(base_dir);
        System.setProperty("server.port", String.valueOf(workerConfiguration.getPort()));
    }

    @Bean
    public WorkerConfiguration getWorkerConfiguration(){
        return workerConfiguration;
    }
}
