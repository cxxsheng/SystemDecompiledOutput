package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class LomoishFilter extends android.filterfw.core.Filter {
    private int mHeight;
    private final java.lang.String mLomoishShader;
    private android.filterfw.core.Program mProgram;
    private java.util.Random mRandom;
    private int mTarget;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public LomoishFilter(java.lang.String str) {
        super(str);
        this.mTileSize = 640;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
        this.mLomoishShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec2 seed;\nuniform float stepsizeX;\nuniform float stepsizeY;\nuniform float stepsize;\nuniform vec2 scale;\nuniform float inv_max_dist;\nvarying vec2 v_texcoord;\nfloat rand(vec2 loc) {\n  float theta1 = dot(loc, vec2(0.9898, 0.233));\n  float theta2 = dot(loc, vec2(12.0, 78.0));\n  float value = cos(theta1) * sin(theta2) + sin(theta1) * cos(theta2);\n  float temp = mod(197.0 * value, 1.0) + value;\n  float part1 = mod(220.0 * temp, 1.0) + temp;\n  float part2 = value * 0.5453;\n  float part3 = cos(theta1 + theta2) * 0.43758;\n  return fract(part1 + part2 + part3);\n}\nvoid main() {\n  vec3 nbr_color = vec3(0.0, 0.0, 0.0);\n  vec2 coord;\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  coord.x = v_texcoord.x - 0.5 * stepsizeX;\n  coord.y = v_texcoord.y - stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  coord.x = v_texcoord.x - stepsizeX;\n  coord.y = v_texcoord.y + 0.5 * stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  coord.x = v_texcoord.x + stepsizeX;\n  coord.y = v_texcoord.y - 0.5 * stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  coord.x = v_texcoord.x + stepsizeX;\n  coord.y = v_texcoord.y + 0.5 * stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  vec3 s_color = vec3(color.rgb + 0.3 * nbr_color);\n  vec3 c_color = vec3(0.0, 0.0, 0.0);\n  float value;\n  if (s_color.r < 0.5) {\n    value = s_color.r;\n  } else {\n    value = 1.0 - s_color.r;\n  }\n  float red = 4.0 * value * value * value;\n  if (s_color.r < 0.5) {\n    c_color.r = red;\n  } else {\n    c_color.r = 1.0 - red;\n  }\n  if (s_color.g < 0.5) {\n    value = s_color.g;\n  } else {\n    value = 1.0 - s_color.g;\n  }\n  float green = 2.0 * value * value;\n  if (s_color.g < 0.5) {\n    c_color.g = green;\n  } else {\n    c_color.g = 1.0 - green;\n  }\n  c_color.b = s_color.b * 0.5 + 0.25;\n  float dither = rand(v_texcoord + seed);\n  vec3 xform = clamp((c_color.rgb - 0.15) * 1.53846, 0.0, 1.0);\n  vec3 temp = clamp((color.rgb + stepsize - 0.15) * 1.53846, 0.0, 1.0);\n  vec3 bw_color = clamp(xform + (temp - xform) * (dither - 0.5), 0.0, 1.0);\n  coord = v_texcoord - vec2(0.5, 0.5);\n  float dist = length(coord * scale);\n  float lumen = 0.85 / (1.0 + exp((dist * inv_max_dist - 0.73) * 20.0)) + 0.15;\n  gl_FragColor = vec4(bw_color * lumen, color.a);\n}\n";
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
                android.filterfw.core.ShaderProgram shaderProgram = new android.filterfw.core.ShaderProgram(filterContext, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec2 seed;\nuniform float stepsizeX;\nuniform float stepsizeY;\nuniform float stepsize;\nuniform vec2 scale;\nuniform float inv_max_dist;\nvarying vec2 v_texcoord;\nfloat rand(vec2 loc) {\n  float theta1 = dot(loc, vec2(0.9898, 0.233));\n  float theta2 = dot(loc, vec2(12.0, 78.0));\n  float value = cos(theta1) * sin(theta2) + sin(theta1) * cos(theta2);\n  float temp = mod(197.0 * value, 1.0) + value;\n  float part1 = mod(220.0 * temp, 1.0) + temp;\n  float part2 = value * 0.5453;\n  float part3 = cos(theta1 + theta2) * 0.43758;\n  return fract(part1 + part2 + part3);\n}\nvoid main() {\n  vec3 nbr_color = vec3(0.0, 0.0, 0.0);\n  vec2 coord;\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  coord.x = v_texcoord.x - 0.5 * stepsizeX;\n  coord.y = v_texcoord.y - stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  coord.x = v_texcoord.x - stepsizeX;\n  coord.y = v_texcoord.y + 0.5 * stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  coord.x = v_texcoord.x + stepsizeX;\n  coord.y = v_texcoord.y - 0.5 * stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  coord.x = v_texcoord.x + stepsizeX;\n  coord.y = v_texcoord.y + 0.5 * stepsizeY;\n  nbr_color += texture2D(tex_sampler_0, coord).rgb - color.rgb;\n  vec3 s_color = vec3(color.rgb + 0.3 * nbr_color);\n  vec3 c_color = vec3(0.0, 0.0, 0.0);\n  float value;\n  if (s_color.r < 0.5) {\n    value = s_color.r;\n  } else {\n    value = 1.0 - s_color.r;\n  }\n  float red = 4.0 * value * value * value;\n  if (s_color.r < 0.5) {\n    c_color.r = red;\n  } else {\n    c_color.r = 1.0 - red;\n  }\n  if (s_color.g < 0.5) {\n    value = s_color.g;\n  } else {\n    value = 1.0 - s_color.g;\n  }\n  float green = 2.0 * value * value;\n  if (s_color.g < 0.5) {\n    c_color.g = green;\n  } else {\n    c_color.g = 1.0 - green;\n  }\n  c_color.b = s_color.b * 0.5 + 0.25;\n  float dither = rand(v_texcoord + seed);\n  vec3 xform = clamp((c_color.rgb - 0.15) * 1.53846, 0.0, 1.0);\n  vec3 temp = clamp((color.rgb + stepsize - 0.15) * 1.53846, 0.0, 1.0);\n  vec3 bw_color = clamp(xform + (temp - xform) * (dither - 0.5), 0.0, 1.0);\n  coord = v_texcoord - vec2(0.5, 0.5);\n  float dist = length(coord * scale);\n  float lumen = 0.85 / (1.0 + exp((dist * inv_max_dist - 0.73) * 20.0)) + 0.15;\n  gl_FragColor = vec4(bw_color * lumen, color.a);\n}\n");
                shaderProgram.setMaximumTileSize(this.mTileSize);
                this.mProgram = shaderProgram;
                this.mTarget = i;
                return;
            default:
                throw new java.lang.RuntimeException("Filter Sharpen does not support frames of target " + i + "!");
        }
    }

    private void initParameters() {
        if (this.mProgram != null) {
            float[] fArr = new float[2];
            if (this.mWidth > this.mHeight) {
                fArr[0] = 1.0f;
                fArr[1] = this.mHeight / this.mWidth;
            } else {
                fArr[0] = this.mWidth / this.mHeight;
                fArr[1] = 1.0f;
            }
            float sqrt = ((float) java.lang.Math.sqrt((fArr[0] * fArr[0]) + (fArr[1] * fArr[1]))) * 0.5f;
            this.mProgram.setHostValue("scale", fArr);
            this.mProgram.setHostValue("inv_max_dist", java.lang.Float.valueOf(1.0f / sqrt));
            this.mProgram.setHostValue("stepsize", java.lang.Float.valueOf(0.003921569f));
            this.mProgram.setHostValue("stepsizeX", java.lang.Float.valueOf(1.0f / this.mWidth));
            this.mProgram.setHostValue("stepsizeY", java.lang.Float.valueOf(1.0f / this.mHeight));
            this.mProgram.setHostValue("seed", new float[]{this.mRandom.nextFloat(), this.mRandom.nextFloat()});
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        if (this.mProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
        }
        if (format.getWidth() != this.mWidth || format.getHeight() != this.mHeight) {
            this.mWidth = format.getWidth();
            this.mHeight = format.getHeight();
            initParameters();
        }
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(format);
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }
}
