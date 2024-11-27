package android.filterpacks.base;

/* loaded from: classes.dex */
public class FrameFetch extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT)
    private android.filterfw.core.FrameFormat mFormat;

    @android.filterfw.core.GenerateFieldPort(name = "key")
    private java.lang.String mKey;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    private boolean mRepeatFrame;

    public FrameFetch(java.lang.String str) {
        super(str);
        this.mRepeatFrame = false;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("frame", this.mFormat == null ? android.filterfw.core.FrameFormat.unspecified() : this.mFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame fetchFrame = filterContext.fetchFrame(this.mKey);
        if (fetchFrame != null) {
            pushOutput("frame", fetchFrame);
            if (!this.mRepeatFrame) {
                closeOutputPort("frame");
                return;
            }
            return;
        }
        delayNextProcess(250);
    }
}
