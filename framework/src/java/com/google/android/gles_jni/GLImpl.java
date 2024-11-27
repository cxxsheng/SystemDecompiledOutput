package com.google.android.gles_jni;

/* loaded from: classes5.dex */
public class GLImpl implements javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL10Ext, javax.microedition.khronos.opengles.GL11, javax.microedition.khronos.opengles.GL11Ext, javax.microedition.khronos.opengles.GL11ExtensionPack {
    private boolean haveCheckedExtensions;
    private boolean have_OES_blend_equation_separate;
    private boolean have_OES_blend_subtract;
    private boolean have_OES_framebuffer_object;
    private boolean have_OES_texture_cube_map;
    java.nio.Buffer _colorPointer = null;
    java.nio.Buffer _normalPointer = null;
    java.nio.Buffer _texCoordPointer = null;
    java.nio.Buffer _vertexPointer = null;
    java.nio.Buffer _pointSizePointerOES = null;
    java.nio.Buffer _matrixIndexPointerOES = null;
    java.nio.Buffer _weightPointerOES = null;

    private static native void _nativeClassInit();

    private native void glColorPointerBounds(int i, int i2, int i3, java.nio.Buffer buffer, int i4);

    private native void glMatrixIndexPointerOESBounds(int i, int i2, int i3, java.nio.Buffer buffer, int i4);

    private native void glNormalPointerBounds(int i, int i2, java.nio.Buffer buffer, int i3);

    private native void glPointSizePointerOESBounds(int i, int i2, java.nio.Buffer buffer, int i3);

    private native void glTexCoordPointerBounds(int i, int i2, int i3, java.nio.Buffer buffer, int i4);

    private native void glVertexPointerBounds(int i, int i2, int i3, java.nio.Buffer buffer, int i4);

    private native void glWeightPointerOESBounds(int i, int i2, int i3, java.nio.Buffer buffer, int i4);

    public native java.lang.String _glGetString(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glActiveTexture(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glAlphaFunc(int i, float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glAlphaFuncx(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glBindBuffer(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glBindFramebufferOES(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glBindRenderbufferOES(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glBindTexture(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glBlendEquation(int i);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glBlendEquationSeparate(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glBlendFunc(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glBlendFuncSeparate(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glBufferData(int i, int i2, java.nio.Buffer buffer, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glBufferSubData(int i, int i2, int i3, java.nio.Buffer buffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native int glCheckFramebufferStatusOES(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glClear(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glClearColor(float f, float f2, float f3, float f4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glClearColorx(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glClearDepthf(float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glClearDepthx(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glClearStencil(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glClientActiveTexture(int i);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glClipPlanef(int i, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glClipPlanef(int i, float[] fArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glClipPlanex(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glClipPlanex(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glColor4f(float f, float f2, float f3, float f4);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glColor4ub(byte b, byte b2, byte b3, byte b4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glColor4x(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glColorMask(boolean z, boolean z2, boolean z3, boolean z4);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glColorPointer(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glCompressedTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.nio.Buffer buffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glCompressedTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.nio.Buffer buffer);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glCopyTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glCopyTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glCullFace(int i);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glCurrentPaletteMatrixOES(int i);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glDeleteBuffers(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glDeleteBuffers(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glDeleteFramebuffersOES(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glDeleteFramebuffersOES(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glDeleteRenderbuffersOES(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glDeleteRenderbuffersOES(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDeleteTextures(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDeleteTextures(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDepthFunc(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDepthMask(boolean z);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDepthRangef(float f, float f2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDepthRangex(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDisable(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDisableClientState(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDrawArrays(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glDrawElements(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glDrawElements(int i, int i2, int i3, java.nio.Buffer buffer);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexfOES(float f, float f2, float f3, float f4, float f5);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexfvOES(java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexfvOES(float[] fArr, int i);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexiOES(int i, int i2, int i3, int i4, int i5);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexivOES(java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexivOES(int[] iArr, int i);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexsOES(short s, short s2, short s3, short s4, short s5);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexsvOES(java.nio.ShortBuffer shortBuffer);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexsvOES(short[] sArr, int i);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexxOES(int i, int i2, int i3, int i4, int i5);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexxvOES(java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glDrawTexxvOES(int[] iArr, int i);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glEnable(int i);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext
    public native void glEnableClientState(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFinish();

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFlush();

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFogf(int i, float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFogfv(int i, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFogfv(int i, float[] fArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFogx(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFogxv(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFogxv(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glFramebufferRenderbufferOES(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glFramebufferTexture2DOES(int i, int i2, int i3, int i4, int i5);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFrontFace(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFrustumf(float f, float f2, float f3, float f4, float f5, float f6);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glFrustumx(int i, int i2, int i3, int i4, int i5, int i6);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGenBuffers(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGenBuffers(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGenFramebuffersOES(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGenFramebuffersOES(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGenRenderbuffersOES(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGenRenderbuffersOES(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glGenTextures(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glGenTextures(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGenerateMipmapOES(int i);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetBooleanv(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetBooleanv(int i, boolean[] zArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetBufferParameteriv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetBufferParameteriv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetClipPlanef(int i, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetClipPlanef(int i, float[] fArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetClipPlanex(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetClipPlanex(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native int glGetError();

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetFixedv(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetFixedv(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetFloatv(int i, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetFloatv(int i, float[] fArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, int[] iArr, int i4);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetIntegerv(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetIntegerv(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetLightfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetLightfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetLightxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetLightxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetMaterialfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetMaterialfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetMaterialxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetMaterialxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetRenderbufferParameterivOES(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetRenderbufferParameterivOES(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexEnviv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexEnviv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexEnvxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexEnvxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetTexGenfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetTexGenfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetTexGeniv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetTexGeniv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetTexGenxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glGetTexGenxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexParameterfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexParameterfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexParameteriv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexParameteriv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexParameterxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glGetTexParameterxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glHint(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native boolean glIsBuffer(int i);

    @Override // javax.microedition.khronos.opengles.GL11
    public native boolean glIsEnabled(int i);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native boolean glIsFramebufferOES(int i);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native boolean glIsRenderbufferOES(int i);

    @Override // javax.microedition.khronos.opengles.GL11
    public native boolean glIsTexture(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightModelf(int i, float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightModelfv(int i, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightModelfv(int i, float[] fArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightModelx(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightModelxv(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightModelxv(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightf(int i, int i2, float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightx(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLightxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLineWidth(float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLineWidthx(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLoadIdentity();

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLoadMatrixf(java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLoadMatrixf(float[] fArr, int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLoadMatrixx(java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLoadMatrixx(int[] iArr, int i);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glLoadPaletteFromModelViewMatrixOES();

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glLogicOp(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMaterialf(int i, int i2, float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMaterialfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMaterialfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMaterialx(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMaterialxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMaterialxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glMatrixIndexPointerOES(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMatrixMode(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMultMatrixf(java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMultMatrixf(float[] fArr, int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMultMatrixx(java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMultMatrixx(int[] iArr, int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMultiTexCoord4f(int i, float f, float f2, float f3, float f4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glMultiTexCoord4x(int i, int i2, int i3, int i4, int i5);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glNormal3f(float f, float f2, float f3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glNormal3x(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glNormalPointer(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glOrthof(float f, float f2, float f3, float f4, float f5, float f6);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glOrthox(int i, int i2, int i3, int i4, int i5, int i6);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glPixelStorei(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glPointParameterf(int i, float f);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glPointParameterfv(int i, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glPointParameterfv(int i, float[] fArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glPointParameterx(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glPointParameterxv(int i, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glPointParameterxv(int i, int[] iArr, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glPointSize(float f);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glPointSizex(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glPolygonOffset(float f, float f2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glPolygonOffsetx(int i, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glPopMatrix();

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glPushMatrix();

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public native int glQueryMatrixxOES(java.nio.IntBuffer intBuffer, java.nio.IntBuffer intBuffer2);

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public native int glQueryMatrixxOES(int[] iArr, int i, int[] iArr2, int i2);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glReadPixels(int i, int i2, int i3, int i4, int i5, int i6, java.nio.Buffer buffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glRenderbufferStorageOES(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glRotatef(float f, float f2, float f3, float f4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glRotatex(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glSampleCoverage(float f, boolean z);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glSampleCoveragex(int i, boolean z);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glScalef(float f, float f2, float f3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glScalex(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glScissor(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glShadeModel(int i);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glStencilFunc(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glStencilMask(int i);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glStencilOp(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexCoordPointer(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexEnvf(int i, int i2, float f);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexEnvfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexEnvfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexEnvi(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexEnviv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexEnviv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexEnvx(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexEnvxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexEnvxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGenf(int i, int i2, float f);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGenfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGenfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGeni(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGeniv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGeniv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGenx(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGenxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexGenxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.nio.Buffer buffer);

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public native void glTexParameterf(int i, int i2, float f);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexParameterfv(int i, int i2, java.nio.FloatBuffer floatBuffer);

    @Override // javax.microedition.khronos.opengles.GL11, javax.microedition.khronos.opengles.GL11Ext
    public native void glTexParameterfv(int i, int i2, float[] fArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexParameteri(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexParameteriv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexParameteriv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glTexParameterx(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexParameterxv(int i, int i2, java.nio.IntBuffer intBuffer);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glTexParameterxv(int i, int i2, int[] iArr, int i3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.nio.Buffer buffer);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glTranslatef(float f, float f2, float f3);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glTranslatex(int i, int i2, int i3);

    @Override // javax.microedition.khronos.opengles.GL11
    public native void glVertexPointer(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL10
    public native void glViewport(int i, int i2, int i3, int i4);

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public native void glWeightPointerOES(int i, int i2, int i3, int i4);

    static {
        _nativeClassInit();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetPointerv(int i, java.nio.Buffer[] bufferArr) {
        throw new java.lang.UnsupportedOperationException("glGetPointerv");
    }

    private static boolean allowIndirectBuffers(java.lang.String str) {
        int i;
        try {
            android.content.pm.ApplicationInfo applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(str, 0L, android.os.UserHandle.myUserId());
            if (applicationInfo == null) {
                i = 0;
            } else {
                i = applicationInfo.targetSdkVersion;
            }
        } catch (android.os.RemoteException e) {
            i = 0;
        }
        android.util.Log.e("OpenGLES", java.lang.String.format("Application %s (SDK target %d) called a GL11 Pointer method with an indirect Buffer.", str, java.lang.Integer.valueOf(i)));
        if (i > 3) {
            return false;
        }
        return true;
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColorPointer(int i, int i2, int i3, java.nio.Buffer buffer) {
        glColorPointerBounds(i, i2, i3, buffer, buffer.remaining());
        if (i == 4) {
            if ((i2 == 5126 || i2 == 5121 || i2 == 5132) && i3 >= 0) {
                this._colorPointer = buffer;
            }
        }
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public java.lang.String glGetString(int i) {
        return _glGetString(i);
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormalPointer(int i, int i2, java.nio.Buffer buffer) {
        glNormalPointerBounds(i, i2, buffer, buffer.remaining());
        if ((i == 5126 || i == 5120 || i == 5122 || i == 5132) && i2 >= 0) {
            this._normalPointer = buffer;
        }
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexCoordPointer(int i, int i2, int i3, java.nio.Buffer buffer) {
        glTexCoordPointerBounds(i, i2, i3, buffer, buffer.remaining());
        if (i == 2 || i == 3 || i == 4) {
            if ((i2 == 5126 || i2 == 5120 || i2 == 5122 || i2 == 5132) && i3 >= 0) {
                this._texCoordPointer = buffer;
            }
        }
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glVertexPointer(int i, int i2, int i3, java.nio.Buffer buffer) {
        glVertexPointerBounds(i, i2, i3, buffer, buffer.remaining());
        if (i == 2 || i == 3 || i == 4) {
            if ((i2 == 5126 || i2 == 5120 || i2 == 5122 || i2 == 5132) && i3 >= 0) {
                this._vertexPointer = buffer;
            }
        }
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointSizePointerOES(int i, int i2, java.nio.Buffer buffer) {
        glPointSizePointerOESBounds(i, i2, buffer, buffer.remaining());
        if ((i == 5126 || i == 5132) && i2 >= 0) {
            this._pointSizePointerOES = buffer;
        }
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glMatrixIndexPointerOES(int i, int i2, int i3, java.nio.Buffer buffer) {
        glMatrixIndexPointerOESBounds(i, i2, i3, buffer, buffer.remaining());
        if (i == 2 || i == 3 || i == 4) {
            if ((i2 == 5126 || i2 == 5120 || i2 == 5122 || i2 == 5132) && i3 >= 0) {
                this._matrixIndexPointerOES = buffer;
            }
        }
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glWeightPointerOES(int i, int i2, int i3, java.nio.Buffer buffer) {
        glWeightPointerOESBounds(i, i2, i3, buffer, buffer.remaining());
    }
}
