package android.telecom;

/* loaded from: classes3.dex */
final class ConnectionServiceAdapterServant {
    private static final int MSG_ADD_CONFERENCE_CALL = 10;
    private static final int MSG_ADD_EXISTING_CONNECTION = 21;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_RELEASED = 35;
    private static final int MSG_HANDLE_CREATE_CONFERENCE_COMPLETE = 37;
    private static final int MSG_HANDLE_CREATE_CONNECTION_COMPLETE = 1;
    private static final int MSG_ON_CONNECTION_EVENT = 26;
    private static final int MSG_ON_POST_DIAL_CHAR = 22;
    private static final int MSG_ON_POST_DIAL_WAIT = 12;
    private static final int MSG_ON_RTT_INITIATION_FAILURE = 31;
    private static final int MSG_ON_RTT_INITIATION_SUCCESS = 30;
    private static final int MSG_ON_RTT_REMOTELY_TERMINATED = 32;
    private static final int MSG_ON_RTT_UPGRADE_REQUEST = 33;
    private static final int MSG_PUT_EXTRAS = 24;
    private static final int MSG_QUERY_LOCATION = 39;
    private static final int MSG_QUERY_REMOTE_CALL_SERVICES = 13;
    private static final int MSG_REMOVE_CALL = 11;
    private static final int MSG_REMOVE_EXTRAS = 25;
    private static final int MSG_SET_ACTIVE = 2;
    private static final int MSG_SET_ADDRESS = 18;
    private static final int MSG_SET_AUDIO_ROUTE = 29;
    private static final int MSG_SET_CALLER_DISPLAY_NAME = 19;
    private static final int MSG_SET_CALL_DIRECTION = 38;
    private static final int MSG_SET_CONFERENCEABLE_CONNECTIONS = 20;
    private static final int MSG_SET_CONFERENCE_MERGE_FAILED = 23;
    private static final int MSG_SET_CONFERENCE_STATE = 36;
    private static final int MSG_SET_CONNECTION_CAPABILITIES = 8;
    private static final int MSG_SET_CONNECTION_PROPERTIES = 27;
    private static final int MSG_SET_DIALING = 4;
    private static final int MSG_SET_DISCONNECTED = 5;
    private static final int MSG_SET_IS_CONFERENCED = 9;
    private static final int MSG_SET_IS_VOIP_AUDIO_MODE = 16;
    private static final int MSG_SET_ON_HOLD = 6;
    private static final int MSG_SET_PHONE_ACCOUNT_CHANGED = 34;
    private static final int MSG_SET_PULLING = 28;
    private static final int MSG_SET_RINGBACK_REQUESTED = 7;
    private static final int MSG_SET_RINGING = 3;
    private static final int MSG_SET_STATUS_HINTS = 17;
    private static final int MSG_SET_VIDEO_CALL_PROVIDER = 15;
    private static final int MSG_SET_VIDEO_STATE = 14;
    private final com.android.internal.telecom.IConnectionServiceAdapter mDelegate;
    private final android.os.Handler mHandler = new android.os.Handler() { // from class: android.telecom.ConnectionServiceAdapterServant.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            try {
                internalHandleMessage(message);
            } catch (android.os.RemoteException e) {
            }
        }

        private void internalHandleMessage(android.os.Message message) throws android.os.RemoteException {
            com.android.internal.os.SomeArgs someArgs;
            switch (message.what) {
                case 1:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.handleCreateConnectionComplete((java.lang.String) someArgs.arg1, (android.telecom.ConnectionRequest) someArgs.arg2, (android.telecom.ParcelableConnection) someArgs.arg3, null);
                        return;
                    } finally {
                    }
                case 2:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setActive((java.lang.String) message.obj, null);
                    return;
                case 3:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setRinging((java.lang.String) message.obj, null);
                    return;
                case 4:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setDialing((java.lang.String) message.obj, null);
                    return;
                case 5:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setDisconnected((java.lang.String) someArgs.arg1, (android.telecom.DisconnectCause) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 6:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setOnHold((java.lang.String) message.obj, null);
                    return;
                case 7:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setRingbackRequested((java.lang.String) message.obj, message.arg1 == 1, null);
                    return;
                case 8:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setConnectionCapabilities((java.lang.String) message.obj, message.arg1, null);
                    return;
                case 9:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setIsConferenced((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 10:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.addConferenceCall((java.lang.String) someArgs.arg1, (android.telecom.ParcelableConference) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 11:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.removeCall((java.lang.String) message.obj, null);
                    return;
                case 12:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onPostDialWait((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 13:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.queryRemoteConnectionServices((com.android.internal.telecom.RemoteServiceCallback) someArgs.arg1, (java.lang.String) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 14:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setVideoState((java.lang.String) message.obj, message.arg1, null);
                    return;
                case 15:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setVideoProvider((java.lang.String) someArgs.arg1, (com.android.internal.telecom.IVideoProvider) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 16:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setIsVoipAudioMode((java.lang.String) message.obj, message.arg1 == 1, null);
                    return;
                case 17:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setStatusHints((java.lang.String) someArgs.arg1, (android.telecom.StatusHints) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 18:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setAddress((java.lang.String) someArgs.arg1, (android.net.Uri) someArgs.arg2, someArgs.argi1, null);
                        return;
                    } finally {
                    }
                case 19:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setCallerDisplayName((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, someArgs.argi1, null);
                        return;
                    } finally {
                    }
                case 20:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setConferenceableConnections((java.lang.String) someArgs.arg1, (java.util.List) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 21:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.addExistingConnection((java.lang.String) someArgs.arg1, (android.telecom.ParcelableConnection) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 22:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onPostDialChar((java.lang.String) someArgs.arg1, (char) someArgs.argi1, null);
                        return;
                    } finally {
                    }
                case 23:
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setConferenceMergeFailed((java.lang.String) ((com.android.internal.os.SomeArgs) message.obj).arg1, null);
                        return;
                    } finally {
                    }
                case 24:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.putExtras((java.lang.String) someArgs.arg1, (android.os.Bundle) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 25:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.removeExtras((java.lang.String) someArgs.arg1, (java.util.List) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 26:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onConnectionEvent((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, (android.os.Bundle) someArgs.arg3, null);
                        return;
                    } finally {
                    }
                case 27:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setConnectionProperties((java.lang.String) message.obj, message.arg1, null);
                    return;
                case 28:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setPulling((java.lang.String) message.obj, null);
                    return;
                case 29:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setAudioRoute((java.lang.String) someArgs.arg1, someArgs.argi1, (java.lang.String) someArgs.arg2, (android.telecom.Logging.Session.Info) someArgs.arg3);
                        return;
                    } finally {
                    }
                case 30:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onRttInitiationSuccess((java.lang.String) message.obj, null);
                    return;
                case 31:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onRttInitiationFailure((java.lang.String) message.obj, message.arg1, null);
                    return;
                case 32:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onRttSessionRemotelyTerminated((java.lang.String) message.obj, null);
                    return;
                case 33:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onRemoteRttRequest((java.lang.String) message.obj, null);
                    return;
                case 34:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onPhoneAccountChanged((java.lang.String) someArgs.arg1, (android.telecom.PhoneAccountHandle) someArgs.arg2, null);
                        return;
                    } finally {
                    }
                case 35:
                    android.telecom.ConnectionServiceAdapterServant.this.mDelegate.onConnectionServiceFocusReleased(null);
                    return;
                case 36:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setConferenceState((java.lang.String) someArgs.arg1, ((java.lang.Boolean) someArgs.arg2).booleanValue(), (android.telecom.Logging.Session.Info) someArgs.arg3);
                        return;
                    } finally {
                    }
                case 37:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.handleCreateConferenceComplete((java.lang.String) someArgs.arg1, (android.telecom.ConnectionRequest) someArgs.arg2, (android.telecom.ParcelableConference) someArgs.arg3, null);
                        return;
                    } finally {
                    }
                case 38:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.setCallDirection((java.lang.String) someArgs.arg1, someArgs.argi1, (android.telecom.Logging.Session.Info) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 39:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.ConnectionServiceAdapterServant.this.mDelegate.queryLocation((java.lang.String) someArgs.arg1, ((java.lang.Long) someArgs.arg2).longValue(), (java.lang.String) someArgs.arg3, (android.os.ResultReceiver) someArgs.arg4, (android.telecom.Logging.Session.Info) someArgs.arg5);
                        return;
                    } finally {
                    }
                default:
                    return;
            }
        }
    };
    private final com.android.internal.telecom.IConnectionServiceAdapter mStub = new com.android.internal.telecom.IConnectionServiceAdapter.Stub() { // from class: android.telecom.ConnectionServiceAdapterServant.2
        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConnectionComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = connectionRequest;
            obtain.arg3 = parcelableConnection;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(1, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConferenceComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = connectionRequest;
            obtain.arg3 = parcelableConference;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(37, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setActive(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(2, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRinging(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(3, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDialing(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(4, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setPulling(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(28, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = disconnectCause;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(5, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setOnHold(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(6, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRingbackRequested(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(7, z ? 1 : 0, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionCapabilities(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(8, i, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionProperties(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(27, i, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceMergeFailed(java.lang.String str, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(23, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setIsConferenced(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(9, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void addConferenceCall(java.lang.String str, android.telecom.ParcelableConference parcelableConference, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = parcelableConference;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(10, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void removeCall(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(11, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialWait(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(12, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialChar(java.lang.String str, char c, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.argi1 = c;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(22, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryRemoteConnectionServices(com.android.internal.telecom.RemoteServiceCallback remoteServiceCallback, java.lang.String str, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = remoteServiceCallback;
            obtain.arg2 = str;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(13, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoState(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(14, i, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoProvider(java.lang.String str, com.android.internal.telecom.IVideoProvider iVideoProvider, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = iVideoProvider;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(15, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void setIsVoipAudioMode(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(16, z ? 1 : 0, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void setStatusHints(java.lang.String str, android.telecom.StatusHints statusHints, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = statusHints;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(17, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void setAddress(java.lang.String str, android.net.Uri uri, int i, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = uri;
            obtain.argi1 = i;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(18, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void setCallerDisplayName(java.lang.String str, java.lang.String str2, int i, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            obtain.argi1 = i;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(19, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void setConferenceableConnections(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = list;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(20, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void addExistingConnection(java.lang.String str, android.telecom.ParcelableConnection parcelableConnection, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = parcelableConnection;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(21, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void putExtras(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = bundle;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(24, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void removeExtras(java.lang.String str, java.util.List<java.lang.String> list, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = list;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(25, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void setAudioRoute(java.lang.String str, int i, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.argi1 = i;
            obtain.arg2 = str2;
            obtain.arg3 = info;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(29, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public final void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            obtain.arg3 = bundle;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(26, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationSuccess(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(30, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationFailure(java.lang.String str, int i, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(31, i, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttSessionRemotelyTerminated(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(32, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRemoteRttRequest(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(33, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPhoneAccountChanged(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = phoneAccountHandle;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(34, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onConnectionServiceFocusReleased(android.telecom.Logging.Session.Info info) {
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(35).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void resetConnectionTime(java.lang.String str, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceState(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = java.lang.Boolean.valueOf(z);
            obtain.arg3 = info;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(36, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setCallDirection(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.argi1 = i;
            obtain.arg2 = info;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(38, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void requestCallEndpointChange(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryLocation(java.lang.String str, long j, java.lang.String str2, android.os.ResultReceiver resultReceiver, android.telecom.Logging.Session.Info info) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = java.lang.Long.valueOf(j);
            obtain.arg3 = str2;
            obtain.arg4 = resultReceiver;
            obtain.arg5 = info;
            android.telecom.ConnectionServiceAdapterServant.this.mHandler.obtainMessage(39, obtain).sendToTarget();
        }
    };

    public ConnectionServiceAdapterServant(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter) {
        this.mDelegate = iConnectionServiceAdapter;
    }

    public com.android.internal.telecom.IConnectionServiceAdapter getStub() {
        return this.mStub;
    }
}
