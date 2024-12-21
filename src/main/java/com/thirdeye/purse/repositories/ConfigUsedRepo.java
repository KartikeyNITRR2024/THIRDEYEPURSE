package com.thirdeye.purse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thirdeye.purse.entity.ConfigUsed;

@Repository
public interface ConfigUsedRepo extends JpaRepository<ConfigUsed, Long> {
}
