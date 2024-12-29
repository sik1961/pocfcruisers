package com.sik.footy;

import java.util.ArrayList;
import java.util.List;

import static com.sik.footy.Constants.LEAGUE_URL_MAP;

public class FootyRunner {
    public static void main(String[] args) {
        TableManager tableManager = new TableManager();
        FootyPredictor predictor = new FootyPredictor();

        List<LeagueTable> leagueTables = new ArrayList<>();
        for (String l:LEAGUE_URL_MAP.keySet()) {
            leagueTables.add(tableManager.build(l, LEAGUE_URL_MAP.get(l)));
        }

        //tableManager.printTable(championship);

        for (LeagueTable t:leagueTables) {
            tableManager.printTable(t);
            predictor.predict(t);
        }

//        for (Integer k:championship.table.keySet()) {
//            System.out.println(championship.table.get(k));
//        }


    }
}