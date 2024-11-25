package simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("<--- Start of simulation --->");
        Simulation simulation = new Simulation();
        simulation.start();
        log.info("<--- End of simulation --->");
    }
}
