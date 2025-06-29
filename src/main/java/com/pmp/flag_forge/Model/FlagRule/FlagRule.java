package com.pmp.flag_forge.Model.FlagRule;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.pmp.flag_forge.Model.FeatureFlag.FeatureFlag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flag_rules", indexes = {
        @Index(name = "idx_flag_rules_type_value", columnList = "rule_type, rule_value"),
        @Index(name = "idx_flag_rules_by_type_value_flag_id", columnList = "rule_type, rule_value, feature_flag_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uq_flag_rule_combination", columnNames = {
                "feature_flag_id", "rule_type", "rule_operator", "rule_value", "target_value"
        })
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlagRule {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(updatable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_flag_id", nullable = false)
    private FeatureFlag featureFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type")
    private RuleType ruleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_operator")
    private RuleOperator ruleOperator;

    // Holds the value to match against, e.g., userId, tenantId, etc.
    @Column(name = "rule_value")
    private String ruleValue;

    // The value to set for the flag when the rule matches (true or false)
    @Column(name = "target_value")
    private Boolean targetValue;

    private Boolean ruleEnabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.ruleEnabled = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
