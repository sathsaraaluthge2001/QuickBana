package com.quickbana.quickbana.service;

import com.quickbana.quickbana.dto.GameApiDTO;
import com.quickbana.quickbana.dto.GameDTO;

public interface GameService {

    GameDTO chekGameResult(Long userId,Long levelId,int count,int solution,int answer,int time);

    GameApiDTO getUrl();

}
