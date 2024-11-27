package android.content;

/* loaded from: classes.dex */
public class UriMatcher {
    private static final int EXACT = 0;
    public static final int NO_MATCH = -1;
    private static final int NUMBER = 1;
    private static final int TEXT = 2;
    private java.util.ArrayList<android.content.UriMatcher> mChildren;
    private int mCode;
    private final java.lang.String mText;
    private final int mWhich;

    public UriMatcher(int i) {
        this.mCode = i;
        this.mWhich = -1;
        this.mChildren = new java.util.ArrayList<>();
        this.mText = null;
    }

    private UriMatcher(int i, java.lang.String str) {
        this.mCode = -1;
        this.mWhich = i;
        this.mChildren = new java.util.ArrayList<>();
        this.mText = str;
    }

    public void addURI(java.lang.String str, java.lang.String str2, int i) {
        java.lang.String[] strArr;
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("code " + i + " is invalid: it must be positive");
        }
        if (str2 == null) {
            strArr = null;
        } else {
            if (str2.length() > 1 && str2.charAt(0) == '/') {
                str2 = str2.substring(1);
            }
            strArr = str2.split("/");
        }
        int length = strArr != null ? strArr.length : 0;
        int i2 = -1;
        android.content.UriMatcher uriMatcher = this;
        while (i2 < length) {
            java.lang.String str3 = i2 < 0 ? str : strArr[i2];
            java.util.ArrayList<android.content.UriMatcher> arrayList = uriMatcher.mChildren;
            int size = arrayList.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                android.content.UriMatcher uriMatcher2 = arrayList.get(i3);
                if (!str3.equals(uriMatcher2.mText)) {
                    i3++;
                } else {
                    uriMatcher = uriMatcher2;
                    break;
                }
            }
            if (i3 == size) {
                android.content.UriMatcher createChild = createChild(str3);
                uriMatcher.mChildren.add(createChild);
                uriMatcher = createChild;
            }
            i2++;
        }
        uriMatcher.mCode = i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static android.content.UriMatcher createChild(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case 35:
                if (str.equals("#")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 42:
                if (str.equals("*")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new android.content.UriMatcher(1, "#");
            case 1:
                return new android.content.UriMatcher(2, "*");
            default:
                return new android.content.UriMatcher(0, str);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0062, code lost:
    
        if (r10.mText.equals(r5) != false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int match(android.net.Uri uri) {
        int i;
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        int size = pathSegments.size();
        if (size != 0 || uri.getAuthority() != null) {
            android.content.UriMatcher uriMatcher = this;
            int i2 = -1;
            while (i2 < size) {
                java.lang.String authority = i2 < 0 ? uri.getAuthority() : pathSegments.get(i2);
                java.util.ArrayList<android.content.UriMatcher> arrayList = uriMatcher.mChildren;
                if (arrayList != null) {
                    int size2 = arrayList.size();
                    android.content.UriMatcher uriMatcher2 = null;
                    int i3 = 0;
                    while (true) {
                        if (i3 < size2) {
                            android.content.UriMatcher uriMatcher3 = arrayList.get(i3);
                            switch (uriMatcher3.mWhich) {
                                case 1:
                                    int length = authority.length();
                                    for (0; i < length; i + 1) {
                                        char charAt = authority.charAt(i);
                                        i = (charAt >= '0' && charAt <= '9') ? i + 1 : 0;
                                    }
                                    uriMatcher2 = uriMatcher3;
                                    break;
                                case 2:
                                    uriMatcher2 = uriMatcher3;
                                    break;
                            }
                            if (uriMatcher2 == null) {
                                i3++;
                            } else {
                                uriMatcher = uriMatcher2;
                            }
                        } else {
                            uriMatcher = uriMatcher2;
                        }
                    }
                    if (uriMatcher == null) {
                        return -1;
                    }
                    i2++;
                } else {
                    return uriMatcher.mCode;
                }
            }
            return uriMatcher.mCode;
        }
        return this.mCode;
    }
}
