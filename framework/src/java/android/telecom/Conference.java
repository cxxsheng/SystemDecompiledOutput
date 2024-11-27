package android.telecom;

/* loaded from: classes3.dex */
public abstract class Conference extends android.telecom.Conferenceable {
    public static final long CONNECT_TIME_NOT_SPECIFIED = 0;
    private android.net.Uri mAddress;
    private int mAddressPresentation;
    private android.telecom.CallAudioState mCallAudioState;
    private int mCallDirection;
    private android.telecom.CallEndpoint mCallEndpoint;
    private java.lang.String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private int mConnectionCapabilities;
    private int mConnectionProperties;
    private android.telecom.DisconnectCause mDisconnectCause;
    private java.lang.String mDisconnectMessage;
    private android.os.Bundle mExtras;
    private android.telecom.PhoneAccountHandle mPhoneAccount;
    private java.util.Set<java.lang.String> mPreviousExtraKeys;
    private android.telecom.StatusHints mStatusHints;
    private java.lang.String mTelecomCallId;
    private final java.util.Set<android.telecom.Conference.Listener> mListeners = new java.util.concurrent.CopyOnWriteArraySet();
    private final java.util.List<android.telecom.Connection> mChildConnections = new java.util.concurrent.CopyOnWriteArrayList();
    private final java.util.List<android.telecom.Connection> mUnmodifiableChildConnections = java.util.Collections.unmodifiableList(this.mChildConnections);
    private final java.util.List<android.telecom.Connection> mConferenceableConnections = new java.util.ArrayList();
    private final java.util.List<android.telecom.Connection> mUnmodifiableConferenceableConnections = java.util.Collections.unmodifiableList(this.mConferenceableConnections);
    private int mState = 1;
    private long mConnectTimeMillis = 0;
    private long mConnectionStartElapsedRealTime = 0;
    private final java.lang.Object mExtrasLock = new java.lang.Object();
    private boolean mRingbackRequested = false;
    private boolean mIsMultiparty = true;
    private final android.telecom.Connection.Listener mConnectionDeathListener = new android.telecom.Connection.Listener() { // from class: android.telecom.Conference.1
        @Override // android.telecom.Connection.Listener
        public void onDestroyed(android.telecom.Connection connection) {
            if (android.telecom.Conference.this.mConferenceableConnections.remove(connection)) {
                android.telecom.Conference.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };

    static abstract class Listener {
        Listener() {
        }

        public void onStateChanged(android.telecom.Conference conference, int i, int i2) {
        }

        public void onDisconnected(android.telecom.Conference conference, android.telecom.DisconnectCause disconnectCause) {
        }

        public void onConnectionAdded(android.telecom.Conference conference, android.telecom.Connection connection) {
        }

        public void onConnectionRemoved(android.telecom.Conference conference, android.telecom.Connection connection) {
        }

        public void onConferenceableConnectionsChanged(android.telecom.Conference conference, java.util.List<android.telecom.Connection> list) {
        }

        public void onDestroyed(android.telecom.Conference conference) {
        }

        public void onConnectionCapabilitiesChanged(android.telecom.Conference conference, int i) {
        }

        public void onConnectionPropertiesChanged(android.telecom.Conference conference, int i) {
        }

        public void onVideoStateChanged(android.telecom.Conference conference, int i) {
        }

        public void onVideoProviderChanged(android.telecom.Conference conference, android.telecom.Connection.VideoProvider videoProvider) {
        }

        public void onStatusHintsChanged(android.telecom.Conference conference, android.telecom.StatusHints statusHints) {
        }

        public void onExtrasChanged(android.telecom.Conference conference, android.os.Bundle bundle) {
        }

        public void onExtrasRemoved(android.telecom.Conference conference, java.util.List<java.lang.String> list) {
        }

        public void onConferenceStateChanged(android.telecom.Conference conference, boolean z) {
        }

        public void onAddressChanged(android.telecom.Conference conference, android.net.Uri uri, int i) {
        }

        public void onConnectionEvent(android.telecom.Conference conference, java.lang.String str, android.os.Bundle bundle) {
        }

        public void onCallerDisplayNameChanged(android.telecom.Conference conference, java.lang.String str, int i) {
        }

        public void onCallDirectionChanged(android.telecom.Conference conference, int i) {
        }

        public void onRingbackRequested(android.telecom.Conference conference, boolean z) {
        }
    }

    public Conference(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        this.mPhoneAccount = phoneAccountHandle;
    }

    @android.annotation.SystemApi
    public final java.lang.String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public final void setTelecomCallId(java.lang.String str) {
        this.mTelecomCallId = str;
    }

    public final android.telecom.PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccount;
    }

    public final java.util.List<android.telecom.Connection> getConnections() {
        return this.mUnmodifiableChildConnections;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final android.telecom.AudioState getAudioState() {
        return new android.telecom.AudioState(this.mCallAudioState);
    }

    @java.lang.Deprecated
    public final android.telecom.CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    public final android.telecom.CallEndpoint getCurrentCallEndpoint() {
        return this.mCallEndpoint;
    }

    public android.telecom.Connection.VideoProvider getVideoProvider() {
        return null;
    }

    public int getVideoState() {
        return 0;
    }

    public void onDisconnect() {
    }

    public void onSeparate(android.telecom.Connection connection) {
    }

    public void onMerge(android.telecom.Connection connection) {
    }

    public void onHold() {
    }

    public void onUnhold() {
    }

    public void onMerge() {
    }

    public void onSwap() {
    }

    public void onPlayDtmfTone(char c) {
    }

    public void onStopDtmfTone() {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onAudioStateChanged(android.telecom.AudioState audioState) {
    }

    @java.lang.Deprecated
    public void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState) {
    }

    public void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint) {
    }

    public void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list) {
    }

    public void onMuteStateChanged(boolean z) {
    }

    public void onConnectionAdded(android.telecom.Connection connection) {
    }

    public void onAddConferenceParticipants(java.util.List<android.net.Uri> list) {
    }

    public void onAnswer(int i) {
    }

    public final void onAnswer() {
        onAnswer(0);
    }

    public void onReject() {
    }

    public final void setOnHold() {
        setState(5);
    }

    public final void setDialing() {
        setState(3);
    }

    public final void setRinging() {
        setState(2);
    }

    public final void setActive() {
        setRingbackRequested(false);
        setState(4);
    }

    public final void setDisconnected(android.telecom.DisconnectCause disconnectCause) {
        this.mDisconnectCause = disconnectCause;
        setState(6);
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDisconnected(this, this.mDisconnectCause);
        }
    }

    public final android.telecom.DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final void setConnectionCapabilities(int i) {
        if (i != this.mConnectionCapabilities) {
            this.mConnectionCapabilities = i;
            java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionCapabilitiesChanged(this, this.mConnectionCapabilities);
            }
        }
    }

    public final void setConnectionProperties(int i) {
        if (i != this.mConnectionProperties) {
            this.mConnectionProperties = i;
            java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionPropertiesChanged(this, this.mConnectionProperties);
            }
        }
    }

    public final boolean addConnection(android.telecom.Connection connection) {
        android.telecom.Log.d(this, "Connection=%s, connection=", connection);
        if (connection != null && !this.mChildConnections.contains(connection) && connection.setConference(this)) {
            this.mChildConnections.add(connection);
            onConnectionAdded(connection);
            java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionAdded(this, connection);
            }
            return true;
        }
        return false;
    }

    public final void removeConnection(android.telecom.Connection connection) {
        android.telecom.Log.d(this, "removing %s from %s", connection, this.mChildConnections);
        if (connection != null && this.mChildConnections.remove(connection)) {
            connection.resetConference();
            java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionRemoved(this, connection);
            }
        }
    }

    public final void setConferenceableConnections(java.util.List<android.telecom.Connection> list) {
        clearConferenceableList();
        for (android.telecom.Connection connection : list) {
            if (!this.mConferenceableConnections.contains(connection)) {
                connection.addConnectionListener(this.mConnectionDeathListener);
                this.mConferenceableConnections.add(connection);
            }
        }
        fireOnConferenceableConnectionsChanged();
    }

    public final void setRingbackRequested(boolean z) {
        if (this.mRingbackRequested != z) {
            this.mRingbackRequested = z;
            java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onRingbackRequested(this, z);
            }
        }
    }

    public final void setVideoState(android.telecom.Connection connection, int i) {
        android.telecom.Log.d(this, "setVideoState Conference: %s Connection: %s VideoState: %s", this, connection, java.lang.Integer.valueOf(i));
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoStateChanged(this, i);
        }
    }

    public final void setVideoProvider(android.telecom.Connection connection, android.telecom.Connection.VideoProvider videoProvider) {
        android.telecom.Log.d(this, "setVideoProvider Conference: %s Connection: %s VideoState: %s", this, connection, videoProvider);
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoProviderChanged(this, videoProvider);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fireOnConferenceableConnectionsChanged() {
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceableConnectionsChanged(this, getConferenceableConnections());
        }
    }

    public final java.util.List<android.telecom.Connection> getConferenceableConnections() {
        return this.mUnmodifiableConferenceableConnections;
    }

    public final void destroy() {
        android.telecom.Log.d(this, "destroying conference : %s", this);
        for (android.telecom.Connection connection : this.mChildConnections) {
            android.telecom.Log.d(this, "removing connection %s", connection);
            removeConnection(connection);
        }
        if (this.mState != 6) {
            android.telecom.Log.d(this, "setting to disconnected", new java.lang.Object[0]);
            setDisconnected(new android.telecom.DisconnectCause(2));
        }
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDestroyed(this);
        }
    }

    final android.telecom.Conference addListener(android.telecom.Conference.Listener listener) {
        this.mListeners.add(listener);
        return this;
    }

    final android.telecom.Conference removeListener(android.telecom.Conference.Listener listener) {
        this.mListeners.remove(listener);
        return this;
    }

    @android.annotation.SystemApi
    public android.telecom.Connection getPrimaryConnection() {
        if (this.mUnmodifiableChildConnections == null || this.mUnmodifiableChildConnections.isEmpty()) {
            return null;
        }
        return this.mUnmodifiableChildConnections.get(0);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final void setConnectTimeMillis(long j) {
        setConnectionTime(j);
    }

    public final void setConnectionTime(long j) {
        this.mConnectTimeMillis = j;
    }

    @java.lang.Deprecated
    public final void setConnectionStartElapsedRealTime(long j) {
        setConnectionStartElapsedRealtimeMillis(j);
    }

    public final void setConnectionStartElapsedRealtimeMillis(long j) {
        this.mConnectionStartElapsedRealTime = j;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final long getConnectTimeMillis() {
        return getConnectionTime();
    }

    public final long getConnectionTime() {
        return this.mConnectTimeMillis;
    }

    public final long getConnectionStartElapsedRealtimeMillis() {
        return this.mConnectionStartElapsedRealTime;
    }

    final void setCallAudioState(android.telecom.CallAudioState callAudioState) {
        android.telecom.Log.d(this, "setCallAudioState %s", callAudioState);
        this.mCallAudioState = callAudioState;
        onAudioStateChanged(getAudioState());
        onCallAudioStateChanged(callAudioState);
    }

    final void setCallEndpoint(android.telecom.CallEndpoint callEndpoint) {
        android.telecom.Log.d(this, "setCallEndpoint %s", callEndpoint);
        this.mCallEndpoint = callEndpoint;
        onCallEndpointChanged(callEndpoint);
    }

    final void setAvailableCallEndpoints(java.util.List<android.telecom.CallEndpoint> list) {
        android.telecom.Log.d(this, "setAvailableCallEndpoints", new java.lang.Object[0]);
        onAvailableCallEndpointsChanged(list);
    }

    final void setMuteState(boolean z) {
        android.telecom.Log.d(this, "setMuteState %s", java.lang.Boolean.valueOf(z));
        onMuteStateChanged(z);
    }

    private void setState(int i) {
        if (this.mState != i) {
            int i2 = this.mState;
            this.mState = i;
            java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onStateChanged(this, i2, i);
            }
        }
    }

    private static class FailureSignalingConference extends android.telecom.Conference {
        private boolean mImmutable;

        public FailureSignalingConference(android.telecom.DisconnectCause disconnectCause, android.telecom.PhoneAccountHandle phoneAccountHandle) {
            super(phoneAccountHandle);
            this.mImmutable = false;
            setDisconnected(disconnectCause);
            this.mImmutable = true;
        }

        public void checkImmutable() {
            if (this.mImmutable) {
                throw new java.lang.UnsupportedOperationException("Conference is immutable");
            }
        }
    }

    public static android.telecom.Conference createFailedConference(android.telecom.DisconnectCause disconnectCause, android.telecom.PhoneAccountHandle phoneAccountHandle) {
        return new android.telecom.Conference.FailureSignalingConference(disconnectCause, phoneAccountHandle);
    }

    private final void clearConferenceableList() {
        java.util.Iterator<android.telecom.Connection> it = this.mConferenceableConnections.iterator();
        while (it.hasNext()) {
            it.next().removeConnectionListener(this.mConnectionDeathListener);
        }
        this.mConferenceableConnections.clear();
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "[State: %s,Capabilites: %s, VideoState: %s, VideoProvider: %s,isRingbackRequested: %s, ThisObject %s]", android.telecom.Connection.stateToString(this.mState), android.telecom.Call.Details.capabilitiesToString(this.mConnectionCapabilities), java.lang.Integer.valueOf(getVideoState()), getVideoProvider(), isRingbackRequested() ? android.hardware.gnss.GnssSignalType.CODE_TYPE_Y : android.hardware.gnss.GnssSignalType.CODE_TYPE_N, super.toString());
    }

    public final void setStatusHints(android.telecom.StatusHints statusHints) {
        this.mStatusHints = statusHints;
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onStatusHintsChanged(this, statusHints);
        }
    }

    public final android.telecom.StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final void setExtras(android.os.Bundle bundle) {
        synchronized (this.mExtrasLock) {
            putExtras(bundle);
            if (this.mPreviousExtraKeys != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str : this.mPreviousExtraKeys) {
                    if (bundle == null || !bundle.containsKey(str)) {
                        arrayList.add(str);
                    }
                }
                if (!arrayList.isEmpty()) {
                    removeExtras(arrayList);
                }
            }
            if (this.mPreviousExtraKeys == null) {
                this.mPreviousExtraKeys = new android.util.ArraySet();
            }
            this.mPreviousExtraKeys.clear();
            if (bundle != null) {
                this.mPreviousExtraKeys.addAll(bundle.keySet());
            }
        }
    }

    public final void putExtras(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        if (bundle == null) {
            return;
        }
        synchronized (this.mExtrasLock) {
            if (this.mExtras == null) {
                this.mExtras = new android.os.Bundle();
            }
            this.mExtras.putAll(bundle);
            bundle2 = new android.os.Bundle(this.mExtras);
        }
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onExtrasChanged(this, new android.os.Bundle(bundle2));
        }
    }

    public final void putExtra(java.lang.String str, boolean z) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean(str, z);
        putExtras(bundle);
    }

    public final void putExtra(java.lang.String str, int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt(str, i);
        putExtras(bundle);
    }

    public final void putExtra(java.lang.String str, java.lang.String str2) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(str, str2);
        putExtras(bundle);
    }

    public final void removeExtras(java.util.List<java.lang.String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        synchronized (this.mExtrasLock) {
            if (this.mExtras != null) {
                java.util.Iterator<java.lang.String> it = list.iterator();
                while (it.hasNext()) {
                    this.mExtras.remove(it.next());
                }
            }
        }
        java.util.List<java.lang.String> unmodifiableList = java.util.Collections.unmodifiableList(list);
        java.util.Iterator<android.telecom.Conference.Listener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onExtrasRemoved(this, unmodifiableList);
        }
    }

    public final void removeExtras(java.lang.String... strArr) {
        removeExtras(java.util.Arrays.asList(strArr));
    }

    public final android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public void onExtrasChanged(android.os.Bundle bundle) {
    }

    @android.annotation.SystemApi
    public void setConferenceState(boolean z) {
        this.mIsMultiparty = z;
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceStateChanged(this, z);
        }
    }

    public final void setCallDirection(int i) {
        android.telecom.Log.d(this, "setDirection %d", java.lang.Integer.valueOf(i));
        this.mCallDirection = i;
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallDirectionChanged(this, i);
        }
    }

    public boolean isMultiparty() {
        return this.mIsMultiparty;
    }

    @android.annotation.SystemApi
    public final void setAddress(android.net.Uri uri, int i) {
        android.telecom.Log.d(this, "setAddress %s", uri);
        this.mAddress = uri;
        this.mAddressPresentation = i;
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAddressChanged(this, uri, i);
        }
    }

    public final android.net.Uri getAddress() {
        return this.mAddress;
    }

    public final int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public final java.lang.String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public final int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public final int getCallDirection() {
        return this.mCallDirection;
    }

    @android.annotation.SystemApi
    public final void setCallerDisplayName(java.lang.String str, int i) {
        android.telecom.Log.d(this, "setCallerDisplayName %s", str);
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i;
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallerDisplayNameChanged(this, str, i);
        }
    }

    final void handleExtrasChanged(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        synchronized (this.mExtrasLock) {
            this.mExtras = bundle;
            if (this.mExtras == null) {
                bundle2 = null;
            } else {
                bundle2 = new android.os.Bundle(this.mExtras);
            }
        }
        onExtrasChanged(bundle2);
    }

    public void sendConferenceEvent(java.lang.String str, android.os.Bundle bundle) {
        java.util.Iterator<android.telecom.Conference.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionEvent(this, str, bundle);
        }
    }
}
