package tnkf.ui.automation.basics;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BasePage extends PageObject {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    private static final EnvironmentVariables config = SystemEnvironmentVariables.createEnvironmentVariables();

    public String getCurrentUrl() {
        return this.getDriver().getCurrentUrl();
    }

    // region MAIN NAVIGATION METHODS

    public void moveToPaymentsPage() {
        highlightClick(By.cssSelector("#mainMenu li[data-qa-file=MenuItem] a[href=\"/payments/\"]"));
        waitForRenderedElementsToBePresent(By.cssSelector("[data-qa-file=PaymentsPage]"));
    }

    // endregion

    // region WebElement WORK METHODS

    protected WebElement highlightFind(By by) {
        logger.info("Find     |   By: {}  |", by.toString());
        WebElement element = find(by);
        highlight(element);
        return element;
    }

    protected List<WebElementFacade> highlightFindAll(By by) {
        logger.info("Find all |   By: {}  |", by.toString());
        List<WebElementFacade> elements = findAll(by);
        highlight(elements);
        return elements;
    }

    protected void highlightClick(By by) {
        logger.info("Click    |   By: {}  |", by.toString());
        WebElement element = find(by);
        highlight(element);
        element.click();
        waitJSLoaded();
    }

    protected void highlightClick(WebElement element) {
        logger.info("Click    |   By: {}  |", element.toString());
        highlight(element);
        element.click();
        waitJSLoaded();
    }

    protected void highlightType(By by, String keys) {
        WebElement element = highlightFind(by);
        element.sendKeys(keys);
    }

    protected void highlightType(WebElement element, String keys) {
        highlight(element);
        element.sendKeys(keys);
    }

    // endregion

    // region PRIVATE HELPER METHODS

    private void waitJSLoaded() {
        int waitJsPause = Integer.valueOf(config.getProperty("wait.js.pause", "100"));
        int waitJsTimeout = Integer.valueOf(config.getProperty("wait.js.timeout", "4000"));
        int times = waitJsTimeout / waitJsPause;
        for(int i = 0; i < times; i++) {
            boolean isJsReady = evaluateJavascript("return document.readyState").toString().equals("complete");
            if (isJsReady)
                return;
            waitPause(waitJsPause);
        }
    }

    private void setBorderStyle(WebElement element, String style) {
        evaluateJavascript("arguments[0].setAttribute('style', arguments[1]);", element, style);
    }


    private void highlight(WebElement element) {
        String borderStyle = "outline: 2px dashed red";
        setBorderStyle(element, borderStyle);
        waitPause(250);
        setBorderStyle(element, "");
    }

    private void highlight(List<WebElementFacade> elements) {
        String borderStyle = "outline: 2px dashed red";
        for (WebElementFacade element:elements) {
            setBorderStyle(element, borderStyle);
        }
        waitPause(250);
        for (WebElementFacade element:elements) {
            setBorderStyle(element, "");
        }
    }

    private void waitPause(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // endregion
}
