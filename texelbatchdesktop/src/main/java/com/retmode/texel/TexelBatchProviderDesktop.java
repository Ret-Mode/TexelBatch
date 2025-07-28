package com.retmode.texel;

import com.badlogic.gdx.AbstractGraphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TexelBatchProviderDesktop implements TexelBatchProvider {
    
    public static void initialize() {
        TexelBatchSingleton.set(new TexelBatchProviderDesktop());
    }

    public SpriteBatch provide(Object graphics, TexelBatchVersion version) {
        switch (version) {
            case EASY: return new TexelBatchEasy();
            case NORMAL: return new TexelBatch();
            default: return null;
        }
    }
}
