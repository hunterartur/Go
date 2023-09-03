package com.simbirsoft.mockauthserver.Util;

import java.util.Base64;

public final class CoderBase64 {

    private CoderBase64() {
    }

    public static String encode(String encode) {
        return Base64.getEncoder().encodeToString(encode.getBytes());
    }

    public static String decode(String decode) {
        byte[] decodeBytes = Base64.getDecoder().decode(decode);
        return new String(decodeBytes);
    }
}
