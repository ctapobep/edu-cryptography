package edu.crypto.algorithms;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class HexTest {

    @Test
    public void cornerCases() throws Exception {
        assertEquals(Hex.fromHex("").length, 0);
        assertEquals(Hex.fromHex(null).length, 0);
        try {
            assertEquals(Hex.fromHex("k")[0], 16);
            fail();
        } catch (Exception e) {
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void singleLetter() throws Exception {
        assertEquals(Hex.fromHex("1")[0], 1);
    }

    @Test
    public void multipleLetters() throws Exception {
        assertEquals(Hex.fromHex("00")[0], 0);
        assertEquals(Hex.fromHex("10")[0], 16);
        assertEquals(Hex.fromHex("ff")[0], -1);
    }

    @Test
    public void longLetters() throws Exception {
        assertEquals(Hex.fromHex("00ff"), new byte[]{0, -1});
        assertEquals(Hex.fromHex("1001"), new byte[]{16, 1});
        assertEquals(Hex.fromHex("ffac"), new byte[]{-1, -84});
    }
}
