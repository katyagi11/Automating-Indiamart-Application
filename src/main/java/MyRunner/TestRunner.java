package MyRunner;

import gherkin.formatter.Reporter;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import java.io.File;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "../FreeCrmBDDFramework/src/main/java/Features", //the path of the feature files
        glue = {"src/main/java/stepDefinitions"}, //the path of the step definition files
        monochrome = true, //display the console output in a proper readable format
        plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"}
//      strict = true //it will check if any step is not defined in step definition file
//		dryRun = false //to check the mapping is proper between feature file and step def file
        //tags = {"~@SmokeTest" , "~@RegressionTest", "~@End2End"}
)

public class TestRunner {
}

//ORed : tags = {"@SmokeTest , @RegressionTest"} -- execute all tests tagged as @SmokeTest OR @RegressionTest
//ANDed : tags = tags = {"@SmokeTest" , "@RegressionTest"} -- execute all tests tagged as @SmokeTest AND @RegressionTest
	

