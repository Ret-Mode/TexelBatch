package com.retmode.texel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TexelBatch extends SpriteBatch {

    protected ShaderProgram program;
    protected Texture currentTexture;
    protected TextureFilter originalMagFilter;
    protected TextureFilter originalMinFilter;
    
    public TexelBatch (ShaderProgram program) {
		super(1000, program);
        program = getShader();

	}

	/** Constructs a SpriteBatch with one buffer and the default shader.
	 * @see SpriteBatch#SpriteBatch(int, ShaderProgram) */
	public TexelBatch (int size, ShaderProgram program) {
		super(size,program);
        this.program = program;
	}

	@Override
    protected void switchTexture (Texture texture) {		
		super.switchTexture(texture);
        program.setUniformf("u_textureSize", (float)texture.getWidth(), (float)texture.getHeight());
	}

}
