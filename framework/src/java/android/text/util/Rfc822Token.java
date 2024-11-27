package android.text.util;

/* loaded from: classes3.dex */
public class Rfc822Token {
    private java.lang.String mAddress;
    private java.lang.String mComment;
    private java.lang.String mName;

    public Rfc822Token(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mName = str;
        this.mAddress = str2;
        this.mComment = str3;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getAddress() {
        return this.mAddress;
    }

    public java.lang.String getComment() {
        return this.mComment;
    }

    public void setName(java.lang.String str) {
        this.mName = str;
    }

    public void setAddress(java.lang.String str) {
        this.mAddress = str;
    }

    public void setComment(java.lang.String str) {
        this.mComment = str;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.mName != null && this.mName.length() != 0) {
            sb.append(quoteNameIfNecessary(this.mName));
            sb.append(' ');
        }
        if (this.mComment != null && this.mComment.length() != 0) {
            sb.append('(');
            sb.append(quoteComment(this.mComment));
            sb.append(") ");
        }
        if (this.mAddress != null && this.mAddress.length() != 0) {
            sb.append('<');
            sb.append(this.mAddress);
            sb.append('>');
        }
        return sb.toString();
    }

    public static java.lang.String quoteNameIfNecessary(java.lang.String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt < 'A' || charAt > 'Z') && ((charAt < 'a' || charAt > 'z') && charAt != ' ' && (charAt < '0' || charAt > '9'))) {
                return '\"' + quoteName(str) + '\"';
            }
        }
        return str;
    }

    public static java.lang.String quoteName(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '\\' || charAt == '\"') {
                sb.append('\\');
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    public static java.lang.String quoteComment(java.lang.String str) {
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '(' || charAt == ')' || charAt == '\\') {
                sb.append('\\');
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    public int hashCode() {
        int hashCode = this.mName != null ? 527 + this.mName.hashCode() : 17;
        if (this.mAddress != null) {
            hashCode = (hashCode * 31) + this.mAddress.hashCode();
        }
        return this.mComment != null ? (hashCode * 31) + this.mComment.hashCode() : hashCode;
    }

    private static boolean stringEquals(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.text.util.Rfc822Token)) {
            return false;
        }
        android.text.util.Rfc822Token rfc822Token = (android.text.util.Rfc822Token) obj;
        return stringEquals(this.mName, rfc822Token.mName) && stringEquals(this.mAddress, rfc822Token.mAddress) && stringEquals(this.mComment, rfc822Token.mComment);
    }
}
