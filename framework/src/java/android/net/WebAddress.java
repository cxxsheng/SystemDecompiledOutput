package android.net;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class WebAddress {
    static final int MATCH_GROUP_AUTHORITY = 2;
    static final int MATCH_GROUP_HOST = 3;
    static final int MATCH_GROUP_PATH = 5;
    static final int MATCH_GROUP_PORT = 4;
    static final int MATCH_GROUP_SCHEME = 1;
    static java.util.regex.Pattern sAddressPattern = java.util.regex.Pattern.compile("(?:(http|https|file)\\:\\/\\/)?(?:([-A-Za-z0-9$_.+!*'(),;?&=]+(?:\\:[-A-Za-z0-9$_.+!*'(),;?&=]+)?)@)?([a-zA-Z0-9 -\ud7ff豈-﷏ﷰ-\uffef%_-][a-zA-Z0-9 -\ud7ff豈-﷏ﷰ-\uffef%_\\.-]*|\\[[0-9a-fA-F:\\.]+\\])?(?:\\:([0-9]*))?(\\/?[^#]*)?.*", 2);
    private java.lang.String mAuthInfo;
    private java.lang.String mHost;
    private java.lang.String mPath;
    private int mPort;
    private java.lang.String mScheme;

    public WebAddress(java.lang.String str) throws android.net.ParseException {
        if (str == null) {
            throw new java.lang.NullPointerException();
        }
        this.mScheme = "";
        this.mHost = "";
        this.mPort = -1;
        this.mPath = "/";
        this.mAuthInfo = "";
        java.util.regex.Matcher matcher = sAddressPattern.matcher(str);
        if (matcher.matches()) {
            java.lang.String group = matcher.group(1);
            if (group != null) {
                this.mScheme = group.toLowerCase(java.util.Locale.ROOT);
            }
            java.lang.String group2 = matcher.group(2);
            if (group2 != null) {
                this.mAuthInfo = group2;
            }
            java.lang.String group3 = matcher.group(3);
            if (group3 != null) {
                this.mHost = group3;
            }
            java.lang.String group4 = matcher.group(4);
            if (group4 != null && group4.length() > 0) {
                try {
                    this.mPort = java.lang.Integer.parseInt(group4);
                } catch (java.lang.NumberFormatException e) {
                    throw new android.net.ParseException("Bad port");
                }
            }
            java.lang.String group5 = matcher.group(5);
            if (group5 != null && group5.length() > 0) {
                if (group5.charAt(0) != '/') {
                    this.mPath = "/" + group5;
                } else {
                    this.mPath = group5;
                }
            }
            if (this.mPort != 443 || !this.mScheme.equals("")) {
                if (this.mPort == -1) {
                    if (this.mScheme.equals(android.content.IntentFilter.SCHEME_HTTPS)) {
                        this.mPort = 443;
                    } else {
                        this.mPort = 80;
                    }
                }
            } else {
                this.mScheme = android.content.IntentFilter.SCHEME_HTTPS;
            }
            if (this.mScheme.equals("")) {
                this.mScheme = android.content.IntentFilter.SCHEME_HTTP;
                return;
            }
            return;
        }
        throw new android.net.ParseException("Bad address");
    }

    public java.lang.String toString() {
        java.lang.String str;
        if ((this.mPort != 443 && this.mScheme.equals(android.content.IntentFilter.SCHEME_HTTPS)) || (this.mPort != 80 && this.mScheme.equals(android.content.IntentFilter.SCHEME_HTTP))) {
            str = ":" + java.lang.Integer.toString(this.mPort);
        } else {
            str = "";
        }
        return this.mScheme + "://" + (this.mAuthInfo.length() > 0 ? this.mAuthInfo + "@" : "") + this.mHost + str + this.mPath;
    }

    public void setScheme(java.lang.String str) {
        this.mScheme = str;
    }

    public java.lang.String getScheme() {
        return this.mScheme;
    }

    public void setHost(java.lang.String str) {
        this.mHost = str;
    }

    public java.lang.String getHost() {
        return this.mHost;
    }

    public void setPort(int i) {
        this.mPort = i;
    }

    public int getPort() {
        return this.mPort;
    }

    public void setPath(java.lang.String str) {
        this.mPath = str;
    }

    public java.lang.String getPath() {
        return this.mPath;
    }

    public void setAuthInfo(java.lang.String str) {
        this.mAuthInfo = str;
    }

    public java.lang.String getAuthInfo() {
        return this.mAuthInfo;
    }
}
