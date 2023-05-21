package io.github.daggerok.springprops;

import java.util.Collections;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class YamlValueConfigsApplication {

    public static void main(String[] args) {
        SpringApplication.run(YamlValueConfigsApplication.class, args);
    }
}

@Data
@Component
class MyYamlValueConfigs {

    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-string}")
    private String aString;

    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent1.a-child1.a-string-value}")
    private String aStringValue11;
    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent1.a-child1.an-another-string-value}")
    private String anAnotherStringValue11;

    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent1.a-child2.a-string-value}")
    private String aStringValue12;
    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent1.a-child2.an-another-string-value}")
    private String anAnotherStringValue12;

    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent2.a-child1.a-string-value}")
    private String aStringValue21;
    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent2.a-child1.an-another-string-value}")
    private String anAnotherStringValue21;

    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent2.a-child2.a-string-value}")
    private String aStringValue22;
    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent2.a-child2.an-another-string-value}")
    private String anAnotherStringValue22;
}

@RestController
@RequiredArgsConstructor
class MyYamlValueConfigsResource {

    final MyYamlValueConfigs myValueConfigs;

    @GetMapping("/my-yaml-value-configs")
    MyYamlValueConfigs getMyValueConfigs() {
        return myValueConfigs;
    }
}

@RestController
class MyBackwardCompatibleResource {

    @Value("${io.github.daggerok.springprops.my-yaml-value-configs.a-parent2.a-child1.a-string-value}")
    String myValueConfigsParent2Child1StringValue;

    @GetMapping("/my-yaml-value-configs/parent2/child1/a-string-value")
    Map<String, String> getMyValueConfigsParent2Child1StringValue() {
        return Collections.singletonMap(
                "io.github.daggerok.springprops.my-yaml-value-configs.a-parent2.a-child1.a-string-value",
                myValueConfigsParent2Child1StringValue
        );
    }
}
