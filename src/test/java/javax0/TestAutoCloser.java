package javax0;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestAutoCloser {
    private boolean opened;

    @Test
    void test() {
        try (final var s = AutoCloser.useResource(new NotAutoclosable())
                .closeWith(sp -> sp.get().dispose())) {
            Assertions.assertTrue(opened);
        }
        Assertions.assertFalse(opened);
    }

    @Test
    void test1() {
        final var notAu = new NotAutoclosable();
        try (final var s = AutoCloser.useResource(notAu)
                .closeWith(notAu::dispose)) {
            Assertions.assertTrue(opened);
        }
        Assertions.assertFalse(opened);
    }

    class NotAutoclosable {
        NotAutoclosable() {
            opened = true;
        }

        void dispose() {
            opened = false;
        }
    }
}
