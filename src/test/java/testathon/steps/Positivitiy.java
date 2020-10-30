package test.java.testathon.steps;

import test.java.testathon.framework.BaseUITest;
import test.java.testathon.pagefactory.CovidHomePage;

public class Positivitiy extends BaseUITest {
    private static final int MaxStates = 3;

    public void getDataSortedByPositivity() throws Exception{

        CovidHomePage covidHomePage = new CovidHomePage(driverInstance.getDriver());
        covidHomePage.sortByPositivityRate();
        covidHomePage.getValidThreeStates(3);
    }
}
