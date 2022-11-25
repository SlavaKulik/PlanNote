package edu.courses.plannote.utils;

import java.util.Base64;

public class CodeDecodeId {
    public static String idEncoder(String id) {
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(id.getBytes()));
    }

    public static String idDecoder(String id) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(id));
    }
}
