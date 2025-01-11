package com.example.findlocal.controllers;

import com.example.findlocal.entity.UserOffering;
import com.example.findlocal.services.UserOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/profile/{profileId}/offering")
public class UserOfferingController {

    private UserOfferingService userOfferingService;

    @Autowired
    public UserOfferingController(UserOfferingService userOfferingService) {
        this.userOfferingService = userOfferingService;
    }

    @GetMapping
    public ResponseEntity<List<UserOffering>> getOfferings(@PathVariable Long profileId) {
        if (profileId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        List<UserOffering> offerings = userOfferingService.getOfferingsByProfileId(profileId);

        return ResponseEntity
                .ok(offerings);
    }


    @PostMapping
    public ResponseEntity<UserOffering> addOffering(@RequestBody UserOffering offering, @PathVariable Long profileId) {
        if (offering.getOffer() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserOffering createdOffering = userOfferingService.createOffering(offering, profileId);

        return ResponseEntity
                .ok(createdOffering);
    }
}
