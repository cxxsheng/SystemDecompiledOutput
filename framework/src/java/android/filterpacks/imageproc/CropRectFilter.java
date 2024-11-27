package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class CropRectFilter extends android.filterfw.core.Filter {
    private int mHeight;

    @android.filterfw.core.GenerateFieldPort(name = "height")
    private int mOutputHeight;

    @android.filterfw.core.GenerateFieldPort(name = "width")
    private int mOutputWidth;
    private android.filterfw.core.Program mProgram;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    @android.filterfw.core.GenerateFieldPort(name = "xorigin")
    private int mXorigin;

    @android.filterfw.core.GenerateFieldPort(name = "yorigin")
    private int mYorigin;

    public CropRectFilter(java.lang.String str) {
        super(str);
        this.mTileSize = 640;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    public void initProgram(android.filterfw.core.FilterContext filterContext, int i) {
        switch (i) {
            case 3:
                android.filterfw.core.ShaderProgram createIdentity = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
                createIdentity.setMaximumTileSize(this.mTileSize);
                this.mProgram = createIdentity;
                this.mTarget = i;
                return;
            default:
                throw new java.lang.RuntimeException("Filter Sharpen does not support frames of target " + i + "!");
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mProgram != null) {
            updateSourceRect(this.mWidth, this.mHeight);
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(android.filterfw.format.ImageFormat.create(this.mOutputWidth, this.mOutputHeight, 3, 3));
        if (this.mProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
        }
        if (format.getWidth() != this.mWidth || format.getHeight() != this.mHeight) {
            updateSourceRect(format.getWidth(), format.getHeight());
        }
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }

    void updateSourceRect(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        ((android.filterfw.core.ShaderProgram) this.mProgram).setSourceRect(this.mXorigin / this.mWidth, this.mYorigin / this.mHeight, this.mOutputWidth / this.mWidth, this.mOutputHeight / this.mHeight);
    }
}
