package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class ToGrayFilter extends android.filterpacks.imageproc.SimpleImageFilter {
    private static final java.lang.String mColorToGray4Shader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float y = dot(color, vec4(0.299, 0.587, 0.114, 0));\n  gl_FragColor = vec4(y, y, y, color.a);\n}\n";

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "invertSource")
    private boolean mInvertSource;
    private android.filterfw.core.MutableFrameFormat mOutputFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;

    public ToGrayFilter(java.lang.String str) {
        super(str, null);
        this.mInvertSource = false;
        this.mTileSize = 640;
    }

    @Override // android.filterpacks.imageproc.SimpleImageFilter, android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3, 3));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterpacks.imageproc.SimpleImageFilter
    protected android.filterfw.core.Program getNativeProgram(android.filterfw.core.FilterContext filterContext) {
        throw new java.lang.RuntimeException("Native toGray not implemented yet!");
    }

    @Override // android.filterpacks.imageproc.SimpleImageFilter
    protected android.filterfw.core.Program getShaderProgram(android.filterfw.core.FilterContext filterContext) {
        int bytesPerSample = getInputFormat(android.app.slice.SliceItem.FORMAT_IMAGE).getBytesPerSample();
        if (bytesPerSample != 4) {
            throw new java.lang.RuntimeException("Unsupported GL input channels: " + bytesPerSample + "! Channels must be 4!");
        }
        android.filterfw.core.ShaderProgram shaderProgram = new android.filterfw.core.ShaderProgram(filterContext, mColorToGray4Shader);
        shaderProgram.setMaximumTileSize(this.mTileSize);
        if (this.mInvertSource) {
            shaderProgram.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        }
        return shaderProgram;
    }
}
