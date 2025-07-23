package com.event.api.controllers;

import com.event.api.domain.records.request.CouponRequestDTO;
import com.event.api.domain.records.response.CouponResponseDTO;
import com.event.api.services.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "Coupon operations")
public class CouponController extends BaseController {

    private final CouponService couponService;

    @Operation(
            summary = "Create coupon", description = "Creating a coupon", parameters = {
                @Parameter(name = "eventId", description = "Id of event", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string"))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "data of coupon",
                required = true,
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponRequestDTO.class))
            ),
            responses = {
                @ApiResponse(responseCode = "200", description = "Coupon created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponRequestDTO.class))),
                @ApiResponse(responseCode = "500", description = "Unexpected error")
            }
    )
    @PostMapping("/event/{eventId}")
    public ResponseEntity<CouponResponseDTO> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data) {
        return ResponseEntity.ok(couponService.addCouponToEvent(eventId, data));
    }
}
