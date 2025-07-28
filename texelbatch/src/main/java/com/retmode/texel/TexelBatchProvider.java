package com.retmode.texel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.AbstractGraphics;

public interface TexelBatchProvider {
    
    enum TexelBatchVersion {
        EASY,
        NORMAL
    };

    SpriteBatch provide(Object graphics, TexelBatchVersion version);
}
