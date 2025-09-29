package com.quickbana.quickbana.service;


import com.quickbana.quickbana.dto.GameApiDTO;
import com.quickbana.quickbana.dto.GameDTO;
import com.quickbana.quickbana.dto.LevelDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private final UserProgressService userProgressService;
    private final LevelService levelService; // Inject LevelService
    private final OkHttpClient okHttpClient;

    public GameServiceImpl(UserProgressService userProgressService, LevelService levelService,OkHttpClient okHttpClient) {
        this.userProgressService = userProgressService;
        this.levelService = levelService;
        this.okHttpClient =okHttpClient;

    }

    @Override
    public GameDTO chekGameResult(Long userId, Long levelId, int count, int solution, int answer, int time) {
        logger.info("Checking game result for userId: {}, levelId: {}, count: {}, solution: {}, answer: {}, time: {}",
                userId, levelId, count, solution, answer, time);
int count1=0;
        GameDTO gameDTO = new GameDTO();

        try {
            LevelDTO levelDTO = levelService.getLevelById(levelId);
            int gameLevelCount = levelDTO.getNum_images();
            logger.info("Retrieved level data for levelId: {} - num_images: {}", levelId, gameLevelCount);

            if (solution == answer) {
                count=count+1;
                if (gameLevelCount == count) {
                    gameDTO.setResponse(true);
                    gameDTO.setFinish(true);
                    gameDTO.setCount(count);
                    userProgressService.insertProgress(userId, levelId, time, 100, true);
                    logger.info("User {} completed level {} successfully. Progress updated.", userId, levelId);
                } else {
                    gameDTO.setResponse(true);
                    gameDTO.setFinish(false);
                    gameDTO.setCount(count);
                    logger.info("User {} answered correctly but has not completed the level yet (count: {}).", userId, count1);
                }
            } else {
                gameDTO.setResponse(false);
                gameDTO.setFinish(false);
                logger.info("User {} provided an incorrect answer for level {}.", userId, levelId);
            }

        } catch (Exception e) {
            logger.error("Error occurred while checking game result for userId: {}, levelId: {}. Error: {}",
                    userId, levelId, e.getMessage(), e);
            throw e;
        }

        return gameDTO;
    }

    @Override
    public GameApiDTO getUrl() {
        String apiUrl = "https://marcconrad.com/uob/banana/api.php";
        //?out=json&base64=no
        GameApiDTO gameApiDTO = new GameApiDTO();

        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                logger.info("API Response: {}", responseBody);

                JSONObject jsonResponse = new JSONObject(responseBody);
                gameApiDTO.setUrl(jsonResponse.optString("question", ""));
                gameApiDTO.setSolution(jsonResponse.optString("solution", ""));

                logger.info("Successfully fetched game question: {}", gameApiDTO.getUrl());
                return gameApiDTO;
            } else {
                logger.error("Failed to fetch data from API. Status code: {}", response.code());
                return null;
            }
        } catch (IOException e) {
            logger.error("Error occurred while calling external API: {}", e.getMessage(), e);
            return null;
        }
    }

}
