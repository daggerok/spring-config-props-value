package io.github.daggerok.springprops;

import java.util.Map;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@ExtendWith(SpringExtension.class)
@DisplayName("ValueConfigsLikeConfigProps tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValueConfigsLikeConfigPropsApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void should_get_value_configs_like_config_props() {
        // when
        val response = restTemplate.exchange("/my-value-configs-like-config-props", HttpMethod.GET, null, MyValueConfigsLikeConfigProps.class);
        log.info(response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // and
        MyValueConfigsLikeConfigProps myValueConfigsLikeConfigProps = response.getBody();
        assertThat(myValueConfigsLikeConfigProps).isNotNull();

        // and
        assertThat(myValueConfigsLikeConfigProps.getAString()).isEqualTo("a string");
        assertThat(myValueConfigsLikeConfigProps.getParent2().getChild1().getAStringValue()).isEqualTo("a string value 21");
    }

    @Test
    void should_get_parent2_child1_string_value() {
        // when
        val mapType = new ParameterizedTypeReference<Map<String, String>>() {};
        val response = restTemplate.exchange("/my-value-configs-like-config-props/parent2/child1/a-string-value", HttpMethod.GET, null, mapType);
        log.info(response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // and
        Map<String, String> map = response.getBody();
        assertThat(map).isNotNull();
        assertThat(map).hasSize(1);

        // and
        val value = map.get("io.github.daggerok.springprops.my-value-configs-like-config-props.a-parent2.a-child1.a-string-value");
        assertThat(value).isEqualTo("a string value 21");
    }
}
