package com.sik.footy.helpers;

import com.sik.footy.core.Constants;
import com.sik.footy.core.EnhancedStats;
import org.jsoup.select.Elements;
import static com.sik.footy.core.Constants.*;

public class Last6Helper {
    public String squash(String input) {
        return input.replaceAll("Result","")
                .replaceAll("Win","")
                .replaceAll("Loss","")
                .replaceAll("Draw","")
                .replaceAll(" ","");
    }

    public EnhancedStats getEnhancedStats(Elements cols) {
        return EnhancedStats.builder()
                .averageGoalsFor(Double.parseDouble(cols.get(I_GLSFR).text())/Double.parseDouble(cols.get(I_PLAYED).text()))
                .averageGoalsAgainst(Double.parseDouble(cols.get(I_GLSAG).text())/Double.parseDouble(cols.get(I_PLAYED).text()))
                .averageGoalDifference(Double.parseDouble(cols.get(I_GLDIF).text())/Double.parseDouble(cols.get(I_PLAYED).text())-
                        Double.parseDouble(cols.get(I_GLDIF).text())/Double.parseDouble(cols.get(I_PLAYED).text()))
                .lastSixFormFactor(this.calculateFormFactor(this.squash(cols.get(I_FORM).text())))
                .build();
    }

    private Double calculateFormFactor(String wdlForm) {
        Double formFactor = 0.0D;
        if (wdlForm.length() == 6) {
            for (int i=0; i<6;i++ ) {
                if (wdlForm.substring(i,i+1).equalsIgnoreCase("W")) {
                    formFactor = formFactor + Constants.LAST_6_MULTIPLIERS.get(i);
                } else if (wdlForm.substring(i,i+1).equalsIgnoreCase("D")) {
                    formFactor = formFactor + (Constants.LAST_6_MULTIPLIERS.get(i)*0.33D);
                }
            }
            return formFactor/2;
        }
        return -1.0D;
    }
}
