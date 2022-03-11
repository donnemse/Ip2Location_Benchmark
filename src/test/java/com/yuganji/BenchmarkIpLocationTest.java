package com.yuganji;

import com.google.common.collect.RangeMap;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.yuganji.IpLocation;
import com.yuganji.IpLocationData;
import com.yuganji.NetUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class BenchmarkIpLocationTest {

    private static RangeMap<Long, IpLocation> ipLocRangeMap;
    private static List<IpLocation> ipLocList;
    private static Map<String, String> sample;
    @BeforeAll
    static void init(){
        IpLocationData instance = new IpLocationData();
        ipLocList = instance.getIpLocList();
        ipLocRangeMap = instance.getIpRangeMap();
        sample = new LinkedHashMap<>();

        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser csvParser = new CsvParser(settings);

        ClassLoader classLoader = BenchmarkIpLocationTest.class.getClassLoader();
        File file = new File(classLoader.getResource("ip2loc_test_dataset.csv").getFile());
        System.out.println(file.getAbsolutePath());
        csvParser.beginParsing(file);
        Record r = null;
        while ((r = csvParser.parseNextRecord()) != null) {
            sample.put(r.getString("s_ip"), r.getString("s_country"));
        }
        csvParser.stopParsing();
    }

    @Test
    @DisplayName("BinarySearch")
    void ip2LocBinarySearch() {
        for (Map.Entry<String, String> entry: sample.entrySet()) {
            Assertions.assertEquals(
                    entry.getValue(),
                    ipLocList.get(
                            Collections.binarySearch(ipLocList, NetUtil.ip2long(entry.getKey()))
                    ).getCode()
            );
        }

    }

    @Test
    @DisplayName("GuavaRangeMap")
    void ip2LocGuavaRangeMap() {
        for (Map.Entry<String, String> entry: sample.entrySet()) {
            Assertions.assertEquals(
                    entry.getValue(),
                    ipLocRangeMap.get(NetUtil.ip2long(entry.getKey())).getCode()
            );
        }
    }
}
