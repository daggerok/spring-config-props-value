package io.github.daggerok.springprops;

import java.util.Collections;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConfigPropsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigPropsApplication.class, args);
    }
}

@Configuration
@EnableConfigurationProperties(MyConfigProps.class)
class MyConfigPropsConfig {
}

@Data
@ConfigurationProperties("io.github.daggerok.springprops.my-config-props")
class MyConfigProps {

    private String aString;
    private Parent aParent1;
    private Parent aParent2;

    @Data
    static class Parent {

        private Child aChild1;
        private Child aChild2;

        @Data
        static class Child {

            private String aStringValue;
            private String anAnotherStringValue;
        }
    }
}

@RestController
@RequiredArgsConstructor
class MyConfigPropsResource {

    final MyConfigProps myConfigProps;

    @GetMapping("/my-config-props")
    MyConfigProps getMyConfigProps() {
        return myConfigProps;
    }
}

@RestController
class MyBackwardCompatibleResource {

    @Value("${io.github.daggerok.springprops.my-config-props.a-parent2.a-child1.a-string-value}")
    String myConfigPropsParent2Child1stringValue;

    @GetMapping("/my-config-props/parent2/child1/a-string-value")
    Map<String, String> getMyConfigPropsParent2Child1stringValue() {
        return Collections.singletonMap(
                "io.github.daggerok.springprops.my-config-props.a-parent2.a-child1.a-string-value",
                myConfigPropsParent2Child1stringValue
        );
    }
}
