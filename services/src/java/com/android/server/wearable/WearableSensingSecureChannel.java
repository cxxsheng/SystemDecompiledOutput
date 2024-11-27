package com.android.server.wearable;

/* loaded from: classes.dex */
final class WearableSensingSecureChannel {
    private static final java.lang.String CDM_ASSOCIATION_DISPLAY_NAME = "PlaceholderDisplayNameFromWSM";
    private static final int READ_BUFFER_SIZE = 8192;
    private static final java.lang.String TAG = com.android.server.wearable.WearableSensingSecureChannel.class.getSimpleName();
    private final android.companion.CompanionDeviceManager mCompanionDeviceManager;
    private final java.io.InputStream mLocalIn;
    private final java.io.OutputStream mLocalOut;
    private final android.os.ParcelFileDescriptor mRemoteFd;
    private final com.android.server.wearable.WearableSensingSecureChannel.SecureTransportListener mSecureTransportListener;
    private final android.os.ParcelFileDescriptor mUnderlyingTransport;
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.wearable.WearableSensingSecureChannel.SoftShutdownExecutor mMessageFromWearableExecutor = new com.android.server.wearable.WearableSensingSecureChannel.SoftShutdownExecutor(java.util.concurrent.Executors.newSingleThreadExecutor());
    private final com.android.server.wearable.WearableSensingSecureChannel.SoftShutdownExecutor mMessageToWearableExecutor = new com.android.server.wearable.WearableSensingSecureChannel.SoftShutdownExecutor(java.util.concurrent.Executors.newSingleThreadExecutor());
    private final com.android.server.wearable.WearableSensingSecureChannel.SoftShutdownExecutor mLightWeightExecutor = new com.android.server.wearable.WearableSensingSecureChannel.SoftShutdownExecutor(java.util.concurrent.Executors.newSingleThreadExecutor());
    private final java.util.concurrent.atomic.AtomicBoolean mTransportAvailable = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.function.Consumer<java.util.List<android.companion.AssociationInfo>> mOnTransportsChangedListener = new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(java.lang.Object obj) {
            com.android.server.wearable.WearableSensingSecureChannel.this.onTransportsChanged((java.util.List) obj);
        }
    };
    private final java.util.function.BiConsumer<java.lang.Integer, byte[]> mOnMessageReceivedListener = new java.util.function.BiConsumer() { // from class: com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda2
        @Override // java.util.function.BiConsumer
        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
            com.android.server.wearable.WearableSensingSecureChannel.this.onMessageReceived(((java.lang.Integer) obj).intValue(), (byte[]) obj2);
        }
    };

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mClosed = false;
    private java.lang.Integer mAssociationId = null;

    interface SecureTransportListener {
        void onError();

        void onSecureTransportAvailable(android.os.ParcelFileDescriptor parcelFileDescriptor);
    }

    static com.android.server.wearable.WearableSensingSecureChannel create(@android.annotation.NonNull android.companion.CompanionDeviceManager companionDeviceManager, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull com.android.server.wearable.WearableSensingSecureChannel.SecureTransportListener secureTransportListener) throws java.io.IOException {
        java.util.Objects.requireNonNull(companionDeviceManager);
        java.util.Objects.requireNonNull(parcelFileDescriptor);
        java.util.Objects.requireNonNull(secureTransportListener);
        android.os.ParcelFileDescriptor[] createSocketPair = android.os.ParcelFileDescriptor.createSocketPair();
        com.android.server.wearable.WearableSensingSecureChannel wearableSensingSecureChannel = new com.android.server.wearable.WearableSensingSecureChannel(companionDeviceManager, parcelFileDescriptor, secureTransportListener, createSocketPair[0], createSocketPair[1]);
        wearableSensingSecureChannel.initialize();
        return wearableSensingSecureChannel;
    }

    private WearableSensingSecureChannel(android.companion.CompanionDeviceManager companionDeviceManager, android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.server.wearable.WearableSensingSecureChannel.SecureTransportListener secureTransportListener, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3) {
        this.mCompanionDeviceManager = companionDeviceManager;
        this.mUnderlyingTransport = parcelFileDescriptor;
        this.mSecureTransportListener = secureTransportListener;
        this.mRemoteFd = parcelFileDescriptor2;
        this.mLocalIn = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor3);
        this.mLocalOut = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor3);
    }

    private void initialize() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.util.Slog.d(TAG, "Requesting CDM association.");
            this.mCompanionDeviceManager.associate(new android.companion.AssociationRequest.Builder().setDisplayName(CDM_ASSOCIATION_DISPLAY_NAME).setSelfManaged(true).build(), this.mLightWeightExecutor, new android.companion.CompanionDeviceManager.Callback() { // from class: com.android.server.wearable.WearableSensingSecureChannel.1
                @Override // android.companion.CompanionDeviceManager.Callback
                public void onAssociationCreated(android.companion.AssociationInfo associationInfo) {
                    com.android.server.wearable.WearableSensingSecureChannel.this.onAssociationCreated(associationInfo.getId());
                }

                @Override // android.companion.CompanionDeviceManager.Callback
                public void onFailure(java.lang.CharSequence charSequence) {
                    android.util.Slog.e(com.android.server.wearable.WearableSensingSecureChannel.TAG, "Failed to create CompanionDeviceManager association: " + ((java.lang.Object) charSequence));
                    com.android.server.wearable.WearableSensingSecureChannel.this.onError();
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAssociationCreated(int i) {
        android.util.Slog.i(TAG, "CDM association created.");
        synchronized (this.mLock) {
            try {
                if (this.mClosed) {
                    return;
                }
                this.mAssociationId = java.lang.Integer.valueOf(i);
                this.mCompanionDeviceManager.addOnMessageReceivedListener(this.mMessageFromWearableExecutor, 1131446919, this.mOnMessageReceivedListener);
                this.mCompanionDeviceManager.addOnTransportsChangedListener(this.mLightWeightExecutor, this.mOnTransportsChangedListener);
                this.mCompanionDeviceManager.attachSystemDataTransport(i, new android.os.ParcelFileDescriptor.AutoCloseInputStream(this.mUnderlyingTransport), new android.os.ParcelFileDescriptor.AutoCloseOutputStream(this.mUnderlyingTransport));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTransportsChanged(java.util.List<android.companion.AssociationInfo> list) {
        synchronized (this.mLock) {
            try {
                if (this.mClosed) {
                    return;
                }
                if (this.mAssociationId == null) {
                    android.util.Slog.e(TAG, "mAssociationId is null when transport changed");
                    return;
                }
                boolean anyMatch = list.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$onTransportsChanged$0;
                        lambda$onTransportsChanged$0 = com.android.server.wearable.WearableSensingSecureChannel.this.lambda$onTransportsChanged$0((android.companion.AssociationInfo) obj);
                        return lambda$onTransportsChanged$0;
                    }
                });
                if (anyMatch && this.mTransportAvailable.compareAndSet(false, true)) {
                    onTransportAvailable();
                } else if (!anyMatch && this.mTransportAvailable.compareAndSet(true, false)) {
                    android.util.Slog.i(TAG, "CDM transport is detached. This is not recoverable.");
                    onError();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onTransportsChanged$0(android.companion.AssociationInfo associationInfo) {
        return associationInfo.getId() == this.mAssociationId.intValue();
    }

    private void onTransportAvailable() {
        android.util.Slog.i(TAG, "Transport available");
        this.mMessageToWearableExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wearable.WearableSensingSecureChannel.this.lambda$onTransportAvailable$1();
            }
        });
        this.mSecureTransportListener.onSecureTransportAvailable(this.mRemoteFd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTransportAvailable$1() {
        int[] iArr = {this.mAssociationId.intValue()};
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                int read = this.mLocalIn.read(bArr);
                if (read != -1) {
                    byte[] bArr2 = new byte[read];
                    java.lang.System.arraycopy(bArr, 0, bArr2, 0, read);
                    android.util.Slog.v(TAG, "Sending message to wearable");
                    this.mCompanionDeviceManager.sendMessage(1132755335, bArr2, iArr);
                } else {
                    android.util.Slog.i(TAG, "Reached EOF when reading from remote stream. Reporting this as an error.");
                    onError();
                    return;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.i(TAG, "IOException while reading from remote stream.");
                onError();
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMessageReceived(int i, byte[] bArr) {
        if (i == this.mAssociationId.intValue()) {
            android.util.Slog.v(TAG, "Received message from wearable.");
            try {
                this.mLocalOut.write(bArr);
                this.mLocalOut.flush();
                return;
            } catch (java.io.IOException e) {
                android.util.Slog.i(TAG, "IOException when writing to remote stream. Closing the secure channel.");
                onError();
                return;
            }
        }
        android.util.Slog.v(TAG, "Received CDM message of type MESSAGE_ONEWAY_FROM_WEARABLE, but it is for another association. Ignoring the message.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError() {
        synchronized (this.mLock) {
            try {
                if (this.mClosed) {
                    return;
                }
                this.mSecureTransportListener.onError();
                close();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void close() {
        synchronized (this.mLock) {
            try {
                if (this.mClosed) {
                    return;
                }
                android.util.Slog.i(TAG, "Closing WearableSensingSecureChannel.");
                this.mClosed = true;
                if (this.mAssociationId != null) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mCompanionDeviceManager.removeOnTransportsChangedListener(this.mOnTransportsChangedListener);
                        this.mCompanionDeviceManager.removeOnMessageReceivedListener(1131446919, this.mOnMessageReceivedListener);
                        this.mCompanionDeviceManager.detachSystemDataTransport(this.mAssociationId.intValue());
                        this.mCompanionDeviceManager.disassociate(this.mAssociationId.intValue());
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                try {
                    this.mLocalIn.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "Encountered IOException when closing local input stream.", e);
                }
                try {
                    this.mLocalOut.close();
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(TAG, "Encountered IOException when closing local output stream.", e2);
                }
                this.mMessageFromWearableExecutor.shutdown();
                this.mMessageToWearableExecutor.shutdown();
                this.mLightWeightExecutor.shutdown();
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    private static class SoftShutdownExecutor implements java.util.concurrent.Executor {
        private final java.util.concurrent.ExecutorService mExecutorService;

        SoftShutdownExecutor(java.util.concurrent.ExecutorService executorService) {
            this.mExecutorService = executorService;
        }

        @Override // java.util.concurrent.Executor
        public void execute(java.lang.Runnable runnable) {
            try {
                this.mExecutorService.execute(runnable);
            } catch (java.util.concurrent.RejectedExecutionException e) {
                android.util.Slog.d(com.android.server.wearable.WearableSensingSecureChannel.TAG, "Received new runnable after shutdown. Ignoring.");
            }
        }

        void shutdown() {
            this.mExecutorService.shutdown();
        }
    }
}
