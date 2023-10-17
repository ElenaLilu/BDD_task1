package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        val balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        val balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
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
        val balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
        val balanceAfterTransferFirstCard = DataHelper.balanceOfSecondCardAfterTransfer
                (balanceOfFirstCardBefore, amount);
        val balanceAfterTransferSecondCard = DataHelper.balanceOfFirstCardAfterTransfer
                (balanceOfSecondCardBefore, amount);
        val balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferMoreThanFirstCardBalance() {
        int amount = 20000;
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val transferPage = dashboardPage.secondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        transferPage.makeTransfer(amount, cardInfo);
    }
}
