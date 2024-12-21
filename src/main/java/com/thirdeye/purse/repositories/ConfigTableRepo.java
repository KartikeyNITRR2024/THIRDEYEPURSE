package com.thirdeye.purse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thirdeye.purse.entity.ConfigTable;


@Repository
public interface ConfigTableRepo extends JpaRepository<ConfigTable, Long> {
}