package com.android.server.companion.datatransfer;

/* loaded from: classes.dex */
public class SystemDataTransferRequestStore {
    private static final java.lang.String FILE_NAME = "companion_device_system_data_transfer_requests.xml";
    private static final java.lang.String LOG_TAG = "CDM_SystemDataTransferRequestStore";
    private static final int READ_FROM_DISK_TIMEOUT = 5;
    private static final java.lang.String XML_ATTR_ASSOCIATION_ID = "association_id";
    private static final java.lang.String XML_ATTR_DATA_TYPE = "data_type";
    private static final java.lang.String XML_ATTR_IS_USER_CONSENTED = "is_user_consented";
    private static final java.lang.String XML_TAG_REQUEST = "request";
    private static final java.lang.String XML_TAG_REQUESTS = "requests";
    private final java.util.concurrent.ConcurrentMap<java.lang.Integer, android.util.AtomicFile> mUserIdToStorageFile = new java.util.concurrent.ConcurrentHashMap();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest>> mCachedPerUser = new android.util.SparseArray<>();
    private final java.util.concurrent.ExecutorService mExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();

    @android.annotation.NonNull
    public java.util.List<android.companion.datatransfer.SystemDataTransferRequest> readRequestsByAssociationId(int i, int i2) {
        java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromCache;
        synchronized (this.mLock) {
            readRequestsFromCache = readRequestsFromCache(i);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest : readRequestsFromCache) {
            if (systemDataTransferRequest.getAssociationId() == i2) {
                arrayList.add(systemDataTransferRequest);
            }
        }
        return arrayList;
    }

    public void writeRequest(final int i, final android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest) {
        final java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromCache;
        android.util.Slog.i(LOG_TAG, "Writing request=" + systemDataTransferRequest + " to store.");
        synchronized (this.mLock) {
            readRequestsFromCache = readRequestsFromCache(i);
            readRequestsFromCache.removeIf(new java.util.function.Predicate() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$writeRequest$0;
                    lambda$writeRequest$0 = com.android.server.companion.datatransfer.SystemDataTransferRequestStore.lambda$writeRequest$0(systemDataTransferRequest, (android.companion.datatransfer.SystemDataTransferRequest) obj);
                    return lambda$writeRequest$0;
                }
            });
            readRequestsFromCache.add(systemDataTransferRequest);
            this.mCachedPerUser.set(i, readRequestsFromCache);
        }
        this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.datatransfer.SystemDataTransferRequestStore.this.lambda$writeRequest$1(i, readRequestsFromCache);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$writeRequest$0(android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest, android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest2) {
        return systemDataTransferRequest2.getAssociationId() == systemDataTransferRequest.getAssociationId();
    }

    public void removeRequestsByAssociationId(final int i, final int i2) {
        final java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromCache;
        android.util.Slog.i(LOG_TAG, "Removing system data transfer requests for userId=" + i + ", associationId=" + i2);
        synchronized (this.mLock) {
            readRequestsFromCache = readRequestsFromCache(i);
            readRequestsFromCache.removeIf(new java.util.function.Predicate() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeRequestsByAssociationId$2;
                    lambda$removeRequestsByAssociationId$2 = com.android.server.companion.datatransfer.SystemDataTransferRequestStore.lambda$removeRequestsByAssociationId$2(i2, (android.companion.datatransfer.SystemDataTransferRequest) obj);
                    return lambda$removeRequestsByAssociationId$2;
                }
            });
            this.mCachedPerUser.set(i, readRequestsFromCache);
        }
        this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.datatransfer.SystemDataTransferRequestStore.this.lambda$removeRequestsByAssociationId$3(i, readRequestsFromCache);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeRequestsByAssociationId$2(int i, android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest) {
        return systemDataTransferRequest.getAssociationId() == i;
    }

    public byte[] getBackupPayload(int i) {
        byte[] fileToByteArray;
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            fileToByteArray = com.android.server.companion.utils.DataStoreUtils.fileToByteArray(storageFileForUser);
        }
        return fileToByteArray;
    }

    public java.util.List<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromPayload(byte[] bArr, int i) {
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(byteArrayInputStream);
                com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, XML_TAG_REQUESTS);
                java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromXml = readRequestsFromXml(resolvePullParser, i);
                byteArrayInputStream.close();
                return readRequestsFromXml;
            } catch (java.lang.Throwable th) {
                try {
                    byteArrayInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(LOG_TAG, "Error while reading requests file", e);
            return new java.util.ArrayList();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromCache(final int i) {
        java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> arrayList = this.mCachedPerUser.get(i);
        if (arrayList == null) {
            try {
                arrayList = (java.util.ArrayList) this.mExecutor.submit(new java.util.concurrent.Callable() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda2
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        java.util.ArrayList lambda$readRequestsFromCache$4;
                        lambda$readRequestsFromCache$4 = com.android.server.companion.datatransfer.SystemDataTransferRequestStore.this.lambda$readRequestsFromCache$4(i);
                        return lambda$readRequestsFromCache$4;
                    }
                }).get(5L, java.util.concurrent.TimeUnit.SECONDS);
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.e(LOG_TAG, "Thread reading SystemDataTransferRequest from disk is interrupted.");
            } catch (java.util.concurrent.ExecutionException e2) {
                android.util.Slog.e(LOG_TAG, "Error occurred while reading SystemDataTransferRequest from disk.");
            } catch (java.util.concurrent.TimeoutException e3) {
                android.util.Slog.e(LOG_TAG, "Reading SystemDataTransferRequest from disk timed out.");
            }
            this.mCachedPerUser.set(i, arrayList);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    /* renamed from: readRequestsFromStore, reason: merged with bridge method [inline-methods] */
    public java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> lambda$readRequestsFromCache$4(int i) {
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        android.util.Slog.i(LOG_TAG, "Reading SystemDataTransferRequests for user " + i + " from file=" + storageFileForUser.getBaseFile().getPath());
        synchronized (storageFileForUser) {
            if (!storageFileForUser.getBaseFile().exists()) {
                android.util.Slog.d(LOG_TAG, "File does not exist -> Abort");
                return new java.util.ArrayList<>();
            }
            try {
                java.io.FileInputStream openRead = storageFileForUser.openRead();
                try {
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                    com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, XML_TAG_REQUESTS);
                    java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromXml = readRequestsFromXml(resolvePullParser, i);
                    if (openRead != null) {
                        openRead.close();
                    }
                    return readRequestsFromXml;
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
                android.util.Slog.e(LOG_TAG, "Error while reading requests file", e);
                return new java.util.ArrayList<>();
            }
        }
    }

    @android.annotation.NonNull
    private java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (!com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, XML_TAG_REQUESTS)) {
            throw new org.xmlpull.v1.XmlPullParserException("The XML doesn't have start tag: requests");
        }
        java.util.ArrayList<android.companion.datatransfer.SystemDataTransferRequest> arrayList = new java.util.ArrayList<>();
        while (true) {
            typedXmlPullParser.nextTag();
            if (!com.android.server.companion.utils.DataStoreUtils.isEndOfTag(typedXmlPullParser, XML_TAG_REQUESTS)) {
                if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, XML_TAG_REQUEST)) {
                    arrayList.add(readRequestFromXml(typedXmlPullParser, i));
                }
            } else {
                return arrayList;
            }
        }
    }

    private android.companion.datatransfer.SystemDataTransferRequest readRequestFromXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (!com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, XML_TAG_REQUEST)) {
            throw new org.xmlpull.v1.XmlPullParserException("XML doesn't have start tag: request");
        }
        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, XML_ATTR_ASSOCIATION_ID);
        int readIntAttribute2 = com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, XML_ATTR_DATA_TYPE);
        boolean readBooleanAttribute = com.android.internal.util.XmlUtils.readBooleanAttribute(typedXmlPullParser, XML_ATTR_IS_USER_CONSENTED);
        switch (readIntAttribute2) {
            case 1:
                android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest = new android.companion.datatransfer.PermissionSyncRequest(readIntAttribute);
                permissionSyncRequest.setUserId(i);
                permissionSyncRequest.setUserConsented(readBooleanAttribute);
                return permissionSyncRequest;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: writeRequestsToStore, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$writeRequest$1(int i, @android.annotation.NonNull final java.util.List<android.companion.datatransfer.SystemDataTransferRequest> list) {
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        android.util.Slog.i(LOG_TAG, "Writing SystemDataTransferRequests for user " + i + " to file=" + storageFileForUser.getBaseFile().getPath());
        synchronized (storageFileForUser) {
            com.android.server.companion.utils.DataStoreUtils.writeToFileSafely(storageFileForUser, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda3
                public final void acceptOrThrow(java.lang.Object obj) {
                    com.android.server.companion.datatransfer.SystemDataTransferRequestStore.this.lambda$writeRequestsToStore$5(list, (java.io.FileOutputStream) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeRequestsToStore$5(java.util.List list, java.io.FileOutputStream fileOutputStream) throws java.lang.Exception {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startDocument((java.lang.String) null, true);
        writeRequestsToXml(resolveSerializer, list);
        resolveSerializer.endDocument();
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.append("System Data Transfer Requests (Cached): ");
                if (this.mCachedPerUser.size() == 0) {
                    printWriter.append("<empty>\n");
                } else {
                    printWriter.append("\n");
                    for (int i = 0; i < this.mCachedPerUser.size(); i++) {
                        int keyAt = this.mCachedPerUser.keyAt(i);
                        java.util.Iterator<android.companion.datatransfer.SystemDataTransferRequest> it = this.mCachedPerUser.get(keyAt).iterator();
                        while (it.hasNext()) {
                            printWriter.append("  u").append((java.lang.CharSequence) java.lang.String.valueOf(keyAt)).append(" -> ").append((java.lang.CharSequence) it.next().toString()).append('\n');
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void writeRequestsToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.Nullable java.util.Collection<android.companion.datatransfer.SystemDataTransferRequest> collection) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_REQUESTS);
        java.util.Iterator<android.companion.datatransfer.SystemDataTransferRequest> it = collection.iterator();
        while (it.hasNext()) {
            writeRequestToXml(typedXmlSerializer, it.next());
        }
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_REQUESTS);
    }

    private void writeRequestToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_REQUEST);
        com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, XML_ATTR_ASSOCIATION_ID, systemDataTransferRequest.getAssociationId());
        com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, XML_ATTR_DATA_TYPE, systemDataTransferRequest.getDataType());
        com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, XML_ATTR_IS_USER_CONSENTED, systemDataTransferRequest.isUserConsented());
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_REQUEST);
    }

    @android.annotation.NonNull
    private android.util.AtomicFile getStorageFileForUser(final int i) {
        return this.mUserIdToStorageFile.computeIfAbsent(java.lang.Integer.valueOf(i), new java.util.function.Function() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.util.AtomicFile lambda$getStorageFileForUser$6;
                lambda$getStorageFileForUser$6 = com.android.server.companion.datatransfer.SystemDataTransferRequestStore.lambda$getStorageFileForUser$6(i, (java.lang.Integer) obj);
                return lambda$getStorageFileForUser$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.AtomicFile lambda$getStorageFileForUser$6(int i, java.lang.Integer num) {
        return com.android.server.companion.utils.DataStoreUtils.createStorageFileForUser(i, FILE_NAME);
    }
}
