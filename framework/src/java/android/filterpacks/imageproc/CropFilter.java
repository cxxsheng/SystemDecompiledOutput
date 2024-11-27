package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class CropFilter extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "fillblack")
    private boolean mFillBlack;
    private final java.lang.String mFragShader;
    private android.filterfw.core.FrameFormat mLastFormat;

    @android.filterfw.core.GenerateFieldPort(name = "oheight")
    private int mOutputHeight;

    @android.filterfw.core.GenerateFieldPort(name = "owidth")
    private int mOutputWidth;
    private android.filterfw.core.Program mProgram;

    public CropFilter(java.lang.String str) {
        super(str);
        this.mLastFormat = null;
        this.mOutputWidth = -1;
        this.mOutputHeight = -1;
        this.mFillBlack = false;
        this.mFragShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  const vec2 lo = vec2(0.0, 0.0);\n  const vec2 hi = vec2(1.0, 1.0);\n  const vec4 black = vec4(0.0, 0.0, 0.0, 1.0);\n  bool out_of_bounds =\n    any(lessThan(v_texcoord, lo)) ||\n    any(greaterThan(v_texcoord, hi));\n  if (out_of_bounds) {\n    gl_FragColor = black;\n  } else {\n    gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n  }\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3));
        addMaskedInputPort("box", android.filterfw.format.ObjectFormat.fromClass(android.filterfw.geometry.Quad.class, 1));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.MutableFrameFormat mutableCopy = frameFormat.mutableCopy();
        mutableCopy.setDimensions(0, 0);
        return mutableCopy;
    }

    protected void createProgram(android.filterfw.core.FilterContext filterContext, android.filterfw.core.FrameFormat frameFormat) {
        if (this.mLastFormat == null || this.mLastFormat.getTarget() != frameFormat.getTarget()) {
            this.mLastFormat = frameFormat;
            this.mProgram = null;
            switch (frameFormat.getTarget()) {
                case 3:
                    if (this.mFillBlack) {
                        this.mProgram = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  const vec2 lo = vec2(0.0, 0.0);\n  const vec2 hi = vec2(1.0, 1.0);\n  const vec4 black = vec4(0.0, 0.0, 0.0, 1.0);\n  bool out_of_bounds =\n    any(lessThan(v_texcoord, lo)) ||\n    any(greaterThan(v_texcoord, hi));\n  if (out_of_bounds) {\n    gl_FragColor = black;\n  } else {\n    gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n  }\n}\n");
                        break;
                    } else {
                        this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
                        break;
                    }
            }
            if (this.mProgram == null) {
                throw new java.lang.RuntimeException("Could not create a program for crop filter " + this + "!");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.Frame pullInput2 = pullInput("box");
        createProgram(filterContext, pullInput.getFormat());
        android.filterfw.geometry.Quad quad = (android.filterfw.geometry.Quad) pullInput2.getObjectValue();
        android.filterfw.core.MutableFrameFormat mutableCopy = pullInput.getFormat().mutableCopy();
        mutableCopy.setDimensions(this.mOutputWidth == -1 ? mutableCopy.getWidth() : this.mOutputWidth, this.mOutputHeight == -1 ? mutableCopy.getHeight() : this.mOutputHeight);
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(mutableCopy);
        if (this.mProgram instanceof android.filterfw.core.ShaderProgram) {
            ((android.filterfw.core.ShaderProgram) this.mProgram).setSourceRegion(quad);
        }
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }
}
