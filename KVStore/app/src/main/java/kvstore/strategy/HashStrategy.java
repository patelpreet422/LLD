package kvstore.strategy;

import kvstore.util.Result;

public interface HashStrategy {
   Result<Long> hash(String key);
}

