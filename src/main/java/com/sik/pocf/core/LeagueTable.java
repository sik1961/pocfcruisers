package com.sik.pocf.core;

import lombok.*;

import java.util.Map;
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class LeagueTable {
    String leagueName;
    Map<Integer, ShipEvent> table;
    Double maxGoalsFor;
    Double maxGoalDiff;
}
