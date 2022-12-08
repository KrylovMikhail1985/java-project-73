package hexlet.code.config;
import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.spring.webmvc.RollbarSpringConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration()
@ComponentScan({
        "hexlet.code",
        "com.rollbar.spring"
})
public class RollbarConfig {
    @Value(value = "${ACCESS_TOKEN}")
    private String accessTToken;

    @Bean
    public Rollbar rollbar() {
        return new Rollbar(getRollbarConfigs(accessTToken));
    }

    private Config getRollbarConfigs(String accessToken) {

        // Reference ConfigBuilder.java for all the properties you can set for Rollbar
        return RollbarSpringConfigBuilder.withAccessToken(accessToken)
                .environment("development")
                .build();
    }
}
