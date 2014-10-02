package edu.crypto.algorithms;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Base64Test {

    @Test(enabled = false)
    public void encode1Byte() throws Exception {
        assertEquals(Base64.toBase64(new byte[]{0b000000_00}), new byte[]{0b00_000000, 0b00_000000});
        assertEquals(Base64.toBase64(new byte[]{0b000000_01}), new byte[]{0b00_000000, 0b00_000001});
        assertEquals(Base64.toBase64(new byte[]{0b000000_10}), new byte[]{0b00_000000, 0b00_000010});
        assertEquals(Base64.toBase64(new byte[]{0b000001_00}), new byte[]{0b00_000001, 0b00_000000});
        assertEquals(Base64.toBase64(new byte[]{(byte) 0b111111_11}), new byte[]{0b00_111111, 0b00_00011});
        assertEquals(Base64.toBase64(new byte[]{(byte) 0b100000_01}), new byte[]{0b00_100000, 0b00_000001});
    }

    @Test
    public void encode2Bytes() throws Exception {
        assertEquals(Base64.toBase64(new byte[]{0b000000_00, 0b0000_0000}), new byte[]{0, 0, 0});
        assertEquals(Base64.toBase64(new byte[]{0b000000_01, 0b0000_0000}), new byte[]{0, 16, 0});
        assertEquals(Base64.toBase64(new byte[]{0b000000_01, 0b0000_0001}), new byte[]{0, 16, 1});
        assertEquals(Base64.toBase64(new byte[]{0b000001_00, (byte) 0b1000_0100}), new byte[]{1, -1});
        assertEquals(Base64.toBase64(new byte[]{(byte) 0b10000001}), new byte[]{-32, 1});
    }

    @Test
    public void fromWikipedia() {
        String original = "Man is distinguished, not only by his reason, but by this singular passion from other " +
                "animals, which is a lust of the mind, that by a perseverance of delight in the continued and " +
                "indefatigable generation of knowledge, exceeds the short vehemence of any carnal pleasure.";
        String base64 = "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0" +
                "aGlzIHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1" +
                "c3Qgb2YgdGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0" +
                "aGUgY29udGludWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdl" +
                "LCBleGNlZWRzIHRoZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=";
        assertEquals(new String(Base64.toBase64(original.getBytes())), base64);
    }
}
