package com.retmode.texel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TexelBatchProviderGL extends TexelBatchProvider {
    
    public static void initialize() {
        TexelBatchProvider.set(new TexelBatchProviderGL());
    }

    public SpriteBatch provide(TexelBatchVersion version, int size) {
        TexelShader.GLMode mode;
        if (!Gdx.graphics.isGL30Available()) {
            mode = TexelShader.GLMode.PRE_GL_330;
        } else {
            mode = TexelShader.GLMode.GL_330;
        }
        
        switch (version) {
            case TEST: return new TexelBatchTest(size, TexelShader.createTexelShader(mode));
            case TEXEL: return new TexelBatch(size, TexelShader.createTexelShader(mode));
            case YE_GOOD_OLD_BATCH: return new SpriteBatch(size, TexelShader.createDefaultBatchShader(mode));
            default: return null;
        }
    }

    public SpriteBatch provide(TexelBatchVersion version) {
        return provide(version, 1000);
    }
}
