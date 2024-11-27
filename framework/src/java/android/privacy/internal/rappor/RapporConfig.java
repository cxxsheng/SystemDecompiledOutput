package android.privacy.internal.rappor;

/* loaded from: classes3.dex */
public class RapporConfig implements android.privacy.DifferentialPrivacyConfig {
    private static final java.lang.String ALGORITHM_NAME = "Rappor";
    final java.lang.String mEncoderId;
    final int mNumBits;
    final int mNumBloomHashes;
    final int mNumCohorts;
    final double mProbabilityF;
    final double mProbabilityP;
    final double mProbabilityQ;

    public RapporConfig(java.lang.String str, int i, double d, double d2, double d3, int i2, int i3) {
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str), "encoderId cannot be empty");
        this.mEncoderId = str;
        com.android.internal.util.Preconditions.checkArgument(i > 0, "numBits needs to be > 0");
        this.mNumBits = i;
        com.android.internal.util.Preconditions.checkArgument(d >= 0.0d && d <= 1.0d, "probabilityF must be in range [0.0, 1.0]");
        this.mProbabilityF = d;
        com.android.internal.util.Preconditions.checkArgument(d2 >= 0.0d && d2 <= 1.0d, "probabilityP must be in range [0.0, 1.0]");
        this.mProbabilityP = d2;
        com.android.internal.util.Preconditions.checkArgument(d3 >= 0.0d && d3 <= 1.0d, "probabilityQ must be in range [0.0, 1.0]");
        this.mProbabilityQ = d3;
        com.android.internal.util.Preconditions.checkArgument(i2 > 0, "numCohorts needs to be > 0");
        this.mNumCohorts = i2;
        com.android.internal.util.Preconditions.checkArgument(i3 > 0, "numBloomHashes needs to be > 0");
        this.mNumBloomHashes = i3;
    }

    @Override // android.privacy.DifferentialPrivacyConfig
    public java.lang.String getAlgorithm() {
        return ALGORITHM_NAME;
    }

    public java.lang.String toString() {
        return java.lang.String.format("EncoderId: %s, NumBits: %d, ProbabilityF: %.3f, ProbabilityP: %.3f, ProbabilityQ: %.3f, NumCohorts: %d, NumBloomHashes: %d", this.mEncoderId, java.lang.Integer.valueOf(this.mNumBits), java.lang.Double.valueOf(this.mProbabilityF), java.lang.Double.valueOf(this.mProbabilityP), java.lang.Double.valueOf(this.mProbabilityQ), java.lang.Integer.valueOf(this.mNumCohorts), java.lang.Integer.valueOf(this.mNumBloomHashes));
    }
}
