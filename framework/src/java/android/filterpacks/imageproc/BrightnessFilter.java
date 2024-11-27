package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class BrightnessFilter extends android.filterpacks.imageproc.SimpleImageFilter {
    private static final java.lang.String mBrightnessShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float brightness;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  gl_FragColor = brightness * color;\n}\n";

    public BrightnessFilter(java.lang.String str) {
        super(str, "brightness");
    }

    @Override // android.filterpacks.imageproc.SimpleImageFilter
    protected android.filterfw.core.Program getNativeProgram(android.filterfw.core.FilterContext filterContext) {
        return new android.filterfw.core.NativeProgram("filterpack_imageproc", "brightness");
    }

    @Override // android.filterpacks.imageproc.SimpleImageFilter
    protected android.filterfw.core.Program getShaderProgram(android.filterfw.core.FilterContext filterContext) {
        return new android.filterfw.core.ShaderProgram(filterContext, mBrightnessShader);
    }
}
