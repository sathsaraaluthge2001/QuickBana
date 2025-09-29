package com.quickbana.quickbana.dto;

import lombok.*;

import javax.persistence.Column;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LevelDTO {

    private Long id;
    private int level_number;
    private int num_images;
    private int time_limit;
}
