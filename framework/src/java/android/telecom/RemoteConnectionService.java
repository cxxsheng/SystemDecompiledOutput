package android.telecom;

/* loaded from: classes3.dex */
final class RemoteConnectionService {
    private final android.telecom.ConnectionService mOurConnectionServiceImpl;
    private final com.android.internal.telecom.IConnectionService mOutgoingConnectionServiceRpc;
    private static final android.telecom.RemoteConnection NULL_CONNECTION = new android.telecom.RemoteConnection("NULL", null, null);
    private static final android.telecom.RemoteConference NULL_CONFERENCE = new android.telecom.RemoteConference("NULL", null);
    private final com.android.internal.telecom.IConnectionServiceAdapter mServantDelegate = new com.android.internal.telecom.IConnectionServiceAdapter() { // from class: android.telecom.RemoteConnectionService.1
        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConnectionComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnection findConnectionForAction = android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "handleCreateConnectionSuccessful");
            if (findConnectionForAction != android.telecom.RemoteConnectionService.NULL_CONNECTION && android.telecom.RemoteConnectionService.this.mPendingConnections.contains(findConnectionForAction)) {
                android.telecom.RemoteConnectionService.this.mPendingConnections.remove(findConnectionForAction);
                findConnectionForAction.setConnectionCapabilities(parcelableConnection.getConnectionCapabilities());
                findConnectionForAction.setConnectionProperties(parcelableConnection.getConnectionProperties());
                if (parcelableConnection.getHandle() != null || parcelableConnection.getState() != 6) {
                    findConnectionForAction.setAddress(parcelableConnection.getHandle(), parcelableConnection.getHandlePresentation());
                }
                if (parcelableConnection.getCallerDisplayName() != null || parcelableConnection.getState() != 6) {
                    findConnectionForAction.setCallerDisplayName(parcelableConnection.getCallerDisplayName(), parcelableConnection.getCallerDisplayNamePresentation());
                }
                if (parcelableConnection.getState() == 6) {
                    findConnectionForAction.setDisconnected(parcelableConnection.getDisconnectCause());
                } else {
                    findConnectionForAction.setState(parcelableConnection.getState());
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str2 : parcelableConnection.getConferenceableConnectionIds()) {
                    if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str2)) {
                        arrayList.add((android.telecom.RemoteConnection) android.telecom.RemoteConnectionService.this.mConnectionById.get(str2));
                    }
                }
                findConnectionForAction.setConferenceableConnections(arrayList);
                findConnectionForAction.setVideoState(parcelableConnection.getVideoState());
                if (findConnectionForAction.getState() == 6) {
                    findConnectionForAction.setDestroyed();
                }
                findConnectionForAction.setStatusHints(parcelableConnection.getStatusHints());
                findConnectionForAction.setIsVoipAudioMode(parcelableConnection.getIsVoipAudioMode());
                findConnectionForAction.setRingbackRequested(parcelableConnection.isRingbackRequested());
                findConnectionForAction.putExtras(parcelableConnection.getExtras());
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConferenceComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setActive(java.lang.String str, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setActive").setState(4);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "setActive").setState(4);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRinging(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setRinging").setState(2);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDialing(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setDialing").setState(3);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setPulling(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setPulling").setState(7);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setDisconnected").setDisconnected(disconnectCause);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "setDisconnected").setDisconnected(disconnectCause);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setOnHold(java.lang.String str, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setOnHold").setState(5);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "setOnHold").setState(5);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRingbackRequested(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setRingbackRequested").setRingbackRequested(z);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionCapabilities(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setConnectionCapabilities").setConnectionCapabilities(i);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "setConnectionCapabilities").setConnectionCapabilities(i);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionProperties(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setConnectionProperties").setConnectionProperties(i);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "setConnectionProperties").setConnectionProperties(i);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setIsConferenced(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnection findConnectionForAction = android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setIsConferenced");
            if (findConnectionForAction != android.telecom.RemoteConnectionService.NULL_CONNECTION) {
                if (str2 == null) {
                    if (findConnectionForAction.getConference() != null) {
                        findConnectionForAction.getConference().removeConnection(findConnectionForAction);
                    }
                } else {
                    android.telecom.RemoteConference findConferenceForAction = android.telecom.RemoteConnectionService.this.findConferenceForAction(str2, "setIsConferenced");
                    if (findConferenceForAction != android.telecom.RemoteConnectionService.NULL_CONFERENCE) {
                        findConferenceForAction.addConnection(findConnectionForAction);
                    }
                }
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceMergeFailed(java.lang.String str, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPhoneAccountChanged(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onConnectionServiceFocusReleased(android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void addConferenceCall(final java.lang.String str, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConference remoteConference = new android.telecom.RemoteConference(str, android.telecom.RemoteConnectionService.this.mOutgoingConnectionServiceRpc);
            java.util.Iterator<java.lang.String> it = parcelableConference.getConnectionIds().iterator();
            while (it.hasNext()) {
                android.telecom.RemoteConnection remoteConnection = (android.telecom.RemoteConnection) android.telecom.RemoteConnectionService.this.mConnectionById.get(it.next());
                if (remoteConnection != null) {
                    remoteConference.addConnection(remoteConnection);
                }
            }
            remoteConference.setState(parcelableConference.getState());
            remoteConference.setConnectionCapabilities(parcelableConference.getConnectionCapabilities());
            remoteConference.setConnectionProperties(parcelableConference.getConnectionProperties());
            remoteConference.putExtras(parcelableConference.getExtras());
            android.telecom.RemoteConnectionService.this.mConferenceById.put(str, remoteConference);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(android.telecom.Connection.EXTRA_ORIGINAL_CONNECTION_ID, str);
            bundle.putParcelable(android.telecom.Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE, parcelableConference.getPhoneAccount());
            remoteConference.putExtras(bundle);
            remoteConference.registerCallback(new android.telecom.RemoteConference.Callback() { // from class: android.telecom.RemoteConnectionService.1.1
                @Override // android.telecom.RemoteConference.Callback
                public void onDestroyed(android.telecom.RemoteConference remoteConference2) {
                    android.telecom.RemoteConnectionService.this.mConferenceById.remove(str);
                    android.telecom.RemoteConnectionService.this.maybeDisconnectAdapter();
                }
            });
            android.telecom.RemoteConnectionService.this.mOurConnectionServiceImpl.addRemoteConference(remoteConference);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void removeCall(java.lang.String str, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "removeCall").setDestroyed();
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "removeCall").setDestroyed();
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialWait(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "onPostDialWait").setPostDialWait(str2);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialChar(java.lang.String str, char c, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "onPostDialChar").onPostDialChar(c);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryRemoteConnectionServices(com.android.internal.telecom.RemoteServiceCallback remoteServiceCallback, java.lang.String str, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoProvider(java.lang.String str, com.android.internal.telecom.IVideoProvider iVideoProvider, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnection.VideoProvider videoProvider;
            java.lang.String opPackageName = android.telecom.RemoteConnectionService.this.mOurConnectionServiceImpl.getApplicationContext().getOpPackageName();
            int i = android.telecom.RemoteConnectionService.this.mOurConnectionServiceImpl.getApplicationInfo().targetSdkVersion;
            if (iVideoProvider == null) {
                videoProvider = null;
            } else {
                videoProvider = new android.telecom.RemoteConnection.VideoProvider(iVideoProvider, opPackageName, i);
            }
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setVideoProvider").setVideoProvider(videoProvider);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoState(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setVideoState").setVideoState(i);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setIsVoipAudioMode(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setIsVoipAudioMode").setIsVoipAudioMode(z);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setStatusHints(java.lang.String str, android.telecom.StatusHints statusHints, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setStatusHints").setStatusHints(statusHints);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setAddress(java.lang.String str, android.net.Uri uri, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setAddress").setAddress(uri, i);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setCallerDisplayName(java.lang.String str, java.lang.String str2, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setCallerDisplayName").setCallerDisplayName(str2, i);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void setConferenceableConnections(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str2 : list) {
                if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str2)) {
                    arrayList.add((android.telecom.RemoteConnection) android.telecom.RemoteConnectionService.this.mConnectionById.get(str2));
                }
            }
            if (android.telecom.RemoteConnectionService.this.hasConnection(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "setConferenceableConnections").setConferenceableConnections(arrayList);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "setConferenceableConnections").setConferenceableConnections(arrayList);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void addExistingConnection(final java.lang.String str, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConference remoteConference;
            android.telecom.Log.i(android.telecom.RemoteConnectionService.this, "addExistingConnection: callId=%s, conn=%s", str, parcelableConnection);
            android.telecom.RemoteConnection remoteConnection = new android.telecom.RemoteConnection(str, android.telecom.RemoteConnectionService.this.mOutgoingConnectionServiceRpc, parcelableConnection, android.telecom.RemoteConnectionService.this.mOurConnectionServiceImpl.getApplicationContext().getOpPackageName(), android.telecom.RemoteConnectionService.this.mOurConnectionServiceImpl.getApplicationInfo().targetSdkVersion);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.telecom.Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE, parcelableConnection.getPhoneAccount());
            if (parcelableConnection.getParentCallId() != null && (remoteConference = (android.telecom.RemoteConference) android.telecom.RemoteConnectionService.this.mConferenceById.get(parcelableConnection.getParentCallId())) != null) {
                bundle.putString(android.telecom.Connection.EXTRA_ADD_TO_CONFERENCE_ID, remoteConference.getId());
                android.telecom.Log.i(this, "addExistingConnection: stash parent of %s as %s", parcelableConnection.getParentCallId(), remoteConference.getId());
            }
            remoteConnection.putExtras(bundle);
            android.telecom.RemoteConnectionService.this.mConnectionById.put(str, remoteConnection);
            remoteConnection.registerCallback(new android.telecom.RemoteConnection.Callback() { // from class: android.telecom.RemoteConnectionService.1.2
                @Override // android.telecom.RemoteConnection.Callback
                public void onDestroyed(android.telecom.RemoteConnection remoteConnection2) {
                    android.telecom.RemoteConnectionService.this.mConnectionById.remove(str);
                    android.telecom.RemoteConnectionService.this.maybeDisconnectAdapter();
                }
            });
            android.telecom.RemoteConnectionService.this.mOurConnectionServiceImpl.addRemoteExistingConnection(remoteConnection);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void putExtras(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.hasConnection(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "putExtras").putExtras(bundle);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "putExtras").putExtras(bundle);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void removeExtras(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.hasConnection(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "removeExtra").removeExtras(list);
            } else {
                android.telecom.RemoteConnectionService.this.findConferenceForAction(str, "removeExtra").removeExtras(list);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setAudioRoute(java.lang.String str, int i, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            android.telecom.RemoteConnectionService.this.hasConnection(str);
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) {
            if (android.telecom.RemoteConnectionService.this.mConnectionById.containsKey(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "onConnectionEvent").onConnectionEvent(str2, bundle);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationSuccess(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            if (android.telecom.RemoteConnectionService.this.hasConnection(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "onRttInitiationSuccess").onRttInitiationSuccess();
            } else {
                android.telecom.Log.w(this, "onRttInitiationSuccess called on a remote conference", new java.lang.Object[0]);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationFailure(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            if (android.telecom.RemoteConnectionService.this.hasConnection(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "onRttInitiationFailure").onRttInitiationFailure(i);
            } else {
                android.telecom.Log.w(this, "onRttInitiationFailure called on a remote conference", new java.lang.Object[0]);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttSessionRemotelyTerminated(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            if (android.telecom.RemoteConnectionService.this.hasConnection(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "onRttSessionRemotelyTerminated").onRttSessionRemotelyTerminated();
            } else {
                android.telecom.Log.w(this, "onRttSessionRemotelyTerminated called on a remote conference", new java.lang.Object[0]);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRemoteRttRequest(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            if (android.telecom.RemoteConnectionService.this.hasConnection(str)) {
                android.telecom.RemoteConnectionService.this.findConnectionForAction(str, "onRemoteRttRequest").onRemoteRttRequest();
            } else {
                android.telecom.Log.w(this, "onRemoteRttRequest called on a remote conference", new java.lang.Object[0]);
            }
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void resetConnectionTime(java.lang.String str, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceState(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setCallDirection(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void requestCallEndpointChange(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryLocation(java.lang.String str, long j, java.lang.String str2, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) {
        }
    };
    private final android.telecom.ConnectionServiceAdapterServant mServant = new android.telecom.ConnectionServiceAdapterServant(this.mServantDelegate);
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telecom.RemoteConnectionService.2
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            java.util.Iterator it = android.telecom.RemoteConnectionService.this.mConnectionById.values().iterator();
            while (it.hasNext()) {
                ((android.telecom.RemoteConnection) it.next()).setDestroyed();
            }
            java.util.Iterator it2 = android.telecom.RemoteConnectionService.this.mConferenceById.values().iterator();
            while (it2.hasNext()) {
                ((android.telecom.RemoteConference) it2.next()).setDestroyed();
            }
            android.telecom.RemoteConnectionService.this.mConnectionById.clear();
            android.telecom.RemoteConnectionService.this.mConferenceById.clear();
            android.telecom.RemoteConnectionService.this.mPendingConnections.clear();
            android.telecom.RemoteConnectionService.this.mOutgoingConnectionServiceRpc.asBinder().unlinkToDeath(android.telecom.RemoteConnectionService.this.mDeathRecipient, 0);
        }
    };
    private final java.util.Map<java.lang.String, android.telecom.RemoteConnection> mConnectionById = new java.util.HashMap();
    private final java.util.Map<java.lang.String, android.telecom.RemoteConference> mConferenceById = new java.util.HashMap();
    private final java.util.Set<android.telecom.RemoteConnection> mPendingConnections = new java.util.HashSet();

    static {
    }

    RemoteConnectionService(com.android.internal.telecom.IConnectionService iConnectionService, android.telecom.ConnectionService connectionService) throws android.os.RemoteException {
        this.mOutgoingConnectionServiceRpc = iConnectionService;
        this.mOutgoingConnectionServiceRpc.asBinder().linkToDeath(this.mDeathRecipient, 0);
        this.mOurConnectionServiceImpl = connectionService;
    }

    public java.lang.String toString() {
        return "[RemoteCS - " + this.mOutgoingConnectionServiceRpc.asBinder().toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    final android.telecom.RemoteConnection createRemoteConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest, boolean z) {
        final java.lang.String obj = java.util.UUID.randomUUID().toString();
        android.os.Bundle bundle = new android.os.Bundle();
        if (connectionRequest.getExtras() != null) {
            bundle.putAll(connectionRequest.getExtras());
        }
        bundle.putString(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME, this.mOurConnectionServiceImpl.getApplicationContext().getOpPackageName());
        android.telecom.ConnectionRequest build = new android.telecom.ConnectionRequest.Builder().setAccountHandle(connectionRequest.getAccountHandle()).setAddress(connectionRequest.getAddress()).setExtras(bundle).setVideoState(connectionRequest.getVideoState()).setRttPipeFromInCall(connectionRequest.getRttPipeFromInCall()).setRttPipeToInCall(connectionRequest.getRttPipeToInCall()).build();
        try {
            if (this.mConnectionById.isEmpty()) {
                this.mOutgoingConnectionServiceRpc.addConnectionServiceAdapter(this.mServant.getStub(), null);
            }
            android.telecom.RemoteConnection remoteConnection = new android.telecom.RemoteConnection(obj, this.mOutgoingConnectionServiceRpc, build);
            this.mPendingConnections.add(remoteConnection);
            this.mConnectionById.put(obj, remoteConnection);
            this.mOutgoingConnectionServiceRpc.createConnection(phoneAccountHandle, obj, build, z, false, null);
            remoteConnection.registerCallback(new android.telecom.RemoteConnection.Callback() { // from class: android.telecom.RemoteConnectionService.3
                @Override // android.telecom.RemoteConnection.Callback
                public void onDestroyed(android.telecom.RemoteConnection remoteConnection2) {
                    android.telecom.RemoteConnectionService.this.mConnectionById.remove(obj);
                    android.telecom.RemoteConnectionService.this.maybeDisconnectAdapter();
                }
            });
            return remoteConnection;
        } catch (android.os.RemoteException e) {
            return android.telecom.RemoteConnection.failure(new android.telecom.DisconnectCause(1, e.toString()));
        }
    }

    android.telecom.RemoteConference createRemoteConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest, boolean z) {
        final java.lang.String obj = java.util.UUID.randomUUID().toString();
        try {
            if (this.mConferenceById.isEmpty()) {
                this.mOutgoingConnectionServiceRpc.addConnectionServiceAdapter(this.mServant.getStub(), null);
            }
            android.telecom.RemoteConference remoteConference = new android.telecom.RemoteConference(obj, this.mOutgoingConnectionServiceRpc);
            this.mOutgoingConnectionServiceRpc.createConference(phoneAccountHandle, obj, connectionRequest, z, false, null);
            remoteConference.registerCallback(new android.telecom.RemoteConference.Callback() { // from class: android.telecom.RemoteConnectionService.4
                @Override // android.telecom.RemoteConference.Callback
                public void onDestroyed(android.telecom.RemoteConference remoteConference2) {
                    android.telecom.RemoteConnectionService.this.mConferenceById.remove(obj);
                    android.telecom.RemoteConnectionService.this.maybeDisconnectAdapter();
                }
            });
            remoteConference.putExtras(connectionRequest.getExtras());
            return remoteConference;
        } catch (android.os.RemoteException e) {
            return android.telecom.RemoteConference.failure(new android.telecom.DisconnectCause(1, e.toString()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasConnection(java.lang.String str) {
        return this.mConnectionById.containsKey(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telecom.RemoteConnection findConnectionForAction(java.lang.String str, java.lang.String str2) {
        if (this.mConnectionById.containsKey(str)) {
            return this.mConnectionById.get(str);
        }
        android.telecom.Log.w(this, "%s - Cannot find Connection %s", str2, str);
        return NULL_CONNECTION;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telecom.RemoteConference findConferenceForAction(java.lang.String str, java.lang.String str2) {
        if (this.mConferenceById.containsKey(str)) {
            return this.mConferenceById.get(str);
        }
        android.telecom.Log.w(this, "%s - Cannot find Conference %s", str2, str);
        return NULL_CONFERENCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeDisconnectAdapter() {
        if (this.mConnectionById.isEmpty() && this.mConferenceById.isEmpty()) {
            try {
                this.mOutgoingConnectionServiceRpc.removeConnectionServiceAdapter(this.mServant.getStub(), null);
            } catch (android.os.RemoteException e) {
            }
        }
    }
}
