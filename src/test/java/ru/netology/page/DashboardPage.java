package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement buy = $(byText("Купить"));
    private final SelenideElement buyAloan = $(byText("Купить в кредит"));
    private final SelenideElement paymentCard = $(byText("Оплата по карте"));
    private final SelenideElement creditCard = $(byText("Кредит по данным карты"));

    // Оплата по карте
    public DashboardPage payBuyCard() {
        buy.click(); //нажимаем кнопку купить
        paymentCard.shouldBe(visible); // видим оплата по карте
        return new DashboardPage();
    }

    // Покупка в кредит
    public DashboardPage payCreditCard() {
        buyAloan.click(); //нажимаем кнопку Купить в кредит
        creditCard.shouldBe(visible); // видим Кредит по данным карты
        return new DashboardPage();
    }
}