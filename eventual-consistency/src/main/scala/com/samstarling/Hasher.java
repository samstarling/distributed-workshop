package com.samstarling;

import scala.math.BigInt;

import java.security.MessageDigest;

public class Hasher {

    public static BigInt sha1(String str) { return hash(str, "SHA-1"); }



    private static BigInt hash(String str, String algo) {
        try {
            MessageDigest md = MessageDigest.getInstance(algo);
            return BigInt.apply(1, md.digest(str.getBytes("UTF-8")));
        }
        catch(Exception exp) {
            throw new RuntimeException("Could not hash string");
        }
    }

}