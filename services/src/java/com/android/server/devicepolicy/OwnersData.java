package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class OwnersData {
    private static final java.lang.String ATTR_CAN_ACCESS_DEVICE_IDS = "canAccessDeviceIds";
    private static final java.lang.String ATTR_COMPONENT_NAME = "component";
    private static final java.lang.String ATTR_DEVICE_OWNER_TYPE_VALUE = "value";
    private static final java.lang.String ATTR_FREEZE_RECORD_END = "end";
    private static final java.lang.String ATTR_FREEZE_RECORD_START = "start";
    private static final java.lang.String ATTR_MIGRATED_POST_UPGRADE = "migratedPostUpgrade";
    private static final java.lang.String ATTR_MIGRATED_TO_POLICY_ENGINE = "migratedToPolicyEngine";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_PACKAGE = "package";
    private static final java.lang.String ATTR_PROFILE_OWNER_OF_ORG_OWNED_DEVICE = "isPoOrganizationOwnedDevice";
    private static final java.lang.String ATTR_REMOTE_BUGREPORT_HASH = "remoteBugreportHash";
    private static final java.lang.String ATTR_REMOTE_BUGREPORT_URI = "remoteBugreportUri";
    private static final java.lang.String ATTR_SECURITY_LOG_MIGRATED = "securityLogMigrated";
    private static final java.lang.String ATTR_SIZE = "size";
    private static final java.lang.String ATTR_USERID = "userId";
    private static final boolean DEBUG = false;
    private static final java.lang.String DEVICE_OWNER_XML = "device_owner_2.xml";
    private static final java.lang.String PROFILE_OWNER_XML = "profile_owner.xml";
    private static final java.lang.String TAG = "DevicePolicyManagerService";
    private static final java.lang.String TAG_DEVICE_OWNER = "device-owner";
    private static final java.lang.String TAG_DEVICE_OWNER_CONTEXT = "device-owner-context";
    private static final java.lang.String TAG_DEVICE_OWNER_PROTECTED_PACKAGES = "device-owner-protected-packages";
    private static final java.lang.String TAG_DEVICE_OWNER_TYPE = "device-owner-type";
    private static final java.lang.String TAG_FREEZE_PERIOD_RECORD = "freeze-record";
    private static final java.lang.String TAG_PENDING_OTA_INFO = "pending-ota-info";
    private static final java.lang.String TAG_POLICY_ENGINE_MIGRATION = "policy-engine-migration";
    private static final java.lang.String TAG_PROFILE_OWNER = "profile-owner";
    private static final java.lang.String TAG_ROOT = "root";
    private static final java.lang.String TAG_SYSTEM_UPDATE_POLICY = "system-update-policy";
    com.android.server.devicepolicy.OwnersData.OwnerInfo mDeviceOwner;

    @android.annotation.Nullable
    @java.lang.Deprecated
    android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> mDeviceOwnerProtectedPackages;
    private final com.android.server.devicepolicy.PolicyPathProvider mPathProvider;
    java.time.LocalDate mSystemUpdateFreezeEnd;
    java.time.LocalDate mSystemUpdateFreezeStart;

    @android.annotation.Nullable
    android.app.admin.SystemUpdateInfo mSystemUpdateInfo;
    android.app.admin.SystemUpdatePolicy mSystemUpdatePolicy;
    int mDeviceOwnerUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    final android.util.ArrayMap<java.lang.String, java.lang.Integer> mDeviceOwnerTypes = new android.util.ArrayMap<>();
    final android.util.ArrayMap<java.lang.Integer, com.android.server.devicepolicy.OwnersData.OwnerInfo> mProfileOwners = new android.util.ArrayMap<>();
    boolean mMigratedToPolicyEngine = false;
    boolean mSecurityLoggingMigrated = false;
    boolean mPoliciesMigratedPostUpdate = false;

    OwnersData(com.android.server.devicepolicy.PolicyPathProvider policyPathProvider) {
        this.mPathProvider = policyPathProvider;
    }

    void load(int[] iArr) {
        new com.android.server.devicepolicy.OwnersData.DeviceOwnerReadWriter().readFromFileLocked();
        for (int i : iArr) {
            new com.android.server.devicepolicy.OwnersData.ProfileOwnerReadWriter(i).readFromFileLocked();
        }
        com.android.server.devicepolicy.OwnersData.OwnerInfo ownerInfo = this.mProfileOwners.get(java.lang.Integer.valueOf(this.mDeviceOwnerUserId));
        android.content.ComponentName componentName = ownerInfo != null ? ownerInfo.admin : null;
        if (this.mDeviceOwner != null && componentName != null) {
            android.util.Slog.w(TAG, java.lang.String.format("User %d has both DO and PO, which is not supported", java.lang.Integer.valueOf(this.mDeviceOwnerUserId)));
        }
    }

    boolean writeDeviceOwner() {
        return new com.android.server.devicepolicy.OwnersData.DeviceOwnerReadWriter().writeToFileLocked();
    }

    boolean writeProfileOwner(int i) {
        return new com.android.server.devicepolicy.OwnersData.ProfileOwnerReadWriter(i).writeToFileLocked();
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        boolean z;
        boolean z2 = true;
        if (this.mDeviceOwner == null) {
            z = false;
        } else {
            indentingPrintWriter.println("Device Owner: ");
            indentingPrintWriter.increaseIndent();
            this.mDeviceOwner.dump(indentingPrintWriter);
            indentingPrintWriter.println("User ID: " + this.mDeviceOwnerUserId);
            indentingPrintWriter.decreaseIndent();
            z = true;
        }
        if (this.mSystemUpdatePolicy != null) {
            if (z) {
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("System Update Policy: " + this.mSystemUpdatePolicy);
            z = true;
        }
        if (this.mProfileOwners != null) {
            for (java.util.Map.Entry<java.lang.Integer, com.android.server.devicepolicy.OwnersData.OwnerInfo> entry : this.mProfileOwners.entrySet()) {
                if (z) {
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("Profile Owner (User " + entry.getKey() + "): ");
                indentingPrintWriter.increaseIndent();
                entry.getValue().dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                z = true;
            }
        }
        if (this.mSystemUpdateInfo == null) {
            z2 = z;
        } else {
            if (z) {
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Pending System Update: " + this.mSystemUpdateInfo);
        }
        if (this.mSystemUpdateFreezeStart != null || this.mSystemUpdateFreezeEnd != null) {
            if (z2) {
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("System update freeze record: " + getSystemUpdateFreezePeriodRecordAsString());
        }
    }

    java.lang.String getSystemUpdateFreezePeriodRecordAsString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("start: ");
        if (this.mSystemUpdateFreezeStart != null) {
            sb.append(this.mSystemUpdateFreezeStart.toString());
        } else {
            sb.append("null");
        }
        sb.append("; end: ");
        if (this.mSystemUpdateFreezeEnd != null) {
            sb.append(this.mSystemUpdateFreezeEnd.toString());
        } else {
            sb.append("null");
        }
        return sb.toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getDeviceOwnerFile() {
        return new java.io.File(this.mPathProvider.getDataSystemDirectory(), DEVICE_OWNER_XML);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getProfileOwnerFile(int i) {
        return new java.io.File(this.mPathProvider.getUserSystemDirectory(i), PROFILE_OWNER_XML);
    }

    private static abstract class FileReadWriter {
        private final java.io.File mFile;

        abstract boolean readInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, java.lang.String str);

        abstract boolean shouldWrite();

        abstract void writeInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException;

        protected FileReadWriter(java.io.File file) {
            this.mFile = file;
        }

        boolean writeToFileLocked() {
            if (!shouldWrite()) {
                if (this.mFile.exists() && !this.mFile.delete()) {
                    android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Failed to remove " + this.mFile.getPath());
                }
                return true;
            }
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mFile);
            java.io.FileOutputStream fileOutputStream = null;
            try {
                java.io.FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_ROOT);
                    writeInner(resolveSerializer);
                    resolveSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_ROOT);
                    resolveSerializer.endDocument();
                    resolveSerializer.flush();
                    atomicFile.finishWrite(startWrite);
                    return true;
                } catch (java.io.IOException e) {
                    e = e;
                    fileOutputStream = startWrite;
                    android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Exception when writing", e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                        return false;
                    }
                    return false;
                }
            } catch (java.io.IOException e2) {
                e = e2;
            }
        }

        void readFromFileLocked() {
            if (!this.mFile.exists()) {
                return;
            }
            java.io.FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = new android.util.AtomicFile(this.mFile).openRead();
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    int i = 0;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next != 1) {
                            switch (next) {
                                case 2:
                                    i++;
                                    java.lang.String name = resolvePullParser.getName();
                                    if (i == 1) {
                                        if (!com.android.server.devicepolicy.OwnersData.TAG_ROOT.equals(name)) {
                                            android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Invalid root tag: " + name);
                                            return;
                                        }
                                    } else if (!readInner(resolvePullParser, i, name)) {
                                        return;
                                    }
                                case 3:
                                    i--;
                            }
                        }
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Error parsing owners information file", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    private class DeviceOwnerReadWriter extends com.android.server.devicepolicy.OwnersData.FileReadWriter {
        protected DeviceOwnerReadWriter() {
            super(com.android.server.devicepolicy.OwnersData.this.getDeviceOwnerFile());
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        boolean shouldWrite() {
            return true;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        void writeInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (com.android.server.devicepolicy.OwnersData.this.mDeviceOwner != null) {
                com.android.server.devicepolicy.OwnersData.this.mDeviceOwner.writeToXml(typedXmlSerializer, com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER);
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER_CONTEXT);
                typedXmlSerializer.attributeInt((java.lang.String) null, "userId", com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerUserId);
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER_CONTEXT);
            }
            if (!com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerTypes.isEmpty()) {
                for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerTypes.entrySet()) {
                    typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER_TYPE);
                    typedXmlSerializer.attribute((java.lang.String) null, "package", entry.getKey());
                    typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_DEVICE_OWNER_TYPE_VALUE, entry.getValue().intValue());
                    typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER_TYPE);
                }
            }
            if (com.android.server.devicepolicy.OwnersData.this.mSystemUpdatePolicy != null) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_SYSTEM_UPDATE_POLICY);
                com.android.server.devicepolicy.OwnersData.this.mSystemUpdatePolicy.saveToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_SYSTEM_UPDATE_POLICY);
            }
            if (com.android.server.devicepolicy.OwnersData.this.mSystemUpdateInfo != null) {
                com.android.server.devicepolicy.OwnersData.this.mSystemUpdateInfo.writeToXml(typedXmlSerializer, com.android.server.devicepolicy.OwnersData.TAG_PENDING_OTA_INFO);
            }
            if (com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeStart != null || com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeEnd != null) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_FREEZE_PERIOD_RECORD);
                if (com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeStart != null) {
                    typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_FREEZE_RECORD_START, com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeStart.toString());
                }
                if (com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeEnd != null) {
                    typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_FREEZE_RECORD_END, com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeEnd.toString());
                }
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_FREEZE_PERIOD_RECORD);
            }
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_POLICY_ENGINE_MIGRATION);
            typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_MIGRATED_TO_POLICY_ENGINE, com.android.server.devicepolicy.OwnersData.this.mMigratedToPolicyEngine);
            typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_MIGRATED_POST_UPGRADE, com.android.server.devicepolicy.OwnersData.this.mPoliciesMigratedPostUpdate);
            if (android.app.admin.flags.Flags.securityLogV2Enabled()) {
                typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_SECURITY_LOG_MIGRATED, com.android.server.devicepolicy.OwnersData.this.mSecurityLoggingMigrated);
            }
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.OwnersData.TAG_POLICY_ENGINE_MIGRATION);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0015, code lost:
        
            if (r9.equals(com.android.server.devicepolicy.OwnersData.TAG_SYSTEM_UPDATE_POLICY) != false) goto L33;
         */
        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        boolean readInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, java.lang.String str) {
            char c = 2;
            if (i > 2) {
                return true;
            }
            r2 = false;
            boolean z = false;
            switch (str.hashCode()) {
                case -2101756875:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_PENDING_OTA_INFO)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -2020438916:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1900517026:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER_CONTEXT)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -725102274:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_POLICY_ENGINE_MIGRATION)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -465393379:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER_PROTECTED_PACKAGES)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 544117227:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_DEVICE_OWNER_TYPE)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1303827527:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_FREEZE_PERIOD_RECORD)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1748301720:
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.devicepolicy.OwnersData.this.mDeviceOwner = com.android.server.devicepolicy.OwnersData.OwnerInfo.readFromXml(typedXmlPullParser);
                    com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerUserId = 0;
                    return true;
                case 1:
                    com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerUserId = typedXmlPullParser.getAttributeInt((java.lang.String) null, "userId", com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerUserId);
                    return true;
                case 2:
                    com.android.server.devicepolicy.OwnersData.this.mSystemUpdatePolicy = android.app.admin.SystemUpdatePolicy.restoreFromXml(typedXmlPullParser);
                    return true;
                case 3:
                    com.android.server.devicepolicy.OwnersData.this.mSystemUpdateInfo = android.app.admin.SystemUpdateInfo.readFromXml(typedXmlPullParser);
                    return true;
                case 4:
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_FREEZE_RECORD_START);
                    java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_FREEZE_RECORD_END);
                    if (attributeValue != null && attributeValue2 != null) {
                        com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeStart = java.time.LocalDate.parse(attributeValue);
                        com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeEnd = java.time.LocalDate.parse(attributeValue2);
                        if (com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeStart.isAfter(com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeEnd)) {
                            android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Invalid system update freeze record loaded");
                            com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeStart = null;
                            com.android.server.devicepolicy.OwnersData.this.mSystemUpdateFreezeEnd = null;
                        }
                    }
                    return true;
                case 5:
                    com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerTypes.put(typedXmlPullParser.getAttributeValue((java.lang.String) null, "package"), java.lang.Integer.valueOf(typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_DEVICE_OWNER_TYPE_VALUE, 0)));
                    return true;
                case 6:
                    java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "package");
                    int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_SIZE, 0);
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (int i2 = 0; i2 < attributeInt; i2++) {
                        arrayList.add(typedXmlPullParser.getAttributeValue((java.lang.String) null, "name" + i2));
                    }
                    if (com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerProtectedPackages == null) {
                        com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerProtectedPackages = new android.util.ArrayMap<>();
                    }
                    com.android.server.devicepolicy.OwnersData.this.mDeviceOwnerProtectedPackages.put(attributeValue3, arrayList);
                    return true;
                case 7:
                    com.android.server.devicepolicy.OwnersData.this.mMigratedToPolicyEngine = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_MIGRATED_TO_POLICY_ENGINE, false);
                    com.android.server.devicepolicy.OwnersData.this.mPoliciesMigratedPostUpdate = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_MIGRATED_POST_UPGRADE, false);
                    com.android.server.devicepolicy.OwnersData ownersData = com.android.server.devicepolicy.OwnersData.this;
                    if (android.app.admin.flags.Flags.securityLogV2Enabled() && typedXmlPullParser.getAttributeBoolean((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_SECURITY_LOG_MIGRATED, false)) {
                        z = true;
                    }
                    ownersData.mSecurityLoggingMigrated = z;
                    return true;
                default:
                    android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Unexpected tag: " + str);
                    return false;
            }
        }
    }

    private class ProfileOwnerReadWriter extends com.android.server.devicepolicy.OwnersData.FileReadWriter {
        private final int mUserId;

        ProfileOwnerReadWriter(int i) {
            super(com.android.server.devicepolicy.OwnersData.this.getProfileOwnerFile(i));
            this.mUserId = i;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        boolean shouldWrite() {
            return com.android.server.devicepolicy.OwnersData.this.mProfileOwners.get(java.lang.Integer.valueOf(this.mUserId)) != null;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        void writeInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            com.android.server.devicepolicy.OwnersData.OwnerInfo ownerInfo = com.android.server.devicepolicy.OwnersData.this.mProfileOwners.get(java.lang.Integer.valueOf(this.mUserId));
            if (ownerInfo != null) {
                ownerInfo.writeToXml(typedXmlSerializer, com.android.server.devicepolicy.OwnersData.TAG_PROFILE_OWNER);
            }
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        boolean readInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, java.lang.String str) {
            char c;
            if (i > 2) {
                return true;
            }
            switch (str.hashCode()) {
                case 2145316239:
                    if (str.equals(com.android.server.devicepolicy.OwnersData.TAG_PROFILE_OWNER)) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.devicepolicy.OwnersData.this.mProfileOwners.put(java.lang.Integer.valueOf(this.mUserId), com.android.server.devicepolicy.OwnersData.OwnerInfo.readFromXml(typedXmlPullParser));
                    return true;
                default:
                    android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Unexpected tag: " + str);
                    return false;
            }
        }
    }

    static class OwnerInfo {
        public final android.content.ComponentName admin;
        public boolean isOrganizationOwnedDevice;
        public final java.lang.String packageName;
        public java.lang.String remoteBugreportHash;
        public java.lang.String remoteBugreportUri;

        OwnerInfo(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) {
            this.admin = componentName;
            this.packageName = componentName.getPackageName();
            this.remoteBugreportUri = str;
            this.remoteBugreportHash = str2;
            this.isOrganizationOwnedDevice = z;
        }

        public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, str);
            if (this.admin != null) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_COMPONENT_NAME, this.admin.flattenToString());
            }
            if (this.remoteBugreportUri != null) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_REMOTE_BUGREPORT_URI, this.remoteBugreportUri);
            }
            if (this.remoteBugreportHash != null) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_REMOTE_BUGREPORT_HASH, this.remoteBugreportHash);
            }
            if (this.isOrganizationOwnedDevice) {
                typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_PROFILE_OWNER_OF_ORG_OWNED_DEVICE, this.isOrganizationOwnedDevice);
            }
            typedXmlSerializer.endTag((java.lang.String) null, str);
        }

        public static com.android.server.devicepolicy.OwnersData.OwnerInfo readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_COMPONENT_NAME);
            java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_REMOTE_BUGREPORT_URI);
            java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_REMOTE_BUGREPORT_HASH);
            boolean equals = "true".equals(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_PROFILE_OWNER_OF_ORG_OWNED_DEVICE)) | "true".equals(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.OwnersData.ATTR_CAN_ACCESS_DEVICE_IDS));
            if (attributeValue == null) {
                android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Owner component not found");
                return null;
            }
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(attributeValue);
            if (unflattenFromString == null) {
                android.util.Slog.e(com.android.server.devicepolicy.OwnersData.TAG, "Owner component not parsable: " + attributeValue);
                return null;
            }
            return new com.android.server.devicepolicy.OwnersData.OwnerInfo(unflattenFromString, attributeValue2, attributeValue3, equals);
        }

        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("admin=" + this.admin);
            indentingPrintWriter.println("package=" + this.packageName);
            indentingPrintWriter.println("isOrganizationOwnedDevice=" + this.isOrganizationOwnedDevice);
        }
    }
}
