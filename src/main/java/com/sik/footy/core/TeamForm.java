package com.sik.footy.core;

import lombok.*;
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class TeamForm {
    private Integer position;
    private String teamName;
    private Integer gamesPlayed;
    private Integer gamesWon;
    private Integer gamesDrawn;
    private Integer gamesLost;
    private Integer goalsFor;
    private Integer goalsAgainst;
    private Integer goalDifference;
    private Integer points;
    private String lastSixForm;
    private EnhancedStats enhancedStats;

}
