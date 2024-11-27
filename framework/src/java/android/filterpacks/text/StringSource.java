package android.filterpacks.text;

/* loaded from: classes.dex */
public class StringSource extends android.filterfw.core.Filter {
    private android.filterfw.core.FrameFormat mOutputFormat;

    @android.filterfw.core.GenerateFieldPort(name = "stringValue")
    private java.lang.String mString;

    public StringSource(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        this.mOutputFormat = android.filterfw.format.ObjectFormat.fromClass(java.lang.String.class, 1);
        addOutputPort("string", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        newFrame.setObjectValue(this.mString);
        newFrame.setTimestamp(-1L);
        pushOutput("string", newFrame);
        closeOutputPort("string");
    }
}
