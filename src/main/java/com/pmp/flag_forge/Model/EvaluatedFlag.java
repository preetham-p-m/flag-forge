package com.pmp.flag_forge.Model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluatedFlag {
    private UUID userId;
    private UUID customerId;
    private UUID flagId;
    private String flagKey;
    private Boolean flagValue;
}
