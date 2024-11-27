package android.util.apk;

/* loaded from: classes3.dex */
public class SignatureInfo {
    public final long apkSigningBlockOffset;
    public final long centralDirOffset;
    public final java.nio.ByteBuffer eocd;
    public final long eocdOffset;
    public final java.nio.ByteBuffer signatureBlock;

    SignatureInfo(java.nio.ByteBuffer byteBuffer, long j, long j2, long j3, java.nio.ByteBuffer byteBuffer2) {
        this.signatureBlock = byteBuffer;
        this.apkSigningBlockOffset = j;
        this.centralDirOffset = j2;
        this.eocdOffset = j3;
        this.eocd = byteBuffer2;
    }
}
