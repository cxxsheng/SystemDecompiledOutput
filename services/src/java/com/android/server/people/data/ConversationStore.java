package com.android.server.people.data;

/* loaded from: classes2.dex */
class ConversationStore {
    private static final java.lang.String CONVERSATIONS_FILE_NAME = "conversations";
    private static final int CONVERSATION_INFOS_END_TOKEN = -1;
    private static final java.lang.String TAG = com.android.server.people.data.ConversationStore.class.getSimpleName();
    private com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter mConversationInfosProtoDiskReadWriter;
    private final java.io.File mPackageDir;
    private final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Map<java.lang.String, com.android.server.people.data.ConversationInfo> mConversationInfoMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Map<android.content.LocusId, java.lang.String> mLocusIdToShortcutIdMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Map<android.net.Uri, java.lang.String> mContactUriToShortcutIdMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Map<java.lang.String, java.lang.String> mPhoneNumberToShortcutIdMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Map<java.lang.String, java.lang.String> mNotifChannelIdToShortcutIdMap = new android.util.ArrayMap();

    ConversationStore(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        this.mScheduledExecutorService = scheduledExecutorService;
        this.mPackageDir = file;
    }

    void loadConversationsFromDisk() {
        java.util.List<com.android.server.people.data.ConversationInfo> read;
        com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter = getConversationInfosProtoDiskReadWriter();
        if (conversationInfosProtoDiskReadWriter == null || (read = conversationInfosProtoDiskReadWriter.read(CONVERSATIONS_FILE_NAME)) == null) {
            return;
        }
        java.util.Iterator<com.android.server.people.data.ConversationInfo> it = read.iterator();
        while (it.hasNext()) {
            updateConversationsInMemory(it.next());
        }
    }

    void saveConversationsToDisk() {
        java.util.ArrayList arrayList;
        com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter = getConversationInfosProtoDiskReadWriter();
        if (conversationInfosProtoDiskReadWriter != null) {
            synchronized (this) {
                arrayList = new java.util.ArrayList(this.mConversationInfoMap.values());
            }
            conversationInfosProtoDiskReadWriter.saveConversationsImmediately(arrayList);
        }
    }

    void addOrUpdate(@android.annotation.NonNull com.android.server.people.data.ConversationInfo conversationInfo) {
        updateConversationsInMemory(conversationInfo);
        scheduleUpdateConversationsOnDisk();
    }

    @android.annotation.Nullable
    com.android.server.people.data.ConversationInfo deleteConversation(@android.annotation.NonNull java.lang.String str) {
        synchronized (this) {
            try {
                com.android.server.people.data.ConversationInfo remove = this.mConversationInfoMap.remove(str);
                if (remove == null) {
                    return null;
                }
                android.content.LocusId locusId = remove.getLocusId();
                if (locusId != null) {
                    this.mLocusIdToShortcutIdMap.remove(locusId);
                }
                android.net.Uri contactUri = remove.getContactUri();
                if (contactUri != null) {
                    this.mContactUriToShortcutIdMap.remove(contactUri);
                }
                java.lang.String contactPhoneNumber = remove.getContactPhoneNumber();
                if (contactPhoneNumber != null) {
                    this.mPhoneNumberToShortcutIdMap.remove(contactPhoneNumber);
                }
                java.lang.String notificationChannelId = remove.getNotificationChannelId();
                if (notificationChannelId != null) {
                    this.mNotifChannelIdToShortcutIdMap.remove(notificationChannelId);
                }
                scheduleUpdateConversationsOnDisk();
                return remove;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void forAllConversations(@android.annotation.NonNull java.util.function.Consumer<com.android.server.people.data.ConversationInfo> consumer) {
        java.util.ArrayList arrayList;
        synchronized (this) {
            arrayList = new java.util.ArrayList(this.mConversationInfoMap.values());
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            consumer.accept((com.android.server.people.data.ConversationInfo) it.next());
        }
    }

    @android.annotation.Nullable
    synchronized com.android.server.people.data.ConversationInfo getConversation(@android.annotation.Nullable java.lang.String str) {
        return str != null ? this.mConversationInfoMap.get(str) : null;
    }

    @android.annotation.Nullable
    synchronized com.android.server.people.data.ConversationInfo getConversationByLocusId(@android.annotation.NonNull android.content.LocusId locusId) {
        return getConversation(this.mLocusIdToShortcutIdMap.get(locusId));
    }

    @android.annotation.Nullable
    synchronized com.android.server.people.data.ConversationInfo getConversationByContactUri(@android.annotation.NonNull android.net.Uri uri) {
        return getConversation(this.mContactUriToShortcutIdMap.get(uri));
    }

    @android.annotation.Nullable
    synchronized com.android.server.people.data.ConversationInfo getConversationByPhoneNumber(@android.annotation.NonNull java.lang.String str) {
        return getConversation(this.mPhoneNumberToShortcutIdMap.get(str));
    }

    @android.annotation.Nullable
    synchronized com.android.server.people.data.ConversationInfo getConversationByNotificationChannelId(@android.annotation.NonNull java.lang.String str) {
        return getConversation(this.mNotifChannelIdToShortcutIdMap.get(str));
    }

    void onDestroy() {
        synchronized (this) {
            this.mConversationInfoMap.clear();
            this.mContactUriToShortcutIdMap.clear();
            this.mLocusIdToShortcutIdMap.clear();
            this.mNotifChannelIdToShortcutIdMap.clear();
            this.mPhoneNumberToShortcutIdMap.clear();
        }
        com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter = getConversationInfosProtoDiskReadWriter();
        if (conversationInfosProtoDiskReadWriter != null) {
            conversationInfosProtoDiskReadWriter.deleteConversationsFile();
        }
    }

    @android.annotation.Nullable
    byte[] getBackupPayload() {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        final java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        forAllConversations(new java.util.function.Consumer() { // from class: com.android.server.people.data.ConversationStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.ConversationStore.lambda$getBackupPayload$0(dataOutputStream, (com.android.server.people.data.ConversationInfo) obj);
            }
        });
        try {
            dataOutputStream.writeInt(-1);
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write conversation infos end token to backup payload.", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getBackupPayload$0(java.io.DataOutputStream dataOutputStream, com.android.server.people.data.ConversationInfo conversationInfo) {
        byte[] backupPayload = conversationInfo.getBackupPayload();
        if (backupPayload == null) {
            return;
        }
        try {
            dataOutputStream.writeInt(backupPayload.length);
            dataOutputStream.write(backupPayload);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write conversation info to backup payload.", e);
        }
    }

    void restore(@android.annotation.NonNull byte[] bArr) {
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
        try {
            for (int readInt = dataInputStream.readInt(); readInt != -1; readInt = dataInputStream.readInt()) {
                byte[] bArr2 = new byte[readInt];
                dataInputStream.readFully(bArr2, 0, readInt);
                com.android.server.people.data.ConversationInfo readFromBackupPayload = com.android.server.people.data.ConversationInfo.readFromBackupPayload(bArr2);
                if (readFromBackupPayload != null) {
                    addOrUpdate(readFromBackupPayload);
                }
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read conversation info from payload.", e);
        }
    }

    private synchronized void updateConversationsInMemory(@android.annotation.NonNull com.android.server.people.data.ConversationInfo conversationInfo) {
        try {
            this.mConversationInfoMap.put(conversationInfo.getShortcutId(), conversationInfo);
            android.content.LocusId locusId = conversationInfo.getLocusId();
            if (locusId != null) {
                this.mLocusIdToShortcutIdMap.put(locusId, conversationInfo.getShortcutId());
            }
            android.net.Uri contactUri = conversationInfo.getContactUri();
            if (contactUri != null) {
                this.mContactUriToShortcutIdMap.put(contactUri, conversationInfo.getShortcutId());
            }
            java.lang.String contactPhoneNumber = conversationInfo.getContactPhoneNumber();
            if (contactPhoneNumber != null) {
                this.mPhoneNumberToShortcutIdMap.put(contactPhoneNumber, conversationInfo.getShortcutId());
            }
            java.lang.String notificationChannelId = conversationInfo.getNotificationChannelId();
            if (notificationChannelId != null) {
                this.mNotifChannelIdToShortcutIdMap.put(notificationChannelId, conversationInfo.getShortcutId());
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void scheduleUpdateConversationsOnDisk() {
        java.util.ArrayList arrayList;
        com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter = getConversationInfosProtoDiskReadWriter();
        if (conversationInfosProtoDiskReadWriter != null) {
            synchronized (this) {
                arrayList = new java.util.ArrayList(this.mConversationInfoMap.values());
            }
            conversationInfosProtoDiskReadWriter.scheduleConversationsSave(arrayList);
        }
    }

    @android.annotation.Nullable
    private com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter getConversationInfosProtoDiskReadWriter() {
        if (!this.mPackageDir.exists()) {
            android.util.Slog.e(TAG, "Package data directory does not exist: " + this.mPackageDir.getAbsolutePath());
            return null;
        }
        if (this.mConversationInfosProtoDiskReadWriter == null) {
            this.mConversationInfosProtoDiskReadWriter = new com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter(this.mPackageDir, CONVERSATIONS_FILE_NAME, this.mScheduledExecutorService);
        }
        return this.mConversationInfosProtoDiskReadWriter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ConversationInfosProtoDiskReadWriter extends com.android.server.people.data.AbstractProtoDiskReadWriter<java.util.List<com.android.server.people.data.ConversationInfo>> {
        private final java.lang.String mConversationInfoFileName;

        ConversationInfosProtoDiskReadWriter(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
            super(file, scheduledExecutorService);
            this.mConversationInfoFileName = str;
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter<java.util.List<com.android.server.people.data.ConversationInfo>> protoStreamWriter() {
            return new com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter() { // from class: com.android.server.people.data.ConversationStore$ConversationInfosProtoDiskReadWriter$$ExternalSyntheticLambda1
                @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter
                public final void write(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.Object obj) {
                    com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter.lambda$protoStreamWriter$0(protoOutputStream, (java.util.List) obj);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$protoStreamWriter$0(android.util.proto.ProtoOutputStream protoOutputStream, java.util.List list) {
            java.util.Iterator it = list.iterator();
            while (it.hasNext()) {
                com.android.server.people.data.ConversationInfo conversationInfo = (com.android.server.people.data.ConversationInfo) it.next();
                long start = protoOutputStream.start(2246267895809L);
                conversationInfo.writeToProto(protoOutputStream);
                protoOutputStream.end(start);
            }
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader<java.util.List<com.android.server.people.data.ConversationInfo>> protoStreamReader() {
            return new com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader() { // from class: com.android.server.people.data.ConversationStore$ConversationInfosProtoDiskReadWriter$$ExternalSyntheticLambda0
                @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader
                public final java.lang.Object read(android.util.proto.ProtoInputStream protoInputStream) {
                    java.util.List lambda$protoStreamReader$1;
                    lambda$protoStreamReader$1 = com.android.server.people.data.ConversationStore.ConversationInfosProtoDiskReadWriter.lambda$protoStreamReader$1(protoInputStream);
                    return lambda$protoStreamReader$1;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.util.List lambda$protoStreamReader$1(android.util.proto.ProtoInputStream protoInputStream) {
            java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
            while (protoInputStream.nextField() != -1) {
                try {
                    if (protoInputStream.getFieldNumber() == 1) {
                        long start = protoInputStream.start(2246267895809L);
                        com.android.server.people.data.ConversationInfo readFromProto = com.android.server.people.data.ConversationInfo.readFromProto(protoInputStream);
                        protoInputStream.end(start);
                        newArrayList.add(readFromProto);
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.people.data.ConversationStore.TAG, "Failed to read protobuf input stream.", e);
                }
            }
            return newArrayList;
        }

        void scheduleConversationsSave(@android.annotation.NonNull java.util.List<com.android.server.people.data.ConversationInfo> list) {
            scheduleSave(this.mConversationInfoFileName, list);
        }

        void saveConversationsImmediately(@android.annotation.NonNull java.util.List<com.android.server.people.data.ConversationInfo> list) {
            saveImmediately(this.mConversationInfoFileName, list);
        }

        void deleteConversationsFile() {
            delete(this.mConversationInfoFileName);
        }
    }
}
