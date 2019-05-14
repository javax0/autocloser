package javax0;

/**
 * A test class that is not auto closeable.
 */
public class NotAutoclosable {
    public boolean opened;
    public static NotAutoclosable open(){
        final var it =new NotAutoclosable();
        it.opened = true;
        return it;
    }

    public  void dispose(){
        opened = false;

    }

}
