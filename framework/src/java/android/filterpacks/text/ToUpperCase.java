package android.filterpacks.text;

/* loaded from: classes.dex */
public class ToUpperCase extends android.filterfw.core.Filter {
    private android.filterfw.core.FrameFormat mOutputFormat;

    public ToUpperCase(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        this.mOutputFormat = android.filterfw.format.ObjectFormat.fromClass(java.lang.String.class, 1);
        addMaskedInputPort("mixedcase", this.mOutputFormat);
        addOutputPort("uppercase", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        java.lang.String str = (java.lang.String) pullInput("mixedcase").getObjectValue();
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        newFrame.setObjectValue(str.toUpperCase(java.util.Locale.getDefault()));
        pushOutput("uppercase", newFrame);
    }
}
