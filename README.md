# IP2Location Lookup Benchmark

A JMH benchmark comparing two IP-to-country lookup strategies using the [IP2Location](https://www.ip2location.com/) dataset.

## Strategies Compared

| Strategy | Description |
|---|---|
| **Binary Search** | `Collections.binarySearch` on a sorted `List<IpLocation>` with a custom `Comparable<Long>` |
| **Guava TreeRangeMap** | `RangeMap.get()` on a `TreeRangeMap<Long, IpLocation>` with closed ranges |

Both load the same `IPCountry.csv` dataset (IP range start/end, country code, country name) and resolve a random IP address to its country.

## Prerequisites

- Java 8+
- `IPCountry.csv` in the project root (IP2Location DB1 CSV format)

## Build & Run

```bash
# Run benchmarks (3 forks, 3 warmup iterations, 3 measurement iterations)
./gradlew jmh

# Run unit tests
./gradlew test
```

## Project Structure

```
src/
  main/java/com/yuganji/
    IpLocation.java          # IP range data class (start, end, code, country)
    IpLocationData.java      # CSV loader -> sorted List + Guava RangeMap
    NetUtil.java             # Random IP generation & IP-to-long conversion
  jmh/java/benchmark/
    BenchmarkIpLocation.java # JMH benchmark (Throughput, ops/sec)
  test/java/com/yuganji/
    BenchmarkIpLocationTest.java  # Correctness tests for both strategies
  test/resources/
    ip2loc_test_dataset.csv       # Test sample data
```

## JMH Configuration

| Parameter | Value |
|---|---|
| Mode | Throughput (ops/sec) |
| Forks | 3 |
| Warmup Iterations | 3 |
| Measurement Iterations | 3 |

## Dependencies

- [Guava](https://github.com/google/guava) - `TreeRangeMap` for range-based lookup
- [Univocity Parsers](https://github.com/uniVocity/univocity-parsers) - CSV parsing
- [JMH](https://github.com/openjdk/jmh) - Microbenchmark harness
- [Lombok](https://projectlombok.org/) - Boilerplate reduction
- [JUnit 5](https://junit.org/junit5/) - Testing

## License

This project uses the IP2Location dataset. Please refer to [IP2Location's terms of use](https://www.ip2location.com/terms) for dataset licensing.
