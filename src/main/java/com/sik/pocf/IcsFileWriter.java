package com.sik.pocf;

import com.sik.pocf.core.ShipEvent;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sik.pocf.core.Constants.*;

public class IcsFileWriter {

    private String version =    "VERSION:2.0\r\n";
    private String prodid =     "PRODID://Elara/lofy/tanare/delp/314sum2015//\r\n";
    private String calBegin =   "BEGIN:VCALENDAR\r\n";
    private String calEnd =     "END:VCALENDAR\r\n";
    private String eventBegin = "BEGIN:VEVENT\r\n";
    private String eventEnd =   "END:VEVENT\r\n";

    private String uid_fmt = "UID:%s\r\n";
    private String dtstamp_fmt = "DTSTAMP:%s\r\n";
    private String organizer_fmt = "ORGANISER:%s\r\n";
    private String dtstart_fmt = "DTSTART:%s\r\n";
    private String dtend_fmt = "DTEND:%s\r\n";
    private String summary_fmt = "SUMMARY:%s\r\n";


    public void writeEventsToIcsFile(List<ShipEvent> shipEvents, String icsFile ){
        StringBuilder builder = new StringBuilder();
        builder.append(icsFile);
        builder.append(".ics");

        try {

            File file = new File(builder.toString());

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(calBegin);
            bw.write(version);
            bw.write(prodid);
            int evtCount=0;
            for (ShipEvent evt : shipEvents) {

                bw.write(eventBegin);
                bw.write(String.format(uid_fmt, UUID.randomUUID()));
                bw.write(String.format(dtstamp_fmt, LocalDateTime.now().format(ICS_DATE_FMT)));
                bw.write(String.format(organizer_fmt, "/com/sik/si/kusners/"));
                bw.write(String.format(dtstart_fmt, evt.getDateTime().format(ICS_DATE_FMT)));
                bw.write(String.format(dtend_fmt, evt.getDateTime().plusMinutes(15L).format(ICS_DATE_FMT)));
                bw.write(String.format(summary_fmt, evt.getVesselName().trim() + " (" + evt.getVesselCompany().trim() + ") " + (evt.getArrival()?"Arrives":"Departs")));
                bw.write(eventEnd);
                evtCount++;

            }


            bw.write(calEnd);

            bw.close();

            System.out.println("Done - " + evtCount + " events written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
