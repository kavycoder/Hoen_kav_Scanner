
        package com.skyscanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.io.IOException;
import java.util.*;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
        // No initialization needed for now
    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment) throws IOException {
        ObjectMapper mapper = new ObjectMapper();


        List<SearchResult> carResults = Arrays.asList(
                mapper.readValue(
                        Objects.requireNonNull(getClass().getClassLoader().getResource("rental_cars.json")),
                        SearchResult[].class
                )
        );

        // Load hotel results
        List<SearchResult> hotelResults = Arrays.asList(
                mapper.readValue(
                        Objects.requireNonNull(getClass().getClassLoader().getResource("hotels.json")),
                        SearchResult[].class
                )
        );


        List<SearchResult> searchResults = new ArrayList<>();
        searchResults.addAll(carResults);
        searchResults.addAll(hotelResults);
        final SearchResource resource = new SearchResource(searchResults);
        environment.jersey().register(resource);


    }
}
