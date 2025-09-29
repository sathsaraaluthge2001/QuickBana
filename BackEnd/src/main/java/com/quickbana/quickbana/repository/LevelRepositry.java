package com.quickbana.quickbana.repository;

import com.quickbana.quickbana.entity.Levels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepositry extends JpaRepository<Levels,Long> {

}
