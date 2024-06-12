package kvstore.util;

import java.util.function.Function;

public sealed interface Result<T> permits Err, Ok {

    static <T> Result<T> err(Throwable error) {
        return new Err<>(error);
    }

    static <T> Result<T> err(String message) {
        return new Err<>(new Throwable(message));
    }

    static <T> Result<T> ok(T value) {
        return new Ok<>(value);
    }

    default boolean isOk() {
        return switch (this) {
            case Err<T> err -> false;
            case Ok<T> ok -> true;
        };
    }

    default boolean isErr() {
        return switch (this) {
            case Err<T> err -> true;
            case Ok<T> ok -> false;
        };
    }

    default T getOr(T defaultValue) {
        return switch (this) {
            case Err<T> err -> defaultValue;
            case Ok<T> ok -> ok.value();
        };
    }

    default T get() throws Throwable {
        if(this.isErr()) {
            throw new UnsupportedOperationException("Result is null can't call get on this result");
        }
        return this.getOrThrow();
    }

    default T getOrThrow() throws Throwable {
        if (this instanceof Ok<T> ok) {
            return ok.value();
        }
        throw ((Err<T>)this).error();
    }

    default <U> Result<U> map(Function<? super T, ? extends U> mapper) {
        if (this instanceof Ok<T> ok) {
            return Result.ok(mapper.apply(ok.value()));
        }
        return (Result<U>)this;
    }

    default <U> Result<U> flatMap(Function<? super T, Result<U>> mapper) {
        if (this instanceof Ok<T> ok) {
            return mapper.apply(ok.value());
        }
        return (Result<U>)this;
    }
}

