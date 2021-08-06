package sn.architech.etatcivil.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import sn.architech.etatcivil.EtatcivilApp;

@CucumberContextConfiguration
@SpringBootTest(classes = EtatcivilApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
