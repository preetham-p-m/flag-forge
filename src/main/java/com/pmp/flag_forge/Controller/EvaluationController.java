package com.pmp.flag_forge.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmp.flag_forge.Model.EvaluatedFlag;
import com.pmp.flag_forge.Service.EvaluationService;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/evaluations")
@AllArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @GetMapping("user/{userId}/flag/{flagId}")
    public ResponseEntity<EvaluatedFlag> getFlagsStatusByUserIdAndFlagId(
            @PathVariable UUID userId,
            @PathVariable UUID flagId) {
        var result = evaluationService.getFlagsStatusByUserIdAndFlagId(userId, flagId);
        return ResponseEntity.ok(result);
    }

}
