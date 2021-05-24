package co.com.univision.widgets.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "univision.url")
public class ConfigProperties {

    String urlContent;
    String base;
    String news;
    String lazyLoad;

}
