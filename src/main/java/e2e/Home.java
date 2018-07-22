package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import static utilities.Browser.driver;


public class Home  {

    public Home()
    {
        PageFactory.initElements(driver(),  this);
    }

    @FindBy(xpath = "//h1[@class ='title']")
    private WebElement homePagetTitle;

    @FindBy(xpath = "//button[@data-test-id='render-challenge']")
    private WebElement renderChallengeButton;

    @FindBy(xpath = "//input[@data-test-id='submit-1']")
    private WebElement enterAnswerOne;

    @FindBy(xpath = "//input[@data-test-id='submit-2']")
    private WebElement enterAnswerTwo;

    @FindBy(xpath = "//input[@data-test-id='submit-3']")
    private WebElement enterAnswerThree;

    @FindBy(xpath = "//input[@data-test-id='submit-4']")
    private WebElement enterAnswerFour;

    @FindBy(xpath = "//button//span[contains(text(),'Submit Answers')]")
    private WebElement submitAnswerButton;

    @FindAll(@FindBy(xpath = "//section[@id='challenge']//div/table/tbody/tr"))
    private WebElement arrayRow;

    @FindAll(@FindBy(xpath = "//section[@id='challenge']//div/table/tbody/tr[1]/td"))
    private WebElement arrayColumn;

    @FindBy(xpath = "//div[@class='dialog']//div[contains(text(),'Congratulations you have succeeded')]")
    private WebElement successMessage;

    @FindBy(xpath = "//button//span[contains(text(),'Close')]")
    private WebElement dialogClose;

    public void openECSDigitalPage()
    {
        driver().navigate().to("http://localhost:3000/");
    }

    public String getPageTitle()
    {
        return homePagetTitle.getText();
    }

    public void clickRenderChallenge()
    {
        renderChallengeButton.click();
    }

    //Currently passing value directly inside the method
    public void enterChallengeValueAndName()
    {
        enterAnswerOne.sendKeys(getTableRowPartitionValue().get(0).toString());
        enterAnswerTwo.sendKeys(getTableRowPartitionValue().get(1).toString());
        enterAnswerThree.sendKeys(getTableRowPartitionValue().get(2).toString());
        enterAnswerFour.sendKeys("Ramesh");
    }

    public void submitAnswers()
    {
        submitAnswerButton.click();
    }

    public String verifyForSuccessAnswer()
    {
        return successMessage.getText();
    }

    public void clickToCloseDialog()
    {
        dialogClose.click();
    }

    // Method to return list of partition index from table data
    public ArrayList<Integer> getTableRowPartitionValue()
    {
        ArrayList<Integer> partitionIndexValue = new ArrayList<Integer>();
        int rowCount = driver().findElements(By.xpath("//section[@id='challenge']//div/table/tbody/tr")).size();
        int colCount = driver().findElements(By.xpath("//section[@id='challenge']//div/table/tbody/tr[1]/td")).size();
        String[] colArray = new String[colCount];

        //Divided xpath in three parts to pass Row_count and Col_count values.
        String first_part = "//section[@id='challenge']//div/table/tbody/tr[";
        String second_part = "]/td[";
        String third_part = "]";

        //Used for loop for number of rows.
        for (int iCount = 1; iCount <= rowCount; iCount++){
            //Used for loop for number of columns.
            for(int jCount = 1; jCount <= colCount; jCount++){
                String final_xpath = first_part + iCount + second_part + jCount + third_part;
                String tableData = driver().findElement(By.xpath(final_xpath)).getText();
                colArray[jCount-1] = tableData;
            }
            partitionIndexValue.add(checkPartitionIndex(colArray));
        }
       return partitionIndexValue;
    }


    //Method to identify the partition index only.
    // This can places in separate class in utilities but for now placing here
    public int checkPartitionIndex(String[] arr){
        //converting String array to interger array
        int iNum = 0;
        Integer[] toPartition=new Integer[arr.length];
        for (String str : arr)
            toPartition[iNum++] = Integer.parseInt(str);

        //Sum up the complete array
        int sumTotal=0;
        for (int i=0; i < toPartition.length; i++){
            sumTotal += toPartition[i];
        }

        //To find the partition number
        int sumRight = 0;
        int sumLeft = 0;
        int iCount = 0;
        for (iCount =1; iCount < toPartition.length-1; iCount++)
        {
            sumLeft += toPartition[iCount-1];
            sumRight = sumTotal - toPartition[iCount] - sumLeft;
            if (sumRight == sumLeft){
                return iCount;
            }
        }
        return iCount;
    }

}
