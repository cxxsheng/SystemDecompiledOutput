package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class SelectionSessionLogger {
    private static final java.lang.String CLASSIFIER_ID = "androidtc";

    static boolean isPlatformLocalTextClassifierSmartSelection(java.lang.String str) {
        return "androidtc".equals(android.view.textclassifier.SelectionSessionLogger.SignatureParser.getClassifierId(str));
    }

    public static final class SignatureParser {
        static java.lang.String getClassifierId(java.lang.String str) {
            int indexOf;
            if (str == null || (indexOf = str.indexOf(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)) < 0) {
                return "";
            }
            return str.substring(0, indexOf);
        }
    }
}
