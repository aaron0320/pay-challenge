import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableQueueTest {
    @Test
    public void testEnqueue() {
        // Check initial state
        Queue<Integer> emptyQueue = new ImmutableQueue<>();
        assertTrue(emptyQueue.isEmpty());
        assertEquals(0, emptyQueue.size());
        assertNull(emptyQueue.head());

        // Enqueue 0
        Queue<Integer> queue_0 =  emptyQueue.enQueue(0);
        assertFalse(queue_0.isEmpty());
        assertEquals(1, queue_0.size());
        assertEquals(0, queue_0.head());
        assertNotSame (queue_0, emptyQueue);

        // Enqueue 5
        Queue<Integer> queue_0_5 =  queue_0.enQueue(5);
        assertFalse(queue_0_5.isEmpty());
        assertEquals(2, queue_0_5.size());
        assertEquals(0, queue_0_5.head());
        assertNotSame (queue_0_5, queue_0);

        // Check if queues still hold their original values
        assertTrue(emptyQueue.isEmpty());
        assertEquals(0, emptyQueue.size());
        assertNull(emptyQueue.head());
        assertFalse(queue_0.isEmpty());
        assertEquals(1, queue_0.size());
        assertEquals(0, queue_0.head());
    }

    @Test
    public void testDequeue() {
        // Prepare initial state
        Queue<Integer> emptyQueue = new ImmutableQueue<>();
        Queue<Integer> queue_0 =  emptyQueue.enQueue(0);
        Queue<Integer> queue_0_5 =  queue_0.enQueue(5);

        // Dequeue 0
        Queue<Integer> queue_5 =  queue_0_5.deQueue();
        assertFalse(queue_5.isEmpty());
        assertEquals(1, queue_5.size());
        assertEquals(5, queue_5.head());
        assertNotSame (queue_5, queue_0_5);

        // Dequeue 5
        Queue<Integer> emptyQueue2 =  queue_5.deQueue();
        assertTrue(emptyQueue2.isEmpty());
        assertEquals(0, emptyQueue2.size());
        assertNull(emptyQueue2.head());
        assertNotSame (emptyQueue2, queue_5);
        assertNotSame (emptyQueue2, emptyQueue);

        // Check dequeue empty queue throw exception
        assertThrows(IllegalStateException.class, emptyQueue2::deQueue);

        // Check if queues still hold their original values
        assertFalse(queue_0_5.isEmpty());
        assertEquals(2, queue_0_5.size());
        assertEquals(0, queue_0_5.head());
        assertFalse(queue_5.isEmpty());
        assertEquals(1, queue_5.size());
        assertEquals(5, queue_5.head());
    }

    @Test
    public void testQueueCopy() {
        Queue<Integer> oldQueue = new ImmutableQueue<>();
        oldQueue = oldQueue.enQueue(1);
        oldQueue = oldQueue.enQueue(2);

        // Copy to new queue
        Queue<Integer> newQueue = new ImmutableQueue<>(oldQueue);
        assertFalse(newQueue.isEmpty());
        assertEquals(2, newQueue.size());
        assertEquals(1, newQueue.head());
        assertNotSame (newQueue, oldQueue);

        // Dequeue 1
        newQueue = newQueue.deQueue();
        assertFalse(newQueue.isEmpty());
        assertEquals(1, newQueue.size());
        assertEquals(2, newQueue.head());

        // Dequeue 2
        newQueue = newQueue.deQueue();
        assertTrue(newQueue.isEmpty());
        assertEquals(0, newQueue.size());
        assertNull(newQueue.head());

        // Check if old queue still hold their original values
        assertFalse(oldQueue.isEmpty());
        assertEquals(2, oldQueue.size());
        assertEquals(1, oldQueue.head());
    }
}