package com.assessment.tests;

import com.assessment.pages.ProductListPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class GestureTests {

    private AndroidDriver driver;
    private ProductListPage productListPage;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");

        File app = new File("ApiDemos-debug.apk");
        if (app.exists()) {
            options.setApp(app.getAbsolutePath());
        }

        options.setAppPackage("io.appium.android.apis");
        options.setAppActivity("io.appium.android.apis.ApiDemos");
        options.setNoReset(false);

        driver = new AndroidDriver(URI.create("http://127.0.0.1:4723/").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        productListPage = new ProductListPage(driver);
    }

    @Test
    public void testScrollToSpecificItem() {
        boolean isDisplayed = productListPage.scrollToItem("Views");
        Assert.assertTrue(isDisplayed, "The target item 'Views' was not visible after scrolling.");
    }

    @Test
    public void testLongPressRevealMenu() {
        productListPage.longPressItem("Views");
        Assert.assertTrue(productListPage.getVisibleItemCount() > 0, "Elements should remain visible after long press interaction.");
    }

    @Test
    public void testItemCountBeforeAndAfterScroll() {
        // Step 1: Click into 'Views' sub-menu screen to load a long scrollable list
        productListPage.clickItem("Views");

        // Step 2: Capture initial fingerprint inside the Views list
        String initialFingerprint = productListPage.getVisibleItemsFingerprint();
        int initialCount = productListPage.getVisibleItemCount();

        // Step 3: Perform swipeUp action
        productListPage.performSingleSwipeUp();

        // Step 4: Capture updated state after scrolling
        String afterScrollFingerprint = productListPage.getVisibleItemsFingerprint();
        int afterScrollCount = productListPage.getVisibleItemCount();

        // Step 5: Execute assertions according to Task 1.3(c)
        Assert.assertTrue(initialCount > 0, "Initial item count should be greater than zero.");
        Assert.assertTrue(afterScrollCount > 0, "Item count after scroll should be greater than zero.");
        Assert.assertNotEquals(initialFingerprint, afterScrollFingerprint, "The visible set of list items should change after performing a swipeUp gesture.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}