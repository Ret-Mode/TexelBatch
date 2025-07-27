package com.retmode.texel;

public class TexelBatchSingleton {
    private static TexelBatchProvider instance = null;
    public static void set(TexelBatchProvider instance) {
        TexelBatchSingleton.instance = instance;
    }

    public static TexelBatchProvider get() {
        return TexelBatchSingleton.instance;
    }
}
