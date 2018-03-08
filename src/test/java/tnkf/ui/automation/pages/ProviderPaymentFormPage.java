package tnkf.ui.automation.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import tnkf.ui.automation.models.PaymentModelMoscow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProviderPaymentFormPage extends HousingPaymentsPage {

    public void fillPaymentFields(PaymentModelMoscow data) {
        fillPayerCode(data.getPayerCode());
        fillProviderPaymentPeriod(data.getPaymentPeriod());
        fillPaymentAmount(String.valueOf(data.getPaymentAmount()));
    }

    public void submitPayment() {
        highlightClick(By.cssSelector("[data-qa-file=FormFieldComponent] button[data-qa-file=UIButton]"));
    }

    public void fillPayerCode(long payerCode) {
        _fillField(_getPayerCodeField(), String.valueOf(payerCode));
    }

    private void fillProviderPaymentPeriod(Date paymentPeriod) {
        if (paymentPeriod != null) {
            String dateValue = new SimpleDateFormat("MMyyyy").format(paymentPeriod);
            _fillField(_getPaymentPeriodField(), dateValue);
        } else {
            _clearFieldValue(_getPaymentPeriodField());
        }
    }

    public void fillProviderPaymentPeriod(String value) {
        _fillField(_getPaymentPeriodField(), value);
    }

    public void fillPaymentAmount(String paymentAmount) {
        _fillField(_getPaymentAmountField(), paymentAmount);
    }

    private void _fillField(WebElement element, String value) {
        _clearFieldValue(element);
        highlightType(element, value);
    }

    private void _clearFieldValue(WebElement element) {
        element.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT, Keys.END), Keys.DELETE);
    }

    // region REQUIRED FIELDS GETTERS

    private WebElement _getPayerCodeField() {
        return highlightFind(By.id("payerCode"));
    }

    private WebElement _getPaymentPeriodField() {
        return highlightFind(By.id("period"));
    }

    private WebElement _getPaymentAmountField() {
        return highlightFind(By.cssSelector("[data-qa-file=FormFieldSet] [data-qa-file=StatelessInput][data-qa-node=label] input"));
    }

    private String _getPaymentPeriodErrorText() {
        return highlightFind(
                By.xpath("//*[@name=\"provider-period\"]/ancestor::div[@data-qa-file=\"FormFieldWrapper\"]/child::div[@data-qa-file=\"UIFormRowError\"]")).getText();
    }

    private String _getPayerCodeErrorText() {
        return highlightFind(
                By.xpath("//*[@name=\"provider-payerCode\"]/ancestor::div[@data-qa-file=\"FormFieldWrapper\"]/child::div[@data-qa-file=\"UIFormRowError\"]")).getText();
    }

    private String _getPaymentAmountErrorText() {
        return highlightFind(By.cssSelector(".ui-form__fieldset_inline .ui-form-field-error-message_ui-form")).getText();
    }

    // endregion

    //  region CHECKERS

    public boolean requiredFieldsEmptyErrors() {
        List<WebElementFacade> requiredInputFields = highlightFindAll(
                By.cssSelector("[data-qa-file=UIPageFrame] [data-qa-file=UIForm] .ui-form__row_default-error-view-visible"));
        if (requiredInputFields.size() != 3) {
            return false;
        }
        for (WebElementFacade field:requiredInputFields) {
            if (!field.find(By.cssSelector("[data-qa-file=UIFormRowError]")).getText().equalsIgnoreCase("Поле обязательное")) {
                return false;
            }
        }
        return true;
    }

    public boolean payerCodeHasInvalidValue() {
        return _getPayerCodeErrorText().equalsIgnoreCase("Поле неправильно заполнено");
    }

    public boolean paymentPeriodHasInvalidValue() {
        return _getPaymentPeriodErrorText().equalsIgnoreCase("Поле заполнено некорректно");
    }

    public boolean paymentAmountLessThanPossible() {
        return _getPaymentAmountErrorText().equalsIgnoreCase("Минимальная сумма перевода — 10 \u20BD");
    }

    public boolean paymentAmountMoreThanPossible() {
        return _getPaymentAmountErrorText().equalsIgnoreCase("Максимальная сумма перевода — 15 000 \u20BD");
    }

    public boolean paymentAmountHasInvalidValue() {
        return _getPaymentAmountErrorText().equalsIgnoreCase("Поле заполнено неверно");
    }

    // endregion
}
