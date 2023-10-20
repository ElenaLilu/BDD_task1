package ru.netology.test;

import lombok.val;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class CardsTest {
    @Test
    public void shouldTransferFromFirstCardToSecond() {
        int amount = 1000;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceOfFirstCardBefore = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = dashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.secondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        val balanceAfterTransferFirstCard = DataHelper.balanceOfSecondCardAfterTransfer
                (balanceOfSecondCardBefore, amount);
        val balanceAfterTransferSecondCard = DataHelper.balanceOfFirstCardAfterTransfer
                (balanceOfFirstCardBefore, amount);
        val balanceOfFirstCardAfter = dashboardPage.getCurrentBalanceOfSecondCard();
        val balanceOfSecondCardAfter = dashboardPage.getCurrentBalanceOfFirstCard();
        assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
    }

    @Test
    public void shouldTransferFromSecondCardToFirst() {
        int amount = 2000;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceOfFirstCardBefore = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = dashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        val balanceAfterTransferFirstCard = DataHelper.balanceOfSecondCardAfterTransfer
                (balanceOfFirstCardBefore, amount);
        val balanceAfterTransferSecondCard = DataHelper.balanceOfFirstCardAfterTransfer
                (balanceOfSecondCardBefore, amount);
        val balanceOfFirstCardAfter = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfter = dashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
    }


    @Test
    void shouldTransferMoreThanFirstCardBalance1() {
        int amount = 20000;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val transferPage = dashboardPage.secondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        transferPage.TransferFailed();
    }
}
