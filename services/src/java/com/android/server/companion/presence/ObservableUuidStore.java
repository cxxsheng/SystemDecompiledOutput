package com.android.server.companion.presence;

/* loaded from: classes.dex */
public class ObservableUuidStore {
    private static final java.lang.String FILE_NAME = "observing_uuids_presence.xml";
    private static final int READ_FROM_DISK_TIMEOUT = 5;
    private static final java.lang.String TAG = "CDM_ObservableUuidStore";
    private static final java.lang.String XML_ATTR_PACKAGE = "package_name";
    private static final java.lang.String XML_ATTR_TIME_APPROVED = "time_approved";
    private static final java.lang.String XML_ATTR_USER_ID = "user_id";
    private static final java.lang.String XML_ATTR_UUID = "uuid";
    private static final java.lang.String XML_TAG_UUID = "uuid";
    private static final java.lang.String XML_TAG_UUIDS = "uuids";
    private final java.util.concurrent.ConcurrentMap<java.lang.Integer, android.util.AtomicFile> mUserIdToStorageFile = new java.util.concurrent.ConcurrentHashMap();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.List<com.android.server.companion.presence.ObservableUuid>> mCachedPerUser = new android.util.SparseArray<>();
    private final java.util.concurrent.ExecutorService mExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();

    public void removeObservableUuid(final int i, final android.os.ParcelUuid parcelUuid, final java.lang.String str) {
        final java.util.List<com.android.server.companion.presence.ObservableUuid> readObservableUuidsFromCache;
        synchronized (this.mLock) {
            readObservableUuidsFromCache = readObservableUuidsFromCache(i);
            readObservableUuidsFromCache.removeIf(new java.util.function.Predicate() { // from class: com.android.server.companion.presence.ObservableUuidStore$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeObservableUuid$0;
                    lambda$removeObservableUuid$0 = com.android.server.companion.presence.ObservableUuidStore.lambda$removeObservableUuid$0(str, parcelUuid, (com.android.server.companion.presence.ObservableUuid) obj);
                    return lambda$removeObservableUuid$0;
                }
            });
            this.mCachedPerUser.set(i, readObservableUuidsFromCache);
        }
        this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.companion.presence.ObservableUuidStore$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.presence.ObservableUuidStore.this.lambda$removeObservableUuid$1(i, readObservableUuidsFromCache);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeObservableUuid$0(java.lang.String str, android.os.ParcelUuid parcelUuid, com.android.server.companion.presence.ObservableUuid observableUuid) {
        return observableUuid.getPackageName().equals(str) && observableUuid.getUuid().equals(parcelUuid);
    }

    public void writeObservableUuid(final int i, final com.android.server.companion.presence.ObservableUuid observableUuid) {
        final java.util.List<com.android.server.companion.presence.ObservableUuid> readObservableUuidsFromCache;
        android.util.Slog.i(TAG, "Writing uuid=" + observableUuid.getUuid() + " to store.");
        synchronized (this.mLock) {
            readObservableUuidsFromCache = readObservableUuidsFromCache(i);
            readObservableUuidsFromCache.removeIf(new java.util.function.Predicate() { // from class: com.android.server.companion.presence.ObservableUuidStore$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$writeObservableUuid$2;
                    lambda$writeObservableUuid$2 = com.android.server.companion.presence.ObservableUuidStore.lambda$writeObservableUuid$2(com.android.server.companion.presence.ObservableUuid.this, (com.android.server.companion.presence.ObservableUuid) obj);
                    return lambda$writeObservableUuid$2;
                }
            });
            readObservableUuidsFromCache.add(observableUuid);
            this.mCachedPerUser.set(i, readObservableUuidsFromCache);
        }
        this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.companion.presence.ObservableUuidStore$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.presence.ObservableUuidStore.this.lambda$writeObservableUuid$3(i, readObservableUuidsFromCache);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$writeObservableUuid$2(com.android.server.companion.presence.ObservableUuid observableUuid, com.android.server.companion.presence.ObservableUuid observableUuid2) {
        return observableUuid2.getUuid().equals(observableUuid.getUuid()) && observableUuid2.getPackageName().equals(observableUuid.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: writeObservableUuidToStore, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$writeObservableUuid$3(int i, @android.annotation.NonNull final java.util.List<com.android.server.companion.presence.ObservableUuid> list) {
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        android.util.Slog.i(TAG, "Writing ObservableUuid for user " + i + " to file=" + storageFileForUser.getBaseFile().getPath());
        synchronized (storageFileForUser) {
            com.android.server.companion.utils.DataStoreUtils.writeToFileSafely(storageFileForUser, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.presence.ObservableUuidStore$$ExternalSyntheticLambda2
                public final void acceptOrThrow(java.lang.Object obj) {
                    com.android.server.companion.presence.ObservableUuidStore.this.lambda$writeObservableUuidToStore$4(list, (java.io.FileOutputStream) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeObservableUuidToStore$4(java.util.List list, java.io.FileOutputStream fileOutputStream) throws java.lang.Exception {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startDocument((java.lang.String) null, true);
        writeObservableUuidToXml(resolveSerializer, list);
        resolveSerializer.endDocument();
    }

    private void writeObservableUuidToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.Nullable java.util.Collection<com.android.server.companion.presence.ObservableUuid> collection) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_UUIDS);
        java.util.Iterator<com.android.server.companion.presence.ObservableUuid> it = collection.iterator();
        while (it.hasNext()) {
            writeUuidToXml(typedXmlSerializer, it.next());
        }
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_UUIDS);
    }

    private void writeUuidToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.companion.presence.ObservableUuid observableUuid) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "uuid");
        com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, XML_ATTR_USER_ID, observableUuid.getUserId());
        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "uuid", observableUuid.getUuid().toString());
        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, XML_ATTR_PACKAGE, observableUuid.getPackageName());
        com.android.internal.util.XmlUtils.writeLongAttribute(typedXmlSerializer, XML_ATTR_TIME_APPROVED, observableUuid.getTimeApprovedMs());
        typedXmlSerializer.endTag((java.lang.String) null, "uuid");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<com.android.server.companion.presence.ObservableUuid> readObservableUuidsFromCache(final int i) {
        java.util.List<com.android.server.companion.presence.ObservableUuid> list = this.mCachedPerUser.get(i);
        if (list == null) {
            try {
                list = (java.util.List) this.mExecutor.submit(new java.util.concurrent.Callable() { // from class: com.android.server.companion.presence.ObservableUuidStore$$ExternalSyntheticLambda4
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        java.util.List lambda$readObservableUuidsFromCache$5;
                        lambda$readObservableUuidsFromCache$5 = com.android.server.companion.presence.ObservableUuidStore.this.lambda$readObservableUuidsFromCache$5(i);
                        return lambda$readObservableUuidsFromCache$5;
                    }
                }).get(5L, java.util.concurrent.TimeUnit.SECONDS);
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.e(TAG, "Thread reading ObservableUuid from disk is interrupted.");
            } catch (java.util.concurrent.ExecutionException e2) {
                android.util.Slog.e(TAG, "Error occurred while reading ObservableUuid from disk.");
            } catch (java.util.concurrent.TimeoutException e3) {
                android.util.Slog.e(TAG, "Reading ObservableUuid from disk timed out.");
            }
            this.mCachedPerUser.set(i, list);
        }
        return list;
    }

    @android.annotation.NonNull
    /* renamed from: readObservableUuidFromStore, reason: merged with bridge method [inline-methods] */
    public java.util.List<com.android.server.companion.presence.ObservableUuid> lambda$readObservableUuidsFromCache$5(int i) {
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        android.util.Slog.i(TAG, "Reading ObservableUuid for user " + i + " from file=" + storageFileForUser.getBaseFile().getPath());
        synchronized (storageFileForUser) {
            if (!storageFileForUser.getBaseFile().exists()) {
                android.util.Slog.d(TAG, "File does not exist -> Abort");
                return new java.util.ArrayList();
            }
            try {
                java.io.FileInputStream openRead = storageFileForUser.openRead();
                try {
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                    com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, XML_TAG_UUIDS);
                    java.util.List<com.android.server.companion.presence.ObservableUuid> readObservableUuidFromXml = readObservableUuidFromXml(resolvePullParser);
                    if (openRead != null) {
                        openRead.close();
                    }
                    return readObservableUuidFromXml;
                } catch (java.lang.Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(TAG, "Error while reading requests file", e);
                return new java.util.ArrayList();
            }
        }
    }

    @android.annotation.NonNull
    private java.util.List<com.android.server.companion.presence.ObservableUuid> readObservableUuidFromXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (!com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, XML_TAG_UUIDS)) {
            throw new org.xmlpull.v1.XmlPullParserException("The XML doesn't have start tag: uuids");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            typedXmlPullParser.nextTag();
            if (!com.android.server.companion.utils.DataStoreUtils.isEndOfTag(typedXmlPullParser, XML_TAG_UUIDS)) {
                if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, "uuid")) {
                    arrayList.add(readUuidFromXml(typedXmlPullParser));
                }
            } else {
                return arrayList;
            }
        }
    }

    private com.android.server.companion.presence.ObservableUuid readUuidFromXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (!com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, "uuid")) {
            throw new org.xmlpull.v1.XmlPullParserException("XML doesn't have start tag: uuid");
        }
        return new com.android.server.companion.presence.ObservableUuid(com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, XML_ATTR_USER_ID), android.os.ParcelUuid.fromString(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "uuid")), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_PACKAGE), java.lang.Long.valueOf(com.android.internal.util.XmlUtils.readLongAttribute(typedXmlPullParser, XML_ATTR_TIME_APPROVED)));
    }

    @android.annotation.NonNull
    private android.util.AtomicFile getStorageFileForUser(final int i) {
        return this.mUserIdToStorageFile.computeIfAbsent(java.lang.Integer.valueOf(i), new java.util.function.Function() { // from class: com.android.server.companion.presence.ObservableUuidStore$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.util.AtomicFile lambda$getStorageFileForUser$6;
                lambda$getStorageFileForUser$6 = com.android.server.companion.presence.ObservableUuidStore.lambda$getStorageFileForUser$6(i, (java.lang.Integer) obj);
                return lambda$getStorageFileForUser$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.AtomicFile lambda$getStorageFileForUser$6(int i, java.lang.Integer num) {
        return com.android.server.companion.utils.DataStoreUtils.createStorageFileForUser(i, FILE_NAME);
    }

    public java.util.List<com.android.server.companion.presence.ObservableUuid> getObservableUuidsForPackage(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                for (com.android.server.companion.presence.ObservableUuid observableUuid : readObservableUuidsFromCache(i)) {
                    if (observableUuid.getPackageName().equals(str)) {
                        arrayList.add(observableUuid);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public java.util.List<com.android.server.companion.presence.ObservableUuid> getObservableUuidsForUser(int i) {
        java.util.List<com.android.server.companion.presence.ObservableUuid> readObservableUuidsFromCache;
        synchronized (this.mLock) {
            readObservableUuidsFromCache = readObservableUuidsFromCache(i);
        }
        return readObservableUuidsFromCache;
    }
}
