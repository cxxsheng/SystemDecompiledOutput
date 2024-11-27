package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class AlphaBlendFilter extends android.filterpacks.imageproc.ImageCombineFilter {
    private final java.lang.String mAlphaBlendShader;

    public AlphaBlendFilter(java.lang.String str) {
        super(str, new java.lang.String[]{android.app.slice.Slice.SUBTYPE_SOURCE, "overlay", "mask"}, "blended", "weight");
        this.mAlphaBlendShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform float weight;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 colorL = texture2D(tex_sampler_0, v_texcoord);\n  vec4 colorR = texture2D(tex_sampler_1, v_texcoord);\n  float blend = texture2D(tex_sampler_2, v_texcoord).r * weight;\n  gl_FragColor = colorL * (1.0 - blend) + colorR * blend;\n}\n";
    }

    @Override // android.filterpacks.imageproc.ImageCombineFilter
    protected android.filterfw.core.Program getNativeProgram(android.filterfw.core.FilterContext filterContext) {
        throw new java.lang.RuntimeException("TODO: Write native implementation for AlphaBlend!");
    }

    @Override // android.filterpacks.imageproc.ImageCombineFilter
    protected android.filterfw.core.Program getShaderProgram(android.filterfw.core.FilterContext filterContext) {
        return new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform float weight;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 colorL = texture2D(tex_sampler_0, v_texcoord);\n  vec4 colorR = texture2D(tex_sampler_1, v_texcoord);\n  float blend = texture2D(tex_sampler_2, v_texcoord).r * weight;\n  gl_FragColor = colorL * (1.0 - blend) + colorR * blend;\n}\n");
    }
}
