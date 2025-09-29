package com.quickbana.quickbana.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "levels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Levels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private int level_number;

    @Column(nullable = false)
    private int num_images;

    @Column(nullable = false)
    private int time_limit;

}
