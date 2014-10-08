package com.nine.drop.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Compressor {
    public static List<Double> compress(List<Double> results, int targetSize) {
        return Lists.newArrayList(padWithZero(results, targetSize)).stream()
                .sorted()
                .collect(new SlidingAverageCollector(results.size(), targetSize));
    }

    private static List<Double> padWithZero(List<Double> data, int targetSize) {
        while(data.size() < targetSize) {
            data.add(0.0);
        }

        return data;
    }

    private static class SlidingAverageCollector implements Collector<Double, List<Double>, List<Double>> {
        private final double targetFrameFactor;
        private int count = 0;
        private List<Double> buffer;

        private final double totalSize;
        private final int targetSize;

        private SlidingAverageCollector(double totalSize, int targetSize) {
            this.targetFrameFactor = totalSize / targetSize;
            this.buffer = new ArrayList<>((int)Math.ceil(targetFrameFactor));

            this.totalSize = totalSize;
            this.targetSize = targetSize;
        }

        @Override
        public Supplier<List<Double>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<Double>, Double> accumulator() {
            return (results, d) -> {
                buffer.add(d);
                count++;
                double currentFrameFactor = count / (results.size() + 1);
                double remainingFrameFactor = (totalSize - count) / (targetSize - results.size());
                if(currentFrameFactor >= targetFrameFactor || remainingFrameFactor < 1.0) {
                    dumpBuffer(results);
                }
            };
        }

        @Override
        public Function<List<Double>, List<Double>> finisher() {
            return results -> {
                if(!buffer.isEmpty()) {
                    dumpBuffer(results);
                }
                return results;
            };
        }

        private void dumpBuffer(List<Double> results) {
            results.add(buffer.stream().collect(Collectors.averagingDouble(x -> x)));
            buffer.clear();
        }

        @Override
        public BinaryOperator<List<Double>> combiner() {
            return (l1, l2) -> {
                throw new UnsupportedOperationException("Combining not possible");
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
    }

    //http://bl.ocks.org/mbostock/3883195
}
