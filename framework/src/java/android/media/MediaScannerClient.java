package android.media;

/* loaded from: classes2.dex */
public interface MediaScannerClient {
    void handleStringTag(java.lang.String str, java.lang.String str2);

    void scanFile(java.lang.String str, long j, long j2, boolean z, boolean z2);

    void setMimeType(java.lang.String str);
}
