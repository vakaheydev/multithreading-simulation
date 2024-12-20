package simulation.util;

import simulation.Field;
import simulation.entity.Entity;
import simulation.entity.Point;
import simulation.entity.animal.Plant;
import simulation.entity.animal.herbivore.*;
import simulation.entity.animal.predator.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class EntityUtil {
    private EntityUtil() {
    }

    public static void addEntities(Field field, Point point, Entity... entities) {
        for (Entity entity : entities) {
            field.addEntity(point.x(), point.y(), entity);
        }
    }

    public static List<Entity> addEntities(Field field, Point point, Class<? extends Entity> clazz, int amount) {
        List<Entity> entityList = new ArrayList<>();
        checkMaxQuantity(field, clazz, amount);

        for (int i = 0; i < amount; i++) {
            Entity entity = newInstance(field, point, clazz);
            entityList.add(entity);
        }

        return entityList;
    }

    private static void checkMaxQuantity(Field field, Class<? extends Entity> clazz, int amount) {
        int maxAmount = getMaxQuantity(field, clazz);
        if (amount > maxAmount) {
            throw new IllegalArgumentException(String.format("Specified amount[%d] is higher than entity[%s] max " +
                    "amount[%d] on location", amount, clazz.getSimpleName(), maxAmount));
        }
    }

    public static List<Entity> addEntities(Field field, Class<? extends Entity> clazz, int amount) {
        List<Entity> entityList = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Point point = PointUtil.getRandomPoint(field, clazz);
            Entity entity = newInstance(field, point, clazz);
            entityList.add(entity);
        }

        return entityList;
    }

    public static List<Entity> addEntities(Field field, int amount) {
        List<Entity> entityList = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Class<? extends Entity> clazz = getRandomClass();
            Point point = PointUtil.getRandomPoint(field, clazz);
            Entity entity = newInstance(field, point, clazz);
            entityList.add(entity);
        }

        return entityList;
    }

    public static Entity addEntity(Field field) {
        return addEntities(field, 1).get(0);
    }

    public static Class<? extends Entity> getRandomClass() {
        List<Class<? extends Entity>> classes = List.of(
                Plant.class,
                Boar.class,
                Buffalo.class,
                Caterpillar.class,
                Deer.class,
                Duck.class,
                Goat.class,
                Horse.class,
                Mouse.class,
                Rabbit.class,
                Sheep.class,
                Bear.class,
                Boa.class,
                Eagle.class,
                Fox.class,
                Wolf.class
        );

        return classes.get(new Random().nextInt(classes.size()));
    }

    public static Class<? extends Entity> getRandomClass(Class<? extends Entity>... classes) {
        return classes[new Random().nextInt(classes.length)];
    }

    public static int getMaxQuantity(Field field, Class<? extends Entity> clazz) {
        try {
            Entity entity = clazz.getDeclaredConstructor().newInstance();
            return (int) clazz.getMethod("maxQuantity").invoke(entity);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T> T newInstance(Field field, Point point, Class<? extends Entity> clazz) {
        try {
            return (T) clazz.getConstructor(Field.class, int.class, int.class).newInstance(field, point.x(), point.y());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T> T newInstance(Field field, Class<? extends Entity> clazz) {
        try {
            return (T) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
