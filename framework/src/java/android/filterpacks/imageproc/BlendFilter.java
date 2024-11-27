package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class BlendFilter extends android.filterpacks.imageproc.ImageCombineFilter {
    private final java.lang.String mBlendShader;

    public BlendFilter(java.lang.String str) {
        super(str, new java.lang.String[]{android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT, android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT}, "blended", "blend");
        this.mBlendShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float blend;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 colorL = texture2D(tex_sampler_0, v_texcoord);\n  vec4 colorR = texture2D(tex_sampler_1, v_texcoord);\n  float weight = colorR.a * blend;\n  gl_FragColor = mix(colorL, colorR, weight);\n}\n";
    }

    @Override // android.filterpacks.imageproc.ImageCombineFilter
    protected android.filterfw.core.Program getNativeProgram(android.filterfw.core.FilterContext filterContext) {
        throw new java.lang.RuntimeException("TODO: Write native implementation for Blend!");
    }

    @Override // android.filterpacks.imageproc.ImageCombineFilter
    protected android.filterfw.core.Program getShaderProgram(android.filterfw.core.FilterContext filterContext) {
        return new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float blend;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 colorL = texture2D(tex_sampler_0, v_texcoord);\n  vec4 colorR = texture2D(tex_sampler_1, v_texcoord);\n  float weight = colorR.a * blend;\n  gl_FragColor = mix(colorL, colorR, weight);\n}\n");
    }
}
