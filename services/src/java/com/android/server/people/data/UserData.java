package com.android.server.people.data;

/* loaded from: classes2.dex */
class UserData {
    private static final int CONVERSATIONS_END_TOKEN = -1;
    private static final java.lang.String TAG = com.android.server.people.data.UserData.class.getSimpleName();

    @android.annotation.Nullable
    private java.lang.String mDefaultDialer;

    @android.annotation.Nullable
    private java.lang.String mDefaultSmsApp;
    private boolean mIsUnlocked;
    private java.util.Map<java.lang.String, com.android.server.people.data.PackageData> mPackageDataMap = new android.util.ArrayMap();
    private final java.io.File mPerUserPeopleDataDir;
    private final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService;
    private final int mUserId;

    UserData(int i, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        this.mUserId = i;
        this.mPerUserPeopleDataDir = new java.io.File(android.os.Environment.getDataSystemCeDirectory(this.mUserId), "people");
        this.mScheduledExecutorService = scheduledExecutorService;
    }

    int getUserId() {
        return this.mUserId;
    }

    void forAllPackages(@android.annotation.NonNull java.util.function.Consumer<com.android.server.people.data.PackageData> consumer) {
        java.util.Iterator<com.android.server.people.data.PackageData> it = this.mPackageDataMap.values().iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    void setUserUnlocked() {
        this.mIsUnlocked = true;
    }

    void setUserStopped() {
        this.mIsUnlocked = false;
    }

    boolean isUnlocked() {
        return this.mIsUnlocked;
    }

    void loadUserData() {
        this.mPerUserPeopleDataDir.mkdir();
        this.mPackageDataMap.putAll(com.android.server.people.data.PackageData.packagesDataFromDisk(this.mUserId, new com.android.server.people.data.UserData$$ExternalSyntheticLambda0(this), new com.android.server.people.data.UserData$$ExternalSyntheticLambda1(this), this.mScheduledExecutorService, this.mPerUserPeopleDataDir));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.people.data.PackageData lambda$getOrCreatePackageData$0(java.lang.String str, java.lang.String str2) {
        return createPackageData(str);
    }

    @android.annotation.NonNull
    com.android.server.people.data.PackageData getOrCreatePackageData(final java.lang.String str) {
        return this.mPackageDataMap.computeIfAbsent(str, new java.util.function.Function() { // from class: com.android.server.people.data.UserData$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.people.data.PackageData lambda$getOrCreatePackageData$0;
                lambda$getOrCreatePackageData$0 = com.android.server.people.data.UserData.this.lambda$getOrCreatePackageData$0(str, (java.lang.String) obj);
                return lambda$getOrCreatePackageData$0;
            }
        });
    }

    @android.annotation.Nullable
    com.android.server.people.data.PackageData getPackageData(@android.annotation.NonNull java.lang.String str) {
        return this.mPackageDataMap.get(str);
    }

    void deletePackageData(@android.annotation.NonNull java.lang.String str) {
        com.android.server.people.data.PackageData remove = this.mPackageDataMap.remove(str);
        if (remove != null) {
            remove.onDestroy();
        }
    }

    void setDefaultDialer(@android.annotation.Nullable java.lang.String str) {
        this.mDefaultDialer = str;
    }

    @android.annotation.Nullable
    com.android.server.people.data.PackageData getDefaultDialer() {
        if (this.mDefaultDialer != null) {
            return getPackageData(this.mDefaultDialer);
        }
        return null;
    }

    void setDefaultSmsApp(@android.annotation.Nullable java.lang.String str) {
        this.mDefaultSmsApp = str;
    }

    @android.annotation.Nullable
    com.android.server.people.data.PackageData getDefaultSmsApp() {
        if (this.mDefaultSmsApp != null) {
            return getPackageData(this.mDefaultSmsApp);
        }
        return null;
    }

    @android.annotation.Nullable
    byte[] getBackupPayload() {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        for (com.android.server.people.data.PackageData packageData : this.mPackageDataMap.values()) {
            try {
                byte[] backupPayload = packageData.getConversationStore().getBackupPayload();
                dataOutputStream.writeInt(backupPayload.length);
                dataOutputStream.write(backupPayload);
                dataOutputStream.writeUTF(packageData.getPackageName());
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Failed to write conversations to backup payload.", e);
                return null;
            }
        }
        try {
            dataOutputStream.writeInt(-1);
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Failed to write conversations end token to backup payload.", e2);
            return null;
        }
    }

    void restore(@android.annotation.NonNull byte[] bArr) {
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
        try {
            for (int readInt = dataInputStream.readInt(); readInt != -1; readInt = dataInputStream.readInt()) {
                byte[] bArr2 = new byte[readInt];
                dataInputStream.readFully(bArr2, 0, readInt);
                getOrCreatePackageData(dataInputStream.readUTF()).getConversationStore().restore(bArr2);
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to restore conversations from backup payload.", e);
        }
    }

    private com.android.server.people.data.PackageData createPackageData(java.lang.String str) {
        return new com.android.server.people.data.PackageData(str, this.mUserId, new com.android.server.people.data.UserData$$ExternalSyntheticLambda0(this), new com.android.server.people.data.UserData$$ExternalSyntheticLambda1(this), this.mScheduledExecutorService, this.mPerUserPeopleDataDir);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDefaultDialer(java.lang.String str) {
        return android.text.TextUtils.equals(this.mDefaultDialer, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDefaultSmsApp(java.lang.String str) {
        return android.text.TextUtils.equals(this.mDefaultSmsApp, str);
    }
}
