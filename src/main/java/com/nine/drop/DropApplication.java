package com.nine.drop;

import com.nine.drop.resource.DataResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropApplication extends Application<DropConfiguration> {
    @Override
    public void initialize(Bootstrap<DropConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(DropConfiguration dropConfiguration, Environment environment) throws Exception {
        environment.jersey().register(DataResource.class);
        environment.jersey().setUrlPattern("/rest/*");
    }

    public static void main(String... args) throws Exception {
        new DropApplication().run(args);
    }
}
