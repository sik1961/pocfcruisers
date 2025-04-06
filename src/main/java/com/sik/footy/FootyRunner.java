package com.sik.pocfcruisers;

import com.sik.pocfcruisers.core.LeagueTable;
import com.sik.pocfcruisers.core.PredictedResult;
import com.sik.pocfcruisers.helpers.pocfcruisersHelper;

import java.util.*;

import static com.sik.pocfcruisers.core.Constants.TABLE_URL_MAP;

public class pocfcruisersRunner {

    private static final String RESULT_FORMAT = "%20s %4s %3s %4s %5s %5s %5s %5s %5s %3s";
    private static final String DBLF = " %.2f";

    public static void main(String[] args) {
        TableManager tableManager = new TableManager();
        FixtureManager fixtureManager = new FixtureManager();
        pocfcruisersPredictor predictor = new pocfcruisersPredictor();
        pocfcruisersHelper helper = new pocfcruisersHelper();

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
        System.out.printf((RESULT_FORMAT) + "%n" ,"League", "Home", "", "Away", "PosΔ", "L6FΔ", "DifΔ", "ForΔ", "AvgΔ", "Res");
        System.out.printf((RESULT_FORMAT) + "%n" ,"------", "----", "", "----", "----", "----", "----", "----", "----", "---");
        for (PredictedResult result: results) {
            System.out.printf((RESULT_FORMAT) + "%n",
                    result.getLeagueName(),
                    helper.abbreviatedName(result.getMatch().getHomeTeam().getTeamName()),
                    "v",
                    helper.abbreviatedName(result.getMatch().getAwayTeam().getTeamName()),
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