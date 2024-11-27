package android.filterfw;

/* loaded from: classes.dex */
public class MffEnvironment {
    private android.filterfw.core.FilterContext mContext;

    protected MffEnvironment(android.filterfw.core.FrameManager frameManager) {
        frameManager = frameManager == null ? new android.filterfw.core.CachedFrameManager() : frameManager;
        this.mContext = new android.filterfw.core.FilterContext();
        this.mContext.setFrameManager(frameManager);
    }

    public android.filterfw.core.FilterContext getContext() {
        return this.mContext;
    }

    public void setGLEnvironment(android.filterfw.core.GLEnvironment gLEnvironment) {
        this.mContext.initGLEnvironment(gLEnvironment);
    }

    public void createGLEnvironment() {
        android.filterfw.core.GLEnvironment gLEnvironment = new android.filterfw.core.GLEnvironment();
        gLEnvironment.initWithNewContext();
        setGLEnvironment(gLEnvironment);
    }

    public void activateGLEnvironment() {
        if (this.mContext.getGLEnvironment() != null) {
            this.mContext.getGLEnvironment().activate();
            return;
        }
        throw new java.lang.NullPointerException("No GLEnvironment in place to activate!");
    }

    public void deactivateGLEnvironment() {
        if (this.mContext.getGLEnvironment() != null) {
            this.mContext.getGLEnvironment().deactivate();
            return;
        }
        throw new java.lang.NullPointerException("No GLEnvironment in place to deactivate!");
    }
}
