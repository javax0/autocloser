package javax0;

import java.util.function.Supplier;

public class AutoCloser<T> {

    private final T resource;

    private AutoCloser(T resource) {
        this.resource = resource;
    }

    public static <T> AutoCloser<T> use(T resource) {
        return new AutoCloser<>(resource);
    }

    public Closable withCloser(Runnable closer){
        return new Closable(closer);
    }

    public class Closable implements Supplier<T>, AutoCloseable {
        private final Runnable closer;

        private Closable(Runnable closer) {
            this.closer = closer;
        }

        @Override
        public T get() {
            return resource;
        }

        @Override
        public void close() {
            closer.run();
        }

    }
}
