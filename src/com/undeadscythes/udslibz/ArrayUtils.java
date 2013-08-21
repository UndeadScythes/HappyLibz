package com.undeadscythes.udslibz;

import java.util.*;

/**
 * For manipulating arrays.
 * @author UndeadScythes
 */
public class ArrayUtils {
    public static int[] reverse(final int[] array) {
        int arrayLen = array.length;
        int[] newArray = new int[arrayLen];
        for(int i = 0; i < arrayLen; i++) {
            newArray[arrayLen - 1 - i] = array[i];
        }
        return newArray;
    }

    public static int count(final int item, final int[] array) {
        int count = 0;
        for(int i = 0; i < array.length; i++) {
            if(array[i] == item) {
                count++;
            }
        }
        return count;
    }

    public static String join(final Sequence[] seqs, final String joiner) {
        String temp = "";
        for(Sequence seq : seqs) {
            temp = temp.concat(joiner + seq.toString());
        }
        return temp.substring(joiner.length());
    }

    public static String join(final Sequence[] seqs, final int length, final String joiner) {
        String temp = "";
        for(Sequence seq : seqs) {
            temp = temp.concat(joiner + seq.getSubSequence(0, length - 1).toString());
        }
        return temp.substring(joiner.length());
    }

    public static String join(final int[] integers, final String joiner) {
        String temp = "";
        for(int integer : integers) {
            temp = temp.concat(joiner + integer);
        }
        return temp.substring(joiner.length());
    }

    public static String join(final Polynomial[] polys, final String joiner, final String variable) {
        String temp = "";
        for(Polynomial poly : polys) {
            temp = temp.concat(joiner + poly.toString(variable));
        }
        return temp.substring(joiner.length());
    }

    public static String joinBinary(final Polynomial[] polys, final String joiner) {
        String temp = "";
        for(Polynomial poly : polys) {
            temp = temp.concat(joiner + poly.toBinary());
        }
        return temp.substring(joiner.length());
    }

    public static String joinBinary(final int[] polys, final int length, final String joiner) {
        String temp = "";
        for(int poly : polys) {
            temp = temp.concat(joiner + BinaryUtils.toString(poly, length));
        }
        return temp.substring(joiner.length());
    }

    public static String joinBinary(final List<Polynomial> polys, final String joiner) {
        String temp = "";
        for(Polynomial poly : polys) {
            temp = temp.concat(joiner + poly.toBinary());
        }
        return temp.substring(joiner.length());
    }

    public static String joinDecimal(final Polynomial[] polys, final String joiner) {
        String temp = "";
        for(Polynomial poly : polys) {
            temp = temp.concat(joiner + poly.toInt());
        }
        return temp.substring(joiner.length());
    }

    public static String joinDecimal(final List<Polynomial> polys, final String joiner) {
        String temp = "";
        for(Polynomial poly : polys) {
            temp = temp.concat(joiner + poly.toInt());
        }
        return temp.substring(joiner.length());
    }

    public static String join(final List<Sequence> seqs, final String joiner) {
        String temp = "";
        for(Sequence seq : seqs) {
            temp = temp.concat(joiner + seq.toString());
        }
        return temp.substring(joiner.length());
    }
}
