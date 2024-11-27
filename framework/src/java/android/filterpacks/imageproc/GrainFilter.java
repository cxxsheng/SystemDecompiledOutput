package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class GrainFilter extends android.filterfw.core.Filter {
    private static final int RAND_THRESHOLD = 128;
    private android.filterfw.core.Program mGrainProgram;
    private final java.lang.String mGrainShader;
    private int mHeight;
    private android.filterfw.core.Program mNoiseProgram;
    private final java.lang.String mNoiseShader;
    private java.util.Random mRandom;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "strength")
    private float mScale;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public GrainFilter(java.lang.String str) {
        super(str);
        this.mScale = 0.0f;
        this.mTileSize = 640;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
        this.mNoiseShader = "precision mediump float;\nuniform vec2 seed;\nvarying vec2 v_texcoord;\nfloat rand(vec2 loc) {\n  float theta1 = dot(loc, vec2(0.9898, 0.233));\n  float theta2 = dot(loc, vec2(12.0, 78.0));\n  float value = cos(theta1) * sin(theta2) + sin(theta1) * cos(theta2);\n  float temp = mod(197.0 * value, 1.0) + value;\n  float part1 = mod(220.0 * temp, 1.0) + temp;\n  float part2 = value * 0.5453;\n  float part3 = cos(theta1 + theta2) * 0.43758;\n  return fract(part1 + part2 + part3);\n}\nvoid main() {\n  gl_FragColor = vec4(rand(v_texcoord + seed), 0.0, 0.0, 1.0);\n}\n";
        this.mGrainShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float scale;\nuniform float stepX;\nuniform float stepY;\nvarying vec2 v_texcoord;\nvoid main() {\n  float noise = texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, stepY)).r * 0.224;\n  noise += 0.4448;\n  noise *= scale;\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = 0.33333 * color.r + 0.33333 * color.g + 0.33333 * color.b;\n  float mask = (1.0 - sqrt(energy));\n  float weight = 1.0 - 1.333 * mask * noise;\n  gl_FragColor = vec4(color.rgb * weight, color.a);\n}\n";
        this.mRandom = new java.util.Random(new java.util.Date().getTime());
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
                android.filterfw.core.ShaderProgram shaderProgram = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform vec2 seed;\nvarying vec2 v_texcoord;\nfloat rand(vec2 loc) {\n  float theta1 = dot(loc, vec2(0.9898, 0.233));\n  float theta2 = dot(loc, vec2(12.0, 78.0));\n  float value = cos(theta1) * sin(theta2) + sin(theta1) * cos(theta2);\n  float temp = mod(197.0 * value, 1.0) + value;\n  float part1 = mod(220.0 * temp, 1.0) + temp;\n  float part2 = value * 0.5453;\n  float part3 = cos(theta1 + theta2) * 0.43758;\n  return fract(part1 + part2 + part3);\n}\nvoid main() {\n  gl_FragColor = vec4(rand(v_texcoord + seed), 0.0, 0.0, 1.0);\n}\n");
                shaderProgram.setMaximumTileSize(this.mTileSize);
                this.mNoiseProgram = shaderProgram;
                android.filterfw.core.ShaderProgram shaderProgram2 = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float scale;\nuniform float stepX;\nuniform float stepY;\nvarying vec2 v_texcoord;\nvoid main() {\n  float noise = texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, stepY)).r * 0.224;\n  noise += 0.4448;\n  noise *= scale;\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = 0.33333 * color.r + 0.33333 * color.g + 0.33333 * color.b;\n  float mask = (1.0 - sqrt(energy));\n  float weight = 1.0 - 1.333 * mask * noise;\n  gl_FragColor = vec4(color.rgb * weight, color.a);\n}\n");
                shaderProgram2.setMaximumTileSize(this.mTileSize);
                this.mGrainProgram = shaderProgram2;
                this.mTarget = i;
                return;
            default:
                throw new java.lang.RuntimeException("Filter Sharpen does not support frames of target " + i + "!");
        }
    }

    private void updateParameters() {
        this.mNoiseProgram.setHostValue("seed", new float[]{this.mRandom.nextFloat(), this.mRandom.nextFloat()});
        this.mGrainProgram.setHostValue("scale", java.lang.Float.valueOf(this.mScale));
    }

    private void updateFrameSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        if (this.mGrainProgram != null) {
            this.mGrainProgram.setHostValue("stepX", java.lang.Float.valueOf(0.5f / this.mWidth));
            this.mGrainProgram.setHostValue("stepY", java.lang.Float.valueOf(0.5f / this.mHeight));
            updateParameters();
        }
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mGrainProgram != null && this.mNoiseProgram != null) {
            updateParameters();
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        android.filterfw.format.ImageFormat.create(format.getWidth() / 2, format.getHeight() / 2, 3, 3);
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(format);
        android.filterfw.core.Frame newFrame2 = filterContext.getFrameManager().newFrame(format);
        if (this.mNoiseProgram == null || this.mGrainProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
            updateParameters();
        }
        if (format.getWidth() != this.mWidth || format.getHeight() != this.mHeight) {
            updateFrameSize(format.getWidth(), format.getHeight());
        }
        this.mNoiseProgram.process(new android.filterfw.core.Frame[0], newFrame);
        this.mGrainProgram.process(new android.filterfw.core.Frame[]{pullInput, newFrame}, newFrame2);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame2);
        newFrame2.release();
        newFrame.release();
    }
}
