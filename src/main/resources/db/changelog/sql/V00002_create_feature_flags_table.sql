CREATE TABLE feature_flags (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  flag_key VARCHAR(255) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  flag_status VARCHAR(50) NOT NULL,
  default_value BOOLEAN,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  managed_by VARCHAR(255)
);

CREATE INDEX idx_flag_key ON feature_flags(flag_key);