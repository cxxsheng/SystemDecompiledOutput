package android.privacy;

/* loaded from: classes3.dex */
public interface DifferentialPrivacyEncoder {
    byte[] encodeBits(byte[] bArr);

    byte[] encodeBoolean(boolean z);

    byte[] encodeString(java.lang.String str);

    android.privacy.DifferentialPrivacyConfig getConfig();

    boolean isInsecureEncoderForTest();
}
