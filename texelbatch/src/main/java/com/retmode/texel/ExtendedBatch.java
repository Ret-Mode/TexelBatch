package com.retmode.texel;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface ExtendedBatch extends Batch {

    void setUvOffsetInPixels(float scale);

    void setBlendParam(float blendParam);

}
