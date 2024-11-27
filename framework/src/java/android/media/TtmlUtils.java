package android.media;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
final class TtmlUtils {
    public static final java.lang.String ATTR_BEGIN = "begin";
    public static final java.lang.String ATTR_DURATION = "dur";
    public static final java.lang.String ATTR_END = "end";
    public static final long INVALID_TIMESTAMP = Long.MAX_VALUE;
    public static final java.lang.String PCDATA = "#pcdata";
    public static final java.lang.String TAG_BODY = "body";
    public static final java.lang.String TAG_BR = "br";
    public static final java.lang.String TAG_DIV = "div";
    public static final java.lang.String TAG_HEAD = "head";
    public static final java.lang.String TAG_LAYOUT = "layout";
    public static final java.lang.String TAG_METADATA = "metadata";
    public static final java.lang.String TAG_P = "p";
    public static final java.lang.String TAG_REGION = "region";
    public static final java.lang.String TAG_SMPTE_DATA = "smpte:data";
    public static final java.lang.String TAG_SMPTE_IMAGE = "smpte:image";
    public static final java.lang.String TAG_SMPTE_INFORMATION = "smpte:information";
    public static final java.lang.String TAG_SPAN = "span";
    public static final java.lang.String TAG_STYLE = "style";
    public static final java.lang.String TAG_STYLING = "styling";
    public static final java.lang.String TAG_TT = "tt";
    private static final java.util.regex.Pattern CLOCK_TIME = java.util.regex.Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final java.util.regex.Pattern OFFSET_TIME = java.util.regex.Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");

    private TtmlUtils() {
    }

    public static long parseTimeExpression(java.lang.String str, int i, int i2, int i3) throws java.lang.NumberFormatException {
        java.util.regex.Matcher matcher = CLOCK_TIME.matcher(str);
        if (matcher.matches()) {
            double parseLong = (java.lang.Long.parseLong(matcher.group(1)) * 3600) + (java.lang.Long.parseLong(matcher.group(2)) * 60) + java.lang.Long.parseLong(matcher.group(3));
            java.lang.String group = matcher.group(4);
            double d = 0.0d;
            double parseDouble = parseLong + (group != null ? java.lang.Double.parseDouble(group) : 0.0d) + (matcher.group(5) != null ? java.lang.Long.parseLong(r0) / i : 0.0d);
            if (matcher.group(6) != null) {
                d = (java.lang.Long.parseLong(r0) / i2) / i;
            }
            return (long) ((parseDouble + d) * 1000.0d);
        }
        java.util.regex.Matcher matcher2 = OFFSET_TIME.matcher(str);
        if (matcher2.matches()) {
            double parseDouble2 = java.lang.Double.parseDouble(matcher2.group(1));
            java.lang.String group2 = matcher2.group(2);
            if (group2.equals("h")) {
                parseDouble2 *= 3.6E9d;
            } else if (group2.equals("m")) {
                parseDouble2 *= 6.0E7d;
            } else if (group2.equals(android.app.blob.XmlTags.TAG_SESSION)) {
                parseDouble2 *= 1000000.0d;
            } else if (group2.equals("ms")) {
                parseDouble2 *= 1000.0d;
            } else if (group2.equals(android.app.backup.FullBackup.FILES_TREE_TOKEN)) {
                parseDouble2 = (parseDouble2 / i) * 1000000.0d;
            } else if (group2.equals("t")) {
                parseDouble2 = (parseDouble2 / i3) * 1000000.0d;
            }
            return (long) parseDouble2;
        }
        throw new java.lang.NumberFormatException("Malformed time expression : " + str);
    }

    public static java.lang.String applyDefaultSpacePolicy(java.lang.String str) {
        return applySpacePolicy(str, true);
    }

    public static java.lang.String applySpacePolicy(java.lang.String str, boolean z) {
        java.lang.String replaceAll = str.replaceAll("\r\n", "\n").replaceAll(" *\n *", "\n");
        if (z) {
            replaceAll = replaceAll.replaceAll("\n", " ");
        }
        return replaceAll.replaceAll("[ \t\\x0B\f\r]+", " ");
    }

    public static java.lang.String extractText(android.media.TtmlNode ttmlNode, long j, long j2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        extractText(ttmlNode, j, j2, sb, false);
        return sb.toString().replaceAll("\n$", "");
    }

    private static void extractText(android.media.TtmlNode ttmlNode, long j, long j2, java.lang.StringBuilder sb, boolean z) {
        if (ttmlNode.mName.equals(PCDATA) && z) {
            sb.append(ttmlNode.mText);
            return;
        }
        if (ttmlNode.mName.equals(TAG_BR) && z) {
            sb.append("\n");
            return;
        }
        if (!ttmlNode.mName.equals(TAG_METADATA) && ttmlNode.isActive(j, j2)) {
            boolean equals = ttmlNode.mName.equals("p");
            int length = sb.length();
            for (int i = 0; i < ttmlNode.mChildren.size(); i++) {
                extractText(ttmlNode.mChildren.get(i), j, j2, sb, equals || z);
            }
            if (equals && length != sb.length()) {
                sb.append("\n");
            }
        }
    }

    public static java.lang.String extractTtmlFragment(android.media.TtmlNode ttmlNode, long j, long j2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        extractTtmlFragment(ttmlNode, j, j2, sb);
        return sb.toString();
    }

    private static void extractTtmlFragment(android.media.TtmlNode ttmlNode, long j, long j2, java.lang.StringBuilder sb) {
        if (ttmlNode.mName.equals(PCDATA)) {
            sb.append(ttmlNode.mText);
            return;
        }
        if (ttmlNode.mName.equals(TAG_BR)) {
            sb.append("<br/>");
            return;
        }
        if (ttmlNode.isActive(j, j2)) {
            sb.append("<");
            sb.append(ttmlNode.mName);
            sb.append(ttmlNode.mAttributes);
            sb.append(">");
            for (int i = 0; i < ttmlNode.mChildren.size(); i++) {
                extractTtmlFragment(ttmlNode.mChildren.get(i), j, j2, sb);
            }
            sb.append("</");
            sb.append(ttmlNode.mName);
            sb.append(">");
        }
    }
}
