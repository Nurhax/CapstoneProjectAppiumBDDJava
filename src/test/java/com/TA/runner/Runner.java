/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TA.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//Setup cucumber
@CucumberOptions(
    features = "src/test/resources/features", // Lokasi file feature
    glue = "com.TA.steps",                    // Lokasi step definitions
    plugin = {"pretty", "html:target/cucumber-reports.html"} // Report HTML
    //,tags = "@Customer" //kalo misalkan mau test sesuai tags aja
)
/**
 *
 * @author M.Iqbal Nurhaq
 */
public class Runner {
    //dummy public class
}
