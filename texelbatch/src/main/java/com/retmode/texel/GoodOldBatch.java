package com.retmode.texel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class GoodOldBatch extends SpriteBatch implements ExtendedBatch {

    public GoodOldBatch(int size, ShaderProgram program) {
        super(size, program);
    }

    public GoodOldBatch(ShaderProgram program) {
        this(1000, program);
    }

    @Override
    public void setUvOffsetInPixels(float scale) {
        ;
    }

    @Override
    public void setBlendParam(float blendParam) {
        ;
    }
}
