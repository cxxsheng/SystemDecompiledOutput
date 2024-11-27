package android.filterpacks.base;

/* loaded from: classes.dex */
public class ObjectSource extends android.filterfw.core.Filter {
    private android.filterfw.core.Frame mFrame;

    @android.filterfw.core.GenerateFieldPort(name = "object")
    private java.lang.Object mObject;

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT)
    private android.filterfw.core.FrameFormat mOutputFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    boolean mRepeatFrame;

    public ObjectSource(java.lang.String str) {
        super(str);
        this.mOutputFormat = android.filterfw.core.FrameFormat.unspecified();
        this.mRepeatFrame = false;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("frame", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (this.mFrame == null) {
            if (this.mObject == null) {
                throw new java.lang.NullPointerException("ObjectSource producing frame with no object set!");
            }
            this.mFrame = filterContext.getFrameManager().newFrame(android.filterfw.format.ObjectFormat.fromObject(this.mObject, 1));
            this.mFrame.setObjectValue(this.mObject);
            this.mFrame.setTimestamp(-1L);
        }
        pushOutput("frame", this.mFrame);
        if (!this.mRepeatFrame) {
            closeOutputPort("frame");
        }
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        this.mFrame.release();
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (str.equals("object") && this.mFrame != null) {
            this.mFrame.release();
            this.mFrame = null;
        }
    }
}
