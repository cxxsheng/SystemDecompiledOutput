package android.media;

/* loaded from: classes2.dex */
public class MediaCasStateException extends java.lang.IllegalStateException {
    private final java.lang.String mDiagnosticInfo;
    private final int mErrorCode;

    private MediaCasStateException(int i, java.lang.String str, java.lang.String str2) {
        super(str);
        this.mErrorCode = i;
        this.mDiagnosticInfo = str2;
    }

    static void throwExceptionIfNeeded(int i) {
        throwExceptionIfNeeded(i, null);
    }

    static void throwExceptionIfNeeded(int i, java.lang.String str) {
        java.lang.String str2;
        if (i == 0) {
            return;
        }
        if (i == 6) {
            throw new java.lang.IllegalArgumentException();
        }
        switch (i) {
            case 1:
                str2 = "No license";
                break;
            case 2:
                str2 = "License expired";
                break;
            case 3:
                str2 = "Session not opened";
                break;
            case 4:
                str2 = "Unsupported scheme or data format";
                break;
            case 5:
                str2 = "Invalid CAS state";
                break;
            case 6:
            case 7:
            case 8:
            case 11:
            default:
                str2 = "Unknown CAS state exception";
                break;
            case 9:
                str2 = "Insufficient output protection";
                break;
            case 10:
                str2 = "Tamper detected";
                break;
            case 12:
                str2 = "Not initialized";
                break;
            case 13:
                str2 = "Decrypt error";
                break;
            case 14:
                str2 = "General CAS error";
                break;
            case 15:
                str2 = "Need Activation";
                break;
            case 16:
                str2 = "Need Pairing";
                break;
            case 17:
                str2 = "No Card";
                break;
            case 18:
                str2 = "Card Muted";
                break;
            case 19:
                str2 = "Card Invalid";
                break;
            case 20:
                str2 = "Blackout";
                break;
            case 21:
                str2 = "Rebooting";
                break;
        }
        throw new android.media.MediaCasStateException(i, str, java.lang.String.format("%s (err=%d)", str2, java.lang.Integer.valueOf(i)));
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public java.lang.String getDiagnosticInfo() {
        return this.mDiagnosticInfo;
    }
}
