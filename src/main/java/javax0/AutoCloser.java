package javax0;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AutoCloser<T> {

    private final T resource;

    private AutoCloser(T resource) {
        this.resource = resource;
    }

    public static <T> AutoCloser<T> useResource(T resource) {
        return new AutoCloser<>(resource);
    }

    public AutoClosableSupplier closeWith(Consumer<Supplier<T>> closer){
        return new AutoClosableSupplier(closer);
    }

    public class AutoClosableSupplier implements Supplier<T>, AutoCloseable {
        private final Consumer<Supplier<T>> closer;

        private AutoClosableSupplier(Consumer<Supplier<T>> closer) {
            this.closer = closer;
        }

        @Override
        public T get() {
            return resource;
        }

        @Override
        public void close() {
            closer.accept(this);
        }

    }
}
