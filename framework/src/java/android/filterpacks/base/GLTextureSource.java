package android.filterpacks.base;

/* loaded from: classes.dex */
public class GLTextureSource extends android.filterfw.core.Filter {
    private android.filterfw.core.Frame mFrame;

    @android.filterfw.core.GenerateFieldPort(name = "height")
    private int mHeight;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    private boolean mRepeatFrame;

    @android.filterfw.core.GenerateFieldPort(name = "texId")
    private int mTexId;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "timestamp")
    private long mTimestamp;

    @android.filterfw.core.GenerateFieldPort(name = "width")
    private int mWidth;

    public GLTextureSource(java.lang.String str) {
        super(str);
        this.mRepeatFrame = false;
        this.mTimestamp = -1L;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("frame", android.filterfw.format.ImageFormat.create(3, 3));
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mFrame != null) {
            this.mFrame.release();
            this.mFrame = null;
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (this.mFrame == null) {
            this.mFrame = filterContext.getFrameManager().newBoundFrame(android.filterfw.format.ImageFormat.create(this.mWidth, this.mHeight, 3, 3), 100, this.mTexId);
            this.mFrame.setTimestamp(this.mTimestamp);
        }
        pushOutput("frame", this.mFrame);
        if (!this.mRepeatFrame) {
            closeOutputPort("frame");
        }
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mFrame != null) {
            this.mFrame.release();
        }
    }
}
