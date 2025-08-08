# TexelBatch

This library replaces Spritebatch functionality to correctly draw pixel art, or serve as a template to implement
other similiar functionalities.

It is based on the shader found here:

https://www.shadertoy.com/view/MllBWf

which is licensed under a CC0 1.0 Universal License
https://creativecommons.org/publicdomain/zero/1.0/

The rest of this repository is under Apache-2 license to match licensing of libgdx.

# Usage

The first step is to actually estimate which implementation You need.
If You are targeting desktop, You probably want TexelBatchGL. Webgl and mobile will need TexelBatchGLES (with sources for GWT).
(Android is not yet tested).

1. Add texel batch version to gradle.properties file:
```
texelBatchVersion=c188def03b
```

2. Add the following to dependencies build.gradle in core project:
```
implementation "com.github.Ret-Mode.TexelBatch:texelbatch:$texelBatchVersion"
```

3. Modify rest of the projects:
When building desktop, add to dependencies build.gradle in lwjgl project:
```
implementation "com.github.Ret-Mode.TexelBatch:texelbatch:$texelBatchVersion"
implementation "com.github.Ret-Mode.TexelBatch:texelbatchgl:$texelBatchVersion"
```

When building gwt, add to dependencies build.gradle in html project:
```
implementation "com.github.Ret-Mode.TexelBatch:texelbatch:$texelBatchVersion"
implementation "com.github.Ret-Mode.TexelBatch:texelbatch:$texelBatchVersion:sources"
implementation "com.github.Ret-Mode.TexelBatch:texelbatchgles:$texelBatchVersion"
implementation "com.github.Ret-Mode.TexelBatch:texelbatchgles:$texelBatchVersion:sources"
```
and add to GdxDefinition.gwt.xml:
```
  <inherits name='com.retmode.texel' />
```

When building mobile, add to dependencies build.gradle project:
```
implementation "com.github.Ret-Mode.TexelBatch:texelbatch:$texelBatchVersion"
implementation "com.github.Ret-Mode.TexelBatch:texelbatchgles:$texelBatchVersion"
```

4. In each of the projects excluding core, add the following initialization before creating main app:

For GL version:
```
TexelBatchProviderGL.initialize();
```

For GLES version:
```
TexelBatchProviderGLES.initialize();
```

5. In Main, in place of usual generation of SpriteBatch, add:
```
SpriteBatch batch = TexelBatchProvider.get().provide(TexelBatchProvider.TexelBatchVersion.TEXEL);
```

6. All textures used by this batch should have filtering set to LINEAR.
In case You do not want to change Your setup, but quickly check if this library us for You, You could try to create batch as follows:
```
SpriteBatch batch = TexelBatchProvider.get().provide(TexelBatchProvider.TexelBatchVersion.TEST);
```
This option will set filtering for You during draw calls, and restore previous filtering after the draw. Please note that this option is very slow and provided only for testing.

7. If You want to compare with default sprite batch, You can also try:
```
SpriteBatch batch = TexelBatchProvider.get().provide(TexelBatchProvider.TexelBatchVersion.YE_GOOD_OLD_BATCH);
```
To create default sprite batch.


# Final notes:

This lib works with gl/gles contexts 2 and 3. Default sprite batch created by this lib is also compatible with mentioned contexts.


# Whats next

You can read more about pixel art rendering at:

https://jorenjoestar.github.io/post/pixel_art_filtering