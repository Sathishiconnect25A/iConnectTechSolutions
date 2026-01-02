package com.example.damageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.damageEntity.DamageRecord;

@Repository
public interface DamageRepository extends JpaRepository<DamageRecord, Long> {
}
