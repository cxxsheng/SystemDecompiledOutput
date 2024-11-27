package android.filterfw.core;

/* loaded from: classes.dex */
public class StreamPort extends android.filterfw.core.InputPort {
    private android.filterfw.core.Frame mFrame;
    private boolean mPersistent;

    public StreamPort(android.filterfw.core.Filter filter, java.lang.String str) {
        super(filter, str);
    }

    @Override // android.filterfw.core.FilterPort
    public void clear() {
        if (this.mFrame != null) {
            this.mFrame.release();
            this.mFrame = null;
        }
    }

    @Override // android.filterfw.core.FilterPort
    public void setFrame(android.filterfw.core.Frame frame) {
        assignFrame(frame, true);
    }

    @Override // android.filterfw.core.FilterPort
    public void pushFrame(android.filterfw.core.Frame frame) {
        assignFrame(frame, false);
    }

    protected synchronized void assignFrame(android.filterfw.core.Frame frame, boolean z) {
        assertPortIsOpen();
        checkFrameType(frame, z);
        if (z) {
            if (this.mFrame != null) {
                this.mFrame.release();
            }
        } else if (this.mFrame != null) {
            throw new java.lang.RuntimeException("Attempting to push more than one frame on port: " + this + "!");
        }
        this.mFrame = frame.retain();
        this.mFrame.markReadOnly();
        this.mPersistent = z;
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized android.filterfw.core.Frame pullFrame() {
        android.filterfw.core.Frame frame;
        if (this.mFrame == null) {
            throw new java.lang.RuntimeException("No frame available to pull on port: " + this + "!");
        }
        frame = this.mFrame;
        if (this.mPersistent) {
            this.mFrame.retain();
        } else {
            this.mFrame = null;
        }
        return frame;
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized boolean hasFrame() {
        return this.mFrame != null;
    }

    @Override // android.filterfw.core.FilterPort
    public java.lang.String toString() {
        return "input " + super.toString();
    }

    @Override // android.filterfw.core.InputPort
    public synchronized void transfer(android.filterfw.core.FilterContext filterContext) {
        if (this.mFrame != null) {
            checkFrameManager(this.mFrame, filterContext);
        }
    }
}
