package com.sik.footy;

import java.util.ArrayList;
import java.util.List;

import static com.sik.footy.Constants.FIXTURE_URL_MAP;
import static com.sik.footy.Constants.TABLE_URL_MAP;

public class FootyRunner {
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

        for (LeagueTable t:leagueTables) {
            tableManager.printTable(t);
        }
        for (LeagueTable t:leagueTables) {
            predictor.predict(t);
        }

//        for (Integer k:championship.table.keySet()) {
//            System.out.println(championship.table.get(k));
//        }


    }
}