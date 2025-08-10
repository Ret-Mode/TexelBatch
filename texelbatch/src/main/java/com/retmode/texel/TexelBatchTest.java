package com.retmode.texel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TexelBatchTest extends TexelBatch {

    protected Texture currentTexture;


    public TexelBatchTest (ShaderProgram program) {
		this(1000,program);
	}

	public TexelBatchTest (int size, ShaderProgram program) {
		super(size, program);
	}

	@Override
    protected void switchTexture (Texture texture) {
		currentTexture = texture;
		super.switchTexture(texture);

	}

	@Override
	public void flush(){

        if (currentTexture != null) {
			TextureFilter originalMagFilter = currentTexture.getMagFilter();
    		TextureFilter originalMinFilter = currentTexture.getMinFilter();
			currentTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			super.flush();
			currentTexture.setFilter(originalMagFilter, originalMinFilter);
		} else {
			// let superclass handle this case
			super.flush();
		}
	}

}
