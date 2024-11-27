package android.privacy.internal.longitudinalreporting;

/* loaded from: classes3.dex */
public class LongitudinalReportingConfig implements android.privacy.DifferentialPrivacyConfig {
    private static final java.lang.String ALGORITHM_NAME = "LongitudinalReporting";
    private final java.lang.String mEncoderId;
    private final android.privacy.internal.rappor.RapporConfig mIRRConfig;
    private final double mProbabilityF;
    private final double mProbabilityP;
    private final double mProbabilityQ;

    public LongitudinalReportingConfig(java.lang.String str, double d, double d2, double d3) {
        boolean z = false;
        com.android.internal.util.Preconditions.checkArgument(d >= 0.0d && d <= 1.0d, "probabilityF must be in range [0.0, 1.0]");
        this.mProbabilityF = d;
        com.android.internal.util.Preconditions.checkArgument(d2 >= 0.0d && d2 <= 1.0d, "probabilityP must be in range [0.0, 1.0]");
        this.mProbabilityP = d2;
        if (d3 >= 0.0d && d3 <= 1.0d) {
            z = true;
        }
        com.android.internal.util.Preconditions.checkArgument(z, "probabilityQ must be in range [0.0, 1.0]");
        this.mProbabilityQ = d3;
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str), "encoderId cannot be empty");
        this.mEncoderId = str;
        this.mIRRConfig = new android.privacy.internal.rappor.RapporConfig(str, 1, 0.0d, d, 1.0d - d, 1, 1);
    }

    @Override // android.privacy.DifferentialPrivacyConfig
    public java.lang.String getAlgorithm() {
        return ALGORITHM_NAME;
    }

    android.privacy.internal.rappor.RapporConfig getIRRConfig() {
        return this.mIRRConfig;
    }

    double getProbabilityP() {
        return this.mProbabilityP;
    }

    double getProbabilityQ() {
        return this.mProbabilityQ;
    }

    java.lang.String getEncoderId() {
        return this.mEncoderId;
    }

    public java.lang.String toString() {
        return java.lang.String.format("EncoderId: %s, ProbabilityF: %.3f, ProbabilityP: %.3f, ProbabilityQ: %.3f", this.mEncoderId, java.lang.Double.valueOf(this.mProbabilityF), java.lang.Double.valueOf(this.mProbabilityP), java.lang.Double.valueOf(this.mProbabilityQ));
    }
}
