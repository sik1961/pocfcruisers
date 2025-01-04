package com.sik.footy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

import static java.lang.String.format;

public class FixtureManager {

    public Last6Helper last6Helper = new Last6Helper();

    private static final String FORM_FORMAT = "%2d %30s %2d %2d %2d %2d %2d %2d %3d %3d %6s %5.2f %5.2f %5.2f %5.2f";
    private static final String HEAD_FORMAT = "%2s %30s %2s %2s %2s %2s %2s %2s %3s %3s %6s %5s %5s %5s %5s";

    public List<Match> build(String leagueName, String tableUrl) {
        Document doc = null;
        Double maxGoalsFor = 0.0D;
        Double maxGoalDiff = 0.0D;

        try {
            doc = Jsoup.connect(tableUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //doc.stream().distinct().forEach(System.out::println);
        Element main = doc.getElementById("main-data");
        System.out.println(">>>>>>>>" + doc.html() + "<<<<<<<<<");
        Elements elements = doc.select("b");
        for (Element element : elements) {
            element.replaceWith(new TextNode(element.toString()));
        }


        String date = null;
        String h2;
        Elements versus;
        for (Element e:main.getAllElements()) {
            System.out.println("****" + e.className() + "=" + e.getElementsMatchingOwnText("Saturday"));
//            h2 = Jsoup.parse(e.html()).select("div:is(h2)").toString();
//            if (h2 != null) {
//                date = h2;
//                System.out.println("Date: " + date);
//            }
            versus = Jsoup.parse(e.html()).select("span:matchesOwn(versus)");
            //versus.forEach(Element::text);

            if (versus != null) {
                //System.out.println("Match: " + date + " " + versus);
            }


        }


        //System.out.println(main.html());
        //main.forEachNode( node -> {} );
        String x =  Jsoup.parse(main.html()).select("span:matchesOwn(versus)").toString();
        System.out.println(x);

//        Element table = doc.select("table").get(0); //select the first table.
//        Elements rows = table.select("tr");


//        for(Integer k:teamFormMap.keySet()) {
//            System.out.println(teamFormMap.get(k));
//        }
    return null;
    }

    public TeamForm getTeamFormByName(LeagueTable leagueTable, String teamName) {
        return leagueTable.getTable().values()
                .stream()
                .filter(teamForm -> teamForm.getTeamName().equals(teamName))
                .findFirst()
                .orElse(null);
    }

    public void printTable(LeagueTable leagueTable) {
        System.out.println("League Table: " + leagueTable.leagueName);
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
                    leagueTable.getTable().get(k).getTeamName(),
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


    }
}
