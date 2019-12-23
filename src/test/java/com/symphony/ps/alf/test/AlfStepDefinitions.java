package com.symphony.ps.alf.test;

import com.symphony.ps.alf.services.PermissionsService;
import io.cucumber.java8.En;
import model.User;

public class AlfStepDefinitions implements En {
    public AlfStepDefinitions() {
        Given("the user is set as a client user", () -> {
            PermissionsService.setClient(SymStepDefinitions.streamId, SymStepDefinitions.getSampleUser().getUserId());
        });

        Given("the user is not set as a client user", () -> {
            PermissionsService.removeClient(SymStepDefinitions.streamId, SymStepDefinitions.getSampleUser().getUserId());
        });
    }
}
