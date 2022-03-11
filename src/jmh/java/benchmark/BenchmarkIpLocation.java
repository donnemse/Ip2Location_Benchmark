package benchmark;

import com.google.common.collect.RangeMap;
import com.yuganji.IpLocation;
import com.yuganji.IpLocationData;
import com.yuganji.NetUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BenchmarkIpLocation {

    private List<IpLocation> ipLocList;

    private RangeMap<Long, IpLocation> ipRangeMap;

    @Setup
    public void setup() {
        IpLocationData instance = new IpLocationData();
        this.ipLocList = instance.getIpLocList();
        this.ipRangeMap = instance.getIpRangeMap();
    }

    @Benchmark
    public void benchGuavaTreeRangeMap(Blackhole bh) {
        bh.consume(
                this.ipRangeMap.get(NetUtil.ip2long(NetUtil.genIp()))
        );
    }

    @Benchmark
    public void benchBinarySearch(Blackhole bh){
        bh.consume(
                Collections.binarySearch(this.ipLocList, NetUtil.ip2long(NetUtil.genIp()))
        );
    }
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkIpLocation.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
