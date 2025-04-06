package com.sik.pocfcruisers;

import com.sik.pocfcruisers.core.LeagueTable;
import com.sik.pocfcruisers.core.TeamForm;
import com.sik.pocfcruisers.helpers.pocfcruisersHelper;
import com.sik.pocfcruisers.helpers.Last6Helper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static com.sik.pocfcruisers.core.Constants.*;

public class TableManager {

    public Last6Helper last6Helper = new Last6Helper();
    public pocfcruisersHelper helper = new pocfcruisersHelper();

    private static final String FORM_FORMAT = "%2d %30s %2d %2d %2d %2d %2d %2d %3d %3d %6s %5.2f %5.2f %5.2f %5.2f";
    private static final String HEAD_FORMAT = "%2s %30s %2s %2s %2s %2s %2s %2s %3s %3s %6s %5s %5s %5s %5s";

    public LeagueTable build(String leagueName, String tableUrl) {
        Document doc = null;
        Double maxGoalsFor = 0.0D;
        Double maxGoalDiff = 0.0D;

        try {
            doc = Jsoup.connect(tableUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Element table = doc.select("table").get(I0); //select the first table.
        Elements rows = table.select("tr");

        Map<Integer, TeamForm> teamFormMap = new HashMap<>();

        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            Element row = rows.get(i);
            Elements cols = row.select("td");

            if (cols.size() == 10) {

                /**
                 * Fix problem caused by BBC merging position & teamname
                 */
                String posName = cols.get(I_POSTEAM).text();
                Integer pos = Integer.parseInt(cols.get(I_POSTEAM).text().split(" ")[I0]);
                String tName = cols.get(I_POSTEAM).text().substring(cols.get(I_POSTEAM).text().indexOf(" ")+1);

                TeamForm teamForm = TeamForm.builder()
                        .position(pos)
                        .teamName(tName)
                        .gamesPlayed(Integer.parseInt(cols.get(I_PLAYED).text()))
                        .gamesWon(Integer.parseInt(cols.get(I_WON).text()))
                        .gamesDrawn(Integer.parseInt(cols.get(I_DRAWN).text()))
                        .gamesLost(Integer.parseInt(cols.get(I_LOST).text()))
                        .goalsFor(Integer.parseInt(cols.get(I_GLSFR).text()))
                        .goalsAgainst(Integer.parseInt(cols.get(I_GLSAG).text()))
                        .goalDifference(Integer.parseInt(cols.get(I_GLDIF).text()))
                        .points(Integer.parseInt(cols.get(I_POINTS).text()))
                        .lastSixForm(last6Helper.squash(cols.get(I_FORM).text()))
                        .enhancedStats(last6Helper.getEnhancedStats(cols))
                        .build();

                teamFormMap.put(pos, teamForm);
                if (Integer.parseInt(cols.get(I_GLSFR).text())>maxGoalsFor) {
                    maxGoalsFor = Double.parseDouble(cols.get(I_GLSFR).text());
                }
                if (Integer.parseInt(cols.get(I_GLDIF).text())>maxGoalDiff) {
                    maxGoalDiff = Double.parseDouble(cols.get(I_GLDIF).text());
                }
            } else {
                throw new IllegalStateException("Column count <> 10 - #cols={}" + cols.size());
            }

        }
    return LeagueTable.builder()
            .leagueName(leagueName)
            .table(teamFormMap)
            .maxGoalsFor(maxGoalsFor)
            .maxGoalDiff(maxGoalDiff)
            .build();
    }

    public TeamForm getTeamFormByName(LeagueTable leagueTable, String teamName) {
        return leagueTable.getTable().values()
                .stream()
                .filter(teamForm -> teamForm.getTeamName().equals(teamName))
                .findFirst()
                .orElse(null);
    }

    public void printTable(LeagueTable leagueTable) {
        System.out.println("League Table: " + leagueTable.getLeagueName());
        System.out.println("---------------------------------------------------");
        System.out.println(format(HEAD_FORMAT,
                "PN",
                "Team",
                "Pl",
                "W",
                "D",
                "L",
                "GF",
                "GA",
                "GD",
                "Pts",
                "Last6",
                "AGF",
                "AGA",
                "AGD",
                "L6F"));
        for(Integer k:leagueTable.getTable().keySet()) {
            System.out.println(format(FORM_FORMAT,
                    leagueTable.getTable().get(k).getPosition(),
                    leagueTable.getTable().get(k).getTeamName() + "(" + helper.abbreviatedName(leagueTable.getTable().get(k).getTeamName()) + ")",
                    leagueTable.getTable().get(k).getGamesPlayed(),
                    leagueTable.getTable().get(k).getGamesWon(),
                    leagueTable.getTable().get(k).getGamesDrawn(),
                    leagueTable.getTable().get(k).getGamesLost(),
                    leagueTable.getTable().get(k).getGoalsFor(),
                    leagueTable.getTable().get(k).getGoalsAgainst(),
                    leagueTable.getTable().get(k).getGoalDifference(),
                    leagueTable.getTable().get(k).getPoints(),
                    leagueTable.getTable().get(k).getLastSixForm(),
                    leagueTable.getTable().get(k).getEnhancedStats().getAverageGoalsFor(),
                    leagueTable.getTable().get(k).getEnhancedStats().getAverageGoalsAgainst(),
                    leagueTable.getTable().get(k).getEnhancedStats().getAverageGoalDifference(),
                    leagueTable.getTable().get(k).getEnhancedStats().getLastSixFormFactor()));
        }
        System.out.println("Max Goal Difference:" + leagueTable.getMaxGoalDiff() + " Max Goals For: " + leagueTable.getMaxGoalsFor());
        System.out.println();
    }
}
