package android.net;

/* loaded from: classes2.dex */
public class MailTo {
    private static final java.lang.String BODY = "body";
    private static final java.lang.String CC = "cc";
    public static final java.lang.String MAILTO_SCHEME = "mailto:";
    private static final java.lang.String SUBJECT = "subject";
    private static final java.lang.String TO = "to";
    private java.util.HashMap<java.lang.String, java.lang.String> mHeaders = new java.util.HashMap<>();

    public static boolean isMailTo(java.lang.String str) {
        if (str != null && str.startsWith("mailto:")) {
            return true;
        }
        return false;
    }

    public static android.net.MailTo parse(java.lang.String str) throws android.net.ParseException {
        if (str == null) {
            throw new java.lang.NullPointerException();
        }
        if (!isMailTo(str)) {
            throw new android.net.ParseException("Not a mailto scheme");
        }
        android.net.Uri parse = android.net.Uri.parse(str.substring("mailto:".length()));
        android.net.MailTo mailTo = new android.net.MailTo();
        java.lang.String query = parse.getQuery();
        if (query != null) {
            for (java.lang.String str2 : query.split("&")) {
                java.lang.String[] split = str2.split("=");
                if (split.length != 0) {
                    mailTo.mHeaders.put(android.net.Uri.decode(split[0]).toLowerCase(java.util.Locale.ROOT), split.length > 1 ? android.net.Uri.decode(split[1]) : null);
                }
            }
        }
        java.lang.String path = parse.getPath();
        if (path != null) {
            java.lang.String to = mailTo.getTo();
            if (to != null) {
                path = path + ", " + to;
            }
            mailTo.mHeaders.put(TO, path);
        }
        return mailTo;
    }

    public java.lang.String getTo() {
        return this.mHeaders.get(TO);
    }

    public java.lang.String getCc() {
        return this.mHeaders.get(CC);
    }

    public java.lang.String getSubject() {
        return this.mHeaders.get("subject");
    }

    public java.lang.String getBody() {
        return this.mHeaders.get("body");
    }

    public java.util.Map<java.lang.String, java.lang.String> getHeaders() {
        return this.mHeaders;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("mailto:");
        sb.append('?');
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : this.mHeaders.entrySet()) {
            sb.append(android.net.Uri.encode(entry.getKey()));
            sb.append('=');
            sb.append(android.net.Uri.encode(entry.getValue()));
            sb.append('&');
        }
        return sb.toString();
    }

    private MailTo() {
    }
}
