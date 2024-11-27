package android.telecom;

/* loaded from: classes3.dex */
public final class RemoteConference {
    private final java.util.Set<android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback>> mCallbackRecords;
    private final java.util.List<android.telecom.RemoteConnection> mChildConnections;
    private final java.util.List<android.telecom.RemoteConnection> mConferenceableConnections;
    private int mConnectionCapabilities;
    private int mConnectionProperties;
    private final com.android.internal.telecom.IConnectionService mConnectionService;
    private android.telecom.DisconnectCause mDisconnectCause;
    private android.os.Bundle mExtras;
    private final java.lang.String mId;
    private int mState;
    private final java.util.List<android.telecom.RemoteConnection> mUnmodifiableChildConnections;
    private final java.util.List<android.telecom.RemoteConnection> mUnmodifiableConferenceableConnections;

    public static abstract class Callback {
        public void onStateChanged(android.telecom.RemoteConference remoteConference, int i, int i2) {
        }

        public void onDisconnected(android.telecom.RemoteConference remoteConference, android.telecom.DisconnectCause disconnectCause) {
        }

        public void onConnectionAdded(android.telecom.RemoteConference remoteConference, android.telecom.RemoteConnection remoteConnection) {
        }

        public void onConnectionRemoved(android.telecom.RemoteConference remoteConference, android.telecom.RemoteConnection remoteConnection) {
        }

        public void onConnectionCapabilitiesChanged(android.telecom.RemoteConference remoteConference, int i) {
        }

        public void onConnectionPropertiesChanged(android.telecom.RemoteConference remoteConference, int i) {
        }

        public void onConferenceableConnectionsChanged(android.telecom.RemoteConference remoteConference, java.util.List<android.telecom.RemoteConnection> list) {
        }

        public void onDestroyed(android.telecom.RemoteConference remoteConference) {
        }

        public void onExtrasChanged(android.telecom.RemoteConference remoteConference, android.os.Bundle bundle) {
        }
    }

    RemoteConference(java.lang.String str, com.android.internal.telecom.IConnectionService iConnectionService) {
        this.mCallbackRecords = new java.util.concurrent.CopyOnWriteArraySet();
        this.mChildConnections = new java.util.concurrent.CopyOnWriteArrayList();
        this.mUnmodifiableChildConnections = java.util.Collections.unmodifiableList(this.mChildConnections);
        this.mConferenceableConnections = new java.util.ArrayList();
        this.mUnmodifiableConferenceableConnections = java.util.Collections.unmodifiableList(this.mConferenceableConnections);
        this.mState = 1;
        this.mId = str;
        this.mConnectionService = iConnectionService;
    }

    RemoteConference(android.telecom.DisconnectCause disconnectCause) {
        this.mCallbackRecords = new java.util.concurrent.CopyOnWriteArraySet();
        this.mChildConnections = new java.util.concurrent.CopyOnWriteArrayList();
        this.mUnmodifiableChildConnections = java.util.Collections.unmodifiableList(this.mChildConnections);
        this.mConferenceableConnections = new java.util.ArrayList();
        this.mUnmodifiableConferenceableConnections = java.util.Collections.unmodifiableList(this.mConferenceableConnections);
        this.mState = 1;
        this.mId = "NULL";
        this.mConnectionService = null;
        this.mState = 6;
        this.mDisconnectCause = disconnectCause;
    }

    java.lang.String getId() {
        return this.mId;
    }

    void setDestroyed() {
        java.util.Iterator<android.telecom.RemoteConnection> it = this.mChildConnections.iterator();
        while (it.hasNext()) {
            it.next().setConference(null);
        }
        for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.1
                @Override // java.lang.Runnable
                public void run() {
                    callback.onDestroyed(this);
                }
            });
        }
    }

    void setState(final int i) {
        if (i != 4 && i != 5 && i != 6) {
            android.telecom.Log.w(this, "Unsupported state transition for Conference call.", android.telecom.Connection.stateToString(i));
            return;
        }
        if (this.mState != i) {
            final int i2 = this.mState;
            this.mState = i;
            for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.2
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onStateChanged(this, i2, i);
                    }
                });
            }
        }
    }

    void addConnection(final android.telecom.RemoteConnection remoteConnection) {
        if (!this.mChildConnections.contains(remoteConnection)) {
            this.mChildConnections.add(remoteConnection);
            remoteConnection.setConference(this);
            for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.3
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionAdded(this, remoteConnection);
                    }
                });
            }
        }
    }

    void removeConnection(final android.telecom.RemoteConnection remoteConnection) {
        if (this.mChildConnections.contains(remoteConnection)) {
            this.mChildConnections.remove(remoteConnection);
            remoteConnection.setConference(null);
            for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.4
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionRemoved(this, remoteConnection);
                    }
                });
            }
        }
    }

    void setConnectionCapabilities(int i) {
        if (this.mConnectionCapabilities != i) {
            this.mConnectionCapabilities = i;
            for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.5
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionCapabilitiesChanged(this, android.telecom.RemoteConference.this.mConnectionCapabilities);
                    }
                });
            }
        }
    }

    void setConnectionProperties(int i) {
        if (this.mConnectionProperties != i) {
            this.mConnectionProperties = i;
            for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.6
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionPropertiesChanged(this, android.telecom.RemoteConference.this.mConnectionProperties);
                    }
                });
            }
        }
    }

    void setConferenceableConnections(java.util.List<android.telecom.RemoteConnection> list) {
        this.mConferenceableConnections.clear();
        this.mConferenceableConnections.addAll(list);
        for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.7
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConferenceableConnectionsChanged(this, android.telecom.RemoteConference.this.mUnmodifiableConferenceableConnections);
                }
            });
        }
    }

    void setDisconnected(final android.telecom.DisconnectCause disconnectCause) {
        if (this.mState != 6) {
            this.mDisconnectCause = disconnectCause;
            setState(6);
            for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.8
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onDisconnected(this, disconnectCause);
                    }
                });
            }
        }
    }

    void putExtras(android.os.Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putAll(bundle);
        notifyExtrasChanged();
    }

    void removeExtras(java.util.List<java.lang.String> list) {
        if (this.mExtras == null || list == null || list.isEmpty()) {
            return;
        }
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            this.mExtras.remove(it.next());
        }
        notifyExtrasChanged();
    }

    private void notifyExtrasChanged() {
        for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConference.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConference.9
                @Override // java.lang.Runnable
                public void run() {
                    callback.onExtrasChanged(this, android.telecom.RemoteConference.this.mExtras);
                }
            });
        }
    }

    public final java.util.List<android.telecom.RemoteConnection> getConnections() {
        return this.mUnmodifiableChildConnections;
    }

    public final int getState() {
        return this.mState;
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public void disconnect() {
        try {
            this.mConnectionService.disconnect(this.mId, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public void separate(android.telecom.RemoteConnection remoteConnection) {
        if (this.mChildConnections.contains(remoteConnection)) {
            try {
                this.mConnectionService.splitFromConference(remoteConnection.getId(), null);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void merge() {
        try {
            this.mConnectionService.mergeConference(this.mId, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public void swap() {
        try {
            this.mConnectionService.swapConference(this.mId, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public void hold() {
        try {
            this.mConnectionService.hold(this.mId, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public void unhold() {
        try {
            this.mConnectionService.unhold(this.mId, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public android.telecom.DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public void playDtmfTone(char c) {
        try {
            this.mConnectionService.playDtmfTone(this.mId, c, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public void stopDtmfTone() {
        try {
            this.mConnectionService.stopDtmfTone(this.mId, null);
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setAudioState(android.telecom.AudioState audioState) {
        setCallAudioState(new android.telecom.CallAudioState(audioState));
    }

    public void setCallAudioState(android.telecom.CallAudioState callAudioState) {
        try {
            this.mConnectionService.onCallAudioStateChanged(this.mId, callAudioState, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public java.util.List<android.telecom.RemoteConnection> getConferenceableConnections() {
        return this.mUnmodifiableConferenceableConnections;
    }

    public final void registerCallback(android.telecom.RemoteConference.Callback callback) {
        registerCallback(callback, new android.os.Handler());
    }

    public final void registerCallback(android.telecom.RemoteConference.Callback callback, android.os.Handler handler) {
        unregisterCallback(callback);
        if (callback != null && handler != null) {
            this.mCallbackRecords.add(new android.telecom.CallbackRecord<>(callback, handler));
        }
    }

    public final void unregisterCallback(android.telecom.RemoteConference.Callback callback) {
        if (callback != null) {
            for (android.telecom.CallbackRecord<android.telecom.RemoteConference.Callback> callbackRecord : this.mCallbackRecords) {
                if (callbackRecord.getCallback() == callback) {
                    this.mCallbackRecords.remove(callbackRecord);
                    return;
                }
            }
        }
    }

    public static android.telecom.RemoteConference failure(android.telecom.DisconnectCause disconnectCause) {
        return new android.telecom.RemoteConference(disconnectCause);
    }
}
