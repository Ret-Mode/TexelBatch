package com.retmode.texel;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TexelShader {
    public enum GLMode {
        PRE_GL_330,
        GL_330,
        PRE_GLES_3,
        GLES_3
    }

    public static String getHeader(GLMode mode) {
        switch(mode) {
            case PRE_GL_330:
            return "#version 150\n";
            case GL_330:
            return "#version 330\n";
            case PRE_GLES_3:
            return "#version 100\n";
            case GLES_3:
            return "#version 300 es\n";
            default:
            throw new RuntimeException("No such mode when selecting shader header");
        }
    }

    public static String getPrecision(GLMode mode) {
        switch(mode) {
            case PRE_GL_330: case GL_330:
            return "#define LOWP \n";
            case PRE_GLES_3: case GLES_3:
            return "#define LOWP lowp\nprecision mediump float;\n";
            default:
            throw new RuntimeException("No such mode when selecting shader header");
        }
    }

    public static String getExtension(GLMode mode){
        switch(mode) {
            case PRE_GL_330: case GL_330: case GLES_3:
            return "";
            case PRE_GLES_3: 
            return "#extension GL_OES_standard_derivatives : enable\n";
            default:
            throw new RuntimeException("No such mode when selecting extension");
        }
    }

    public static String getAttribIn(GLMode mode) {
        switch(mode) {
            case PRE_GL_330: case PRE_GLES_3:
            return "attribute";
            case GL_330: case GLES_3:
            return "in";
            default:
            throw new RuntimeException("No such mode when selecting attrib in");
        }
    }

    public static String getVaryingVert(GLMode mode) {
        switch(mode) {
            case PRE_GL_330: case PRE_GLES_3:
            return "varying";
            case GL_330: case GLES_3:
            return "out";
            default:
            throw new RuntimeException("No such mode when selecting varying in vert shader");
        }
    }

    public static String getVaryingFrag(GLMode mode) {
        switch(mode) {
            case PRE_GL_330: case PRE_GLES_3:
            return "varying";
            case GL_330: case GLES_3:
            return "in";
            default:
            throw new RuntimeException("No such mode when selecting varying in frag shader");
        }
    }

    public static String getOutColorDecl(GLMode mode) {
        switch(mode) {
            case PRE_GL_330: case PRE_GLES_3:
            return "";
            case GL_330: case GLES_3:
            return "out vec4 " + getOutColorLabel(mode) + ";\n";
            default:
            throw new RuntimeException("No such mode when selecting out color decl");
        }
    }

    public static String getOutColorLabel(GLMode mode) {
        switch(mode) {
            case PRE_GL_330: case PRE_GLES_3:
            return "gl_FragColor";
            case GL_330: case GLES_3:
            return "FragColor";
            default:
            throw new RuntimeException("No such mode when selecting out color label");
        }
    }

    public static String getTextureLookup(GLMode mode) {
        switch(mode) {
            case PRE_GL_330: case PRE_GLES_3: 
            return "texture2D";
            case GL_330: case GLES_3:
            return "texture";
            default:
            throw new RuntimeException("No such mode when selecting texture lookup");
        }
    }

    static public ShaderProgram createTexelShader (GLMode mode) {
		String header = getHeader(mode);
        String extension = getExtension(mode);
        String precision = getPrecision(mode);
        String attributeIn = getAttribIn(mode);
        String varyingVert = getVaryingVert(mode);
        String varyingFrag = getVaryingFrag(mode);
        String outColorDecl = getOutColorDecl(mode);
        String outColorLabel = getOutColorLabel(mode);
        String textureLookup  =getTextureLookup(mode);
		String vertexShader = header
            + attributeIn + " vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ attributeIn + " vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ attributeIn + " vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ varyingVert + " vec4 v_color;\n" //
			+ varyingVert + " vec2 v_texCoords;\n" //
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
		String fragmentShader = header
            + extension
            + precision
			+ varyingFrag + " LOWP vec4 v_color;\n" //
			+ varyingFrag + " vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
            + "uniform vec2 u_textureSize;\n" //
            + outColorDecl
			+ "void main()\n"//
			+ "{\n" //
			+ "  vec2 uv = v_texCoords*u_textureSize + 0.5;\n" //
			+ "  vec2 fl = floor(uv);\n" //
			+ "  vec2 fr = fract(uv);\n" //
			+ "  vec2 aa = abs(dFdx(uv) + dFdy(uv))*0.75;\n" //
			+ "  fr = smoothstep( vec2(0.5)-aa, vec2(0.5)+aa, fr);\n" //
			+ "  uv = (fl+fr-0.5) / u_textureSize;\n" //
			+ outColorLabel + " = v_color * " + textureLookup + "(u_texture, uv.xy);\n" //
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}

    static public ShaderProgram createDefaultBatchShader (GLMode mode) {
		String header = getHeader(mode);
        String precision = getPrecision(mode);
        String extension = getExtension(mode);
        String attributeIn = getAttribIn(mode);
        String varyingVert = getVaryingVert(mode);
        String varyingFrag = getVaryingFrag(mode);
        String outColorDecl = getOutColorDecl(mode);
        String outColorLabel = getOutColorLabel(mode);
        String textureLookup  = getTextureLookup(mode);
		String vertexShader = header
            + attributeIn + " vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ attributeIn + " vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ attributeIn + " vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ varyingVert + " vec4 v_color;\n" //
			+ varyingVert + " vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";

		String fragmentShader = header
            + precision
            + extension
			+ varyingFrag + " LOWP vec4 v_color;\n" //
			+ varyingFrag + " vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
            + outColorDecl
			+ "void main()\n"//
			+ "{\n" //
			+ outColorLabel + " = v_color * " + textureLookup + "(u_texture, v_texCoords);\n"
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}
}
