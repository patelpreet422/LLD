package kvstore.strategy;

import kvstore.util.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SimpleHashStrategyTest {
    @Test
    public void testHashEmptyString() throws Throwable {
        SimpleHashStrategy strategy = new SimpleHashStrategy();
        Result<Long> hashValue = strategy.hash(null);
        // Assert that the hash value for an empty string is not 0
        assertNotEquals(0, hashValue.isErr());
    }

    @Test
    public void testHashEqualStrings() throws Throwable {
        SimpleHashStrategy strategy = new SimpleHashStrategy();
        String key1 = "test";
        String key2 = "test";
        long hashValue1 = strategy.hash(key1).get();
        long hashValue2 = strategy.hash(key2).get();
        // Assert that equal strings have the same hash value
        assertEquals(hashValue1, hashValue2);
    }

    @Test
    public void testHashEqualStringsButDifferentStringObjects() throws Throwable {
        SimpleHashStrategy strategy = new SimpleHashStrategy();
        String key1 = new String("test");
        String key2 = new String("test");
        long hashValue1 = strategy.hash(key1).get();
        long hashValue2 = strategy.hash(key2).get();
        // Assert that equal strings have the same hash value
        assertEquals(hashValue1, hashValue2);
    }

    @Test
    public void testHashDifferentStrings() throws Throwable {
        SimpleHashStrategy strategy = new SimpleHashStrategy();
        String key1 = "test1";
        String key2 = "test2";
        long hashValue1 = strategy.hash(key1).get();
        long hashValue2 = strategy.hash(key2).get();
        // Assert that different strings have different hash values
        assertNotEquals(hashValue1, hashValue2);
    }

}