package android.filterfw.core;

/* loaded from: classes.dex */
public class SimpleFrameManager extends android.filterfw.core.FrameManager {
    @Override // android.filterfw.core.FrameManager
    public android.filterfw.core.Frame newFrame(android.filterfw.core.FrameFormat frameFormat) {
        return createNewFrame(frameFormat);
    }

    @Override // android.filterfw.core.FrameManager
    public android.filterfw.core.Frame newBoundFrame(android.filterfw.core.FrameFormat frameFormat, int i, long j) {
        switch (frameFormat.getTarget()) {
            case 3:
                android.filterfw.core.GLFrame gLFrame = new android.filterfw.core.GLFrame(frameFormat, this, i, j);
                gLFrame.init(getGLEnvironment());
                return gLFrame;
            default:
                throw new java.lang.RuntimeException("Attached frames are not supported for target type: " + android.filterfw.core.FrameFormat.targetToString(frameFormat.getTarget()) + "!");
        }
    }

    private android.filterfw.core.Frame createNewFrame(android.filterfw.core.FrameFormat frameFormat) {
        switch (frameFormat.getTarget()) {
            case 1:
                return new android.filterfw.core.SimpleFrame(frameFormat, this);
            case 2:
                return new android.filterfw.core.NativeFrame(frameFormat, this);
            case 3:
                android.filterfw.core.GLFrame gLFrame = new android.filterfw.core.GLFrame(frameFormat, this);
                gLFrame.init(getGLEnvironment());
                return gLFrame;
            case 4:
                return new android.filterfw.core.VertexFrame(frameFormat, this);
            default:
                throw new java.lang.RuntimeException("Unsupported frame target type: " + android.filterfw.core.FrameFormat.targetToString(frameFormat.getTarget()) + "!");
        }
    }

    @Override // android.filterfw.core.FrameManager
    public android.filterfw.core.Frame retainFrame(android.filterfw.core.Frame frame) {
        frame.incRefCount();
        return frame;
    }

    @Override // android.filterfw.core.FrameManager
    public android.filterfw.core.Frame releaseFrame(android.filterfw.core.Frame frame) {
        int decRefCount = frame.decRefCount();
        if (decRefCount == 0 && frame.hasNativeAllocation()) {
            frame.releaseNativeAllocation();
            return null;
        }
        if (decRefCount < 0) {
            throw new java.lang.RuntimeException("Frame reference count dropped below 0!");
        }
        return frame;
    }
}
