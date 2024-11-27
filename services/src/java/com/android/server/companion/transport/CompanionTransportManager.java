package com.android.server.companion.transport;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public class CompanionTransportManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CDM_CompanionTransportManager";
    private final com.android.server.companion.AssociationStore mAssociationStore;
    private final android.content.Context mContext;
    private boolean mSecureTransportEnabled = true;

    @com.android.internal.annotations.GuardedBy({"mTransports"})
    private final android.util.SparseArray<com.android.server.companion.transport.Transport> mTransports = new android.util.SparseArray<>();

    @android.annotation.NonNull
    private final android.os.RemoteCallbackList<android.companion.IOnTransportsChangedListener> mTransportsListeners = new android.os.RemoteCallbackList<>();

    @android.annotation.NonNull
    private final android.util.SparseArray<android.companion.IOnMessageReceivedListener> mMessageListeners = new android.util.SparseArray<>();

    public CompanionTransportManager(android.content.Context context, com.android.server.companion.AssociationStore associationStore) {
        this.mContext = context;
        this.mAssociationStore = associationStore;
    }

    public void addListener(int i, @android.annotation.NonNull android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) {
        this.mMessageListeners.put(i, iOnMessageReceivedListener);
        synchronized (this.mTransports) {
            for (int i2 = 0; i2 < this.mTransports.size(); i2++) {
                try {
                    this.mTransports.valueAt(i2).addListener(i, iOnMessageReceivedListener);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void addListener(final android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) {
        android.util.Slog.i(TAG, "Registering OnTransportsChangedListener");
        this.mTransportsListeners.register(iOnTransportsChangedListener);
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mTransports) {
            for (int i = 0; i < this.mTransports.size(); i++) {
                try {
                    android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(this.mTransports.keyAt(i));
                    if (associationById != null) {
                        arrayList.add(associationById);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        this.mTransportsListeners.broadcast(new java.util.function.Consumer() { // from class: com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.companion.transport.CompanionTransportManager.lambda$addListener$0(iOnTransportsChangedListener, arrayList, (android.companion.IOnTransportsChangedListener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addListener$0(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener, java.util.List list, android.companion.IOnTransportsChangedListener iOnTransportsChangedListener2) {
        if (iOnTransportsChangedListener2 == iOnTransportsChangedListener) {
            try {
                iOnTransportsChangedListener.onTransportsChanged(list);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void removeListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) {
        this.mTransportsListeners.unregister(iOnTransportsChangedListener);
    }

    public void removeListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) {
        this.mMessageListeners.remove(i);
    }

    public void sendMessage(int i, byte[] bArr, int[] iArr) {
        android.util.Slog.d(TAG, "Sending message 0x" + java.lang.Integer.toHexString(i) + " data length " + bArr.length);
        synchronized (this.mTransports) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                try {
                    if (this.mTransports.contains(iArr[i2])) {
                        this.mTransports.get(iArr[i2]).sendMessage(i, bArr);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void attachSystemDataTransport(java.lang.String str, int i, int i2, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        synchronized (this.mTransports) {
            try {
                if (this.mTransports.contains(i2)) {
                    detachSystemDataTransport(str, i, i2);
                }
                initializeTransport(i2, parcelFileDescriptor, null);
                notifyOnTransportsChanged();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void detachSystemDataTransport(java.lang.String str, int i, int i2) {
        synchronized (this.mTransports) {
            try {
                com.android.server.companion.transport.Transport transport = this.mTransports.get(i2);
                if (transport != null) {
                    this.mTransports.delete(i2);
                    transport.stop();
                }
                notifyOnTransportsChanged();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void notifyOnTransportsChanged() {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mTransports) {
            for (int i = 0; i < this.mTransports.size(); i++) {
                try {
                    android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(this.mTransports.keyAt(i));
                    if (associationById != null) {
                        arrayList.add(associationById);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        this.mTransportsListeners.broadcast(new java.util.function.Consumer() { // from class: com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.companion.transport.CompanionTransportManager.lambda$notifyOnTransportsChanged$1(arrayList, (android.companion.IOnTransportsChangedListener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyOnTransportsChanged$1(java.util.List list, android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) {
        try {
            iOnTransportsChangedListener.onTransportsChanged(list);
        } catch (android.os.RemoteException e) {
        }
    }

    private void initializeTransport(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        com.android.server.companion.transport.Transport secureTransport;
        android.util.Slog.i(TAG, "Initializing transport");
        if (!isSecureTransportEnabled()) {
            android.util.Slog.i(TAG, "Secure channel is disabled. Creating raw transport");
            secureTransport = new com.android.server.companion.transport.RawTransport(i, parcelFileDescriptor, this.mContext);
        } else if (android.os.Build.isDebuggable()) {
            android.util.Slog.d(TAG, "Creating an unauthenticated secure channel");
            secureTransport = new com.android.server.companion.transport.SecureTransport(i, parcelFileDescriptor, this.mContext, "CDM".getBytes(java.nio.charset.StandardCharsets.UTF_8), null);
        } else if (bArr != null) {
            android.util.Slog.d(TAG, "Creating a PSK-authenticated secure channel");
            secureTransport = new com.android.server.companion.transport.SecureTransport(i, parcelFileDescriptor, this.mContext, bArr, null);
        } else {
            android.util.Slog.d(TAG, "Creating a secure channel");
            secureTransport = new com.android.server.companion.transport.SecureTransport(i, parcelFileDescriptor, this.mContext);
        }
        addMessageListenersToTransport(secureTransport);
        secureTransport.setOnTransportClosedListener(new com.android.server.companion.transport.Transport.OnTransportClosedListener() { // from class: com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda2
            @Override // com.android.server.companion.transport.Transport.OnTransportClosedListener
            public final void onClosed(com.android.server.companion.transport.Transport transport) {
                com.android.server.companion.transport.CompanionTransportManager.this.detachSystemDataTransport(transport);
            }
        });
        secureTransport.start();
        synchronized (this.mTransports) {
            this.mTransports.put(i, secureTransport);
        }
    }

    public java.util.concurrent.Future<?> requestPermissionRestore(int i, byte[] bArr) {
        synchronized (this.mTransports) {
            try {
                com.android.server.companion.transport.Transport transport = this.mTransports.get(i);
                if (transport == null) {
                    return java.util.concurrent.CompletableFuture.failedFuture(new java.io.IOException("Missing transport"));
                }
                return transport.sendMessage(1669491075, bArr);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        synchronized (this.mTransports) {
            try {
                printWriter.append("System Data Transports: ");
                if (this.mTransports.size() == 0) {
                    printWriter.append("<empty>\n");
                } else {
                    printWriter.append("\n");
                    for (int i = 0; i < this.mTransports.size(); i++) {
                        printWriter.append("  ").append((java.lang.CharSequence) this.mTransports.get(this.mTransports.keyAt(i)).toString()).append('\n');
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void enableSecureTransport(boolean z) {
        this.mSecureTransportEnabled = z;
    }

    public com.android.server.companion.transport.CompanionTransportManager.EmulatedTransport createEmulatedTransport(int i) {
        com.android.server.companion.transport.CompanionTransportManager.EmulatedTransport emulatedTransport;
        synchronized (this.mTransports) {
            emulatedTransport = new com.android.server.companion.transport.CompanionTransportManager.EmulatedTransport(i, new android.os.ParcelFileDescriptor(new java.io.FileDescriptor()), this.mContext);
            addMessageListenersToTransport(emulatedTransport);
            this.mTransports.put(i, emulatedTransport);
            notifyOnTransportsChanged();
        }
        return emulatedTransport;
    }

    public static class EmulatedTransport extends com.android.server.companion.transport.RawTransport {
        @Override // com.android.server.companion.transport.RawTransport
        public /* bridge */ /* synthetic */ java.lang.String toString() {
            return super.toString();
        }

        EmulatedTransport(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.Context context) {
            super(i, parcelFileDescriptor, context);
        }

        public void processMessage(int i, int i2, byte[] bArr) throws java.io.IOException {
            handleMessage(i, i2, bArr);
        }

        @Override // com.android.server.companion.transport.RawTransport, com.android.server.companion.transport.Transport
        protected void sendMessage(int i, int i2, @android.annotation.NonNull byte[] bArr) throws java.io.IOException {
            android.util.Slog.e("CDM_CompanionTransport", "Black-holing emulated message type 0x" + java.lang.Integer.toHexString(i) + " sequence " + i2 + " length " + bArr.length + " to association " + this.mAssociationId);
        }
    }

    private boolean isSecureTransportEnabled() {
        return this.mSecureTransportEnabled;
    }

    private void addMessageListenersToTransport(com.android.server.companion.transport.Transport transport) {
        for (int i = 0; i < this.mMessageListeners.size(); i++) {
            transport.addListener(this.mMessageListeners.keyAt(i), this.mMessageListeners.valueAt(i));
        }
    }

    void detachSystemDataTransport(com.android.server.companion.transport.Transport transport) {
        android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(transport.mAssociationId);
        if (associationById != null) {
            detachSystemDataTransport(associationById.getPackageName(), associationById.getUserId(), associationById.getId());
        }
    }
}
