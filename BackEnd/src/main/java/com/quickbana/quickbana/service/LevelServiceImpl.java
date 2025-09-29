package com.quickbana.quickbana.service;

import com.quickbana.quickbana.dto.LevelDTO;
import com.quickbana.quickbana.dto.UserDTO;
import com.quickbana.quickbana.entity.Levels;
import com.quickbana.quickbana.entity.User;
import com.quickbana.quickbana.exception.UserNotFoundException;
import com.quickbana.quickbana.repository.LevelRepositry;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService{

    private final LevelRepositry levelRepositry;

    public LevelServiceImpl(LevelRepositry levelRepositry) {
        this.levelRepositry = levelRepositry;
    }

    @Override
    public LevelDTO createLevel(Levels level) {
        Levels savedLevel =levelRepositry.save(level);
        return mapToDTO(savedLevel);
    }

    @Override
    public List<LevelDTO> getAllLevels() {
        List<Levels> levels=levelRepositry.findAll();
        return levels.stream().map(this::mapToDTO).collect(Collectors.toList());

    }

    @Override
    public LevelDTO getLevelById(Long id) {

        Levels level =levelRepositry.findById(id).orElseThrow(()->new UserNotFoundException("Leve not found"));
        return mapToDTO(level);
    }



    private LevelDTO mapToDTO(Levels savedLevel) {
        return LevelDTO.builder()
                .id(savedLevel.getId())
                .level_number(savedLevel.getLevel_number())
                .num_images(savedLevel.getNum_images())
                .time_limit(savedLevel.getTime_limit())
                .build();
    }

}
