import java.util.Arrays;

/**
 * ImmutableQueue which instantiate a new queue for write operations.
 * Note that it is NOT a deep copy.
 * i.e. If the child element is mutable, while multiple queues holding the element, the element values can be changed.
 *
 * @param <T> - Template
 */
public class ImmutableQueue<T> implements Queue<T> {
    private final Object[] array;

    public ImmutableQueue() {
        this.array = new Object[0];
    }

    public ImmutableQueue(Queue<T> thatQueue) {
        int n = thatQueue.size();
        this.array = new Object[n];
        for (int i = 0; i < n; i++) {
            array[i] = thatQueue.head();
            thatQueue = thatQueue.deQueue();
        }
    }

    private ImmutableQueue(Object[] array) {
        this.array = array;
    }

    @Override
    public Queue<T> enQueue(T t) {
        Object[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = t;
        return new ImmutableQueue<>(newArray);
    }

    @Override
    public Queue<T> deQueue() {
        if (array.length == 0)
            throw new IllegalStateException("Queue is empty");
        return new ImmutableQueue<>(Arrays.copyOfRange(array, 1, array.length));
    }

    @SuppressWarnings("unchecked")
    @Override
    public T head() {
        if (array.length == 0)
            return null;
        return (T) array[0];
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public int size() {
        return array.length;
    }
}
