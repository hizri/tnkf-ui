package tnkf.ui.automation.features;

import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import tnkf.ui.automation.basics.BaseSteps;
import tnkf.ui.automation.basics.BaseTest;
import tnkf.ui.automation.basics.BaseFactory;
import tnkf.ui.automation.factories.PaymentModelFactory;
import tnkf.ui.automation.models.PaymentModelMoscow;
import tnkf.ui.automation.steps.PaymentSteps;

public class TestScenario extends BaseTest {

    @Steps
    private BaseSteps baseSteps;

    @Steps
    private PaymentSteps paymentSteps;

    private PaymentModelMoscow data;

    @Before
    public void open_home_page() {
        data = PaymentModelFactory.randomRequired();
        baseSteps.open_home_page();
    }

    @Test
    public void test_scenario() {
        baseSteps.move_to_payments_page();
        paymentSteps.check_current_page_is_payments();
        paymentSteps.move_to_housing_payments_page();
        paymentSteps.check_current_page_is_housing_payment();
        paymentSteps.switch_to_moscow_region();
        String providerName = paymentSteps.get_payment_provider_link_by_index(0).getText();
        paymentSteps.open_provider_payment_page_by_index(0);
        String providerUrl = paymentSteps.get_current_url();
        paymentSteps.switch_to_payment_tab();

        // Validation
        // empty mandatory values
        paymentSteps.submit_payment_form();
        paymentSteps.check_empty_required_validation_fails();

        // Field: Payer Code
        paymentSteps.fill_payment_form(data);
        paymentSteps.fill_payer_code(BaseFactory.random.nextInt(999999999));
        paymentSteps.submit_payment_form();
        paymentSteps.check_payer_code_has_invalid_value();

        // Field: Payment period
        paymentSteps.fill_payment_form(data);
        paymentSteps.fill_payment_period("221988");
        paymentSteps.submit_payment_form();
        paymentSteps.check_payment_period_has_invalid_value();

        paymentSteps.fill_payment_period(String.valueOf(BaseFactory.random.nextInt(99999)));
        paymentSteps.submit_payment_form();
        paymentSteps.check_payment_period_has_invalid_value();

        // Field: Payment amount
        paymentSteps.fill_payment_form(data);
        paymentSteps.fill_payment_amount(String.valueOf(BaseFactory.random.nextInt(10)));
        paymentSteps.submit_payment_form();
        paymentSteps.check_payment_amount_less_than_possible();

        paymentSteps.fill_payment_amount(String.valueOf(15000 + BaseFactory.random.nextInt(999999)));
        paymentSteps.submit_payment_form();
        paymentSteps.check_payment_amount_more_than_possible();

        paymentSteps.fill_payment_amount("1 - (22 + 46)*4/2");
        paymentSteps.submit_payment_form();
        paymentSteps.check_payment_amount_wrong_value();

        // search saved provider
        paymentSteps.move_to_payments_page();
        paymentSteps.search_provider_by_name(providerName);
        paymentSteps.check_search_suggestion_accuracy();
        paymentSteps.open_suggested_provider_by_index(0);
        paymentSteps.check_current_url_matches_expected(providerUrl);

        // search saved provider in other region
        paymentSteps.move_to_payments_page();
        paymentSteps.move_to_housing_payments_page();
        paymentSteps.switch_to_st_peterburg_region();
        paymentSteps.check_provider_not_in_list(providerName);
        paymentSteps.search_provider_by_name(providerName);
        paymentSteps.check_empty_search_result();
    }

}
