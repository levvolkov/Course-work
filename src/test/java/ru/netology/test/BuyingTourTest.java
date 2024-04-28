package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class BuyingTourTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openSource() {
        open("http://localhost:8080");
        SQLHelper.deleteTable();
    }

    @Test
    @DisplayName("Покупка APPROVED карта")
    void theCardPaymentMustBeApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(cardinfo);
        form.paymentSuccessfull();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Покупка DECLINED кредитной картой")
    void theCardPaymentMustBeDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.BuyCreditCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(cardinfo);
        form.declinedPayment();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Номер карты из одних нулей ")
    public void isZeroCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCardNumberZero());
        form.declinedPayment();
    }

    @Test
    @DisplayName("Ввод менее 16 цифр в поле номер карты")
    public void lessCard16() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCardNumberLess());
        form.invalidCardFormat();
    }

    @Test
    @DisplayName("Ввод в поле номер карты цифр и спецсимвола")
    public void symbolCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCardNumberSymbol());
        form.invalidCardFormat();
    }

    @Test
    @DisplayName("Ввод в поле номер карты цифр и кириллицы")
    public void cyrillicCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCardNumberCyrillic());
        form.invalidCardFormat();
    }

    @Test
    @DisplayName("Ввод в поле номер карты цифр и латиницы")
    public void latinCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCardNumberLatin());
        form.invalidCardFormat();
    }

    @Test
    @DisplayName("Незаполненный номер карты")
    public void emptyCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCardNumberEmpty());
        form.invalidCardFormat();
    }

    @Test
    @DisplayName("Поле месяц более 12 ")
    public void moreThanMonth12() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getMonth13());
        form.invalidCardExpirationDate();
    }

    @Test
    @DisplayName("Ноль в поле месяц")
    public void zeroMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getMonthZero());
        form.monthNotValid();
    }

    @Test
    @DisplayName("Ввод в поле месяц цифры и спецсимвола")
    public void simbolMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getMonthSymbol());
        form.monthNotValid();
    }

    @Test
    @DisplayName("Ввод в поле месяц цифры и буквы")
    public void letterMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getMonthLatin());
        form.monthNotValid();
    }

    @Test
    @DisplayName("Одна цифра в поле месяц")
    public void oneDigitMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getMonthOneDigit());
        form.monthNotValid();
    }

    @Test
    @DisplayName("Два нуля в поле месяц")
    public void twoZerosMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getMonthTwoZeros());
        form.monthNotValid();
    }

    @Test
    @DisplayName("Поле месяц не заполнено")
    public void emptyMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getMonthEmpty());
        form.monthNotValid();
    }

    @Test
    @DisplayName("Год меньше текущего на 1 год")
    public void lessThanCurrentOneYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getYearLessThanCurrent());
        form.theСardExpired();
    }

    @Test
    @DisplayName("Год больше текущего на 10 лет")
    public void yearLongerThanTheCurrent() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getYearMoreThanTheCurrentOne());
        form.invalidCardExpirationDate();
    }

    @Test
    @DisplayName("Ввод в поле год цифры и спецсимволов")
    public void symbolYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getYearSymbol());
        form.yearNotValid();
    }

    @Test
    @DisplayName("Ввод в поле год цифры и буквы")
    public void letterYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getYearLatin());
        form.yearNotValid();
    }

    @Test
    @DisplayName("Поле год одна цифра")
    public void oneDigitYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getYearOneDigit());
        form.yearNotValid();
    }

    @Test
    @DisplayName("Поле год не заполнено")
    public void notValidEmptyYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getYearEmpty());
        form.yearNotValid();
    }

    @Test
    @DisplayName("Поле владелец на кириллице")
    public void cyrillicInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getHolderCyrillic());
        form.ownerNotValid();
    }

    @Test
    @DisplayName("Спецсимвол в поле владелец")
    public void symbolInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getHolderSymbol());
        form.ownerNotValid();
    }

    @Test
    @DisplayName("Цифра в поле владелец")
    public void digitInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getHolderDigit());
        form.ownerNotValid();
    }

    @Test
    @DisplayName("Одна буква в поле владелец")
    public void oneLetterInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getHolderOneLetter());
        form.ownerNotValid();
    }

    @Test
    @DisplayName("Более 100 букв в поле владелец")
    public void moreThan100lettersPerHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getHolderMoreThan100());
        form.ownerNotValid();
    }

    @Test
    @DisplayName("Незаполненное поле владелец")
    public void emptyInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getHolderEmpty());
        form.ownerNotValid();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV цифры и спецсимвола")
    public void symbolInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCVCSymbol());
        form.cvcNotValid();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV цифры и буквы")
    public void lettersInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCVCLetter());
        form.cvcNotValid();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV одной цифры")
    public void oneDigitInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCVConeDigit());
        form.cvcNotValid();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV двух цифр")
    public void twoDigitInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCVCtwoDigit());
        form.cvcNotValid();
    }

    @Test
    @DisplayName("CVC/CVV заполнен нулями")
    public void filledWidthZeroInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCVC000());
        form.cvcNotValid();
    }

    @Test
    @DisplayName("Незаполненное поле CVC/CVV")
    public void emptyInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.fillingOutTheForm(DataHelper.getCVCempty());
        form.ownerNotValid();
    }

    @Test
    @DisplayName("Незаполненная форма")
    void sendAnEmptyPurchaseForm() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PaymentPage();
        form.emptyform();
    }
}
