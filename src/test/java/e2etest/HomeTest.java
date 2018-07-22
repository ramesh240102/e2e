package e2etest;

import e2e.Home;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.Browser;


public class HomeTest {

    Home home = new Home();

    @BeforeClass
    public void toOpenWebPageAndVerifyTitle()
    {
        home.openECSDigitalPage();
        Assert.assertEquals(home.getPageTitle(),"Welcome to the ECSDigital Engineer in Test tech test");
    }

    @Test
    public void identifyPartitionIndexTest()
    {
        home.clickRenderChallenge();
        home.enterChallengeValueAndName();
        home.submitAnswers();
        Assert.assertTrue(home.verifyForSuccessAnswer().contains("Congratulations you have succeeded." +
                " Please submit your challenge"));
        home.clickToCloseDialog();
    }

    @AfterClass
    public void toCloseWebPage()
    {
        Browser.closeDriver();
    }

}
