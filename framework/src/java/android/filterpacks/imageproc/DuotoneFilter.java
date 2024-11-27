package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class DuotoneFilter extends android.filterfw.core.Filter {
    private final java.lang.String mDuotoneShader;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "first_color")
    private int mFirstColor;
    private android.filterfw.core.Program mProgram;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "second_color")
    private int mSecondColor;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;

    public DuotoneFilter(java.lang.String str) {
        super(str);
        this.mFirstColor = -65536;
        this.mSecondColor = -256;
        this.mTileSize = 640;
        this.mTarget = 0;
        this.mDuotoneShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 first;\nuniform vec3 second;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = (color.r + color.g + color.b) * 0.3333;\n  vec3 new_color = (1.0 - energy) * first + energy * second;\n  gl_FragColor = vec4(new_color.rgb, color.a);\n}\n";
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
                android.filterfw.core.ShaderProgram shaderProgram = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 first;\nuniform vec3 second;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = (color.r + color.g + color.b) * 0.3333;\n  vec3 new_color = (1.0 - energy) * first + energy * second;\n  gl_FragColor = vec4(new_color.rgb, color.a);\n}\n");
                shaderProgram.setMaximumTileSize(this.mTileSize);
                this.mProgram = shaderProgram;
                this.mTarget = i;
                return;
            default:
                throw new java.lang.RuntimeException("Filter Duotone does not support frames of target " + i + "!");
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
        updateParameters();
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }

    private void updateParameters() {
        this.mProgram.setHostValue("first", new float[]{android.graphics.Color.red(this.mFirstColor) / 255.0f, android.graphics.Color.green(this.mFirstColor) / 255.0f, android.graphics.Color.blue(this.mFirstColor) / 255.0f});
        this.mProgram.setHostValue("second", new float[]{android.graphics.Color.red(this.mSecondColor) / 255.0f, android.graphics.Color.green(this.mSecondColor) / 255.0f, android.graphics.Color.blue(this.mSecondColor) / 255.0f});
    }
}
