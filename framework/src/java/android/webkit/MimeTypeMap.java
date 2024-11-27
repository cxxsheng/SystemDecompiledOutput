package android.webkit;

/* loaded from: classes4.dex */
public class MimeTypeMap {
    private static final android.webkit.MimeTypeMap sMimeTypeMap = new android.webkit.MimeTypeMap();

    private MimeTypeMap() {
    }

    public static java.lang.String getFileExtensionFromUrl(java.lang.String str) {
        int lastIndexOf;
        if (!android.text.TextUtils.isEmpty(str)) {
            int lastIndexOf2 = str.lastIndexOf(35);
            if (lastIndexOf2 > 0) {
                str = str.substring(0, lastIndexOf2);
            }
            int lastIndexOf3 = str.lastIndexOf(63);
            if (lastIndexOf3 > 0) {
                str = str.substring(0, lastIndexOf3);
            }
            int lastIndexOf4 = str.lastIndexOf(47);
            if (lastIndexOf4 >= 0) {
                str = str.substring(lastIndexOf4 + 1);
            }
            if (!str.isEmpty() && java.util.regex.Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", str) && (lastIndexOf = str.lastIndexOf(46)) >= 0) {
                return str.substring(lastIndexOf + 1);
            }
            return "";
        }
        return "";
    }

    public boolean hasMimeType(java.lang.String str) {
        return libcore.content.type.MimeMap.getDefault().hasMimeType(str);
    }

    public java.lang.String getMimeTypeFromExtension(java.lang.String str) {
        return libcore.content.type.MimeMap.getDefault().guessMimeTypeFromExtension(str);
    }

    private static java.lang.String mimeTypeFromExtension(java.lang.String str) {
        return libcore.content.type.MimeMap.getDefault().guessMimeTypeFromExtension(str);
    }

    public boolean hasExtension(java.lang.String str) {
        return libcore.content.type.MimeMap.getDefault().hasExtension(str);
    }

    public java.lang.String getExtensionFromMimeType(java.lang.String str) {
        return libcore.content.type.MimeMap.getDefault().guessExtensionFromMimeType(str);
    }

    java.lang.String remapGenericMimeType(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.lang.String str4;
        if ("text/plain".equals(str) || "application/octet-stream".equals(str)) {
            if (str3 == null) {
                str4 = null;
            } else {
                str4 = android.webkit.URLUtil.parseContentDisposition(str3);
            }
            if (str4 != null) {
                str2 = str4;
            }
            java.lang.String mimeTypeFromExtension = getMimeTypeFromExtension(getFileExtensionFromUrl(str2));
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
            return str;
        }
        if ("text/vnd.wap.wml".equals(str)) {
            return "text/plain";
        }
        if (com.google.android.mms.ContentType.APP_WAP_XHTML.equals(str)) {
            return com.google.android.mms.ContentType.APP_XHTML;
        }
        return str;
    }

    public static android.webkit.MimeTypeMap getSingleton() {
        return sMimeTypeMap;
    }
}
