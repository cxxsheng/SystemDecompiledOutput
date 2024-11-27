package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class BitmapSource extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "bitmap")
    private android.graphics.Bitmap mBitmap;
    private android.filterfw.core.Frame mImageFrame;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "recycleBitmap")
    private boolean mRecycleBitmap;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    boolean mRepeatFrame;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(name = "target")
    java.lang.String mTargetString;

    public BitmapSource(java.lang.String str) {
        super(str);
        this.mRecycleBitmap = true;
        this.mRepeatFrame = false;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3, 0));
    }

    public void loadImage(android.filterfw.core.FilterContext filterContext) {
        this.mTarget = android.filterfw.core.FrameFormat.readTargetString(this.mTargetString);
        this.mImageFrame = filterContext.getFrameManager().newFrame(android.filterfw.format.ImageFormat.create(this.mBitmap.getWidth(), this.mBitmap.getHeight(), 3, this.mTarget));
        this.mImageFrame.setBitmap(this.mBitmap);
        this.mImageFrame.setTimestamp(-1L);
        if (this.mRecycleBitmap) {
            this.mBitmap.recycle();
        }
        this.mBitmap = null;
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if ((str.equals("bitmap") || str.equals("target")) && this.mImageFrame != null) {
            this.mImageFrame.release();
            this.mImageFrame = null;
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (this.mImageFrame == null) {
            loadImage(filterContext);
        }
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, this.mImageFrame);
        if (!this.mRepeatFrame) {
            closeOutputPort(android.app.slice.SliceItem.FORMAT_IMAGE);
        }
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mImageFrame != null) {
            this.mImageFrame.release();
            this.mImageFrame = null;
        }
    }
}
