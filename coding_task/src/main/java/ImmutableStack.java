/**
 * Note that it is NOT a deep copy.
 * i.e. If the child element is mutable, while multiple queues holding the element, the element values can be changed.
 *
 * @param <T> - Template
 */
public class ImmutableStack<T> implements Stack<T>{
    private final ImmutableStack<T> stack;
    private final T item;

    public ImmutableStack() {
        this.stack = null;
        this.item = null;
    }

    private ImmutableStack(ImmutableStack<T> stack, T item) {
        this.stack = stack;
        this.item = item;
    }

    @Override
    public Stack<T> push(T t) {
        return new ImmutableStack<>(this, t);
    }

    @Override
    public Stack<T> pop() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty, pop is not allowed");
        return this.stack;
    }

    @Override
    public T peek() {
        return this.item;
    }

    @Override
    public boolean isEmpty() {
        return this.stack == null;
    }
}
