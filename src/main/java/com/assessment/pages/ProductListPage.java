package com.assessment.pages;

import com.assessment.utils.GestureUtils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductListPage extends BasePage {

    private final By visibleListItems = By.xpath("//android.widget.TextView");

    public ProductListPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean scrollToItem(String itemText) {
        By itemLocator = By.xpath("//android.widget.TextView[@content-desc='" + itemText + "'] | //android.widget.TextView[@text='" + itemText + "']");
        GestureUtils.scrollUntilVisible(driver, itemLocator, 10);
        return driver.findElement(itemLocator).isDisplayed();
    }

    public void clickItem(String itemText) {
        By itemLocator = By.xpath("//android.widget.TextView[@content-desc='" + itemText + "'] | //android.widget.TextView[@text='" + itemText + "']");
        GestureUtils.scrollUntilVisible(driver, itemLocator, 10);
        click(itemLocator);
    }

    public void longPressItem(String itemText) {
        By itemLocator = By.xpath("//android.widget.TextView[@content-desc='" + itemText + "'] | //android.widget.TextView[@text='" + itemText + "']");
        GestureUtils.scrollUntilVisible(driver, itemLocator, 5);
        WebElement element = driver.findElement(itemLocator);
        GestureUtils.longPress(driver, element, 1500);
    }

    public int getVisibleItemCount() {
        List<WebElement> items = driver.findElements(visibleListItems);
        return items.size();
    }

    public String getVisibleItemsFingerprint() {
        List<WebElement> items = driver.findElements(visibleListItems);
        StringBuilder sb = new StringBuilder();
        for (WebElement item : items) {
            sb.append(item.getText()).append("|");
        }
        return sb.toString();
    }

    public void performSingleSwipeUp() {
        GestureUtils.swipeUp(driver);
    }
}