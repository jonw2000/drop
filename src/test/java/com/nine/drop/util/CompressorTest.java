package com.nine.drop.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CompressorTest {
    @Test
    public void testSimpleCompression() {
        List<Double> input = Lists.newArrayList(1.12, 2.11, 3.10, 4.9, 5.8, 6.7, 7.6, 8.5, 9.4, 10.3, 11.2, 12.1);
        List<Double> results = Compressor.compress(input, 4);

        assertArrayEquals(new double[]{2.11, 5.8, 8.5, 11.2}, toPrimitive(results), 0.01);
    }

    @Test
    public void testSimpleCompressionUnordered() {
        List<Double> input = Lists.newArrayList(1.12, 2.11, 3.10, 4.9, 5.8, 6.7, 7.6, 8.5, 9.4, 10.3, 11.2, 12.1);
        Collections.shuffle(input);
        List<Double> results = Compressor.compress(input, 4);

        assertArrayEquals(new double[]{2.11, 5.8, 8.5, 11.2}, toPrimitive(results), 0.01);
    }

    @Test
    public void testCompressionWithLeftOvers() {
        List<Double> input = Lists.newArrayList(1.12, 2.11, 3.10, 4.9, 5.8, 6.7, 7.6, 8.5, 9.4, 10.3, 11.2);
        Collections.shuffle(input);
        List<Double> results = Compressor.compress(input, 4);

        assertArrayEquals(new double[]{2.11, 5.8, 8.5, 10.75}, toPrimitive(results), 0.01);
    }

    @Test
    public void testCompressionWithPadding() {
        List<Double> input = Lists.newArrayList(1.12, 2.11);
        List<Double> results = Compressor.compress(input, 4);

        assertArrayEquals(new double[]{0.0, 0.0, 1.12, 2.11}, toPrimitive(results), 0.01);
    }

    @Test
    public void testWithNegativesAndZeros() {
        List<Double> input = Lists.newArrayList(-1.12, -2.11, -3.10, 0.0, 0.0, 0.0, 7.6, 8.5, 9.4, 10.3, 11.2);
        Collections.shuffle(input);
        List<Double> results = Compressor.compress(input, 4);

        assertArrayEquals(new double[]{average(-1.12, -2.11, -3.10), average(0.0, 0.0, 0.0), average(7.6, 8.5, 9.4), average(10.3, 11.2)}, toPrimitive(results), 0.01);
    }

    @Test
    public void testCountSizeIsReturned() {
        for(int i=0; i<1000; i++) {
            for(int c=1; c<=10; c++) {
                List<Double> results = Compressor.compress(randomDoubleList(i), c);
                assertEquals(c, results.size());
            }
        }
    }

    private List<Double> randomDoubleList(int size) {
        Random random = new Random();
        List<Double> result = new ArrayList<>(size);
        for(int i=0; i<size; i++) {
            result.add(random.nextDouble());
        }

        return result;
    }

    private double[] toPrimitive(List<Double> doubleList) {
        double[] primitive = new double[doubleList.size()];
        for(int i=0; i<doubleList.size(); i++)
            primitive[i] = doubleList.get(i);

        return primitive;
    }

    private double average(double... values) {
        double sum = 0;
        for(double value : values)
            sum += value;

        return sum / values.length;
    }
}
