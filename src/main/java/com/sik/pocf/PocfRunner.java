package com.sik.pocf;

import com.sik.pocf.core.ShipEvent;
import com.sik.pocf.helpers.PocfHelper;

import java.util.List;

public class PocfRunner {

    public static void main(String[] args) {
        PocfHelper helper = new PocfHelper();
        IcsFileWriter icsFileWriter = new IcsFileWriter();
        List<ShipEvent> shipEvents = helper.getShipEventsFromCsv();
        for (ShipEvent e:shipEvents) {
            System.out.println(e);
        }
        icsFileWriter.writeEventsToIcsFile(shipEvents, "/Users/sik/cruisers");
    }
}