package com.android.server.slice;

/* loaded from: classes2.dex */
public class SliceProviderPermissions implements com.android.server.slice.DirtyTracker, com.android.server.slice.DirtyTracker.Persistable {
    private static final java.lang.String ATTR_AUTHORITY = "authority";
    private static final java.lang.String ATTR_PKG = "pkg";
    private static final java.lang.String NAMESPACE = null;
    private static final java.lang.String TAG = "SliceProviderPermissions";
    private static final java.lang.String TAG_AUTHORITY = "authority";
    private static final java.lang.String TAG_PKG = "pkg";
    static final java.lang.String TAG_PROVIDER = "provider";
    private final android.util.ArrayMap<java.lang.String, com.android.server.slice.SliceProviderPermissions.SliceAuthority> mAuths = new android.util.ArrayMap<>();
    private final com.android.server.slice.SlicePermissionManager.PkgUser mPkg;
    private final com.android.server.slice.DirtyTracker mTracker;

    public SliceProviderPermissions(@android.annotation.NonNull com.android.server.slice.SlicePermissionManager.PkgUser pkgUser, @android.annotation.NonNull com.android.server.slice.DirtyTracker dirtyTracker) {
        this.mPkg = pkgUser;
        this.mTracker = dirtyTracker;
    }

    public com.android.server.slice.SlicePermissionManager.PkgUser getPkg() {
        return this.mPkg;
    }

    public synchronized java.util.Collection<com.android.server.slice.SliceProviderPermissions.SliceAuthority> getAuthorities() {
        return new java.util.ArrayList(this.mAuths.values());
    }

    public synchronized com.android.server.slice.SliceProviderPermissions.SliceAuthority getOrCreateAuthority(java.lang.String str) {
        com.android.server.slice.SliceProviderPermissions.SliceAuthority sliceAuthority;
        sliceAuthority = this.mAuths.get(str);
        if (sliceAuthority == null) {
            sliceAuthority = new com.android.server.slice.SliceProviderPermissions.SliceAuthority(str, this);
            this.mAuths.put(str, sliceAuthority);
            onPersistableDirty(sliceAuthority);
        }
        return sliceAuthority;
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
            xmlSerializer.startTag(NAMESPACE, TAG_PROVIDER);
            xmlSerializer.attribute(NAMESPACE, "pkg", this.mPkg.toString());
            int size = this.mAuths.size();
            for (int i = 0; i < size; i++) {
                xmlSerializer.startTag(NAMESPACE, "authority");
                xmlSerializer.attribute(NAMESPACE, "authority", this.mAuths.valueAt(i).mAuthority);
                this.mAuths.valueAt(i).writeTo(xmlSerializer);
                xmlSerializer.endTag(NAMESPACE, "authority");
            }
            xmlSerializer.endTag(NAMESPACE, TAG_PROVIDER);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public static com.android.server.slice.SliceProviderPermissions createFrom(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.slice.DirtyTracker dirtyTracker) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (true) {
            if (xmlPullParser.getEventType() == 2 && TAG_PROVIDER.equals(xmlPullParser.getName())) {
                break;
            }
            xmlPullParser.next();
        }
        int depth = xmlPullParser.getDepth();
        com.android.server.slice.SliceProviderPermissions sliceProviderPermissions = new com.android.server.slice.SliceProviderPermissions(new com.android.server.slice.SlicePermissionManager.PkgUser(xmlPullParser.getAttributeValue(NAMESPACE, "pkg")), dirtyTracker);
        xmlPullParser.next();
        while (xmlPullParser.getDepth() > depth) {
            if (xmlPullParser.getEventType() == 2 && "authority".equals(xmlPullParser.getName())) {
                try {
                    com.android.server.slice.SliceProviderPermissions.SliceAuthority sliceAuthority = new com.android.server.slice.SliceProviderPermissions.SliceAuthority(xmlPullParser.getAttributeValue(NAMESPACE, "authority"), sliceProviderPermissions);
                    sliceAuthority.readFrom(xmlPullParser);
                    sliceProviderPermissions.mAuths.put(sliceAuthority.getAuthority(), sliceAuthority);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(TAG, "Couldn't read PkgUser", e);
                }
            }
            xmlPullParser.next();
        }
        return sliceProviderPermissions;
    }

    public static java.lang.String getFileName(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        return java.lang.String.format("provider_%s", pkgUser.toString());
    }

    public static class SliceAuthority implements com.android.server.slice.DirtyTracker.Persistable {
        private final java.lang.String mAuthority;
        private final android.util.ArraySet<com.android.server.slice.SlicePermissionManager.PkgUser> mPkgs = new android.util.ArraySet<>();
        private final com.android.server.slice.DirtyTracker mTracker;

        public SliceAuthority(java.lang.String str, com.android.server.slice.DirtyTracker dirtyTracker) {
            this.mAuthority = str;
            this.mTracker = dirtyTracker;
        }

        public java.lang.String getAuthority() {
            return this.mAuthority;
        }

        public synchronized void addPkg(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
            if (this.mPkgs.add(pkgUser)) {
                this.mTracker.onPersistableDirty(this);
            }
        }

        public synchronized void removePkg(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
            if (this.mPkgs.remove(pkgUser)) {
                this.mTracker.onPersistableDirty(this);
            }
        }

        public synchronized java.util.Collection<com.android.server.slice.SlicePermissionManager.PkgUser> getPkgs() {
            return new android.util.ArraySet((android.util.ArraySet) this.mPkgs);
        }

        @Override // com.android.server.slice.DirtyTracker.Persistable
        public java.lang.String getFileName() {
            return null;
        }

        @Override // com.android.server.slice.DirtyTracker.Persistable
        public synchronized void writeTo(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
            int size = this.mPkgs.size();
            for (int i = 0; i < size; i++) {
                xmlSerializer.startTag(com.android.server.slice.SliceProviderPermissions.NAMESPACE, "pkg");
                xmlSerializer.text(this.mPkgs.valueAt(i).toString());
                xmlSerializer.endTag(com.android.server.slice.SliceProviderPermissions.NAMESPACE, "pkg");
            }
        }

        public synchronized void readFrom(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            try {
                xmlPullParser.next();
                int depth = xmlPullParser.getDepth();
                while (xmlPullParser.getDepth() >= depth) {
                    if (xmlPullParser.getEventType() == 2 && "pkg".equals(xmlPullParser.getName())) {
                        this.mPkgs.add(new com.android.server.slice.SlicePermissionManager.PkgUser(xmlPullParser.nextText()));
                    }
                    xmlPullParser.next();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (!getClass().equals(obj != null ? obj.getClass() : null)) {
                return false;
            }
            com.android.server.slice.SliceProviderPermissions.SliceAuthority sliceAuthority = (com.android.server.slice.SliceProviderPermissions.SliceAuthority) obj;
            return java.util.Objects.equals(this.mAuthority, sliceAuthority.mAuthority) && java.util.Objects.equals(this.mPkgs, sliceAuthority.mPkgs);
        }

        public java.lang.String toString() {
            return java.lang.String.format("(%s: %s)", this.mAuthority, this.mPkgs.toString());
        }
    }
}
