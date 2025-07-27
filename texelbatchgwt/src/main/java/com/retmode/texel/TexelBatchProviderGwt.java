package com.retmode.texel;

import com.badlogic.gdx.AbstractGraphics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtGL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.google.gwt.webgl.client;

public class TexelBatchProviderGwt implements TexelBatchProvider {

    public static void initialize() {
        TexelBatchSingleton.set(new TexelBatchProviderGwt());
    }

    public SpriteBatch provide(AbstractGraphics graphics, TexelBatchVersion version) {
        if (!graphics.isGL30Available()) {
            GwtGL20 context = (GwtGL20)graphics.getGL20();
            Gdx.app.log("Loading extension resulted in", ""+context.gl.getExtension("OES_standard_derivatives").toString());
        }
        switch (version) {
            case EASY: return new TexelBatchEasy();
            case NORMAL: return new TexelBatch();
            default: return null;
        }
    }
}
