package tnkf.ui.automation.factories;

import tnkf.ui.automation.basics.BaseFactory;
import tnkf.ui.automation.models.PaymentModelMoscow;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PaymentModelFactory extends BaseFactory {

    public static PaymentModelMoscow randomRequired() {
        PaymentModelMoscow data = new PaymentModelMoscow();
        long generatedPayerCode = (long) (1000000000 + random.nextFloat() * 10000000000L);
        int paymentAmount = random.nextInt(14991) + 10;
        data.setPaymentPeriod(faker.date().between(faker.date().past(365, TimeUnit.DAYS), new Date()));
        data.setPayerCode(generatedPayerCode);
        data.setPaymentAmount(paymentAmount);
        return data;
    }

    public static PaymentModelMoscow randomAllFields() {
        PaymentModelMoscow data = randomRequired();
        int payment = data.getPaymentAmount();
        data.setInsuranceAmount(payment - random.nextInt(payment - 1));
        return data;
    }

}
