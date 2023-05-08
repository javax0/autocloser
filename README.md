# autocloser
 A simle class that helps to handle the automatic closing of resources (objects) that do not implement the
 `AutoCloseable` interface in a try-with-resources block.
 
As a try-with-resources block can only handle a resource that implements the `AutoCloseable` interface we have
to envelope the non implementing resource into one that does implement. The envelope is an `AutoCloser`
object (more precisely an `AutoCloser<T>.Closer` instance) that implements the `AutoCloseable` interface
as well as the `Supplier<T>` interface. The implemented `AutoCloseable#close()` method invokes a
`Consumer<Supplier<T>>` that gets the original `T` resource. The resource itself can be defined as the
argument to the `useResource(Object)` method. The consumer has to be defined as the argument to the
`closeWith(Consumer)` method.

The use of the class is

```
try (final var s = AutoCloser.useResource(new NotAutoclosable())
           .closeWith(sp -> sp.get().dispose())) {
       ...
   }
```

The closing functionality can also be defined as a `Runnable` with an overloadedversion of
`closeWith(Runnable)`.


