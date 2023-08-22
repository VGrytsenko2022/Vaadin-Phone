package ua.vg.vp;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.vg.vp.config.PhoneConfiguration;


/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "vphone", variant = Lumo.DARK)
@PWA(name = "VPhone 1.0.1", shortName = "Vaadin Phone", iconPath = "images/PidginBird.png", backgroundColor = "#233348", themeColor = "#233348")
public class Application implements AppShellConfigurator {
    public static PhoneConfiguration configuration;
    @Autowired
    public Application(PhoneConfiguration configuration){
       Application.configuration = configuration;
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static PhoneConfiguration getConfiguration() {
        return configuration;
    }
}
