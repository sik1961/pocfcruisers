package com.sik.pocf.core;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ShipEvent {
    private Integer id;
    private String dayOfWeek;
    private LocalDateTime dateTime;
    private String vesselName;
    private String vesselCompany;
    private Boolean arrival;
    private Boolean departure;
    private String berth;
    private Integer grossTonnage;
    private Integer length;
    private Integer pax;
    private Integer crew;
    private String language;
    //private String fromPort;

}
