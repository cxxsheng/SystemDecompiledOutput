package android.telecom;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class Phone {
    public static final int SDK_VERSION_R = 30;
    private android.telecom.CallAudioState mCallAudioState;
    private final java.lang.String mCallingPackage;
    private final android.telecom.InCallAdapter mInCallAdapter;
    private final int mTargetSdkVersion;
    private final java.util.Map<java.lang.String, android.telecom.Call> mCallByTelecomCallId = new android.util.ArrayMap();
    private final java.util.List<android.telecom.Call> mCalls = new java.util.concurrent.CopyOnWriteArrayList();
    private final java.util.List<android.telecom.Call> mUnmodifiableCalls = java.util.Collections.unmodifiableList(this.mCalls);
    private final java.util.List<android.telecom.Phone.Listener> mListeners = new java.util.concurrent.CopyOnWriteArrayList();
    private boolean mCanAddCall = true;
    private final java.lang.Object mLock = new java.lang.Object();

    public static abstract class Listener {
        @java.lang.Deprecated
        public void onAudioStateChanged(android.telecom.Phone phone, android.telecom.AudioState audioState) {
        }

        public void onCallAudioStateChanged(android.telecom.Phone phone, android.telecom.CallAudioState callAudioState) {
        }

        public void onBringToForeground(android.telecom.Phone phone, boolean z) {
        }

        public void onCallAdded(android.telecom.Phone phone, android.telecom.Call call) {
        }

        public void onCallRemoved(android.telecom.Phone phone, android.telecom.Call call) {
        }

        public void onCanAddCallChanged(android.telecom.Phone phone, boolean z) {
        }

        public void onSilenceRinger(android.telecom.Phone phone) {
        }
    }

    Phone(android.telecom.InCallAdapter inCallAdapter, java.lang.String str, int i) {
        this.mInCallAdapter = inCallAdapter;
        this.mCallingPackage = str;
        this.mTargetSdkVersion = i;
    }

    final void internalAddCall(android.telecom.ParcelableCall parcelableCall) {
        if (this.mTargetSdkVersion < 30 && parcelableCall.getState() == 12) {
            android.telecom.Log.i(this, "Skipping adding audio processing call for sdk compatibility", new java.lang.Object[0]);
            return;
        }
        android.telecom.Call callById = getCallById(parcelableCall.getId());
        if (callById == null) {
            android.telecom.Call call = new android.telecom.Call(this, parcelableCall.getId(), this.mInCallAdapter, parcelableCall.getState(), this.mCallingPackage, this.mTargetSdkVersion);
            synchronized (this.mLock) {
                this.mCallByTelecomCallId.put(parcelableCall.getId(), call);
                this.mCalls.add(call);
            }
            checkCallTree(parcelableCall);
            call.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
            fireCallAdded(call);
            if (call.getState() == 7) {
                internalRemoveCall(call);
                return;
            }
            return;
        }
        android.telecom.Log.w(this, "Call %s added, but it was already present", callById.internalGetCallId());
        checkCallTree(parcelableCall);
        callById.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
    }

    final void internalRemoveCall(android.telecom.Call call) {
        synchronized (this.mLock) {
            this.mCallByTelecomCallId.remove(call.internalGetCallId());
            this.mCalls.remove(call);
        }
        android.telecom.InCallService.VideoCall videoCall = call.getVideoCall();
        if (videoCall != null) {
            videoCall.destroy();
        }
        fireCallRemoved(call);
    }

    final void internalUpdateCall(android.telecom.ParcelableCall parcelableCall) {
        if (this.mTargetSdkVersion < 30 && parcelableCall.getState() == 12) {
            android.telecom.Log.i(this, "removing audio processing call during update for sdk compatibility", new java.lang.Object[0]);
            android.telecom.Call callById = getCallById(parcelableCall.getId());
            if (callById != null) {
                internalRemoveCall(callById);
                return;
            }
            return;
        }
        android.telecom.Call callById2 = getCallById(parcelableCall.getId());
        if (callById2 != null) {
            checkCallTree(parcelableCall);
            callById2.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
        } else if (this.mTargetSdkVersion < 30) {
            if (parcelableCall.getState() == 4 || parcelableCall.getState() == 13) {
                android.telecom.Log.i(this, "adding call during update for sdk compatibility", new java.lang.Object[0]);
                internalAddCall(parcelableCall);
            }
        }
    }

    android.telecom.Call getCallById(java.lang.String str) {
        android.telecom.Call call;
        synchronized (this.mLock) {
            call = this.mCallByTelecomCallId.get(str);
        }
        return call;
    }

    final void internalSetPostDialWait(java.lang.String str, java.lang.String str2) {
        android.telecom.Call callById = getCallById(str);
        if (callById != null) {
            callById.internalSetPostDialWait(str2);
        }
    }

    final void internalCallAudioStateChanged(android.telecom.CallAudioState callAudioState) {
        if (!java.util.Objects.equals(this.mCallAudioState, callAudioState)) {
            this.mCallAudioState = callAudioState;
            fireCallAudioStateChanged(callAudioState);
        }
    }

    final android.telecom.Call internalGetCallByTelecomId(java.lang.String str) {
        return getCallById(str);
    }

    final void internalBringToForeground(boolean z) {
        fireBringToForeground(z);
    }

    final void internalSetCanAddCall(boolean z) {
        if (this.mCanAddCall != z) {
            this.mCanAddCall = z;
            fireCanAddCallChanged(z);
        }
    }

    final void internalSilenceRinger() {
        fireSilenceRinger();
    }

    final void internalOnConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        android.telecom.Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnConnectionEvent(str2, bundle);
        }
    }

    final void internalOnRttUpgradeRequest(java.lang.String str, int i) {
        android.telecom.Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnRttUpgradeRequest(i);
        }
    }

    final void internalOnRttInitiationFailure(java.lang.String str, int i) {
        android.telecom.Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnRttInitiationFailure(i);
        }
    }

    final void internalOnHandoverFailed(java.lang.String str, int i) {
        android.telecom.Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnHandoverFailed(i);
        }
    }

    final void internalOnHandoverComplete(java.lang.String str) {
        android.telecom.Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnHandoverComplete();
        }
    }

    final void destroy() {
        for (android.telecom.Call call : this.mCalls) {
            android.telecom.InCallService.VideoCall videoCall = call.getVideoCall();
            if (videoCall != null) {
                videoCall.destroy();
            }
            if (call.getState() != 7) {
                call.internalSetDisconnected();
            }
        }
    }

    public final void addListener(android.telecom.Phone.Listener listener) {
        this.mListeners.add(listener);
    }

    public final void removeListener(android.telecom.Phone.Listener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
        }
    }

    public final java.util.List<android.telecom.Call> getCalls() {
        return this.mUnmodifiableCalls;
    }

    public final boolean canAddCall() {
        return this.mCanAddCall;
    }

    public final void setMuted(boolean z) {
        this.mInCallAdapter.mute(z);
    }

    public final void setAudioRoute(int i) {
        this.mInCallAdapter.setAudioRoute(i);
    }

    public void requestBluetoothAudio(java.lang.String str) {
        this.mInCallAdapter.requestBluetoothAudio(str);
    }

    public void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallEndpointException> outcomeReceiver) {
        this.mInCallAdapter.requestCallEndpointChange(callEndpoint, executor, outcomeReceiver);
    }

    public final void setProximitySensorOn() {
        this.mInCallAdapter.turnProximitySensorOn();
    }

    public final void setProximitySensorOff(boolean z) {
        this.mInCallAdapter.turnProximitySensorOff(z);
    }

    @java.lang.Deprecated
    public final android.telecom.AudioState getAudioState() {
        return new android.telecom.AudioState(this.mCallAudioState);
    }

    public final android.telecom.CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    private void fireCallAdded(android.telecom.Call call) {
        java.util.Iterator<android.telecom.Phone.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallAdded(this, call);
        }
    }

    private void fireCallRemoved(android.telecom.Call call) {
        java.util.Iterator<android.telecom.Phone.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallRemoved(this, call);
        }
    }

    private void fireCallAudioStateChanged(android.telecom.CallAudioState callAudioState) {
        for (android.telecom.Phone.Listener listener : this.mListeners) {
            listener.onCallAudioStateChanged(this, callAudioState);
            listener.onAudioStateChanged(this, new android.telecom.AudioState(callAudioState));
        }
    }

    private void fireBringToForeground(boolean z) {
        java.util.Iterator<android.telecom.Phone.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onBringToForeground(this, z);
        }
    }

    private void fireCanAddCallChanged(boolean z) {
        java.util.Iterator<android.telecom.Phone.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCanAddCallChanged(this, z);
        }
    }

    private void fireSilenceRinger() {
        java.util.Iterator<android.telecom.Phone.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onSilenceRinger(this);
        }
    }

    private void checkCallTree(android.telecom.ParcelableCall parcelableCall) {
        if (parcelableCall.getChildCallIds() != null) {
            for (int i = 0; i < parcelableCall.getChildCallIds().size(); i++) {
                if (!this.mCallByTelecomCallId.containsKey(parcelableCall.getChildCallIds().get(i))) {
                    android.telecom.Log.wtf(this, "ParcelableCall %s has nonexistent child %s", parcelableCall.getId(), parcelableCall.getChildCallIds().get(i));
                }
            }
        }
    }
}
