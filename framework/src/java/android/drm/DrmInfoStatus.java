package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmInfoStatus {
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_OK = 1;
    public final android.drm.ProcessedData data;
    public final int infoType;
    public final java.lang.String mimeType;
    public final int statusCode;

    public DrmInfoStatus(int i, int i2, android.drm.ProcessedData processedData, java.lang.String str) {
        if (!android.drm.DrmInfoRequest.isValidType(i2)) {
            throw new java.lang.IllegalArgumentException("infoType: " + i2);
        }
        if (!isValidStatusCode(i)) {
            throw new java.lang.IllegalArgumentException("Unsupported status code: " + i);
        }
        if (str == null || str == "") {
            throw new java.lang.IllegalArgumentException("mimeType is null or an empty string");
        }
        this.statusCode = i;
        this.infoType = i2;
        this.data = processedData;
        this.mimeType = str;
    }

    private boolean isValidStatusCode(int i) {
        return i == 1 || i == 2;
    }
}
