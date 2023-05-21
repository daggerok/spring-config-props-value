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
@DisplayName("YamlValueConfigs tests")
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YamlValueConfigsApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void should_get_yaml_value_configs() {
        // when
        val response = restTemplate.exchange("/my-yaml-value-configs", HttpMethod.GET, null, MyYamlValueConfigs.class);
        log.info(response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // and
        MyYamlValueConfigs myValueConfigs = response.getBody();
        assertThat(myValueConfigs).isNotNull();

        // and
        assertThat(myValueConfigs.getAString()).isEqualTo("a string");
        assertThat(myValueConfigs.getAStringValue21()).isEqualTo("a string value 21");
    }

    @Test
    void should_get_parent2_child1_string_value() {
        // when
        val mapType = new ParameterizedTypeReference<Map<String, String>>() {};
        val response = restTemplate.exchange("/my-yaml-value-configs/parent2/child1/a-string-value", HttpMethod.GET, null, mapType);
        log.info(response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // and
        Map<String, String> map = response.getBody();
        assertThat(map).isNotNull();
        assertThat(map).hasSize(1);

        // and
        val value = map.get("io.github.daggerok.springprops.my-yaml-value-configs.a-parent2.a-child1.a-string-value");
        assertThat(value).isEqualTo("a string value 21");
    }
}
