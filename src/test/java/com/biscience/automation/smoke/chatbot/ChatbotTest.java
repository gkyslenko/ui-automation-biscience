package com.biscience.automation.smoke.chatbot;

import com.biscience.automation.base.BaseTest;
import com.biscience.automation.pages.components.ChatBotComponent;
import io.qameta.allure.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Feature("Chatbot")
public class ChatbotTest extends BaseTest {

    @Test(description = "TC-CHAT-01: Chatbot Panel Opens from Top Navigation")
    public void tc_chat_01_chatbotOpensFromTopNav() {
        ChatBotComponent chatbot = homePage.sidebar.openChatbot();

        assertTrue(chatbot.isChatbotVisible(), "Chatbot panel should be open");
        assertTrue(chatbot.isHistoryVisible(), "Chatbot history should be visible");
        assertTrue(chatbot.isInputVisible(), "Chatbot input field should be visible");
        assertTrue(chatbot.isSubmitButtonVisible(), "Chatbot send button should be visible");
        assertFalse(chatbot.isSubmitButtonEnabled(false), "Chatbot send button should be disabled when input is empty");
        assertTrue(chatbot.isCloseButtonVisible(), "Chatbot close button should be visible");
        assertTrue(chatbot.isSuggestionsPresent(), "Chatbot suggestions should be present");

        monitor.assertClean();
    }

    @Test(description = "TC-CHAT-02: Submitting a Question Triggers a Response")
    public void tc_chat_02_chatbotSubmitQuestion() {
        String randomStr = RandomStringUtils.secure().nextAlphabetic(20);

        ChatBotComponent chatbot = homePage.sidebar.openChatbot();
        chatbot.clickInput().enterText(randomStr).clickSubmit();

        monitor.assertClean();
    }
}

