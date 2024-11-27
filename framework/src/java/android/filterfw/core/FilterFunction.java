package android.filterfw.core;

/* loaded from: classes.dex */
public class FilterFunction {
    private android.filterfw.core.Filter mFilter;
    private android.filterfw.core.FilterContext mFilterContext;
    private boolean mFilterIsSetup = false;
    private android.filterfw.core.FilterFunction.FrameHolderPort[] mResultHolders;

    private class FrameHolderPort extends android.filterfw.core.StreamPort {
        public FrameHolderPort() {
            super(null, "holder");
        }
    }

    public FilterFunction(android.filterfw.core.FilterContext filterContext, android.filterfw.core.Filter filter) {
        this.mFilterContext = filterContext;
        this.mFilter = filter;
    }

    public android.filterfw.core.Frame execute(android.filterfw.core.KeyValueMap keyValueMap) {
        boolean z;
        android.filterfw.core.Frame frame;
        int numberOfOutputs = this.mFilter.getNumberOfOutputs();
        if (numberOfOutputs > 1) {
            throw new java.lang.RuntimeException("Calling execute on filter " + this.mFilter + " with multiple outputs! Use executeMulti() instead!");
        }
        if (!this.mFilterIsSetup) {
            connectFilterOutputs();
            this.mFilterIsSetup = true;
        }
        android.filterfw.core.GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment != null && !gLEnvironment.isActive()) {
            gLEnvironment.activate();
            z = true;
        } else {
            z = false;
        }
        for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : keyValueMap.entrySet()) {
            if (entry.getValue() instanceof android.filterfw.core.Frame) {
                this.mFilter.pushInputFrame(entry.getKey(), (android.filterfw.core.Frame) entry.getValue());
            } else {
                this.mFilter.pushInputValue(entry.getKey(), entry.getValue());
            }
        }
        if (this.mFilter.getStatus() != 3) {
            this.mFilter.openOutputs();
        }
        this.mFilter.performProcess(this.mFilterContext);
        if (numberOfOutputs == 1 && this.mResultHolders[0].hasFrame()) {
            frame = this.mResultHolders[0].pullFrame();
        } else {
            frame = null;
        }
        if (z) {
            gLEnvironment.deactivate();
        }
        return frame;
    }

    public android.filterfw.core.Frame executeWithArgList(java.lang.Object... objArr) {
        return execute(android.filterfw.core.KeyValueMap.fromKeyValues(objArr));
    }

    public void close() {
        this.mFilter.performClose(this.mFilterContext);
    }

    public android.filterfw.core.FilterContext getContext() {
        return this.mFilterContext;
    }

    public android.filterfw.core.Filter getFilter() {
        return this.mFilter;
    }

    public void setInputFrame(java.lang.String str, android.filterfw.core.Frame frame) {
        this.mFilter.setInputFrame(str, frame);
    }

    public void setInputValue(java.lang.String str, java.lang.Object obj) {
        this.mFilter.setInputValue(str, obj);
    }

    public void tearDown() {
        this.mFilter.performTearDown(this.mFilterContext);
        this.mFilter = null;
    }

    public java.lang.String toString() {
        return this.mFilter.getName();
    }

    private void connectFilterOutputs() {
        this.mResultHolders = new android.filterfw.core.FilterFunction.FrameHolderPort[this.mFilter.getNumberOfOutputs()];
        int i = 0;
        for (android.filterfw.core.OutputPort outputPort : this.mFilter.getOutputPorts()) {
            this.mResultHolders[i] = new android.filterfw.core.FilterFunction.FrameHolderPort();
            outputPort.connectTo(this.mResultHolders[i]);
            i++;
        }
    }
}
