package edu.crypto.algorithms;

import java.nio.ByteBuffer;

public class Base64 {

    public static byte[] toBase64(byte[] unencoded) {
        return org.apache.commons.codec.binary.Base64.encodeBase64(unencoded);
    }

    private static byte[] yetNotWorkingOwnImplementationOfToBase64(byte[] unencoded) {
        if (unencoded == null || unencoded.length == 0) {
            return new byte[0];
        }
        byte[] result;
        if (unencoded.length % 3 == 0) {
            result = new byte[unencoded.length * 8 / 6];
        } else {
            result = new byte[unencoded.length * 8 / 6 + 1];
        }

        for (int i = 0, hexByteIndex = 0; i < unencoded.length; i += 3) {
            result[hexByteIndex] = (byte) (unencoded[i] >>> 2); //first 6 left bits of 1st byte

            //2nd base64 byte
            result[++hexByteIndex] = (byte) (unencoded[i] & 0b000000_11); //last 2 right bits of 1st byte
            if (i + 1 >= unencoded.length) {
                break;
            }
            result[hexByteIndex] <<= 4;//if we have next byte, then last bits to be moved to the right
            result[hexByteIndex] |= (byte) ((unencoded[i + 1] & 0b1111_0000) >>> 4);//first 4 bits of 2nd byte
            //3d base64 byte
            result[++hexByteIndex] = (byte) (unencoded[i + 1] & 0b0000_1111);//4 right bits of 2nd byte
            if (i + 2 >= unencoded.length) {
                break;
            }
            result[hexByteIndex] <<= 2;//if we have next unencoded byte, then we'll need 2 more bits from right
            result[hexByteIndex] |= (byte) ((unencoded[i + 2] & 0b11_000000) >>> 6);//first 2 bits of 3d byte
            result[++hexByteIndex] = (byte) (unencoded[i + 2] & 0b00_111111);//last 6 bits of 3d byte

            hexByteIndex++;//prepare for next cycle
        }
        return result;
    }
}
