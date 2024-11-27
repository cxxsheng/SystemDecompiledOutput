package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
class ContextHubClientManager {
    public static final int ACTION_CANCELLED = 2;
    public static final int ACTION_REGISTERED = 0;
    public static final int ACTION_UNREGISTERED = 1;
    private static final boolean DEBUG_LOG_ENABLED = false;
    private static final int MAX_CLIENT_ID = 32767;
    private static final int NUM_CLIENT_RECORDS = 20;
    private static final java.lang.String TAG = "ContextHubClientManager";
    private final android.content.Context mContext;
    private final com.android.server.location.contexthub.IContextHubWrapper mContextHubProxy;
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Short, com.android.server.location.contexthub.ContextHubClientBroker> mHostEndPointIdToClientMap = new java.util.concurrent.ConcurrentHashMap<>();
    private int mNextHostEndPointId = 0;
    private final com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<com.android.server.location.contexthub.ContextHubClientManager.RegistrationRecord> mRegistrationRecordDeque = new com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<>(20);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Action {
    }

    private class RegistrationRecord {
        private final int mAction;
        private final java.lang.String mBroker;
        private final long mTimestamp = java.lang.System.currentTimeMillis();

        RegistrationRecord(java.lang.String str, int i) {
            this.mBroker = str;
            this.mAction = i;
        }

        void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
            protoOutputStream.write(1112396529665L, this.mTimestamp);
            protoOutputStream.write(1120986464258L, this.mAction);
            protoOutputStream.write(1138166333443L, this.mBroker);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(com.android.server.location.contexthub.ContextHubServiceUtil.formatDateFromTimestamp(this.mTimestamp));
            sb.append(" ");
            sb.append(this.mAction == 0 ? "+ " : "- ");
            sb.append(this.mBroker);
            if (this.mAction == 2) {
                sb.append(" (cancelled)");
            }
            return sb.toString();
        }
    }

    ContextHubClientManager(android.content.Context context, com.android.server.location.contexthub.IContextHubWrapper iContextHubWrapper) {
        this.mContext = context;
        this.mContextHubProxy = iContextHubWrapper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [android.os.IBinder, com.android.server.location.contexthub.ContextHubClientBroker, java.lang.Object] */
    android.hardware.location.IContextHubClient registerClient(android.hardware.location.ContextHubInfo contextHubInfo, android.hardware.location.IContextHubClientCallback iContextHubClientCallback, java.lang.String str, com.android.server.location.contexthub.ContextHubTransactionManager contextHubTransactionManager, java.lang.String str2) {
        ?? contextHubClientBroker;
        synchronized (this) {
            short hostEndPointId = getHostEndPointId();
            contextHubClientBroker = new com.android.server.location.contexthub.ContextHubClientBroker(this.mContext, this.mContextHubProxy, this, contextHubInfo, hostEndPointId, iContextHubClientCallback, str, contextHubTransactionManager, str2);
            this.mHostEndPointIdToClientMap.put(java.lang.Short.valueOf(hostEndPointId), contextHubClientBroker);
            this.mRegistrationRecordDeque.add(new com.android.server.location.contexthub.ContextHubClientManager.RegistrationRecord(contextHubClientBroker.toString(), 0));
        }
        try {
            contextHubClientBroker.attachDeathRecipient();
            android.util.Log.d(TAG, "Registered client with host endpoint ID " + ((int) contextHubClientBroker.getHostEndPointId()));
            return android.hardware.location.IContextHubClient.Stub.asInterface((android.os.IBinder) contextHubClientBroker);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to attach death recipient to client");
            contextHubClientBroker.close();
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [android.os.IBinder, com.android.server.location.contexthub.ContextHubClientBroker] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    android.hardware.location.IContextHubClient registerClient(android.hardware.location.ContextHubInfo contextHubInfo, android.app.PendingIntent pendingIntent, long j, java.lang.String str, com.android.server.location.contexthub.ContextHubTransactionManager contextHubTransactionManager) {
        ?? r1;
        java.lang.String str2 = "Regenerated";
        synchronized (this) {
            try {
                com.android.server.location.contexthub.ContextHubClientBroker clientBroker = getClientBroker(contextHubInfo.getId(), pendingIntent, j);
                if (clientBroker == null) {
                    short hostEndPointId = getHostEndPointId();
                    com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker = new com.android.server.location.contexthub.ContextHubClientBroker(this.mContext, this.mContextHubProxy, this, contextHubInfo, hostEndPointId, pendingIntent, j, str, contextHubTransactionManager);
                    this.mHostEndPointIdToClientMap.put(java.lang.Short.valueOf(hostEndPointId), contextHubClientBroker);
                    str2 = "Registered";
                    this.mRegistrationRecordDeque.add(new com.android.server.location.contexthub.ContextHubClientManager.RegistrationRecord(contextHubClientBroker.toString(), 0));
                    r1 = contextHubClientBroker;
                } else {
                    clientBroker.setAttributionTag(str);
                    r1 = clientBroker;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.util.Log.d(TAG, str2 + " client with host endpoint ID " + ((int) r1.getHostEndPointId()));
        return android.hardware.location.IContextHubClient.Stub.asInterface((android.os.IBinder) r1);
    }

    byte onMessageFromNanoApp(int i, short s, android.hardware.location.NanoAppMessage nanoAppMessage, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
        if (nanoAppMessage.isBroadcastMessage()) {
            if (android.chre.flags.Flags.reliableMessageImplementation() && nanoAppMessage.isReliable()) {
                android.util.Log.e(TAG, "Received reliable broadcast message from " + nanoAppMessage.getNanoAppId());
                return (byte) 2;
            }
            if (!list2.isEmpty()) {
                android.util.Log.wtf(TAG, "Received broadcast message with permissions from " + nanoAppMessage.getNanoAppId());
            }
            com.android.server.location.contexthub.ContextHubEventLogger.getInstance().logMessageFromNanoapp(i, nanoAppMessage, true);
            broadcastMessage(i, nanoAppMessage, list, list2);
            return (byte) 0;
        }
        com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker = this.mHostEndPointIdToClientMap.get(java.lang.Short.valueOf(s));
        if (contextHubClientBroker == null) {
            com.android.server.location.contexthub.ContextHubEventLogger.getInstance().logMessageFromNanoapp(i, nanoAppMessage, false);
            android.util.Log.e(TAG, "Cannot send message to unregistered client (host endpoint ID = " + ((int) s) + ")");
            return (byte) 4;
        }
        com.android.server.location.contexthub.ContextHubEventLogger.getInstance().logMessageFromNanoapp(i, nanoAppMessage, true);
        return contextHubClientBroker.sendMessageToClient(nanoAppMessage, list, list2);
    }

    void unregisterClient(short s) {
        com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker = this.mHostEndPointIdToClientMap.get(java.lang.Short.valueOf(s));
        if (contextHubClientBroker != null) {
            this.mRegistrationRecordDeque.add(new com.android.server.location.contexthub.ContextHubClientManager.RegistrationRecord(contextHubClientBroker.toString(), contextHubClientBroker.isPendingIntentCancelled() ? 2 : 1));
        }
        if (this.mHostEndPointIdToClientMap.remove(java.lang.Short.valueOf(s)) != null) {
            android.util.Log.d(TAG, "Unregistered client with host endpoint ID " + ((int) s));
            return;
        }
        android.util.Log.e(TAG, "Cannot unregister non-existing client with host endpoint ID " + ((int) s));
    }

    void onNanoAppLoaded(int i, final long j) {
        forEachClientOfHub(i, new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubClientManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.location.contexthub.ContextHubClientBroker) obj).onNanoAppLoaded(j);
            }
        });
    }

    void onNanoAppUnloaded(int i, final long j) {
        forEachClientOfHub(i, new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubClientManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.location.contexthub.ContextHubClientBroker) obj).onNanoAppUnloaded(j);
            }
        });
    }

    void onHubReset(int i) {
        forEachClientOfHub(i, new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubClientManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.location.contexthub.ContextHubClientBroker) obj).onHubReset();
            }
        });
    }

    void onNanoAppAborted(int i, final long j, final int i2) {
        forEachClientOfHub(i, new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubClientManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.location.contexthub.ContextHubClientBroker) obj).onNanoAppAborted(j, i2);
            }
        });
    }

    void forEachClientOfHub(int i, java.util.function.Consumer<com.android.server.location.contexthub.ContextHubClientBroker> consumer) {
        for (com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker : this.mHostEndPointIdToClientMap.values()) {
            if (contextHubClientBroker.getAttachedContextHubId() == i) {
                consumer.accept(contextHubClientBroker);
            }
        }
    }

    private short getHostEndPointId() {
        if (this.mHostEndPointIdToClientMap.size() == 32768) {
            throw new java.lang.IllegalStateException("Could not register client - max limit exceeded");
        }
        int i = this.mNextHostEndPointId;
        int i2 = 0;
        while (true) {
            if (i2 > MAX_CLIENT_ID) {
                break;
            }
            if (!this.mHostEndPointIdToClientMap.containsKey(java.lang.Short.valueOf((short) i))) {
                this.mNextHostEndPointId = i != MAX_CLIENT_ID ? i + 1 : 0;
            } else {
                i = i == MAX_CLIENT_ID ? 0 : i + 1;
                i2++;
            }
        }
        return (short) i;
    }

    private void broadcastMessage(int i, final android.hardware.location.NanoAppMessage nanoAppMessage, final java.util.List<java.lang.String> list, final java.util.List<java.lang.String> list2) {
        forEachClientOfHub(i, new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubClientManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.location.contexthub.ContextHubClientBroker) obj).sendMessageToClient(nanoAppMessage, list, list2);
            }
        });
    }

    private com.android.server.location.contexthub.ContextHubClientBroker getClientBroker(int i, android.app.PendingIntent pendingIntent, long j) {
        for (com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker : this.mHostEndPointIdToClientMap.values()) {
            if (contextHubClientBroker.hasPendingIntent(pendingIntent, j) && contextHubClientBroker.getAttachedContextHubId() == i) {
                return contextHubClientBroker;
            }
        }
        return null;
    }

    void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        for (com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker : this.mHostEndPointIdToClientMap.values()) {
            long start = protoOutputStream.start(2246267895809L);
            contextHubClientBroker.dump(protoOutputStream);
            protoOutputStream.end(start);
        }
        java.util.Iterator<com.android.server.location.contexthub.ContextHubClientManager.RegistrationRecord> descendingIterator = this.mRegistrationRecordDeque.descendingIterator();
        while (descendingIterator.hasNext()) {
            long start2 = protoOutputStream.start(2246267895810L);
            descendingIterator.next().dump(protoOutputStream);
            protoOutputStream.end(start2);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<com.android.server.location.contexthub.ContextHubClientBroker> it = this.mHostEndPointIdToClientMap.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(java.lang.System.lineSeparator());
        }
        sb.append(java.lang.System.lineSeparator());
        sb.append("Registration History:");
        sb.append(java.lang.System.lineSeparator());
        java.util.Iterator<com.android.server.location.contexthub.ContextHubClientManager.RegistrationRecord> descendingIterator = this.mRegistrationRecordDeque.descendingIterator();
        while (descendingIterator.hasNext()) {
            sb.append(descendingIterator.next());
            sb.append(java.lang.System.lineSeparator());
        }
        return sb.toString();
    }
}
