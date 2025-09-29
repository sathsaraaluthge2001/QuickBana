package com.quickbana.quickbana.service;

import com.quickbana.quickbana.dto.LevelDTO;
import com.quickbana.quickbana.entity.Levels;

import java.util.List;

public interface LevelService {

    LevelDTO createLevel(Levels level);
    List<LevelDTO> getAllLevels();
    LevelDTO getLevelById(Long id);


}
