package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class TransferOwnershipMetadataManager {
    static final java.lang.String ADMIN_TYPE_DEVICE_OWNER = "device-owner";
    static final java.lang.String ADMIN_TYPE_PROFILE_OWNER = "profile-owner";
    public static final java.lang.String OWNER_TRANSFER_METADATA_XML = "owner-transfer-metadata.xml";
    private static final java.lang.String TAG = com.android.server.devicepolicy.TransferOwnershipMetadataManager.class.getName();

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG_ADMIN_TYPE = "admin-type";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG_SOURCE_COMPONENT = "source-component";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG_TARGET_COMPONENT = "target-component";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG_USER_ID = "user-id";
    private final com.android.server.devicepolicy.TransferOwnershipMetadataManager.Injector mInjector;

    TransferOwnershipMetadataManager() {
        this(new com.android.server.devicepolicy.TransferOwnershipMetadataManager.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    TransferOwnershipMetadataManager(com.android.server.devicepolicy.TransferOwnershipMetadataManager.Injector injector) {
        this.mInjector = injector;
    }

    boolean saveMetadataFile(com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata metadata) {
        java.io.File file = new java.io.File(this.mInjector.getOwnerTransferMetadataDir(), OWNER_TRANSFER_METADATA_XML);
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(file);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                insertSimpleTag(resolveSerializer, TAG_USER_ID, java.lang.Integer.toString(metadata.userId));
                insertSimpleTag(resolveSerializer, TAG_SOURCE_COMPONENT, metadata.sourceComponent.flattenToString());
                insertSimpleTag(resolveSerializer, TAG_TARGET_COMPONENT, metadata.targetComponent.flattenToString());
                insertSimpleTag(resolveSerializer, TAG_ADMIN_TYPE, metadata.adminType);
                resolveSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
                return true;
            } catch (java.io.IOException e) {
                e = e;
                fileOutputStream = startWrite;
                android.util.Slog.e(TAG, "Caught exception while trying to save Owner Transfer Params to file " + file, e);
                file.delete();
                atomicFile.failWrite(fileOutputStream);
                return false;
            }
        } catch (java.io.IOException e2) {
            e = e2;
        }
    }

    private void insertSimpleTag(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.text(str2);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    @android.annotation.Nullable
    com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata loadMetadataFile() {
        java.io.File file = new java.io.File(this.mInjector.getOwnerTransferMetadataDir(), OWNER_TRANSFER_METADATA_XML);
        if (!file.exists()) {
            return null;
        }
        android.util.Slog.d(TAG, "Loading TransferOwnershipMetadataManager from " + file);
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            try {
                com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata parseMetadataFile = parseMetadataFile(android.util.Xml.resolvePullParser(fileInputStream));
                fileInputStream.close();
                return parseMetadataFile;
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.IllegalArgumentException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Caught exception while trying to load the owner transfer params from file " + file, e);
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0058, code lost:
    
        if (r6.equals(com.android.server.devicepolicy.TransferOwnershipMetadataManager.TAG_TARGET_COMPONENT) != false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata parseMetadataFile(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        java.lang.String str = null;
        int i = 0;
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        while (true) {
            int next = typedXmlPullParser.next();
            char c = 1;
            if (next != 1 && (next != 3 || typedXmlPullParser.getDepth() > depth)) {
                if (next != 3 && next != 4) {
                    java.lang.String name = typedXmlPullParser.getName();
                    switch (name.hashCode()) {
                        case -337219647:
                            break;
                        case -147180963:
                            if (name.equals(TAG_USER_ID)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 281362891:
                            if (name.equals(TAG_SOURCE_COMPONENT)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 641951480:
                            if (name.equals(TAG_ADMIN_TYPE)) {
                                c = 3;
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
                            typedXmlPullParser.next();
                            i = java.lang.Integer.parseInt(typedXmlPullParser.getText());
                            break;
                        case 1:
                            typedXmlPullParser.next();
                            str2 = typedXmlPullParser.getText();
                            break;
                        case 2:
                            typedXmlPullParser.next();
                            str = typedXmlPullParser.getText();
                            break;
                        case 3:
                            typedXmlPullParser.next();
                            str3 = typedXmlPullParser.getText();
                            break;
                    }
                }
            }
        }
        return new com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata(str, str2, i, str3);
    }

    void deleteMetadataFile() {
        new java.io.File(this.mInjector.getOwnerTransferMetadataDir(), OWNER_TRANSFER_METADATA_XML).delete();
    }

    boolean metadataFileExists() {
        return new java.io.File(this.mInjector.getOwnerTransferMetadataDir(), OWNER_TRANSFER_METADATA_XML).exists();
    }

    static class Metadata {
        final java.lang.String adminType;
        final android.content.ComponentName sourceComponent;
        final android.content.ComponentName targetComponent;
        final int userId;

        Metadata(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.content.ComponentName componentName2, @android.annotation.NonNull int i, @android.annotation.NonNull java.lang.String str) {
            this.sourceComponent = componentName;
            this.targetComponent = componentName2;
            java.util.Objects.requireNonNull(componentName);
            java.util.Objects.requireNonNull(componentName2);
            com.android.internal.util.Preconditions.checkStringNotEmpty(str);
            this.userId = i;
            this.adminType = str;
        }

        Metadata(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull int i, @android.annotation.NonNull java.lang.String str3) {
            this(unflattenComponentUnchecked(str), unflattenComponentUnchecked(str2), i, str3);
        }

        private static android.content.ComponentName unflattenComponentUnchecked(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            return android.content.ComponentName.unflattenFromString(str);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata)) {
                return false;
            }
            com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata metadata = (com.android.server.devicepolicy.TransferOwnershipMetadataManager.Metadata) obj;
            return this.userId == metadata.userId && this.sourceComponent.equals(metadata.sourceComponent) && this.targetComponent.equals(metadata.targetComponent) && android.text.TextUtils.equals(this.adminType, metadata.adminType);
        }

        public int hashCode() {
            return ((((((this.userId + 31) * 31) + this.sourceComponent.hashCode()) * 31) + this.targetComponent.hashCode()) * 31) + this.adminType.hashCode();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        public java.io.File getOwnerTransferMetadataDir() {
            return android.os.Environment.getDataSystemDirectory();
        }
    }
}
