package edu.crypto.algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class English {
    public static double score(byte[] text, boolean phrases) {
        double result = 0;
        for (int i = 0; i < text.length; i++) {
            byte letter = text[i];
            if (letter < 32 && letter!= 10) {
                result -= 100;
                continue;
            }
            Double byteWeight = BYTE_WEIGHT.get(letter);
            if (byteWeight == null) {
                result -= 20;
            } else {
                result += byteWeight;
            }

            if (phrases) {
                for (Map.Entry<byte[], Double> word : WORDS_WEIGHT.entrySet()) {
                    int wordLength = word.getKey().length;
                    if (wordLength < text.length - i) {
                        byte[] pieceOfText = new byte[wordLength];
                        System.arraycopy(text, i, pieceOfText, 0, wordLength);
                        if (Arrays.equals(pieceOfText, word.getKey())) {
                            result += word.getValue();
                        }
                    }
                }
            }
        }
        return result;
    }

    public static final Map<Byte, Double> BYTE_WEIGHT = new HashMap<>(52);
    public static final Map<byte[], Double> WORDS_WEIGHT = new HashMap<>();
    public static final Map<Character, Double> LETTER_WEIGHT = new HashMap<>(52);

    static {
        LETTER_WEIGHT.put(' ', 30.0);
        LETTER_WEIGHT.put('e', 12.7);
        LETTER_WEIGHT.put('t', 9.1);
        LETTER_WEIGHT.put('a', 8.2);
        LETTER_WEIGHT.put('o', 7.5);
        LETTER_WEIGHT.put('i', 7.0);
        LETTER_WEIGHT.put('n', 6.7);
        LETTER_WEIGHT.put('s', 6.3);
        LETTER_WEIGHT.put('h', 6.1);
        LETTER_WEIGHT.put('r', 6.0);
        LETTER_WEIGHT.put('d', 4.3);
        LETTER_WEIGHT.put('l', 4.0);
        LETTER_WEIGHT.put('u', 2.8);
        LETTER_WEIGHT.put('c', 2.8);
        LETTER_WEIGHT.put('m', 2.4);
        LETTER_WEIGHT.put('w', 2.4);
        LETTER_WEIGHT.put('f', 2.2);
        LETTER_WEIGHT.put('y', 2.0);
        LETTER_WEIGHT.put('g', 2.0);
        LETTER_WEIGHT.put('p', 1.9);
        LETTER_WEIGHT.put('b', 1.5);
        LETTER_WEIGHT.put('v', 1.0);
        LETTER_WEIGHT.put('k', 0.8);
        LETTER_WEIGHT.put('x', 0.2);
        LETTER_WEIGHT.put('j', 0.2);
        LETTER_WEIGHT.put('q', 0.1);
        LETTER_WEIGHT.put('z', 0.1);

        Map<Character, Double> bigLetters = new HashMap<>(26);
        for (Map.Entry<Character, Double> letter : LETTER_WEIGHT.entrySet()) {
            bigLetters.put(new String(new char[]{letter.getKey()}).toUpperCase().charAt(0), letter.getValue() / 2);
        }
        LETTER_WEIGHT.putAll(bigLetters);
        LETTER_WEIGHT.put('\'', 1.0);
        LETTER_WEIGHT.put('.', 0.5);
        LETTER_WEIGHT.put(',', 0.7);
        LETTER_WEIGHT.put('\n', 0.2);
        LETTER_WEIGHT.put('"', 0.1);
        LETTER_WEIGHT.put('!', 0.1);
        LETTER_WEIGHT.put('?', 0.1);
        LETTER_WEIGHT.put(':', 0.1);

        for (Map.Entry<Character, Double> letter : LETTER_WEIGHT.entrySet()) {
            BYTE_WEIGHT.put((byte) letter.getKey().charValue(), letter.getValue());
        }

        WORDS_WEIGHT.put("qu".getBytes(), 10.0);
        WORDS_WEIGHT.put("at".getBytes(), 20.0);
        WORDS_WEIGHT.put("so".getBytes(), 20.0);
        WORDS_WEIGHT.put("th".getBytes(), 20.0);
        WORDS_WEIGHT.put("Th".getBytes(), 20.0);
        WORDS_WEIGHT.put("sh".getBytes(), 20.0);
        WORDS_WEIGHT.put("ea".getBytes(), 20.0);
        WORDS_WEIGHT.put("is".getBytes(), 20.0);
        WORDS_WEIGHT.put("'s".getBytes(), 20.0);
        WORDS_WEIGHT.put("he".getBytes(), 20.0);
        WORDS_WEIGHT.put("do".getBytes(), 20.0);
        WORDS_WEIGHT.put("we".getBytes(), 20.0);
        WORDS_WEIGHT.put("me".getBytes(), 20.0);
        WORDS_WEIGHT.put("my".getBytes(), 20.0);
        WORDS_WEIGHT.put("in".getBytes(), 20.0);
        WORDS_WEIGHT.put("it".getBytes(), 20.0);
        WORDS_WEIGHT.put("or".getBytes(), 20.0);
        WORDS_WEIGHT.put("on".getBytes(), 20.0);
        WORDS_WEIGHT.put("if".getBytes(), 20.0);
        WORDS_WEIGHT.put("to".getBytes(), 25.0);
        WORDS_WEIGHT.put("the".getBytes(), 30.0);
        WORDS_WEIGHT.put("The".getBytes(), 30.0);
        WORDS_WEIGHT.put("she".getBytes(), 30.0);
        WORDS_WEIGHT.put("for".getBytes(), 30.0);
        WORDS_WEIGHT.put("are".getBytes(), 30.0);
        WORDS_WEIGHT.put("'re".getBytes(), 30.0);
        WORDS_WEIGHT.put(" a ".getBytes(), 30.0);
        WORDS_WEIGHT.put("you".getBytes(), 30.0);
        WORDS_WEIGHT.put("ing".getBytes(), 30.0);
        WORDS_WEIGHT.put(" the ".getBytes(), 50.0);
        WORDS_WEIGHT.put(" and ".getBytes(), 50.0);
    }

}
