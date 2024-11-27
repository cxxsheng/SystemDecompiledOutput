package android.net;

/* loaded from: classes2.dex */
public abstract class Uri implements android.os.Parcelable, java.lang.Comparable<android.net.Uri> {
    private static final java.lang.String DEFAULT_ENCODING = "UTF-8";
    private static final int NOT_CALCULATED = -2;
    private static final int NOT_FOUND = -1;
    private static final java.lang.String NOT_HIERARCHICAL = "This isn't a hierarchical URI.";
    private static final int NULL_TYPE_ID = 0;
    private static final java.lang.String LOG = android.net.Uri.class.getSimpleName();
    public static final android.net.Uri EMPTY = new android.net.Uri.HierarchicalUri(null, android.net.Uri.Part.NULL, android.net.Uri.PathPart.EMPTY, android.net.Uri.Part.NULL, android.net.Uri.Part.NULL);
    public static final android.os.Parcelable.Creator<android.net.Uri> CREATOR = new android.os.Parcelable.Creator<android.net.Uri>() { // from class: android.net.Uri.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.Uri createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            switch (readInt) {
                case 0:
                    return null;
                case 1:
                    return android.net.Uri.StringUri.readFrom(parcel);
                case 2:
                    return android.net.Uri.OpaqueUri.readFrom(parcel);
                case 3:
                    return android.net.Uri.HierarchicalUri.readFrom(parcel);
                default:
                    throw new java.lang.IllegalArgumentException("Unknown URI type: " + readInt);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.Uri[] newArray(int i) {
            return new android.net.Uri[i];
        }
    };
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    public abstract android.net.Uri.Builder buildUpon();

    public abstract java.lang.String getAuthority();

    public abstract java.lang.String getEncodedAuthority();

    public abstract java.lang.String getEncodedFragment();

    public abstract java.lang.String getEncodedPath();

    public abstract java.lang.String getEncodedQuery();

    public abstract java.lang.String getEncodedSchemeSpecificPart();

    public abstract java.lang.String getEncodedUserInfo();

    public abstract java.lang.String getFragment();

    public abstract java.lang.String getHost();

    public abstract java.lang.String getLastPathSegment();

    public abstract java.lang.String getPath();

    public abstract java.util.List<java.lang.String> getPathSegments();

    public abstract int getPort();

    public abstract java.lang.String getQuery();

    public abstract java.lang.String getScheme();

    public abstract java.lang.String getSchemeSpecificPart();

    public abstract java.lang.String getUserInfo();

    public abstract boolean isHierarchical();

    public abstract boolean isRelative();

    public abstract java.lang.String toString();

    static class NotCachedHolder {
        static final java.lang.String NOT_CACHED = new java.lang.String("NOT CACHED");

        private NotCachedHolder() {
        }
    }

    private Uri() {
    }

    public boolean isOpaque() {
        return !isHierarchical();
    }

    public boolean isAbsolute() {
        return !isRelative();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.Uri)) {
            return false;
        }
        return toString().equals(((android.net.Uri) obj).toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(android.net.Uri uri) {
        return toString().compareTo(uri.toString());
    }

    @android.annotation.SystemApi
    public java.lang.String toSafeString() {
        java.lang.String scheme = getScheme();
        java.lang.String schemeSpecificPart = getSchemeSpecificPart();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        if (scheme != null) {
            sb.append(scheme);
            sb.append(":");
            if (scheme.equalsIgnoreCase(android.telecom.PhoneAccount.SCHEME_TEL) || scheme.equalsIgnoreCase("sip") || scheme.equalsIgnoreCase(android.content.Context.SMS_SERVICE) || scheme.equalsIgnoreCase("smsto") || scheme.equalsIgnoreCase("mailto") || scheme.equalsIgnoreCase("nfc")) {
                if (schemeSpecificPart != null) {
                    for (int i = 0; i < schemeSpecificPart.length(); i++) {
                        char charAt = schemeSpecificPart.charAt(i);
                        if (charAt == '-' || charAt == '@' || charAt == '.') {
                            sb.append(charAt);
                        } else {
                            sb.append(com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X);
                        }
                    }
                }
            } else {
                java.lang.String host = getHost();
                int port = getPort();
                java.lang.String path = getPath();
                java.lang.String authority = getAuthority();
                if (authority != null) {
                    sb.append("//");
                }
                if (host != null) {
                    sb.append(host);
                }
                if (port != -1) {
                    sb.append(":").append(port);
                }
                if (authority != null || path != null) {
                    sb.append("/...");
                }
            }
        }
        return sb.toString();
    }

    public static android.net.Uri parse(java.lang.String str) {
        return new android.net.Uri.StringUri(str);
    }

    public static android.net.Uri fromFile(java.io.File file) {
        if (file == null) {
            throw new java.lang.NullPointerException("file");
        }
        return new android.net.Uri.HierarchicalUri("file", android.net.Uri.Part.EMPTY, android.net.Uri.PathPart.fromDecoded(file.getAbsolutePath()), android.net.Uri.Part.NULL, android.net.Uri.Part.NULL);
    }

    private static class StringUri extends android.net.Uri.AbstractHierarchicalUri {
        static final int TYPE_ID = 1;
        private android.net.Uri.Part authority;
        private volatile int cachedFsi;
        private volatile int cachedSsi;
        private android.net.Uri.Part fragment;
        private android.net.Uri.PathPart path;
        private android.net.Uri.Part query;
        private volatile java.lang.String scheme;
        private android.net.Uri.Part ssp;
        private final java.lang.String uriString;

        private StringUri(java.lang.String str) {
            super();
            this.cachedSsi = -2;
            this.cachedFsi = -2;
            this.scheme = android.net.Uri.NotCachedHolder.NOT_CACHED;
            if (str == null) {
                throw new java.lang.NullPointerException("uriString");
            }
            this.uriString = str;
        }

        static android.net.Uri readFrom(android.os.Parcel parcel) {
            return new android.net.Uri.StringUri(parcel.readString8());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(1);
            parcel.writeString8(this.uriString);
        }

        private int findSchemeSeparator() {
            if (this.cachedSsi == -2) {
                int indexOf = this.uriString.indexOf(58);
                this.cachedSsi = indexOf;
                return indexOf;
            }
            return this.cachedSsi;
        }

        private int findFragmentSeparator() {
            if (this.cachedFsi == -2) {
                int indexOf = this.uriString.indexOf(35, findSchemeSeparator());
                this.cachedFsi = indexOf;
                return indexOf;
            }
            return this.cachedFsi;
        }

        @Override // android.net.Uri
        public boolean isHierarchical() {
            int findSchemeSeparator = findSchemeSeparator();
            if (findSchemeSeparator == -1) {
                return true;
            }
            int i = findSchemeSeparator + 1;
            return this.uriString.length() != i && this.uriString.charAt(i) == '/';
        }

        @Override // android.net.Uri
        public boolean isRelative() {
            return findSchemeSeparator() == -1;
        }

        @Override // android.net.Uri
        public java.lang.String getScheme() {
            if (this.scheme != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                return this.scheme;
            }
            java.lang.String parseScheme = parseScheme();
            this.scheme = parseScheme;
            return parseScheme;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String parseScheme() {
            int findSchemeSeparator = findSchemeSeparator();
            if (findSchemeSeparator == -1) {
                return null;
            }
            return this.uriString.substring(0, findSchemeSeparator);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.net.Uri.Part getSsp() {
            if (this.ssp != null) {
                return this.ssp;
            }
            android.net.Uri.Part fromEncoded = android.net.Uri.Part.fromEncoded(parseSsp());
            this.ssp = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedSchemeSpecificPart() {
            return getSsp().getEncoded();
        }

        @Override // android.net.Uri
        public java.lang.String getSchemeSpecificPart() {
            return getSsp().getDecoded();
        }

        private java.lang.String parseSsp() {
            int findSchemeSeparator = findSchemeSeparator();
            int findFragmentSeparator = findFragmentSeparator();
            if (findFragmentSeparator == -1) {
                return this.uriString.substring(findSchemeSeparator + 1);
            }
            return this.uriString.substring(findSchemeSeparator + 1, findFragmentSeparator);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.net.Uri.Part getAuthorityPart() {
            if (this.authority == null) {
                android.net.Uri.Part fromEncoded = android.net.Uri.Part.fromEncoded(parseAuthority(this.uriString, findSchemeSeparator()));
                this.authority = fromEncoded;
                return fromEncoded;
            }
            return this.authority;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedAuthority() {
            return getAuthorityPart().getEncoded();
        }

        @Override // android.net.Uri
        public java.lang.String getAuthority() {
            return getAuthorityPart().getDecoded();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.net.Uri.PathPart getPathPart() {
            if (this.path == null) {
                android.net.Uri.PathPart fromEncoded = android.net.Uri.PathPart.fromEncoded(parsePath());
                this.path = fromEncoded;
                return fromEncoded;
            }
            return this.path;
        }

        @Override // android.net.Uri
        public java.lang.String getPath() {
            return getPathPart().getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedPath() {
            return getPathPart().getEncoded();
        }

        @Override // android.net.Uri
        public java.util.List<java.lang.String> getPathSegments() {
            return getPathPart().getPathSegments();
        }

        private java.lang.String parsePath() {
            java.lang.String str = this.uriString;
            int findSchemeSeparator = findSchemeSeparator();
            if (findSchemeSeparator > -1) {
                int i = findSchemeSeparator + 1;
                if ((i == str.length()) || str.charAt(i) != '/') {
                    return null;
                }
            }
            return parsePath(str, findSchemeSeparator);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.net.Uri.Part getQueryPart() {
            if (this.query != null) {
                return this.query;
            }
            android.net.Uri.Part fromEncoded = android.net.Uri.Part.fromEncoded(parseQuery());
            this.query = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedQuery() {
            return getQueryPart().getEncoded();
        }

        private java.lang.String parseQuery() {
            int indexOf = this.uriString.indexOf(63, findSchemeSeparator());
            if (indexOf == -1) {
                return null;
            }
            int findFragmentSeparator = findFragmentSeparator();
            if (findFragmentSeparator == -1) {
                return this.uriString.substring(indexOf + 1);
            }
            if (findFragmentSeparator < indexOf) {
                return null;
            }
            return this.uriString.substring(indexOf + 1, findFragmentSeparator);
        }

        @Override // android.net.Uri
        public java.lang.String getQuery() {
            return getQueryPart().getDecoded();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.net.Uri.Part getFragmentPart() {
            if (this.fragment != null) {
                return this.fragment;
            }
            android.net.Uri.Part fromEncoded = android.net.Uri.Part.fromEncoded(parseFragment());
            this.fragment = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedFragment() {
            return getFragmentPart().getEncoded();
        }

        private java.lang.String parseFragment() {
            int findFragmentSeparator = findFragmentSeparator();
            if (findFragmentSeparator == -1) {
                return null;
            }
            return this.uriString.substring(findFragmentSeparator + 1);
        }

        @Override // android.net.Uri
        public java.lang.String getFragment() {
            return getFragmentPart().getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String toString() {
            return this.uriString;
        }

        static java.lang.String parseAuthority(java.lang.String str, int i) {
            int length = str.length();
            int i2 = i + 2;
            if (length > i2 && str.charAt(i + 1) == '/' && str.charAt(i2) == '/') {
                int i3 = i + 3;
                for (int i4 = i3; i4 < length; i4++) {
                    switch (str.charAt(i4)) {
                        case '#':
                        case '/':
                        case '?':
                        case '\\':
                            break;
                        default:
                    }
                    return str.substring(i3, i4);
                }
                return str.substring(i3, i4);
            }
            return null;
        }

        static java.lang.String parsePath(java.lang.String str, int i) {
            int i2;
            int length = str.length();
            int i3 = i + 2;
            if (length > i3 && str.charAt(i + 1) == '/' && str.charAt(i3) == '/') {
                i2 = i + 3;
                while (i2 < length) {
                    switch (str.charAt(i2)) {
                        case '#':
                        case '?':
                            return "";
                        case '/':
                        case '\\':
                            break;
                        default:
                            i2++;
                    }
                }
            } else {
                i2 = i + 1;
            }
            for (int i4 = i2; i4 < length; i4++) {
                switch (str.charAt(i4)) {
                    case '#':
                    case '?':
                        break;
                    default:
                }
                return str.substring(i2, i4);
            }
            return str.substring(i2, i4);
        }

        @Override // android.net.Uri
        public android.net.Uri.Builder buildUpon() {
            if (isHierarchical()) {
                return new android.net.Uri.Builder().scheme(getScheme()).authority(getAuthorityPart()).path(getPathPart()).query(getQueryPart()).fragment(getFragmentPart());
            }
            return new android.net.Uri.Builder().scheme(getScheme()).opaquePart(getSsp()).fragment(getFragmentPart());
        }
    }

    public static android.net.Uri fromParts(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (str == null) {
            throw new java.lang.NullPointerException("scheme");
        }
        if (str2 == null) {
            throw new java.lang.NullPointerException("ssp");
        }
        return new android.net.Uri.OpaqueUri(str, android.net.Uri.Part.fromDecoded(str2), android.net.Uri.Part.fromDecoded(str3));
    }

    private static class OpaqueUri extends android.net.Uri {
        static final int TYPE_ID = 2;
        private volatile java.lang.String cachedString;
        private final android.net.Uri.Part fragment;
        private final java.lang.String scheme;
        private final android.net.Uri.Part ssp;

        private OpaqueUri(java.lang.String str, android.net.Uri.Part part, android.net.Uri.Part part2) {
            super();
            this.cachedString = android.net.Uri.NotCachedHolder.NOT_CACHED;
            this.scheme = str;
            this.ssp = part;
            this.fragment = part2 == null ? android.net.Uri.Part.NULL : part2;
        }

        static android.net.Uri readFrom(android.os.Parcel parcel) {
            android.net.Uri.StringUri stringUri = new android.net.Uri.StringUri(parcel.readString8());
            return new android.net.Uri.OpaqueUri(stringUri.parseScheme(), stringUri.getSsp(), stringUri.getFragmentPart());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(2);
            parcel.writeString8(toString());
        }

        @Override // android.net.Uri
        public boolean isHierarchical() {
            return false;
        }

        @Override // android.net.Uri
        public boolean isRelative() {
            return this.scheme == null;
        }

        @Override // android.net.Uri
        public java.lang.String getScheme() {
            return this.scheme;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedSchemeSpecificPart() {
            return this.ssp.getEncoded();
        }

        @Override // android.net.Uri
        public java.lang.String getSchemeSpecificPart() {
            return this.ssp.getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getAuthority() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedAuthority() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getPath() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedPath() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getQuery() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedQuery() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getFragment() {
            return this.fragment.getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedFragment() {
            return this.fragment.getEncoded();
        }

        @Override // android.net.Uri
        public java.util.List<java.lang.String> getPathSegments() {
            return java.util.Collections.emptyList();
        }

        @Override // android.net.Uri
        public java.lang.String getLastPathSegment() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getUserInfo() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedUserInfo() {
            return null;
        }

        @Override // android.net.Uri
        public java.lang.String getHost() {
            return null;
        }

        @Override // android.net.Uri
        public int getPort() {
            return -1;
        }

        @Override // android.net.Uri
        public java.lang.String toString() {
            if (this.cachedString != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                return this.cachedString;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.scheme).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            sb.append(getEncodedSchemeSpecificPart());
            if (!this.fragment.isEmpty()) {
                sb.append('#').append(this.fragment.getEncoded());
            }
            java.lang.String sb2 = sb.toString();
            this.cachedString = sb2;
            return sb2;
        }

        @Override // android.net.Uri
        public android.net.Uri.Builder buildUpon() {
            return new android.net.Uri.Builder().scheme(this.scheme).opaquePart(this.ssp).fragment(this.fragment);
        }
    }

    static class PathSegments extends java.util.AbstractList<java.lang.String> implements java.util.RandomAccess {
        static final android.net.Uri.PathSegments EMPTY = new android.net.Uri.PathSegments(null, 0);
        final java.lang.String[] segments;
        final int size;

        PathSegments(java.lang.String[] strArr, int i) {
            this.segments = strArr;
            this.size = i;
        }

        @Override // java.util.AbstractList, java.util.List
        public java.lang.String get(int i) {
            if (i >= this.size) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return this.segments[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.size;
        }
    }

    static class PathSegmentsBuilder {
        java.lang.String[] segments;
        int size = 0;

        PathSegmentsBuilder() {
        }

        void add(java.lang.String str) {
            if (this.segments == null) {
                this.segments = new java.lang.String[4];
            } else if (this.size + 1 == this.segments.length) {
                java.lang.String[] strArr = new java.lang.String[this.segments.length * 2];
                java.lang.System.arraycopy(this.segments, 0, strArr, 0, this.segments.length);
                this.segments = strArr;
            }
            java.lang.String[] strArr2 = this.segments;
            int i = this.size;
            this.size = i + 1;
            strArr2[i] = str;
        }

        android.net.Uri.PathSegments build() {
            if (this.segments == null) {
                return android.net.Uri.PathSegments.EMPTY;
            }
            try {
                return new android.net.Uri.PathSegments(this.segments, this.size);
            } finally {
                this.segments = null;
            }
        }
    }

    private static abstract class AbstractHierarchicalUri extends android.net.Uri {
        private volatile java.lang.String host;
        private volatile int port;
        private android.net.Uri.Part userInfo;

        private AbstractHierarchicalUri() {
            super();
            this.host = android.net.Uri.NotCachedHolder.NOT_CACHED;
            this.port = -2;
        }

        @Override // android.net.Uri
        public java.lang.String getLastPathSegment() {
            java.util.List<java.lang.String> pathSegments = getPathSegments();
            int size = pathSegments.size();
            if (size == 0) {
                return null;
            }
            return pathSegments.get(size - 1);
        }

        private android.net.Uri.Part getUserInfoPart() {
            if (this.userInfo != null) {
                return this.userInfo;
            }
            android.net.Uri.Part fromEncoded = android.net.Uri.Part.fromEncoded(parseUserInfo());
            this.userInfo = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public final java.lang.String getEncodedUserInfo() {
            return getUserInfoPart().getEncoded();
        }

        private java.lang.String parseUserInfo() {
            int lastIndexOf;
            java.lang.String encodedAuthority = getEncodedAuthority();
            if (encodedAuthority == null || (lastIndexOf = encodedAuthority.lastIndexOf(64)) == -1) {
                return null;
            }
            return encodedAuthority.substring(0, lastIndexOf);
        }

        @Override // android.net.Uri
        public java.lang.String getUserInfo() {
            return getUserInfoPart().getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getHost() {
            if (this.host != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                return this.host;
            }
            java.lang.String parseHost = parseHost();
            this.host = parseHost;
            return parseHost;
        }

        private java.lang.String parseHost() {
            java.lang.String substring;
            java.lang.String encodedAuthority = getEncodedAuthority();
            if (encodedAuthority == null) {
                return null;
            }
            int lastIndexOf = encodedAuthority.lastIndexOf(64);
            int findPortSeparator = findPortSeparator(encodedAuthority);
            if (findPortSeparator == -1) {
                substring = encodedAuthority.substring(lastIndexOf + 1);
            } else {
                substring = encodedAuthority.substring(lastIndexOf + 1, findPortSeparator);
            }
            return decode(substring);
        }

        @Override // android.net.Uri
        public int getPort() {
            if (this.port == -2) {
                int parsePort = parsePort();
                this.port = parsePort;
                return parsePort;
            }
            return this.port;
        }

        private int parsePort() {
            java.lang.String encodedAuthority = getEncodedAuthority();
            int findPortSeparator = findPortSeparator(encodedAuthority);
            if (findPortSeparator == -1) {
                return -1;
            }
            try {
                return java.lang.Integer.parseInt(decode(encodedAuthority.substring(findPortSeparator + 1)));
            } catch (java.lang.NumberFormatException e) {
                android.util.Log.w(android.net.Uri.LOG, "Error parsing port string.", e);
                return -1;
            }
        }

        private int findPortSeparator(java.lang.String str) {
            if (str == null) {
                return -1;
            }
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                if (':' == charAt) {
                    return length;
                }
                if (charAt < '0' || charAt > '9') {
                    return -1;
                }
            }
            return -1;
        }
    }

    private static class HierarchicalUri extends android.net.Uri.AbstractHierarchicalUri {
        static final int TYPE_ID = 3;
        private final android.net.Uri.Part authority;
        private final android.net.Uri.Part fragment;
        private final android.net.Uri.PathPart path;
        private final android.net.Uri.Part query;
        private final java.lang.String scheme;
        private android.net.Uri.Part ssp;
        private volatile java.lang.String uriString;

        private HierarchicalUri(java.lang.String str, android.net.Uri.Part part, android.net.Uri.PathPart pathPart, android.net.Uri.Part part2, android.net.Uri.Part part3) {
            super();
            this.uriString = android.net.Uri.NotCachedHolder.NOT_CACHED;
            this.scheme = str;
            this.authority = android.net.Uri.Part.nonNull(part);
            this.path = generatePath(pathPart);
            this.query = android.net.Uri.Part.nonNull(part2);
            this.fragment = android.net.Uri.Part.nonNull(part3);
        }

        private android.net.Uri.PathPart generatePath(android.net.Uri.PathPart pathPart) {
            if ((this.scheme != null && this.scheme.length() > 0) || !this.authority.isEmpty()) {
                pathPart = android.net.Uri.PathPart.makeAbsolute(pathPart);
            }
            return pathPart == null ? android.net.Uri.PathPart.NULL : pathPart;
        }

        static android.net.Uri readFrom(android.os.Parcel parcel) {
            android.net.Uri.StringUri stringUri = new android.net.Uri.StringUri(parcel.readString8());
            return new android.net.Uri.HierarchicalUri(stringUri.getScheme(), stringUri.getAuthorityPart(), stringUri.getPathPart(), stringUri.getQueryPart(), stringUri.getFragmentPart());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(3);
            parcel.writeString8(toString());
        }

        @Override // android.net.Uri
        public boolean isHierarchical() {
            return true;
        }

        @Override // android.net.Uri
        public boolean isRelative() {
            return this.scheme == null;
        }

        @Override // android.net.Uri
        public java.lang.String getScheme() {
            return this.scheme;
        }

        private android.net.Uri.Part getSsp() {
            if (this.ssp != null) {
                return this.ssp;
            }
            android.net.Uri.Part fromEncoded = android.net.Uri.Part.fromEncoded(makeSchemeSpecificPart());
            this.ssp = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedSchemeSpecificPart() {
            return getSsp().getEncoded();
        }

        @Override // android.net.Uri
        public java.lang.String getSchemeSpecificPart() {
            return getSsp().getDecoded();
        }

        private java.lang.String makeSchemeSpecificPart() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            appendSspTo(sb);
            return sb.toString();
        }

        private void appendSspTo(java.lang.StringBuilder sb) {
            java.lang.String encoded = this.authority.getEncoded();
            if (encoded != null) {
                sb.append("//").append(encoded);
            }
            java.lang.String encoded2 = this.path.getEncoded();
            if (encoded2 != null) {
                sb.append(encoded2);
            }
            if (!this.query.isEmpty()) {
                sb.append('?').append(this.query.getEncoded());
            }
        }

        @Override // android.net.Uri
        public java.lang.String getAuthority() {
            return this.authority.getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedAuthority() {
            return this.authority.getEncoded();
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedPath() {
            return this.path.getEncoded();
        }

        @Override // android.net.Uri
        public java.lang.String getPath() {
            return this.path.getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getQuery() {
            return this.query.getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedQuery() {
            return this.query.getEncoded();
        }

        @Override // android.net.Uri
        public java.lang.String getFragment() {
            return this.fragment.getDecoded();
        }

        @Override // android.net.Uri
        public java.lang.String getEncodedFragment() {
            return this.fragment.getEncoded();
        }

        @Override // android.net.Uri
        public java.util.List<java.lang.String> getPathSegments() {
            return this.path.getPathSegments();
        }

        @Override // android.net.Uri
        public java.lang.String toString() {
            if (this.uriString != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                return this.uriString;
            }
            java.lang.String makeUriString = makeUriString();
            this.uriString = makeUriString;
            return makeUriString;
        }

        private java.lang.String makeUriString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (this.scheme != null) {
                sb.append(this.scheme).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            }
            appendSspTo(sb);
            if (!this.fragment.isEmpty()) {
                sb.append('#').append(this.fragment.getEncoded());
            }
            return sb.toString();
        }

        @Override // android.net.Uri
        public android.net.Uri.Builder buildUpon() {
            return new android.net.Uri.Builder().scheme(this.scheme).authority(this.authority).path(this.path).query(this.query).fragment(this.fragment);
        }
    }

    public static final class Builder {
        private android.net.Uri.Part authority;
        private android.net.Uri.Part fragment;
        private android.net.Uri.Part opaquePart;
        private android.net.Uri.PathPart path;
        private android.net.Uri.Part query;
        private java.lang.String scheme;

        public android.net.Uri.Builder scheme(java.lang.String str) {
            if (str != null) {
                this.scheme = str.replace("://", "");
            } else {
                this.scheme = null;
            }
            return this;
        }

        android.net.Uri.Builder opaquePart(android.net.Uri.Part part) {
            this.opaquePart = part;
            return this;
        }

        public android.net.Uri.Builder opaquePart(java.lang.String str) {
            return opaquePart(android.net.Uri.Part.fromDecoded(str));
        }

        public android.net.Uri.Builder encodedOpaquePart(java.lang.String str) {
            return opaquePart(android.net.Uri.Part.fromEncoded(str));
        }

        android.net.Uri.Builder authority(android.net.Uri.Part part) {
            this.opaquePart = null;
            this.authority = part;
            return this;
        }

        public android.net.Uri.Builder authority(java.lang.String str) {
            return authority(android.net.Uri.Part.fromDecoded(str));
        }

        public android.net.Uri.Builder encodedAuthority(java.lang.String str) {
            return authority(android.net.Uri.Part.fromEncoded(str));
        }

        android.net.Uri.Builder path(android.net.Uri.PathPart pathPart) {
            this.opaquePart = null;
            this.path = pathPart;
            return this;
        }

        public android.net.Uri.Builder path(java.lang.String str) {
            return path(android.net.Uri.PathPart.fromDecoded(str));
        }

        public android.net.Uri.Builder encodedPath(java.lang.String str) {
            return path(android.net.Uri.PathPart.fromEncoded(str));
        }

        public android.net.Uri.Builder appendPath(java.lang.String str) {
            return path(android.net.Uri.PathPart.appendDecodedSegment(this.path, str));
        }

        public android.net.Uri.Builder appendEncodedPath(java.lang.String str) {
            return path(android.net.Uri.PathPart.appendEncodedSegment(this.path, str));
        }

        android.net.Uri.Builder query(android.net.Uri.Part part) {
            this.opaquePart = null;
            this.query = part;
            return this;
        }

        public android.net.Uri.Builder query(java.lang.String str) {
            return query(android.net.Uri.Part.fromDecoded(str));
        }

        public android.net.Uri.Builder encodedQuery(java.lang.String str) {
            return query(android.net.Uri.Part.fromEncoded(str));
        }

        android.net.Uri.Builder fragment(android.net.Uri.Part part) {
            this.fragment = part;
            return this;
        }

        public android.net.Uri.Builder fragment(java.lang.String str) {
            return fragment(android.net.Uri.Part.fromDecoded(str));
        }

        public android.net.Uri.Builder encodedFragment(java.lang.String str) {
            return fragment(android.net.Uri.Part.fromEncoded(str));
        }

        public android.net.Uri.Builder appendQueryParameter(java.lang.String str, java.lang.String str2) {
            this.opaquePart = null;
            java.lang.String str3 = android.net.Uri.encode(str, null) + "=" + android.net.Uri.encode(str2, null);
            if (this.query == null) {
                this.query = android.net.Uri.Part.fromEncoded(str3);
                return this;
            }
            java.lang.String encoded = this.query.getEncoded();
            if (encoded == null || encoded.length() == 0) {
                this.query = android.net.Uri.Part.fromEncoded(str3);
            } else {
                this.query = android.net.Uri.Part.fromEncoded(encoded + "&" + str3);
            }
            return this;
        }

        public android.net.Uri.Builder clearQuery() {
            return query((android.net.Uri.Part) null);
        }

        public android.net.Uri build() {
            android.net.Uri.PathPart pathPart;
            if (this.opaquePart != null) {
                if (this.scheme == null) {
                    throw new java.lang.UnsupportedOperationException("An opaque URI must have a scheme.");
                }
                return new android.net.Uri.OpaqueUri(this.scheme, this.opaquePart, this.fragment);
            }
            android.net.Uri.PathPart pathPart2 = this.path;
            if (pathPart2 == null || pathPart2 == android.net.Uri.PathPart.NULL) {
                pathPart = android.net.Uri.PathPart.EMPTY;
            } else if (!hasSchemeOrAuthority()) {
                pathPart = pathPart2;
            } else {
                pathPart = android.net.Uri.PathPart.makeAbsolute(pathPart2);
            }
            return new android.net.Uri.HierarchicalUri(this.scheme, this.authority, pathPart, this.query, this.fragment);
        }

        private boolean hasSchemeOrAuthority() {
            return (this.scheme == null && (this.authority == null || this.authority == android.net.Uri.Part.NULL)) ? false : true;
        }

        public java.lang.String toString() {
            return build().toString();
        }
    }

    public java.util.Set<java.lang.String> getQueryParameterNames() {
        if (isOpaque()) {
            throw new java.lang.UnsupportedOperationException(NOT_HIERARCHICAL);
        }
        java.lang.String encodedQuery = getEncodedQuery();
        if (encodedQuery == null) {
            return java.util.Collections.emptySet();
        }
        java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        int i = 0;
        do {
            int indexOf = encodedQuery.indexOf(38, i);
            if (indexOf == -1) {
                indexOf = encodedQuery.length();
            }
            int indexOf2 = encodedQuery.indexOf(61, i);
            if (indexOf2 > indexOf || indexOf2 == -1) {
                indexOf2 = indexOf;
            }
            linkedHashSet.add(decode(encodedQuery.substring(i, indexOf2)));
            i = indexOf + 1;
        } while (i < encodedQuery.length());
        return java.util.Collections.unmodifiableSet(linkedHashSet);
    }

    public java.util.List<java.lang.String> getQueryParameters(java.lang.String str) {
        if (isOpaque()) {
            throw new java.lang.UnsupportedOperationException(NOT_HIERARCHICAL);
        }
        if (str == null) {
            throw new java.lang.NullPointerException("key");
        }
        java.lang.String encodedQuery = getEncodedQuery();
        if (encodedQuery == null) {
            return java.util.Collections.emptyList();
        }
        try {
            java.lang.String encode = java.net.URLEncoder.encode(str, "UTF-8");
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int i = 0;
            while (true) {
                int indexOf = encodedQuery.indexOf(38, i);
                int length = indexOf != -1 ? indexOf : encodedQuery.length();
                int indexOf2 = encodedQuery.indexOf(61, i);
                if (indexOf2 > length || indexOf2 == -1) {
                    indexOf2 = length;
                }
                if (indexOf2 - i == encode.length() && encodedQuery.regionMatches(i, encode, 0, encode.length())) {
                    if (indexOf2 == length) {
                        arrayList.add("");
                    } else {
                        arrayList.add(decode(encodedQuery.substring(indexOf2 + 1, length)));
                    }
                }
                if (indexOf != -1) {
                    i = indexOf + 1;
                } else {
                    return java.util.Collections.unmodifiableList(arrayList);
                }
            }
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public java.lang.String getQueryParameter(java.lang.String str) {
        if (isOpaque()) {
            throw new java.lang.UnsupportedOperationException(NOT_HIERARCHICAL);
        }
        if (str == null) {
            throw new java.lang.NullPointerException("key");
        }
        java.lang.String encodedQuery = getEncodedQuery();
        if (encodedQuery == null) {
            return null;
        }
        java.lang.String encode = encode(str, null);
        int length = encodedQuery.length();
        int i = 0;
        while (true) {
            int indexOf = encodedQuery.indexOf(38, i);
            int i2 = indexOf != -1 ? indexOf : length;
            int indexOf2 = encodedQuery.indexOf(61, i);
            if (indexOf2 > i2 || indexOf2 == -1) {
                indexOf2 = i2;
            }
            if (indexOf2 - i == encode.length() && encodedQuery.regionMatches(i, encode, 0, encode.length())) {
                if (indexOf2 != i2) {
                    return android.net.UriCodec.decode(encodedQuery.substring(indexOf2 + 1, i2), true, java.nio.charset.StandardCharsets.UTF_8, false);
                }
                return "";
            }
            if (indexOf == -1) {
                return null;
            }
            i = indexOf + 1;
        }
    }

    public boolean getBooleanQueryParameter(java.lang.String str, boolean z) {
        java.lang.String queryParameter = getQueryParameter(str);
        if (queryParameter == null) {
            return z;
        }
        java.lang.String lowerCase = queryParameter.toLowerCase(java.util.Locale.ROOT);
        return ("false".equals(lowerCase) || android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS.equals(lowerCase)) ? false : true;
    }

    public android.net.Uri normalizeScheme() {
        java.lang.String scheme = getScheme();
        if (scheme == null) {
            return this;
        }
        java.lang.String lowerCase = scheme.toLowerCase(java.util.Locale.ROOT);
        return scheme.equals(lowerCase) ? this : buildUpon().scheme(lowerCase).build();
    }

    public static void writeToParcel(android.os.Parcel parcel, android.net.Uri uri) {
        if (uri == null) {
            parcel.writeInt(0);
        } else {
            uri.writeToParcel(parcel, 0);
        }
    }

    public static java.lang.String encode(java.lang.String str) {
        return encode(str, null);
    }

    public static java.lang.String encode(java.lang.String str, java.lang.String str2) {
        java.lang.StringBuilder sb = null;
        if (str == null) {
            return null;
        }
        int length = str.length();
        int i = 0;
        while (i < length) {
            int i2 = i;
            while (i2 < length && isAllowed(str.charAt(i2), str2)) {
                i2++;
            }
            if (i2 == length) {
                if (i == 0) {
                    return str;
                }
                sb.append((java.lang.CharSequence) str, i, length);
                return sb.toString();
            }
            if (sb == null) {
                sb = new java.lang.StringBuilder();
            }
            if (i2 > i) {
                sb.append((java.lang.CharSequence) str, i, i2);
            }
            i = i2 + 1;
            while (i < length && !isAllowed(str.charAt(i), str2)) {
                i++;
            }
            try {
                byte[] bytes = str.substring(i2, i).getBytes("UTF-8");
                int length2 = bytes.length;
                for (int i3 = 0; i3 < length2; i3++) {
                    sb.append('%');
                    sb.append(HEX_DIGITS[(bytes[i3] & 240) >> 4]);
                    sb.append(HEX_DIGITS[bytes[i3] & 15]);
                }
            } catch (java.io.UnsupportedEncodingException e) {
                throw new java.lang.AssertionError(e);
            }
        }
        return sb == null ? str : sb.toString();
    }

    private static boolean isAllowed(char c, java.lang.String str) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || !((c < '0' || c > '9') && "_-!.~'()*".indexOf(c) == -1 && (str == null || str.indexOf(c) == -1));
    }

    public static java.lang.String encodeIfNotEncoded(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return null;
        }
        if (!android.content.pm.Flags.encodeAppIntent() || isEncoded(str, str2)) {
            return str;
        }
        return encode(str, str2);
    }

    private static boolean isEncoded(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!isAllowed(charAt, str2) && charAt != '%') {
                return false;
            }
        }
        return true;
    }

    public static java.lang.String decode(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return android.net.UriCodec.decode(str, false, java.nio.charset.StandardCharsets.UTF_8, false);
    }

    public static java.lang.String decodeIfNeeded(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return (android.content.pm.Flags.encodeAppIntent() && str.contains("%")) ? decode(str) : str;
    }

    static abstract class AbstractPart {
        volatile java.lang.String decoded;
        volatile java.lang.String encoded;

        abstract java.lang.String getEncoded();

        AbstractPart(java.lang.String str, java.lang.String str2) {
            if (str != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                this.encoded = str;
                this.decoded = android.net.Uri.NotCachedHolder.NOT_CACHED;
            } else {
                if (str2 != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                    this.encoded = android.net.Uri.NotCachedHolder.NOT_CACHED;
                    this.decoded = str2;
                    return;
                }
                throw new java.lang.IllegalArgumentException("Neither encoded nor decoded");
            }
        }

        final java.lang.String getDecoded() {
            if (this.decoded != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                return this.decoded;
            }
            java.lang.String decode = android.net.Uri.decode(this.encoded);
            this.decoded = decode;
            return decode;
        }
    }

    static class Part extends android.net.Uri.AbstractPart {
        static final android.net.Uri.Part NULL = new android.net.Uri.Part.EmptyPart(null);
        static final android.net.Uri.Part EMPTY = new android.net.Uri.Part.EmptyPart("");

        private Part(java.lang.String str, java.lang.String str2) {
            super(str, str2);
        }

        boolean isEmpty() {
            return false;
        }

        @Override // android.net.Uri.AbstractPart
        java.lang.String getEncoded() {
            if (this.encoded != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                return this.encoded;
            }
            java.lang.String encode = android.net.Uri.encode(this.decoded);
            this.encoded = encode;
            return encode;
        }

        static android.net.Uri.Part nonNull(android.net.Uri.Part part) {
            return part == null ? NULL : part;
        }

        static android.net.Uri.Part fromEncoded(java.lang.String str) {
            return from(str, android.net.Uri.NotCachedHolder.NOT_CACHED);
        }

        static android.net.Uri.Part fromDecoded(java.lang.String str) {
            return from(android.net.Uri.NotCachedHolder.NOT_CACHED, str);
        }

        static android.net.Uri.Part from(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                return NULL;
            }
            if (str.length() == 0) {
                return EMPTY;
            }
            if (str2 == null) {
                return NULL;
            }
            if (str2.length() == 0) {
                return EMPTY;
            }
            return new android.net.Uri.Part(str, str2);
        }

        private static class EmptyPart extends android.net.Uri.Part {
            public EmptyPart(java.lang.String str) {
                super(str, str);
                if (str != null && !str.isEmpty()) {
                    throw new java.lang.IllegalArgumentException("Expected empty value, got: " + str);
                }
                this.decoded = str;
                this.encoded = str;
            }

            @Override // android.net.Uri.Part
            boolean isEmpty() {
                return true;
            }
        }
    }

    static class PathPart extends android.net.Uri.AbstractPart {
        private android.net.Uri.PathSegments pathSegments;
        static final android.net.Uri.PathPart NULL = new android.net.Uri.PathPart(null, null);
        static final android.net.Uri.PathPart EMPTY = new android.net.Uri.PathPart("", "");

        private PathPart(java.lang.String str, java.lang.String str2) {
            super(str, str2);
        }

        @Override // android.net.Uri.AbstractPart
        java.lang.String getEncoded() {
            if (this.encoded != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                return this.encoded;
            }
            java.lang.String encode = android.net.Uri.encode(this.decoded, "/");
            this.encoded = encode;
            return encode;
        }

        android.net.Uri.PathSegments getPathSegments() {
            if (this.pathSegments != null) {
                return this.pathSegments;
            }
            java.lang.String encoded = getEncoded();
            if (encoded == null) {
                android.net.Uri.PathSegments pathSegments = android.net.Uri.PathSegments.EMPTY;
                this.pathSegments = pathSegments;
                return pathSegments;
            }
            android.net.Uri.PathSegmentsBuilder pathSegmentsBuilder = new android.net.Uri.PathSegmentsBuilder();
            int i = 0;
            while (true) {
                int indexOf = encoded.indexOf(47, i);
                if (indexOf <= -1) {
                    break;
                }
                if (i < indexOf) {
                    pathSegmentsBuilder.add(android.net.Uri.decode(encoded.substring(i, indexOf)));
                }
                i = indexOf + 1;
            }
            if (i < encoded.length()) {
                pathSegmentsBuilder.add(android.net.Uri.decode(encoded.substring(i)));
            }
            android.net.Uri.PathSegments build = pathSegmentsBuilder.build();
            this.pathSegments = build;
            return build;
        }

        static android.net.Uri.PathPart appendEncodedSegment(android.net.Uri.PathPart pathPart, java.lang.String str) {
            java.lang.String str2;
            if (pathPart == null) {
                return fromEncoded("/" + str);
            }
            java.lang.String encoded = pathPart.getEncoded();
            if (encoded == null) {
                encoded = "";
            }
            int length = encoded.length();
            if (length == 0) {
                str2 = "/" + str;
            } else {
                str2 = encoded.charAt(length + (-1)) == '/' ? encoded + str : encoded + "/" + str;
            }
            return fromEncoded(str2);
        }

        static android.net.Uri.PathPart appendDecodedSegment(android.net.Uri.PathPart pathPart, java.lang.String str) {
            return appendEncodedSegment(pathPart, android.net.Uri.encode(str));
        }

        static android.net.Uri.PathPart fromEncoded(java.lang.String str) {
            return from(str, android.net.Uri.NotCachedHolder.NOT_CACHED);
        }

        static android.net.Uri.PathPart fromDecoded(java.lang.String str) {
            return from(android.net.Uri.NotCachedHolder.NOT_CACHED, str);
        }

        static android.net.Uri.PathPart from(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                return NULL;
            }
            if (str.length() == 0) {
                return EMPTY;
            }
            return new android.net.Uri.PathPart(str, str2);
        }

        static android.net.Uri.PathPart makeAbsolute(android.net.Uri.PathPart pathPart) {
            java.lang.String str;
            boolean z = pathPart.encoded != android.net.Uri.NotCachedHolder.NOT_CACHED;
            java.lang.String str2 = z ? pathPart.encoded : pathPart.decoded;
            if (str2 == null || str2.length() == 0 || str2.startsWith("/")) {
                return pathPart;
            }
            java.lang.String str3 = z ? "/" + pathPart.encoded : android.net.Uri.NotCachedHolder.NOT_CACHED;
            if (pathPart.decoded != android.net.Uri.NotCachedHolder.NOT_CACHED) {
                str = "/" + pathPart.decoded;
            } else {
                str = android.net.Uri.NotCachedHolder.NOT_CACHED;
            }
            return new android.net.Uri.PathPart(str3, str);
        }
    }

    public static android.net.Uri withAppendedPath(android.net.Uri uri, java.lang.String str) {
        return uri.buildUpon().appendEncodedPath(str).build();
    }

    public android.net.Uri getCanonicalUri() {
        if ("file".equals(getScheme())) {
            try {
                java.lang.String canonicalPath = new java.io.File(getPath()).getCanonicalPath();
                if (android.os.Environment.isExternalStorageEmulated()) {
                    java.lang.String obj = android.os.Environment.getLegacyExternalStorageDirectory().toString();
                    if (canonicalPath.startsWith(obj)) {
                        return fromFile(new java.io.File(android.os.Environment.getExternalStorageDirectory().toString(), canonicalPath.substring(obj.length() + 1)));
                    }
                }
                return fromFile(new java.io.File(canonicalPath));
            } catch (java.io.IOException e) {
                return this;
            }
        }
        return this;
    }

    public void checkFileUriExposed(java.lang.String str) {
        if ("file".equals(getScheme()) && getPath() != null && !getPath().startsWith("/system/")) {
            android.os.StrictMode.onFileUriExposed(this, str);
        }
    }

    public void checkContentUriWithoutPermission(java.lang.String str, int i) {
        if ("content".equals(getScheme()) && !android.content.Intent.isAccessUriMode(i)) {
            android.os.StrictMode.onContentUriWithoutPermission(this, str);
        }
    }

    public boolean isPathPrefixMatch(android.net.Uri uri) {
        if (!java.util.Objects.equals(getScheme(), uri.getScheme()) || !java.util.Objects.equals(getAuthority(), uri.getAuthority())) {
            return false;
        }
        java.util.List<java.lang.String> pathSegments = getPathSegments();
        java.util.List<java.lang.String> pathSegments2 = uri.getPathSegments();
        int size = pathSegments2.size();
        if (pathSegments.size() < size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!java.util.Objects.equals(pathSegments.get(i), pathSegments2.get(i))) {
                return false;
            }
        }
        return true;
    }
}
