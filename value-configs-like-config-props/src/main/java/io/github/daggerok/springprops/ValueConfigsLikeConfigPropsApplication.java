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
public class ValueConfigsLikeConfigPropsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValueConfigsLikeConfigPropsApplication.class, args);
    }
}

@Data
@Component
class MyValueConfigsLikeConfigProps {

    @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-string}")
    String aString;

    final Parent1 parent1;
    final Parent2 parent2;

    @Data
    @Component
    static class Parent1 {
        final Child11 child1;
        final Child12 child2;
    }

    @Data
    @Component
    static class Parent2 {
        final Child21 child1;
        final Child22 child2;
    }

    @Data
    @Component
    static class Child11 {

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent1.a-child1.a-string-value}")
        String aStringValue;

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent1.a-child1.an-another-string-value}")
        String anAnotherStringValue;
    }

    @Data
    @Component
    static class Child12 {

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent1.a-child2.a-string-value}")
        String aStringValue;

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent1.a-child2.an-another-string-value}")
        String anAnotherStringValue;
    }

    @Data
    @Component
    static class Child21 {

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent2.a-child1.a-string-value}")
        String aStringValue;

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent2.a-child1.an-another-string-value}")
        String anAnotherStringValue;
    }

    @Data
    @Component
    static class Child22 {

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent2.a-child2.a-string-value}")
        String aStringValue;

        @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent2.a-child2.an-another-string-value}")
        String anAnotherStringValue;
    }
}

@RestController
@RequiredArgsConstructor
class MyValueConfigsLikeConfigPropsResource {

    final MyValueConfigsLikeConfigProps myValueConfigsLikeConfigProps;

    @GetMapping("/my-value-configs-like-config-props")
    MyValueConfigsLikeConfigProps getMyValueConfigsLikeConfigProps() {
        return myValueConfigsLikeConfigProps;
    }
}

@RestController
class MyBackwardCompatibleResource {

    @Value("${io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent2.a-child1.a-string-value}")
    String myValueConfigsParent2Child1StringValue;

    @GetMapping("/my-value-configs-like-config-props/parent2/child1/a-string-value")
    Map<String, String> getMyValueConfigsParent2Child1StringValue() {
        return Collections.singletonMap(
                "io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent2.a-child1.a-string-value",
                myValueConfigsParent2Child1StringValue
        );
    }
}
