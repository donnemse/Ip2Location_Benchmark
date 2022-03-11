package com.yuganji;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class IpLocationData {

    @Getter
    private List<IpLocation> ipLocList;

    @Getter
    private RangeMap<Long, IpLocation> ipRangeMap;

    public IpLocationData() {
        this.ipLocList = new LinkedList<>();
        this.ipRangeMap = TreeRangeMap.create();
        CsvParser parser = new CsvParser(new CsvParserSettings());
        parser.beginParsing(new File("./IPCountry.csv"));
        Record r = null;


        while ((r = parser.parseNextRecord()) != null) {
            IpLocation ipLoc = IpLocation.builder()
                    .start(r.getLong(0))
                    .end(r.getLong(1))
                    .code(r.getString(2))
                    .country(r.getString(3))
                    .build();
            this.ipLocList.add(ipLoc);
            this.ipRangeMap.put(
                    Range.closed(r.getLong(0), r.getLong(1)),
                    ipLoc
            );
        }
        Collections.sort(this.ipLocList, new Comparator<IpLocation>() {
            @Override
            public int compare(IpLocation o1, IpLocation o2) {
                return (int)(o1.getStart() - o2.getStart());
            }
        });
        parser.stopParsing();
    }
}
