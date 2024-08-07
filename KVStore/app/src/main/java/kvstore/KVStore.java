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

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class KVStore {

    private final HashStrategy hashStrategy;

    private Stack<ConcurrentHashMap<String, String>> tranxStack = new Stack<>();

    public KVStore(HashStrategy hashStrategy) {
        this.hashStrategy = hashStrategy;
    }

    private void addKey(ConcurrentHashMap<String, String> store, String key, String value) {
        Result<Long> hash = hashStrategy.hash(key);

        switch (hash) {
            case Err<Long> err -> log.error("Error while hashing key", err.error());
            case Ok<Long> ok -> store.put(key, value);
        }

    }

    public void addKey(String key, String value) {
        if(tranxStack.empty()) {
            throw new IllegalStateException("No on going transaction, call store.begin() to start the transaction");
        }

        addKey(tranxStack.peek(), key, value);
    }

    private String getValue(ConcurrentHashMap<String, String> store, String key) throws Throwable {
        long hash = hashStrategy.hash(key).get();
        return store.get(key);
    }

    public String getValue(String key) throws Throwable {
        if(tranxStack.empty()) {
            throw new IllegalStateException("No on going transaction, call store.begin() to start the transaction");
        }

       return getValue(tranxStack.peek(), key);
    }

    private void removeKey(ConcurrentHashMap<String, String> store, String key) throws Throwable {
        long hash = hashStrategy.hash(key).get();
        store.remove(key);
    }

    public void removeKey(String key) throws Throwable {
        if(tranxStack.empty()) {
            throw new IllegalStateException("No on going transaction, call store.begin() to start the transaction");
        }

        removeKey(tranxStack.peek(), key);
    }

    public void begin() {
        tranxStack.push(new ConcurrentHashMap<>());
    }
}

