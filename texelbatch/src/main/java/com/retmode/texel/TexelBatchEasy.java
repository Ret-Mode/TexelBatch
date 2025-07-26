package com.retmode.texel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TexelBatchEasy extends TexelBatch {

    protected Texture currentTexture;

    
    public TexelBatchEasy () {
		super();
	}

	public TexelBatchEasy (int size) {
		super(size);
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
