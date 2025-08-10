package com.retmode.texel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TexelBatchProviderGLES extends TexelBatchProvider {

    public static void initialize() {
        TexelBatchProvider.set(new TexelBatchProviderGLES());
    }

    public Batch provide(TexelBatchVersion version, int size) {
        
        TexelShader.GLMode mode;
        if (!Gdx.graphics.isGL30Available()) {
            if (!Gdx.graphics.supportsExtension("OES_standard_derivatives")) {
                Gdx.app.log("Texel:","OES_standard_derivatives cannot be loaded.");
            } 
            mode = TexelShader.GLMode.PRE_GLES_3;
            
        } else {
            mode = TexelShader.GLMode.GLES_3;
        }
        try{        
            switch (version) {
                case TEST: return new TexelBatchTest(size, TexelShader.createTexelShader(mode));
                case TEXEL: return new TexelBatch(size, TexelShader.createTexelShader(mode));
                case YE_GOOD_OLD_BATCH: default: return new SpriteBatch(size, TexelShader.createDefaultBatchShader(mode));
            }
        } catch (Exception e) {
            Gdx.app.log("Texel:",e.toString());
            Gdx.app.log("Texel:","Got exception when generating batch, fallback to default SpriteBatch");
            return new SpriteBatch(size, TexelShader.createDefaultBatchShader(mode));
        }
    }

    public Batch provide(TexelBatchVersion version) {
        return provide(version, 1000);
    }
}
