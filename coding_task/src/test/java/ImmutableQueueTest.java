import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableQueueTest {
    private Queue<Integer> emptyQueue;

    @BeforeEach
    public void beforeEach() {
        this.emptyQueue = new ImmutableQueue<>();
    }

    @Test
    public void testEnqueue() {
        // Check initial state
        assertTrue(this.emptyQueue.isEmpty());
        assertNull(this.emptyQueue.head());

        // Enqueue 0
        Queue<Integer> queue_0 = this.emptyQueue.enQueue(0);
        assertFalse(queue_0.isEmpty());
        assertEquals(0, queue_0.head());
        assertNotSame(this.emptyQueue, queue_0);

        // Enqueue 5
        Queue<Integer> queue_0_5 = queue_0.enQueue(5);
        assertFalse(queue_0_5.isEmpty());
        assertEquals(0, queue_0_5.head());
        assertNotSame(queue_0, queue_0_5);

        // Check if queues still hold their original values
        assertTrue(this.emptyQueue.isEmpty());
        assertNull(this.emptyQueue.head());
        assertFalse(queue_0.isEmpty());
        assertEquals(0, queue_0.head());
    }

    @Test
    public void testDequeue() {
        // Prepare initial state
        Queue<Integer> queue_0 = this.emptyQueue.enQueue(0);
        Queue<Integer> queue_0_5 = queue_0.enQueue(5);

        // Dequeue 0
        Queue<Integer> queue_5 = queue_0_5.deQueue();
        assertFalse(queue_5.isEmpty());
        assertEquals(5, queue_5.head());
        assertNotSame(queue_0_5, queue_5);
        assertNotSame(queue_0, queue_5);

        // Dequeue 5
        Queue<Integer> emptyQueueDeQueued = queue_5.deQueue();
        assertTrue(emptyQueueDeQueued.isEmpty());
        assertNull(emptyQueueDeQueued.head());
        assertNotSame(queue_5, emptyQueueDeQueued);
        assertNotSame(this.emptyQueue, emptyQueueDeQueued);

        // Check dequeue empty queue throw exception
        assertThrows(IllegalStateException.class, this.emptyQueue::deQueue);
        assertThrows(IllegalStateException.class, emptyQueueDeQueued::deQueue);

        // Check if queues still hold their original values
        assertFalse(queue_0_5.isEmpty());
        assertEquals(0, queue_0_5.head());
        assertFalse(queue_5.isEmpty());
        assertEquals(5, queue_5.head());
    }

    @Test
    public void testIntermediateState_ElementsInBothInStackAndOutStack() {
        // Prepare initial state
        Queue<Integer> queue_0 = this.emptyQueue.enQueue(0);
        Queue<Integer> queue_0_5 = queue_0.enQueue(5);

        // Dequeue 0
        Queue<Integer> queue_5 = queue_0_5.deQueue();
        assertFalse(queue_5.isEmpty());
        assertEquals(5, queue_5.head());
        assertNotSame(queue_0_5, queue_5);
        assertNotSame(queue_0, queue_5);

        // Enqueue 3
        Queue<Integer> queue_5_3 = queue_5.enQueue(3);
        assertFalse(queue_5_3.isEmpty());
        assertEquals(5, queue_5_3.head());
        assertNotSame(queue_5, queue_5_3);

        // Dequeue 5
        Queue<Integer> queue_3 = queue_5_3.deQueue();
        assertFalse(queue_3.isEmpty());
        assertEquals(3, queue_3.head());
        assertNotSame(queue_5_3, queue_3);

        // Dequeue 3
        Queue<Integer> emptyQueueDeQueued = queue_3.deQueue();
        assertTrue(emptyQueueDeQueued.isEmpty());
        assertNull(emptyQueueDeQueued.head());
        assertNotSame(queue_3, emptyQueueDeQueued);
        assertNotSame(this.emptyQueue, emptyQueueDeQueued);

        // Check dequeue empty queue throw exception
        assertThrows(IllegalStateException.class, this.emptyQueue::deQueue);
        assertThrows(IllegalStateException.class, emptyQueueDeQueued::deQueue);

        // Check if queues still hold their original values
        assertFalse(queue_0_5.isEmpty());
        assertEquals(0, queue_0_5.head());
        assertFalse(queue_5.isEmpty());
        assertEquals(5, queue_5.head());
        assertFalse(queue_5_3.isEmpty());
        assertEquals(5, queue_5_3.head());
        assertFalse(queue_3.isEmpty());
        assertEquals(3, queue_3.head());
    }

    @Test
    public void testThreadSafety() throws Exception {
        // Instantiate 10 threads to manipulate the same queue
        int threadCount = 10;
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        Collection<Future<Queue<Integer>>> futures = new ArrayList<>(threadCount);
        // Run each thread with the same sequence, with random wait
        for (int t = 0; t < threadCount; ++t) {
            futures.add(service.submit(() -> {
                Random random = new Random();
                Queue<Integer> queue_0 = this.emptyQueue.enQueue(0);
                Thread.sleep(random.nextInt(20));
                Queue<Integer> queue_0_5 = queue_0.enQueue(5);
                Thread.sleep(random.nextInt(20));
                Queue<Integer> queue_5 = queue_0_5.deQueue();
                Thread.sleep(random.nextInt(20));
                Queue<Integer> queue_5_3 = queue_5.enQueue(3);
                Thread.sleep(random.nextInt(20));
                Queue<Integer> queue_3 = queue_5_3.deQueue();
                Thread.sleep(random.nextInt(20));
                return queue_3.deQueue();
            }));
        }
        // If the queue is thread-safe, all 10 threads should return the same result
        for (Future<Queue<Integer>> f : futures) {
            Queue<Integer> resultQueue = f.get();
            assertTrue(resultQueue.isEmpty());
            assertNull(resultQueue.head());
        }
    }
}