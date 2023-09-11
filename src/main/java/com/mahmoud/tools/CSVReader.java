package com.mahmoud.tools;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CSVReader {
    public static List<Map<String,String>> getDataFromCSV(String path) {
        List<Map<String,String>> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CSVReader.class.getResourceAsStream(path))));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord csvRecord : csvParser) {
                Map<String, String> map = new HashMap<String,String>(){{
                    put("searchColumn",csvRecord.get("searchColumn"));
                    put("searchText",csvRecord.get("searchText"));
                    put("returnColumn",csvRecord.get("returnColumn"));
                    put("expectedText",csvRecord.get("expectedText"));
                }};
                data.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
