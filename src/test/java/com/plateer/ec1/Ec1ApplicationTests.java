package com.plateer.ec1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Ec1ApplicationTests {

    @Autowired
    private TestMapper testMapper;

    @Test
    @DisplayName("TestMapper Test")
    public void mybatis_Mapper_XML_테스트() throws Exception {

        // given
        int seq = 1;

        // when
        TestVO vo = testMapper.selectInt();

        // then
        assertThat(vo.getNum()).isEqualTo(1);

    }

}
