package nz.co.twg.erpfisuppliers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@Slf4j
@SpringBootApplication
public class Application {
   public static void main(String[] args) {
      log.info("Coupa Supplier Interface Application is Started....");
      SpringApplication.run(Application.class, args);
   }
}