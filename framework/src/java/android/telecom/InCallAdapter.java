package android.telecom;

/* loaded from: classes3.dex */
public final class InCallAdapter {
    private final com.android.internal.telecom.IInCallAdapter mAdapter;

    public InCallAdapter(com.android.internal.telecom.IInCallAdapter iInCallAdapter) {
        this.mAdapter = iInCallAdapter;
    }

    public void answerCall(java.lang.String str, int i) {
        try {
            this.mAdapter.answerCall(str, i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void deflectCall(java.lang.String str, android.net.Uri uri) {
        try {
            this.mAdapter.deflectCall(str, uri);
        } catch (android.os.RemoteException e) {
        }
    }

    public void rejectCall(java.lang.String str, boolean z, java.lang.String str2) {
        try {
            this.mAdapter.rejectCall(str, z, str2);
        } catch (android.os.RemoteException e) {
        }
    }

    public void rejectCall(java.lang.String str, int i) {
        try {
            this.mAdapter.rejectCallWithReason(str, i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void transferCall(java.lang.String str, android.net.Uri uri, boolean z) {
        try {
            this.mAdapter.transferCall(str, uri, z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void transferCall(java.lang.String str, java.lang.String str2) {
        try {
            this.mAdapter.consultativeTransfer(str, str2);
        } catch (android.os.RemoteException e) {
        }
    }

    public void disconnectCall(java.lang.String str) {
        try {
            this.mAdapter.disconnectCall(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void holdCall(java.lang.String str) {
        try {
            this.mAdapter.holdCall(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void unholdCall(java.lang.String str) {
        try {
            this.mAdapter.unholdCall(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void mute(boolean z) {
        try {
            this.mAdapter.mute(z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void setAudioRoute(int i) {
        try {
            this.mAdapter.setAudioRoute(i, null);
        } catch (android.os.RemoteException e) {
        }
    }

    public void enterBackgroundAudioProcessing(java.lang.String str) {
        try {
            this.mAdapter.enterBackgroundAudioProcessing(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void exitBackgroundAudioProcessing(java.lang.String str, boolean z) {
        try {
            this.mAdapter.exitBackgroundAudioProcessing(str, z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void requestBluetoothAudio(java.lang.String str) {
        try {
            this.mAdapter.setAudioRoute(2, str);
        } catch (android.os.RemoteException e) {
        }
    }

    /* renamed from: android.telecom.InCallAdapter$1, reason: invalid class name */
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
                    executor.execute(new java.lang.Runnable() { // from class: android.telecom.InCallAdapter$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(null);
                        }
                    });
                } else {
                    java.util.concurrent.Executor executor2 = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                    executor2.execute(new java.lang.Runnable() { // from class: android.telecom.InCallAdapter$1$$ExternalSyntheticLambda1
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

    public void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallEndpointException> outcomeReceiver) {
        try {
            this.mAdapter.requestCallEndpointChange(callEndpoint, new android.telecom.InCallAdapter.AnonymousClass1(null, executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            android.telecom.Log.d(this, "Remote exception calling requestCallEndpointChange", new java.lang.Object[0]);
        }
    }

    public void playDtmfTone(java.lang.String str, char c) {
        try {
            this.mAdapter.playDtmfTone(str, c);
        } catch (android.os.RemoteException e) {
        }
    }

    public void stopDtmfTone(java.lang.String str) {
        try {
            this.mAdapter.stopDtmfTone(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void postDialContinue(java.lang.String str, boolean z) {
        try {
            this.mAdapter.postDialContinue(str, z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void phoneAccountSelected(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) {
        try {
            this.mAdapter.phoneAccountSelected(str, phoneAccountHandle, z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void conference(java.lang.String str, java.lang.String str2) {
        try {
            this.mAdapter.conference(str, str2);
        } catch (android.os.RemoteException e) {
        }
    }

    public void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list) {
        try {
            this.mAdapter.addConferenceParticipants(str, list);
        } catch (android.os.RemoteException e) {
        }
    }

    public void splitFromConference(java.lang.String str) {
        try {
            this.mAdapter.splitFromConference(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void mergeConference(java.lang.String str) {
        try {
            this.mAdapter.mergeConference(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void swapConference(java.lang.String str) {
        try {
            this.mAdapter.swapConference(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void pullExternalCall(java.lang.String str) {
        try {
            this.mAdapter.pullExternalCall(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendCallEvent(java.lang.String str, java.lang.String str2, int i, android.os.Bundle bundle) {
        try {
            this.mAdapter.sendCallEvent(str, str2, i, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void putExtras(java.lang.String str, android.os.Bundle bundle) {
        try {
            this.mAdapter.putExtras(str, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void putExtra(java.lang.String str, java.lang.String str2, boolean z) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putBoolean(str2, z);
            this.mAdapter.putExtras(str, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void putExtra(java.lang.String str, java.lang.String str2, int i) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt(str2, i);
            this.mAdapter.putExtras(str, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void putExtra(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(str2, str3);
            this.mAdapter.putExtras(str, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void removeExtras(java.lang.String str, java.util.List<java.lang.String> list) {
        try {
            this.mAdapter.removeExtras(str, list);
        } catch (android.os.RemoteException e) {
        }
    }

    public void turnProximitySensorOn() {
        try {
            this.mAdapter.turnOnProximitySensor();
        } catch (android.os.RemoteException e) {
        }
    }

    public void turnProximitySensorOff(boolean z) {
        try {
            this.mAdapter.turnOffProximitySensor(z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendRttRequest(java.lang.String str) {
        try {
            this.mAdapter.sendRttRequest(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void respondToRttRequest(java.lang.String str, int i, boolean z) {
        try {
            this.mAdapter.respondToRttRequest(str, i, z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void stopRtt(java.lang.String str) {
        try {
            this.mAdapter.stopRtt(str);
        } catch (android.os.RemoteException e) {
        }
    }

    public void setRttMode(java.lang.String str, int i) {
        try {
            this.mAdapter.setRttMode(str, i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void handoverTo(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, int i, android.os.Bundle bundle) {
        try {
            this.mAdapter.handoverTo(str, phoneAccountHandle, i, bundle);
        } catch (android.os.RemoteException e) {
        }
    }
}
