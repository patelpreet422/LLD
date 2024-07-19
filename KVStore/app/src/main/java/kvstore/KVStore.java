/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package kvstore;

import kvstore.strategy.HashStrategy;
import kvstore.util.Err;
import kvstore.util.Ok;
import kvstore.util.Result;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class KVStore {

    private final HashStrategy hashStrategy;
    private ConcurrentHashMap<String, String> store;

    public KVStore(HashStrategy hashStrategy) {
        this.hashStrategy = hashStrategy;
    }

    public void addKey(String key, String value) {
        Result<Long> hash = hashStrategy.hash(key);

        switch (hash) {
            case Err<Long> err -> log.error("Error while hashing key", err.error());
            case Ok<Long> ok -> store.put(key, value);
        }

    }

    public String getValue(String key) throws Throwable {
        long hash = hashStrategy.hash(key).get();
        return store.get(key);
    }

    public void deleteKey(String key) throws Throwable {
        long hash = hashStrategy.hash(key).get();
        store.remove(key);
    }
}

