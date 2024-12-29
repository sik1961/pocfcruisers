package com.sik.footy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FootyPredictor {

    private static final String VERSUS = " versus ";
    private static final String KICK_OFF = " kick off ";
    private static final String V = ":";
    private static final String HW = "HW";
    private static final String AW = "AW";
    private static final String RESULT_FORMAT = "%30s %3s %30s %6s %1.2f %6s %1.2f %6s %1.2f %6s %1.2f";
    private static final String HEADER_FORMAT = "%30s %3s %30s %11s %11s %11s %11s";

    TableManager tableManager = new TableManager();

    public void predict(LeagueTable league) {

        TableManager tableManager = new TableManager();
        List<String> matchList = this.getMatchesFromFile(league.getLeagueName() + "-fixtures.txt");


//        for(Match m:matchList) {
//            System.out.println(m);
//        }
        System.out.println("Matches: " + league.getLeagueName());
        System.out.printf((HEADER_FORMAT) ,"Home Team", "", "Away Team", "Lg-PosΔ", "L6FormΔ", "GlDiffΔ", "GlsForΔ");
        System.out.println();
        List<Match> matches = this.buildMatches(league, matchList);
        for (Match m:matches) {
            System.out.printf((RESULT_FORMAT) ,
                    m.getHomeTeam().getTeamName(),
                    " v ",
                    m.getAwayTeam().getTeamName(),
                    m.getHomeTeam().getPosition() < m.getAwayTeam().getPosition()?HW:AW,
                    Math.abs((double) (m.getHomeTeam().getPosition() - m.getAwayTeam().getPosition()) /league.getTable().size()),
                    m.getHomeTeam().getEnhancedStats().getLastSixFormFactor() > m.getAwayTeam().getEnhancedStats().getLastSixFormFactor()?HW:AW,
                    Math.abs((double) m.getHomeTeam().getEnhancedStats().getLastSixFormFactor()-m.getAwayTeam().getEnhancedStats().getLastSixFormFactor()),
                    m.getHomeTeam().getEnhancedStats().getAverageGoalDifference() > m.getAwayTeam().getEnhancedStats().getAverageGoalDifference()?HW:AW,
                    Math.abs((double) m.getHomeTeam().getEnhancedStats().getAverageGoalDifference()-m.getAwayTeam().getEnhancedStats().getAverageGoalDifference())/league.getMaxGoalDiff(),
                    m.getHomeTeam().getEnhancedStats().getAverageGoalsFor() > m.getAwayTeam().getEnhancedStats().getAverageGoalsFor()?HW:AW,
                    Math.abs((double) m.getHomeTeam().getEnhancedStats().getAverageGoalsFor()-m.getAwayTeam().getEnhancedStats().getAverageGoalsFor())/league.getMaxGoalsFor());
            System.out.println();
        }

    }

    private List<Match> buildMatches(LeagueTable leagueTable, List<String> matchList) {
        List<Match> matches = new ArrayList<>();
        for(String m: matchList) {
            matches.add(new Match.MatchBuilder()
                    .homeTeam(tableManager.getTeamFormByName(leagueTable ,m.split(V)[0]))
                    .awayTeam(tableManager.getTeamFormByName(leagueTable,m.split(V)[1]))
                    .build());
        }
        return matches;
    }

    public List<String> getMatchesFromFile(String fixtureFile) {


        List<String> matchList = new ArrayList<>();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        List<String> fixtureList = new ArrayList<>();
        try (InputStream is = classLoader.getResourceAsStream(fixtureFile)) {
            if (is != null) {
                try (InputStreamReader isr = new InputStreamReader(is);
                     BufferedReader reader = new BufferedReader(isr)) {
                    fixtureList = reader.lines().filter(l -> l.contains(VERSUS)).collect(Collectors.toList());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(String f:fixtureList) {
            matchList.add(f.split(VERSUS)[0] + ":" + f.split(VERSUS)[1].split(KICK_OFF)[0]);
        }
        return matchList;
    }
}
