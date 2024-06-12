package kvstore.util;

public record Err<T>(Throwable error) implements Result<T> { }
