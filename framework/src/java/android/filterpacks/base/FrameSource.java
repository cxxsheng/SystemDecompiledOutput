package android.filterpacks.base;

/* loaded from: classes.dex */
public class FrameSource extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFinalPort(name = android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT)
    private android.filterfw.core.FrameFormat mFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "frame")
    private android.filterfw.core.Frame mFrame;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    private boolean mRepeatFrame;

    public FrameSource(java.lang.String str) {
        super(str);
        this.mFrame = null;
        this.mRepeatFrame = false;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("frame", this.mFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (this.mFrame != null) {
            pushOutput("frame", this.mFrame);
        }
        if (!this.mRepeatFrame) {
            closeOutputPort("frame");
        }
    }
}
