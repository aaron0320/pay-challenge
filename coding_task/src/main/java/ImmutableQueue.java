/**
 * Note that it is NOT a deep copy.
 * i.e. If the child element is mutable, while multiple queues holding the element, the element values can be changed.
 *
 * @param <T> - Template
 */
public class ImmutableQueue<T> implements Queue<T> {
    private ImmutableStack<T> inStack;
    private ImmutableStack<T> outStack;

    public ImmutableQueue() {
        this.inStack = new ImmutableStack<>();
        this.outStack = new ImmutableStack<>();
    }

    private ImmutableQueue(ImmutableStack<T> inStack, ImmutableStack<T> outStack) {
        this.inStack = inStack;
        this.outStack = outStack;
    }

    @Override
    public Queue<T> enQueue(T t) {
        return new ImmutableQueue<>(
                (ImmutableStack<T>) this.inStack.push(t),
                this.outStack
        );
    }

    @Override
    public Queue<T> deQueue() {
        if (this.outStack.isEmpty() && this.inStack.isEmpty())
            throw new IllegalStateException("Queue is empty, dequeue is not allowed");

        if (this.outStack.isEmpty()) {
            moveFromInStackToOutStack();
        }
        return new ImmutableQueue<>(
                this.inStack,
                (ImmutableStack<T>) this.outStack.pop()
        );
    }

    @Override
    public T head() {
        if (!this.outStack.isEmpty()) {
            return this.outStack.peek();
        }
        if (!this.inStack.isEmpty()) {
            moveFromInStackToOutStack();
        }
        return this.outStack.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.outStack.isEmpty() && this.inStack.isEmpty();
    }

    private void moveFromInStackToOutStack() {
        while (!this.inStack.isEmpty()) {
            this.outStack = (ImmutableStack<T>) this.outStack.push(this.inStack.peek());
            this.inStack = (ImmutableStack<T>) this.inStack.pop();
        }
    }
}
