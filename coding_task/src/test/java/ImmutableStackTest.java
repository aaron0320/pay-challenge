import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableStackTest {
    private Stack<Integer> emptyStack;

    @BeforeEach
    public void beforeEach() {
        this.emptyStack = new ImmutableStack<>();
    }

    @Test
    public void testPush() {
        // Check initial state
        assertTrue(this.emptyStack.isEmpty());
        assertNull(this.emptyStack.peek());

        // Push 0
        Stack<Integer> stack_0 = this.emptyStack.push(0);
        assertFalse(stack_0.isEmpty());
        assertEquals(0, stack_0.peek());
        assertNotSame(stack_0, this.emptyStack);

        // Push 5
        Stack<Integer> stack_0_5 = stack_0.push(5);
        assertFalse(stack_0_5.isEmpty());
        assertEquals(5, stack_0_5.peek());
        assertNotSame(stack_0_5, stack_0);

        // Check if stacks still hold their original values
        assertTrue(this.emptyStack.isEmpty());
        assertNull(this.emptyStack.peek());
        assertFalse(stack_0.isEmpty());
        assertEquals(0, stack_0.peek());
    }

    @Test
    public void testPop() {
        // Prepare initial state
        Stack<Integer> stack_0 = this.emptyStack.push(0);
        Stack<Integer> stack_0_5 = stack_0.push(5);

        // Pop 0
        Stack<Integer> stack_0_popped = stack_0_5.pop();
        assertFalse(stack_0_popped.isEmpty());
        assertEquals(0, stack_0_popped.peek());
        assertNotSame(stack_0_5, stack_0_popped);
        assertSame(stack_0, stack_0_popped);

        // Pop 5
        Stack<Integer> emptyStackPopped = stack_0_popped.pop();
        assertTrue(emptyStackPopped.isEmpty());
        assertNull(emptyStackPopped.peek());
        assertNotSame(stack_0_popped, emptyStackPopped);
        assertSame(this.emptyStack, emptyStackPopped);

        // Check pop empty stack throw exception
        assertThrows(IllegalStateException.class, emptyStackPopped::pop);

        // Check if queues still hold their original values
        assertTrue(this.emptyStack.isEmpty());
        assertNull(this.emptyStack.peek());
        assertFalse(stack_0.isEmpty());
        assertEquals(0, stack_0.peek());
        assertFalse(stack_0_5.isEmpty());
        assertEquals(5, stack_0_5.peek());
    }

}