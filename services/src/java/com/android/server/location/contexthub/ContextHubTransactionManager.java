package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
class ContextHubTransactionManager {
    private static final int MAX_PENDING_REQUESTS = 10000;
    private static final int NUM_TRANSACTION_RECORDS = 20;
    private static final java.lang.String TAG = "ContextHubTransactionManager";
    private final com.android.server.location.contexthub.ContextHubClientManager mClientManager;
    private final com.android.server.location.contexthub.IContextHubWrapper mContextHubProxy;
    private final com.android.server.location.contexthub.NanoAppStateManager mNanoAppStateManager;
    private final java.util.ArrayDeque<com.android.server.location.contexthub.ContextHubServiceTransaction> mTransactionQueue = new java.util.ArrayDeque<>();
    private final java.util.concurrent.atomic.AtomicInteger mNextAvailableId = new java.util.concurrent.atomic.AtomicInteger();
    private final java.util.concurrent.atomic.AtomicInteger mNextAvailableMessageSequenceNumber = new java.util.concurrent.atomic.AtomicInteger();
    private final java.util.concurrent.ScheduledThreadPoolExecutor mTimeoutExecutor = new java.util.concurrent.ScheduledThreadPoolExecutor(1);
    private java.util.concurrent.ScheduledFuture<?> mTimeoutFuture = null;
    private final com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<com.android.server.location.contexthub.ContextHubTransactionManager.TransactionRecord> mTransactionRecordDeque = new com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<>(20);

    private class TransactionRecord {
        private final long mTimestamp = java.lang.System.currentTimeMillis();
        private final java.lang.String mTransaction;

        TransactionRecord(java.lang.String str) {
            this.mTransaction = str;
        }

        public java.lang.String toString() {
            return com.android.server.location.contexthub.ContextHubServiceUtil.formatDateFromTimestamp(this.mTimestamp) + " " + this.mTransaction;
        }
    }

    ContextHubTransactionManager(com.android.server.location.contexthub.IContextHubWrapper iContextHubWrapper, com.android.server.location.contexthub.ContextHubClientManager contextHubClientManager, com.android.server.location.contexthub.NanoAppStateManager nanoAppStateManager) {
        this.mContextHubProxy = iContextHubWrapper;
        this.mClientManager = contextHubClientManager;
        this.mNanoAppStateManager = nanoAppStateManager;
    }

    com.android.server.location.contexthub.ContextHubServiceTransaction createLoadTransaction(final int i, final android.hardware.location.NanoAppBinary nanoAppBinary, final android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, java.lang.String str) {
        return new com.android.server.location.contexthub.ContextHubServiceTransaction(this.mNextAvailableId.getAndIncrement(), 0, nanoAppBinary.getNanoAppId(), str) { // from class: com.android.server.location.contexthub.ContextHubTransactionManager.1
            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            int onTransact() {
                try {
                    return com.android.server.location.contexthub.ContextHubTransactionManager.this.mContextHubProxy.loadNanoapp(i, nanoAppBinary, getTransactionId());
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while trying to load nanoapp with ID 0x" + java.lang.Long.toHexString(nanoAppBinary.getNanoAppId()), e);
                    return 1;
                }
            }

            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            void onTransactionComplete(int i2) {
                com.android.server.location.contexthub.ContextHubStatsLog.write(com.android.server.location.contexthub.ContextHubStatsLog.CHRE_CODE_DOWNLOAD_TRANSACTED, nanoAppBinary.getNanoAppId(), nanoAppBinary.getNanoAppVersion(), 1, com.android.server.location.contexthub.ContextHubTransactionManager.this.toStatsTransactionResult(i2));
                com.android.server.location.contexthub.ContextHubEventLogger.getInstance().logNanoappLoad(i, nanoAppBinary.getNanoAppId(), nanoAppBinary.getNanoAppVersion(), nanoAppBinary.getBinary().length, i2 == 0);
                if (i2 == 0) {
                    com.android.server.location.contexthub.ContextHubTransactionManager.this.mNanoAppStateManager.addNanoAppInstance(i, nanoAppBinary.getNanoAppId(), nanoAppBinary.getNanoAppVersion());
                }
                try {
                    iContextHubTransactionCallback.onTransactionComplete(i2);
                    if (i2 == 0) {
                        com.android.server.location.contexthub.ContextHubTransactionManager.this.mClientManager.onNanoAppLoaded(i, nanoAppBinary.getNanoAppId());
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while calling client onTransactionComplete", e);
                }
            }
        };
    }

    com.android.server.location.contexthub.ContextHubServiceTransaction createUnloadTransaction(final int i, final long j, final android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, java.lang.String str) {
        return new com.android.server.location.contexthub.ContextHubServiceTransaction(this.mNextAvailableId.getAndIncrement(), 1, j, str) { // from class: com.android.server.location.contexthub.ContextHubTransactionManager.2
            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            int onTransact() {
                try {
                    return com.android.server.location.contexthub.ContextHubTransactionManager.this.mContextHubProxy.unloadNanoapp(i, j, getTransactionId());
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while trying to unload nanoapp with ID 0x" + java.lang.Long.toHexString(j), e);
                    return 1;
                }
            }

            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            void onTransactionComplete(int i2) {
                com.android.server.location.contexthub.ContextHubStatsLog.write(com.android.server.location.contexthub.ContextHubStatsLog.CHRE_CODE_DOWNLOAD_TRANSACTED, j, 0, 2, com.android.server.location.contexthub.ContextHubTransactionManager.this.toStatsTransactionResult(i2));
                com.android.server.location.contexthub.ContextHubEventLogger.getInstance().logNanoappUnload(i, j, i2 == 0);
                if (i2 == 0) {
                    com.android.server.location.contexthub.ContextHubTransactionManager.this.mNanoAppStateManager.removeNanoAppInstance(i, j);
                }
                try {
                    iContextHubTransactionCallback.onTransactionComplete(i2);
                    if (i2 == 0) {
                        com.android.server.location.contexthub.ContextHubTransactionManager.this.mClientManager.onNanoAppUnloaded(i, j);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while calling client onTransactionComplete", e);
                }
            }
        };
    }

    com.android.server.location.contexthub.ContextHubServiceTransaction createEnableTransaction(final int i, final long j, final android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, java.lang.String str) {
        return new com.android.server.location.contexthub.ContextHubServiceTransaction(this.mNextAvailableId.getAndIncrement(), 2, str) { // from class: com.android.server.location.contexthub.ContextHubTransactionManager.3
            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            int onTransact() {
                try {
                    return com.android.server.location.contexthub.ContextHubTransactionManager.this.mContextHubProxy.enableNanoapp(i, j, getTransactionId());
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while trying to enable nanoapp with ID 0x" + java.lang.Long.toHexString(j), e);
                    return 1;
                }
            }

            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            void onTransactionComplete(int i2) {
                try {
                    iContextHubTransactionCallback.onTransactionComplete(i2);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while calling client onTransactionComplete", e);
                }
            }
        };
    }

    com.android.server.location.contexthub.ContextHubServiceTransaction createDisableTransaction(final int i, final long j, final android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, java.lang.String str) {
        return new com.android.server.location.contexthub.ContextHubServiceTransaction(this.mNextAvailableId.getAndIncrement(), 3, str) { // from class: com.android.server.location.contexthub.ContextHubTransactionManager.4
            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            int onTransact() {
                try {
                    return com.android.server.location.contexthub.ContextHubTransactionManager.this.mContextHubProxy.disableNanoapp(i, j, getTransactionId());
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while trying to disable nanoapp with ID 0x" + java.lang.Long.toHexString(j), e);
                    return 1;
                }
            }

            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            void onTransactionComplete(int i2) {
                try {
                    iContextHubTransactionCallback.onTransactionComplete(i2);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while calling client onTransactionComplete", e);
                }
            }
        };
    }

    com.android.server.location.contexthub.ContextHubServiceTransaction createMessageTransaction(final short s, final int i, final android.hardware.location.NanoAppMessage nanoAppMessage, final android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, java.lang.String str) {
        return new com.android.server.location.contexthub.ContextHubServiceTransaction(this.mNextAvailableId.getAndIncrement(), 5, str, this.mNextAvailableMessageSequenceNumber.getAndIncrement()) { // from class: com.android.server.location.contexthub.ContextHubTransactionManager.5
            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            int onTransact() {
                try {
                    nanoAppMessage.setIsReliable(true);
                    nanoAppMessage.setMessageSequenceNumber(getMessageSequenceNumber().intValue());
                    return com.android.server.location.contexthub.ContextHubTransactionManager.this.mContextHubProxy.sendMessageToContextHub(s, i, nanoAppMessage);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while trying to send a reliable message", e);
                    return 1;
                }
            }

            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            void onTransactionComplete(int i2) {
                try {
                    iContextHubTransactionCallback.onTransactionComplete(i2);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while calling client onTransactionComplete", e);
                }
            }
        };
    }

    com.android.server.location.contexthub.ContextHubServiceTransaction createQueryTransaction(final int i, final android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, java.lang.String str) {
        return new com.android.server.location.contexthub.ContextHubServiceTransaction(this.mNextAvailableId.getAndIncrement(), 4, str) { // from class: com.android.server.location.contexthub.ContextHubTransactionManager.6
            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            int onTransact() {
                try {
                    return com.android.server.location.contexthub.ContextHubTransactionManager.this.mContextHubProxy.queryNanoapps(i);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while trying to query for nanoapps", e);
                    return 1;
                }
            }

            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            void onTransactionComplete(int i2) {
                onQueryResponse(i2, java.util.Collections.emptyList());
            }

            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
            void onQueryResponse(int i2, java.util.List<android.hardware.location.NanoAppState> list) {
                try {
                    iContextHubTransactionCallback.onQueryResponse(i2, list);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubTransactionManager.TAG, "RemoteException while calling client onQueryComplete", e);
                }
            }
        };
    }

    synchronized void addTransaction(com.android.server.location.contexthub.ContextHubServiceTransaction contextHubServiceTransaction) throws java.lang.IllegalStateException {
        if (this.mTransactionQueue.size() == 10000) {
            throw new java.lang.IllegalStateException("Transaction queue is full (capacity = 10000)");
        }
        this.mTransactionQueue.add(contextHubServiceTransaction);
        this.mTransactionRecordDeque.add(new com.android.server.location.contexthub.ContextHubTransactionManager.TransactionRecord(contextHubServiceTransaction.toString()));
        if (this.mTransactionQueue.size() == 1) {
            startNextTransaction();
        }
    }

    synchronized void onTransactionResponse(int i, boolean z) {
        com.android.server.location.contexthub.ContextHubServiceTransaction peek = this.mTransactionQueue.peek();
        if (peek == null) {
            android.util.Log.w(TAG, "Received unexpected transaction response (no transaction pending)");
            return;
        }
        if (peek.getTransactionId() != i) {
            android.util.Log.w(TAG, "Received unexpected transaction response (expected ID = " + peek.getTransactionId() + ", received ID = " + i + ")");
            return;
        }
        peek.onTransactionComplete(z ? 0 : 5);
        removeTransactionAndStartNext();
    }

    synchronized void onMessageDeliveryResponse(int i, boolean z) {
        com.android.server.location.contexthub.ContextHubServiceTransaction peek = this.mTransactionQueue.peek();
        if (peek == null) {
            android.util.Log.w(TAG, "Received unexpected transaction response (no transaction pending)");
            return;
        }
        java.lang.Integer messageSequenceNumber = peek.getMessageSequenceNumber();
        if (peek.getTransactionType() == 5 && messageSequenceNumber != null && messageSequenceNumber.intValue() == i) {
            peek.onTransactionComplete(z ? 0 : 5);
            removeTransactionAndStartNext();
            return;
        }
        android.util.Log.w(TAG, "Received unexpected message transaction response (expected message sequence number = " + peek.getMessageSequenceNumber() + ", received messageSequenceNumber = " + i + ")");
    }

    synchronized void onQueryResponse(java.util.List<android.hardware.location.NanoAppState> list) {
        com.android.server.location.contexthub.ContextHubServiceTransaction peek = this.mTransactionQueue.peek();
        if (peek == null) {
            android.util.Log.w(TAG, "Received unexpected query response (no transaction pending)");
            return;
        }
        if (peek.getTransactionType() != 4) {
            android.util.Log.w(TAG, "Received unexpected query response (expected " + peek + ")");
            return;
        }
        peek.onQueryResponse(0, list);
        removeTransactionAndStartNext();
    }

    synchronized void onHubReset() {
        if (this.mTransactionQueue.peek() == null) {
            return;
        }
        removeTransactionAndStartNext();
    }

    private void removeTransactionAndStartNext() {
        this.mTimeoutFuture.cancel(false);
        this.mTransactionQueue.remove().setComplete();
        if (!this.mTransactionQueue.isEmpty()) {
            startNextTransaction();
        }
    }

    private void startNextTransaction() {
        int i = 1;
        while (i != 0 && !this.mTransactionQueue.isEmpty()) {
            final com.android.server.location.contexthub.ContextHubServiceTransaction peek = this.mTransactionQueue.peek();
            int onTransact = peek.onTransact();
            if (onTransact == 0) {
                try {
                    this.mTimeoutFuture = this.mTimeoutExecutor.schedule(new java.lang.Runnable() { // from class: com.android.server.location.contexthub.ContextHubTransactionManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.location.contexthub.ContextHubTransactionManager.this.lambda$startNextTransaction$0(peek);
                        }
                    }, peek.getTimeout(java.util.concurrent.TimeUnit.MILLISECONDS), java.util.concurrent.TimeUnit.MILLISECONDS);
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Error when schedule a timer", e);
                }
            } else {
                peek.onTransactionComplete(com.android.server.location.contexthub.ContextHubServiceUtil.toTransactionResult(onTransact));
                this.mTransactionQueue.remove();
            }
            i = onTransact;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startNextTransaction$0(com.android.server.location.contexthub.ContextHubServiceTransaction contextHubServiceTransaction) {
        synchronized (this) {
            try {
                if (!contextHubServiceTransaction.isComplete()) {
                    android.util.Log.d(TAG, contextHubServiceTransaction + " timed out");
                    contextHubServiceTransaction.onTransactionComplete(6);
                    removeTransactionAndStartNext();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toStatsTransactionResult(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
            default:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
        }
    }

    public java.lang.String toString() {
        int i;
        com.android.server.location.contexthub.ContextHubServiceTransaction[] contextHubServiceTransactionArr;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(100);
        synchronized (this) {
            contextHubServiceTransactionArr = (com.android.server.location.contexthub.ContextHubServiceTransaction[]) this.mTransactionQueue.toArray(new com.android.server.location.contexthub.ContextHubServiceTransaction[0]);
        }
        for (i = 0; i < contextHubServiceTransactionArr.length; i++) {
            sb.append(i + ": " + contextHubServiceTransactionArr[i] + "\n");
        }
        sb.append("Transaction History:\n");
        java.util.Iterator<com.android.server.location.contexthub.ContextHubTransactionManager.TransactionRecord> descendingIterator = this.mTransactionRecordDeque.descendingIterator();
        while (descendingIterator.hasNext()) {
            sb.append(descendingIterator.next() + "\n");
        }
        return sb.toString();
    }
}
