package com.nine.drop.resource;

import com.nine.drop.util.Compressor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("/data")
public class DataResource {
    private static final int RAW_SIZE = 1000;
    private static final int COMPRESSED_SIZE = 100;
    private static final double ZERO_RATE = 0.0;

    @GET
    @Produces("application/json")
    public List<Double> getData() {
        List<Double> rawData = randomDoubles(RAW_SIZE);
        return Compressor.compress(rawData, COMPRESSED_SIZE);
    }

    private List<Double> randomDoubles(int size) {
        Random random = new Random();
        List<Double> results = new ArrayList<>(size);
        for(int i=0; i<size; i++) {
            if(random.nextDouble() < ZERO_RATE) {
                results.add(0.0);
            } else {
                if(random.nextDouble() < 0.0) {
                    results.add(random.nextDouble() * -100000);
                } else {
                    results.add(random.nextDouble() * 100000);
                }
            }
        }

        return results;
    }
}
