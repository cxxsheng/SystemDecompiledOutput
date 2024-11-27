package android.webkit;

/* loaded from: classes4.dex */
class LegacyErrorStrings {
    private static final java.lang.String LOGTAG = "Http";

    private LegacyErrorStrings() {
    }

    static java.lang.String getString(int i, android.content.Context context) {
        return context.getText(getResource(i)).toString();
    }

    private static int getResource(int i) {
        switch (i) {
            case -15:
                break;
            case -14:
                break;
            case -13:
                break;
            case -12:
                break;
            case -11:
                break;
            case -10:
                break;
            case -9:
                break;
            case -8:
                break;
            case -7:
                break;
            case -6:
                break;
            case -5:
                break;
            case -4:
                break;
            case -3:
                break;
            case -2:
                break;
            case -1:
                break;
            case 0:
                break;
            default:
                android.util.Log.w(LOGTAG, "Using generic message for unknown error code: " + i);
                break;
        }
        return com.android.internal.R.string.httpError;
    }
}
