package android.filterfw.core;

/* loaded from: classes.dex */
public class ShaderProgram extends android.filterfw.core.Program {
    private android.filterfw.core.GLEnvironment mGLEnvironment;
    private int mMaxTileSize = 0;
    private android.filterfw.core.StopWatchMap mTimer = null;
    private int shaderProgramId;

    private native boolean allocate(android.filterfw.core.GLEnvironment gLEnvironment, java.lang.String str, java.lang.String str2);

    private native boolean beginShaderDrawing();

    private native boolean compileAndLink();

    private native boolean deallocate();

    private native java.lang.Object getUniformValue(java.lang.String str);

    private static native android.filterfw.core.ShaderProgram nativeCreateIdentity(android.filterfw.core.GLEnvironment gLEnvironment);

    private native boolean setShaderAttributeValues(java.lang.String str, float[] fArr, int i);

    private native boolean setShaderAttributeVertexFrame(java.lang.String str, android.filterfw.core.VertexFrame vertexFrame, int i, int i2, int i3, int i4, boolean z);

    private native boolean setShaderBlendEnabled(boolean z);

    private native boolean setShaderBlendFunc(int i, int i2);

    private native boolean setShaderClearColor(float f, float f2, float f3);

    private native boolean setShaderClearsOutput(boolean z);

    private native boolean setShaderDrawMode(int i);

    private native boolean setShaderTileCounts(int i, int i2);

    private native boolean setShaderVertexCount(int i);

    private native boolean setTargetRegion(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    private native boolean setUniformValue(java.lang.String str, java.lang.Object obj);

    private native boolean shaderProcess(android.filterfw.core.GLFrame[] gLFrameArr, android.filterfw.core.GLFrame gLFrame);

    public native boolean setSourceRegion(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    private void setTimer() {
        this.mTimer = new android.filterfw.core.StopWatchMap();
    }

    private ShaderProgram() {
    }

    private ShaderProgram(android.filterfw.core.NativeAllocatorTag nativeAllocatorTag) {
    }

    public ShaderProgram(android.filterfw.core.FilterContext filterContext, java.lang.String str) {
        this.mGLEnvironment = getGLEnvironment(filterContext);
        allocate(this.mGLEnvironment, null, str);
        if (!compileAndLink()) {
            throw new java.lang.RuntimeException("Could not compile and link shader!");
        }
        setTimer();
    }

    public ShaderProgram(android.filterfw.core.FilterContext filterContext, java.lang.String str, java.lang.String str2) {
        this.mGLEnvironment = getGLEnvironment(filterContext);
        allocate(this.mGLEnvironment, str, str2);
        if (!compileAndLink()) {
            throw new java.lang.RuntimeException("Could not compile and link shader!");
        }
        setTimer();
    }

    public static android.filterfw.core.ShaderProgram createIdentity(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.ShaderProgram nativeCreateIdentity = nativeCreateIdentity(getGLEnvironment(filterContext));
        nativeCreateIdentity.setTimer();
        return nativeCreateIdentity;
    }

    protected void finalize() throws java.lang.Throwable {
        deallocate();
    }

    public android.filterfw.core.GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    @Override // android.filterfw.core.Program
    public void process(android.filterfw.core.Frame[] frameArr, android.filterfw.core.Frame frame) {
        if (this.mTimer.LOG_MFF_RUNNING_TIMES) {
            this.mTimer.start("glFinish");
            android.opengl.GLES20.glFinish();
            this.mTimer.stop("glFinish");
        }
        android.filterfw.core.GLFrame[] gLFrameArr = new android.filterfw.core.GLFrame[frameArr.length];
        for (int i = 0; i < frameArr.length; i++) {
            if (frameArr[i] instanceof android.filterfw.core.GLFrame) {
                gLFrameArr[i] = (android.filterfw.core.GLFrame) frameArr[i];
            } else {
                throw new java.lang.RuntimeException("ShaderProgram got non-GL frame as input " + i + "!");
            }
        }
        if (frame instanceof android.filterfw.core.GLFrame) {
            android.filterfw.core.GLFrame gLFrame = (android.filterfw.core.GLFrame) frame;
            if (this.mMaxTileSize > 0) {
                setShaderTileCounts(((frame.getFormat().getWidth() + this.mMaxTileSize) - 1) / this.mMaxTileSize, ((frame.getFormat().getHeight() + this.mMaxTileSize) - 1) / this.mMaxTileSize);
            }
            if (!shaderProcess(gLFrameArr, gLFrame)) {
                throw new java.lang.RuntimeException("Error executing ShaderProgram!");
            }
            if (this.mTimer.LOG_MFF_RUNNING_TIMES) {
                android.opengl.GLES20.glFinish();
                return;
            }
            return;
        }
        throw new java.lang.RuntimeException("ShaderProgram got non-GL output frame!");
    }

    @Override // android.filterfw.core.Program
    public void setHostValue(java.lang.String str, java.lang.Object obj) {
        if (!setUniformValue(str, obj)) {
            throw new java.lang.RuntimeException("Error setting uniform value for variable '" + str + "'!");
        }
    }

    @Override // android.filterfw.core.Program
    public java.lang.Object getHostValue(java.lang.String str) {
        return getUniformValue(str);
    }

    public void setAttributeValues(java.lang.String str, float[] fArr, int i) {
        if (!setShaderAttributeValues(str, fArr, i)) {
            throw new java.lang.RuntimeException("Error setting attribute value for attribute '" + str + "'!");
        }
    }

    public void setAttributeValues(java.lang.String str, android.filterfw.core.VertexFrame vertexFrame, int i, int i2, int i3, int i4, boolean z) {
        if (!setShaderAttributeVertexFrame(str, vertexFrame, i, i2, i3, i4, z)) {
            throw new java.lang.RuntimeException("Error setting attribute value for attribute '" + str + "'!");
        }
    }

    public void setSourceRegion(android.filterfw.geometry.Quad quad) {
        setSourceRegion(quad.p0.x, quad.p0.y, quad.p1.x, quad.p1.y, quad.p2.x, quad.p2.y, quad.p3.x, quad.p3.y);
    }

    public void setTargetRegion(android.filterfw.geometry.Quad quad) {
        setTargetRegion(quad.p0.x, quad.p0.y, quad.p1.x, quad.p1.y, quad.p2.x, quad.p2.y, quad.p3.x, quad.p3.y);
    }

    public void setSourceRect(float f, float f2, float f3, float f4) {
        float f5 = f + f3;
        float f6 = f2 + f4;
        setSourceRegion(f, f2, f5, f2, f, f6, f5, f6);
    }

    public void setTargetRect(float f, float f2, float f3, float f4) {
        float f5 = f + f3;
        float f6 = f2 + f4;
        setTargetRegion(f, f2, f5, f2, f, f6, f5, f6);
    }

    public void setClearsOutput(boolean z) {
        if (!setShaderClearsOutput(z)) {
            throw new java.lang.RuntimeException("Could not set clears-output flag to " + z + "!");
        }
    }

    public void setClearColor(float f, float f2, float f3) {
        if (!setShaderClearColor(f, f2, f3)) {
            throw new java.lang.RuntimeException("Could not set clear color to " + f + "," + f2 + "," + f3 + "!");
        }
    }

    public void setBlendEnabled(boolean z) {
        if (!setShaderBlendEnabled(z)) {
            throw new java.lang.RuntimeException("Could not set Blending " + z + "!");
        }
    }

    public void setBlendFunc(int i, int i2) {
        if (!setShaderBlendFunc(i, i2)) {
            throw new java.lang.RuntimeException("Could not set BlendFunc " + i + "," + i2 + "!");
        }
    }

    public void setDrawMode(int i) {
        if (!setShaderDrawMode(i)) {
            throw new java.lang.RuntimeException("Could not set GL draw-mode to " + i + "!");
        }
    }

    public void setVertexCount(int i) {
        if (!setShaderVertexCount(i)) {
            throw new java.lang.RuntimeException("Could not set GL vertex count to " + i + "!");
        }
    }

    public void setMaximumTileSize(int i) {
        this.mMaxTileSize = i;
    }

    public void beginDrawing() {
        if (!beginShaderDrawing()) {
            throw new java.lang.RuntimeException("Could not prepare shader-program for drawing!");
        }
    }

    private static android.filterfw.core.GLEnvironment getGLEnvironment(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.GLEnvironment gLEnvironment = filterContext != null ? filterContext.getGLEnvironment() : null;
        if (gLEnvironment == null) {
            throw new java.lang.NullPointerException("Attempting to create ShaderProgram with no GL environment in place!");
        }
        return gLEnvironment;
    }

    static {
        java.lang.System.loadLibrary("filterfw");
    }
}
