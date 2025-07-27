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
    
    public TexelBatch () {
		super(1000, createDefaultShader ());
        program = getShader();

	}

	/** Constructs a SpriteBatch with one buffer and the default shader.
	 * @see SpriteBatch#SpriteBatch(int, ShaderProgram) */
	public TexelBatch (int size) {
		super(size,createDefaultShader());
        program = getShader();
	}

	/** Returns a new instance of the default shader used by TexelBatch for GL2 when no shader is specified. */
	static public ShaderProgram createDefaultShader () {
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";



		// SHADER BELOW IS IMPLEMENTED FROM THE SHADERTOY AT https://www.shadertoy.com/view/MllBWf
		
		// #define PI 3.14159265359
		// #define rot(a) mat2(cos(a+PI*vec4(0,1.5,0.5,0)))

		// vec4 myTexture(vec2 uv) {
			
		//     vec2 res = iChannelResolution[0].xy;
		//     uv = uv*res + 0.5;
			
		//     // tweak fractionnal value of the texture coordinate
		//     vec2 fl = floor(uv);
		//     vec2 fr = fract(uv);
		//     vec2 aa = fwidth(uv)*0.75;
		//     fr = smoothstep( vec2(0.5)-aa, vec2(0.5)+aa, fr);
			
		//     uv = (fl+fr-0.5) / res;
		//     return texture(iChannel0, uv);
			
		// }

		// vec4 nearest(vec2 uv) {
		//     uv *= iChannelResolution[0].xy;
		//     uv = floor(uv)+0.5;
		//     uv /= iChannelResolution[0].xy;
		//     return textureLod(iChannel0, uv, 0.0);
		// }

		// vec4 mipmap(vec2 uv) {
		//     return texture(iChannel0, uv);
		// }

		// void mainImage( out vec4 fragColor, in vec2 fragCoord ) {
		// 	vec2 uv = (fragCoord.xy - iResolution.xy*0.5) / iResolution.x;
		//     vec2 prevUV = uv;
			
		//     uv *= rot(iTime*0.2);
		//     uv.x *= iChannelResolution[0].y / iChannelResolution[0].x;
		//     uv *= 4.0 + exp2(iMouse.x*0.01);
			
		// 	fragColor = myTexture(uv).rrrr;
			
		//     if (prevUV.x < -0.25) fragColor = nearest(uv).rrrr;
		//     if (prevUV.x > 0.25) fragColor = mipmap(uv).rrrr;
			
		//     if (abs(prevUV.x+0.25) < 0.005) fragColor.rgb = vec3(0);
		//     if (abs(prevUV.x-0.25) < 0.005) fragColor.rgb = vec3(0);
			
			
			
			
			
		//}
		String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n" //
			+ "#extension GL_OES_standard_derivatives : enable\n"
			+ "precision mediump float;\n" //
			+ "#else\n" //
			+ "#define LOWP \n" //
			+ "#endif\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
            + "uniform vec2 u_textureSize;\n" //
			+ "void main()\n"//
			+ "{\n" //
			+ "  vec2 uv = v_texCoords*u_textureSize + 0.5;\n" //
			+ "  vec2 fl = floor(uv);\n" //
			+ "  vec2 fr = fract(uv);\n" //
			+ "  vec2 aa = abs(dFdx(uv) + dFdy(uv))*0.75;\n" //
			+ "  fr = smoothstep( vec2(0.5)-aa, vec2(0.5)+aa, fr);\n" //
			+ "  uv = (fl+fr-0.5) / u_textureSize;\n" //
			+ "  gl_FragColor = v_color * texture2D(u_texture, uv);\n" //
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}

	@Override
    protected void switchTexture (Texture texture) {		
		super.switchTexture(texture);
        program.setUniformf("u_textureSize", (float)texture.getWidth(), (float)texture.getHeight());
	}

}
