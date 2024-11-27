package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class ClientController {
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.inputmethod.ClientState> mClients = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final java.util.List<com.android.server.inputmethod.ClientController.ClientControllerCallback> mCallbacks = new java.util.ArrayList();

    interface ClientControllerCallback {
        void onClientRemoved(com.android.server.inputmethod.ClientState clientState);
    }

    ClientController(android.content.pm.PackageManagerInternal packageManagerInternal) {
        this.mPackageManagerInternal = packageManagerInternal;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    com.android.server.inputmethod.ClientState addClient(final com.android.server.inputmethod.IInputMethodClientInvoker iInputMethodClientInvoker, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i, int i2, int i3) {
        android.os.IBinder.DeathRecipient deathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.inputmethod.ClientController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.inputmethod.ClientController.this.lambda$addClient$0(iInputMethodClientInvoker);
            }
        };
        int size = this.mClients.size();
        for (int i4 = 0; i4 < size; i4++) {
            com.android.server.inputmethod.ClientState valueAt = this.mClients.valueAt(i4);
            if (valueAt.mUid == i2 && valueAt.mPid == i3 && valueAt.mSelfReportedDisplayId == i) {
                throw new java.lang.SecurityException("uid=" + i2 + "/pid=" + i3 + "/displayId=" + i + " is already registered");
            }
        }
        try {
            iInputMethodClientInvoker.asBinder().linkToDeath(deathRecipient, 0);
            com.android.server.inputmethod.ClientState clientState = new com.android.server.inputmethod.ClientState(iInputMethodClientInvoker, iRemoteInputConnection, i2, i3, i, deathRecipient);
            this.mClients.put(iInputMethodClientInvoker.asBinder(), clientState);
            return clientState;
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addClient$0(com.android.server.inputmethod.IInputMethodClientInvoker iInputMethodClientInvoker) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            removeClientAsBinder(iInputMethodClientInvoker.asBinder());
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @com.android.internal.annotations.VisibleForTesting
    boolean removeClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        return removeClientAsBinder(iInputMethodClient.asBinder());
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean removeClientAsBinder(android.os.IBinder iBinder) {
        com.android.server.inputmethod.ClientState remove = this.mClients.remove(iBinder);
        if (remove == null) {
            return false;
        }
        iBinder.unlinkToDeath(remove.mClientDeathRecipient, 0);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onClientRemoved(remove);
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void addClientControllerCallback(com.android.server.inputmethod.ClientController.ClientControllerCallback clientControllerCallback) {
        this.mCallbacks.add(clientControllerCallback);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    com.android.server.inputmethod.ClientState getClient(android.os.IBinder iBinder) {
        return this.mClients.get(iBinder);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void forAllClients(java.util.function.Consumer<com.android.server.inputmethod.ClientState> consumer) {
        for (int i = 0; i < this.mClients.size(); i++) {
            consumer.accept(this.mClients.valueAt(i));
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean verifyClientAndPackageMatch(@android.annotation.NonNull com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, @android.annotation.NonNull java.lang.String str) {
        com.android.server.inputmethod.ClientState clientState = this.mClients.get(iInputMethodClient.asBinder());
        if (clientState == null) {
            throw new java.lang.IllegalArgumentException("unknown client " + iInputMethodClient.asBinder());
        }
        return com.android.server.inputmethod.InputMethodUtils.checkIfPackageBelongsToUid(this.mPackageManagerInternal, clientState.mUid, str);
    }
}
