package com.kwt;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@CucumberOptions(tags = "not @wip")
@SpringBootTest(classes = MarvelStudioFilmsSteps.class)
public class CucumberTestContextConfiguration {
}
