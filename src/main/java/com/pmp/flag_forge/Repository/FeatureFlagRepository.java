package com.pmp.flag_forge.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmp.flag_forge.Model.FeatureFlag.FeatureFlag;

@Repository
public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, UUID> {

    boolean existsByFlagKey(String flagKey);

    Optional<FeatureFlag> findByFlagKey(String flagKey);

    List<FeatureFlag> findByFlagKeyIn(List<String> flagKeys);
}
