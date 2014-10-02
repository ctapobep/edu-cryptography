package edu.crypto.algorithms;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BitwiseTest {

    @Test
    public void testHammingDistance() throws Exception {
        assertEquals(Bitwise.hammingDistance("this is a test".getBytes(), "wokka wokka!!!".getBytes()), 37);
    }

    @Test
    public void eachNthByte() throws Exception {
        byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        assertEquals(Bitwise.eachNthByte(bytes, 1, 0), bytes);
        assertEquals(Bitwise.eachNthByte(bytes, 2, 0), new byte[]{1, 3, 5, 7, 9});
        assertEquals(Bitwise.eachNthByte(bytes, 2, 1), new byte[]{2, 4, 6, 8, 0});
        assertEquals(Bitwise.eachNthByte(bytes, 3, 2), new byte[]{3, 6, 9});
        assertEquals(Bitwise.eachNthByte(bytes, 10, 1), new byte[]{2});
        assertEquals(Bitwise.eachNthByte(bytes, 11, 1), new byte[]{2});
        assertEquals(Bitwise.eachNthByte(bytes, 12, 11), new byte[]{});
    }

    @Test
    public void findCipherSize() throws Exception {
        Bitwise.findCipherSize(new byte[]{'a', 'c', 'f', 'k'}, 1);
    }
}
