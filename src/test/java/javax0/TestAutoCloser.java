package javax0;

import org.junit.jupiter.api.Test;

class TestAutoCloser {

    @Test
    void test() {
        final var notAu = NotAutoclosable.open();
        try (final AutoCloser.Closable s = AutoCloser.use(notAu).withCloser(() -> notAu.dispose())) {
System.out.println("Open "+notAu.opened);
        }
System.out.println("Open "+notAu.opened);
    }
}
