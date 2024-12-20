package simulation.exception;

import simulation.entity.Entity;

public class TooMuchEntitiesException extends ValidationException {
    public TooMuchEntitiesException(Entity entity) {
        super(String.format("There is already max amount of specified " +
                "entity[%s] on this location", entity));
    }
}
