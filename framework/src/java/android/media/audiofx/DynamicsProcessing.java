package android.media.audiofx;

/* loaded from: classes2.dex */
public final class DynamicsProcessing extends android.media.audiofx.AudioEffect {
    private static final int CHANNEL_COUNT_MAX = 32;
    private static final float CHANNEL_DEFAULT_INPUT_GAIN = 0.0f;
    private static final int CONFIG_DEFAULT_MBC_BANDS = 6;
    private static final int CONFIG_DEFAULT_POSTEQ_BANDS = 6;
    private static final int CONFIG_DEFAULT_PREEQ_BANDS = 6;
    private static final boolean CONFIG_DEFAULT_USE_LIMITER = true;
    private static final boolean CONFIG_DEFAULT_USE_MBC = true;
    private static final boolean CONFIG_DEFAULT_USE_POSTEQ = true;
    private static final boolean CONFIG_DEFAULT_USE_PREEQ = true;
    private static final int CONFIG_DEFAULT_VARIANT = 0;
    private static final float CONFIG_PREFERRED_FRAME_DURATION_MS = 10.0f;
    private static final float DEFAULT_MAX_FREQUENCY = 20000.0f;
    private static final float DEFAULT_MIN_FREQUENCY = 220.0f;
    private static final float EQ_DEFAULT_GAIN = 0.0f;
    private static final float LIMITER_DEFAULT_ATTACK_TIME = 1.0f;
    private static final boolean LIMITER_DEFAULT_ENABLED = true;
    private static final int LIMITER_DEFAULT_LINK_GROUP = 0;
    private static final float LIMITER_DEFAULT_POST_GAIN = 0.0f;
    private static final float LIMITER_DEFAULT_RATIO = 10.0f;
    private static final float LIMITER_DEFAULT_RELEASE_TIME = 60.0f;
    private static final float LIMITER_DEFAULT_THRESHOLD = -2.0f;
    private static final float MBC_DEFAULT_ATTACK_TIME = 3.0f;
    private static final boolean MBC_DEFAULT_ENABLED = true;
    private static final float MBC_DEFAULT_EXPANDER_RATIO = 1.0f;
    private static final float MBC_DEFAULT_KNEE_WIDTH = 0.0f;
    private static final float MBC_DEFAULT_NOISE_GATE_THRESHOLD = -90.0f;
    private static final float MBC_DEFAULT_POST_GAIN = 0.0f;
    private static final float MBC_DEFAULT_PRE_GAIN = 0.0f;
    private static final float MBC_DEFAULT_RATIO = 1.0f;
    private static final float MBC_DEFAULT_RELEASE_TIME = 80.0f;
    private static final float MBC_DEFAULT_THRESHOLD = -45.0f;
    private static final int PARAM_ENGINE_ARCHITECTURE = 48;
    private static final int PARAM_GET_CHANNEL_COUNT = 16;
    private static final int PARAM_INPUT_GAIN = 32;
    private static final int PARAM_LIMITER = 112;
    private static final int PARAM_MBC = 80;
    private static final int PARAM_MBC_BAND = 85;
    private static final int PARAM_POST_EQ = 96;
    private static final int PARAM_POST_EQ_BAND = 101;
    private static final int PARAM_PRE_EQ = 64;
    private static final int PARAM_PRE_EQ_BAND = 69;
    private static final boolean POSTEQ_DEFAULT_ENABLED = true;
    private static final boolean PREEQ_DEFAULT_ENABLED = true;
    private static final java.lang.String TAG = "DynamicsProcessing";
    public static final int VARIANT_FAVOR_FREQUENCY_RESOLUTION = 0;
    public static final int VARIANT_FAVOR_TIME_RESOLUTION = 1;
    private android.media.audiofx.DynamicsProcessing.BaseParameterListener mBaseParamListener;
    private int mChannelCount;
    private android.media.audiofx.DynamicsProcessing.OnParameterChangeListener mParamListener;
    private final java.lang.Object mParamListenerLock;
    private static final float mMinFreqLog = (float) java.lang.Math.log10(220.0d);
    private static final float mMaxFreqLog = (float) java.lang.Math.log10(20000.0d);

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.DynamicsProcessing dynamicsProcessing, int i, int i2);
    }

    public DynamicsProcessing(int i) {
        this(0, i);
    }

    public DynamicsProcessing(int i, int i2) {
        this(i, i2, null);
    }

    public DynamicsProcessing(int i, int i2, android.media.audiofx.DynamicsProcessing.Config config) {
        super(EFFECT_TYPE_DYNAMICS_PROCESSING, EFFECT_TYPE_NULL, i, i2);
        android.media.audiofx.DynamicsProcessing.Config config2;
        this.mChannelCount = 0;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
        if (i2 == 0) {
            android.util.Log.w(TAG, "WARNING: attaching a DynamicsProcessing to global output mix isdeprecated!");
        }
        this.mChannelCount = getChannelCount();
        if (config == null) {
            config2 = new android.media.audiofx.DynamicsProcessing.Config.Builder(0, this.mChannelCount, true, 6, true, 6, true, 6, true).build();
        } else {
            config2 = new android.media.audiofx.DynamicsProcessing.Config(this.mChannelCount, config);
        }
        setEngineArchitecture(config2.getVariant(), config2.getPreferredFrameDuration(), config2.isPreEqInUse(), config2.getPreEqBandCount(), config2.isMbcInUse(), config2.getMbcBandCount(), config2.isPostEqInUse(), config2.getPostEqBandCount(), config2.isLimiterInUse());
        for (int i3 = 0; i3 < this.mChannelCount; i3++) {
            updateEngineChannelByChannelIndex(i3, config2.getChannelByChannelIndex(i3));
        }
    }

    public android.media.audiofx.DynamicsProcessing.Config getConfig() {
        java.lang.Number[] numberArr = {0, java.lang.Float.valueOf(0.0f), 0, 0, 0, 0, 0, 0, 0};
        byte[] numberArrayToByteArray = numberArrayToByteArray(new java.lang.Number[]{48});
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr);
        android.media.audiofx.DynamicsProcessing.Config build = new android.media.audiofx.DynamicsProcessing.Config.Builder(numberArr[0].intValue(), this.mChannelCount, numberArr[2].intValue() > 0, numberArr[3].intValue(), numberArr[4].intValue() > 0, numberArr[5].intValue(), numberArr[6].intValue() > 0, numberArr[7].intValue(), numberArr[8].intValue() > 0).setPreferredFrameDuration(numberArr[1].floatValue()).build();
        for (int i = 0; i < this.mChannelCount; i++) {
            build.setChannelTo(i, queryEngineByChannelIndex(i));
        }
        return build;
    }

    public static class Stage {
        private boolean mEnabled;
        private boolean mInUse;

        public Stage(boolean z, boolean z2) {
            this.mInUse = z;
            this.mEnabled = z2;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        public boolean isInUse() {
            return this.mInUse;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(java.lang.String.format(" Stage InUse: %b\n", java.lang.Boolean.valueOf(isInUse())));
            if (isInUse()) {
                sb.append(java.lang.String.format(" Stage Enabled: %b\n", java.lang.Boolean.valueOf(this.mEnabled)));
            }
            return sb.toString();
        }
    }

    public static class BandStage extends android.media.audiofx.DynamicsProcessing.Stage {
        private int mBandCount;

        public BandStage(boolean z, boolean z2, int i) {
            super(z, z2);
            this.mBandCount = isInUse() ? i : 0;
        }

        public int getBandCount() {
            return this.mBandCount;
        }

        @Override // android.media.audiofx.DynamicsProcessing.Stage
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append(java.lang.String.format(" Band Count: %d\n", java.lang.Integer.valueOf(this.mBandCount)));
            }
            return sb.toString();
        }
    }

    public static class BandBase {
        private float mCutoffFrequency;
        private boolean mEnabled;

        public BandBase(boolean z, float f) {
            this.mEnabled = z;
            this.mCutoffFrequency = f;
        }

        public java.lang.String toString() {
            return java.lang.String.format(" Enabled: %b\n", java.lang.Boolean.valueOf(this.mEnabled)) + java.lang.String.format(" CutoffFrequency: %f\n", java.lang.Float.valueOf(this.mCutoffFrequency));
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        public float getCutoffFrequency() {
            return this.mCutoffFrequency;
        }

        public void setCutoffFrequency(float f) {
            this.mCutoffFrequency = f;
        }
    }

    public static final class EqBand extends android.media.audiofx.DynamicsProcessing.BandBase {
        private float mGain;

        public EqBand(boolean z, float f, float f2) {
            super(z, f);
            this.mGain = f2;
        }

        public EqBand(android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            super(eqBand.isEnabled(), eqBand.getCutoffFrequency());
            this.mGain = eqBand.mGain;
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandBase
        public java.lang.String toString() {
            return super.toString() + java.lang.String.format(" Gain: %f\n", java.lang.Float.valueOf(this.mGain));
        }

        public float getGain() {
            return this.mGain;
        }

        public void setGain(float f) {
            this.mGain = f;
        }
    }

    public static final class MbcBand extends android.media.audiofx.DynamicsProcessing.BandBase {
        private float mAttackTime;
        private float mExpanderRatio;
        private float mKneeWidth;
        private float mNoiseGateThreshold;
        private float mPostGain;
        private float mPreGain;
        private float mRatio;
        private float mReleaseTime;
        private float mThreshold;

        public MbcBand(boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            super(z, f);
            this.mAttackTime = f2;
            this.mReleaseTime = f3;
            this.mRatio = f4;
            this.mThreshold = f5;
            this.mKneeWidth = f6;
            this.mNoiseGateThreshold = f7;
            this.mExpanderRatio = f8;
            this.mPreGain = f9;
            this.mPostGain = f10;
        }

        public MbcBand(android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
            super(mbcBand.isEnabled(), mbcBand.getCutoffFrequency());
            this.mAttackTime = mbcBand.mAttackTime;
            this.mReleaseTime = mbcBand.mReleaseTime;
            this.mRatio = mbcBand.mRatio;
            this.mThreshold = mbcBand.mThreshold;
            this.mKneeWidth = mbcBand.mKneeWidth;
            this.mNoiseGateThreshold = mbcBand.mNoiseGateThreshold;
            this.mExpanderRatio = mbcBand.mExpanderRatio;
            this.mPreGain = mbcBand.mPreGain;
            this.mPostGain = mbcBand.mPostGain;
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandBase
        public java.lang.String toString() {
            return super.toString() + java.lang.String.format(" AttackTime: %f (ms)\n", java.lang.Float.valueOf(this.mAttackTime)) + java.lang.String.format(" ReleaseTime: %f (ms)\n", java.lang.Float.valueOf(this.mReleaseTime)) + java.lang.String.format(" Ratio: 1:%f\n", java.lang.Float.valueOf(this.mRatio)) + java.lang.String.format(" Threshold: %f (dB)\n", java.lang.Float.valueOf(this.mThreshold)) + java.lang.String.format(" NoiseGateThreshold: %f(dB)\n", java.lang.Float.valueOf(this.mNoiseGateThreshold)) + java.lang.String.format(" ExpanderRatio: %f:1\n", java.lang.Float.valueOf(this.mExpanderRatio)) + java.lang.String.format(" PreGain: %f (dB)\n", java.lang.Float.valueOf(this.mPreGain)) + java.lang.String.format(" PostGain: %f (dB)\n", java.lang.Float.valueOf(this.mPostGain));
        }

        public float getAttackTime() {
            return this.mAttackTime;
        }

        public void setAttackTime(float f) {
            this.mAttackTime = f;
        }

        public float getReleaseTime() {
            return this.mReleaseTime;
        }

        public void setReleaseTime(float f) {
            this.mReleaseTime = f;
        }

        public float getRatio() {
            return this.mRatio;
        }

        public void setRatio(float f) {
            this.mRatio = f;
        }

        public float getThreshold() {
            return this.mThreshold;
        }

        public void setThreshold(float f) {
            this.mThreshold = f;
        }

        public float getKneeWidth() {
            return this.mKneeWidth;
        }

        public void setKneeWidth(float f) {
            this.mKneeWidth = f;
        }

        public float getNoiseGateThreshold() {
            return this.mNoiseGateThreshold;
        }

        public void setNoiseGateThreshold(float f) {
            this.mNoiseGateThreshold = f;
        }

        public float getExpanderRatio() {
            return this.mExpanderRatio;
        }

        public void setExpanderRatio(float f) {
            this.mExpanderRatio = f;
        }

        public float getPreGain() {
            return this.mPreGain;
        }

        public void setPreGain(float f) {
            this.mPreGain = f;
        }

        public float getPostGain() {
            return this.mPostGain;
        }

        public void setPostGain(float f) {
            this.mPostGain = f;
        }
    }

    public static final class Eq extends android.media.audiofx.DynamicsProcessing.BandStage {
        private final android.media.audiofx.DynamicsProcessing.EqBand[] mBands;

        public Eq(boolean z, boolean z2, int i) {
            super(z, z2, i);
            float f;
            if (isInUse()) {
                this.mBands = new android.media.audiofx.DynamicsProcessing.EqBand[i];
                for (int i2 = 0; i2 < i; i2++) {
                    if (i <= 1) {
                        f = 20000.0f;
                    } else {
                        f = (float) java.lang.Math.pow(10.0d, android.media.audiofx.DynamicsProcessing.mMinFreqLog + ((i2 * (android.media.audiofx.DynamicsProcessing.mMaxFreqLog - android.media.audiofx.DynamicsProcessing.mMinFreqLog)) / (i - 1)));
                    }
                    this.mBands[i2] = new android.media.audiofx.DynamicsProcessing.EqBand(true, f, 0.0f);
                }
                return;
            }
            this.mBands = null;
        }

        public Eq(android.media.audiofx.DynamicsProcessing.Eq eq) {
            super(eq.isInUse(), eq.isEnabled(), eq.getBandCount());
            if (isInUse()) {
                this.mBands = new android.media.audiofx.DynamicsProcessing.EqBand[eq.mBands.length];
                for (int i = 0; i < this.mBands.length; i++) {
                    this.mBands[i] = new android.media.audiofx.DynamicsProcessing.EqBand(eq.mBands[i]);
                }
                return;
            }
            this.mBands = null;
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandStage, android.media.audiofx.DynamicsProcessing.Stage
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append("--->EqBands: " + this.mBands.length + "\n");
                for (int i = 0; i < this.mBands.length; i++) {
                    sb.append(java.lang.String.format("  Band %d\n", java.lang.Integer.valueOf(i)));
                    sb.append(this.mBands[i].toString());
                }
            }
            return sb.toString();
        }

        private void checkBand(int i) {
            if (this.mBands == null || i < 0 || i >= this.mBands.length) {
                throw new java.lang.IllegalArgumentException("band index " + i + " out of bounds");
            }
        }

        public void setBand(int i, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            checkBand(i);
            this.mBands[i] = new android.media.audiofx.DynamicsProcessing.EqBand(eqBand);
        }

        public android.media.audiofx.DynamicsProcessing.EqBand getBand(int i) {
            checkBand(i);
            return this.mBands[i];
        }
    }

    public static final class Mbc extends android.media.audiofx.DynamicsProcessing.BandStage {
        private final android.media.audiofx.DynamicsProcessing.MbcBand[] mBands;

        public Mbc(boolean z, boolean z2, int i) {
            super(z, z2, i);
            float f;
            if (isInUse()) {
                this.mBands = new android.media.audiofx.DynamicsProcessing.MbcBand[i];
                for (int i2 = 0; i2 < i; i2++) {
                    if (i <= 1) {
                        f = 20000.0f;
                    } else {
                        f = (float) java.lang.Math.pow(10.0d, android.media.audiofx.DynamicsProcessing.mMinFreqLog + ((i2 * (android.media.audiofx.DynamicsProcessing.mMaxFreqLog - android.media.audiofx.DynamicsProcessing.mMinFreqLog)) / (i - 1)));
                    }
                    this.mBands[i2] = new android.media.audiofx.DynamicsProcessing.MbcBand(true, f, 3.0f, android.media.audiofx.DynamicsProcessing.MBC_DEFAULT_RELEASE_TIME, 1.0f, android.media.audiofx.DynamicsProcessing.MBC_DEFAULT_THRESHOLD, 0.0f, android.media.audiofx.DynamicsProcessing.MBC_DEFAULT_NOISE_GATE_THRESHOLD, 1.0f, 0.0f, 0.0f);
                }
                return;
            }
            this.mBands = null;
        }

        public Mbc(android.media.audiofx.DynamicsProcessing.Mbc mbc) {
            super(mbc.isInUse(), mbc.isEnabled(), mbc.getBandCount());
            if (isInUse()) {
                this.mBands = new android.media.audiofx.DynamicsProcessing.MbcBand[mbc.mBands.length];
                for (int i = 0; i < this.mBands.length; i++) {
                    this.mBands[i] = new android.media.audiofx.DynamicsProcessing.MbcBand(mbc.mBands[i]);
                }
                return;
            }
            this.mBands = null;
        }

        @Override // android.media.audiofx.DynamicsProcessing.BandStage, android.media.audiofx.DynamicsProcessing.Stage
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append("--->MbcBands: " + this.mBands.length + "\n");
                for (int i = 0; i < this.mBands.length; i++) {
                    sb.append(java.lang.String.format("  Band %d\n", java.lang.Integer.valueOf(i)));
                    sb.append(this.mBands[i].toString());
                }
            }
            return sb.toString();
        }

        private void checkBand(int i) {
            if (this.mBands == null || i < 0 || i >= this.mBands.length) {
                throw new java.lang.IllegalArgumentException("band index " + i + " out of bounds");
            }
        }

        public void setBand(int i, android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
            checkBand(i);
            this.mBands[i] = new android.media.audiofx.DynamicsProcessing.MbcBand(mbcBand);
        }

        public android.media.audiofx.DynamicsProcessing.MbcBand getBand(int i) {
            checkBand(i);
            return this.mBands[i];
        }
    }

    public static final class Limiter extends android.media.audiofx.DynamicsProcessing.Stage {
        private float mAttackTime;
        private int mLinkGroup;
        private float mPostGain;
        private float mRatio;
        private float mReleaseTime;
        private float mThreshold;

        public Limiter(boolean z, boolean z2, int i, float f, float f2, float f3, float f4, float f5) {
            super(z, z2);
            this.mLinkGroup = i;
            this.mAttackTime = f;
            this.mReleaseTime = f2;
            this.mRatio = f3;
            this.mThreshold = f4;
            this.mPostGain = f5;
        }

        public Limiter(android.media.audiofx.DynamicsProcessing.Limiter limiter) {
            super(limiter.isInUse(), limiter.isEnabled());
            this.mLinkGroup = limiter.mLinkGroup;
            this.mAttackTime = limiter.mAttackTime;
            this.mReleaseTime = limiter.mReleaseTime;
            this.mRatio = limiter.mRatio;
            this.mThreshold = limiter.mThreshold;
            this.mPostGain = limiter.mPostGain;
        }

        @Override // android.media.audiofx.DynamicsProcessing.Stage
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(super.toString());
            if (isInUse()) {
                sb.append(java.lang.String.format(" LinkGroup: %d (group)\n", java.lang.Integer.valueOf(this.mLinkGroup)));
                sb.append(java.lang.String.format(" AttackTime: %f (ms)\n", java.lang.Float.valueOf(this.mAttackTime)));
                sb.append(java.lang.String.format(" ReleaseTime: %f (ms)\n", java.lang.Float.valueOf(this.mReleaseTime)));
                sb.append(java.lang.String.format(" Ratio: 1:%f\n", java.lang.Float.valueOf(this.mRatio)));
                sb.append(java.lang.String.format(" Threshold: %f (dB)\n", java.lang.Float.valueOf(this.mThreshold)));
                sb.append(java.lang.String.format(" PostGain: %f (dB)\n", java.lang.Float.valueOf(this.mPostGain)));
            }
            return sb.toString();
        }

        public int getLinkGroup() {
            return this.mLinkGroup;
        }

        public void setLinkGroup(int i) {
            this.mLinkGroup = i;
        }

        public float getAttackTime() {
            return this.mAttackTime;
        }

        public void setAttackTime(float f) {
            this.mAttackTime = f;
        }

        public float getReleaseTime() {
            return this.mReleaseTime;
        }

        public void setReleaseTime(float f) {
            this.mReleaseTime = f;
        }

        public float getRatio() {
            return this.mRatio;
        }

        public void setRatio(float f) {
            this.mRatio = f;
        }

        public float getThreshold() {
            return this.mThreshold;
        }

        public void setThreshold(float f) {
            this.mThreshold = f;
        }

        public float getPostGain() {
            return this.mPostGain;
        }

        public void setPostGain(float f) {
            this.mPostGain = f;
        }
    }

    public static final class Channel {
        private float mInputGain;
        private android.media.audiofx.DynamicsProcessing.Limiter mLimiter;
        private android.media.audiofx.DynamicsProcessing.Mbc mMbc;
        private android.media.audiofx.DynamicsProcessing.Eq mPostEq;
        private android.media.audiofx.DynamicsProcessing.Eq mPreEq;

        public Channel(float f, boolean z, int i, boolean z2, int i2, boolean z3, int i3, boolean z4) {
            this.mInputGain = f;
            this.mPreEq = new android.media.audiofx.DynamicsProcessing.Eq(z, true, i);
            this.mMbc = new android.media.audiofx.DynamicsProcessing.Mbc(z2, true, i2);
            this.mPostEq = new android.media.audiofx.DynamicsProcessing.Eq(z3, true, i3);
            this.mLimiter = new android.media.audiofx.DynamicsProcessing.Limiter(z4, true, 0, 1.0f, 60.0f, 10.0f, -2.0f, 0.0f);
        }

        public Channel(android.media.audiofx.DynamicsProcessing.Channel channel) {
            this.mInputGain = channel.mInputGain;
            this.mPreEq = new android.media.audiofx.DynamicsProcessing.Eq(channel.mPreEq);
            this.mMbc = new android.media.audiofx.DynamicsProcessing.Mbc(channel.mMbc);
            this.mPostEq = new android.media.audiofx.DynamicsProcessing.Eq(channel.mPostEq);
            this.mLimiter = new android.media.audiofx.DynamicsProcessing.Limiter(channel.mLimiter);
        }

        public java.lang.String toString() {
            return java.lang.String.format(" InputGain: %f\n", java.lang.Float.valueOf(this.mInputGain)) + "-->PreEq\n" + this.mPreEq.toString() + "-->MBC\n" + this.mMbc.toString() + "-->PostEq\n" + this.mPostEq.toString() + "-->Limiter\n" + this.mLimiter.toString();
        }

        public float getInputGain() {
            return this.mInputGain;
        }

        public void setInputGain(float f) {
            this.mInputGain = f;
        }

        public android.media.audiofx.DynamicsProcessing.Eq getPreEq() {
            return this.mPreEq;
        }

        public void setPreEq(android.media.audiofx.DynamicsProcessing.Eq eq) {
            if (eq.getBandCount() != this.mPreEq.getBandCount()) {
                throw new java.lang.IllegalArgumentException("PreEqBandCount changed from " + this.mPreEq.getBandCount() + " to " + eq.getBandCount());
            }
            this.mPreEq = new android.media.audiofx.DynamicsProcessing.Eq(eq);
        }

        public android.media.audiofx.DynamicsProcessing.EqBand getPreEqBand(int i) {
            return this.mPreEq.getBand(i);
        }

        public void setPreEqBand(int i, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            this.mPreEq.setBand(i, eqBand);
        }

        public android.media.audiofx.DynamicsProcessing.Mbc getMbc() {
            return this.mMbc;
        }

        public void setMbc(android.media.audiofx.DynamicsProcessing.Mbc mbc) {
            if (mbc.getBandCount() != this.mMbc.getBandCount()) {
                throw new java.lang.IllegalArgumentException("MbcBandCount changed from " + this.mMbc.getBandCount() + " to " + mbc.getBandCount());
            }
            this.mMbc = new android.media.audiofx.DynamicsProcessing.Mbc(mbc);
        }

        public android.media.audiofx.DynamicsProcessing.MbcBand getMbcBand(int i) {
            return this.mMbc.getBand(i);
        }

        public void setMbcBand(int i, android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
            this.mMbc.setBand(i, mbcBand);
        }

        public android.media.audiofx.DynamicsProcessing.Eq getPostEq() {
            return this.mPostEq;
        }

        public void setPostEq(android.media.audiofx.DynamicsProcessing.Eq eq) {
            if (eq.getBandCount() != this.mPostEq.getBandCount()) {
                throw new java.lang.IllegalArgumentException("PostEqBandCount changed from " + this.mPostEq.getBandCount() + " to " + eq.getBandCount());
            }
            this.mPostEq = new android.media.audiofx.DynamicsProcessing.Eq(eq);
        }

        public android.media.audiofx.DynamicsProcessing.EqBand getPostEqBand(int i) {
            return this.mPostEq.getBand(i);
        }

        public void setPostEqBand(int i, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            this.mPostEq.setBand(i, eqBand);
        }

        public android.media.audiofx.DynamicsProcessing.Limiter getLimiter() {
            return this.mLimiter;
        }

        public void setLimiter(android.media.audiofx.DynamicsProcessing.Limiter limiter) {
            this.mLimiter = new android.media.audiofx.DynamicsProcessing.Limiter(limiter);
        }
    }

    public static final class Config {
        private final android.media.audiofx.DynamicsProcessing.Channel[] mChannel;
        private final int mChannelCount;
        private final boolean mLimiterInUse;
        private final int mMbcBandCount;
        private final boolean mMbcInUse;
        private final int mPostEqBandCount;
        private final boolean mPostEqInUse;
        private final int mPreEqBandCount;
        private final boolean mPreEqInUse;
        private final float mPreferredFrameDuration;
        private final int mVariant;

        public Config(int i, float f, int i2, boolean z, int i3, boolean z2, int i4, boolean z3, int i5, boolean z4, android.media.audiofx.DynamicsProcessing.Channel[] channelArr) {
            this.mVariant = i;
            this.mPreferredFrameDuration = f;
            this.mChannelCount = i2;
            this.mPreEqInUse = z;
            this.mPreEqBandCount = i3;
            this.mMbcInUse = z2;
            this.mMbcBandCount = i4;
            this.mPostEqInUse = z3;
            this.mPostEqBandCount = i5;
            this.mLimiterInUse = z4;
            this.mChannel = new android.media.audiofx.DynamicsProcessing.Channel[this.mChannelCount];
            for (int i6 = 0; i6 < this.mChannelCount; i6++) {
                if (i6 < channelArr.length) {
                    this.mChannel[i6] = new android.media.audiofx.DynamicsProcessing.Channel(channelArr[i6]);
                }
            }
        }

        public Config(int i, android.media.audiofx.DynamicsProcessing.Config config) {
            this.mVariant = config.mVariant;
            this.mPreferredFrameDuration = config.mPreferredFrameDuration;
            this.mChannelCount = config.mChannelCount;
            this.mPreEqInUse = config.mPreEqInUse;
            this.mPreEqBandCount = config.mPreEqBandCount;
            this.mMbcInUse = config.mMbcInUse;
            this.mMbcBandCount = config.mMbcBandCount;
            this.mPostEqInUse = config.mPostEqInUse;
            this.mPostEqBandCount = config.mPostEqBandCount;
            this.mLimiterInUse = config.mLimiterInUse;
            if (this.mChannelCount != config.mChannel.length) {
                throw new java.lang.IllegalArgumentException("configuration channel counts differ " + this.mChannelCount + " !=" + config.mChannel.length);
            }
            if (i < 1) {
                throw new java.lang.IllegalArgumentException("channel resizing less than 1 not allowed");
            }
            this.mChannel = new android.media.audiofx.DynamicsProcessing.Channel[i];
            for (int i2 = 0; i2 < i; i2++) {
                if (i2 >= this.mChannelCount) {
                    this.mChannel[i2] = new android.media.audiofx.DynamicsProcessing.Channel(config.mChannel[this.mChannelCount - 1]);
                } else {
                    this.mChannel[i2] = new android.media.audiofx.DynamicsProcessing.Channel(config.mChannel[i2]);
                }
            }
        }

        public Config(android.media.audiofx.DynamicsProcessing.Config config) {
            this(config.mChannelCount, config);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(java.lang.String.format("Variant: %d\n", java.lang.Integer.valueOf(this.mVariant)));
            sb.append(java.lang.String.format("PreferredFrameDuration: %f\n", java.lang.Float.valueOf(this.mPreferredFrameDuration)));
            sb.append(java.lang.String.format("ChannelCount: %d\n", java.lang.Integer.valueOf(this.mChannelCount)));
            sb.append(java.lang.String.format("PreEq inUse: %b, bandCount:%d\n", java.lang.Boolean.valueOf(this.mPreEqInUse), java.lang.Integer.valueOf(this.mPreEqBandCount)));
            sb.append(java.lang.String.format("Mbc inUse: %b, bandCount: %d\n", java.lang.Boolean.valueOf(this.mMbcInUse), java.lang.Integer.valueOf(this.mMbcBandCount)));
            sb.append(java.lang.String.format("PostEq inUse: %b, bandCount: %d\n", java.lang.Boolean.valueOf(this.mPostEqInUse), java.lang.Integer.valueOf(this.mPostEqBandCount)));
            sb.append(java.lang.String.format("Limiter inUse: %b\n", java.lang.Boolean.valueOf(this.mLimiterInUse)));
            for (int i = 0; i < this.mChannel.length; i++) {
                sb.append(java.lang.String.format("==Channel %d\n", java.lang.Integer.valueOf(i)));
                sb.append(this.mChannel[i].toString());
            }
            return sb.toString();
        }

        private void checkChannel(int i) {
            if (i < 0 || i >= this.mChannel.length) {
                throw new java.lang.IllegalArgumentException("ChannelIndex out of bounds");
            }
        }

        public int getVariant() {
            return this.mVariant;
        }

        public float getPreferredFrameDuration() {
            return this.mPreferredFrameDuration;
        }

        public boolean isPreEqInUse() {
            return this.mPreEqInUse;
        }

        public int getPreEqBandCount() {
            return this.mPreEqBandCount;
        }

        public boolean isMbcInUse() {
            return this.mMbcInUse;
        }

        public int getMbcBandCount() {
            return this.mMbcBandCount;
        }

        public boolean isPostEqInUse() {
            return this.mPostEqInUse;
        }

        public int getPostEqBandCount() {
            return this.mPostEqBandCount;
        }

        public boolean isLimiterInUse() {
            return this.mLimiterInUse;
        }

        public android.media.audiofx.DynamicsProcessing.Channel getChannelByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i];
        }

        public void setChannelTo(int i, android.media.audiofx.DynamicsProcessing.Channel channel) {
            checkChannel(i);
            if (this.mMbcBandCount != channel.getMbc().getBandCount()) {
                throw new java.lang.IllegalArgumentException("MbcBandCount changed from " + this.mMbcBandCount + " to " + channel.getPreEq().getBandCount());
            }
            if (this.mPreEqBandCount != channel.getPreEq().getBandCount()) {
                throw new java.lang.IllegalArgumentException("PreEqBandCount changed from " + this.mPreEqBandCount + " to " + channel.getPreEq().getBandCount());
            }
            if (this.mPostEqBandCount != channel.getPostEq().getBandCount()) {
                throw new java.lang.IllegalArgumentException("PostEqBandCount changed from " + this.mPostEqBandCount + " to " + channel.getPostEq().getBandCount());
            }
            this.mChannel[i] = new android.media.audiofx.DynamicsProcessing.Channel(channel);
        }

        public void setAllChannelsTo(android.media.audiofx.DynamicsProcessing.Channel channel) {
            for (int i = 0; i < this.mChannel.length; i++) {
                setChannelTo(i, channel);
            }
        }

        public float getInputGainByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getInputGain();
        }

        public void setInputGainByChannelIndex(int i, float f) {
            checkChannel(i);
            this.mChannel[i].setInputGain(f);
        }

        public void setInputGainAllChannelsTo(float f) {
            for (int i = 0; i < this.mChannel.length; i++) {
                this.mChannel[i].setInputGain(f);
            }
        }

        public android.media.audiofx.DynamicsProcessing.Eq getPreEqByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getPreEq();
        }

        public void setPreEqByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Eq eq) {
            checkChannel(i);
            this.mChannel[i].setPreEq(eq);
        }

        public void setPreEqAllChannelsTo(android.media.audiofx.DynamicsProcessing.Eq eq) {
            for (int i = 0; i < this.mChannel.length; i++) {
                this.mChannel[i].setPreEq(eq);
            }
        }

        public android.media.audiofx.DynamicsProcessing.EqBand getPreEqBandByChannelIndex(int i, int i2) {
            checkChannel(i);
            return this.mChannel[i].getPreEqBand(i2);
        }

        public void setPreEqBandByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            checkChannel(i);
            this.mChannel[i].setPreEqBand(i2, eqBand);
        }

        public void setPreEqBandAllChannelsTo(int i, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            for (int i2 = 0; i2 < this.mChannel.length; i2++) {
                this.mChannel[i2].setPreEqBand(i, eqBand);
            }
        }

        public android.media.audiofx.DynamicsProcessing.Mbc getMbcByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getMbc();
        }

        public void setMbcByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Mbc mbc) {
            checkChannel(i);
            this.mChannel[i].setMbc(mbc);
        }

        public void setMbcAllChannelsTo(android.media.audiofx.DynamicsProcessing.Mbc mbc) {
            for (int i = 0; i < this.mChannel.length; i++) {
                this.mChannel[i].setMbc(mbc);
            }
        }

        public android.media.audiofx.DynamicsProcessing.MbcBand getMbcBandByChannelIndex(int i, int i2) {
            checkChannel(i);
            return this.mChannel[i].getMbcBand(i2);
        }

        public void setMbcBandByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
            checkChannel(i);
            this.mChannel[i].setMbcBand(i2, mbcBand);
        }

        public void setMbcBandAllChannelsTo(int i, android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
            for (int i2 = 0; i2 < this.mChannel.length; i2++) {
                this.mChannel[i2].setMbcBand(i, mbcBand);
            }
        }

        public android.media.audiofx.DynamicsProcessing.Eq getPostEqByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getPostEq();
        }

        public void setPostEqByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Eq eq) {
            checkChannel(i);
            this.mChannel[i].setPostEq(eq);
        }

        public void setPostEqAllChannelsTo(android.media.audiofx.DynamicsProcessing.Eq eq) {
            for (int i = 0; i < this.mChannel.length; i++) {
                this.mChannel[i].setPostEq(eq);
            }
        }

        public android.media.audiofx.DynamicsProcessing.EqBand getPostEqBandByChannelIndex(int i, int i2) {
            checkChannel(i);
            return this.mChannel[i].getPostEqBand(i2);
        }

        public void setPostEqBandByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            checkChannel(i);
            this.mChannel[i].setPostEqBand(i2, eqBand);
        }

        public void setPostEqBandAllChannelsTo(int i, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
            for (int i2 = 0; i2 < this.mChannel.length; i2++) {
                this.mChannel[i2].setPostEqBand(i, eqBand);
            }
        }

        public android.media.audiofx.DynamicsProcessing.Limiter getLimiterByChannelIndex(int i) {
            checkChannel(i);
            return this.mChannel[i].getLimiter();
        }

        public void setLimiterByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Limiter limiter) {
            checkChannel(i);
            this.mChannel[i].setLimiter(limiter);
        }

        public void setLimiterAllChannelsTo(android.media.audiofx.DynamicsProcessing.Limiter limiter) {
            for (int i = 0; i < this.mChannel.length; i++) {
                this.mChannel[i].setLimiter(limiter);
            }
        }

        public static final class Builder {
            private android.media.audiofx.DynamicsProcessing.Channel[] mChannel;
            private int mChannelCount;
            private boolean mLimiterInUse;
            private int mMbcBandCount;
            private boolean mMbcInUse;
            private int mPostEqBandCount;
            private boolean mPostEqInUse;
            private int mPreEqBandCount;
            private boolean mPreEqInUse;
            private float mPreferredFrameDuration = 10.0f;
            private int mVariant;

            public Builder(int i, int i2, boolean z, int i3, boolean z2, int i4, boolean z3, int i5, boolean z4) {
                this.mVariant = i;
                this.mChannelCount = i2;
                this.mPreEqInUse = z;
                this.mPreEqBandCount = i3;
                this.mMbcInUse = z2;
                this.mMbcBandCount = i4;
                this.mPostEqInUse = z3;
                this.mPostEqBandCount = i5;
                this.mLimiterInUse = z4;
                this.mChannel = new android.media.audiofx.DynamicsProcessing.Channel[this.mChannelCount];
                for (int i6 = 0; i6 < this.mChannelCount; i6++) {
                    this.mChannel[i6] = new android.media.audiofx.DynamicsProcessing.Channel(0.0f, this.mPreEqInUse, this.mPreEqBandCount, this.mMbcInUse, this.mMbcBandCount, this.mPostEqInUse, this.mPostEqBandCount, this.mLimiterInUse);
                }
            }

            private void checkChannel(int i) {
                if (i < 0 || i >= this.mChannel.length) {
                    throw new java.lang.IllegalArgumentException("ChannelIndex out of bounds");
                }
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setPreferredFrameDuration(float f) {
                if (f < 0.0f) {
                    throw new java.lang.IllegalArgumentException("Expected positive frameDuration");
                }
                this.mPreferredFrameDuration = f;
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setInputGainByChannelIndex(int i, float f) {
                checkChannel(i);
                this.mChannel[i].setInputGain(f);
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setInputGainAllChannelsTo(float f) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    this.mChannel[i].setInputGain(f);
                }
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setChannelTo(int i, android.media.audiofx.DynamicsProcessing.Channel channel) {
                checkChannel(i);
                if (this.mMbcBandCount != channel.getMbc().getBandCount()) {
                    throw new java.lang.IllegalArgumentException("MbcBandCount changed from " + this.mMbcBandCount + " to " + channel.getPreEq().getBandCount());
                }
                if (this.mPreEqBandCount != channel.getPreEq().getBandCount()) {
                    throw new java.lang.IllegalArgumentException("PreEqBandCount changed from " + this.mPreEqBandCount + " to " + channel.getPreEq().getBandCount());
                }
                if (this.mPostEqBandCount != channel.getPostEq().getBandCount()) {
                    throw new java.lang.IllegalArgumentException("PostEqBandCount changed from " + this.mPostEqBandCount + " to " + channel.getPostEq().getBandCount());
                }
                this.mChannel[i] = new android.media.audiofx.DynamicsProcessing.Channel(channel);
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setAllChannelsTo(android.media.audiofx.DynamicsProcessing.Channel channel) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setChannelTo(i, channel);
                }
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setPreEqByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Eq eq) {
                checkChannel(i);
                this.mChannel[i].setPreEq(eq);
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setPreEqAllChannelsTo(android.media.audiofx.DynamicsProcessing.Eq eq) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setPreEqByChannelIndex(i, eq);
                }
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setMbcByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Mbc mbc) {
                checkChannel(i);
                this.mChannel[i].setMbc(mbc);
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setMbcAllChannelsTo(android.media.audiofx.DynamicsProcessing.Mbc mbc) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setMbcByChannelIndex(i, mbc);
                }
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setPostEqByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Eq eq) {
                checkChannel(i);
                this.mChannel[i].setPostEq(eq);
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setPostEqAllChannelsTo(android.media.audiofx.DynamicsProcessing.Eq eq) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setPostEqByChannelIndex(i, eq);
                }
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setLimiterByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Limiter limiter) {
                checkChannel(i);
                this.mChannel[i].setLimiter(limiter);
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config.Builder setLimiterAllChannelsTo(android.media.audiofx.DynamicsProcessing.Limiter limiter) {
                for (int i = 0; i < this.mChannel.length; i++) {
                    setLimiterByChannelIndex(i, limiter);
                }
                return this;
            }

            public android.media.audiofx.DynamicsProcessing.Config build() {
                return new android.media.audiofx.DynamicsProcessing.Config(this.mVariant, this.mPreferredFrameDuration, this.mChannelCount, this.mPreEqInUse, this.mPreEqBandCount, this.mMbcInUse, this.mMbcBandCount, this.mPostEqInUse, this.mPostEqBandCount, this.mLimiterInUse, this.mChannel);
            }
        }
    }

    public android.media.audiofx.DynamicsProcessing.Channel getChannelByChannelIndex(int i) {
        return queryEngineByChannelIndex(i);
    }

    public void setChannelTo(int i, android.media.audiofx.DynamicsProcessing.Channel channel) {
        updateEngineChannelByChannelIndex(i, channel);
    }

    public void setAllChannelsTo(android.media.audiofx.DynamicsProcessing.Channel channel) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setChannelTo(i, channel);
        }
    }

    public float getInputGainByChannelIndex(int i) {
        return getTwoFloat(32, i);
    }

    public void setInputGainbyChannel(int i, float f) {
        setTwoFloat(32, i, f);
    }

    public void setInputGainAllChannelsTo(float f) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setInputGainbyChannel(i, f);
        }
    }

    public android.media.audiofx.DynamicsProcessing.Eq getPreEqByChannelIndex(int i) {
        return queryEngineEqByChannelIndex(64, i);
    }

    public void setPreEqByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Eq eq) {
        updateEngineEqByChannelIndex(64, i, eq);
    }

    public void setPreEqAllChannelsTo(android.media.audiofx.DynamicsProcessing.Eq eq) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setPreEqByChannelIndex(i, eq);
        }
    }

    public android.media.audiofx.DynamicsProcessing.EqBand getPreEqBandByChannelIndex(int i, int i2) {
        return queryEngineEqBandByChannelIndex(69, i, i2);
    }

    public void setPreEqBandByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
        updateEngineEqBandByChannelIndex(69, i, i2, eqBand);
    }

    public void setPreEqBandAllChannelsTo(int i, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
        for (int i2 = 0; i2 < this.mChannelCount; i2++) {
            setPreEqBandByChannelIndex(i2, i, eqBand);
        }
    }

    public android.media.audiofx.DynamicsProcessing.Mbc getMbcByChannelIndex(int i) {
        return queryEngineMbcByChannelIndex(i);
    }

    public void setMbcByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Mbc mbc) {
        updateEngineMbcByChannelIndex(i, mbc);
    }

    public void setMbcAllChannelsTo(android.media.audiofx.DynamicsProcessing.Mbc mbc) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setMbcByChannelIndex(i, mbc);
        }
    }

    public android.media.audiofx.DynamicsProcessing.MbcBand getMbcBandByChannelIndex(int i, int i2) {
        return queryEngineMbcBandByChannelIndex(i, i2);
    }

    public void setMbcBandByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
        updateEngineMbcBandByChannelIndex(i, i2, mbcBand);
    }

    public void setMbcBandAllChannelsTo(int i, android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
        for (int i2 = 0; i2 < this.mChannelCount; i2++) {
            setMbcBandByChannelIndex(i2, i, mbcBand);
        }
    }

    public android.media.audiofx.DynamicsProcessing.Eq getPostEqByChannelIndex(int i) {
        return queryEngineEqByChannelIndex(96, i);
    }

    public void setPostEqByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Eq eq) {
        updateEngineEqByChannelIndex(96, i, eq);
    }

    public void setPostEqAllChannelsTo(android.media.audiofx.DynamicsProcessing.Eq eq) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setPostEqByChannelIndex(i, eq);
        }
    }

    public android.media.audiofx.DynamicsProcessing.EqBand getPostEqBandByChannelIndex(int i, int i2) {
        return queryEngineEqBandByChannelIndex(101, i, i2);
    }

    public void setPostEqBandByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
        updateEngineEqBandByChannelIndex(101, i, i2, eqBand);
    }

    public void setPostEqBandAllChannelsTo(int i, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
        for (int i2 = 0; i2 < this.mChannelCount; i2++) {
            setPostEqBandByChannelIndex(i2, i, eqBand);
        }
    }

    public android.media.audiofx.DynamicsProcessing.Limiter getLimiterByChannelIndex(int i) {
        return queryEngineLimiterByChannelIndex(i);
    }

    public void setLimiterByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Limiter limiter) {
        updateEngineLimiterByChannelIndex(i, limiter);
    }

    public void setLimiterAllChannelsTo(android.media.audiofx.DynamicsProcessing.Limiter limiter) {
        for (int i = 0; i < this.mChannelCount; i++) {
            setLimiterByChannelIndex(i, limiter);
        }
    }

    public int getChannelCount() {
        return getOneInt(16);
    }

    private void setEngineArchitecture(int i, float f, boolean z, int i2, boolean z2, int i3, boolean z3, int i4, boolean z4) {
        setNumberArray(new java.lang.Number[]{48}, new java.lang.Number[]{java.lang.Integer.valueOf(i), java.lang.Float.valueOf(f), java.lang.Integer.valueOf(z ? 1 : 0), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(z2 ? 1 : 0), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(z3 ? 1 : 0), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(z4 ? 1 : 0)});
    }

    private void updateEngineEqBandByChannelIndex(int i, int i2, int i3, android.media.audiofx.DynamicsProcessing.EqBand eqBand) {
        setNumberArray(new java.lang.Number[]{java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)}, new java.lang.Number[]{java.lang.Integer.valueOf(eqBand.isEnabled() ? 1 : 0), java.lang.Float.valueOf(eqBand.getCutoffFrequency()), java.lang.Float.valueOf(eqBand.getGain())});
    }

    private android.media.audiofx.DynamicsProcessing.Eq queryEngineEqByChannelIndex(int i, int i2) {
        java.lang.Number[] numberArr = new java.lang.Number[2];
        numberArr[0] = java.lang.Integer.valueOf(i == 64 ? 64 : 96);
        numberArr[1] = java.lang.Integer.valueOf(i2);
        java.lang.Number[] numberArr2 = {0, 0, 0};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        int intValue = numberArr2[2].intValue();
        android.media.audiofx.DynamicsProcessing.Eq eq = new android.media.audiofx.DynamicsProcessing.Eq(numberArr2[0].intValue() > 0, numberArr2[1].intValue() > 0, intValue);
        for (int i3 = 0; i3 < intValue; i3++) {
            eq.setBand(i3, queryEngineEqBandByChannelIndex(i == 64 ? 69 : 101, i2, i3));
        }
        return eq;
    }

    private android.media.audiofx.DynamicsProcessing.EqBand queryEngineEqBandByChannelIndex(int i, int i2, int i3) {
        java.lang.Number[] numberArr = {java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)};
        java.lang.Float valueOf = java.lang.Float.valueOf(0.0f);
        java.lang.Number[] numberArr2 = {0, valueOf, valueOf};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        return new android.media.audiofx.DynamicsProcessing.EqBand(numberArr2[0].intValue() > 0, numberArr2[1].floatValue(), numberArr2[2].floatValue());
    }

    private void updateEngineEqByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.Eq eq) {
        int bandCount = eq.getBandCount();
        setNumberArray(new java.lang.Number[]{java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)}, new java.lang.Number[]{java.lang.Integer.valueOf(eq.isInUse() ? 1 : 0), java.lang.Integer.valueOf(eq.isEnabled() ? 1 : 0), java.lang.Integer.valueOf(bandCount)});
        for (int i3 = 0; i3 < bandCount; i3++) {
            updateEngineEqBandByChannelIndex(i == 64 ? 69 : 101, i2, i3, eq.getBand(i3));
        }
    }

    private android.media.audiofx.DynamicsProcessing.Mbc queryEngineMbcByChannelIndex(int i) {
        java.lang.Number[] numberArr = {0, 0, 0};
        byte[] numberArrayToByteArray = numberArrayToByteArray(new java.lang.Number[]{80, java.lang.Integer.valueOf(i)});
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr);
        int intValue = numberArr[2].intValue();
        android.media.audiofx.DynamicsProcessing.Mbc mbc = new android.media.audiofx.DynamicsProcessing.Mbc(numberArr[0].intValue() > 0, numberArr[1].intValue() > 0, intValue);
        for (int i2 = 0; i2 < intValue; i2++) {
            mbc.setBand(i2, queryEngineMbcBandByChannelIndex(i, i2));
        }
        return mbc;
    }

    private android.media.audiofx.DynamicsProcessing.MbcBand queryEngineMbcBandByChannelIndex(int i, int i2) {
        java.lang.Number[] numberArr = {85, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)};
        java.lang.Float valueOf = java.lang.Float.valueOf(0.0f);
        java.lang.Number[] numberArr2 = {0, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf, valueOf};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        return new android.media.audiofx.DynamicsProcessing.MbcBand(numberArr2[0].intValue() > 0, numberArr2[1].floatValue(), numberArr2[2].floatValue(), numberArr2[3].floatValue(), numberArr2[4].floatValue(), numberArr2[5].floatValue(), numberArr2[6].floatValue(), numberArr2[7].floatValue(), numberArr2[8].floatValue(), numberArr2[9].floatValue(), numberArr2[10].floatValue());
    }

    private void updateEngineMbcBandByChannelIndex(int i, int i2, android.media.audiofx.DynamicsProcessing.MbcBand mbcBand) {
        setNumberArray(new java.lang.Number[]{85, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)}, new java.lang.Number[]{java.lang.Integer.valueOf(mbcBand.isEnabled() ? 1 : 0), java.lang.Float.valueOf(mbcBand.getCutoffFrequency()), java.lang.Float.valueOf(mbcBand.getAttackTime()), java.lang.Float.valueOf(mbcBand.getReleaseTime()), java.lang.Float.valueOf(mbcBand.getRatio()), java.lang.Float.valueOf(mbcBand.getThreshold()), java.lang.Float.valueOf(mbcBand.getKneeWidth()), java.lang.Float.valueOf(mbcBand.getNoiseGateThreshold()), java.lang.Float.valueOf(mbcBand.getExpanderRatio()), java.lang.Float.valueOf(mbcBand.getPreGain()), java.lang.Float.valueOf(mbcBand.getPostGain())});
    }

    private void updateEngineMbcByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Mbc mbc) {
        int bandCount = mbc.getBandCount();
        setNumberArray(new java.lang.Number[]{80, java.lang.Integer.valueOf(i)}, new java.lang.Number[]{java.lang.Integer.valueOf(mbc.isInUse() ? 1 : 0), java.lang.Integer.valueOf(mbc.isEnabled() ? 1 : 0), java.lang.Integer.valueOf(bandCount)});
        for (int i2 = 0; i2 < bandCount; i2++) {
            updateEngineMbcBandByChannelIndex(i, i2, mbc.getBand(i2));
        }
    }

    private void updateEngineLimiterByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Limiter limiter) {
        setNumberArray(new java.lang.Number[]{112, java.lang.Integer.valueOf(i)}, new java.lang.Number[]{java.lang.Integer.valueOf(limiter.isInUse() ? 1 : 0), java.lang.Integer.valueOf(limiter.isEnabled() ? 1 : 0), java.lang.Integer.valueOf(limiter.getLinkGroup()), java.lang.Float.valueOf(limiter.getAttackTime()), java.lang.Float.valueOf(limiter.getReleaseTime()), java.lang.Float.valueOf(limiter.getRatio()), java.lang.Float.valueOf(limiter.getThreshold()), java.lang.Float.valueOf(limiter.getPostGain())});
    }

    private android.media.audiofx.DynamicsProcessing.Limiter queryEngineLimiterByChannelIndex(int i) {
        java.lang.Number[] numberArr = {112, java.lang.Integer.valueOf(i)};
        java.lang.Float valueOf = java.lang.Float.valueOf(0.0f);
        java.lang.Number[] numberArr2 = {0, 0, 0, valueOf, valueOf, valueOf, valueOf, valueOf};
        byte[] numberArrayToByteArray = numberArrayToByteArray(numberArr);
        byte[] numberArrayToByteArray2 = numberArrayToByteArray(numberArr2);
        getParameter(numberArrayToByteArray, numberArrayToByteArray2);
        byteArrayToNumberArray(numberArrayToByteArray2, numberArr2);
        return new android.media.audiofx.DynamicsProcessing.Limiter(numberArr2[0].intValue() > 0, numberArr2[1].intValue() > 0, numberArr2[2].intValue(), numberArr2[3].floatValue(), numberArr2[4].floatValue(), numberArr2[5].floatValue(), numberArr2[6].floatValue(), numberArr2[7].floatValue());
    }

    private android.media.audiofx.DynamicsProcessing.Channel queryEngineByChannelIndex(int i) {
        float twoFloat = getTwoFloat(32, i);
        android.media.audiofx.DynamicsProcessing.Eq queryEngineEqByChannelIndex = queryEngineEqByChannelIndex(64, i);
        android.media.audiofx.DynamicsProcessing.Mbc queryEngineMbcByChannelIndex = queryEngineMbcByChannelIndex(i);
        android.media.audiofx.DynamicsProcessing.Eq queryEngineEqByChannelIndex2 = queryEngineEqByChannelIndex(96, i);
        android.media.audiofx.DynamicsProcessing.Limiter queryEngineLimiterByChannelIndex = queryEngineLimiterByChannelIndex(i);
        android.media.audiofx.DynamicsProcessing.Channel channel = new android.media.audiofx.DynamicsProcessing.Channel(twoFloat, queryEngineEqByChannelIndex.isInUse(), queryEngineEqByChannelIndex.getBandCount(), queryEngineMbcByChannelIndex.isInUse(), queryEngineMbcByChannelIndex.getBandCount(), queryEngineEqByChannelIndex2.isInUse(), queryEngineEqByChannelIndex2.getBandCount(), queryEngineLimiterByChannelIndex.isInUse());
        channel.setInputGain(twoFloat);
        channel.setPreEq(queryEngineEqByChannelIndex);
        channel.setMbc(queryEngineMbcByChannelIndex);
        channel.setPostEq(queryEngineEqByChannelIndex2);
        channel.setLimiter(queryEngineLimiterByChannelIndex);
        return channel;
    }

    private void updateEngineChannelByChannelIndex(int i, android.media.audiofx.DynamicsProcessing.Channel channel) {
        setTwoFloat(32, i, channel.getInputGain());
        updateEngineEqByChannelIndex(64, i, channel.getPreEq());
        updateEngineMbcByChannelIndex(i, channel.getMbc());
        updateEngineEqByChannelIndex(96, i, channel.getPostEq());
        updateEngineLimiterByChannelIndex(i, channel.getLimiter());
    }

    private int getOneInt(int i) {
        int[] iArr = new int[1];
        checkStatus(getParameter(new int[]{i}, iArr));
        return iArr[0];
    }

    private void setTwoFloat(int i, int i2, float f) {
        checkStatus(setParameter(new int[]{i, i2}, floatToByteArray(f)));
    }

    private byte[] numberArrayToByteArray(java.lang.Number[] numberArr) {
        int i = 0;
        for (int i2 = 0; i2 < numberArr.length; i2++) {
            if ((numberArr[i2] instanceof java.lang.Integer) || (numberArr[i2] instanceof java.lang.Float)) {
                i += 4;
            } else {
                throw new java.lang.IllegalArgumentException("unknown value type " + numberArr[i2].getClass());
            }
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(i);
        allocate.order(java.nio.ByteOrder.nativeOrder());
        for (int i3 = 0; i3 < numberArr.length; i3++) {
            if (numberArr[i3] instanceof java.lang.Integer) {
                allocate.putInt(numberArr[i3].intValue());
            } else if (numberArr[i3] instanceof java.lang.Float) {
                allocate.putFloat(numberArr[i3].floatValue());
            }
        }
        return allocate.array();
    }

    private void byteArrayToNumberArray(byte[] bArr, java.lang.Number[] numberArr) {
        int i = 0;
        int i2 = 0;
        while (i < bArr.length && i2 < numberArr.length) {
            if (numberArr[i2] instanceof java.lang.Integer) {
                numberArr[i2] = java.lang.Integer.valueOf(byteArrayToInt(bArr, i));
                i += 4;
                i2++;
            } else if (numberArr[i2] instanceof java.lang.Float) {
                numberArr[i2] = java.lang.Float.valueOf(byteArrayToFloat(bArr, i));
                i += 4;
                i2++;
            } else {
                throw new java.lang.IllegalArgumentException("can't convert " + numberArr[i2].getClass());
            }
        }
        if (i2 != numberArr.length) {
            throw new java.lang.IllegalArgumentException("only converted " + i2 + " values out of " + numberArr.length + " expected");
        }
    }

    private void setNumberArray(java.lang.Number[] numberArr, java.lang.Number[] numberArr2) {
        checkStatus(setParameter(numberArrayToByteArray(numberArr), numberArrayToByteArray(numberArr2)));
    }

    private float getTwoFloat(int i, int i2) {
        int[] iArr = {i, i2};
        byte[] bArr = new byte[4];
        checkStatus(getParameter(iArr, bArr));
        return byteArrayToFloat(bArr);
    }

    private void updateEffectArchitecture() {
        this.mChannelCount = getChannelCount();
    }

    private class BaseParameterListener implements android.media.audiofx.AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            android.media.audiofx.DynamicsProcessing.OnParameterChangeListener onParameterChangeListener;
            int i2;
            int i3;
            if (i != 0) {
                return;
            }
            synchronized (android.media.audiofx.DynamicsProcessing.this.mParamListenerLock) {
                if (android.media.audiofx.DynamicsProcessing.this.mParamListener == null) {
                    onParameterChangeListener = null;
                } else {
                    onParameterChangeListener = android.media.audiofx.DynamicsProcessing.this.mParamListener;
                }
            }
            if (onParameterChangeListener != null) {
                if (bArr.length != 4) {
                    i2 = -1;
                } else {
                    i2 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 0);
                }
                if (bArr2.length != 4) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i3 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr2, 0);
                }
                if (i2 != -1 && i3 != Integer.MIN_VALUE) {
                    onParameterChangeListener.onParameterChange(android.media.audiofx.DynamicsProcessing.this, i2, i3);
                }
            }
        }
    }

    public void setParameterListener(android.media.audiofx.DynamicsProcessing.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mBaseParamListener = new android.media.audiofx.DynamicsProcessing.BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
            this.mParamListener = onParameterChangeListener;
        }
    }

    public static class Settings {
        public int channelCount;
        public float[] inputGain;

        public Settings() {
        }

        public Settings(java.lang.String str) {
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "=;");
            if (stringTokenizer.countTokens() != 3) {
                throw new java.lang.IllegalArgumentException("settings: " + str);
            }
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(android.media.audiofx.DynamicsProcessing.TAG)) {
                throw new java.lang.IllegalArgumentException("invalid settings for DynamicsProcessing: " + nextToken);
            }
            try {
                java.lang.String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("channelCount")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.channelCount = java.lang.Short.parseShort(stringTokenizer.nextToken());
                if (this.channelCount > 32) {
                    throw new java.lang.IllegalArgumentException("too many channels Settings:" + str);
                }
                if (stringTokenizer.countTokens() != this.channelCount * 1) {
                    throw new java.lang.IllegalArgumentException("settings: " + str);
                }
                this.inputGain = new float[this.channelCount];
                for (int i = 0; i < this.channelCount; i++) {
                    java.lang.String nextToken3 = stringTokenizer.nextToken();
                    if (!nextToken3.equals(i + "_inputGain")) {
                        throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken3);
                    }
                    this.inputGain[i] = java.lang.Float.parseFloat(stringTokenizer.nextToken());
                }
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public java.lang.String toString() {
            java.lang.String str = new java.lang.String("DynamicsProcessing;channelCount=" + java.lang.Integer.toString(this.channelCount));
            for (int i = 0; i < this.channelCount; i++) {
                str = str.concat(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR + i + "_inputGain=" + java.lang.Float.toString(this.inputGain[i]));
            }
            return str;
        }
    }

    public android.media.audiofx.DynamicsProcessing.Settings getProperties() {
        android.media.audiofx.DynamicsProcessing.Settings settings = new android.media.audiofx.DynamicsProcessing.Settings();
        settings.channelCount = getChannelCount();
        if (settings.channelCount > 32) {
            throw new java.lang.IllegalArgumentException("too many channels Settings:" + settings);
        }
        settings.inputGain = new float[settings.channelCount];
        for (int i = 0; i < settings.channelCount; i++) {
        }
        return settings;
    }

    public void setProperties(android.media.audiofx.DynamicsProcessing.Settings settings) {
        if (settings.channelCount != settings.inputGain.length || settings.channelCount != this.mChannelCount) {
            throw new java.lang.IllegalArgumentException("settings invalid channel count: " + settings.channelCount);
        }
        for (int i = 0; i < this.mChannelCount; i++) {
        }
    }
}
