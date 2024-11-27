package android.media.effect;

/* loaded from: classes2.dex */
public class EffectContext {
    private android.media.effect.EffectFactory mFactory;
    private final int GL_STATE_FBO = 0;
    private final int GL_STATE_PROGRAM = 1;
    private final int GL_STATE_ARRAYBUFFER = 2;
    private final int GL_STATE_COUNT = 3;
    private int[] mOldState = new int[3];
    android.filterfw.core.FilterContext mFilterContext = new android.filterfw.core.FilterContext();

    public static android.media.effect.EffectContext createWithCurrentGlContext() {
        android.media.effect.EffectContext effectContext = new android.media.effect.EffectContext();
        effectContext.initInCurrentGlContext();
        return effectContext;
    }

    public android.media.effect.EffectFactory getFactory() {
        return this.mFactory;
    }

    public void release() {
        this.mFilterContext.tearDown();
        this.mFilterContext = null;
    }

    private EffectContext() {
        this.mFilterContext.setFrameManager(new android.filterfw.core.CachedFrameManager());
        this.mFactory = new android.media.effect.EffectFactory(this);
    }

    private void initInCurrentGlContext() {
        if (!android.filterfw.core.GLEnvironment.isAnyContextActive()) {
            throw new java.lang.RuntimeException("Attempting to initialize EffectContext with no active GL context!");
        }
        android.filterfw.core.GLEnvironment gLEnvironment = new android.filterfw.core.GLEnvironment();
        gLEnvironment.initWithCurrentContext();
        this.mFilterContext.initGLEnvironment(gLEnvironment);
    }

    final void assertValidGLState() {
        android.filterfw.core.GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment == null || !gLEnvironment.isContextActive()) {
            if (android.filterfw.core.GLEnvironment.isAnyContextActive()) {
                throw new java.lang.RuntimeException("Applying effect in wrong GL context!");
            }
            throw new java.lang.RuntimeException("Attempting to apply effect without valid GL context!");
        }
    }

    final void saveGLState() {
        android.opengl.GLES20.glGetIntegerv(36006, this.mOldState, 0);
        android.opengl.GLES20.glGetIntegerv(android.opengl.GLES20.GL_CURRENT_PROGRAM, this.mOldState, 1);
        android.opengl.GLES20.glGetIntegerv(34964, this.mOldState, 2);
    }

    final void restoreGLState() {
        android.opengl.GLES20.glBindFramebuffer(36160, this.mOldState[0]);
        android.opengl.GLES20.glUseProgram(this.mOldState[1]);
        android.opengl.GLES20.glBindBuffer(34962, this.mOldState[2]);
    }
}
