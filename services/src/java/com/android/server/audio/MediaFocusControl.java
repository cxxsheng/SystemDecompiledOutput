package com.android.server.audio;

/* loaded from: classes.dex */
public class MediaFocusControl implements com.android.server.audio.PlayerFocusEnforcer {
    static final boolean DEBUG = false;
    static final int DUCKING_IN_APP_SDK_LEVEL = 25;
    static final boolean ENFORCE_DUCKING = true;
    static final boolean ENFORCE_DUCKING_FOR_NEW = true;
    static final boolean ENFORCE_FADEOUT_FOR_FOCUS_LOSS = true;
    static final boolean ENFORCE_MUTING_FOR_RING_OR_CALL = true;
    private static final int MAX_STACK_SIZE = 100;
    private static final int MSG_L_FOCUS_LOSS_AFTER_FADE = 1;
    private static final int MSL_L_FORGET_UID = 2;
    private static final int RING_CALL_MUTING_ENFORCEMENT_DELAY_MS = 100;
    private static final java.lang.String TAG = "MediaFocusControl";
    private static final java.lang.String mMetricsId = "audio.focus";
    private final android.app.AppOpsManager mAppOps;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mExtFocusChangeLock"})
    private long mExtFocusChangeCounter;

    @android.annotation.NonNull
    private final com.android.server.audio.PlayerFocusEnforcer mFocusEnforcer;
    private android.os.Handler mFocusHandler;
    private android.os.HandlerThread mFocusThread;
    private boolean mMultiAudioFocusEnabled;
    private static final java.lang.Object mAudioFocusLock = new java.lang.Object();
    private static final com.android.server.utils.EventLogger mEventLogger = new com.android.server.utils.EventLogger(50, "focus commands as seen by MediaFocusControl");
    private static final int[] USAGES_TO_MUTE_IN_RING_OR_CALL = {1, 14};
    private boolean mRingOrCallActive = false;
    private final java.lang.Object mExtFocusChangeLock = new java.lang.Object();
    private final java.util.Stack<com.android.server.audio.FocusRequester> mFocusStack = new java.util.Stack<>();
    java.util.ArrayList<com.android.server.audio.FocusRequester> mMultiAudioFocusList = new java.util.ArrayList<>();
    private boolean mNotifyFocusOwnerOnDuck = true;
    private java.util.ArrayList<android.media.audiopolicy.IAudioPolicyCallback> mFocusFollowers = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    @android.annotation.Nullable
    private android.media.audiopolicy.IAudioPolicyCallback mFocusPolicy = null;

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    @android.annotation.Nullable
    private android.media.audiopolicy.IAudioPolicyCallback mPreviousFocusPolicy = null;
    private java.util.HashMap<java.lang.String, com.android.server.audio.FocusRequester> mFocusOwnersForFocusPolicy = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    @android.annotation.Nullable
    private android.os.IBinder mFocusFreezerForTest = null;

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    @android.annotation.Nullable
    private android.os.IBinder.DeathRecipient mFocusFreezerDeathHandler = null;

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    @android.annotation.Nullable
    private int[] mFocusFreezeExemptUids = null;

    protected MediaFocusControl(android.content.Context context, com.android.server.audio.PlayerFocusEnforcer playerFocusEnforcer) {
        this.mMultiAudioFocusEnabled = false;
        this.mContext = context;
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService("appops");
        this.mFocusEnforcer = playerFocusEnforcer;
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mMultiAudioFocusEnabled = android.provider.Settings.System.getIntForUser(contentResolver, "multi_audio_focus_enabled", 0, contentResolver.getUserId()) != 0;
        initFocusThreading();
    }

    protected void dump(java.io.PrintWriter printWriter) {
        printWriter.println("\nMediaFocusControl dump time: " + java.text.DateFormat.getTimeInstance().format(new java.util.Date()));
        dumpFocusStack(printWriter);
        printWriter.println("\n");
        mEventLogger.dump(printWriter);
        dumpMultiAudioFocus(printWriter);
    }

    public long getFocusFadeOutDurationForTest() {
        return getFadeOutDurationMillis(new android.media.AudioAttributes.Builder().setUsage(1).build());
    }

    public long getFocusUnmuteDelayAfterFadeOutForTest() {
        return getFadeInDelayForOffendersMillis(new android.media.AudioAttributes.Builder().setUsage(1).build());
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public boolean duckPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester2, boolean z) {
        return this.mFocusEnforcer.duckPlayers(focusRequester, focusRequester2, z);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void restoreVShapedPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester) {
        this.mFocusEnforcer.restoreVShapedPlayers(focusRequester);
        this.mFocusHandler.removeEqualMessages(2, new com.android.server.audio.MediaFocusControl.ForgetFadeUidInfo(focusRequester.getClientUid()));
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void mutePlayersForCall(int[] iArr) {
        this.mFocusEnforcer.mutePlayersForCall(iArr);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void unmutePlayersForCall() {
        this.mFocusEnforcer.unmutePlayersForCall();
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public boolean fadeOutPlayers(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester2) {
        return this.mFocusEnforcer.fadeOutPlayers(focusRequester, focusRequester2);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void forgetUid(int i) {
        this.mFocusEnforcer.forgetUid(i);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public long getFadeOutDurationMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return 0L;
        }
        return this.mFocusEnforcer.getFadeOutDurationMillis(audioAttributes);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public long getFadeInDelayForOffendersMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return 0L;
        }
        return this.mFocusEnforcer.getFadeInDelayForOffendersMillis(audioAttributes);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public boolean shouldEnforceFade() {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return true;
        }
        return this.mFocusEnforcer.shouldEnforceFade();
    }

    void noFocusForSuspendedApp(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (mAudioFocusLock) {
            try {
                java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mFocusStack.iterator();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                while (it.hasNext()) {
                    com.android.server.audio.FocusRequester next = it.next();
                    if (next.hasSameUid(i) && next.hasSamePackage(str)) {
                        arrayList.add(next.getClientId());
                        mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("focus owner:" + next.getClientId() + " in uid:" + i + " pack: " + str + " getting AUDIOFOCUS_LOSS due to app suspension").printLog(TAG));
                        next.dispatchFocusChange(-1);
                    }
                }
                java.util.Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    removeFocusStackEntry((java.lang.String) it2.next(), false, true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean hasAudioFocusUsers() {
        boolean z;
        synchronized (mAudioFocusLock) {
            z = !this.mFocusStack.empty();
        }
        return z;
    }

    protected boolean maybeDiscardAudioFocusOwner() {
        synchronized (mAudioFocusLock) {
            try {
                if (this.mFocusStack.empty()) {
                    return true;
                }
                com.android.server.audio.FocusRequester peek = this.mFocusStack.peek();
                if (peek.isAlwaysVisibleUser()) {
                    return false;
                }
                this.mFocusStack.pop();
                peek.handleFocusLoss(-1, null, false);
                peek.release();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    java.util.List<android.media.AudioFocusInfo> getFocusStack() {
        java.util.ArrayList arrayList;
        synchronized (mAudioFocusLock) {
            try {
                arrayList = new java.util.ArrayList(this.mFocusStack.size());
                java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mFocusStack.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().toAudioFocusInfo());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    protected int getExclusiveFocusOwnerUid() {
        synchronized (mAudioFocusLock) {
            try {
                if (this.mFocusStack.empty()) {
                    return -1;
                }
                com.android.server.audio.FocusRequester peek = this.mFocusStack.peek();
                if (peek.getGainRequest() != 4) {
                    return -1;
                }
                return peek.getClientUid();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean sendFocusLoss(@android.annotation.NonNull android.media.AudioFocusInfo audioFocusInfo) {
        com.android.server.audio.FocusRequester focusRequester;
        synchronized (mAudioFocusLock) {
            try {
                java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mFocusStack.iterator();
                while (true) {
                    focusRequester = null;
                    if (!it.hasNext()) {
                        break;
                    }
                    com.android.server.audio.FocusRequester next = it.next();
                    if (next.getClientId().equals(audioFocusInfo.getClientId())) {
                        next.handleFocusLoss(-1, null, false);
                        focusRequester = next;
                        break;
                    }
                }
                if (focusRequester == null) {
                    return false;
                }
                this.mFocusStack.remove(focusRequester);
                focusRequester.release();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    private void notifyTopOfAudioFocusStack() {
        if (!this.mFocusStack.empty() && canReassignAudioFocus()) {
            this.mFocusStack.peek().handleFocusGain(1);
        }
        if (this.mMultiAudioFocusEnabled && !this.mMultiAudioFocusList.isEmpty()) {
            java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mMultiAudioFocusList.iterator();
            while (it.hasNext()) {
                com.android.server.audio.FocusRequester next = it.next();
                if (isLockedFocusOwner(next)) {
                    next.handleFocusGain(1);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    private void propagateFocusLossFromGain_syncAf(int i, com.android.server.audio.FocusRequester focusRequester, boolean z) {
        java.util.LinkedList linkedList = new java.util.LinkedList();
        if (!this.mFocusStack.empty()) {
            java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                com.android.server.audio.FocusRequester next = it.next();
                if (next.handleFocusLossFromGain(i, focusRequester, z)) {
                    linkedList.add(next.getClientId());
                }
            }
        }
        if (this.mMultiAudioFocusEnabled && !this.mMultiAudioFocusList.isEmpty()) {
            java.util.Iterator<com.android.server.audio.FocusRequester> it2 = this.mMultiAudioFocusList.iterator();
            while (it2.hasNext()) {
                com.android.server.audio.FocusRequester next2 = it2.next();
                if (next2.handleFocusLossFromGain(i, focusRequester, z)) {
                    linkedList.add(next2.getClientId());
                }
            }
        }
        java.util.Iterator it3 = linkedList.iterator();
        while (it3.hasNext()) {
            removeFocusStackEntry((java.lang.String) it3.next(), false, true);
        }
    }

    private void dumpFocusStack(java.io.PrintWriter printWriter) {
        printWriter.println("\nAudio Focus stack entries (last is top of stack):");
        synchronized (mAudioFocusLock) {
            try {
                java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mFocusStack.iterator();
                while (it.hasNext()) {
                    it.next().dump(printWriter);
                }
                printWriter.println("\n");
                if (this.mFocusPolicy == null) {
                    printWriter.println("No external focus policy\n");
                } else {
                    printWriter.println("External focus policy: " + this.mFocusPolicy + ", focus owners:\n");
                    dumpExtFocusPolicyFocusOwners(printWriter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println("\n");
        printWriter.println(" Notify on duck:  " + this.mNotifyFocusOwnerOnDuck + "\n");
        printWriter.println(" In ring or call: " + this.mRingOrCallActive + "\n");
    }

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    private void removeFocusStackEntry(java.lang.String str, boolean z, boolean z2) {
        if (!this.mFocusStack.empty() && this.mFocusStack.peek().hasSameClient(str)) {
            com.android.server.audio.FocusRequester pop = this.mFocusStack.pop();
            pop.maybeRelease();
            r1 = z2 ? pop.toAudioFocusInfo() : null;
            if (z) {
                notifyTopOfAudioFocusStack();
            }
        } else {
            java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                com.android.server.audio.FocusRequester next = it.next();
                if (next.hasSameClient(str)) {
                    android.util.Log.i(TAG, "AudioFocus  removeFocusStackEntry(): removing entry for " + str);
                    it.remove();
                    if (z2) {
                        r1 = next.toAudioFocusInfo();
                    }
                    next.maybeRelease();
                }
            }
        }
        if (r1 != null) {
            r1.clearLossReceived();
            notifyExtPolicyFocusLoss_syncAf(r1, false);
        }
        if (this.mMultiAudioFocusEnabled && !this.mMultiAudioFocusList.isEmpty()) {
            java.util.Iterator<com.android.server.audio.FocusRequester> it2 = this.mMultiAudioFocusList.iterator();
            while (it2.hasNext()) {
                com.android.server.audio.FocusRequester next2 = it2.next();
                if (next2.hasSameClient(str)) {
                    it2.remove();
                    next2.release();
                }
            }
            if (z) {
                notifyTopOfAudioFocusStack();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    public void removeFocusStackEntryOnDeath(android.os.IBinder iBinder) {
        boolean z = !this.mFocusStack.isEmpty() && this.mFocusStack.peek().hasSameBinder(iBinder);
        java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mFocusStack.iterator();
        while (it.hasNext()) {
            com.android.server.audio.FocusRequester next = it.next();
            if (next.hasSameBinder(iBinder)) {
                android.util.Log.i(TAG, "AudioFocus  removeFocusStackEntryOnDeath(): removing entry for " + iBinder);
                mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("focus requester:" + next.getClientId() + " in uid:" + next.getClientUid() + " pack:" + next.getPackageName() + " died"));
                notifyExtPolicyFocusLoss_syncAf(next.toAudioFocusInfo(), false);
                it.remove();
                next.release();
            }
        }
        if (z) {
            notifyTopOfAudioFocusStack();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    public void removeFocusEntryForExtPolicyOnDeath(android.os.IBinder iBinder) {
        if (this.mFocusOwnersForFocusPolicy.isEmpty()) {
            return;
        }
        java.util.Iterator<java.util.Map.Entry<java.lang.String, com.android.server.audio.FocusRequester>> it = this.mFocusOwnersForFocusPolicy.entrySet().iterator();
        while (it.hasNext()) {
            com.android.server.audio.FocusRequester value = it.next().getValue();
            if (value.hasSameBinder(iBinder)) {
                it.remove();
                mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("focus requester:" + value.getClientId() + " in uid:" + value.getClientUid() + " pack:" + value.getPackageName() + " died"));
                value.release();
                notifyExtFocusPolicyFocusAbandon_syncAf(value.toAudioFocusInfo());
                return;
            }
        }
    }

    private boolean canReassignAudioFocus() {
        if (!this.mFocusStack.isEmpty() && isLockedFocusOwner(this.mFocusStack.peek())) {
            return false;
        }
        return true;
    }

    private boolean isLockedFocusOwner(com.android.server.audio.FocusRequester focusRequester) {
        return focusRequester.hasSameClient("AudioFocus_For_Phone_Ring_And_Calls") || focusRequester.isLockedFocusOwner();
    }

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    private int pushBelowLockedFocusOwnersAndPropagate(com.android.server.audio.FocusRequester focusRequester) {
        int size = this.mFocusStack.size();
        for (int size2 = this.mFocusStack.size() - 1; size2 >= 0; size2--) {
            if (isLockedFocusOwner(this.mFocusStack.elementAt(size2))) {
                size = size2;
            }
        }
        if (size == this.mFocusStack.size()) {
            android.util.Log.e(TAG, "No exclusive focus owner found in propagateFocusLossFromGain_syncAf()", new java.lang.Exception());
            propagateFocusLossFromGain_syncAf(focusRequester.getGainRequest(), focusRequester, false);
            this.mFocusStack.push(focusRequester);
            return 1;
        }
        this.mFocusStack.insertElementAt(focusRequester, size);
        java.util.LinkedList linkedList = new java.util.LinkedList();
        for (int i = size - 1; i >= 0; i--) {
            if (this.mFocusStack.elementAt(i).handleFocusLossFromGain(focusRequester.getGainRequest(), focusRequester, false)) {
                linkedList.add(this.mFocusStack.elementAt(i).getClientId());
            }
        }
        java.util.Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            removeFocusStackEntry((java.lang.String) it.next(), false, true);
        }
        return 2;
    }

    protected class AudioFocusDeathHandler implements android.os.IBinder.DeathRecipient {
        private android.os.IBinder mCb;

        AudioFocusDeathHandler(android.os.IBinder iBinder) {
            this.mCb = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.audio.MediaFocusControl.mAudioFocusLock) {
                try {
                    if (com.android.server.audio.MediaFocusControl.this.mFocusPolicy != null) {
                        com.android.server.audio.MediaFocusControl.this.removeFocusEntryForExtPolicyOnDeath(this.mCb);
                    } else {
                        com.android.server.audio.MediaFocusControl.this.removeFocusStackEntryOnDeath(this.mCb);
                        if (com.android.server.audio.MediaFocusControl.this.mMultiAudioFocusEnabled && !com.android.server.audio.MediaFocusControl.this.mMultiAudioFocusList.isEmpty()) {
                            java.util.Iterator<com.android.server.audio.FocusRequester> it = com.android.server.audio.MediaFocusControl.this.mMultiAudioFocusList.iterator();
                            while (it.hasNext()) {
                                com.android.server.audio.FocusRequester next = it.next();
                                if (next.hasSameBinder(this.mCb)) {
                                    it.remove();
                                    next.release();
                                }
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    protected void setDuckingInExtPolicyAvailable(boolean z) {
        this.mNotifyFocusOwnerOnDuck = !z;
    }

    boolean mustNotifyFocusOwnerOnDuck() {
        return this.mNotifyFocusOwnerOnDuck;
    }

    void addFocusFollower(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        boolean z;
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            try {
                java.util.Iterator<android.media.audiopolicy.IAudioPolicyCallback> it = this.mFocusFollowers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (it.next().asBinder().equals(iAudioPolicyCallback.asBinder())) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    return;
                }
                this.mFocusFollowers.add(iAudioPolicyCallback);
                notifyExtPolicyCurrentFocusAsync(iAudioPolicyCallback);
            } finally {
            }
        }
    }

    void removeFocusFollower(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            try {
                java.util.Iterator<android.media.audiopolicy.IAudioPolicyCallback> it = this.mFocusFollowers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    android.media.audiopolicy.IAudioPolicyCallback next = it.next();
                    if (next.asBinder().equals(iAudioPolicyCallback.asBinder())) {
                        this.mFocusFollowers.remove(next);
                        break;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setFocusPolicy(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, boolean z) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            if (z) {
                try {
                    this.mPreviousFocusPolicy = this.mFocusPolicy;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mFocusPolicy = iAudioPolicyCallback;
        }
    }

    void unsetFocusPolicy(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, boolean z) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            try {
                if (this.mFocusPolicy == iAudioPolicyCallback) {
                    if (z) {
                        this.mFocusPolicy = this.mPreviousFocusPolicy;
                    } else {
                        this.mFocusPolicy = null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void notifyExtPolicyCurrentFocusAsync(final android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        new java.lang.Thread() { // from class: com.android.server.audio.MediaFocusControl.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (com.android.server.audio.MediaFocusControl.mAudioFocusLock) {
                    if (com.android.server.audio.MediaFocusControl.this.mFocusStack.isEmpty()) {
                        return;
                    }
                    try {
                        iAudioPolicyCallback.notifyAudioFocusGrant(((com.android.server.audio.FocusRequester) com.android.server.audio.MediaFocusControl.this.mFocusStack.peek()).toAudioFocusInfo(), 1);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(com.android.server.audio.MediaFocusControl.TAG, "Can't call notifyAudioFocusGrant() on IAudioPolicyCallback " + iAudioPolicyCallback.asBinder(), e);
                    }
                }
            }
        }.start();
    }

    void notifyExtPolicyFocusGrant_syncAf(android.media.AudioFocusInfo audioFocusInfo, int i) {
        java.util.Iterator<android.media.audiopolicy.IAudioPolicyCallback> it = this.mFocusFollowers.iterator();
        while (it.hasNext()) {
            android.media.audiopolicy.IAudioPolicyCallback next = it.next();
            try {
                next.notifyAudioFocusGrant(audioFocusInfo, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Can't call notifyAudioFocusGrant() on IAudioPolicyCallback " + next.asBinder(), e);
            }
        }
    }

    void notifyExtPolicyFocusLoss_syncAf(android.media.AudioFocusInfo audioFocusInfo, boolean z) {
        java.util.Iterator<android.media.audiopolicy.IAudioPolicyCallback> it = this.mFocusFollowers.iterator();
        while (it.hasNext()) {
            android.media.audiopolicy.IAudioPolicyCallback next = it.next();
            try {
                next.notifyAudioFocusLoss(audioFocusInfo, z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Can't call notifyAudioFocusLoss() on IAudioPolicyCallback " + next.asBinder(), e);
            }
        }
    }

    boolean notifyExtFocusPolicyFocusRequest_syncAf(android.media.AudioFocusInfo audioFocusInfo, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, @android.annotation.NonNull android.os.IBinder iBinder) {
        boolean z;
        synchronized (this.mExtFocusChangeLock) {
            long j = this.mExtFocusChangeCounter;
            this.mExtFocusChangeCounter = 1 + j;
            audioFocusInfo.setGen(j);
        }
        com.android.server.audio.FocusRequester focusRequester = this.mFocusOwnersForFocusPolicy.get(audioFocusInfo.getClientId());
        if (focusRequester != null) {
            if (focusRequester.hasSameDispatcher(iAudioFocusDispatcher)) {
                z = false;
            } else {
                focusRequester.release();
                z = true;
            }
        } else {
            z = true;
        }
        if (z) {
            com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler = new com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler(iBinder);
            try {
                iBinder.linkToDeath(audioFocusDeathHandler, 0);
                this.mFocusOwnersForFocusPolicy.put(audioFocusInfo.getClientId(), new com.android.server.audio.FocusRequester(audioFocusInfo, iAudioFocusDispatcher, iBinder, audioFocusDeathHandler, this));
            } catch (android.os.RemoteException e) {
                return false;
            }
        }
        try {
            this.mFocusPolicy.notifyAudioFocusRequest(audioFocusInfo, 1);
            return true;
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Can't call notifyAudioFocusRequest() on IAudioPolicyCallback " + this.mFocusPolicy.asBinder(), e2);
            return false;
        }
    }

    void setFocusRequestResultFromExtPolicy(android.media.AudioFocusInfo audioFocusInfo, int i) {
        synchronized (this.mExtFocusChangeLock) {
            try {
                if (audioFocusInfo.getGen() > this.mExtFocusChangeCounter) {
                    return;
                }
                synchronized (mAudioFocusLock) {
                    try {
                        com.android.server.audio.FocusRequester focusRequesterLocked = getFocusRequesterLocked(audioFocusInfo.getClientId(), i == 0);
                        if (focusRequesterLocked != null) {
                            focusRequesterLocked.dispatchFocusResultFromExtPolicy(i);
                            if (android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
                                focusRequesterLocked.handleFocusGainFromRequest(i);
                            }
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    boolean notifyExtFocusPolicyFocusAbandon_syncAf(android.media.AudioFocusInfo audioFocusInfo) {
        if (this.mFocusPolicy == null) {
            return false;
        }
        com.android.server.audio.FocusRequester remove = this.mFocusOwnersForFocusPolicy.remove(audioFocusInfo.getClientId());
        if (remove != null) {
            remove.release();
        }
        try {
            this.mFocusPolicy.notifyAudioFocusAbandon(audioFocusInfo);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Can't call notifyAudioFocusAbandon() on IAudioPolicyCallback " + this.mFocusPolicy.asBinder(), e);
            return true;
        }
    }

    int dispatchFocusChange(android.media.AudioFocusInfo audioFocusInfo, int i) {
        synchronized (mAudioFocusLock) {
            try {
                com.android.server.audio.FocusRequester focusRequesterLocked = getFocusRequesterLocked(audioFocusInfo.getClientId(), i == -1);
                if (focusRequesterLocked == null) {
                    return 0;
                }
                return focusRequesterLocked.dispatchFocusChange(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int dispatchFocusChangeWithFade(android.media.AudioFocusInfo audioFocusInfo, int i, java.util.List<android.media.AudioFocusInfo> list) {
        synchronized (mAudioFocusLock) {
            try {
                java.lang.String clientId = audioFocusInfo.getClientId();
                com.android.server.audio.FocusRequester focusRequesterLocked = getFocusRequesterLocked(clientId, false);
                if (focusRequesterLocked == null) {
                    return 0;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i2 = 0; i2 < list.size(); i2++) {
                    com.android.server.audio.FocusRequester focusRequesterLocked2 = getFocusRequesterLocked(list.get(i2).getClientId(), false);
                    if (focusRequesterLocked2 != null) {
                        arrayList.add(focusRequesterLocked2);
                    }
                }
                int dispatchFocusChangeWithFadeLocked = focusRequesterLocked.dispatchFocusChangeWithFadeLocked(i, arrayList);
                if (dispatchFocusChangeWithFadeLocked != 2 && i == -1) {
                    this.mFocusOwnersForFocusPolicy.remove(clientId);
                }
                return dispatchFocusChangeWithFadeLocked;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    private com.android.server.audio.FocusRequester getFocusRequesterLocked(java.lang.String str, boolean z) {
        if (this.mFocusPolicy == null) {
            return null;
        }
        if (z) {
            return this.mFocusOwnersForFocusPolicy.remove(str);
        }
        return this.mFocusOwnersForFocusPolicy.get(str);
    }

    private void dumpExtFocusPolicyFocusOwners(java.io.PrintWriter printWriter) {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, com.android.server.audio.FocusRequester>> it = this.mFocusOwnersForFocusPolicy.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().dump(printWriter);
        }
    }

    protected int getCurrentAudioFocus() {
        synchronized (mAudioFocusLock) {
            try {
                if (this.mFocusStack.empty()) {
                    return 0;
                }
                return this.mFocusStack.peek().getGainRequest();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected static int getFocusRampTimeMs(int i, android.media.AudioAttributes audioAttributes) {
        switch (audioAttributes.getUsage()) {
            case 1:
            case 14:
                return 1000;
            case 2:
            case 3:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 1002:
                return 500;
            case 4:
            case 6:
            case 11:
            case 12:
            case 16:
            case 1003:
                return com.android.server.am.ProcessList.PREVIOUS_APP_ADJ;
            default:
                return 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    protected int requestAudioFocus(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, java.lang.String str3, int i2, int i3, boolean z, int i4, boolean z2) {
        int i5;
        ?? r15;
        int i6;
        android.media.AudioFocusInfo audioFocusInfo;
        boolean z3;
        boolean z4;
        boolean z5;
        byte b;
        int callingUid;
        new android.media.MediaMetrics.Item(mMetricsId).setUid(android.os.Binder.getCallingUid()).set(android.media.MediaMetrics.Property.CALLING_PACKAGE, str2).set(android.media.MediaMetrics.Property.CLIENT_NAME, str).set(android.media.MediaMetrics.Property.EVENT, "requestAudioFocus").set(android.media.MediaMetrics.Property.FLAGS, java.lang.Integer.valueOf(i2)).set(android.media.MediaMetrics.Property.FOCUS_CHANGE_HINT, android.media.AudioManager.audioFocusToString(i)).record();
        int callingUid2 = i2 == 8 ? i4 : android.os.Binder.getCallingUid();
        mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("requestAudioFocus() from uid/pid " + callingUid2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingPid() + " AA=" + audioAttributes.usageToString() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + audioAttributes.contentTypeToString() + " clientId=" + str + " callingPack=" + str2 + " req=" + i + " flags=0x" + java.lang.Integer.toHexString(i2) + " sdk=" + i3).printLog(TAG));
        if (!iBinder.pingBinder()) {
            android.util.Log.e(TAG, " AudioFocus DOA client for requestAudioFocus(), aborting.");
            return 0;
        }
        int noteOp = this.mAppOps.noteOp(32, android.os.Binder.getCallingUid(), str2, str3, (java.lang.String) null);
        if (!z2 && noteOp != 0) {
            return 0;
        }
        synchronized (mAudioFocusLock) {
            try {
                if (isFocusFrozenForTest()) {
                    if ((i2 & 8) == 8) {
                        callingUid = i4;
                    } else {
                        callingUid = android.os.Binder.getCallingUid();
                    }
                    if (isFocusFrozenForTestForUid(callingUid)) {
                        android.util.Log.i(TAG, "requestAudioFocus: focus frozen for test for uid:" + callingUid);
                        return 0;
                    }
                    android.util.Log.i(TAG, "requestAudioFocus: focus frozen for test but uid:" + callingUid + " is exempt");
                }
                if (this.mFocusStack.size() > 100) {
                    android.util.Log.e(TAG, "Max AudioFocus stack size reached, failing requestAudioFocus()");
                    return 0;
                }
                int i7 = (!this.mRingOrCallActive) & ("AudioFocus_For_Phone_Ring_And_Calls".compareTo(str) == 0);
                if (i7 != false) {
                    this.mRingOrCallActive = true;
                }
                if (this.mFocusPolicy != null) {
                    i5 = 100;
                    r15 = 0;
                    i6 = callingUid2;
                    audioFocusInfo = new android.media.AudioFocusInfo(audioAttributes, callingUid2, str, str2, i, 0, i2, i3);
                } else {
                    i5 = 100;
                    r15 = 0;
                    i6 = callingUid2;
                    audioFocusInfo = null;
                }
                if (canReassignAudioFocus()) {
                    z3 = r15;
                } else {
                    if ((i2 & 1) == 0) {
                        return r15;
                    }
                    z3 = true;
                }
                if (this.mFocusPolicy != null) {
                    return notifyExtFocusPolicyFocusRequest_syncAf(audioFocusInfo, iAudioFocusDispatcher, iBinder) ? i5 : r15;
                }
                com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler = new com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler(iBinder);
                try {
                    iBinder.linkToDeath(audioFocusDeathHandler, r15);
                    if (this.mFocusStack.empty() || !this.mFocusStack.peek().hasSameClient(str)) {
                        z4 = true;
                    } else {
                        com.android.server.audio.FocusRequester peek = this.mFocusStack.peek();
                        if (peek.getGainRequest() != i || peek.getGrantFlags() != i2) {
                            z4 = true;
                            if (!z3) {
                                this.mFocusStack.pop();
                                peek.release();
                            }
                        } else {
                            iBinder.unlinkToDeath(audioFocusDeathHandler, r15);
                            notifyExtPolicyFocusGrant_syncAf(peek.toAudioFocusInfo(), 1);
                            return 1;
                        }
                    }
                    removeFocusStackEntry(str, r15, r15);
                    boolean z6 = z4;
                    com.android.server.audio.FocusRequester focusRequester = new com.android.server.audio.FocusRequester(audioAttributes, i, i2, iAudioFocusDispatcher, iBinder, str, audioFocusDeathHandler, str2, i6, this, i3);
                    if (!this.mMultiAudioFocusEnabled || i != z6) {
                        z5 = z;
                    } else if (i7 != false) {
                        if (this.mMultiAudioFocusList.isEmpty()) {
                            z5 = z;
                        } else {
                            java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mMultiAudioFocusList.iterator();
                            while (it.hasNext()) {
                                it.next().handleFocusLossFromGain(i, focusRequester, z);
                            }
                            z5 = z;
                        }
                    } else {
                        if (!this.mMultiAudioFocusList.isEmpty()) {
                            java.util.Iterator<com.android.server.audio.FocusRequester> it2 = this.mMultiAudioFocusList.iterator();
                            while (it2.hasNext()) {
                                if (it2.next().getClientUid() == android.os.Binder.getCallingUid()) {
                                    b = false;
                                    break;
                                }
                            }
                        }
                        b = z6 ? 1 : 0;
                        if (b != false) {
                            this.mMultiAudioFocusList.add(focusRequester);
                        }
                        focusRequester.handleFocusGainFromRequest(z6 ? 1 : 0);
                        notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), z6 ? 1 : 0);
                        return z6 ? 1 : 0;
                    }
                    if (z3) {
                        int pushBelowLockedFocusOwnersAndPropagate = pushBelowLockedFocusOwnersAndPropagate(focusRequester);
                        if (pushBelowLockedFocusOwnersAndPropagate != 0) {
                            notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), pushBelowLockedFocusOwnersAndPropagate);
                        }
                        return pushBelowLockedFocusOwnersAndPropagate;
                    }
                    propagateFocusLossFromGain_syncAf(i, focusRequester, z5);
                    this.mFocusStack.push(focusRequester);
                    focusRequester.handleFocusGainFromRequest(z6 ? 1 : 0);
                    notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), z6 ? 1 : 0);
                    if ((i7 & true) != false) {
                        runAudioCheckerForRingOrCallAsync(z6);
                    }
                    return z6 ? 1 : 0;
                } catch (android.os.RemoteException e) {
                    int i8 = r15;
                    android.util.Log.w(TAG, "AudioFocus  requestAudioFocus() could not link to " + iBinder + " binder death");
                    return i8;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected int abandonAudioFocus(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) {
        new android.media.MediaMetrics.Item(mMetricsId).setUid(android.os.Binder.getCallingUid()).set(android.media.MediaMetrics.Property.CALLING_PACKAGE, str2).set(android.media.MediaMetrics.Property.CLIENT_NAME, str).set(android.media.MediaMetrics.Property.EVENT, "abandonAudioFocus").record();
        mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("abandonAudioFocus() from uid/pid " + android.os.Binder.getCallingUid() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingPid() + " clientId=" + str).printLog(TAG));
        try {
        } catch (java.util.ConcurrentModificationException e) {
            android.util.Log.e(TAG, "FATAL EXCEPTION AudioFocus  abandonAudioFocus() caused " + e);
            e.printStackTrace();
        }
        synchronized (mAudioFocusLock) {
            try {
                if (this.mFocusPolicy != null && notifyExtFocusPolicyFocusAbandon_syncAf(new android.media.AudioFocusInfo(audioAttributes, android.os.Binder.getCallingUid(), str, str2, 0, 0, 0, 0))) {
                    return 1;
                }
                boolean z = this.mRingOrCallActive & ("AudioFocus_For_Phone_Ring_And_Calls".compareTo(str) == 0);
                if (z) {
                    this.mRingOrCallActive = false;
                }
                removeFocusStackEntry(str, true, true);
                if (z & true) {
                    runAudioCheckerForRingOrCallAsync(false);
                }
                return 1;
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    private boolean isFocusFrozenForTest() {
        return this.mFocusFreezerForTest != null;
    }

    @com.android.internal.annotations.GuardedBy({"mAudioFocusLock"})
    private boolean isFocusFrozenForTestForUid(int i) {
        if (isFocusFrozenForTest()) {
            return false;
        }
        for (int i2 : this.mFocusFreezeExemptUids) {
            if (i2 == i) {
                return false;
            }
        }
        return true;
    }

    protected boolean enterAudioFocusFreezeForTest(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull int[] iArr) {
        android.util.Log.i(TAG, "enterAudioFocusFreezeForTest UIDs exempt:" + java.util.Arrays.toString(iArr));
        synchronized (mAudioFocusLock) {
            if (this.mFocusFreezerForTest != null) {
                android.util.Log.e(TAG, "Error enterAudioFocusFreezeForTest: focus already frozen");
                return false;
            }
            try {
                this.mFocusFreezerDeathHandler = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.audio.MediaFocusControl.2
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        android.util.Log.i(com.android.server.audio.MediaFocusControl.TAG, "Audio focus freezer died, exiting focus freeze for test");
                        com.android.server.audio.MediaFocusControl.this.releaseFocusFreeze();
                    }
                };
                iBinder.linkToDeath(this.mFocusFreezerDeathHandler, 0);
                this.mFocusFreezerForTest = iBinder;
                this.mFocusFreezeExemptUids = (int[]) iArr.clone();
                return true;
            } catch (android.os.RemoteException e) {
                this.mFocusFreezerForTest = null;
                this.mFocusFreezeExemptUids = null;
                return false;
            }
        }
    }

    protected boolean exitAudioFocusFreezeForTest(@android.annotation.NonNull android.os.IBinder iBinder) {
        java.lang.String str;
        synchronized (mAudioFocusLock) {
            try {
                if (this.mFocusFreezerForTest != iBinder) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Error exitAudioFocusFreezeForTest: ");
                    if (this.mFocusFreezerForTest == null) {
                        str = "call to exit while not frozen";
                    } else {
                        str = "call to exit not coming from freeze owner";
                    }
                    sb.append(str);
                    android.util.Log.e(TAG, sb.toString());
                    return false;
                }
                this.mFocusFreezerForTest.unlinkToDeath(this.mFocusFreezerDeathHandler, 0);
                releaseFocusFreeze();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseFocusFreeze() {
        synchronized (mAudioFocusLock) {
            this.mFocusFreezerDeathHandler = null;
            this.mFocusFreezeExemptUids = null;
            this.mFocusFreezerForTest = null;
        }
    }

    protected void unregisterAudioFocusClient(java.lang.String str) {
        synchronized (mAudioFocusLock) {
            removeFocusStackEntry(str, false, true);
        }
    }

    private void runAudioCheckerForRingOrCallAsync(final boolean z) {
        new java.lang.Thread() { // from class: com.android.server.audio.MediaFocusControl.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (z) {
                    try {
                        java.lang.Thread.sleep(100L);
                    } catch (java.lang.InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (com.android.server.audio.MediaFocusControl.mAudioFocusLock) {
                    try {
                        if (com.android.server.audio.MediaFocusControl.this.mRingOrCallActive) {
                            com.android.server.audio.MediaFocusControl.this.mFocusEnforcer.mutePlayersForCall(com.android.server.audio.MediaFocusControl.USAGES_TO_MUTE_IN_RING_OR_CALL);
                        } else {
                            com.android.server.audio.MediaFocusControl.this.mFocusEnforcer.unmutePlayersForCall();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }.start();
    }

    public void updateMultiAudioFocus(boolean z) {
        android.util.Log.d(TAG, "updateMultiAudioFocus( " + z + " )");
        this.mMultiAudioFocusEnabled = z;
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        android.provider.Settings.System.putIntForUser(contentResolver, "multi_audio_focus_enabled", z ? 1 : 0, contentResolver.getUserId());
        if (!this.mFocusStack.isEmpty()) {
            this.mFocusStack.peek().handleFocusLoss(-1, null, false);
        }
        if (!z && !this.mMultiAudioFocusList.isEmpty()) {
            java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mMultiAudioFocusList.iterator();
            while (it.hasNext()) {
                it.next().handleFocusLoss(-1, null, false);
            }
            this.mMultiAudioFocusList.clear();
        }
    }

    public boolean getMultiAudioFocusEnabled() {
        return this.mMultiAudioFocusEnabled;
    }

    long getFadeOutDurationOnFocusLossMillis(android.media.AudioAttributes audioAttributes) {
        return getFadeOutDurationMillis(audioAttributes);
    }

    private void dumpMultiAudioFocus(java.io.PrintWriter printWriter) {
        printWriter.println("Multi Audio Focus enabled :" + this.mMultiAudioFocusEnabled);
        if (!this.mMultiAudioFocusList.isEmpty()) {
            printWriter.println("Multi Audio Focus List:");
            printWriter.println("------------------------------");
            java.util.Iterator<com.android.server.audio.FocusRequester> it = this.mMultiAudioFocusList.iterator();
            while (it.hasNext()) {
                it.next().dump(printWriter);
            }
            printWriter.println("------------------------------");
        }
    }

    void postDelayedLossAfterFade(com.android.server.audio.FocusRequester focusRequester, long j) {
        this.mFocusHandler.sendMessageDelayed(this.mFocusHandler.obtainMessage(1, focusRequester), j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postForgetUidLater(com.android.server.audio.FocusRequester focusRequester) {
        this.mFocusHandler.sendMessageDelayed(this.mFocusHandler.obtainMessage(2, new com.android.server.audio.MediaFocusControl.ForgetFadeUidInfo(focusRequester.getClientUid())), getFadeInDelayForOffendersMillis(focusRequester.getAudioAttributes()));
    }

    private void initFocusThreading() {
        this.mFocusThread = new android.os.HandlerThread(TAG);
        this.mFocusThread.start();
        this.mFocusHandler = new android.os.Handler(this.mFocusThread.getLooper()) { // from class: com.android.server.audio.MediaFocusControl.4
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        synchronized (com.android.server.audio.MediaFocusControl.mAudioFocusLock) {
                            try {
                                com.android.server.audio.FocusRequester focusRequester = (com.android.server.audio.FocusRequester) message.obj;
                                if (focusRequester.isInFocusLossLimbo()) {
                                    focusRequester.dispatchFocusChange(-1);
                                    focusRequester.release();
                                    com.android.server.audio.MediaFocusControl.this.postForgetUidLater(focusRequester);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        return;
                    case 2:
                        com.android.server.audio.MediaFocusControl.this.mFocusEnforcer.forgetUid(((com.android.server.audio.MediaFocusControl.ForgetFadeUidInfo) message.obj).mUid);
                        return;
                    default:
                        return;
                }
            }
        };
    }

    private static final class ForgetFadeUidInfo {
        private final int mUid;

        ForgetFadeUidInfo(int i) {
            this.mUid = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && com.android.server.audio.MediaFocusControl.ForgetFadeUidInfo.class == obj.getClass() && ((com.android.server.audio.MediaFocusControl.ForgetFadeUidInfo) obj).mUid == this.mUid) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.mUid;
        }
    }
}
