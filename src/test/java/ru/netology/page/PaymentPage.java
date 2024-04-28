package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    private SelenideElement cardnumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement owner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvccvv = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continuebutton = $(byText("Продолжить"));

    private SelenideElement formatCardError = $(byText("Неверный формат")).parent().$(".input__sub");
    private SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement expiredCardError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement termCardError = $(byText("Истёк срок действия карты"));
    private SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");

    //заполнение формы
    public void fillingOutTheForm(DataHelper.CardInfo cardInfo) {
        cardnumber.setValue(cardInfo.getCardnumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        owner.setValue(cardInfo.getHolder());
        cvccvv.setValue(cardInfo.getCodcvccvv());
        continuebutton.click();
    }

    // не заполненная форма
    public void emptyform() {
        continuebutton.click();
        cardNumberError.shouldBe(visible);
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        ownerError.shouldBe(visible);
        cvcError.shouldBe(visible);
    }

    // успешная оплата
    public void paymentSuccessfull() {
        $(".notification_status_ok").shouldBe(Condition.visible, Duration.ofSeconds(30));
    }

    public void declinedPayment() {
        $(byCssSelector("div.notification.notification_status_error.notification_has-closer.notification_stick-to_right.notification_theme_alfa-on-white")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void invalidCardFormat() {
        formatCardError.shouldBe(visible);
    }

    public void invalidCardExpirationDate() {
        expiredCardError.shouldBe(visible);
    }

    public void theСardExpired() {
        termCardError.shouldBe(visible);
    }

    public void monthNotValid() {
        monthError.shouldBe(visible);
    }

    public void yearNotValid() {
        yearError.shouldBe(visible);
    }

    public void ownerNotValid() {
        ownerError.shouldBe(visible);
    }

    public void cvcNotValid() {
        cvcError.shouldBe(visible);
    }
}