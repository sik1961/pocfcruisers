package com.sik.footy.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Match {
    TeamForm homeTeam;
    TeamForm awayTeam;
}
