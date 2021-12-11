package work.lclpnet.mmoquark.util;

@FunctionalInterface
public interface TriFunction<R, T, U, V> {
    R apply(T t, U u, V v);
}