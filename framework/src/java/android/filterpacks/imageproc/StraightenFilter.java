package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class StraightenFilter extends android.filterfw.core.Filter {
    private static final float DEGREE_TO_RADIAN = 0.017453292f;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "angle")
    private float mAngle;
    private int mHeight;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maxAngle")
    private float mMaxAngle;
    private android.filterfw.core.Program mProgram;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public StraightenFilter(java.lang.String str) {
        super(str);
        this.mAngle = 0.0f;
        this.mMaxAngle = 45.0f;
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
            updateParameters();
        }
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(format);
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }

    private void updateParameters() {
        float cos = (float) java.lang.Math.cos(this.mAngle * DEGREE_TO_RADIAN);
        float sin = (float) java.lang.Math.sin(this.mAngle * DEGREE_TO_RADIAN);
        if (this.mMaxAngle <= 0.0f) {
            throw new java.lang.RuntimeException("Max angle is out of range (0-180).");
        }
        this.mMaxAngle = this.mMaxAngle <= 90.0f ? this.mMaxAngle : 90.0f;
        float f = -cos;
        float f2 = -sin;
        android.filterfw.geometry.Point point = new android.filterfw.geometry.Point((this.mWidth * f) + (this.mHeight * sin), (this.mWidth * f2) - (this.mHeight * cos));
        android.filterfw.geometry.Point point2 = new android.filterfw.geometry.Point((this.mWidth * cos) + (this.mHeight * sin), (this.mWidth * sin) - (this.mHeight * cos));
        android.filterfw.geometry.Point point3 = new android.filterfw.geometry.Point((f * this.mWidth) - (this.mHeight * sin), (f2 * this.mWidth) + (this.mHeight * cos));
        android.filterfw.geometry.Point point4 = new android.filterfw.geometry.Point((this.mWidth * cos) - (this.mHeight * sin), (sin * this.mWidth) + (cos * this.mHeight));
        float min = java.lang.Math.min(this.mWidth / java.lang.Math.max(java.lang.Math.abs(point.x), java.lang.Math.abs(point2.x)), this.mHeight / java.lang.Math.max(java.lang.Math.abs(point.y), java.lang.Math.abs(point2.y))) * 0.5f;
        point.set(((point.x * min) / this.mWidth) + 0.5f, ((point.y * min) / this.mHeight) + 0.5f);
        point2.set(((point2.x * min) / this.mWidth) + 0.5f, ((point2.y * min) / this.mHeight) + 0.5f);
        point3.set(((point3.x * min) / this.mWidth) + 0.5f, ((point3.y * min) / this.mHeight) + 0.5f);
        point4.set(((point4.x * min) / this.mWidth) + 0.5f, ((min * point4.y) / this.mHeight) + 0.5f);
        ((android.filterfw.core.ShaderProgram) this.mProgram).setSourceRegion(new android.filterfw.geometry.Quad(point, point2, point3, point4));
    }
}
