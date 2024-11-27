package android.filterfw.core;

/* loaded from: classes.dex */
public class OutputPort extends android.filterfw.core.FilterPort {
    protected android.filterfw.core.InputPort mBasePort;
    protected android.filterfw.core.InputPort mTargetPort;

    public OutputPort(android.filterfw.core.Filter filter, java.lang.String str) {
        super(filter, str);
    }

    public void connectTo(android.filterfw.core.InputPort inputPort) {
        if (this.mTargetPort != null) {
            throw new java.lang.RuntimeException(this + " already connected to " + this.mTargetPort + "!");
        }
        this.mTargetPort = inputPort;
        this.mTargetPort.setSourcePort(this);
    }

    public boolean isConnected() {
        return this.mTargetPort != null;
    }

    @Override // android.filterfw.core.FilterPort
    public void open() {
        super.open();
        if (this.mTargetPort != null && !this.mTargetPort.isOpen()) {
            this.mTargetPort.open();
        }
    }

    @Override // android.filterfw.core.FilterPort
    public void close() {
        super.close();
        if (this.mTargetPort != null && this.mTargetPort.isOpen()) {
            this.mTargetPort.close();
        }
    }

    public android.filterfw.core.InputPort getTargetPort() {
        return this.mTargetPort;
    }

    public android.filterfw.core.Filter getTargetFilter() {
        if (this.mTargetPort == null) {
            return null;
        }
        return this.mTargetPort.getFilter();
    }

    public void setBasePort(android.filterfw.core.InputPort inputPort) {
        this.mBasePort = inputPort;
    }

    public android.filterfw.core.InputPort getBasePort() {
        return this.mBasePort;
    }

    @Override // android.filterfw.core.FilterPort
    public boolean filterMustClose() {
        return !isOpen() && isBlocking();
    }

    @Override // android.filterfw.core.FilterPort
    public boolean isReady() {
        return (isOpen() && this.mTargetPort.acceptsFrame()) || !isBlocking();
    }

    @Override // android.filterfw.core.FilterPort
    public void clear() {
        if (this.mTargetPort != null) {
            this.mTargetPort.clear();
        }
    }

    @Override // android.filterfw.core.FilterPort
    public void pushFrame(android.filterfw.core.Frame frame) {
        if (this.mTargetPort == null) {
            throw new java.lang.RuntimeException("Attempting to push frame on unconnected port: " + this + "!");
        }
        this.mTargetPort.pushFrame(frame);
    }

    @Override // android.filterfw.core.FilterPort
    public void setFrame(android.filterfw.core.Frame frame) {
        assertPortIsOpen();
        if (this.mTargetPort == null) {
            throw new java.lang.RuntimeException("Attempting to set frame on unconnected port: " + this + "!");
        }
        this.mTargetPort.setFrame(frame);
    }

    @Override // android.filterfw.core.FilterPort
    public android.filterfw.core.Frame pullFrame() {
        throw new java.lang.RuntimeException("Cannot pull frame on " + this + "!");
    }

    @Override // android.filterfw.core.FilterPort
    public boolean hasFrame() {
        if (this.mTargetPort == null) {
            return false;
        }
        return this.mTargetPort.hasFrame();
    }

    @Override // android.filterfw.core.FilterPort
    public java.lang.String toString() {
        return "output " + super.toString();
    }
}
