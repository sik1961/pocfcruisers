package com.sik.pocf.helpers;

import com.sik.pocf.core.ShipEvent;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.sik.pocf.core.Constants.*;

public class PocfHelper {

    public String abbreviatedName(String name) {
        String[] words = name.split(" ");
        if (words.length > 2) {
            return words[0].substring(0, 1).toUpperCase() + words[1].substring(0,1).toUpperCase() + words[2].substring(0,1).toUpperCase();
        } else if (words.length > 1) {
            return words[0].substring(0, 2).toUpperCase() + words[1].substring(0,1).toUpperCase();
        } else {
            return words[0].substring(0,3).toUpperCase();
        }
    }

    public List<ShipEvent> getShipEventsFromCsv() {
        Properties props = this.getPropsFromFile();
        List<ShipEvent> shipEvents = new ArrayList<>();



            try {
                BufferedReader br = new BufferedReader(new FileReader(props.getProperty("csvFile")));
                String line;
                while (true) {
                    if (!((line = br.readLine()) != null)) break;

                    //System.out.println(line);

                    String[] cols = line.split(",");
                    //System.out.println(">>>>>>>>>>>" + cols.length);

                    if (this.isRealData(cols)) {
                        if (!cols[I_ARRTIM].equalsIgnoreCase("n/a")) {
                            ShipEvent arrival = ShipEvent.builder()
                                    .id(Integer.valueOf(cols[I_ID]))
                                    .dayOfWeek(cols[I_DAY])
                                    .dateTime(this.buildDateTime(cols[I_DATE], cols[I_ARRTIM]))
                                    .arrival(true)
                                    .departure(false)
                                    .vesselName(cols[I_VESSEL])
                                    .vesselCompany(cols[I_COMPANY])
                                    .berth(cols[I_BERTH])
                                    .grossTonnage(Integer.valueOf(cols[I_GTONNS]))
                                    .length(Integer.valueOf(cols[I_LENGTH]))
                                    .pax(Integer.valueOf(cols[I_PAX]))
                                    .crew(Integer.valueOf(cols[I_CREW]))
                                    .language(cols.length>12?cols[I_LANG]:"")
                                    .fromPort(cols.length>13?cols[I_FROM]:"")
                                    .build();
                            shipEvents.add(arrival);
                        }
                        if (!cols[I_DEPTIM].equalsIgnoreCase("n/a")) {
                            ShipEvent departure = ShipEvent.builder()
                                    .id(Integer.valueOf(cols[I_ID]))
                                    .dayOfWeek(cols[I_DAY])
                                    .dateTime(this.buildDateTime(cols[I_DATE], cols[I_DEPTIM]))
                                    .arrival(false)
                                    .departure(true)
                                    .vesselName(cols[I_VESSEL])
                                    .vesselCompany(cols[I_COMPANY])
                                    .berth(cols[I_BERTH])
                                    .grossTonnage(Integer.valueOf(cols[I_GTONNS]))
                                    .length(Integer.valueOf(cols[I_LENGTH]))
                                    .pax(Integer.valueOf(cols[I_PAX]))
                                    .crew(Integer.valueOf(cols[I_CREW]))
                                    .language(cols.length>12?cols[I_LANG]:"")
                                    .fromPort(cols.length>13?cols[I_FROM]:"")
                                    .build();
                            shipEvents.add(departure);
                        }
                    }
                }

                } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        return shipEvents;
    }

    private boolean isRealData(String[] cols) {
        return (cols.length >= SHIP_EVT_MIN_LEN && !cols[I_ID].isEmpty());
    }

    private LocalDateTime buildDateTime(String date, String time) {
        return LocalDateTime.parse(date + time , CSV_DATE_FMT).minusHours(1L);
    }

    Properties getPropsFromFile() {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config.properties");
        try {
            props.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }


}




