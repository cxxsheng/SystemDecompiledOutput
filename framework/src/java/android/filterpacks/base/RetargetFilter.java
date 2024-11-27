package android.filterpacks.base;

/* loaded from: classes.dex */
public class RetargetFilter extends android.filterfw.core.Filter {
    private android.filterfw.core.MutableFrameFormat mOutputFormat;
    private int mTarget;

    @android.filterfw.core.GenerateFinalPort(hasDefault = false, name = "target")
    private java.lang.String mTargetString;

    public RetargetFilter(java.lang.String str) {
        super(str);
        this.mTarget = -1;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        this.mTarget = android.filterfw.core.FrameFormat.readTargetString(this.mTargetString);
        addInputPort("frame");
        addOutputBasedOnInput("frame", "frame");
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.MutableFrameFormat mutableCopy = frameFormat.mutableCopy();
        mutableCopy.setTarget(this.mTarget);
        return mutableCopy;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame duplicateFrameToTarget = filterContext.getFrameManager().duplicateFrameToTarget(pullInput("frame"), this.mTarget);
        pushOutput("frame", duplicateFrameToTarget);
        duplicateFrameToTarget.release();
    }
}
