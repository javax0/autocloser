package javax0;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A simle class that helps to handle the automatic closing of resources (objects) that do not implement the
 * {@link AutoCloseable} interface in a try-with-resources block.
 * <p>
 * As a try-with-resources block can only handle a resource that implements the {@link AutoCloseable} interface we have
 * to envelope the non implementing resource into one that does implement. The envelope is an {@code AutoCloser}
 * object (more precisely an {@code AutoCloser<T>.Closer} instance) that implements the {@link AutoCloseable} interface
 * as well as the {@link Supplier<T>} interface. The implemented {@link AutoCloseable#close()} method invokes a
 * {@link Consumer<Supplier<T>>} that gets the original {@code T} resource. The resource itself can be defined as the
 * argument to the {@link #useResource(Object)} method. The consumer has to be defined as the argument to the
 * {@link #closeWith(Consumer)} method.
 * <p>
 * The use of the class is
 * <pre>{@code
 * try (final var s = AutoCloser.useResource(new NotAutoclosable())
 *            .closeWith(sp -> sp.get().dispose())) {
 *        ...
 *    }
 * }</pre>
 * <p>
 * The closing functionality can also be defined as a {@link Runnable} with an overloadedversion of
 * {@link #closeWith(Runnable)}.
 *
 * @param <T> the type of the original resource.
 */
public class AutoCloser<T> {

    private final T resource;

    private AutoCloser(T resource) {
        this.resource = resource;
    }

    public static <T> AutoCloser<T> useResource(T resource) {
        return new AutoCloser<>(resource);
    }

    public AutoClosableSupplier closeWith(Consumer<Supplier<T>> closer) {
        return new AutoClosableSupplier(closer);
    }

    public AutoClosableSupplier closeWith(Runnable closer) {
        return new AutoClosableSupplier(t -> closer.run());
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
