package com.banking.banking_app_backend.card.controller;

import com.banking.banking_app_backend.card.dto.request.CardInsertRequest;
import com.banking.banking_app_backend.card.dto.response.CardResponse;
import com.banking.banking_app_backend.card.service.CardService;
import com.banking.banking_app_backend.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CardResponse>>> getAllCards(){
        List<CardResponse> response = cardService.getAllCards();


        return ResponseEntity.ok(
                ApiResponse.success(response, "Fetched cards successfully")
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CardResponse>> insertCard(@RequestBody @Valid CardInsertRequest request){
        CardResponse response = cardService.insertCard(request);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Card created successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CardResponse>> getCard(@PathVariable Long id){
        CardResponse response = cardService.getCard(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Fetched card successfully")
        );
    }

    @PatchMapping("/{id}/freeze")
    public ResponseEntity<ApiResponse<CardResponse>> handleFreeze(@PathVariable Long id){
        CardResponse response = cardService.handleFreeze(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Updated card status successfully")
        );
    }

    @PatchMapping("/{id}/unfreeze")
    public ResponseEntity<ApiResponse<CardResponse>> handleUnfreeze(@PathVariable Long id){
        CardResponse response = cardService.handleUnfreeze(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Updated card status successfully")
        );
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<ApiResponse<CardResponse>> handleClose(@PathVariable Long id){
        CardResponse response = cardService.handleClose(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Updated card status successfully")
        );
    }

    @PatchMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<CardResponse>>> getCardsByAccountId(@PathVariable Long id){
        List<CardResponse> response = cardService.getCardsByAccountId(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Fetch cards by account successfully")
        );
    }

}
