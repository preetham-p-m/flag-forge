CREATE TABLE flag_rules (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  feature_flag_id UUID NOT NULL,
  rule_type VARCHAR(50),
  rule_operator VARCHAR(50),
  rule_value VARCHAR(255),
  target_value BOOLEAN,
  rule_enabled BOOLEAN,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  CONSTRAINT fk_flag_rule_feature_flag FOREIGN KEY (feature_flag_id) REFERENCES feature_flags(id) ON DELETE CASCADE,
  CONSTRAINT uq_flag_rule_combination UNIQUE (
    feature_flag_id,
    rule_type,
    rule_operator,
    rule_value,
    target_value
  )
);

CREATE INDEX idx_flag_rules_type_value ON flag_rules(rule_type, rule_value);

CREATE INDEX idx_flag_rules_by_type_value_flag_id ON flag_rules(rule_type, rule_value, feature_flag_id);