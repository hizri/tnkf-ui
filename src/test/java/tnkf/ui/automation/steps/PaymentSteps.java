package tnkf.ui.automation.steps;

import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import tnkf.ui.automation.basics.BaseSteps;
import tnkf.ui.automation.models.PaymentModelMoscow;
import tnkf.ui.automation.pages.HousingPaymentsPage;
import tnkf.ui.automation.pages.PaymentsPage;
import tnkf.ui.automation.pages.ProviderPaymentFormPage;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentSteps extends BaseSteps {

    private PaymentsPage paymentsPage;
    private HousingPaymentsPage housingPaymentsPage;
    private ProviderPaymentFormPage providerPaymentFormPage;

    @Step
    public void switch_to_moscow_region() {
        paymentsPage.switchToMoscowRegion();
    }

    @Step
    public void switch_to_st_peterburg_region() {
        paymentsPage.switchToStPetersburgRegion();
    }

    @Step
    public void move_to_payments_page() {
        paymentsPage.moveToPaymentsPage();
    }

    @Step
    public void move_to_housing_payments_page() {
        paymentsPage.moveToHousingPaymentsPage();
    }

    @Step
    public WebElement get_payment_provider_link_by_index(int index) {
        return housingPaymentsPage.getPaymentProviderLinkByIndex(index);
    }

    @Step
    public void open_provider_payment_page_by_index(int index) {
        housingPaymentsPage.openProviderPaymentPageByIndex(index);
    }

    @Step
    public void search_provider_by_name(String name) {
        housingPaymentsPage.searchProviderByName(name);
    }

    @Step
    public void open_suggested_provider_by_index(int index) {
        housingPaymentsPage.openSuggestionByIndex(index);
    }

    @Step
    public void switch_to_payment_tab() {
        housingPaymentsPage.switchToTabByIndex(1);
        housingPaymentsPage.waitForRenderedElements(By.cssSelector("div[data-qa-file=Container]:not([class*=Hidden]) #period"));
    }

    @Step
    public void fill_payment_form(PaymentModelMoscow data) {
        providerPaymentFormPage.fillPaymentFields(data);
    }

    @Step
    public void fill_payment_period(String value) {
        providerPaymentFormPage.fillProviderPaymentPeriod(value);
    }

    @Step
    public void fill_payment_amount(String amount) {
        providerPaymentFormPage.fillPaymentAmount(amount);
    }

    @Step
    public void fill_payer_code(long payerCode) {
        providerPaymentFormPage.fillPayerCode(payerCode);
    }

    @Step
    public void submit_payment_form() {
        providerPaymentFormPage.submitPayment();
    }

    // region CHECKER STEPS

    @Step
    public void check_current_page_is_payments() {
        assertThat(paymentsPage.isPaymentsPage()).isTrue();
    }

    @Step
    public void check_search_suggestion_accuracy() {
        assertThat(housingPaymentsPage.checkSearchSuggestionAccuracy());
    }

    @Step
    public void check_current_page_is_housing_payment() {
        assertThat(housingPaymentsPage.isHousingPaymentPage()).isTrue();
    }

    @Step
    public void check_empty_search_result() {
        assertThat(housingPaymentsPage.isEmptySearchResult()).isTrue();
    }

    @Step
    public void check_empty_required_validation_fails() {
        assertThat(providerPaymentFormPage.requiredFieldsEmptyErrors()).isTrue();
    }

    @Step
    public void check_payer_code_has_invalid_value() {
        assertThat(providerPaymentFormPage.payerCodeHasInvalidValue()).isTrue();
    }

    @Step
    public void check_payment_period_has_invalid_value() {
        assertThat(providerPaymentFormPage.paymentPeriodHasInvalidValue()).isTrue();
    }

    @Step
    public void check_payment_amount_less_than_possible() {
        assertThat(providerPaymentFormPage.paymentAmountLessThanPossible()).isTrue();
    }

    @Step
    public void check_payment_amount_more_than_possible() {
        assertThat(providerPaymentFormPage.paymentAmountMoreThanPossible()).isTrue();
    }

    @Step
    public void check_payment_amount_wrong_value() {
        assertThat(providerPaymentFormPage.paymentAmountHasInvalidValue()).isTrue();
    }

    @Step
    public void check_provider_not_in_list(String providerName) {
        assertThat(providerPaymentFormPage.isProviderIsInList(providerName));
    }

    // endregion
}
