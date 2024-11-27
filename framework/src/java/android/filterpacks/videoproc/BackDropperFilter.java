package android.filterpacks.videoproc;

/* loaded from: classes.dex */
public class BackDropperFilter extends android.filterfw.core.Filter {
    private static final float DEFAULT_ACCEPT_STDDEV = 0.85f;
    private static final float DEFAULT_ADAPT_RATE_BG = 0.0f;
    private static final float DEFAULT_ADAPT_RATE_FG = 0.0f;
    private static final java.lang.String DEFAULT_AUTO_WB_SCALE = "0.25";
    private static final float DEFAULT_EXPOSURE_CHANGE = 1.0f;
    private static final int DEFAULT_HIER_LRG_EXPONENT = 3;
    private static final float DEFAULT_HIER_LRG_SCALE = 0.7f;
    private static final int DEFAULT_HIER_MID_EXPONENT = 2;
    private static final float DEFAULT_HIER_MID_SCALE = 0.6f;
    private static final int DEFAULT_HIER_SML_EXPONENT = 0;
    private static final float DEFAULT_HIER_SML_SCALE = 0.5f;
    private static final float DEFAULT_LEARNING_ADAPT_RATE = 0.2f;
    private static final int DEFAULT_LEARNING_DONE_THRESHOLD = 20;
    private static final int DEFAULT_LEARNING_DURATION = 40;
    private static final int DEFAULT_LEARNING_VERIFY_DURATION = 10;
    private static final float DEFAULT_MASK_BLEND_BG = 0.65f;
    private static final float DEFAULT_MASK_BLEND_FG = 0.95f;
    private static final int DEFAULT_MASK_HEIGHT_EXPONENT = 8;
    private static final float DEFAULT_MASK_VERIFY_RATE = 0.25f;
    private static final int DEFAULT_MASK_WIDTH_EXPONENT = 8;
    private static final float DEFAULT_UV_SCALE_FACTOR = 1.35f;
    private static final float DEFAULT_WHITE_BALANCE_BLUE_CHANGE = 0.0f;
    private static final float DEFAULT_WHITE_BALANCE_RED_CHANGE = 0.0f;
    private static final int DEFAULT_WHITE_BALANCE_TOGGLE = 0;
    private static final float DEFAULT_Y_SCALE_FACTOR = 0.4f;
    private static final java.lang.String DISTANCE_STORAGE_SCALE = "0.6";
    private static final java.lang.String MASK_SMOOTH_EXPONENT = "2.0";
    private static final java.lang.String MIN_VARIANCE = "3.0";
    private static final java.lang.String RGB_TO_YUV_MATRIX = "0.299, -0.168736,  0.5,      0.000, 0.587, -0.331264, -0.418688, 0.000, 0.114,  0.5,      -0.081312, 0.000, 0.000,  0.5,       0.5,      1.000 ";
    private static final java.lang.String TAG = "BackDropperFilter";
    private static final java.lang.String VARIANCE_STORAGE_SCALE = "5.0";
    private static final java.lang.String mAutomaticWhiteBalance = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float pyramid_depth;\nuniform bool autowb_toggle;\nvarying vec2 v_texcoord;\nvoid main() {\n   vec4 mean_video = texture2D(tex_sampler_0, v_texcoord, pyramid_depth);\n   vec4 mean_bg = texture2D(tex_sampler_1, v_texcoord, pyramid_depth);\n   float green_normalizer = mean_video.g / mean_bg.g;\n   vec4 adjusted_value = vec4(mean_bg.r / mean_video.r * green_normalizer, 1., \n                         mean_bg.b / mean_video.b * green_normalizer, 1.) * auto_wb_scale; \n   gl_FragColor = autowb_toggle ? adjusted_value : vec4(auto_wb_scale);\n}\n";
    private static final java.lang.String mBgDistanceShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform float subsample_level;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord, subsample_level);\n  vec4 fg = coeff_yuv * vec4(fg_rgb.rgb, 1.);\n  vec4 mean = texture2D(tex_sampler_1, v_texcoord);\n  vec4 variance = inv_var_scale * texture2D(tex_sampler_2, v_texcoord);\n\n  float dist_y = gauss_dist_y(fg.r, mean.r, variance.r);\n  float dist_uv = gauss_dist_uv(fg.gb, mean.gb, variance.gb);\n  gl_FragColor = vec4(0.5*fg.rg, dist_scale*dist_y, dist_scale*dist_uv);\n}\n";
    private static final java.lang.String mBgMaskShader = "uniform sampler2D tex_sampler_0;\nuniform float accept_variance;\nuniform vec2 yuv_weights;\nuniform float scale_lrg;\nuniform float scale_mid;\nuniform float scale_sml;\nuniform float exp_lrg;\nuniform float exp_mid;\nuniform float exp_sml;\nvarying vec2 v_texcoord;\nbool is_fg(vec2 dist_yc, float accept_variance) {\n  return ( dot(yuv_weights, dist_yc) >= accept_variance );\n}\nvoid main() {\n  vec4 dist_lrg_sc = texture2D(tex_sampler_0, v_texcoord, exp_lrg);\n  vec4 dist_mid_sc = texture2D(tex_sampler_0, v_texcoord, exp_mid);\n  vec4 dist_sml_sc = texture2D(tex_sampler_0, v_texcoord, exp_sml);\n  vec2 dist_lrg = inv_dist_scale * dist_lrg_sc.ba;\n  vec2 dist_mid = inv_dist_scale * dist_mid_sc.ba;\n  vec2 dist_sml = inv_dist_scale * dist_sml_sc.ba;\n  vec2 norm_dist = 0.75 * dist_sml / accept_variance;\n  bool is_fg_lrg = is_fg(dist_lrg, accept_variance * scale_lrg);\n  bool is_fg_mid = is_fg_lrg || is_fg(dist_mid, accept_variance * scale_mid);\n  float is_fg_sml =\n      float(is_fg_mid || is_fg(dist_sml, accept_variance * scale_sml));\n  float alpha = 0.5 * is_fg_sml + 0.3 * float(is_fg_mid) + 0.2 * float(is_fg_lrg);\n  gl_FragColor = vec4(alpha, norm_dist, is_fg_sml);\n}\n";
    private static final java.lang.String mBgSubtractForceShader = "  vec4 ghost_rgb = (fg_adjusted * 0.7 + vec4(0.3,0.3,0.4,0.))*0.65 + \n                   0.35*bg_rgb;\n  float glow_start = 0.75 * mask_blend_bg; \n  float glow_max   = mask_blend_bg; \n  gl_FragColor = mask.a < glow_start ? bg_rgb : \n                 mask.a < glow_max ? mix(bg_rgb, vec4(0.9,0.9,1.0,1.0), \n                                     (mask.a - glow_start) / (glow_max - glow_start) ) : \n                 mask.a < mask_blend_fg ? mix(vec4(0.9,0.9,1.0,1.0), ghost_rgb, \n                                    (mask.a - glow_max) / (mask_blend_fg - glow_max) ) : \n                 ghost_rgb;\n}\n";
    private static final java.lang.String mBgSubtractShader = "uniform mat3 bg_fit_transform;\nuniform float mask_blend_bg;\nuniform float mask_blend_fg;\nuniform float exposure_change;\nuniform float whitebalancered_change;\nuniform float whitebalanceblue_change;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform sampler2D tex_sampler_3;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec2 bg_texcoord = (bg_fit_transform * vec3(v_texcoord, 1.)).xy;\n  vec4 bg_rgb = texture2D(tex_sampler_1, bg_texcoord);\n  vec4 wb_auto_scale = texture2D(tex_sampler_3, v_texcoord) * exposure_change / auto_wb_scale;\n  vec4 wb_manual_scale = vec4(1. + whitebalancered_change, 1., 1. + whitebalanceblue_change, 1.);\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord);\n  vec4 fg_adjusted = fg_rgb * wb_manual_scale * wb_auto_scale;\n  vec4 mask = texture2D(tex_sampler_2, v_texcoord, \n                      2.0);\n  float alpha = smoothstep(mask_blend_bg, mask_blend_fg, mask.a);\n  gl_FragColor = mix(bg_rgb, fg_adjusted, alpha);\n";
    private static final java.lang.String mMaskVerifyShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float verify_rate;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 lastmask = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  float newmask = mix(lastmask.a, mask.a, verify_rate);\n  gl_FragColor = vec4(0., 0., 0., newmask);\n}\n";
    private static final java.lang.String mUpdateBgModelMeanShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform float subsample_level;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord, subsample_level);\n  vec4 fg = coeff_yuv * vec4(fg_rgb.rgb, 1.);\n  vec4 mean = texture2D(tex_sampler_1, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_2, v_texcoord, \n                      2.0);\n\n  float alpha = local_adapt_rate(mask.a);\n  vec4 new_mean = mix(mean, fg, alpha);\n  gl_FragColor = new_mean;\n}\n";
    private static final java.lang.String mUpdateBgModelVarianceShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform sampler2D tex_sampler_3;\nuniform float subsample_level;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord, subsample_level);\n  vec4 fg = coeff_yuv * vec4(fg_rgb.rgb, 1.);\n  vec4 mean = texture2D(tex_sampler_1, v_texcoord);\n  vec4 variance = inv_var_scale * texture2D(tex_sampler_2, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_3, v_texcoord, \n                      2.0);\n\n  float alpha = local_adapt_rate(mask.a);\n  vec4 cur_variance = (fg-mean)*(fg-mean);\n  vec4 new_variance = mix(variance, cur_variance, alpha);\n  new_variance = max(new_variance, vec4(min_variance));\n  gl_FragColor = var_scale * new_variance;\n}\n";
    private final int BACKGROUND_FILL_CROP;
    private final int BACKGROUND_FIT;
    private final int BACKGROUND_STRETCH;
    private android.filterfw.core.ShaderProgram copyShaderProgram;
    private boolean isOpen;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "acceptStddev")
    private float mAcceptStddev;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "adaptRateBg")
    private float mAdaptRateBg;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "adaptRateFg")
    private float mAdaptRateFg;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "learningAdaptRate")
    private float mAdaptRateLearning;
    private android.filterfw.core.GLFrame mAutoWB;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "autowbToggle")
    private int mAutoWBToggle;
    private android.filterfw.core.ShaderProgram mAutomaticWhiteBalanceProgram;
    private android.filterfw.core.MutableFrameFormat mAverageFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "backgroundFitMode")
    private int mBackgroundFitMode;
    private boolean mBackgroundFitModeChanged;
    private android.filterfw.core.ShaderProgram mBgDistProgram;
    private android.filterfw.core.GLFrame mBgInput;
    private android.filterfw.core.ShaderProgram mBgMaskProgram;
    private android.filterfw.core.GLFrame[] mBgMean;
    private android.filterfw.core.ShaderProgram mBgSubtractProgram;
    private android.filterfw.core.ShaderProgram mBgUpdateMeanProgram;
    private android.filterfw.core.ShaderProgram mBgUpdateVarianceProgram;
    private android.filterfw.core.GLFrame[] mBgVariance;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "chromaScale")
    private float mChromaScale;
    private android.filterfw.core.ShaderProgram mCopyOutProgram;
    private android.filterfw.core.GLFrame mDistance;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "exposureChange")
    private float mExposureChange;
    private int mFrameCount;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "hierLrgExp")
    private int mHierarchyLrgExp;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "hierLrgScale")
    private float mHierarchyLrgScale;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "hierMidExp")
    private int mHierarchyMidExp;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "hierMidScale")
    private float mHierarchyMidScale;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "hierSmlExp")
    private int mHierarchySmlExp;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "hierSmlScale")
    private float mHierarchySmlScale;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "learningDoneListener")
    private android.filterpacks.videoproc.BackDropperFilter.LearningDoneListener mLearningDoneListener;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "learningDuration")
    private int mLearningDuration;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "learningVerifyDuration")
    private int mLearningVerifyDuration;
    private final boolean mLogVerbose;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "lumScale")
    private float mLumScale;
    private android.filterfw.core.GLFrame mMask;
    private android.filterfw.core.GLFrame mMaskAverage;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maskBg")
    private float mMaskBg;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maskFg")
    private float mMaskFg;
    private android.filterfw.core.MutableFrameFormat mMaskFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maskHeightExp")
    private int mMaskHeightExp;
    private android.filterfw.core.GLFrame[] mMaskVerify;
    private android.filterfw.core.ShaderProgram mMaskVerifyProgram;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maskWidthExp")
    private int mMaskWidthExp;
    private android.filterfw.core.MutableFrameFormat mMemoryFormat;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "mirrorBg")
    private boolean mMirrorBg;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "orientation")
    private int mOrientation;
    private android.filterfw.core.FrameFormat mOutputFormat;
    private boolean mPingPong;

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = "provideDebugOutputs")
    private boolean mProvideDebugOutputs;
    private int mPyramidDepth;
    private float mRelativeAspect;
    private boolean mStartLearning;
    private int mSubsampleLevel;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "useTheForce")
    private boolean mUseTheForce;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "maskVerifyRate")
    private float mVerifyRate;
    private android.filterfw.core.GLFrame mVideoInput;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "whitebalanceblueChange")
    private float mWhiteBalanceBlueChange;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "whitebalanceredChange")
    private float mWhiteBalanceRedChange;
    private long startTime;
    private static final float[] DEFAULT_BG_FIT_TRANSFORM = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final java.lang.String[] mInputNames = {"video", android.net.NetworkPolicyManager.FIREWALL_CHAIN_NAME_BACKGROUND};
    private static final java.lang.String[] mOutputNames = {"video"};
    private static final java.lang.String[] mDebugOutputNames = {"debug1", "debug2"};
    private static java.lang.String mSharedUtilShader = "precision mediump float;\nuniform float fg_adapt_rate;\nuniform float bg_adapt_rate;\nconst mat4 coeff_yuv = mat4(0.299, -0.168736,  0.5,      0.000, 0.587, -0.331264, -0.418688, 0.000, 0.114,  0.5,      -0.081312, 0.000, 0.000,  0.5,       0.5,      1.000 );\nconst float dist_scale = 0.6;\nconst float inv_dist_scale = 1. / dist_scale;\nconst float var_scale=5.0;\nconst float inv_var_scale = 1. / var_scale;\nconst float min_variance = inv_var_scale *3.0/ 256.;\nconst float auto_wb_scale = 0.25;\n\nfloat gauss_dist_y(float y, float mean, float variance) {\n  float dist = (y - mean) * (y - mean) / variance;\n  return dist;\n}\nfloat gauss_dist_uv(vec2 uv, vec2 mean, vec2 variance) {\n  vec2 dist = (uv - mean) * (uv - mean) / variance;\n  return dist.r + dist.g;\n}\nfloat local_adapt_rate(float alpha) {\n  return mix(bg_adapt_rate, fg_adapt_rate, alpha);\n}\n\n";

    public interface LearningDoneListener {
        void onLearningDone(android.filterpacks.videoproc.BackDropperFilter backDropperFilter);
    }

    public BackDropperFilter(java.lang.String str) {
        super(str);
        this.BACKGROUND_STRETCH = 0;
        this.BACKGROUND_FIT = 1;
        this.BACKGROUND_FILL_CROP = 2;
        this.mBackgroundFitMode = 2;
        this.mLearningDuration = 40;
        this.mLearningVerifyDuration = 10;
        this.mAcceptStddev = DEFAULT_ACCEPT_STDDEV;
        this.mHierarchyLrgScale = DEFAULT_HIER_LRG_SCALE;
        this.mHierarchyMidScale = 0.6f;
        this.mHierarchySmlScale = 0.5f;
        this.mMaskWidthExp = 8;
        this.mMaskHeightExp = 8;
        this.mHierarchyLrgExp = 3;
        this.mHierarchyMidExp = 2;
        this.mHierarchySmlExp = 0;
        this.mLumScale = 0.4f;
        this.mChromaScale = DEFAULT_UV_SCALE_FACTOR;
        this.mMaskBg = DEFAULT_MASK_BLEND_BG;
        this.mMaskFg = DEFAULT_MASK_BLEND_FG;
        this.mExposureChange = 1.0f;
        this.mWhiteBalanceRedChange = 0.0f;
        this.mWhiteBalanceBlueChange = 0.0f;
        this.mAutoWBToggle = 0;
        this.mAdaptRateLearning = 0.2f;
        this.mAdaptRateBg = 0.0f;
        this.mAdaptRateFg = 0.0f;
        this.mVerifyRate = 0.25f;
        this.mLearningDoneListener = null;
        this.mUseTheForce = false;
        this.mProvideDebugOutputs = false;
        this.mMirrorBg = false;
        this.mOrientation = 0;
        this.startTime = -1L;
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
        java.lang.String str2 = android.os.SystemProperties.get("ro.media.effect.bgdropper.adj");
        if (str2.length() > 0) {
            try {
                this.mAcceptStddev += java.lang.Float.parseFloat(str2);
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Adjusting accept threshold by " + str2 + ", now " + this.mAcceptStddev);
                }
            } catch (java.lang.NumberFormatException e) {
                android.util.Log.e(TAG, "Badly formatted property ro.media.effect.bgdropper.adj: " + str2);
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        android.filterfw.core.MutableFrameFormat create = android.filterfw.format.ImageFormat.create(3, 0);
        for (java.lang.String str : mInputNames) {
            addMaskedInputPort(str, create);
        }
        for (java.lang.String str2 : mOutputNames) {
            addOutputBasedOnInput(str2, "video");
        }
        if (this.mProvideDebugOutputs) {
            for (java.lang.String str3 : mDebugOutputNames) {
                addOutputBasedOnInput(str3, "video");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.MutableFrameFormat mutableCopy = frameFormat.mutableCopy();
        if (!java.util.Arrays.asList(mOutputNames).contains(str)) {
            mutableCopy.setDimensions(0, 0);
        }
        return mutableCopy;
    }

    private boolean createMemoryFormat(android.filterfw.core.FrameFormat frameFormat) {
        if (this.mMemoryFormat != null) {
            return false;
        }
        if (frameFormat.getWidth() == 0 || frameFormat.getHeight() == 0) {
            throw new java.lang.RuntimeException("Attempting to process input frame with unknown size");
        }
        this.mMaskFormat = frameFormat.mutableCopy();
        int pow = (int) java.lang.Math.pow(2.0d, this.mMaskWidthExp);
        int pow2 = (int) java.lang.Math.pow(2.0d, this.mMaskHeightExp);
        this.mMaskFormat.setDimensions(pow, pow2);
        this.mPyramidDepth = java.lang.Math.max(this.mMaskWidthExp, this.mMaskHeightExp);
        this.mMemoryFormat = this.mMaskFormat.mutableCopy();
        int max = java.lang.Math.max(this.mMaskWidthExp, pyramidLevel(frameFormat.getWidth()));
        int max2 = java.lang.Math.max(this.mMaskHeightExp, pyramidLevel(frameFormat.getHeight()));
        this.mPyramidDepth = java.lang.Math.max(max, max2);
        int max3 = java.lang.Math.max(pow, (int) java.lang.Math.pow(2.0d, max));
        int max4 = java.lang.Math.max(pow2, (int) java.lang.Math.pow(2.0d, max2));
        this.mMemoryFormat.setDimensions(max3, max4);
        this.mSubsampleLevel = this.mPyramidDepth - java.lang.Math.max(this.mMaskWidthExp, this.mMaskHeightExp);
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Mask frames size " + pow + " x " + pow2);
            android.util.Log.v(TAG, "Pyramid levels " + max + " x " + max2);
            android.util.Log.v(TAG, "Memory frames size " + max3 + " x " + max4);
        }
        this.mAverageFormat = frameFormat.mutableCopy();
        this.mAverageFormat.setDimensions(1, 1);
        return true;
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Preparing BackDropperFilter!");
        }
        this.mBgMean = new android.filterfw.core.GLFrame[2];
        this.mBgVariance = new android.filterfw.core.GLFrame[2];
        this.mMaskVerify = new android.filterfw.core.GLFrame[2];
        this.copyShaderProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
    }

    private void allocateFrames(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FilterContext filterContext) {
        if (!createMemoryFormat(frameFormat)) {
            return;
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Allocating BackDropperFilter frames");
        }
        int size = this.mMaskFormat.getSize();
        byte[] bArr = new byte[size];
        byte[] bArr2 = new byte[size];
        byte[] bArr3 = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = Byte.MIN_VALUE;
            bArr2[i] = 10;
            bArr3[i] = 0;
        }
        for (int i2 = 0; i2 < 2; i2++) {
            this.mBgMean[i2] = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mMaskFormat);
            this.mBgMean[i2].setData(bArr, 0, size);
            this.mBgVariance[i2] = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mMaskFormat);
            this.mBgVariance[i2].setData(bArr2, 0, size);
            this.mMaskVerify[i2] = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mMaskFormat);
            this.mMaskVerify[i2].setData(bArr3, 0, size);
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Done allocating texture for Mean and Variance objects!");
        }
        this.mDistance = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mMaskFormat);
        this.mMask = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mMaskFormat);
        this.mAutoWB = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mAverageFormat);
        this.mVideoInput = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mMemoryFormat);
        this.mBgInput = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mMemoryFormat);
        this.mMaskAverage = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(this.mAverageFormat);
        this.mBgDistProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mBgDistanceShader);
        this.mBgDistProgram.setHostValue("subsample_level", java.lang.Float.valueOf(this.mSubsampleLevel));
        this.mBgMaskProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mBgMaskShader);
        this.mBgMaskProgram.setHostValue("accept_variance", java.lang.Float.valueOf(this.mAcceptStddev * this.mAcceptStddev));
        this.mBgMaskProgram.setHostValue("yuv_weights", new float[]{this.mLumScale, this.mChromaScale});
        this.mBgMaskProgram.setHostValue("scale_lrg", java.lang.Float.valueOf(this.mHierarchyLrgScale));
        this.mBgMaskProgram.setHostValue("scale_mid", java.lang.Float.valueOf(this.mHierarchyMidScale));
        this.mBgMaskProgram.setHostValue("scale_sml", java.lang.Float.valueOf(this.mHierarchySmlScale));
        this.mBgMaskProgram.setHostValue("exp_lrg", java.lang.Float.valueOf(this.mSubsampleLevel + this.mHierarchyLrgExp));
        this.mBgMaskProgram.setHostValue("exp_mid", java.lang.Float.valueOf(this.mSubsampleLevel + this.mHierarchyMidExp));
        this.mBgMaskProgram.setHostValue("exp_sml", java.lang.Float.valueOf(this.mSubsampleLevel + this.mHierarchySmlExp));
        if (this.mUseTheForce) {
            this.mBgSubtractProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mBgSubtractShader + mBgSubtractForceShader);
        } else {
            this.mBgSubtractProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mBgSubtractShader + "}\n");
        }
        this.mBgSubtractProgram.setHostValue("bg_fit_transform", DEFAULT_BG_FIT_TRANSFORM);
        this.mBgSubtractProgram.setHostValue("mask_blend_bg", java.lang.Float.valueOf(this.mMaskBg));
        this.mBgSubtractProgram.setHostValue("mask_blend_fg", java.lang.Float.valueOf(this.mMaskFg));
        this.mBgSubtractProgram.setHostValue("exposure_change", java.lang.Float.valueOf(this.mExposureChange));
        this.mBgSubtractProgram.setHostValue("whitebalanceblue_change", java.lang.Float.valueOf(this.mWhiteBalanceBlueChange));
        this.mBgSubtractProgram.setHostValue("whitebalancered_change", java.lang.Float.valueOf(this.mWhiteBalanceRedChange));
        this.mBgUpdateMeanProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mUpdateBgModelMeanShader);
        this.mBgUpdateMeanProgram.setHostValue("subsample_level", java.lang.Float.valueOf(this.mSubsampleLevel));
        this.mBgUpdateVarianceProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mUpdateBgModelVarianceShader);
        this.mBgUpdateVarianceProgram.setHostValue("subsample_level", java.lang.Float.valueOf(this.mSubsampleLevel));
        this.mCopyOutProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
        this.mAutomaticWhiteBalanceProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mAutomaticWhiteBalance);
        this.mAutomaticWhiteBalanceProgram.setHostValue("pyramid_depth", java.lang.Float.valueOf(this.mPyramidDepth));
        this.mAutomaticWhiteBalanceProgram.setHostValue("autowb_toggle", java.lang.Integer.valueOf(this.mAutoWBToggle));
        this.mMaskVerifyProgram = new android.filterfw.core.ShaderProgram(filterContext, mSharedUtilShader + mMaskVerifyShader);
        this.mMaskVerifyProgram.setHostValue("verify_rate", java.lang.Float.valueOf(this.mVerifyRate));
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Shader width set to " + this.mMemoryFormat.getWidth());
        }
        this.mRelativeAspect = 1.0f;
        this.mFrameCount = 0;
        this.mStartLearning = true;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput("video");
        android.filterfw.core.Frame pullInput2 = pullInput(android.net.NetworkPolicyManager.FIREWALL_CHAIN_NAME_BACKGROUND);
        allocateFrames(pullInput.getFormat(), filterContext);
        if (this.mStartLearning) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Starting learning");
            }
            this.mBgUpdateMeanProgram.setHostValue("bg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateLearning));
            this.mBgUpdateMeanProgram.setHostValue("fg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateLearning));
            this.mBgUpdateVarianceProgram.setHostValue("bg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateLearning));
            this.mBgUpdateVarianceProgram.setHostValue("fg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateLearning));
            this.mFrameCount = 0;
        }
        int i = !this.mPingPong ? 1 : 0;
        boolean z = this.mPingPong;
        this.mPingPong = !this.mPingPong;
        updateBgScaling(pullInput, pullInput2, this.mBackgroundFitModeChanged);
        this.mBackgroundFitModeChanged = false;
        this.copyShaderProgram.process(pullInput, this.mVideoInput);
        this.copyShaderProgram.process(pullInput2, this.mBgInput);
        this.mVideoInput.generateMipMap();
        this.mVideoInput.setTextureParameter(10241, 9985);
        this.mBgInput.generateMipMap();
        this.mBgInput.setTextureParameter(10241, 9985);
        if (this.mStartLearning) {
            this.copyShaderProgram.process(this.mVideoInput, this.mBgMean[i]);
            this.mStartLearning = false;
        }
        this.mBgDistProgram.process(new android.filterfw.core.Frame[]{this.mVideoInput, this.mBgMean[i], this.mBgVariance[i]}, this.mDistance);
        this.mDistance.generateMipMap();
        this.mDistance.setTextureParameter(10241, 9985);
        this.mBgMaskProgram.process(this.mDistance, this.mMask);
        this.mMask.generateMipMap();
        this.mMask.setTextureParameter(10241, 9985);
        this.mAutomaticWhiteBalanceProgram.process(new android.filterfw.core.Frame[]{this.mVideoInput, this.mBgInput}, this.mAutoWB);
        if (this.mFrameCount <= this.mLearningDuration) {
            pushOutput("video", pullInput);
            if (this.mFrameCount == this.mLearningDuration - this.mLearningVerifyDuration) {
                this.copyShaderProgram.process(this.mMask, this.mMaskVerify[z ? 1 : 0]);
                this.mBgUpdateMeanProgram.setHostValue("bg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateBg));
                this.mBgUpdateMeanProgram.setHostValue("fg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateFg));
                this.mBgUpdateVarianceProgram.setHostValue("bg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateBg));
                this.mBgUpdateVarianceProgram.setHostValue("fg_adapt_rate", java.lang.Float.valueOf(this.mAdaptRateFg));
            } else if (this.mFrameCount > this.mLearningDuration - this.mLearningVerifyDuration) {
                this.mMaskVerifyProgram.process(new android.filterfw.core.Frame[]{this.mMaskVerify[i], this.mMask}, this.mMaskVerify[z ? 1 : 0]);
                this.mMaskVerify[z ? 1 : 0].generateMipMap();
                this.mMaskVerify[z ? 1 : 0].setTextureParameter(10241, 9985);
            }
            if (this.mFrameCount == this.mLearningDuration) {
                this.copyShaderProgram.process(this.mMaskVerify[z ? 1 : 0], this.mMaskAverage);
                int i2 = this.mMaskAverage.getData().array()[3] & 255;
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, java.lang.String.format("Mask_average is %d, threshold is %d", java.lang.Integer.valueOf(i2), 20));
                }
                if (i2 >= 20) {
                    this.mStartLearning = true;
                } else {
                    if (this.mLogVerbose) {
                        android.util.Log.v(TAG, "Learning done");
                    }
                    if (this.mLearningDoneListener != null) {
                        this.mLearningDoneListener.onLearningDone(this);
                    }
                }
            }
        } else {
            android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(pullInput.getFormat());
            this.mBgSubtractProgram.process(new android.filterfw.core.Frame[]{pullInput, pullInput2, this.mMask, this.mAutoWB}, newFrame);
            pushOutput("video", newFrame);
            newFrame.release();
        }
        if (this.mFrameCount < this.mLearningDuration - this.mLearningVerifyDuration || this.mAdaptRateBg > 0.0d || this.mAdaptRateFg > 0.0d) {
            this.mBgUpdateMeanProgram.process(new android.filterfw.core.Frame[]{this.mVideoInput, this.mBgMean[i], this.mMask}, this.mBgMean[z ? 1 : 0]);
            this.mBgMean[z ? 1 : 0].generateMipMap();
            this.mBgMean[z ? 1 : 0].setTextureParameter(10241, 9985);
            this.mBgUpdateVarianceProgram.process(new android.filterfw.core.Frame[]{this.mVideoInput, this.mBgMean[i], this.mBgVariance[i], this.mMask}, this.mBgVariance[z ? 1 : 0]);
            this.mBgVariance[z ? 1 : 0].generateMipMap();
            this.mBgVariance[z ? 1 : 0].setTextureParameter(10241, 9985);
        }
        if (this.mProvideDebugOutputs) {
            android.filterfw.core.Frame newFrame2 = filterContext.getFrameManager().newFrame(pullInput.getFormat());
            this.mCopyOutProgram.process(pullInput, newFrame2);
            pushOutput("debug1", newFrame2);
            newFrame2.release();
            android.filterfw.core.Frame newFrame3 = filterContext.getFrameManager().newFrame(this.mMemoryFormat);
            this.mCopyOutProgram.process(this.mMask, newFrame3);
            pushOutput("debug2", newFrame3);
            newFrame3.release();
        }
        this.mFrameCount++;
        if (this.mLogVerbose && this.mFrameCount % 30 == 0) {
            if (this.startTime == -1) {
                filterContext.getGLEnvironment().activate();
                android.opengl.GLES20.glFinish();
                this.startTime = android.os.SystemClock.elapsedRealtime();
            } else {
                filterContext.getGLEnvironment().activate();
                android.opengl.GLES20.glFinish();
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                android.util.Log.v(TAG, "Avg. frame duration: " + java.lang.String.format("%.2f", java.lang.Double.valueOf((elapsedRealtime - this.startTime) / 30.0d)) + " ms. Avg. fps: " + java.lang.String.format("%.2f", java.lang.Double.valueOf(1000.0d / ((elapsedRealtime - this.startTime) / 30.0d))));
                this.startTime = elapsedRealtime;
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void close(android.filterfw.core.FilterContext filterContext) {
        if (this.mMemoryFormat == null) {
            return;
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Filter Closing!");
        }
        for (int i = 0; i < 2; i++) {
            this.mBgMean[i].release();
            this.mBgVariance[i].release();
            this.mMaskVerify[i].release();
        }
        this.mDistance.release();
        this.mMask.release();
        this.mAutoWB.release();
        this.mVideoInput.release();
        this.mBgInput.release();
        this.mMaskAverage.release();
        this.mMemoryFormat = null;
    }

    public synchronized void relearn() {
        this.mStartLearning = true;
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (str.equals("backgroundFitMode")) {
            this.mBackgroundFitModeChanged = true;
            return;
        }
        if (str.equals("acceptStddev")) {
            this.mBgMaskProgram.setHostValue("accept_variance", java.lang.Float.valueOf(this.mAcceptStddev * this.mAcceptStddev));
            return;
        }
        if (str.equals("hierLrgScale")) {
            this.mBgMaskProgram.setHostValue("scale_lrg", java.lang.Float.valueOf(this.mHierarchyLrgScale));
            return;
        }
        if (str.equals("hierMidScale")) {
            this.mBgMaskProgram.setHostValue("scale_mid", java.lang.Float.valueOf(this.mHierarchyMidScale));
            return;
        }
        if (str.equals("hierSmlScale")) {
            this.mBgMaskProgram.setHostValue("scale_sml", java.lang.Float.valueOf(this.mHierarchySmlScale));
            return;
        }
        if (str.equals("hierLrgExp")) {
            this.mBgMaskProgram.setHostValue("exp_lrg", java.lang.Float.valueOf(this.mSubsampleLevel + this.mHierarchyLrgExp));
            return;
        }
        if (str.equals("hierMidExp")) {
            this.mBgMaskProgram.setHostValue("exp_mid", java.lang.Float.valueOf(this.mSubsampleLevel + this.mHierarchyMidExp));
            return;
        }
        if (str.equals("hierSmlExp")) {
            this.mBgMaskProgram.setHostValue("exp_sml", java.lang.Float.valueOf(this.mSubsampleLevel + this.mHierarchySmlExp));
            return;
        }
        if (str.equals("lumScale") || str.equals("chromaScale")) {
            this.mBgMaskProgram.setHostValue("yuv_weights", new float[]{this.mLumScale, this.mChromaScale});
            return;
        }
        if (str.equals("maskBg")) {
            this.mBgSubtractProgram.setHostValue("mask_blend_bg", java.lang.Float.valueOf(this.mMaskBg));
            return;
        }
        if (str.equals("maskFg")) {
            this.mBgSubtractProgram.setHostValue("mask_blend_fg", java.lang.Float.valueOf(this.mMaskFg));
            return;
        }
        if (str.equals("exposureChange")) {
            this.mBgSubtractProgram.setHostValue("exposure_change", java.lang.Float.valueOf(this.mExposureChange));
            return;
        }
        if (str.equals("whitebalanceredChange")) {
            this.mBgSubtractProgram.setHostValue("whitebalancered_change", java.lang.Float.valueOf(this.mWhiteBalanceRedChange));
        } else if (str.equals("whitebalanceblueChange")) {
            this.mBgSubtractProgram.setHostValue("whitebalanceblue_change", java.lang.Float.valueOf(this.mWhiteBalanceBlueChange));
        } else if (str.equals("autowbToggle")) {
            this.mAutomaticWhiteBalanceProgram.setHostValue("autowb_toggle", java.lang.Integer.valueOf(this.mAutoWBToggle));
        }
    }

    private void updateBgScaling(android.filterfw.core.Frame frame, android.filterfw.core.Frame frame2, boolean z) {
        float f;
        float f2;
        float f3;
        float f4;
        float width = (frame.getFormat().getWidth() / frame.getFormat().getHeight()) / (frame2.getFormat().getWidth() / frame2.getFormat().getHeight());
        if (width != this.mRelativeAspect || z) {
            this.mRelativeAspect = width;
            switch (this.mBackgroundFitMode) {
                case 0:
                default:
                    f = 0.0f;
                    f2 = 0.0f;
                    f3 = 1.0f;
                    f4 = 1.0f;
                    break;
                case 1:
                    if (this.mRelativeAspect > 1.0f) {
                        f = 0.0f;
                        f2 = 0.5f - (this.mRelativeAspect * 0.5f);
                        f4 = this.mRelativeAspect * 1.0f;
                        f3 = 1.0f;
                        break;
                    } else {
                        float f5 = 0.5f - (0.5f / this.mRelativeAspect);
                        f3 = 1.0f / this.mRelativeAspect;
                        f2 = 0.0f;
                        f = f5;
                        f4 = 1.0f;
                        break;
                    }
                case 2:
                    if (this.mRelativeAspect > 1.0f) {
                        float f6 = 0.5f - (0.5f / this.mRelativeAspect);
                        f3 = 1.0f / this.mRelativeAspect;
                        f2 = 0.0f;
                        f = f6;
                        f4 = 1.0f;
                        break;
                    } else {
                        f = 0.0f;
                        f2 = 0.5f - (this.mRelativeAspect * 0.5f);
                        f4 = this.mRelativeAspect;
                        f3 = 1.0f;
                        break;
                    }
            }
            if (this.mMirrorBg) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Mirroring the background!");
                }
                if (this.mOrientation == 0 || this.mOrientation == 180) {
                    f4 = -f4;
                    f2 = 1.0f - f2;
                } else {
                    f3 = -f3;
                    f = 1.0f - f;
                }
            }
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "bgTransform: xMin, yMin, xWidth, yWidth : " + f2 + ", " + f + ", " + f4 + ", " + f3 + ", mRelAspRatio = " + this.mRelativeAspect);
            }
            this.mBgSubtractProgram.setHostValue("bg_fit_transform", new float[]{f4, 0.0f, 0.0f, 0.0f, f3, 0.0f, f2, f, 1.0f});
        }
    }

    private int pyramidLevel(int i) {
        return ((int) java.lang.Math.floor(java.lang.Math.log10(i) / java.lang.Math.log10(2.0d))) - 1;
    }
}
