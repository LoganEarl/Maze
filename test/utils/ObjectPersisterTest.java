package utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ObjectPersisterTest {
    private ObjectPersister<String> persister;
    private static final String DATA_DIRECTORY = System.getProperty("user.dir").replace("\\", "/") + "/data/";
    private static final String toSave = "TestString";
    private static final String fileName = "testFile.ser";

    @BeforeEach
    void setup(){
        persister = new ObjectPersister<>(DATA_DIRECTORY);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @AfterEach
    void tearDown(){
        File f = new File(DATA_DIRECTORY + fileName);
        try {
            f.delete();
        }catch(Exception ignored){}
    }

    @Test
    void testSaveObject() {
        assert(persister.saveObject(toSave, fileName));
        assert(new File(DATA_DIRECTORY,fileName).exists());
    }

    @Test
    void testSaveObjectThrows(){
        assertThrows(IllegalArgumentException.class, ()-> persister.saveObject(null, fileName));
        assertThrows(IllegalArgumentException.class, ()-> persister.saveObject(toSave, null));
        assertThrows(IllegalArgumentException.class, ()-> persister.saveObject(toSave, ""));
    }

    @Test
    void testLoadObject() {
        persister.saveObject(toSave, fileName);
        String s = persister.loadObject(fileName);
        assert(s != null && s.equals(toSave));
    }

    @Test
    void testLoadObjectThrows(){
        assertThrows(IllegalArgumentException.class, ()-> persister.loadObject(null));
        assertThrows(IllegalArgumentException.class, ()-> persister.loadObject(""));
    }
}