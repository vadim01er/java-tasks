package ru.mail.polis.homework.io.object;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SerializerTest {

    private final static int QUANTITY = 100;
    private final static List<String> foodList = Arrays.asList("mushroom", "animal", "bark", "leaves", "berries");
    private final String fileName = "src/test/java/ru/mail/polis/homework/io/object/serialize.txt";
    Serializer serializer = new Serializer();

    private static final List<Animal> animals = new ArrayList<Animal>() {{
        Random random = new Random();
        for (int i = 0; i < QUANTITY; i++) {
            add(
                    new Animal(random.nextInt(),
                            String.valueOf(random.nextInt()),
                            Animal.Habitat.values()[random.nextInt(2)],
                            new ArrayList<String>() {{
                                add(foodList.get(random.nextInt(4)));
                            }},
                            random.nextBoolean(),
                            random.nextDouble() * 100,
                            new Heart(random.nextBoolean())
                    )
            );

        }
    }};

    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
        Random random = new Random();
        for (int i = 0; i < QUANTITY; i++) {
            add(
                    new AnimalWithMethods(random.nextInt(),
                            String.valueOf(random.nextInt()),
                            Animal.Habitat.values()[random.nextInt(2)],
                            new ArrayList<String>() {{
                                add(foodList.get(random.nextInt(4)));
                            }},
                            random.nextBoolean(),
                            random.nextDouble() * 100,
                            new Heart(random.nextBoolean())
                    )
            );

        }
    }};

    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<AnimalExternalizable>() {{
        Random random = new Random();
        for (int i = 0; i < QUANTITY; i++) {
            add(
                    new AnimalExternalizable(random.nextInt(),
                            String.valueOf(random.nextInt()),
                            Animal.Habitat.values()[random.nextInt(2)],
                            new ArrayList<String>() {{
                                add(foodList.get(random.nextInt(4)));
                                add(foodList.get(random.nextInt(4)));
                            }},
                            random.nextBoolean(),
                            random.nextDouble() * 100,
                            new Heart(random.nextBoolean())
                    )
            );

        }
    }};

    @Test
    public void defaultSerializeTest() {
        serializer.defaultSerialize(animals, fileName);

        List<Animal> animalsSerializer = serializer.defaultDeserialize(fileName);

        Assert.assertArrayEquals(animals.toArray(), animalsSerializer.toArray());
    }


    @Test
    public void serializeWithMethodsTest() {
        serializer.serializeWithMethods(animalsWithMethods, fileName);

        List<AnimalWithMethods> animalsSerializer = serializer.deserializeWithMethods(fileName);

        Assert.assertArrayEquals(animalsWithMethods.toArray(), animalsSerializer.toArray());
    }


    @Test
    public void serializeWithExternalizableTest() {
        serializer.serializeWithExternalizable(animalsExternalizable, fileName);

        List<AnimalExternalizable> animalsSerializer = serializer.deserializeWithExternalizable(fileName);

        Assert.assertArrayEquals(animalsExternalizable.toArray(), animalsSerializer.toArray());
    }


    @Test
    public void customSerialize() {
        serializer.customSerialize(animals, fileName);

        List<Animal> animalsSerializer = serializer.customDeserialize(fileName);

        Assert.assertArrayEquals(animals.toArray(), animalsSerializer.toArray());
    }


    @After
    public void deleteFile() {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
