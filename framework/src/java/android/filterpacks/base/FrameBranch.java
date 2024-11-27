package android.filterpacks.base;

/* loaded from: classes.dex */
public class FrameBranch extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = "outputs")
    private int mNumberOfOutputs;

    public FrameBranch(java.lang.String str) {
        super(str);
        this.mNumberOfOutputs = 2;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("in");
        for (int i = 0; i < this.mNumberOfOutputs; i++) {
            addOutputBasedOnInput("out" + i, "in");
        }
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput("in");
        for (int i = 0; i < this.mNumberOfOutputs; i++) {
            pushOutput("out" + i, pullInput);
        }
    }
}
