package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
final class PersistentDataStore {
    private static final int CURRENT_PERSISTENCE_VERSION = 1;
    private static final boolean DEBUG = false;
    private static final java.lang.String FILE_NAME = "companion_device_manager.xml";
    private static final java.lang.String FILE_NAME_LEGACY = "companion_device_manager_associations.xml";
    private static final java.lang.String LEGACY_XML_ATTR_DEVICE = "device";
    private static final java.lang.String TAG = "CompanionDevice_PersistentDataStore";
    private static final java.lang.String XML_ATTR_DISPLAY_NAME = "display_name";
    private static final java.lang.String XML_ATTR_ID = "id";
    private static final java.lang.String XML_ATTR_LAST_TIME_CONNECTED = "last_time_connected";
    private static final java.lang.String XML_ATTR_MAC_ADDRESS = "mac_address";
    private static final java.lang.String XML_ATTR_NOTIFY_DEVICE_NEARBY = "notify_device_nearby";
    private static final java.lang.String XML_ATTR_PACKAGE = "package";
    private static final java.lang.String XML_ATTR_PACKAGE_NAME = "package_name";
    private static final java.lang.String XML_ATTR_PENDING = "pending";
    private static final java.lang.String XML_ATTR_PERSISTENCE_VERSION = "persistence-version";
    private static final java.lang.String XML_ATTR_PROFILE = "profile";
    private static final java.lang.String XML_ATTR_REVOKED = "revoked";
    private static final java.lang.String XML_ATTR_SELF_MANAGED = "self_managed";
    private static final java.lang.String XML_ATTR_SYSTEM_DATA_SYNC_FLAGS = "system_data_sync_flags";
    private static final java.lang.String XML_ATTR_TIME_APPROVED = "time_approved";
    private static final java.lang.String XML_TAG_ASSOCIATION = "association";
    private static final java.lang.String XML_TAG_ASSOCIATIONS = "associations";
    private static final java.lang.String XML_TAG_ID = "id";
    private static final java.lang.String XML_TAG_PACKAGE = "package";
    private static final java.lang.String XML_TAG_PREVIOUSLY_USED_IDS = "previously-used-ids";
    private static final java.lang.String XML_TAG_STATE = "state";
    private static final java.lang.String XML_TAG_TAG = "tag";

    @android.annotation.NonNull
    private final java.util.concurrent.ConcurrentMap<java.lang.Integer, android.util.AtomicFile> mUserIdToStorageFile = new java.util.concurrent.ConcurrentHashMap();

    PersistentDataStore() {
    }

    void readStateForUsers(@android.annotation.NonNull java.util.List<android.content.pm.UserInfo> list, @android.annotation.NonNull java.util.Set<android.companion.AssociationInfo> set, @android.annotation.NonNull android.util.SparseArray<java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>>> sparseArray) {
        java.util.Iterator<android.content.pm.UserInfo> it = list.iterator();
        while (it.hasNext()) {
            int i = it.next().id;
            java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> arrayMap = new android.util.ArrayMap<>();
            java.util.HashSet hashSet = new java.util.HashSet();
            readStateForUser(i, hashSet, arrayMap);
            int firstAssociationIdForUser = com.android.server.companion.CompanionDeviceManagerService.getFirstAssociationIdForUser(i);
            int lastAssociationIdForUser = com.android.server.companion.CompanionDeviceManagerService.getLastAssociationIdForUser(i);
            java.util.Iterator it2 = hashSet.iterator();
            while (it2.hasNext()) {
                int id = ((android.companion.AssociationInfo) it2.next()).getId();
                if (id < firstAssociationIdForUser || id > lastAssociationIdForUser) {
                    android.util.Slog.e(TAG, "Wrong association ID assignment: " + id + ". Association belongs to u" + i + " and thus its ID should be within [" + firstAssociationIdForUser + ", " + lastAssociationIdForUser + "] range.");
                }
            }
            set.addAll(hashSet);
            sparseArray.append(i, arrayMap);
        }
    }

    void readStateForUser(int i, @android.annotation.NonNull java.util.Collection<android.companion.AssociationInfo> collection, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        java.io.File file;
        android.util.AtomicFile atomicFile;
        java.lang.String str;
        android.util.Slog.i(TAG, "Reading associations for user " + i + " from disk");
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            try {
                if (!storageFileForUser.getBaseFile().exists()) {
                    file = getBaseLegacyStorageFileForUser(i);
                    if (!file.exists()) {
                        return;
                    }
                    atomicFile = new android.util.AtomicFile(file);
                    str = XML_TAG_ASSOCIATIONS;
                } else {
                    file = null;
                    atomicFile = storageFileForUser;
                    str = "state";
                }
                int readStateFromFileLocked = readStateFromFileLocked(i, atomicFile, str, collection, map);
                if (file != null || readStateFromFileLocked < 1) {
                    persistStateToFileLocked(storageFileForUser, collection, map);
                    if (file != null) {
                        file.delete();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void persistStateForUser(int i, @android.annotation.NonNull java.util.Collection<android.companion.AssociationInfo> collection, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        android.util.Slog.i(TAG, "Writing associations for user " + i + " to disk");
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            persistStateToFileLocked(storageFileForUser, collection, map);
        }
    }

    private int readStateFromFileLocked(int i, @android.annotation.NonNull android.util.AtomicFile atomicFile, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.Collection<android.companion.AssociationInfo> collection, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        try {
            java.io.FileInputStream openRead = atomicFile.openRead();
            try {
                int readStateFromInputStream = readStateFromInputStream(i, openRead, str, collection, map);
                if (openRead != null) {
                    openRead.close();
                }
                return readStateFromInputStream;
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
            android.util.Slog.e(TAG, "Error while reading associations file", e);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003a, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int readStateFromInputStream(int i, @android.annotation.NonNull java.io.InputStream inputStream, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.Collection<android.companion.AssociationInfo> collection, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, str);
        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, XML_ATTR_PERSISTENCE_VERSION, 0);
        switch (readIntAttribute) {
            case 0:
                readAssociationsV0(resolvePullParser, i, collection);
                break;
            case 1:
                while (true) {
                    resolvePullParser.nextTag();
                    if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(resolvePullParser, XML_TAG_ASSOCIATIONS)) {
                        readAssociationsV1(resolvePullParser, i, collection);
                    } else if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(resolvePullParser, XML_TAG_PREVIOUSLY_USED_IDS)) {
                        readPreviouslyUsedIdsV1(resolvePullParser, map);
                    } else if (com.android.server.companion.utils.DataStoreUtils.isEndOfTag(resolvePullParser, str)) {
                        break;
                    }
                }
        }
    }

    private void persistStateToFileLocked(@android.annotation.NonNull android.util.AtomicFile atomicFile, @android.annotation.Nullable final java.util.Collection<android.companion.AssociationInfo> collection, @android.annotation.NonNull final java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        com.android.server.companion.utils.DataStoreUtils.writeToFileSafely(atomicFile, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.PersistentDataStore$$ExternalSyntheticLambda1
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.companion.PersistentDataStore.lambda$persistStateToFileLocked$0(collection, map, (java.io.FileOutputStream) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$persistStateToFileLocked$0(java.util.Collection collection, java.util.Map map, java.io.FileOutputStream fileOutputStream) throws java.lang.Exception {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startDocument((java.lang.String) null, true);
        resolveSerializer.startTag((java.lang.String) null, "state");
        com.android.internal.util.XmlUtils.writeIntAttribute(resolveSerializer, XML_ATTR_PERSISTENCE_VERSION, 1);
        writeAssociations(resolveSerializer, collection);
        writePreviouslyUsedIds(resolveSerializer, map);
        resolveSerializer.endTag((java.lang.String) null, "state");
        resolveSerializer.endDocument();
    }

    @android.annotation.NonNull
    private android.util.AtomicFile getStorageFileForUser(final int i) {
        return this.mUserIdToStorageFile.computeIfAbsent(java.lang.Integer.valueOf(i), new java.util.function.Function() { // from class: com.android.server.companion.PersistentDataStore$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.util.AtomicFile lambda$getStorageFileForUser$1;
                lambda$getStorageFileForUser$1 = com.android.server.companion.PersistentDataStore.lambda$getStorageFileForUser$1(i, (java.lang.Integer) obj);
                return lambda$getStorageFileForUser$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.AtomicFile lambda$getStorageFileForUser$1(int i, java.lang.Integer num) {
        return com.android.server.companion.utils.DataStoreUtils.createStorageFileForUser(i, FILE_NAME);
    }

    byte[] getBackupPayload(int i) {
        byte[] fileToByteArray;
        android.util.Slog.i(TAG, "Fetching stored state data for user " + i + " from disk");
        android.util.AtomicFile storageFileForUser = getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            fileToByteArray = com.android.server.companion.utils.DataStoreUtils.fileToByteArray(storageFileForUser);
        }
        return fileToByteArray;
    }

    void readStateFromPayload(byte[] bArr, int i, @android.annotation.NonNull java.util.Set<android.companion.AssociationInfo> set, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                readStateFromInputStream(i, byteArrayInputStream, "state", set, map);
                byteArrayInputStream.close();
            } catch (java.lang.Throwable th) {
                try {
                    byteArrayInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Error while reading associations file", e);
        }
    }

    @android.annotation.NonNull
    private static java.io.File getBaseLegacyStorageFileForUser(int i) {
        return new java.io.File(android.os.Environment.getUserSystemDirectory(i), FILE_NAME_LEGACY);
    }

    private static void readAssociationsV0(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, @android.annotation.NonNull java.util.Collection<android.companion.AssociationInfo> collection) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        requireStartOfTag(typedXmlPullParser, XML_TAG_ASSOCIATIONS);
        int firstAssociationIdForUser = com.android.server.companion.CompanionDeviceManagerService.getFirstAssociationIdForUser(i);
        while (true) {
            typedXmlPullParser.nextTag();
            if (!com.android.server.companion.utils.DataStoreUtils.isEndOfTag(typedXmlPullParser, XML_TAG_ASSOCIATIONS)) {
                if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, XML_TAG_ASSOCIATION)) {
                    readAssociationV0(typedXmlPullParser, i, firstAssociationIdForUser, collection);
                    firstAssociationIdForUser++;
                }
            } else {
                return;
            }
        }
    }

    private static void readAssociationV0(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, int i2, @android.annotation.NonNull java.util.Collection<android.companion.AssociationInfo> collection) throws org.xmlpull.v1.XmlPullParserException {
        requireStartOfTag(typedXmlPullParser, XML_TAG_ASSOCIATION);
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, com.android.server.pm.Settings.ATTR_PACKAGE);
        java.lang.String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_TAG_TAG);
        java.lang.String readStringAttribute3 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, LEGACY_XML_ATTR_DEVICE);
        if (readStringAttribute != null && readStringAttribute3 != null) {
            collection.add(new android.companion.AssociationInfo(i2, i, readStringAttribute, readStringAttribute2, android.net.MacAddress.fromString(readStringAttribute3), null, com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_PROFILE), null, false, com.android.internal.util.XmlUtils.readBooleanAttribute(typedXmlPullParser, XML_ATTR_NOTIFY_DEVICE_NEARBY), false, false, com.android.internal.util.XmlUtils.readLongAttribute(typedXmlPullParser, XML_ATTR_TIME_APPROVED, 0L), com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, 0));
        }
    }

    private static void readAssociationsV1(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, @android.annotation.NonNull java.util.Collection<android.companion.AssociationInfo> collection) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        requireStartOfTag(typedXmlPullParser, XML_TAG_ASSOCIATIONS);
        while (true) {
            typedXmlPullParser.nextTag();
            if (!com.android.server.companion.utils.DataStoreUtils.isEndOfTag(typedXmlPullParser, XML_TAG_ASSOCIATIONS)) {
                if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, XML_TAG_ASSOCIATION)) {
                    readAssociationV1(typedXmlPullParser, i, collection);
                }
            } else {
                return;
            }
        }
    }

    private static void readAssociationV1(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, @android.annotation.NonNull java.util.Collection<android.companion.AssociationInfo> collection) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        requireStartOfTag(typedXmlPullParser, XML_TAG_ASSOCIATION);
        android.companion.AssociationInfo createAssociationInfoNoThrow = createAssociationInfoNoThrow(com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, "id"), i, com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, com.android.server.pm.Settings.ATTR_PACKAGE), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_TAG_TAG), stringToMacAddress(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_MAC_ADDRESS)), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_DISPLAY_NAME), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_PROFILE), com.android.internal.util.XmlUtils.readBooleanAttribute(typedXmlPullParser, XML_ATTR_SELF_MANAGED), com.android.internal.util.XmlUtils.readBooleanAttribute(typedXmlPullParser, XML_ATTR_NOTIFY_DEVICE_NEARBY), com.android.internal.util.XmlUtils.readBooleanAttribute(typedXmlPullParser, XML_ATTR_REVOKED, false), com.android.internal.util.XmlUtils.readBooleanAttribute(typedXmlPullParser, XML_ATTR_PENDING, false), com.android.internal.util.XmlUtils.readLongAttribute(typedXmlPullParser, XML_ATTR_TIME_APPROVED, 0L), com.android.internal.util.XmlUtils.readLongAttribute(typedXmlPullParser, XML_ATTR_LAST_TIME_CONNECTED, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME), com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, XML_ATTR_SYSTEM_DATA_SYNC_FLAGS, 0));
        if (createAssociationInfoNoThrow != null) {
            collection.add(createAssociationInfoNoThrow);
        }
    }

    private static void readPreviouslyUsedIdsV1(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        requireStartOfTag(typedXmlPullParser, XML_TAG_PREVIOUSLY_USED_IDS);
        while (true) {
            typedXmlPullParser.nextTag();
            if (!com.android.server.companion.utils.DataStoreUtils.isEndOfTag(typedXmlPullParser, XML_TAG_PREVIOUSLY_USED_IDS)) {
                if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, com.android.server.pm.Settings.ATTR_PACKAGE)) {
                    java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_PACKAGE_NAME);
                    java.util.HashSet hashSet = new java.util.HashSet();
                    while (true) {
                        typedXmlPullParser.nextTag();
                        if (com.android.server.companion.utils.DataStoreUtils.isEndOfTag(typedXmlPullParser, com.android.server.pm.Settings.ATTR_PACKAGE)) {
                            break;
                        } else if (com.android.server.companion.utils.DataStoreUtils.isStartOfTag(typedXmlPullParser, "id")) {
                            typedXmlPullParser.nextToken();
                            hashSet.add(java.lang.Integer.valueOf(java.lang.Integer.parseInt(typedXmlPullParser.getText())));
                        }
                    }
                    map.put(readStringAttribute, hashSet);
                }
            } else {
                return;
            }
        }
    }

    private static void writeAssociations(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer, @android.annotation.Nullable java.util.Collection<android.companion.AssociationInfo> collection) throws java.io.IOException {
        org.xmlpull.v1.XmlSerializer startTag = xmlSerializer.startTag(null, XML_TAG_ASSOCIATIONS);
        java.util.Iterator<android.companion.AssociationInfo> it = collection.iterator();
        while (it.hasNext()) {
            writeAssociation(startTag, it.next());
        }
        startTag.endTag(null, XML_TAG_ASSOCIATIONS);
    }

    private static void writeAssociation(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer, @android.annotation.NonNull android.companion.AssociationInfo associationInfo) throws java.io.IOException {
        org.xmlpull.v1.XmlSerializer startTag = xmlSerializer.startTag(null, XML_TAG_ASSOCIATION);
        com.android.internal.util.XmlUtils.writeIntAttribute(startTag, "id", associationInfo.getId());
        com.android.internal.util.XmlUtils.writeStringAttribute(startTag, XML_ATTR_PROFILE, associationInfo.getDeviceProfile());
        com.android.internal.util.XmlUtils.writeStringAttribute(startTag, com.android.server.pm.Settings.ATTR_PACKAGE, associationInfo.getPackageName());
        com.android.internal.util.XmlUtils.writeStringAttribute(startTag, XML_TAG_TAG, associationInfo.getTag());
        com.android.internal.util.XmlUtils.writeStringAttribute(startTag, XML_ATTR_MAC_ADDRESS, associationInfo.getDeviceMacAddressAsString());
        com.android.internal.util.XmlUtils.writeStringAttribute(startTag, XML_ATTR_DISPLAY_NAME, associationInfo.getDisplayName());
        com.android.internal.util.XmlUtils.writeBooleanAttribute(startTag, XML_ATTR_SELF_MANAGED, associationInfo.isSelfManaged());
        com.android.internal.util.XmlUtils.writeBooleanAttribute(startTag, XML_ATTR_NOTIFY_DEVICE_NEARBY, associationInfo.isNotifyOnDeviceNearby());
        com.android.internal.util.XmlUtils.writeBooleanAttribute(startTag, XML_ATTR_REVOKED, associationInfo.isRevoked());
        com.android.internal.util.XmlUtils.writeBooleanAttribute(startTag, XML_ATTR_PENDING, associationInfo.isPending());
        com.android.internal.util.XmlUtils.writeLongAttribute(startTag, XML_ATTR_TIME_APPROVED, associationInfo.getTimeApprovedMs());
        com.android.internal.util.XmlUtils.writeLongAttribute(startTag, XML_ATTR_LAST_TIME_CONNECTED, associationInfo.getLastTimeConnectedMs());
        com.android.internal.util.XmlUtils.writeIntAttribute(startTag, XML_ATTR_SYSTEM_DATA_SYNC_FLAGS, associationInfo.getSystemDataSyncFlags());
        startTag.endTag(null, XML_TAG_ASSOCIATION);
    }

    private static void writePreviouslyUsedIds(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) throws java.io.IOException {
        org.xmlpull.v1.XmlSerializer startTag = xmlSerializer.startTag(null, XML_TAG_PREVIOUSLY_USED_IDS);
        for (java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.Integer>> entry : map.entrySet()) {
            writePreviouslyUsedIdsForPackage(startTag, entry.getKey(), entry.getValue());
        }
        startTag.endTag(null, XML_TAG_PREVIOUSLY_USED_IDS);
    }

    private static void writePreviouslyUsedIdsForPackage(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.Set<java.lang.Integer> set) throws java.io.IOException {
        final org.xmlpull.v1.XmlSerializer startTag = xmlSerializer.startTag(null, com.android.server.pm.Settings.ATTR_PACKAGE);
        com.android.internal.util.XmlUtils.writeStringAttribute(startTag, XML_ATTR_PACKAGE_NAME, str);
        com.android.internal.util.CollectionUtils.forEach(set, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.PersistentDataStore$$ExternalSyntheticLambda0
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.companion.PersistentDataStore.lambda$writePreviouslyUsedIdsForPackage$2(startTag, (java.lang.Integer) obj);
            }
        });
        startTag.endTag(null, com.android.server.pm.Settings.ATTR_PACKAGE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$writePreviouslyUsedIdsForPackage$2(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.Integer num) throws java.lang.Exception {
        xmlSerializer.startTag(null, "id").text(java.lang.Integer.toString(num.intValue())).endTag(null, "id");
    }

    private static void requireStartOfTag(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, @android.annotation.NonNull java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
        if (!com.android.server.companion.utils.DataStoreUtils.isStartOfTag(xmlPullParser, str)) {
            throw new org.xmlpull.v1.XmlPullParserException("Should be at the start of \"associations\" tag");
        }
    }

    @android.annotation.Nullable
    private static android.net.MacAddress stringToMacAddress(@android.annotation.Nullable java.lang.String str) {
        if (str != null) {
            return android.net.MacAddress.fromString(str);
        }
        return null;
    }

    private static android.companion.AssociationInfo createAssociationInfoNoThrow(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.net.MacAddress macAddress, @android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.Nullable java.lang.String str3, boolean z, boolean z2, boolean z3, boolean z4, long j, long j2, int i3) {
        try {
            return new android.companion.AssociationInfo(i, i2, str, str2, macAddress, charSequence, str3, null, z, z2, z3, z4, j, j2, i3);
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}
