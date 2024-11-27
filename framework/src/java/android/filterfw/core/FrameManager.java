package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class FrameManager {
    private android.filterfw.core.FilterContext mContext;

    public abstract android.filterfw.core.Frame newBoundFrame(android.filterfw.core.FrameFormat frameFormat, int i, long j);

    public abstract android.filterfw.core.Frame newFrame(android.filterfw.core.FrameFormat frameFormat);

    public abstract android.filterfw.core.Frame releaseFrame(android.filterfw.core.Frame frame);

    public abstract android.filterfw.core.Frame retainFrame(android.filterfw.core.Frame frame);

    public android.filterfw.core.Frame duplicateFrame(android.filterfw.core.Frame frame) {
        android.filterfw.core.Frame newFrame = newFrame(frame.getFormat());
        newFrame.setDataFromFrame(frame);
        return newFrame;
    }

    public android.filterfw.core.Frame duplicateFrameToTarget(android.filterfw.core.Frame frame, int i) {
        android.filterfw.core.MutableFrameFormat mutableCopy = frame.getFormat().mutableCopy();
        mutableCopy.setTarget(i);
        android.filterfw.core.Frame newFrame = newFrame(mutableCopy);
        newFrame.setDataFromFrame(frame);
        return newFrame;
    }

    public android.filterfw.core.FilterContext getContext() {
        return this.mContext;
    }

    public android.filterfw.core.GLEnvironment getGLEnvironment() {
        if (this.mContext != null) {
            return this.mContext.getGLEnvironment();
        }
        return null;
    }

    public void tearDown() {
    }

    void setContext(android.filterfw.core.FilterContext filterContext) {
        this.mContext = filterContext;
    }
}
