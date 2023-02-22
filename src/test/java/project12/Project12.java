package project12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**1- Go to "https://demo.openmrs.org/openmrs/login.htm".
 2- Try to log in with username="Admin" and password="Admin123"
 without choosing a location and verify that the error
 message is "You must choose a location!".
 3- Enter the same username and password.
 4- Hover over the location names one by one and check if
 the background color changes.
 5- Choose one of the locations randomly and click on
 "Log In" button.
 6- Click on the location icon
 7- Click on every location one by one and check if it is
 among the locations on the login page and current location changes.
 8- Verify that admin button is displayed
 9- Hover over "Admin" button and verify that "My Account"
 button is displayed.
 10- Click on "My Account" button and verify that the title of
 the page is "My Account".
 11- Click on "My Languages" button.
 12- Verify that the title of the page is "My Languages"
 13- Select a random language from the dropdown menu.
 14- Check and uncheck the boxes one by one. Verify
 that the box is checked and unchecked each time.
 15- Check all the boxes and verify that they are all checked
 16- Click on The "Save" button and verify error message is
 displayed.

 Note: Create a new project. Create your Utility class and add all the methods you need
 including hard wait in case you need.*/
public class Project12 {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();
        //1- Go to "https://demo.openmrs.org/openmrs/login.htm".
        driver.get("https://demo.openmrs.org/openmrs/login.htm");

        //2- Try to log in with username="Admin" and password="Admin123" without choosing a location and verify that the error message is "You must choose a location!"
        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys("Admin");
        WebElement password= driver.findElement(By.id("password"));
        password.sendKeys("Admin123");
        WebElement login=driver.findElement(By.cssSelector("input[id='loginButton']"));
        login.click();
        WebElement errorMessage=driver.findElement(By.id("sessionLocationError"));
        System.out.println("Step2:Verify that error message is displayed: "+errorMessage.getText());
        System.out.println("========================================================================");

        //3- Enter the same username and password.
        driver.navigate().refresh();
        WebElement username2=driver.findElement(By.id("username"));
        username2.sendKeys("Admin");
        WebElement password2= driver.findElement(By.id("password"));
        password2.sendKeys("Admin123");
        //4- Hover over the location names one by one and check if the background color changes.
        List<WebElement> locations=driver.findElements(By.cssSelector("li[tabindex='0']"));
        List<String> locationsBeforeLogin=new ArrayList<>();
        Actions actions=new Actions(driver);
        for (WebElement location:locations
        ) {
            locationsBeforeLogin.add(location.getText());
            String before=location.getCssValue("background color");
            Action hoverOver=actions.moveToElement(location).build();
            hoverOver.perform();
            String after=location.getCssValue("background color");
            System.out.println("Step4:Verify that background color is changed?: "+ before.equals(after));
        }
        System.out.println("===============================================================");

        //5- Choose one of the locations randomly and click on
        // "Log In" button.
        locations.get((int)(Math.random()*6)).click();
        WebElement login2=driver.findElement(By.cssSelector("input[id='loginButton']"));
        login2.click();

        //6- Click on the location icon
        WebElement selectedLocation=driver.findElement(By.id("selected-location"));
        selectedLocation.click();
        // 7- Click on every location one by one and check if it is among the locations on the login page and current location changes.
        List<WebElement> locationsAfterLogin=driver.findElements(By.cssSelector("div>.select>li"));


        for (WebElement location:locationsAfterLogin
        ) {
            System.out.println("Step7:Verify that both page have same locations: " + locationsBeforeLogin.contains(location.getText()));
            location.click();
            selectedLocation.click();
        }
        locationsAfterLogin.get(0).click();

        //8- Verify that admin button is displayed
        WebElement adminButton=driver.findElement(By.cssSelector(".nav-item.identifier"));
        System.out.println("Step8:Is admin button displayed? "+ adminButton.isDisplayed());
        System.out.println("======================================================");


        //9- Hover over "Admin" button and verify that "My Account" button is displayed.
        actions.moveToElement(adminButton).perform();
        WebElement myAccount=driver.findElement(By.partialLinkText("My Account"));
        System.out.println("Step9:Is My Account button is displayed?: "+ myAccount.isDisplayed());
        System.out.println("======================================================");


        //10- Click on "My Account" button and verify that the title of the page is "My Account".
        myAccount.click();
        System.out.println("Step10:Is title of the page is \"My Account\"?: "+ driver.getTitle().equals("My Account"));
        System.out.println("======================================================");
//        11- Click on "My Languages" button.
        WebElement myLanguages=driver.findElement(By.partialLinkText("My Languages"));
        myLanguages.click();
        //12- Verify that the title of the page is "My Languages"
        System.out.println("Step12: Is title of the page is \"My Languages\":"+ driver.getTitle().equals("My Languages"));
        System.out.println("======================================================");
        // 13- Select a random language from the dropdown menu.
        WebElement primaryLanguage=driver.findElement(By.id("default-locale-field"));
        Select select=new Select(primaryLanguage);
        select.selectByIndex((int)(Math.random()*5)+1);
        String randomlySelectedLanguage=select.getFirstSelectedOption().getText();
        System.out.println("Randomly Selected Language is: "+ randomlySelectedLanguage);
        // System.out.println("Step 13: Randomly Selected Language: "+ driver.findElement(By.id("default-locale-field")).getText());

        //14- Check and uncheck the boxes one by one. Verify that the box is checked and unchecked each time.
        List<WebElement> languages=driver.findElements(By.cssSelector("input[type='checkbox']"));
        System.out.println("Step14 A:Verify the each box is checked:");
        for (WebElement language:languages) {
            language.click();
            System.out.println("Checked language: "+ language.getAttribute("value")+ ", displayed language: " +language.isSelected());
        }
        System.out.println("======================================================");
        System.out.println("Step14 B:Verify the each box is unchecked:");
        for (WebElement language:languages) {
            language.click();
            System.out.println("Unchecked language: "+ language.getAttribute("value")+ ", displayed language: " +language.isSelected());
        }
        System.out.println("======================================================");
        //15- Check all the boxes and verify that they are all checked
        for (WebElement language:languages) {
            language.click();
            System.out.println("Step5:Checked language: "+ language.getAttribute("value")+ ", displayed language: " +language.isSelected());
        }
        System.out.println("======================================================");
        //16-Click on The "Save" button and verify error message is displayed.
        WebElement saveButton=driver.findElement(By.cssSelector("input[type='submit']"));
        saveButton.click();
        WebElement errorMessageAfterSave=driver.findElement(By.cssSelector("div>p"));
        System.out.println("Step16:Verify that error message is displayed: "+ errorMessageAfterSave.getText());




    }
}
