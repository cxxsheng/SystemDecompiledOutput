package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class BitmapOverlayFilter extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "bitmap")
    private android.graphics.Bitmap mBitmap;
    private android.filterfw.core.Frame mFrame;
    private final java.lang.String mOverlayShader;
    private android.filterfw.core.Program mProgram;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;

    public BitmapOverlayFilter(java.lang.String str) {
        super(str);
        this.mTileSize = 640;
        this.mTarget = 0;
        this.mOverlayShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 original = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  gl_FragColor = vec4(original.rgb * (1.0 - mask.a) + mask.rgb, 1.0);\n}\n";
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
                android.filterfw.core.ShaderProgram shaderProgram = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 original = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  gl_FragColor = vec4(original.rgb * (1.0 - mask.a) + mask.rgb, 1.0);\n}\n");
                shaderProgram.setMaximumTileSize(this.mTileSize);
                this.mProgram = shaderProgram;
                this.mTarget = i;
                return;
            default:
                throw new java.lang.RuntimeException("Filter FisheyeFilter does not support frames of target " + i + "!");
        }
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (this.mFrame != null) {
            this.mFrame.release();
            this.mFrame = null;
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
        if (this.mBitmap != null) {
            android.filterfw.core.Frame createBitmapFrame = createBitmapFrame(filterContext);
            this.mProgram.process(new android.filterfw.core.Frame[]{pullInput, createBitmapFrame}, newFrame);
            createBitmapFrame.release();
        } else {
            newFrame.setDataFromFrame(pullInput);
        }
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }

    private android.filterfw.core.Frame createBitmapFrame(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(android.filterfw.format.ImageFormat.create(this.mBitmap.getWidth(), this.mBitmap.getHeight(), 3, 3));
        newFrame.setBitmap(this.mBitmap);
        this.mBitmap.recycle();
        this.mBitmap = null;
        return newFrame;
    }
}
