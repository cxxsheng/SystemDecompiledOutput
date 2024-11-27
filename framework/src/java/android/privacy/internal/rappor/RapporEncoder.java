package android.privacy.internal.rappor;

/* loaded from: classes3.dex */
public class RapporEncoder implements android.privacy.DifferentialPrivacyEncoder {
    private static final byte[] INSECURE_SECRET = {-41, 104, -103, -109, -108, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 83, 84, -2, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -2, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -41, 104, -103, -109, -108, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 83, 84, -2, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -2, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -41, 104, -103, -109, -108, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 83, 84, -2, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -2, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84};
    private static final java.security.SecureRandom sSecureRandom = new java.security.SecureRandom();
    private final android.privacy.internal.rappor.RapporConfig mConfig;
    private final com.google.android.rappor.Encoder mEncoder;
    private final boolean mIsSecure;

    private RapporEncoder(android.privacy.internal.rappor.RapporConfig rapporConfig, boolean z, byte[] bArr) {
        java.util.Random random;
        byte[] bArr2;
        this.mConfig = rapporConfig;
        this.mIsSecure = z;
        if (z) {
            bArr2 = bArr;
            random = sSecureRandom;
        } else {
            random = new java.util.Random(getInsecureSeed(rapporConfig.mEncoderId));
            bArr2 = INSECURE_SECRET;
        }
        this.mEncoder = new com.google.android.rappor.Encoder(random, (java.security.MessageDigest) null, (java.security.MessageDigest) null, bArr2, rapporConfig.mEncoderId, rapporConfig.mNumBits, rapporConfig.mProbabilityF, rapporConfig.mProbabilityP, rapporConfig.mProbabilityQ, rapporConfig.mNumCohorts, rapporConfig.mNumBloomHashes);
    }

    private long getInsecureSeed(java.lang.String str) {
        try {
            return java.nio.ByteBuffer.wrap(java.security.MessageDigest.getInstance("SHA-256").digest(str.getBytes(java.nio.charset.StandardCharsets.UTF_8))).getLong();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.AssertionError("Unable generate insecure seed");
        }
    }

    public static android.privacy.internal.rappor.RapporEncoder createEncoder(android.privacy.internal.rappor.RapporConfig rapporConfig, byte[] bArr) {
        return new android.privacy.internal.rappor.RapporEncoder(rapporConfig, true, bArr);
    }

    public static android.privacy.internal.rappor.RapporEncoder createInsecureEncoderForTest(android.privacy.internal.rappor.RapporConfig rapporConfig) {
        return new android.privacy.internal.rappor.RapporEncoder(rapporConfig, false, null);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeString(java.lang.String str) {
        return this.mEncoder.encodeString(str);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBoolean(boolean z) {
        return this.mEncoder.encodeBoolean(z);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBits(byte[] bArr) {
        return this.mEncoder.encodeBits(bArr);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public android.privacy.internal.rappor.RapporConfig getConfig() {
        return this.mConfig;
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public boolean isInsecureEncoderForTest() {
        return !this.mIsSecure;
    }
}
