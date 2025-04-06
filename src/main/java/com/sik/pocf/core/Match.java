package com.sik.pocf.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Match {
    ShipEvent homeTeam;
    ShipEvent awayTeam;
}
