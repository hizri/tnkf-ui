package tnkf.ui.automation.models;

import java.util.Date;

public class PaymentModelMoscow {
    private long payerCode;
    private Date paymentPeriod;
    private int insuranceAmount;
    private int paymentAmount;

    public long getPayerCode() {
        return payerCode;
    }

    public void setPayerCode(long payerCode) {
        this.payerCode = payerCode;
    }

    public Date getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(Date paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public int getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(int insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
