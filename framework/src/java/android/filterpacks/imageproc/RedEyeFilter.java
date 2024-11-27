package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class RedEyeFilter extends android.filterfw.core.Filter {
    private static final float DEFAULT_RED_INTENSITY = 1.3f;
    private static final float MIN_RADIUS = 10.0f;
    private static final float RADIUS_RATIO = 0.06f;
    private final android.graphics.Canvas mCanvas;

    @android.filterfw.core.GenerateFieldPort(name = "centers")
    private float[] mCenters;
    private int mHeight;
    private final android.graphics.Paint mPaint;
    private android.filterfw.core.Program mProgram;
    private float mRadius;
    private android.graphics.Bitmap mRedEyeBitmap;
    private android.filterfw.core.Frame mRedEyeFrame;
    private final java.lang.String mRedEyeShader;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public RedEyeFilter(java.lang.String str) {
        super(str);
        this.mTileSize = 640;
        this.mCanvas = new android.graphics.Canvas();
        this.mPaint = new android.graphics.Paint();
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
        this.mRedEyeShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float intensity;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  if (mask.a > 0.0) {\n    float green_blue = color.g + color.b;\n    float red_intensity = color.r / green_blue;\n    if (red_intensity > intensity) {\n      color.r = 0.5 * green_blue;\n    }\n  }\n  gl_FragColor = color;\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(android.filterfw.core.FilterContext filterContext, int i) {
        switch (i) {
            case 3:
                android.filterfw.core.ShaderProgram shaderProgram = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float intensity;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  if (mask.a > 0.0) {\n    float green_blue = color.g + color.b;\n    float red_intensity = color.r / green_blue;\n    if (red_intensity > intensity) {\n      color.r = 0.5 * green_blue;\n    }\n  }\n  gl_FragColor = color;\n}\n");
                shaderProgram.setMaximumTileSize(this.mTileSize);
                this.mProgram = shaderProgram;
                this.mProgram.setHostValue("intensity", java.lang.Float.valueOf(1.3f));
                this.mTarget = i;
                return;
            default:
                throw new java.lang.RuntimeException("Filter RedEye does not support frames of target " + i + "!");
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(format);
        if (this.mProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
        }
        if (format.getWidth() != this.mWidth || format.getHeight() != this.mHeight) {
            this.mWidth = format.getWidth();
            this.mHeight = format.getHeight();
        }
        createRedEyeFrame(filterContext);
        this.mProgram.process(new android.filterfw.core.Frame[]{pullInput, this.mRedEyeFrame}, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
        this.mRedEyeFrame.release();
        this.mRedEyeFrame = null;
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mProgram != null) {
            updateProgramParams();
        }
    }

    private void createRedEyeFrame(android.filterfw.core.FilterContext filterContext) {
        int i = this.mWidth / 2;
        int i2 = this.mHeight / 2;
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(createBitmap);
        this.mPaint.setColor(-1);
        this.mRadius = java.lang.Math.max(MIN_RADIUS, java.lang.Math.min(i, i2) * RADIUS_RATIO);
        for (int i3 = 0; i3 < this.mCenters.length; i3 += 2) {
            this.mCanvas.drawCircle(this.mCenters[i3] * i, this.mCenters[i3 + 1] * i2, this.mRadius, this.mPaint);
        }
        this.mRedEyeFrame = filterContext.getFrameManager().newFrame(android.filterfw.format.ImageFormat.create(i, i2, 3, 3));
        this.mRedEyeFrame.setBitmap(createBitmap);
        createBitmap.recycle();
    }

    private void updateProgramParams() {
        if (this.mCenters.length % 2 == 1) {
            throw new java.lang.RuntimeException("The size of center array must be even.");
        }
    }
}
