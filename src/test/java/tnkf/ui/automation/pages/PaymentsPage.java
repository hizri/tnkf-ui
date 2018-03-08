package tnkf.ui.automation.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import tnkf.ui.automation.basics.BasePage;

import java.util.ArrayList;

@DefaultUrl("/payments/")
public class PaymentsPage extends BasePage {

    public void switchToMoscowRegion() {
        WebElement regionLink = highlightFind(By.cssSelector("span[role=button][data-qa-file=PaymentsCatalogHeader] [data-qa-file=Link]"));
        if (!regionLink.getText().equalsIgnoreCase("Москве")) {
            highlightClick(regionLink);
            waitForRenderedElements(By.cssSelector("[data-qa-file=UIPopupRegions]"));
            switchToRegionByIndex(0);
        }
    }

    public void switchToStPetersburgRegion() {
        WebElement regionLink = highlightFind(By.cssSelector("span[role=button][data-qa-file=PaymentsCatalogHeader] [data-qa-file=Link]"));
        if (!regionLink.getText().equalsIgnoreCase("Санкт-Петербурге")) {
            highlightClick(regionLink);
            waitForRenderedElements(By.cssSelector("[data-qa-file=UIPopupRegions]"));
            switchToRegionByIndex(1);
        }
    }

    public void moveToHousingPaymentsPage() {
        highlightClick(By.cssSelector("[data-qa-file=Housing][data-qa-node=path]"));
        waitForRenderedElements(By.cssSelector("[data-qa-file=PaymentsProvidersPage]"));
    }

    @Override
    public void moveToPaymentsPage() {
        highlightClick(By.cssSelector("#mainMenu li[data-qa-file=HeaderMenuItem] a[href=\"/payments/\"]"));
        waitForRenderedElementsToBePresent(By.cssSelector("[data-qa-file=PaymentsPage]"));
    }

    public boolean isPaymentsPage() {
        ArrayList<Boolean> result = new ArrayList<>();
        result.add(highlightFind(By.cssSelector("[data-qa-file=PaymentsPageMenu]")).isDisplayed());
        result.add(highlightFind(By.cssSelector("[data-qa-file=FormFieldSearchAndPay]")).isDisplayed());
        return result.stream().allMatch(p -> p);
    }

    private void switchToRegionByIndex(int index) {
        String selector =
                String.format("[data-qa-file=UIPopupRegions] div[class*=layout] div[class*=item]:nth-of-type(%s) a", index + 1);
        highlightClick(By.cssSelector(selector));
        waitForRenderedElementsToDisappear(By.cssSelector("[data-qa-file=UIPopupRegions]"));
    }
}
