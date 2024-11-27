package android.telecom;

/* loaded from: classes3.dex */
public final class RemoteConnection {
    private android.net.Uri mAddress;
    private int mAddressPresentation;
    private final java.util.Set<android.telecom.RemoteConnection.CallbackRecord> mCallbackRecords;
    private java.lang.String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private java.lang.String mCallingPackageAbbreviation;
    private android.telecom.RemoteConference mConference;
    private final java.util.List<android.telecom.RemoteConnection> mConferenceableConnections;
    private boolean mConnected;
    private int mConnectionCapabilities;
    private final java.lang.String mConnectionId;
    private int mConnectionProperties;
    private com.android.internal.telecom.IConnectionService mConnectionService;
    private android.telecom.DisconnectCause mDisconnectCause;
    private android.os.Bundle mExtras;
    private boolean mIsVoipAudioMode;
    private boolean mRingbackRequested;
    private int mState;
    private android.telecom.StatusHints mStatusHints;
    private final java.util.List<android.telecom.RemoteConnection> mUnmodifiableconferenceableConnections;
    private android.telecom.RemoteConnection.VideoProvider mVideoProvider;
    private int mVideoState;

    public static abstract class Callback {
        public void onStateChanged(android.telecom.RemoteConnection remoteConnection, int i) {
        }

        public void onDisconnected(android.telecom.RemoteConnection remoteConnection, android.telecom.DisconnectCause disconnectCause) {
        }

        public void onRingbackRequested(android.telecom.RemoteConnection remoteConnection, boolean z) {
        }

        public void onConnectionCapabilitiesChanged(android.telecom.RemoteConnection remoteConnection, int i) {
        }

        public void onConnectionPropertiesChanged(android.telecom.RemoteConnection remoteConnection, int i) {
        }

        public void onPostDialWait(android.telecom.RemoteConnection remoteConnection, java.lang.String str) {
        }

        public void onPostDialChar(android.telecom.RemoteConnection remoteConnection, char c) {
        }

        public void onVoipAudioChanged(android.telecom.RemoteConnection remoteConnection, boolean z) {
        }

        public void onStatusHintsChanged(android.telecom.RemoteConnection remoteConnection, android.telecom.StatusHints statusHints) {
        }

        public void onAddressChanged(android.telecom.RemoteConnection remoteConnection, android.net.Uri uri, int i) {
        }

        public void onCallerDisplayNameChanged(android.telecom.RemoteConnection remoteConnection, java.lang.String str, int i) {
        }

        public void onVideoStateChanged(android.telecom.RemoteConnection remoteConnection, int i) {
        }

        public void onDestroyed(android.telecom.RemoteConnection remoteConnection) {
        }

        public void onConferenceableConnectionsChanged(android.telecom.RemoteConnection remoteConnection, java.util.List<android.telecom.RemoteConnection> list) {
        }

        public void onVideoProviderChanged(android.telecom.RemoteConnection remoteConnection, android.telecom.RemoteConnection.VideoProvider videoProvider) {
        }

        public void onConferenceChanged(android.telecom.RemoteConnection remoteConnection, android.telecom.RemoteConference remoteConference) {
        }

        public void onExtrasChanged(android.telecom.RemoteConnection remoteConnection, android.os.Bundle bundle) {
        }

        public void onConnectionEvent(android.telecom.RemoteConnection remoteConnection, java.lang.String str, android.os.Bundle bundle) {
        }

        public void onRttInitiationSuccess(android.telecom.RemoteConnection remoteConnection) {
        }

        public void onRttInitiationFailure(android.telecom.RemoteConnection remoteConnection, int i) {
        }

        public void onRttSessionRemotelyTerminated(android.telecom.RemoteConnection remoteConnection) {
        }

        public void onRemoteRttRequest(android.telecom.RemoteConnection remoteConnection) {
        }
    }

    public static class VideoProvider {
        private final java.lang.String mCallingPackage;
        private final int mTargetSdkVersion;
        private final com.android.internal.telecom.IVideoProvider mVideoProviderBinder;
        private final com.android.internal.telecom.IVideoCallback mVideoCallbackDelegate = new com.android.internal.telecom.IVideoCallback() { // from class: android.telecom.RemoteConnection.VideoProvider.1
            @Override // com.android.internal.telecom.IVideoCallback
            public void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) {
                java.util.Iterator it = android.telecom.RemoteConnection.VideoProvider.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((android.telecom.RemoteConnection.VideoProvider.Callback) it.next()).onSessionModifyRequestReceived(android.telecom.RemoteConnection.VideoProvider.this, videoProfile);
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
                java.util.Iterator it = android.telecom.RemoteConnection.VideoProvider.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((android.telecom.RemoteConnection.VideoProvider.Callback) it.next()).onSessionModifyResponseReceived(android.telecom.RemoteConnection.VideoProvider.this, i, videoProfile, videoProfile2);
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void handleCallSessionEvent(int i) {
                java.util.Iterator it = android.telecom.RemoteConnection.VideoProvider.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((android.telecom.RemoteConnection.VideoProvider.Callback) it.next()).onCallSessionEvent(android.telecom.RemoteConnection.VideoProvider.this, i);
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changePeerDimensions(int i, int i2) {
                java.util.Iterator it = android.telecom.RemoteConnection.VideoProvider.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((android.telecom.RemoteConnection.VideoProvider.Callback) it.next()).onPeerDimensionsChanged(android.telecom.RemoteConnection.VideoProvider.this, i, i2);
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changeCallDataUsage(long j) {
                java.util.Iterator it = android.telecom.RemoteConnection.VideoProvider.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((android.telecom.RemoteConnection.VideoProvider.Callback) it.next()).onCallDataUsageChanged(android.telecom.RemoteConnection.VideoProvider.this, j);
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) {
                java.util.Iterator it = android.telecom.RemoteConnection.VideoProvider.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((android.telecom.RemoteConnection.VideoProvider.Callback) it.next()).onCameraCapabilitiesChanged(android.telecom.RemoteConnection.VideoProvider.this, cameraCapabilities);
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changeVideoQuality(int i) {
                java.util.Iterator it = android.telecom.RemoteConnection.VideoProvider.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((android.telecom.RemoteConnection.VideoProvider.Callback) it.next()).onVideoQualityChanged(android.telecom.RemoteConnection.VideoProvider.this, i);
                }
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return null;
            }
        };
        private final android.telecom.VideoCallbackServant mVideoCallbackServant = new android.telecom.VideoCallbackServant(this.mVideoCallbackDelegate);
        private final java.util.Set<android.telecom.RemoteConnection.VideoProvider.Callback> mCallbacks = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap(8, 0.9f, 1));

        public static abstract class Callback {
            public void onSessionModifyRequestReceived(android.telecom.RemoteConnection.VideoProvider videoProvider, android.telecom.VideoProfile videoProfile) {
            }

            public void onSessionModifyResponseReceived(android.telecom.RemoteConnection.VideoProvider videoProvider, int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
            }

            public void onCallSessionEvent(android.telecom.RemoteConnection.VideoProvider videoProvider, int i) {
            }

            public void onPeerDimensionsChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, int i, int i2) {
            }

            public void onCallDataUsageChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, long j) {
            }

            public void onCameraCapabilitiesChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) {
            }

            public void onVideoQualityChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, int i) {
            }
        }

        VideoProvider(com.android.internal.telecom.IVideoProvider iVideoProvider, java.lang.String str, int i) {
            this.mVideoProviderBinder = iVideoProvider;
            this.mCallingPackage = str;
            this.mTargetSdkVersion = i;
            try {
                this.mVideoProviderBinder.addVideoCallback(this.mVideoCallbackServant.getStub().asBinder());
            } catch (android.os.RemoteException e) {
            }
        }

        public void registerCallback(android.telecom.RemoteConnection.VideoProvider.Callback callback) {
            this.mCallbacks.add(callback);
        }

        public void unregisterCallback(android.telecom.RemoteConnection.VideoProvider.Callback callback) {
            this.mCallbacks.remove(callback);
        }

        public void setCamera(java.lang.String str) {
            try {
                this.mVideoProviderBinder.setCamera(str, this.mCallingPackage, this.mTargetSdkVersion);
            } catch (android.os.RemoteException e) {
            }
        }

        public void setPreviewSurface(android.view.Surface surface) {
            try {
                this.mVideoProviderBinder.setPreviewSurface(surface);
            } catch (android.os.RemoteException e) {
            }
        }

        public void setDisplaySurface(android.view.Surface surface) {
            try {
                this.mVideoProviderBinder.setDisplaySurface(surface);
            } catch (android.os.RemoteException e) {
            }
        }

        public void setDeviceOrientation(int i) {
            try {
                this.mVideoProviderBinder.setDeviceOrientation(i);
            } catch (android.os.RemoteException e) {
            }
        }

        public void setZoom(float f) {
            try {
                this.mVideoProviderBinder.setZoom(f);
            } catch (android.os.RemoteException e) {
            }
        }

        public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
            try {
                this.mVideoProviderBinder.sendSessionModifyRequest(videoProfile, videoProfile2);
            } catch (android.os.RemoteException e) {
            }
        }

        public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) {
            try {
                this.mVideoProviderBinder.sendSessionModifyResponse(videoProfile);
            } catch (android.os.RemoteException e) {
            }
        }

        public void requestCameraCapabilities() {
            try {
                this.mVideoProviderBinder.requestCameraCapabilities();
            } catch (android.os.RemoteException e) {
            }
        }

        public void requestCallDataUsage() {
            try {
                this.mVideoProviderBinder.requestCallDataUsage();
            } catch (android.os.RemoteException e) {
            }
        }

        public void setPauseImage(android.net.Uri uri) {
            try {
                this.mVideoProviderBinder.setPauseImage(uri);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    RemoteConnection(java.lang.String str, com.android.internal.telecom.IConnectionService iConnectionService, android.telecom.ConnectionRequest connectionRequest) {
        this.mCallbackRecords = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap(8, 0.9f, 1));
        this.mConferenceableConnections = new java.util.ArrayList();
        this.mUnmodifiableconferenceableConnections = java.util.Collections.unmodifiableList(this.mConferenceableConnections);
        this.mState = 1;
        this.mConnectionId = str;
        this.mConnectionService = iConnectionService;
        this.mConnected = true;
        this.mState = 0;
        if (connectionRequest != null && connectionRequest.getExtras() != null && connectionRequest.getExtras().containsKey(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME)) {
            this.mCallingPackageAbbreviation = android.telecom.Log.getPackageAbbreviation(connectionRequest.getExtras().getString(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME));
        }
    }

    RemoteConnection(java.lang.String str, com.android.internal.telecom.IConnectionService iConnectionService, android.telecom.ParcelableConnection parcelableConnection, java.lang.String str2, int i) {
        this.mCallbackRecords = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap(8, 0.9f, 1));
        this.mConferenceableConnections = new java.util.ArrayList();
        this.mUnmodifiableconferenceableConnections = java.util.Collections.unmodifiableList(this.mConferenceableConnections);
        this.mState = 1;
        this.mConnectionId = str;
        this.mConnectionService = iConnectionService;
        this.mConnected = true;
        this.mState = parcelableConnection.getState();
        this.mDisconnectCause = parcelableConnection.getDisconnectCause();
        this.mRingbackRequested = parcelableConnection.isRingbackRequested();
        this.mConnectionCapabilities = parcelableConnection.getConnectionCapabilities();
        this.mConnectionProperties = parcelableConnection.getConnectionProperties();
        this.mVideoState = parcelableConnection.getVideoState();
        com.android.internal.telecom.IVideoProvider videoProvider = parcelableConnection.getVideoProvider();
        if (videoProvider != null) {
            this.mVideoProvider = new android.telecom.RemoteConnection.VideoProvider(videoProvider, str2, i);
        } else {
            this.mVideoProvider = null;
        }
        this.mIsVoipAudioMode = parcelableConnection.getIsVoipAudioMode();
        this.mStatusHints = parcelableConnection.getStatusHints();
        this.mAddress = parcelableConnection.getHandle();
        this.mAddressPresentation = parcelableConnection.getHandlePresentation();
        this.mCallerDisplayName = parcelableConnection.getCallerDisplayName();
        this.mCallerDisplayNamePresentation = parcelableConnection.getCallerDisplayNamePresentation();
        this.mConference = null;
        putExtras(parcelableConnection.getExtras());
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.telecom.Connection.EXTRA_ORIGINAL_CONNECTION_ID, str);
        putExtras(bundle);
        this.mCallingPackageAbbreviation = android.telecom.Log.getPackageAbbreviation(str2);
    }

    RemoteConnection(android.telecom.DisconnectCause disconnectCause) {
        this.mCallbackRecords = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap(8, 0.9f, 1));
        this.mConferenceableConnections = new java.util.ArrayList();
        this.mUnmodifiableconferenceableConnections = java.util.Collections.unmodifiableList(this.mConferenceableConnections);
        this.mState = 1;
        this.mConnectionId = "NULL";
        this.mConnected = false;
        this.mState = 6;
        this.mDisconnectCause = disconnectCause;
    }

    public void registerCallback(android.telecom.RemoteConnection.Callback callback) {
        registerCallback(callback, new android.os.Handler());
    }

    public void registerCallback(android.telecom.RemoteConnection.Callback callback, android.os.Handler handler) {
        unregisterCallback(callback);
        if (callback != null && handler != null) {
            this.mCallbackRecords.add(new android.telecom.RemoteConnection.CallbackRecord(callback, handler));
        }
    }

    public void unregisterCallback(android.telecom.RemoteConnection.Callback callback) {
        if (callback != null) {
            for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
                if (callbackRecord.getCallback() == callback) {
                    this.mCallbackRecords.remove(callbackRecord);
                    return;
                }
            }
        }
    }

    public int getState() {
        return this.mState;
    }

    public android.telecom.DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public boolean isVoipAudioMode() {
        return this.mIsVoipAudioMode;
    }

    public android.telecom.StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public android.net.Uri getAddress() {
        return this.mAddress;
    }

    public int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public java.lang.CharSequence getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public final android.telecom.RemoteConnection.VideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public final android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public void abort() {
        android.telecom.Log.startSession("RC.a", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.abort(this.mConnectionId, android.telecom.Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void answer() {
        android.telecom.Log.startSession("RC.an", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.answer(this.mConnectionId, android.telecom.Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void answer(int i) {
        android.telecom.Log.startSession("RC.an2", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.answerVideo(this.mConnectionId, i, android.telecom.Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void reject() {
        android.telecom.Log.startSession("RC.r", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.reject(this.mConnectionId, android.telecom.Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void hold() {
        android.telecom.Log.startSession("RC.h", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.hold(this.mConnectionId, android.telecom.Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void unhold() {
        android.telecom.Log.startSession("RC.u", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.unhold(this.mConnectionId, android.telecom.Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void disconnect() {
        android.telecom.Log.startSession("RC.d", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.disconnect(this.mConnectionId, android.telecom.Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void playDtmfTone(char c) {
        android.telecom.Log.startSession("RC.pDT", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.playDtmfTone(this.mConnectionId, c, null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void stopDtmfTone() {
        android.telecom.Log.startSession("RC.sDT", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.stopDtmfTone(this.mConnectionId, null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void postDialContinue(boolean z) {
        android.telecom.Log.startSession("RC.pDC", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.onPostDialContinue(this.mConnectionId, z, null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void pullExternalCall() {
        android.telecom.Log.startSession("RC.pEC", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.pullExternalCall(this.mConnectionId, null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void addConferenceParticipants(java.util.List<android.net.Uri> list) {
        try {
            if (this.mConnected) {
                this.mConnectionService.addConferenceParticipants(this.mConnectionId, list, null);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setAudioState(android.telecom.AudioState audioState) {
        setCallAudioState(new android.telecom.CallAudioState(audioState));
    }

    public void setCallAudioState(android.telecom.CallAudioState callAudioState) {
        android.telecom.Log.startSession("RC.sCAS", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.onCallAudioStateChanged(this.mConnectionId, callAudioState, null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void startRtt(android.telecom.Connection.RttTextStream rttTextStream) {
        android.telecom.Log.startSession("RC.sR", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.startRtt(this.mConnectionId, rttTextStream.getFdFromInCall(), rttTextStream.getFdToInCall(), null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void stopRtt() {
        android.telecom.Log.startSession("RC.stR", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.stopRtt(this.mConnectionId, null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    @android.annotation.SystemApi
    public void onCallFilteringCompleted(android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo) {
        android.telecom.Log.startSession("RC.oCFC", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.onCallFilteringCompleted(this.mConnectionId, callFilteringCompletionInfo, null);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public void sendRttUpgradeResponse(android.telecom.Connection.RttTextStream rttTextStream) {
        android.telecom.Log.startSession("RC.sRUR", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                if (rttTextStream == null) {
                    this.mConnectionService.respondToRttUpgradeRequest(this.mConnectionId, null, null, null);
                } else {
                    this.mConnectionService.respondToRttUpgradeRequest(this.mConnectionId, rttTextStream.getFdFromInCall(), rttTextStream.getFdToInCall(), null);
                }
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.telecom.Log.endSession();
            throw th;
        }
        android.telecom.Log.endSession();
    }

    public java.util.List<android.telecom.RemoteConnection> getConferenceableConnections() {
        return this.mUnmodifiableconferenceableConnections;
    }

    public android.telecom.RemoteConference getConference() {
        return this.mConference;
    }

    private java.lang.String getActiveOwnerInfo() {
        android.telecom.Logging.Session.Info externalSession = android.telecom.Log.getExternalSession();
        if (externalSession == null) {
            return null;
        }
        return externalSession.ownerInfo;
    }

    java.lang.String getId() {
        return this.mConnectionId;
    }

    com.android.internal.telecom.IConnectionService getConnectionService() {
        return this.mConnectionService;
    }

    void setState(final int i) {
        if (this.mState != i) {
            this.mState = i;
            for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.1
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onStateChanged(this, i);
                    }
                });
            }
        }
    }

    void setDisconnected(final android.telecom.DisconnectCause disconnectCause) {
        if (this.mState != 6) {
            this.mState = 6;
            this.mDisconnectCause = disconnectCause;
            for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.2
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onDisconnected(this, disconnectCause);
                    }
                });
            }
        }
    }

    void setRingbackRequested(final boolean z) {
        if (this.mRingbackRequested != z) {
            this.mRingbackRequested = z;
            for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.3
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onRingbackRequested(this, z);
                    }
                });
            }
        }
    }

    void setConnectionCapabilities(final int i) {
        this.mConnectionCapabilities = i;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.4
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConnectionCapabilitiesChanged(this, i);
                }
            });
        }
    }

    void setConnectionProperties(final int i) {
        this.mConnectionProperties = i;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.5
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConnectionPropertiesChanged(this, i);
                }
            });
        }
    }

    void setDestroyed() {
        if (!this.mCallbackRecords.isEmpty()) {
            if (this.mState != 6) {
                setDisconnected(new android.telecom.DisconnectCause(1, "Connection destroyed."));
            }
            for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.6
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onDestroyed(this);
                    }
                });
            }
            this.mCallbackRecords.clear();
            this.mConnected = false;
        }
    }

    void setPostDialWait(final java.lang.String str) {
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.7
                @Override // java.lang.Runnable
                public void run() {
                    callback.onPostDialWait(this, str);
                }
            });
        }
    }

    void onPostDialChar(final char c) {
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.8
                @Override // java.lang.Runnable
                public void run() {
                    callback.onPostDialChar(this, c);
                }
            });
        }
    }

    void setVideoState(final int i) {
        this.mVideoState = i;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.9
                @Override // java.lang.Runnable
                public void run() {
                    callback.onVideoStateChanged(this, i);
                }
            });
        }
    }

    void setVideoProvider(final android.telecom.RemoteConnection.VideoProvider videoProvider) {
        this.mVideoProvider = videoProvider;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.10
                @Override // java.lang.Runnable
                public void run() {
                    callback.onVideoProviderChanged(this, videoProvider);
                }
            });
        }
    }

    void setIsVoipAudioMode(final boolean z) {
        this.mIsVoipAudioMode = z;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.11
                @Override // java.lang.Runnable
                public void run() {
                    callback.onVoipAudioChanged(this, z);
                }
            });
        }
    }

    void setStatusHints(final android.telecom.StatusHints statusHints) {
        this.mStatusHints = statusHints;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.12
                @Override // java.lang.Runnable
                public void run() {
                    callback.onStatusHintsChanged(this, statusHints);
                }
            });
        }
    }

    void setAddress(final android.net.Uri uri, final int i) {
        this.mAddress = uri;
        this.mAddressPresentation = i;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.13
                @Override // java.lang.Runnable
                public void run() {
                    callback.onAddressChanged(this, uri, i);
                }
            });
        }
    }

    void setCallerDisplayName(final java.lang.String str, final int i) {
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i;
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.14
                @Override // java.lang.Runnable
                public void run() {
                    callback.onCallerDisplayNameChanged(this, str, i);
                }
            });
        }
    }

    void setConferenceableConnections(java.util.List<android.telecom.RemoteConnection> list) {
        this.mConferenceableConnections.clear();
        this.mConferenceableConnections.addAll(list);
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.15
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConferenceableConnectionsChanged(this, android.telecom.RemoteConnection.this.mUnmodifiableconferenceableConnections);
                }
            });
        }
    }

    void setConference(final android.telecom.RemoteConference remoteConference) {
        if (this.mConference != remoteConference) {
            this.mConference = remoteConference;
            for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
                final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.16
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConferenceChanged(this, remoteConference);
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
        try {
            this.mExtras.putAll(bundle);
        } catch (android.os.BadParcelableException e) {
            android.telecom.Log.w(this, "putExtras: could not unmarshal extras; exception = " + e, new java.lang.Object[0]);
        }
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
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.17
                @Override // java.lang.Runnable
                public void run() {
                    callback.onExtrasChanged(this, android.telecom.RemoteConnection.this.mExtras);
                }
            });
        }
    }

    void onConnectionEvent(final java.lang.String str, final android.os.Bundle bundle) {
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection.18
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConnectionEvent(this, str, bundle);
                }
            });
        }
    }

    void onRttInitiationSuccess() {
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.RemoteConnection.Callback.this.onRttInitiationSuccess(this);
                }
            });
        }
    }

    void onRttInitiationFailure(final int i) {
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.RemoteConnection.Callback.this.onRttInitiationFailure(this, i);
                }
            });
        }
    }

    void onRttSessionRemotelyTerminated() {
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.RemoteConnection.Callback.this.onRttSessionRemotelyTerminated(this);
                }
            });
        }
    }

    void onRemoteRttRequest() {
        for (android.telecom.RemoteConnection.CallbackRecord callbackRecord : this.mCallbackRecords) {
            final android.telecom.RemoteConnection.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.RemoteConnection.Callback.this.onRemoteRttRequest(this);
                }
            });
        }
    }

    public static android.telecom.RemoteConnection failure(android.telecom.DisconnectCause disconnectCause) {
        return new android.telecom.RemoteConnection(disconnectCause);
    }

    private static final class CallbackRecord extends android.telecom.RemoteConnection.Callback {
        private final android.telecom.RemoteConnection.Callback mCallback;
        private final android.os.Handler mHandler;

        public CallbackRecord(android.telecom.RemoteConnection.Callback callback, android.os.Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
        }

        public android.telecom.RemoteConnection.Callback getCallback() {
            return this.mCallback;
        }

        public android.os.Handler getHandler() {
            return this.mHandler;
        }
    }
}
