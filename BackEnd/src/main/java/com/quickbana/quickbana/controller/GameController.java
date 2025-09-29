package com.quickbana.quickbana.controller;

import com.quickbana.quickbana.dto.GameApiDTO;
import com.quickbana.quickbana.dto.GameDTO;
import com.quickbana.quickbana.dto.UserProgressDTO;
import com.quickbana.quickbana.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public GameDTO gameResultChecking(@RequestParam Long userId,
                                      @RequestParam Long levelId,
                                      @RequestParam int  solution,
                                      @RequestParam int answer,
                                      @RequestParam int count,
                                      @RequestParam int time) {
        logger.info("Received game result check request - userId: {}, levelId: {}, solution: {}, answer: {}, count: {}, time: {}",
                userId, levelId, solution, answer, count, time);

        return gameService.chekGameResult(userId, levelId,count,solution,answer,time);
    }

    @GetMapping("/url")
    public GameApiDTO urlFetching() {
        logger.info("Received game url fetcher"
              );

        return gameService.getUrl();
    }
}
