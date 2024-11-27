package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class InputPort extends android.filterfw.core.FilterPort {
    protected android.filterfw.core.OutputPort mSourcePort;

    public abstract void transfer(android.filterfw.core.FilterContext filterContext);

    public InputPort(android.filterfw.core.Filter filter, java.lang.String str) {
        super(filter, str);
    }

    public void setSourcePort(android.filterfw.core.OutputPort outputPort) {
        if (this.mSourcePort != null) {
            throw new java.lang.RuntimeException(this + " already connected to " + this.mSourcePort + "!");
        }
        this.mSourcePort = outputPort;
    }

    public boolean isConnected() {
        return this.mSourcePort != null;
    }

    @Override // android.filterfw.core.FilterPort
    public void open() {
        super.open();
        if (this.mSourcePort != null && !this.mSourcePort.isOpen()) {
            this.mSourcePort.open();
        }
    }

    @Override // android.filterfw.core.FilterPort
    public void close() {
        if (this.mSourcePort != null && this.mSourcePort.isOpen()) {
            this.mSourcePort.close();
        }
        super.close();
    }

    public android.filterfw.core.OutputPort getSourcePort() {
        return this.mSourcePort;
    }

    public android.filterfw.core.Filter getSourceFilter() {
        if (this.mSourcePort == null) {
            return null;
        }
        return this.mSourcePort.getFilter();
    }

    public android.filterfw.core.FrameFormat getSourceFormat() {
        return this.mSourcePort != null ? this.mSourcePort.getPortFormat() : getPortFormat();
    }

    public java.lang.Object getTarget() {
        return null;
    }

    @Override // android.filterfw.core.FilterPort
    public boolean filterMustClose() {
        return (isOpen() || !isBlocking() || hasFrame()) ? false : true;
    }

    @Override // android.filterfw.core.FilterPort
    public boolean isReady() {
        return hasFrame() || !isBlocking();
    }

    public boolean acceptsFrame() {
        return !hasFrame();
    }
}
