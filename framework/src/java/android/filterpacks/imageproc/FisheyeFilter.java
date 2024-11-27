package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class FisheyeFilter extends android.filterfw.core.Filter {
    private static final java.lang.String TAG = "FisheyeFilter";
    private static final java.lang.String mFisheyeShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec2 scale;\nuniform float alpha;\nuniform float radius2;\nuniform float factor;\nvarying vec2 v_texcoord;\nvoid main() {\n  const float m_pi_2 = 1.570963;\n  const float min_dist = 0.01;\n  vec2 coord = v_texcoord - vec2(0.5, 0.5);\n  float dist = length(coord * scale);\n  dist = max(dist, min_dist);\n  float radian = m_pi_2 - atan(alpha * sqrt(radius2 - dist * dist), dist);\n  float scalar = radian * factor / dist;\n  vec2 new_coord = coord * scalar + vec2(0.5, 0.5);\n  gl_FragColor = texture2D(tex_sampler_0, new_coord);\n}\n";
    private int mHeight;
    private android.filterfw.core.Program mProgram;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "scale")
    private float mScale;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public FisheyeFilter(java.lang.String str) {
        super(str);
        this.mScale = 0.0f;
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

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(android.filterfw.core.FilterContext filterContext, int i) {
        switch (i) {
            case 3:
                android.filterfw.core.ShaderProgram shaderProgram = new android.filterfw.core.ShaderProgram(filterContext, mFisheyeShader);
                shaderProgram.setMaximumTileSize(this.mTileSize);
                this.mProgram = shaderProgram;
                this.mTarget = i;
                return;
            default:
                throw new java.lang.RuntimeException("Filter FisheyeFilter does not support frames of target " + i + "!");
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
            updateFrameSize(format.getWidth(), format.getHeight());
        }
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mProgram != null) {
            updateProgramParams();
        }
    }

    private void updateFrameSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        updateProgramParams();
    }

    private void updateProgramParams() {
        float[] fArr = new float[2];
        if (this.mWidth > this.mHeight) {
            fArr[0] = 1.0f;
            fArr[1] = this.mHeight / this.mWidth;
        } else {
            fArr[0] = this.mWidth / this.mHeight;
            fArr[1] = 1.0f;
        }
        float f = (this.mScale * 2.0f) + 0.75f;
        float sqrt = (float) java.lang.Math.sqrt(((fArr[0] * fArr[0]) + (fArr[1] * fArr[1])) * 0.25f);
        float f2 = 1.15f * sqrt;
        float atan = sqrt / (1.5707964f - ((float) java.lang.Math.atan((f / sqrt) * ((float) java.lang.Math.sqrt(r4 - r2)))));
        this.mProgram.setHostValue("scale", fArr);
        this.mProgram.setHostValue("radius2", java.lang.Float.valueOf(f2 * f2));
        this.mProgram.setHostValue("factor", java.lang.Float.valueOf(atan));
        this.mProgram.setHostValue("alpha", java.lang.Float.valueOf(f));
    }
}
