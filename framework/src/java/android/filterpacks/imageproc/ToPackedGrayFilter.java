package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class ToPackedGrayFilter extends android.filterfw.core.Filter {
    private final java.lang.String mColorToPackedGrayShader;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "keepAspectRatio")
    private boolean mKeepAspectRatio;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "oheight")
    private int mOHeight;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "owidth")
    private int mOWidth;
    private android.filterfw.core.Program mProgram;

    public ToPackedGrayFilter(java.lang.String str) {
        super(str);
        this.mOWidth = 0;
        this.mOHeight = 0;
        this.mKeepAspectRatio = false;
        this.mColorToPackedGrayShader = "precision mediump float;\nconst vec4 coeff_y = vec4(0.299, 0.587, 0.114, 0);\nuniform sampler2D tex_sampler_0;\nuniform float pix_stride;\nvarying vec2 v_texcoord;\nvoid main() {\n  for (int i = 0; i < 4; ++i) {\n    vec4 p = texture2D(tex_sampler_0,\n                       v_texcoord + vec2(pix_stride * float(i), 0.0));\n    gl_FragColor[i] = dot(p, coeff_y);\n  }\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3, 3));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return convertInputFormat(frameFormat);
    }

    private void checkOutputDimensions(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new java.lang.RuntimeException("Invalid output dimensions: " + i + " " + i2);
        }
    }

    private android.filterfw.core.FrameFormat convertInputFormat(android.filterfw.core.FrameFormat frameFormat) {
        int i = this.mOWidth;
        int i2 = this.mOHeight;
        int width = frameFormat.getWidth();
        int height = frameFormat.getHeight();
        if (this.mOWidth == 0) {
            i = width;
        }
        if (this.mOHeight == 0) {
            i2 = height;
        }
        if (this.mKeepAspectRatio) {
            if (width > height) {
                i = java.lang.Math.max(i, i2);
                i2 = (height * i) / width;
            } else {
                i2 = java.lang.Math.max(i, i2);
                i = (width * i2) / height;
            }
        }
        return android.filterfw.format.ImageFormat.create((i <= 0 || i >= 4) ? 4 * (i / 4) : 4, i2, 1, 2);
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        this.mProgram = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nconst vec4 coeff_y = vec4(0.299, 0.587, 0.114, 0);\nuniform sampler2D tex_sampler_0;\nuniform float pix_stride;\nvarying vec2 v_texcoord;\nvoid main() {\n  for (int i = 0; i < 4; ++i) {\n    vec4 p = texture2D(tex_sampler_0,\n                       v_texcoord + vec2(pix_stride * float(i), 0.0));\n    gl_FragColor[i] = dot(p, coeff_y);\n  }\n}\n");
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        android.filterfw.core.FrameFormat convertInputFormat = convertInputFormat(format);
        int width = convertInputFormat.getWidth();
        int height = convertInputFormat.getHeight();
        checkOutputDimensions(width, height);
        this.mProgram.setHostValue("pix_stride", java.lang.Float.valueOf(1.0f / width));
        android.filterfw.core.MutableFrameFormat mutableCopy = format.mutableCopy();
        mutableCopy.setDimensions(width / 4, height);
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(mutableCopy);
        this.mProgram.process(pullInput, newFrame);
        android.filterfw.core.Frame newFrame2 = filterContext.getFrameManager().newFrame(convertInputFormat);
        newFrame2.setDataFromFrame(newFrame);
        newFrame.release();
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame2);
        newFrame2.release();
    }
}
