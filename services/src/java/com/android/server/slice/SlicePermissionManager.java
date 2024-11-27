package com.android.server.slice;

/* loaded from: classes2.dex */
public class SlicePermissionManager implements com.android.server.slice.DirtyTracker {
    static final int DB_VERSION = 2;
    private static final long PERMISSION_CACHE_PERIOD = 300000;
    private static final java.lang.String SLICE_DIR = "slice";
    private static final java.lang.String TAG = "SlicePermissionManager";
    private static final java.lang.String TAG_LIST = "slice-access-list";
    private static final long WRITE_GRACE_PERIOD = 500;
    private final java.lang.String ATT_VERSION;
    private final android.util.ArrayMap<com.android.server.slice.SlicePermissionManager.PkgUser, com.android.server.slice.SliceClientPermissions> mCachedClients;
    private final android.util.ArrayMap<com.android.server.slice.SlicePermissionManager.PkgUser, com.android.server.slice.SliceProviderPermissions> mCachedProviders;
    private final android.content.Context mContext;
    private final android.util.ArraySet<com.android.server.slice.DirtyTracker.Persistable> mDirty;
    private final android.os.Handler mHandler;
    private final java.io.File mSliceDir;

    @com.android.internal.annotations.VisibleForTesting
    SlicePermissionManager(android.content.Context context, android.os.Looper looper, java.io.File file) {
        this.ATT_VERSION = "version";
        this.mCachedProviders = new android.util.ArrayMap<>();
        this.mCachedClients = new android.util.ArrayMap<>();
        this.mDirty = new android.util.ArraySet<>();
        this.mContext = context;
        this.mHandler = new com.android.server.slice.SlicePermissionManager.H(looper);
        this.mSliceDir = file;
    }

    public SlicePermissionManager(android.content.Context context, android.os.Looper looper) {
        this(context, looper, new java.io.File(android.os.Environment.getDataDirectory(), "system/slice"));
    }

    public void grantFullAccess(java.lang.String str, int i) {
        getClient(new com.android.server.slice.SlicePermissionManager.PkgUser(str, i)).setHasFullAccess(true);
    }

    public void grantSliceAccess(java.lang.String str, int i, java.lang.String str2, int i2, android.net.Uri uri) {
        com.android.server.slice.SlicePermissionManager.PkgUser pkgUser = new com.android.server.slice.SlicePermissionManager.PkgUser(str, i);
        com.android.server.slice.SlicePermissionManager.PkgUser pkgUser2 = new com.android.server.slice.SlicePermissionManager.PkgUser(str2, i2);
        getClient(pkgUser).grantUri(uri, pkgUser2);
        getProvider(pkgUser2).getOrCreateAuthority(android.content.ContentProvider.getUriWithoutUserId(uri).getAuthority()).addPkg(pkgUser);
    }

    public void revokeSliceAccess(java.lang.String str, int i, java.lang.String str2, int i2, android.net.Uri uri) {
        com.android.server.slice.SlicePermissionManager.PkgUser pkgUser = new com.android.server.slice.SlicePermissionManager.PkgUser(str, i);
        getClient(pkgUser).revokeUri(uri, new com.android.server.slice.SlicePermissionManager.PkgUser(str2, i2));
    }

    public void removePkg(java.lang.String str, int i) {
        com.android.server.slice.SlicePermissionManager.PkgUser pkgUser = new com.android.server.slice.SlicePermissionManager.PkgUser(str, i);
        for (com.android.server.slice.SliceProviderPermissions.SliceAuthority sliceAuthority : getProvider(pkgUser).getAuthorities()) {
            java.util.Iterator<com.android.server.slice.SlicePermissionManager.PkgUser> it = sliceAuthority.getPkgs().iterator();
            while (it.hasNext()) {
                getClient(it.next()).removeAuthority(sliceAuthority.getAuthority(), i);
            }
        }
        getClient(pkgUser).clear();
        this.mHandler.obtainMessage(3, pkgUser).sendToTarget();
    }

    public java.lang.String[] getAllPackagesGranted(java.lang.String str) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<com.android.server.slice.SliceProviderPermissions.SliceAuthority> it = getProvider(new com.android.server.slice.SlicePermissionManager.PkgUser(str, 0)).getAuthorities().iterator();
        while (it.hasNext()) {
            java.util.Iterator<com.android.server.slice.SlicePermissionManager.PkgUser> it2 = it.next().getPkgs().iterator();
            while (it2.hasNext()) {
                arraySet.add(it2.next().mPkg);
            }
        }
        return (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
    }

    public boolean hasFullAccess(java.lang.String str, int i) {
        return getClient(new com.android.server.slice.SlicePermissionManager.PkgUser(str, i)).hasFullAccess();
    }

    public boolean hasPermission(java.lang.String str, int i, android.net.Uri uri) {
        com.android.server.slice.SliceClientPermissions client = getClient(new com.android.server.slice.SlicePermissionManager.PkgUser(str, i));
        return client.hasFullAccess() || client.hasPermission(android.content.ContentProvider.getUriWithoutUserId(uri), android.content.ContentProvider.getUserIdFromUri(uri, i));
    }

    @Override // com.android.server.slice.DirtyTracker
    public void onPersistableDirty(com.android.server.slice.DirtyTracker.Persistable persistable) {
        this.mHandler.removeMessages(2);
        this.mHandler.obtainMessage(1, persistable).sendToTarget();
        this.mHandler.sendEmptyMessageDelayed(2, 500L);
    }

    public void writeBackup(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.slice.DirtyTracker.Persistable persistable;
        synchronized (this) {
            try {
                xmlSerializer.startTag(null, TAG_LIST);
                xmlSerializer.attribute(null, "version", java.lang.String.valueOf(2));
                com.android.server.slice.DirtyTracker dirtyTracker = new com.android.server.slice.DirtyTracker() { // from class: com.android.server.slice.SlicePermissionManager$$ExternalSyntheticLambda0
                    @Override // com.android.server.slice.DirtyTracker
                    public final void onPersistableDirty(com.android.server.slice.DirtyTracker.Persistable persistable2) {
                        com.android.server.slice.SlicePermissionManager.lambda$writeBackup$0(persistable2);
                    }
                };
                if (this.mHandler.hasMessages(2)) {
                    this.mHandler.removeMessages(2);
                    handlePersist();
                }
                for (java.lang.String str : new java.io.File(this.mSliceDir.getAbsolutePath()).list()) {
                    com.android.server.slice.SlicePermissionManager.ParserHolder parser = getParser(str);
                    while (true) {
                        try {
                            if (parser.parser.getEventType() == 1) {
                                persistable = null;
                                break;
                            } else if (parser.parser.getEventType() == 2) {
                                if ("client".equals(parser.parser.getName())) {
                                    persistable = com.android.server.slice.SliceClientPermissions.createFrom(parser.parser, dirtyTracker);
                                } else {
                                    persistable = com.android.server.slice.SliceProviderPermissions.createFrom(parser.parser, dirtyTracker);
                                }
                            } else {
                                parser.parser.next();
                            }
                        } finally {
                        }
                    }
                    if (persistable != null) {
                        persistable.writeTo(xmlSerializer);
                    } else {
                        android.util.Slog.w(TAG, "Invalid or empty slice permissions file: " + str);
                    }
                    if (parser != null) {
                        parser.close();
                    }
                }
                xmlSerializer.endTag(null, TAG_LIST);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$writeBackup$0(com.android.server.slice.DirtyTracker.Persistable persistable) {
    }

    public void readRestore(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        synchronized (this) {
            while (true) {
                try {
                    if (xmlPullParser.getEventType() == 2 && TAG_LIST.equals(xmlPullParser.getName())) {
                        break;
                    }
                    if (xmlPullParser.getEventType() == 1) {
                        break;
                    } else {
                        xmlPullParser.next();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "version", 0) < 2) {
                return;
            }
            while (xmlPullParser.getEventType() != 1) {
                if (xmlPullParser.getEventType() == 2) {
                    if ("client".equals(xmlPullParser.getName())) {
                        com.android.server.slice.SliceClientPermissions createFrom = com.android.server.slice.SliceClientPermissions.createFrom(xmlPullParser, this);
                        synchronized (this.mCachedClients) {
                            this.mCachedClients.put(createFrom.getPkg(), createFrom);
                        }
                        onPersistableDirty(createFrom);
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(4, createFrom.getPkg()), 300000L);
                    } else if ("provider".equals(xmlPullParser.getName())) {
                        com.android.server.slice.SliceProviderPermissions createFrom2 = com.android.server.slice.SliceProviderPermissions.createFrom(xmlPullParser, this);
                        synchronized (this.mCachedProviders) {
                            this.mCachedProviders.put(createFrom2.getPkg(), createFrom2);
                        }
                        onPersistableDirty(createFrom2);
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(5, createFrom2.getPkg()), 300000L);
                    } else {
                        xmlPullParser.next();
                    }
                } else {
                    xmlPullParser.next();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x006e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.slice.SliceClientPermissions getClient(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        com.android.server.slice.SliceClientPermissions sliceClientPermissions;
        synchronized (this.mCachedClients) {
            sliceClientPermissions = this.mCachedClients.get(pkgUser);
        }
        if (sliceClientPermissions == null) {
            try {
                com.android.server.slice.SlicePermissionManager.ParserHolder parser = getParser(com.android.server.slice.SliceClientPermissions.getFileName(pkgUser));
                try {
                    com.android.server.slice.SliceClientPermissions createFrom = com.android.server.slice.SliceClientPermissions.createFrom(parser.parser, this);
                    synchronized (this.mCachedClients) {
                        this.mCachedClients.put(pkgUser, createFrom);
                    }
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(4, pkgUser), 300000L);
                    if (parser != null) {
                        parser.close();
                    }
                    return createFrom;
                } catch (java.lang.Throwable th) {
                    if (parser != null) {
                        try {
                            parser.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.FileNotFoundException e) {
                sliceClientPermissions = new com.android.server.slice.SliceClientPermissions(pkgUser, this);
                synchronized (this.mCachedClients) {
                    this.mCachedClients.put(pkgUser, sliceClientPermissions);
                }
                return sliceClientPermissions;
            } catch (java.io.IOException e2) {
                android.util.Log.e(TAG, "Can't read client", e2);
                sliceClientPermissions = new com.android.server.slice.SliceClientPermissions(pkgUser, this);
                synchronized (this.mCachedClients) {
                }
            } catch (org.xmlpull.v1.XmlPullParserException e3) {
                android.util.Log.e(TAG, "Can't read client", e3);
                sliceClientPermissions = new com.android.server.slice.SliceClientPermissions(pkgUser, this);
                synchronized (this.mCachedClients) {
                }
            }
        }
        return sliceClientPermissions;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x006e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.slice.SliceProviderPermissions getProvider(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        com.android.server.slice.SliceProviderPermissions sliceProviderPermissions;
        synchronized (this.mCachedProviders) {
            sliceProviderPermissions = this.mCachedProviders.get(pkgUser);
        }
        if (sliceProviderPermissions == null) {
            try {
                com.android.server.slice.SlicePermissionManager.ParserHolder parser = getParser(com.android.server.slice.SliceProviderPermissions.getFileName(pkgUser));
                try {
                    com.android.server.slice.SliceProviderPermissions createFrom = com.android.server.slice.SliceProviderPermissions.createFrom(parser.parser, this);
                    synchronized (this.mCachedProviders) {
                        this.mCachedProviders.put(pkgUser, createFrom);
                    }
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(5, pkgUser), 300000L);
                    if (parser != null) {
                        parser.close();
                    }
                    return createFrom;
                } catch (java.lang.Throwable th) {
                    if (parser != null) {
                        try {
                            parser.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.FileNotFoundException e) {
                sliceProviderPermissions = new com.android.server.slice.SliceProviderPermissions(pkgUser, this);
                synchronized (this.mCachedProviders) {
                    this.mCachedProviders.put(pkgUser, sliceProviderPermissions);
                }
                return sliceProviderPermissions;
            } catch (java.io.IOException e2) {
                android.util.Log.e(TAG, "Can't read provider", e2);
                sliceProviderPermissions = new com.android.server.slice.SliceProviderPermissions(pkgUser, this);
                synchronized (this.mCachedProviders) {
                }
            } catch (org.xmlpull.v1.XmlPullParserException e3) {
                android.util.Log.e(TAG, "Can't read provider", e3);
                sliceProviderPermissions = new com.android.server.slice.SliceProviderPermissions(pkgUser, this);
                synchronized (this.mCachedProviders) {
                }
            }
        }
        return sliceProviderPermissions;
    }

    private com.android.server.slice.SlicePermissionManager.ParserHolder getParser(java.lang.String str) throws java.io.FileNotFoundException, org.xmlpull.v1.XmlPullParserException {
        android.util.AtomicFile file = getFile(str);
        com.android.server.slice.SlicePermissionManager.ParserHolder parserHolder = new com.android.server.slice.SlicePermissionManager.ParserHolder();
        parserHolder.input = file.openRead();
        parserHolder.parser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser();
        parserHolder.parser.setInput(parserHolder.input, android.util.Xml.Encoding.UTF_8.name());
        return parserHolder;
    }

    private android.util.AtomicFile getFile(java.lang.String str) {
        if (!this.mSliceDir.exists()) {
            this.mSliceDir.mkdir();
        }
        return new android.util.AtomicFile(new java.io.File(this.mSliceDir, str));
    }

    @com.android.internal.annotations.VisibleForTesting
    void handlePersist() {
        synchronized (this) {
            java.util.Iterator<com.android.server.slice.DirtyTracker.Persistable> it = this.mDirty.iterator();
            while (it.hasNext()) {
                com.android.server.slice.DirtyTracker.Persistable next = it.next();
                android.util.AtomicFile file = getFile(next.getFileName());
                try {
                    java.io.FileOutputStream startWrite = file.startWrite();
                    try {
                        org.xmlpull.v1.XmlSerializer newSerializer = org.xmlpull.v1.XmlPullParserFactory.newInstance().newSerializer();
                        newSerializer.setOutput(startWrite, android.util.Xml.Encoding.UTF_8.name());
                        next.writeTo(newSerializer);
                        newSerializer.flush();
                        file.finishWrite(startWrite);
                    } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e) {
                        android.util.Slog.w(TAG, "Failed to save access file, restoring backup", e);
                        file.failWrite(startWrite);
                    }
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(TAG, "Failed to save access file", e2);
                    return;
                }
            }
            this.mDirty.clear();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addDirtyImmediate(com.android.server.slice.DirtyTracker.Persistable persistable) {
        this.mDirty.add(persistable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemove(com.android.server.slice.SlicePermissionManager.PkgUser pkgUser) {
        getFile(com.android.server.slice.SliceClientPermissions.getFileName(pkgUser)).delete();
        getFile(com.android.server.slice.SliceProviderPermissions.getFileName(pkgUser)).delete();
        this.mDirty.remove(this.mCachedClients.remove(pkgUser));
        this.mDirty.remove(this.mCachedProviders.remove(pkgUser));
    }

    private final class H extends android.os.Handler {
        private static final int MSG_ADD_DIRTY = 1;
        private static final int MSG_CLEAR_CLIENT = 4;
        private static final int MSG_CLEAR_PROVIDER = 5;
        private static final int MSG_PERSIST = 2;
        private static final int MSG_REMOVE = 3;

        public H(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.slice.SlicePermissionManager.this.mDirty.add((com.android.server.slice.DirtyTracker.Persistable) message.obj);
                    return;
                case 2:
                    com.android.server.slice.SlicePermissionManager.this.handlePersist();
                    return;
                case 3:
                    com.android.server.slice.SlicePermissionManager.this.handleRemove((com.android.server.slice.SlicePermissionManager.PkgUser) message.obj);
                    return;
                case 4:
                    synchronized (com.android.server.slice.SlicePermissionManager.this.mCachedClients) {
                        com.android.server.slice.SlicePermissionManager.this.mCachedClients.remove(message.obj);
                    }
                    return;
                case 5:
                    synchronized (com.android.server.slice.SlicePermissionManager.this.mCachedProviders) {
                        com.android.server.slice.SlicePermissionManager.this.mCachedProviders.remove(message.obj);
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public static class PkgUser {
        private static final java.lang.String FORMAT = "%s@%d";
        private static final java.lang.String SEPARATOR = "@";
        private final java.lang.String mPkg;
        private final int mUserId;

        public PkgUser(java.lang.String str, int i) {
            this.mPkg = str;
            this.mUserId = i;
        }

        public PkgUser(java.lang.String str) throws java.lang.IllegalArgumentException {
            try {
                java.lang.String[] split = str.split(SEPARATOR, 2);
                this.mPkg = split[0];
                this.mUserId = java.lang.Integer.parseInt(split[1]);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        }

        public java.lang.String getPkg() {
            return this.mPkg;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public int hashCode() {
            return this.mPkg.hashCode() + this.mUserId;
        }

        public boolean equals(java.lang.Object obj) {
            if (!getClass().equals(obj != null ? obj.getClass() : null)) {
                return false;
            }
            com.android.server.slice.SlicePermissionManager.PkgUser pkgUser = (com.android.server.slice.SlicePermissionManager.PkgUser) obj;
            return java.util.Objects.equals(pkgUser.mPkg, this.mPkg) && pkgUser.mUserId == this.mUserId;
        }

        public java.lang.String toString() {
            return java.lang.String.format(FORMAT, this.mPkg, java.lang.Integer.valueOf(this.mUserId));
        }
    }

    private class ParserHolder implements java.lang.AutoCloseable {
        private java.io.InputStream input;
        private org.xmlpull.v1.XmlPullParser parser;

        private ParserHolder() {
        }

        @Override // java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            this.input.close();
        }
    }
}
