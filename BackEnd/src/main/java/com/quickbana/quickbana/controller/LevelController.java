package com.quickbana.quickbana.controller;

import com.quickbana.quickbana.dto.LevelDTO;
import com.quickbana.quickbana.dto.UserDTO;
import com.quickbana.quickbana.entity.Levels;
import com.quickbana.quickbana.entity.User;
import com.quickbana.quickbana.service.LevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/levels")
public class LevelController {

    private final LevelService levelService;


    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @PostMapping
    public ResponseEntity<LevelDTO> createLevel(@RequestBody Levels levels) {
        return ResponseEntity.ok(levelService.createLevel(levels));
    }

    @GetMapping
    public ResponseEntity<List<LevelDTO>> getAllLevels() {
        return ResponseEntity.ok(levelService.getAllLevels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LevelDTO> findLevels(@RequestBody Long  id) {
        return ResponseEntity.ok(levelService.getLevelById(id));
    }





}
