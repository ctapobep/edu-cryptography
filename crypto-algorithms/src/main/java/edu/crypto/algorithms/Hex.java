package edu.crypto.algorithms;

public class Hex {
    public static byte[] fromHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new byte[0];
        }
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("There cannot be hex string with odd number of letters since the data" +
                    " is always in bytes and 1 byte = 2hex letters.");
        }
        byte[] result = new byte[hex.length() / 2];
        char[] originalChars = hex.toCharArray();
        for (int i = 0, byteIndex = 0; byteIndex < originalChars.length; i++, byteIndex += 2) {
            char lowerBitsLetter = originalChars[byteIndex + 1];
            char higherBitsLetter = originalChars[byteIndex];
            byte b = (byte) (FROM_HEX_TO_BYTE[lowerBitsLetter] | (FROM_HEX_TO_BYTE[higherBitsLetter] << 4));
            result[i] = b;
        }
        return result;
    }

    public static String toString(byte[] bytes){
        return new String(org.apache.commons.codec.binary.Hex.encodeHex(bytes));
    }

    private static final char[] FROM_BYTE_TO_HEX = "0123456789abcde".toCharArray();
    private static final byte[] FROM_HEX_TO_BYTE = new byte[103];

    static {
        for (int i = 48; i < 58; i++) {//filling integers
            FROM_HEX_TO_BYTE[i] = (byte) (i - 48);
        }
        for (int i = 97; i < 103; i++) {
            FROM_HEX_TO_BYTE[i] = (byte) (i - 87);//filling abcdef
        }
    }
}
