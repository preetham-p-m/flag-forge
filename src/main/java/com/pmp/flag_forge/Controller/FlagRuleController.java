package com.pmp.flag_forge.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.pmp.flag_forge.Model.FlagRule;
import com.pmp.flag_forge.Model.FlagRuleDto;
import com.pmp.flag_forge.Service.FlagRuleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/flag-rules")
public class FlagRuleController {
    private final FlagRuleService flagRuleService;

    @PostMapping
    public ResponseEntity<FlagRule> create(@RequestBody @Valid FlagRuleDto flagRuleDto) {
        var flagRule = flagRuleService.create(flagRuleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(flagRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        flagRuleService.deleteById(id);
        return ResponseEntity.ok("Flag Rule deleted successfully for id " + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlagRule> getById(@PathVariable UUID id) {
        var flagRule = flagRuleService.getById(id);
        return ResponseEntity.ok(flagRule);
    }

    @PatchMapping("/toggle/{id}")
    public ResponseEntity<FlagRule> toggleById(@PathVariable UUID id) {
        var flagRule = flagRuleService.toggleById(id);
        return ResponseEntity.ok(flagRule);
    }
}
