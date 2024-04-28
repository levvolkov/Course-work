package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.lang.String.format;

public class DataHelper {

    // создание конструктора
    private DataHelper() {
    }

    public static final Faker faker = new Faker(new Locale("en"));

    // данные карты
    @Value
    public static class CardInfo {
        String cardnumber;
        String month;
        String year;
        String holder;
        String codcvccvv;
    }

    // заполнение поля "Номер карты"
    public static String getApprovedCardNumber() {
        return ("1111 2222 3333 4444");
    }

    public static String getDeclinedCardNumber() {
        return ("5555 6666 7777 8888");
    }

    // Заполнение поля "Номер карты" не валидными значениями
    public static CardInfo getCardNumberZero() {
        return new CardInfo("0000000000000000", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberLess() {
        return new CardInfo("2222222222", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberSymbol() {
        return new CardInfo("555555555?77777", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberCyrillic() {
        return new CardInfo("68123456789иии56", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberLatin() {
        return new CardInfo("787490124874SV88", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberEmpty() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    // Заполнение поля "Месяц"
    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return format("%02d", localDate.getMonthValue());
    }

    //Заполнение поля "Месяц" не валидными значениями
    public static CardInfo getMonth13() {
        return new CardInfo(getApprovedCardNumber(), "13", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthZero() {
        return new CardInfo(getApprovedCardNumber(), "0", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthSymbol() {
        return new CardInfo(getApprovedCardNumber(), "1!", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthLatin() {
        return new CardInfo(getApprovedCardNumber(), "F2", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthOneDigit() {
        return new CardInfo(getApprovedCardNumber(), "5", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthTwoZeros() {
        return new CardInfo(getApprovedCardNumber(), "00", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthEmpty() {
        return new CardInfo(getApprovedCardNumber(), "", getValidYear(), getValidHolder(), getValidCVC());
    }

    // Заполнение поля "Год"
    public static String getValidYear() {
        return LocalDate.now().plusYears(0).format(DateTimeFormatter.ofPattern("yy"));
    }

    // Заполнение поля "Год" не валидными символами
    public static CardInfo getYearLessThanCurrent() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "23", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearMoreThanTheCurrentOne() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "34", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearSymbol() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "2+", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearLatin() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "dg", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "2", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "", getValidHolder(), getValidCVC());
    }

    // Заполнение поля "Владелец"
    public static String getValidHolder() {
        return faker.name().firstName();
    }

    // Заполнение поля "Владелец" не валидными значениями
    public static CardInfo getHolderCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Иван", getValidCVC());
    }

    public static CardInfo getHolderSymbol() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Ivan%", getValidCVC());
    }

    public static CardInfo getHolderDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "1van", getValidCVC());
    }

    public static CardInfo getHolderOneLetter() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "S", getValidCVC());
    }

    public static CardInfo getHolderMoreThan100() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Aaaaaaaaaaaaaaaaaaaaaaaaadddddddddddddddddddddddddlllllllllllllllllllllllllyyyyyyyyyyyyyyyyyyyyyyyyyeeeeeeeeee", getValidCVC());
    }

    public static CardInfo getHolderEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "", getValidCVC());
    }

    // Заполнение поля "CVC/CVV"

    public static String getValidCVC() {
        return ("333");
    }

    // Заполнение "CVC/CVV" не валидными значениями
    public static CardInfo getCVCSymbol() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "22%");
    }

    public static CardInfo getCVCLetter() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "22D");
    }

    public static CardInfo getCVConeDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "8");
    }

    public static CardInfo getCVCtwoDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "99");
    }

    public static CardInfo getCVC000() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "000");
    }

    public static CardInfo getCVCempty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "");
    }
}
