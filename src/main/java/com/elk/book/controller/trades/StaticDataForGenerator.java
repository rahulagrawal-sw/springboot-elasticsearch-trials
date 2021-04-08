package com.elk.book.controller.trades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface StaticDataForGenerator {

    Random random = new Random();

    static String randomPTSSystem() {
        List<String> list = new ArrayList<String>();
        list.add("MUREX");
        list.add("SOPHIS");
        list.add("CALYPSO");
        list.add("MNS");
        list.add("TRADEMESYS");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }

    static String randomTradeVersion() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }

    static String randomCounterPartyCode() {
        List<String> list = new ArrayList<String>();
        list.add("SBI");
        list.add("ICICI");
        list.add("OBC");
        list.add("ONB");
        list.add("UNION");
        list.add("UNION");
        list.add("HDFC");
        list.add("BAJAJFINANCE");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }

    static String randomTraderId() {
        List<String> list = new ArrayList<String>();
        list.add("1234");
        list.add("2345");
        list.add("5678");
        list.add("3456");
        list.add("4567");
        list.add("6789");
        list.add("6779");
        list.add("1779");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }


    static String randomSupervisorId() {
        List<String> list = new ArrayList<String>();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        list.add("4444");
        list.add("5555");
        list.add("6666");
        list.add("7777");
        list.add("8888");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }

    static String randomDeskCode() {
        List<String> list = new ArrayList<String>();
        list.add("DESK1");
        list.add("DESK2");
        list.add("DESK3");
        list.add("DESK4");
        list.add("DESK5");
        list.add("DESK6");
        list.add("DESK7");
        list.add("DESK8");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }


    static String randomBusinessLineCode() {
        List<String> list = new ArrayList<String>();
        list.add("BL_1111");
        list.add("BL_2222");
        list.add("BL_3333");
        list.add("BL_4444");
        list.add("BL_5555");
        list.add("BL_6666");
        list.add("BL_7777");
        list.add("BL_8888");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }

    static String randomKRI() {
        List<String> list = new ArrayList<String>();
        list.add("DUMMY_COUNTERPARTY");
        list.add("LATE_TRADE");
        list.add("MULTIPLE_AMEND");
        list.add("SAME_DAY_CANCEL");
        list.add("PNL_LIMIT_BREACHED");
        list.add("DESK_LIMIT_BREACHED");
        list.add("TRADER_LEFT");
        list.add("HIGH_NOTIONAL_AMT");

        String randomStr = list.get(random.nextInt(list.size()));
        return randomStr;
    }

    static double randomNotionalAmt() {
        double rangeMin = 10000d;
        double rangeMax = 90000d;

        double randomValue = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        return randomValue;
    }

    static LocalDate randomTradeDate() {
        int year = random.nextInt(21) + 2000;
        int month = random.nextInt(11) + 1;
        int day = random.nextInt(26) + 1;

        return LocalDate.of(year, month, day);
    }
}
