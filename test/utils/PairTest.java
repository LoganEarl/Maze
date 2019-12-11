package utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PairTest {
    private Pair<Integer> p1;

    @BeforeEach
    void setUp(){
        p1 = new Pair<>(1,2);
    }

    @Test
    void itemA() {
        assert(p1.itemA() == 1);
    }

    @Test
    void itemB() {
        assert(p1.itemB() == 2);
    }

    @Test
    void otherItem() {
        assert(p1.otherItem(2) == 1);
        assert(p1.otherItem(1) == 2);
        assert(p1.otherItem(3) == 1);
    }

    @Test
    void testConstructorExceptions(){
        assertThrows(IllegalArgumentException.class, ()-> new Pair<Integer>(null,1));
        assertThrows(IllegalArgumentException.class, ()-> new Pair<Integer>(1,null));
        assertThrows(IllegalArgumentException.class, ()-> new Pair<Integer>(null,null));
    }

    @Test
    void testOtherItemExceptions(){
        assertThrows(IllegalArgumentException.class, ()->p1.otherItem(null));
    }

    @SuppressWarnings({"ConstantConditions", "EqualsWithItself"})
    @Test
    void testEquals() {
        Pair<Integer> p2 = new Pair<>(1,2);
        assert(p1.equals(p2));
        p2 = new Pair<>(1,3);
        assert(!p1.equals(p2));
        p2 = new Pair<>(2,2);
        assert(!p1.equals(p2));
        assert(!p1.equals(null));
        assert(p1.equals(p1));
    }
}