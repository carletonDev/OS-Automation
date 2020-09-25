package desktop.database.services;

import desktop.database.pojos.Steps;
import desktop.database.pojos.Suite;
import desktop.database.pojos.TestCase;
import desktop.database.pojos.TestCaseSteps;
import desktop.database.repositories.StepsRepository;
import desktop.database.repositories.SuiteRepository;
import desktop.database.repositories.TestCaseRepository;
import desktop.excel.ExcelFileNames;
import desktop.excel.dataDrive.ClassParser;
import desktop.excel.dataDrive.ClassParser.ClassParserException;
import desktop.excel.dataDrive.ClassParser.DataNotAvailableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class MapToPojos {

  private ClassParser parser;

  public MapToPojos(ClassParser parser) {
    this.parser=parser;

  }

  public List<TestCase> maptoTestCasePojo(Map<String, List<String>> data)
      throws ClassParserException, DataNotAvailableException {
    List<TestCase> list;
    List<Suite> suites = new ArrayList<>();
    AtomicInteger count = new AtomicInteger();
    //get list of Suites
    data.get(ExcelFileNames.suite.name()).forEach(value -> {
      Suite suite = new Suite();
      suite.setSuiteName(value);
      suites.add(suite);
    });
    //move all other data into list
    list = parser.getTestCaseFromMap(data);

    //set suites to TestCase
    list.forEach(testCase -> {
      testCase.setSuite(suites.get(count.get()));
      count.getAndIncrement();
    });

    return list;
  }

  List<TestCase> maptoTestCasePojo(Map<String, List<String>> data,
      SuiteRepository suiteRepository)
      throws ClassParserException, DataNotAvailableException {
    List<TestCase> list;
    List<Suite> suites = new ArrayList<>();
    AtomicInteger count = new AtomicInteger();
    //get list of Suites
    data.get(ExcelFileNames.suite.name()).forEach(value -> {
      Suite suite = new Suite();
      suite.setSuiteName(value);
      if (suiteRepository != null) {
        Optional<Suite> example = suiteRepository.findOne(Example.of(suite));
        suites.add(example.orElse(suite));
      } else {
        suites.add(suite);
      }
    });
    //move all other data into list
    list = parser.getTestCaseFromMap(data);

    //set suites to TestCase
    list.forEach(testCase -> {
      testCase.setSuite(suites.get(count.get()));
      count.getAndIncrement();
    });

    return list;
  }

  List<TestCaseSteps> maptoTestCaseStepsPojo(Map<String, List<String>> data,
      TestCaseRepository testCaseRepository, StepsRepository stepsRepository)
      throws ClassParserException, DataNotAvailableException {
    //Declare values
    List<TestCaseSteps> list;
    List<TestCase> testCases = new ArrayList<>();
    List<Steps> steps = new ArrayList<>();
    AtomicInteger count = new AtomicInteger();

    //getList of Test Cases from Repository for each test case name in excel spreadsheet
    data.get("testCaseName").forEach(value -> {
      TestCase testCase = new TestCase();
      testCase.setTestCaseName(value);
      if (testCaseRepository != null) {
        Optional<TestCase> optional = testCaseRepository.findOne(Example.of(testCase));
        testCases.add(optional.orElse(testCase));
      } else {
        testCases.add(testCase);
      }
    });
    //get List of Steps from Repository for each row of step names in excel spreadsheet
    data.get("steps").forEach(value -> {
      Steps step = new Steps();
      step.setAction(value);
      if (stepsRepository != null) {
        Optional<Steps> optional = stepsRepository.findOne(Example.of(step));
        steps.add(optional.orElse(step));
      } else {
        steps.add(step);
      }
    });
    //use class parser class to parse remaining map data into a list of TestCaseSteps
    list = parser.getTestCaseStepsFromMap(data);

    //for Each TestCase Step Object insert the Step from the List of Steps matching and Test Case
    list.forEach(tcs -> {
      tcs.setSteps(steps.get(count.get()));
      tcs.setTestCase(
          testCases.get(count.get()));
      count.getAndIncrement();
    });

    return list;
  }



  public List<TestCaseSteps> maptoTestCaseStepsPojo(Map<String, List<String>> data)
      throws ClassParserException, DataNotAvailableException {
    //Declare values
    List<TestCaseSteps> list;
    List<TestCase> testCases = new ArrayList<>();
    List<Steps> steps = new ArrayList<>();
    AtomicInteger count = new AtomicInteger();

    //getList of Test Cases from Repository for each test case name in excel spreadsheet
    data.get("testCaseName").forEach(value -> {
      TestCase testCase = new TestCase();
      testCase.setTestCaseName(value);
      testCases.add(testCase);
    });
    //get List of Steps from Repository for each row of step names in excel spreadsheet
    data.get("steps").forEach(value -> {
      Steps step = new Steps();
      step.setAction(value);
      steps.add(step);
    });
    //use class parser class to parse remaining map data into a list of TestCaseSteps
    list = parser.getTestCaseStepsFromMap(data);

    //for Each TestCase Step Object insert the Step from the List of Steps matching and Test Case
    list.forEach(tcs -> {
      tcs.setSteps(steps.get(count.get()));
      tcs.setTestCase(
          testCases.get(count.get()));
      count.getAndIncrement();
    });

    return list;
  }
}
