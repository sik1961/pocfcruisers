package com.sik.pocf.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class PredictedResult implements Comparable<PredictedResult> {
    private String leagueName;
    private Match match;
    private double positionDelta;
    private double l6formDelta;
    private double goalsForDelta;
    private double goalDiffDelta;
    private double overallDelta;
    private String overallResult;

    @Override
    public int compareTo(PredictedResult that) {
        return Double.compare(that.getOverallDelta(),this.overallDelta)==0 ?
                Double.compare(that.getL6formDelta(), this.l6formDelta) :
                Double.compare(that.getOverallDelta(),this.overallDelta);
    }

}
