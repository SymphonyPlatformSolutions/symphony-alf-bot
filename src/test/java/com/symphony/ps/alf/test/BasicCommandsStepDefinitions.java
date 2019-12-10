package com.symphony.ps.alf.test;

import io.cucumber.java8.En;
import model.OutboundMessage;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class BasicCommandsStepDefinitions extends AlfStepDefinitions implements En {
    String configType;
    String actualAnswer;

    public BasicCommandsStepDefinitions() {
        Given("there is a valid configuration for {string}", (String configType) -> {
            this.configType = configType;
        });

        When("a user types {string}", (String msgText) -> {
            imListener.onIMMessage(getInboundMessage("", msgText));

            verify(messagesClient).sendMessage(streamIdCaptor.capture(), msgCaptor.capture());
            OutboundMessage outMsg = msgCaptor.getValue();
            actualAnswer = outMsg.getMessage();
        });

        Then("the bot should reply with the help text",
            (String expectedAnswer) -> assertEquals(expectedAnswer, actualAnswer)
        );
    }
}
