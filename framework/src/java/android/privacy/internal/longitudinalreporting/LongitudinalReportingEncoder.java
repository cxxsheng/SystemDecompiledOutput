package android.privacy.internal.longitudinalreporting;

/* loaded from: classes3.dex */
public class LongitudinalReportingEncoder implements android.privacy.DifferentialPrivacyEncoder {
    private static final boolean DEBUG = false;
    private static final java.lang.String PRR1_ENCODER_ID = "prr1_encoder_id";
    private static final java.lang.String PRR2_ENCODER_ID = "prr2_encoder_id";
    private static final java.lang.String TAG = "LongitudinalEncoder";
    private final android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig mConfig;
    private final java.lang.Boolean mFakeValue;
    private final android.privacy.internal.rappor.RapporEncoder mIRREncoder;
    private final boolean mIsSecure;

    public static android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder createEncoder(android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig longitudinalReportingConfig, byte[] bArr) {
        return new android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder(longitudinalReportingConfig, true, bArr);
    }

    public static android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder createInsecureEncoderForTest(android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig longitudinalReportingConfig) {
        return new android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder(longitudinalReportingConfig, false, null);
    }

    private LongitudinalReportingEncoder(android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig longitudinalReportingConfig, boolean z, byte[] bArr) {
        android.privacy.internal.rappor.RapporEncoder createInsecureEncoderForTest;
        this.mConfig = longitudinalReportingConfig;
        this.mIsSecure = z;
        if (getLongTermRandomizedResult(longitudinalReportingConfig.getProbabilityP(), z, bArr, longitudinalReportingConfig.getEncoderId() + PRR1_ENCODER_ID)) {
            this.mFakeValue = java.lang.Boolean.valueOf(getLongTermRandomizedResult(longitudinalReportingConfig.getProbabilityQ(), z, bArr, longitudinalReportingConfig.getEncoderId() + PRR2_ENCODER_ID));
        } else {
            this.mFakeValue = null;
        }
        android.privacy.internal.rappor.RapporConfig iRRConfig = longitudinalReportingConfig.getIRRConfig();
        if (z) {
            createInsecureEncoderForTest = android.privacy.internal.rappor.RapporEncoder.createEncoder(iRRConfig, bArr);
        } else {
            createInsecureEncoderForTest = android.privacy.internal.rappor.RapporEncoder.createInsecureEncoderForTest(iRRConfig);
        }
        this.mIRREncoder = createInsecureEncoderForTest;
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeString(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBoolean(boolean z) {
        if (this.mFakeValue != null) {
            z = this.mFakeValue.booleanValue();
        }
        return this.mIRREncoder.encodeBoolean(z);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBits(byte[] bArr) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig getConfig() {
        return this.mConfig;
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public boolean isInsecureEncoderForTest() {
        return !this.mIsSecure;
    }

    public static boolean getLongTermRandomizedResult(double d, boolean z, byte[] bArr, java.lang.String str) {
        android.privacy.internal.rappor.RapporEncoder createInsecureEncoderForTest;
        double d2 = d < 0.5d ? 2.0d * d : 2.0d * (1.0d - d);
        boolean z2 = d >= 0.5d;
        android.privacy.internal.rappor.RapporConfig rapporConfig = new android.privacy.internal.rappor.RapporConfig(str, 1, d2, 0.0d, 1.0d, 1, 1);
        if (z) {
            createInsecureEncoderForTest = android.privacy.internal.rappor.RapporEncoder.createEncoder(rapporConfig, bArr);
        } else {
            createInsecureEncoderForTest = android.privacy.internal.rappor.RapporEncoder.createInsecureEncoderForTest(rapporConfig);
        }
        return createInsecureEncoderForTest.encodeBoolean(z2)[0] > 0;
    }
}
