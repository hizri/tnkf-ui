package tnkf.ui.automation.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@DefaultUrl("/payments/categories/kommunalnie-platezhi/")
public class HousingPaymentsPage extends PaymentsPage {

    public WebElement getPaymentProviderLinkByIndex(int index) {
        return getProvidersLinkByIndex(index);
    }

    public void openProviderPaymentPageByIndex(int index) {
        highlightClick(getProvidersLinkByIndex(index));
        waitForRenderedElements(By.cssSelector("[data-qa-file=UISearchProvider][data-qa-node=div]"));
    }

    public void searchProviderByName(String name) {
        highlightType(By.cssSelector("input[data-qa-file=SearchInput]"), name);
        waitForRenderedElements(By.cssSelector("[data-qa-file=SuggestBlock]"));
    }

    public boolean checkSearchSuggestionAccuracy() {
        String searchQuery = highlightFind(By.cssSelector("[data-qa-file=SearchInput]")).getAttribute("value");
        String firstSuggestEntry =
                highlightFind(By.cssSelector("[data-qa-file=SuggestEntry] [data-qa-file=Text][class*=size_17]")).getText();
        return firstSuggestEntry.equalsIgnoreCase(searchQuery);
    }

    public void openSuggestionByIndex(int index) {
        highlightClick(getSuggestionEntries().get(index));
    }

    public boolean isHousingPaymentPage() {
        List<Boolean> keyElements = new ArrayList<>();
        keyElements.add(highlightFind(By.cssSelector("[data-qa-file=PaymentsProvidersPage]")).isDisplayed());
        keyElements.add(!getAllProvidersLinks().isEmpty());
        return keyElements.stream().allMatch(p -> p);
    }

    public boolean isEmptySearchResult() {
        return highlightFind(By.cssSelector("[data-qa-file=SuggestEntry][class*=empty]")).isDisplayed();
    }

    public void switchToTabByIndex(int index) {
        String selector = String.format("[data-qa-file=TabContainer]:nth-child(%s)", index + 1);
        highlightClick(By.cssSelector(selector));
    }

    public boolean isProviderIsInList(String providerName) {
        List<WebElementFacade> providersLinks = getAllProvidersLinks();
        for (WebElement element:providersLinks) {
            if (element.getAttribute("title").equalsIgnoreCase(providerName)) {
                return true;
            }
        }
        return false;
    }

    private List<WebElementFacade> getSuggestionEntries() {
        return highlightFindAll(By.cssSelector("[data-qa-file=SuggestEntry] [data-qa-file=SuggestEntry]:first-child"));
    }

    private List<WebElementFacade> getAllProvidersLinks() {
        return highlightFindAll(By.cssSelector("[data-qa-file=UIMenuItemProvider] .ui-menu__link [data-qa-file=Link]"));
    }

    private WebElement getProvidersLinkByIndex(int index) {
        String selector =
                String.format("[data-qa-file=UIMenuItemProvider]:nth-of-type(%s) .ui-menu__link [data-qa-file=Link]", index + 1);
        return highlightFind(By.cssSelector(selector));
    }

}
