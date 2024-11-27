package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class FilterPort {
    private static final java.lang.String TAG = "FilterPort";
    protected android.filterfw.core.Filter mFilter;
    protected java.lang.String mName;
    protected android.filterfw.core.FrameFormat mPortFormat;
    protected boolean mIsBlocking = true;
    protected boolean mIsOpen = false;
    protected boolean mChecksType = false;
    private boolean mLogVerbose = android.util.Log.isLoggable(TAG, 2);

    public abstract void clear();

    public abstract boolean filterMustClose();

    public abstract boolean hasFrame();

    public abstract boolean isReady();

    public abstract android.filterfw.core.Frame pullFrame();

    public abstract void pushFrame(android.filterfw.core.Frame frame);

    public abstract void setFrame(android.filterfw.core.Frame frame);

    public FilterPort(android.filterfw.core.Filter filter, java.lang.String str) {
        this.mName = str;
        this.mFilter = filter;
    }

    public boolean isAttached() {
        return this.mFilter != null;
    }

    public android.filterfw.core.FrameFormat getPortFormat() {
        return this.mPortFormat;
    }

    public void setPortFormat(android.filterfw.core.FrameFormat frameFormat) {
        this.mPortFormat = frameFormat;
    }

    public android.filterfw.core.Filter getFilter() {
        return this.mFilter;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public void setBlocking(boolean z) {
        this.mIsBlocking = z;
    }

    public void setChecksType(boolean z) {
        this.mChecksType = z;
    }

    public void open() {
        if (!this.mIsOpen && this.mLogVerbose) {
            android.util.Log.v(TAG, "Opening " + this);
        }
        this.mIsOpen = true;
    }

    public void close() {
        if (this.mIsOpen && this.mLogVerbose) {
            android.util.Log.v(TAG, "Closing " + this);
        }
        this.mIsOpen = false;
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public boolean isBlocking() {
        return this.mIsBlocking;
    }

    public java.lang.String toString() {
        return "port '" + this.mName + "' of " + this.mFilter;
    }

    protected void assertPortIsOpen() {
        if (!isOpen()) {
            throw new java.lang.RuntimeException("Illegal operation on closed " + this + "!");
        }
    }

    protected void checkFrameType(android.filterfw.core.Frame frame, boolean z) {
        if ((this.mChecksType || z) && this.mPortFormat != null && !frame.getFormat().isCompatibleWith(this.mPortFormat)) {
            throw new java.lang.RuntimeException("Frame passed to " + this + " is of incorrect type! Expected " + this.mPortFormat + " but got " + frame.getFormat());
        }
    }

    protected void checkFrameManager(android.filterfw.core.Frame frame, android.filterfw.core.FilterContext filterContext) {
        if (frame.getFrameManager() != null && frame.getFrameManager() != filterContext.getFrameManager()) {
            throw new java.lang.RuntimeException("Frame " + frame + " is managed by foreign FrameManager! ");
        }
    }
}
