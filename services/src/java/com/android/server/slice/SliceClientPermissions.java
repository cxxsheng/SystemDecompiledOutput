package com.android.server.slice;

/* loaded from: classes2.dex */
public class SliceClientPermissions implements com.android.server.slice.DirtyTracker, com.android.server.slice.DirtyTracker.Persistable {
    private static final java.lang.String ATTR_AUTHORITY = "authority";
    private static final java.lang.String ATTR_FULL_ACCESS = "fullAccess";
    private static final java.lang.String ATTR_PKG = "pkg";
    private static final java.lang.String NAMESPACE = null;
    private static final java.lang.String TAG = "SliceClientPermissions";
    private static final java.lang.String TAG_AUTHORITY = "authority";
    static final java.lang.String TAG_CLIENT = "client";
    private static final java.lang.String TAG_PATH = "path";
    private final android.util.ArrayMap<com.android.server.slice.SlicePermissionManager.PkgUser, com.android.server.slice.SliceClientPermissions.SliceAuthority> mAuths = new android.util.ArrayMap<>();
    private boolean mHasFullAccess;
    private final com.android.server.slice.SlicePermissionManager.PkgUser mPkg;
    private final com.android.server.slice.DirtyTracker mTracker;

    public SliceClientPermissions(@android.annotation.NonNull com.android.server.slice.SlicePermissionManager.PkgUser pkgUser, @android.annotation.NonNull com.android.server.slice.DirtyTracker dirtyTracker) {
        this.mPkg = pkgUser;
        this.mTracker = dirtyTracker;
    }

    public com.android.server.slice.SlicePermissionManager.PkgUser getPkg() {
        return this.mPkg;
    }

    public synchronized java.util.Collection<com.android.server.slice.SliceClientPermissions.SliceAuthority> getAuthorities() {
        return new java.util.ArrayList(this.mAuths.values());
    }

    public synchronized com.android.server.slice.SliceClientPermissions.SliceAuthority getOrCreateAuthority(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser, com.android.server.slice.SlicePermissionManager.PkgUser pkgUser2) {
        com.android.server.slice.SliceClientPermissions.SliceAuthority sliceAuthority;
        sliceAuthority = this.mAuths.get(pkgUser);
        if (sliceAuthority == null) {
            sliceAuthority = new com.android.server.slice.SliceClientPermissions.SliceAuthority(pkgUser.getPkg(), pkgUser2, this);
            this.mAuths.put(pkgUser, sliceAuthority);
            onPersistableDirty(sliceAuthority);
        }
        return sliceAuthority;
    }

    public synchronized com.android.server.slice.SliceClientPermissions.SliceAuthority getAuthority(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        return this.mAuths.get(pkgUser);
    }

    public boolean hasFullAccess() {
        return this.mHasFullAccess;
    }

    public void setHasFullAccess(boolean z) {
        if (this.mHasFullAccess == z) {
            return;
        }
        this.mHasFullAccess = z;
        this.mTracker.onPersistableDirty(this);
    }

    public void removeAuthority(java.lang.String str, int i) {
        if (this.mAuths.remove(new com.android.server.slice.SlicePermissionManager.PkgUser(str, i)) != null) {
            this.mTracker.onPersistableDirty(this);
        }
    }

    public synchronized boolean hasPermission(android.net.Uri uri, int i) {
        boolean z = false;
        if (!java.util.Objects.equals(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT, uri.getScheme())) {
            return false;
        }
        com.android.server.slice.SliceClientPermissions.SliceAuthority authority = getAuthority(new com.android.server.slice.SlicePermissionManager.PkgUser(uri.getAuthority(), i));
        if (authority != null) {
            if (authority.hasPermission(uri.getPathSegments())) {
                z = true;
            }
        }
        return z;
    }

    public void grantUri(android.net.Uri uri, com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        getOrCreateAuthority(new com.android.server.slice.SlicePermissionManager.PkgUser(uri.getAuthority(), pkgUser.getUserId()), pkgUser).addPath(uri.getPathSegments());
    }

    public void revokeUri(android.net.Uri uri, com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        getOrCreateAuthority(new com.android.server.slice.SlicePermissionManager.PkgUser(uri.getAuthority(), pkgUser.getUserId()), pkgUser).removePath(uri.getPathSegments());
    }

    public void clear() {
        if (this.mHasFullAccess || !this.mAuths.isEmpty()) {
            this.mHasFullAccess = false;
            this.mAuths.clear();
            onPersistableDirty(this);
        }
    }

    @Override // com.android.server.slice.DirtyTracker
    public void onPersistableDirty(com.android.server.slice.DirtyTracker.Persistable persistable) {
        this.mTracker.onPersistableDirty(this);
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public java.lang.String getFileName() {
        return getFileName(this.mPkg);
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public synchronized void writeTo(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        try {
            xmlSerializer.startTag(NAMESPACE, TAG_CLIENT);
            xmlSerializer.attribute(NAMESPACE, ATTR_PKG, this.mPkg.toString());
            xmlSerializer.attribute(NAMESPACE, ATTR_FULL_ACCESS, this.mHasFullAccess ? "1" : "0");
            int size = this.mAuths.size();
            for (int i = 0; i < size; i++) {
                xmlSerializer.startTag(NAMESPACE, "authority");
                xmlSerializer.attribute(NAMESPACE, "authority", this.mAuths.valueAt(i).mAuthority);
                xmlSerializer.attribute(NAMESPACE, ATTR_PKG, this.mAuths.valueAt(i).mPkg.toString());
                this.mAuths.valueAt(i).writeTo(xmlSerializer);
                xmlSerializer.endTag(NAMESPACE, "authority");
            }
            xmlSerializer.endTag(NAMESPACE, TAG_CLIENT);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public static com.android.server.slice.SliceClientPermissions createFrom(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.slice.DirtyTracker dirtyTracker) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (true) {
            if (xmlPullParser.getEventType() != 2 || !TAG_CLIENT.equals(xmlPullParser.getName())) {
                if (xmlPullParser.getEventType() == 1) {
                    throw new org.xmlpull.v1.XmlPullParserException("Can't find client tag in xml");
                }
                xmlPullParser.next();
            } else {
                int depth = xmlPullParser.getDepth();
                com.android.server.slice.SliceClientPermissions sliceClientPermissions = new com.android.server.slice.SliceClientPermissions(new com.android.server.slice.SlicePermissionManager.PkgUser(xmlPullParser.getAttributeValue(NAMESPACE, ATTR_PKG)), dirtyTracker);
                java.lang.String attributeValue = xmlPullParser.getAttributeValue(NAMESPACE, ATTR_FULL_ACCESS);
                if (attributeValue == null) {
                    attributeValue = "0";
                }
                sliceClientPermissions.mHasFullAccess = java.lang.Integer.parseInt(attributeValue) != 0;
                xmlPullParser.next();
                while (xmlPullParser.getDepth() > depth) {
                    if (xmlPullParser.getEventType() == 1) {
                        return sliceClientPermissions;
                    }
                    if (xmlPullParser.getEventType() == 2 && "authority".equals(xmlPullParser.getName())) {
                        try {
                            com.android.server.slice.SlicePermissionManager.PkgUser pkgUser = new com.android.server.slice.SlicePermissionManager.PkgUser(xmlPullParser.getAttributeValue(NAMESPACE, ATTR_PKG));
                            com.android.server.slice.SliceClientPermissions.SliceAuthority sliceAuthority = new com.android.server.slice.SliceClientPermissions.SliceAuthority(xmlPullParser.getAttributeValue(NAMESPACE, "authority"), pkgUser, sliceClientPermissions);
                            sliceAuthority.readFrom(xmlPullParser);
                            sliceClientPermissions.mAuths.put(new com.android.server.slice.SlicePermissionManager.PkgUser(sliceAuthority.getAuthority(), pkgUser.getUserId()), sliceAuthority);
                        } catch (java.lang.IllegalArgumentException e) {
                            android.util.Slog.e(TAG, "Couldn't read PkgUser", e);
                        }
                    }
                    xmlPullParser.next();
                }
                return sliceClientPermissions;
            }
        }
    }

    public static java.lang.String getFileName(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        return java.lang.String.format("client_%s", pkgUser.toString());
    }

    public static class SliceAuthority implements com.android.server.slice.DirtyTracker.Persistable {
        public static final java.lang.String DELIMITER = "/";
        private final java.lang.String mAuthority;
        private final android.util.ArraySet<java.lang.String[]> mPaths = new android.util.ArraySet<>();
        private final com.android.server.slice.SlicePermissionManager.PkgUser mPkg;
        private final com.android.server.slice.DirtyTracker mTracker;

        public SliceAuthority(java.lang.String str, com.android.server.slice.SlicePermissionManager.PkgUser pkgUser, com.android.server.slice.DirtyTracker dirtyTracker) {
            this.mAuthority = str;
            this.mPkg = pkgUser;
            this.mTracker = dirtyTracker;
        }

        public java.lang.String getAuthority() {
            return this.mAuthority;
        }

        public com.android.server.slice.SlicePermissionManager.PkgUser getPkg() {
            return this.mPkg;
        }

        void addPath(java.util.List<java.lang.String> list) {
            java.lang.String[] strArr = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
            for (int size = this.mPaths.size() - 1; size >= 0; size--) {
                java.lang.String[] valueAt = this.mPaths.valueAt(size);
                if (isPathPrefixMatch(valueAt, strArr)) {
                    return;
                }
                if (isPathPrefixMatch(strArr, valueAt)) {
                    this.mPaths.removeAt(size);
                }
            }
            this.mPaths.add(strArr);
            this.mTracker.onPersistableDirty(this);
        }

        void removePath(java.util.List<java.lang.String> list) {
            java.lang.String[] strArr = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
            boolean z = false;
            for (int size = this.mPaths.size() - 1; size >= 0; size--) {
                if (isPathPrefixMatch(strArr, this.mPaths.valueAt(size))) {
                    this.mPaths.removeAt(size);
                    z = true;
                }
            }
            if (z) {
                this.mTracker.onPersistableDirty(this);
            }
        }

        public synchronized java.util.Collection<java.lang.String[]> getPaths() {
            return new android.util.ArraySet((android.util.ArraySet) this.mPaths);
        }

        public boolean hasPermission(java.util.List<java.lang.String> list) {
            java.util.Iterator<java.lang.String[]> it = this.mPaths.iterator();
            while (it.hasNext()) {
                if (isPathPrefixMatch(it.next(), (java.lang.String[]) list.toArray(new java.lang.String[list.size()]))) {
                    return true;
                }
            }
            return false;
        }

        private boolean isPathPrefixMatch(java.lang.String[] strArr, java.lang.String[] strArr2) {
            int length = strArr.length;
            if (strArr2.length < length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (!java.util.Objects.equals(strArr2[i], strArr[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.slice.DirtyTracker.Persistable
        public java.lang.String getFileName() {
            return null;
        }

        @Override // com.android.server.slice.DirtyTracker.Persistable
        public synchronized void writeTo(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
            int size = this.mPaths.size();
            for (int i = 0; i < size; i++) {
                java.lang.String[] valueAt = this.mPaths.valueAt(i);
                if (valueAt != null) {
                    xmlSerializer.startTag(com.android.server.slice.SliceClientPermissions.NAMESPACE, com.android.server.slice.SliceClientPermissions.TAG_PATH);
                    xmlSerializer.text(encodeSegments(valueAt));
                    xmlSerializer.endTag(com.android.server.slice.SliceClientPermissions.NAMESPACE, com.android.server.slice.SliceClientPermissions.TAG_PATH);
                }
            }
        }

        public synchronized void readFrom(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            try {
                xmlPullParser.next();
                int depth = xmlPullParser.getDepth();
                while (xmlPullParser.getDepth() >= depth) {
                    if (xmlPullParser.getEventType() == 2 && com.android.server.slice.SliceClientPermissions.TAG_PATH.equals(xmlPullParser.getName())) {
                        this.mPaths.add(decodeSegments(xmlPullParser.nextText()));
                    }
                    xmlPullParser.next();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        private java.lang.String encodeSegments(java.lang.String[] strArr) {
            java.lang.String[] strArr2 = new java.lang.String[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                strArr2[i] = android.net.Uri.encode(strArr[i]);
            }
            return android.text.TextUtils.join(DELIMITER, strArr2);
        }

        private java.lang.String[] decodeSegments(java.lang.String str) {
            java.lang.String[] split = str.split(DELIMITER, -1);
            for (int i = 0; i < split.length; i++) {
                split[i] = android.net.Uri.decode(split[i]);
            }
            return split;
        }

        public boolean equals(java.lang.Object obj) {
            if (!getClass().equals(obj != null ? obj.getClass() : null)) {
                return false;
            }
            com.android.server.slice.SliceClientPermissions.SliceAuthority sliceAuthority = (com.android.server.slice.SliceClientPermissions.SliceAuthority) obj;
            if (this.mPaths.size() != sliceAuthority.mPaths.size()) {
                return false;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mPaths);
            java.util.ArrayList arrayList2 = new java.util.ArrayList(sliceAuthority.mPaths);
            arrayList.sort(java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.slice.SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj2) {
                    java.lang.String lambda$equals$0;
                    lambda$equals$0 = com.android.server.slice.SliceClientPermissions.SliceAuthority.lambda$equals$0((java.lang.String[]) obj2);
                    return lambda$equals$0;
                }
            }));
            arrayList2.sort(java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.slice.SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj2) {
                    java.lang.String lambda$equals$1;
                    lambda$equals$1 = com.android.server.slice.SliceClientPermissions.SliceAuthority.lambda$equals$1((java.lang.String[]) obj2);
                    return lambda$equals$1;
                }
            }));
            for (int i = 0; i < arrayList.size(); i++) {
                java.lang.String[] strArr = (java.lang.String[]) arrayList.get(i);
                java.lang.String[] strArr2 = (java.lang.String[]) arrayList2.get(i);
                if (strArr.length != strArr2.length) {
                    return false;
                }
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    if (!java.util.Objects.equals(strArr[i2], strArr2[i2])) {
                        return false;
                    }
                }
            }
            return java.util.Objects.equals(this.mAuthority, sliceAuthority.mAuthority) && java.util.Objects.equals(this.mPkg, sliceAuthority.mPkg);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.String lambda$equals$0(java.lang.String[] strArr) {
            return android.text.TextUtils.join(",", strArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.String lambda$equals$1(java.lang.String[] strArr) {
            return android.text.TextUtils.join(",", strArr);
        }

        public java.lang.String toString() {
            return java.lang.String.format("(%s, %s: %s)", this.mAuthority, this.mPkg.toString(), pathToString(this.mPaths));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.String lambda$pathToString$2(java.lang.String[] strArr) {
            return android.text.TextUtils.join(DELIMITER, strArr);
        }

        private java.lang.String pathToString(android.util.ArraySet<java.lang.String[]> arraySet) {
            return android.text.TextUtils.join(", ", (java.lang.Iterable) arraySet.stream().map(new java.util.function.Function() { // from class: com.android.server.slice.SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$pathToString$2;
                    lambda$pathToString$2 = com.android.server.slice.SliceClientPermissions.SliceAuthority.lambda$pathToString$2((java.lang.String[]) obj);
                    return lambda$pathToString$2;
                }
            }).collect(java.util.stream.Collectors.toList()));
        }
    }
}
