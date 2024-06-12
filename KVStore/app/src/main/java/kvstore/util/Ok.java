package kvstore.util;

public record Ok<T>(T value) implements Result<T> {
}
