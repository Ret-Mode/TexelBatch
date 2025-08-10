package com.retmode.texel;

public abstract class TexelBatchProvider {

    private static TexelBatchProvider instance = null;
    protected static void set(TexelBatchProvider instance) {
        TexelBatchProvider.instance = instance;
    }

    public static TexelBatchProvider get() {
        return TexelBatchProvider.instance;
    }

    public enum TexelBatchVersion {
        TEST,
        TEXEL,
        YE_GOOD_OLD_BATCH
    };

    abstract public ExtendedBatch provide(TexelBatchVersion version);
    abstract public ExtendedBatch provide(TexelBatchVersion version, int size);

}
