package com.sik.footy;

import java.util.*;

import static com.sik.footy.Constants.FIXTURE_URL_MAP;
import static com.sik.footy.Constants.TABLE_URL_MAP;

public class FootyRunner {

    private static final String RESULT_FORMAT = "%20s %31s %3s %31s %5s %5s %5s %5s %5s %3s";
    private static final String DBLF = " %.2f";

    public static void main(String[] args) {
        TableManager tableManager = new TableManager();
        FixtureManager fixtureManager = new FixtureManager();
        FootyPredictor predictor = new FootyPredictor();

        List<LeagueTable> leagueTables = new ArrayList<>();
        for (String l: TABLE_URL_MAP.keySet()) {
            leagueTables.add(tableManager.build(l, TABLE_URL_MAP.get(l)));
        }

        //fixtureManager.build("championship", FIXTURE_URL_MAP.get("championship"));

        //tableManager.printTable(championship);

        List<PredictedResult> results = new ArrayList<>();

        for (LeagueTable table:leagueTables) {
            tableManager.printTable(table);
        }
        for (LeagueTable table:leagueTables) {
            results.addAll(predictor.predict(table));
        }

        //List<PredictedResult> orderedResults = new ArrayList<>(results);

        Collections.sort(results);
        System.out.println();
        System.out.println("Predicted results - Ordered Most HW -> Most AW");
        System.out.printf((RESULT_FORMAT) + "%n" ,"League", "Home Team", "", "Away Team", "PosΔ", "L6FΔ", "DifΔ", "ForΔ", "AvgΔ", "Res");
        System.out.printf((RESULT_FORMAT) + "%n" ,"------", "---------", "", "---------", "----", "----", "----", "----", "----", "---");
        for (PredictedResult result: results) {
            System.out.printf((RESULT_FORMAT) + "%n",
                    result.getLeagueName(),
                    result.getMatch().getHomeTeam().getTeamName(),
                    "v",
                    result.getMatch().getAwayTeam().getTeamName(),
                    String.format(DBLF, result.getPositionDelta()),
                    String.format(DBLF, result.getL6formDelta()),
                    String.format(DBLF, result.getGoalsForDelta()),
                    String.format(DBLF, result.getGoalDiffDelta()),
                    String.format(DBLF, result.getOverallDelta()),
                    result.getOverallResult());
        }

//        for (Integer k:championship.table.keySet()) {
//            System.out.println(championship.table.get(k));
//        }


    }
}