package android.filterpacks.numeric;

/* loaded from: classes.dex */
public class SinWaveFilter extends android.filterfw.core.Filter {
    private android.filterfw.core.FrameFormat mOutputFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "stepSize")
    private float mStepSize;
    private float mValue;

    public SinWaveFilter(java.lang.String str) {
        super(str);
        this.mStepSize = 0.05f;
        this.mValue = 0.0f;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        this.mOutputFormat = android.filterfw.format.ObjectFormat.fromClass(java.lang.Float.class, 1);
        addOutputPort("value", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public void open(android.filterfw.core.FilterContext filterContext) {
        this.mValue = 0.0f;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        newFrame.setObjectValue(java.lang.Float.valueOf((((float) java.lang.Math.sin(this.mValue)) + 1.0f) / 2.0f));
        pushOutput("value", newFrame);
        this.mValue += this.mStepSize;
        newFrame.release();
    }
}
