package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class RotateFilter extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "angle")
    private int mAngle;
    private int mHeight;
    private int mOutputHeight;
    private int mOutputWidth;
    private android.filterfw.core.Program mProgram;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public RotateFilter(java.lang.String str) {
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
                createIdentity.setClearsOutput(true);
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
            updateParameters();
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        if (this.mProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
        }
        if (format.getWidth() != this.mWidth || format.getHeight() != this.mHeight) {
            this.mWidth = format.getWidth();
            this.mHeight = format.getHeight();
            this.mOutputWidth = this.mWidth;
            this.mOutputHeight = this.mHeight;
            updateParameters();
        }
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(android.filterfw.format.ImageFormat.create(this.mOutputWidth, this.mOutputHeight, 3, 3));
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }

    private void updateParameters() {
        float f;
        if (this.mAngle % 90 == 0) {
            float f2 = 0.0f;
            if (this.mAngle % 180 == 0) {
                f2 = this.mAngle % 360 == 0 ? 1.0f : -1.0f;
                f = 0.0f;
            } else {
                f = (this.mAngle + 90) % 360 != 0 ? 1.0f : -1.0f;
                this.mOutputWidth = this.mHeight;
                this.mOutputHeight = this.mWidth;
            }
            float f3 = -f2;
            float f4 = -f;
            float f5 = (f2 + f + 1.0f) * 0.5f;
            ((android.filterfw.core.ShaderProgram) this.mProgram).setTargetRegion(new android.filterfw.geometry.Quad(new android.filterfw.geometry.Point((f3 + f + 1.0f) * 0.5f, ((f4 - f2) + 1.0f) * 0.5f), new android.filterfw.geometry.Point(f5, ((f - f2) + 1.0f) * 0.5f), new android.filterfw.geometry.Point(((f3 - f) + 1.0f) * 0.5f, (f4 + f2 + 1.0f) * 0.5f), new android.filterfw.geometry.Point(((f2 - f) + 1.0f) * 0.5f, f5)));
            return;
        }
        throw new java.lang.RuntimeException("degree has to be multiply of 90.");
    }
}
