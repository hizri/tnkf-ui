package tnkf.ui.automation.basics;

import net.thucydides.core.annotations.Step;
import tnkf.ui.automation.pages.HomePage;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseSteps {

    private HomePage homePage;
    private BasePage basePage;

    @Step
    public void open_home_page() {
        homePage.open();
    }

    @Step
    public String get_current_url() {
        return basePage.getCurrentUrl();
    }

    @Step
    public void move_to_payments_page() {
        homePage.moveToPaymentsPage();
    }

    // region CHECKER STEPS

    @Step
    public void check_current_url_matches_expected(String providerUrl) {
        assertThat(basePage.getCurrentUrl()).isEqualToIgnoringCase(providerUrl);
    }

    // endregion
}
