package edu.crypto.algorithms;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.TreeMap;

import static com.google.common.base.Preconditions.checkArgument;

public class Bitwise {
    public static byte[] xor(byte[] input, byte[] cipher) {
        byte[] result = new byte[input.length];
        for (int i = 0, cipherIndex = 0; i < input.length; i++, cipherIndex++) {
            if (cipherIndex == cipher.length) {
                cipherIndex = 0;
            }
            result[i] = (byte) (input[i] ^ cipher[cipherIndex]);
        }
        return result;
    }

    public static byte[] guessSingleByteXorCipher(byte[] input, boolean usualTextWithPhrases) {
        double maxScore = 0;
        byte maxScoreCipher = 0;
        for (int i = 0; i < 256; i++) {
            byte[] unencrypted = xor(input, new byte[]{(byte) i});
            double score = English.score(unencrypted, usualTextWithPhrases);
            if (score > maxScore) {
                maxScore = score;
                maxScoreCipher = (byte) i;
            }
        }
        return xor(input, new byte[]{maxScoreCipher});
    }

    public static int hammingDistance(byte[] first, byte[] second) {
        checkArgument(first.length == second.length);
        BitSet firstBits = BitSet.valueOf(first);
        BitSet secondBits = BitSet.valueOf(second);
        firstBits.xor(secondBits);
        return firstBits.cardinality();
    }

    public static byte[] guessRepeatingXorCipher(byte[] input) {
        int[] cipherSizes = findCipherSize(input, 5);
        List<byte[]> decryptedOptions = new ArrayList<>();
        for (int cipherSize : cipherSizes) {
            decryptedOptions.add(guessSingleRepeatingXor(input, cipherSize));
        }

        double highestScore = 0;
        byte[] bestDecryptedOption = null;
        for (byte[] decrypted : decryptedOptions) {
            double score = English.score(decrypted, true);
            if (score > highestScore) {
                highestScore = score;
                bestDecryptedOption = decrypted;
            }
        }
        return bestDecryptedOption;
    }

    private static byte[] guessSingleRepeatingXor(byte[] input, int cipherSize) {
        List<byte[]> splitByBlocks = new ArrayList<>();
        for (int cipherLetterIndex = 0; cipherLetterIndex < cipherSize; cipherLetterIndex++) {
            splitByBlocks.add(eachNthByte(input, cipherSize, cipherLetterIndex));
        }
        List<byte[]> guessedBlocks = new ArrayList<>();
        for (byte[] block : splitByBlocks) {
            guessedBlocks.add(guessSingleByteXorCipher(block, false));
        }
        byte[] result = new byte[input.length];
        for (int i = 0, globalCounter = 0; i < guessedBlocks.get(0).length; i++) {
            for (int j = 0; j < guessedBlocks.size() && globalCounter < result.length; j++) {
                result[globalCounter++] = guessedBlocks.get(j)[i];
            }
        }
        return result;
    }

    public static byte[] eachNthByte(byte[] input, int distance, int nthFromDistance) {
        checkArgument(distance > nthFromDistance);
        int numberOfBlocks = input.length / distance;
        numberOfBlocks = input.length % distance > nthFromDistance ? numberOfBlocks + 1 : numberOfBlocks;
        byte[] result = new byte[numberOfBlocks];
        for (int i = 0; i < numberOfBlocks; i++) {
            int nextElement = i * distance + nthFromDistance;
            result[i] = input[nextElement];
        }
        return result;
    }

    public static int[] findCipherSize(byte[] input, int numberOfResults) {
        TreeMap<Double, List<Integer>> distanceVsCipherSize = new TreeMap<>();

        for (int cipherSize = 1; cipherSize < input.length / 4; cipherSize++) {
            byte[] firstChunk = new byte[cipherSize * 2];
            byte[] secondChunk = new byte[cipherSize * 2];
            System.arraycopy(input, 0, firstChunk, 0, cipherSize * 2);//2x of the cipher length to lessen the probability of wrong choice
            System.arraycopy(input, cipherSize * 2, firstChunk, 0, cipherSize * 2);

            double distance = hammingDistance(firstChunk, secondChunk);
            double normalizedDistance = distance / (double) cipherSize;

            List<Integer> cipherSizes = distanceVsCipherSize.get(normalizedDistance);
            if (cipherSizes == null) {
                cipherSizes = new ArrayList<>();
            }
            cipherSizes.add(cipherSize);
            distanceVsCipherSize.put(normalizedDistance, cipherSizes);
        }

        int i = 0;
        int[] result = new int[numberOfResults];
        breakAtAll:
        for (List<Integer> cipherSizes : distanceVsCipherSize.values()) {
            for (Integer nextCipherSize : cipherSizes) {
                result[i] = nextCipherSize;
                if (++i >= numberOfResults) {
                    break breakAtAll;
                }
            }
        }
        return result;
    }

    public static byte[] guessSingleByteXorCipher(String hex) {
        return guessSingleByteXorCipher(Hex.fromHex(hex), true);
    }
}
