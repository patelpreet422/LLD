package kvstore.strategy;

import kvstore.util.Result;

import java.util.Objects;

public class SimpleHashStrategy implements HashStrategy {

    @Override
    public Result<Long> hash(String key) {
        if(Objects.isNull(key)) {
            return Result.err("key to hash can't be null");
        }
        return Result.ok((long) key.hashCode());
    }
}
