package android.telecom;

/* loaded from: classes3.dex */
final class ConnectionServiceAdapter implements android.os.IBinder.DeathRecipient {
    private final java.util.Set<com.android.internal.telecom.IConnectionServiceAdapter> mAdapters = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap(8, 0.9f, 1));

    ConnectionServiceAdapter() {
    }

    void addAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            if (it.next().asBinder() == iConnectionServiceAdapter.asBinder()) {
                android.telecom.Log.w(this, "Ignoring duplicate adapter addition.", new java.lang.Object[0]);
                return;
            }
        }
        if (this.mAdapters.add(iConnectionServiceAdapter)) {
            try {
                iConnectionServiceAdapter.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                this.mAdapters.remove(iConnectionServiceAdapter);
            }
        }
    }

    void removeAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter) {
        if (iConnectionServiceAdapter != null) {
            for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter2 : this.mAdapters) {
                if (iConnectionServiceAdapter2.asBinder() == iConnectionServiceAdapter.asBinder() && this.mAdapters.remove(iConnectionServiceAdapter2)) {
                    iConnectionServiceAdapter.asBinder().unlinkToDeath(this, 0);
                    return;
                }
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            com.android.internal.telecom.IConnectionServiceAdapter next = it.next();
            if (!next.asBinder().isBinderAlive()) {
                it.remove();
                next.asBinder().unlinkToDeath(this, 0);
            }
        }
    }

    void handleCreateConnectionComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConnection parcelableConnection) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().handleCreateConnectionComplete(str, connectionRequest, parcelableConnection, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void handleCreateConferenceComplete(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, android.telecom.ParcelableConference parcelableConference) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().handleCreateConferenceComplete(str, connectionRequest, parcelableConference, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setActive(java.lang.String str) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setActive(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setRinging(java.lang.String str) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setRinging(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setDialing(java.lang.String str) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setDialing(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setPulling(java.lang.String str) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setPulling(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setDisconnected(str, disconnectCause, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setOnHold(java.lang.String str) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setOnHold(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setRingbackRequested(java.lang.String str, boolean z) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setRingbackRequested(str, z, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setConnectionCapabilities(java.lang.String str, int i) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConnectionCapabilities(str, i, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setConnectionProperties(java.lang.String str, int i) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConnectionProperties(str, i, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setIsConferenced(java.lang.String str, java.lang.String str2) {
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                android.telecom.Log.d(this, "sending connection %s with conference %s", str, str2);
                iConnectionServiceAdapter.setIsConferenced(str, str2, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onConferenceMergeFailed(java.lang.String str) {
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                android.telecom.Log.d(this, "merge failed for call %s", str);
                iConnectionServiceAdapter.setConferenceMergeFailed(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void resetConnectionTime(java.lang.String str) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().resetConnectionTime(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void removeCall(java.lang.String str) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().removeCall(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onPostDialWait(java.lang.String str, java.lang.String str2) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onPostDialWait(str, str2, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onPostDialChar(java.lang.String str, char c) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onPostDialChar(str, c, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void addConferenceCall(java.lang.String str, android.telecom.ParcelableConference parcelableConference) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().addConferenceCall(str, parcelableConference, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void queryRemoteConnectionServices(com.android.internal.telecom.RemoteServiceCallback remoteServiceCallback, java.lang.String str) {
        if (this.mAdapters.size() == 1) {
            try {
                this.mAdapters.iterator().next().queryRemoteConnectionServices(remoteServiceCallback, str, android.telecom.Log.getExternalSession());
                return;
            } catch (android.os.RemoteException e) {
                android.telecom.Log.e(this, e, "Exception trying to query for remote CSs", new java.lang.Object[0]);
                return;
            }
        }
        try {
            remoteServiceCallback.onResult(java.util.Collections.EMPTY_LIST, java.util.Collections.EMPTY_LIST);
        } catch (android.os.RemoteException e2) {
            android.telecom.Log.e(this, e2, "Exception trying to query for remote CSs", new java.lang.Object[0]);
        }
    }

    void setVideoProvider(java.lang.String str, android.telecom.Connection.VideoProvider videoProvider) {
        com.android.internal.telecom.IVideoProvider iVideoProvider;
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            if (videoProvider == null) {
                iVideoProvider = null;
            } else {
                try {
                    iVideoProvider = videoProvider.getInterface();
                } catch (android.os.RemoteException e) {
                }
            }
            iConnectionServiceAdapter.setVideoProvider(str, iVideoProvider, android.telecom.Log.getExternalSession());
        }
    }

    void setIsVoipAudioMode(java.lang.String str, boolean z) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setIsVoipAudioMode(str, z, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setStatusHints(java.lang.String str, android.telecom.StatusHints statusHints) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setStatusHints(str, statusHints, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setAddress(java.lang.String str, android.net.Uri uri, int i) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setAddress(str, uri, i, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setCallerDisplayName(java.lang.String str, java.lang.String str2, int i) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setCallerDisplayName(str, str2, i, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setVideoState(java.lang.String str, int i) {
        android.telecom.Log.v(this, "setVideoState: %d", java.lang.Integer.valueOf(i));
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setVideoState(str, i, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setConferenceableConnections(java.lang.String str, java.util.List<java.lang.String> list) {
        android.telecom.Log.v(this, "setConferenceableConnections: %s, %s", str, list);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConferenceableConnections(str, list, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void addExistingConnection(java.lang.String str, android.telecom.ParcelableConnection parcelableConnection) {
        android.telecom.Log.v(this, "addExistingConnection: %s", str);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().addExistingConnection(str, parcelableConnection, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void putExtras(java.lang.String str, android.os.Bundle bundle) {
        android.telecom.Log.v(this, "putExtras: %s", str);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().putExtras(str, bundle, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void putExtra(java.lang.String str, java.lang.String str2, boolean z) {
        android.telecom.Log.v(this, "putExtra: %s %s=%b", str, str2, java.lang.Boolean.valueOf(z));
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putBoolean(str2, z);
                iConnectionServiceAdapter.putExtras(str, bundle, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void putExtra(java.lang.String str, java.lang.String str2, int i) {
        android.telecom.Log.v(this, "putExtra: %s %s=%d", str, str2, java.lang.Integer.valueOf(i));
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putInt(str2, i);
                iConnectionServiceAdapter.putExtras(str, bundle, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void putExtra(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.telecom.Log.v(this, "putExtra: %s %s=%s", str, str2, str3);
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putString(str2, str3);
                iConnectionServiceAdapter.putExtras(str, bundle, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void removeExtras(java.lang.String str, java.util.List<java.lang.String> list) {
        android.telecom.Log.v(this, "removeExtras: %s %s", str, list);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().removeExtras(str, list, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setAudioRoute(java.lang.String str, int i, java.lang.String str2) {
        android.telecom.Log.v(this, "setAudioRoute: %s %s %s", str, android.telecom.CallAudioState.audioRouteToString(i), str2);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setAudioRoute(str, i, str2, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void requestCallEndpointChange(java.lang.String str, android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallEndpointException> outcomeReceiver) {
        android.telecom.Log.v(this, "requestCallEndpointChange", new java.lang.Object[0]);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().requestCallEndpointChange(str, callEndpoint, new android.telecom.ConnectionServiceAdapter.AnonymousClass1(null, executor, outcomeReceiver), android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
                android.telecom.Log.d(this, "Remote exception calling requestCallEndpointChange", new java.lang.Object[0]);
            }
        }
    }

    /* renamed from: android.telecom.ConnectionServiceAdapter$1, reason: invalid class name */
    class AnonymousClass1 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, final android.os.Bundle bundle) {
            super.onReceiveResult(i, bundle);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (i == 0) {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telecom.ConnectionServiceAdapter$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(null);
                        }
                    });
                } else {
                    java.util.concurrent.Executor executor2 = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                    executor2.execute(new java.lang.Runnable() { // from class: android.telecom.ConnectionServiceAdapter$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError((android.telecom.CallEndpointException) bundle.getParcelable(android.telecom.CallEndpointException.CHANGE_ERROR, android.telecom.CallEndpointException.class));
                        }
                    });
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        android.telecom.Log.v(this, "onConnectionEvent: %s", str2);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onConnectionEvent(str, str2, bundle, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onRttInitiationSuccess(java.lang.String str) {
        android.telecom.Log.v(this, "onRttInitiationSuccess: %s", str);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRttInitiationSuccess(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onRttInitiationFailure(java.lang.String str, int i) {
        android.telecom.Log.v(this, "onRttInitiationFailure: %s", str);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRttInitiationFailure(str, i, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onRttSessionRemotelyTerminated(java.lang.String str) {
        android.telecom.Log.v(this, "onRttSessionRemotelyTerminated: %s", str);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRttSessionRemotelyTerminated(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onRemoteRttRequest(java.lang.String str) {
        android.telecom.Log.v(this, "onRemoteRttRequest: %s", str);
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRemoteRttRequest(str, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onPhoneAccountChanged(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle) {
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                android.telecom.Log.d(this, "onPhoneAccountChanged %s", str);
                iConnectionServiceAdapter.onPhoneAccountChanged(str, phoneAccountHandle, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void onConnectionServiceFocusReleased() {
        for (com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                android.telecom.Log.d(this, "onConnectionServiceFocusReleased", new java.lang.Object[0]);
                iConnectionServiceAdapter.onConnectionServiceFocusReleased(android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setConferenceState(java.lang.String str, boolean z) {
        android.telecom.Log.v(this, "setConferenceState: %s %b", str, java.lang.Boolean.valueOf(z));
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConferenceState(str, z, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void setCallDirection(java.lang.String str, int i) {
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setCallDirection(str, i, android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void queryLocation(java.lang.String str, long j, java.lang.String str2, java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.location.Location, android.telecom.QueryLocationException> outcomeReceiver) {
        android.telecom.Log.v(this, "queryLocation: %s %d", str, java.lang.Long.valueOf(j));
        java.util.Iterator<com.android.internal.telecom.IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().queryLocation(str, j, str2, new android.telecom.ConnectionServiceAdapter.AnonymousClass2(null, executor, outcomeReceiver), android.telecom.Log.getExternalSession());
            } catch (android.os.RemoteException e) {
                android.telecom.Log.d(this, "queryLocation: Exception e : " + e, new java.lang.Object[0]);
                executor.execute(new java.lang.Runnable() { // from class: android.telecom.ConnectionServiceAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(new android.telecom.QueryLocationException(e.getMessage(), 5));
                    }
                });
            }
        }
    }

    /* renamed from: android.telecom.ConnectionServiceAdapter$2, reason: invalid class name */
    class AnonymousClass2 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, final android.os.Bundle bundle) {
            super.onReceiveResult(i, bundle);
            if (i == 1) {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telecom.ConnectionServiceAdapter$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onResult((android.location.Location) bundle.getParcelable(android.telecom.Connection.EXTRA_KEY_QUERY_LOCATION, android.location.Location.class));
                    }
                });
            } else {
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telecom.ConnectionServiceAdapter$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError((android.telecom.QueryLocationException) bundle.getParcelable(android.telecom.QueryLocationException.QUERY_LOCATION_ERROR, android.telecom.QueryLocationException.class));
                    }
                });
            }
        }
    }
}
