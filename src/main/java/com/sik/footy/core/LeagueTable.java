package com.sik.pocfcruisers.core;

import lombok.*;

import java.util.Map;
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class LeagueTable {
    String leagueName;
    Map<Integer, TeamForm> table;
    Double maxGoalsFor;
    Double maxGoalDiff;
}
