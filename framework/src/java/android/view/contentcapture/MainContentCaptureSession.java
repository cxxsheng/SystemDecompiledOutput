package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class MainContentCaptureSession extends android.view.contentcapture.ContentCaptureSession {
    private static final java.lang.String CONTENT_CAPTURE_WRONG_THREAD_METRIC_ID = "content_capture.value_content_capture_wrong_thread_count";
    private static final boolean FORCE_FLUSH = true;
    private static final int MSG_FLUSH = 1;
    private static final java.lang.String TAG = android.view.contentcapture.MainContentCaptureSession.class.getSimpleName();
    private android.os.IBinder mApplicationToken;
    public android.content.ComponentName mComponentName;
    public android.view.contentprotection.ContentProtectionEventProcessor mContentProtectionEventProcessor;
    private final android.view.contentcapture.ContentCaptureManager.StrippedContext mContext;
    public android.view.contentcapture.IContentCaptureDirectManager mDirectServiceInterface;
    private android.os.IBinder.DeathRecipient mDirectServiceVulture;
    public java.util.ArrayList<android.view.contentcapture.ContentCaptureEvent> mEvents;
    private final android.util.LocalLog mFlushHistory;
    private final android.os.Handler mHandler;
    private final android.view.contentcapture.ContentCaptureManager mManager;
    private long mNextFlush;
    private final android.view.contentcapture.MainContentCaptureSession.SessionStateReceiver mSessionStateReceiver;
    private android.os.IBinder mShareableActivityToken;
    private final android.view.contentcapture.IContentCaptureManager mSystemServerInterface;
    private final java.util.concurrent.atomic.AtomicBoolean mDisabled = new java.util.concurrent.atomic.AtomicBoolean(false);
    private int mState = 0;
    private boolean mNextFlushForTextChanged = false;
    private final java.util.concurrent.atomic.AtomicInteger mWrongThreadCount = new java.util.concurrent.atomic.AtomicInteger(0);

    /* JADX INFO: Access modifiers changed from: private */
    static class SessionStateReceiver extends com.android.internal.os.IResultReceiver.Stub {
        private final java.lang.ref.WeakReference<android.view.contentcapture.MainContentCaptureSession> mMainSession;

        SessionStateReceiver(android.view.contentcapture.MainContentCaptureSession mainContentCaptureSession) {
            this.mMainSession = new java.lang.ref.WeakReference<>(mainContentCaptureSession);
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(final int i, android.os.Bundle bundle) {
            final android.os.IBinder iBinder;
            final android.view.contentcapture.MainContentCaptureSession mainContentCaptureSession = this.mMainSession.get();
            if (mainContentCaptureSession == null) {
                android.util.Log.w(android.view.contentcapture.MainContentCaptureSession.TAG, "received result after mina session released");
                return;
            }
            if (bundle != null) {
                if (bundle.getBoolean("enabled")) {
                    mainContentCaptureSession.mDisabled.set(i == 2);
                    return;
                }
                iBinder = bundle.getBinder("binder");
                if (iBinder == null) {
                    android.util.Log.wtf(android.view.contentcapture.MainContentCaptureSession.TAG, "No binder extra result");
                    mainContentCaptureSession.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$SessionStateReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.view.contentcapture.MainContentCaptureSession.this.resetSession(260);
                        }
                    });
                    return;
                }
            } else {
                iBinder = null;
            }
            mainContentCaptureSession.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$SessionStateReceiver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.contentcapture.MainContentCaptureSession.this.onSessionStarted(i, iBinder);
                }
            });
        }
    }

    public MainContentCaptureSession(android.view.contentcapture.ContentCaptureManager.StrippedContext strippedContext, android.view.contentcapture.ContentCaptureManager contentCaptureManager, android.os.Handler handler, android.view.contentcapture.IContentCaptureManager iContentCaptureManager) {
        this.mContext = strippedContext;
        this.mManager = contentCaptureManager;
        this.mHandler = handler;
        this.mSystemServerInterface = iContentCaptureManager;
        int i = this.mManager.mOptions.logHistorySize;
        this.mFlushHistory = i > 0 ? new android.util.LocalLog(i) : null;
        this.mSessionStateReceiver = new android.view.contentcapture.MainContentCaptureSession.SessionStateReceiver(this);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    android.view.contentcapture.ContentCaptureSession getMainCaptureSession() {
        return this;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    android.view.contentcapture.ContentCaptureSession newChild(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        android.view.contentcapture.ChildContentCaptureSession childContentCaptureSession = new android.view.contentcapture.ChildContentCaptureSession(this, contentCaptureContext);
        internalNotifyChildSessionStarted(this.mId, childContentCaptureSession.mId, contentCaptureContext);
        return childContentCaptureSession;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void start(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.ComponentName componentName, int i) {
        checkOnUiThread();
        if (isContentCaptureEnabled()) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "start(): token=" + iBinder + ", comp=" + android.content.ComponentName.flattenToShortString(componentName));
            }
            if (hasStarted()) {
                if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
                    android.util.Log.d(TAG, "ignoring handleStartSession(" + iBinder + "/" + android.content.ComponentName.flattenToShortString(componentName) + " while on state " + getStateAsString(this.mState));
                    return;
                }
                return;
            }
            this.mState = 1;
            this.mApplicationToken = iBinder;
            this.mShareableActivityToken = iBinder2;
            this.mComponentName = componentName;
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "handleStartSession(): token=" + iBinder + ", act=" + getDebugState() + ", id=" + this.mId);
            }
            try {
                this.mSystemServerInterface.startSession(this.mApplicationToken, this.mShareableActivityToken, componentName, this.mId, i, this.mSessionStateReceiver);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error starting session for " + componentName.flattenToShortString() + ": " + e);
            }
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$onDestroy$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDestroy$0() {
        try {
            flush(4);
        } finally {
            destroySession();
        }
    }

    public void onSessionStarted(int i, android.os.IBinder iBinder) {
        checkOnUiThread();
        if (iBinder != null) {
            this.mDirectServiceInterface = android.view.contentcapture.IContentCaptureDirectManager.Stub.asInterface(iBinder);
            this.mDirectServiceVulture = new android.os.IBinder.DeathRecipient() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda13
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    android.view.contentcapture.MainContentCaptureSession.this.lambda$onSessionStarted$1();
                }
            };
            try {
                iBinder.linkToDeath(this.mDirectServiceVulture, 0);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to link to death on " + iBinder + ": " + e);
            }
        }
        if (isContentProtectionEnabled()) {
            this.mContentProtectionEventProcessor = new android.view.contentprotection.ContentProtectionEventProcessor(this.mManager.getContentProtectionEventBuffer(), this.mHandler, this.mSystemServerInterface, this.mComponentName.getPackageName(), this.mManager.mOptions.contentProtectionOptions);
        } else {
            this.mContentProtectionEventProcessor = null;
        }
        if ((i & 4) != 0) {
            resetSession(i);
        } else {
            this.mState = i;
            this.mDisabled.set(false);
            lambda$scheduleFlush$2(7);
        }
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "handleSessionStarted() result: id=" + this.mId + " resultCode=" + i + ", state=" + getStateAsString(this.mState) + ", disabled=" + this.mDisabled.get() + ", binder=" + iBinder + ", events=" + (this.mEvents != null ? this.mEvents.size() : 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSessionStarted$1() {
        android.util.Log.w(TAG, "Keeping session " + this.mId + " when service died");
        this.mState = 1024;
        this.mDisabled.set(true);
    }

    /* renamed from: sendEvent, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$notifyWindowBoundsChanged$13(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, false);
    }

    private void sendEvent(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent, boolean z) {
        checkOnUiThread();
        int type = contentCaptureEvent.getType();
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "handleSendEvent(" + getDebugState() + "): " + contentCaptureEvent);
        }
        if (!hasStarted() && type != -1 && type != 6) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "handleSendEvent(" + getDebugState() + ", " + android.view.contentcapture.ContentCaptureEvent.getTypeAsString(type) + "): dropping because session not started yet");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "handleSendEvent(): ignoring when disabled");
                return;
            }
            return;
        }
        if (android.os.Trace.isTagEnabled(8L) && type == 4) {
            android.os.Trace.asyncTraceBegin(8L, "sendEventAsync", 0);
        }
        if (isContentProtectionReceiverEnabled()) {
            sendContentProtectionEvent(contentCaptureEvent);
        }
        if (isContentCaptureReceiverEnabled()) {
            sendContentCaptureEvent(contentCaptureEvent, z);
        }
        if (android.os.Trace.isTagEnabled(8L) && type == 5) {
            android.os.Trace.asyncTraceEnd(8L, "sendEventAsync", 0);
        }
    }

    private void sendContentProtectionEvent(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        checkOnUiThread();
        if (this.mContentProtectionEventProcessor != null) {
            this.mContentProtectionEventProcessor.processEvent(contentCaptureEvent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x019d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendContentCaptureEvent(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent, boolean z) {
        boolean z2;
        int size;
        int i;
        android.view.contentcapture.ContentCaptureEvent contentCaptureEvent2;
        android.view.contentcapture.ContentCaptureEvent contentCaptureEvent3;
        checkOnUiThread();
        int type = contentCaptureEvent.getType();
        int i2 = this.mManager.mOptions.maxBufferSize;
        if (this.mEvents == null) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "handleSendEvent(): creating buffer for " + i2 + " events");
            }
            this.mEvents = new java.util.ArrayList<>(i2);
        }
        int i3 = 3;
        if (type == 3) {
            java.lang.CharSequence text = contentCaptureEvent.getText();
            if (contentCaptureEvent.hasComposingSpan()) {
                int size2 = this.mEvents.size() - 1;
                while (true) {
                    if (size2 < 0) {
                        contentCaptureEvent3 = null;
                        break;
                    }
                    contentCaptureEvent3 = this.mEvents.get(size2);
                    if (contentCaptureEvent.getId().equals(contentCaptureEvent3.getId())) {
                        break;
                    } else {
                        size2--;
                    }
                }
                if (contentCaptureEvent3 != null && contentCaptureEvent3.hasComposingSpan()) {
                    java.lang.CharSequence text2 = contentCaptureEvent3.getText();
                    boolean z3 = (android.text.TextUtils.isEmpty(text2) || android.text.TextUtils.isEmpty(text)) ? false : true;
                    if (android.text.TextUtils.equals(text2, text) && contentCaptureEvent3.hasSameComposingSpan(contentCaptureEvent) && contentCaptureEvent3.hasSameSelectionSpan(contentCaptureEvent)) {
                        z2 = false;
                    } else if (!z3) {
                        z2 = true;
                    } else {
                        contentCaptureEvent3.mergeEvent(contentCaptureEvent);
                        z2 = false;
                    }
                    if (!z2 && android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                        android.util.Log.v(TAG, "Buffering VIEW_TEXT_CHANGED event, updated text=" + android.view.contentcapture.ContentCaptureHelper.getSanitizedString(text));
                    }
                    if (!this.mEvents.isEmpty() && type == 2) {
                        contentCaptureEvent2 = this.mEvents.get(this.mEvents.size() - 1);
                        if (contentCaptureEvent2.getType() == 2 && contentCaptureEvent.getSessionId() == contentCaptureEvent2.getSessionId()) {
                            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                                android.util.Log.v(TAG, "Buffering TYPE_VIEW_DISAPPEARED events for session " + contentCaptureEvent2.getSessionId());
                            }
                            contentCaptureEvent2.mergeEvent(contentCaptureEvent);
                            z2 = false;
                        }
                    }
                    if (z2) {
                        this.mEvents.add(contentCaptureEvent);
                    }
                    size = this.mEvents.size();
                    if (!(size < i2) && !z) {
                        if (type == 3) {
                            this.mNextFlushForTextChanged = true;
                            i = 6;
                        } else {
                            if (this.mNextFlushForTextChanged) {
                                if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                                    android.util.Log.i(TAG, "Not scheduling flush because next flush is for text changed");
                                    return;
                                }
                                return;
                            }
                            i = 5;
                        }
                        scheduleFlush(i, true);
                        return;
                    }
                    if (this.mState == 2 && size >= i2) {
                        if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
                            android.util.Log.d(TAG, "Closing session for " + getDebugState() + " after " + size + " delayed events");
                        }
                        resetSession(132);
                        return;
                    }
                    switch (type) {
                        case -2:
                            i3 = 4;
                            break;
                        case -1:
                            break;
                        case 4:
                            i3 = 9;
                            break;
                        case 5:
                            i3 = 10;
                            break;
                        default:
                            if (!z) {
                                i3 = 1;
                                break;
                            } else {
                                i3 = 8;
                                break;
                            }
                    }
                    flush(i3);
                }
            }
        }
        z2 = true;
        if (!this.mEvents.isEmpty()) {
            contentCaptureEvent2 = this.mEvents.get(this.mEvents.size() - 1);
            if (contentCaptureEvent2.getType() == 2) {
                if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                }
                contentCaptureEvent2.mergeEvent(contentCaptureEvent);
                z2 = false;
            }
        }
        if (z2) {
        }
        size = this.mEvents.size();
        if (!(size < i2)) {
        }
        if (this.mState == 2) {
        }
        switch (type) {
            case -2:
                break;
            case -1:
                break;
            case 4:
                break;
            case 5:
                break;
        }
        flush(i3);
    }

    private boolean hasStarted() {
        checkOnUiThread();
        return this.mState != 0;
    }

    private void scheduleFlush(final int i, boolean z) {
        int i2;
        checkOnUiThread();
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "handleScheduleFlush(" + getDebugState(i) + ", checkExisting=" + z);
        }
        if (!hasStarted()) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "handleScheduleFlush(): session not started yet");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            android.util.Log.e(TAG, "handleScheduleFlush(" + getDebugState(i) + "): should not be called when disabled. events=" + (this.mEvents == null ? null : java.lang.Integer.valueOf(this.mEvents.size())));
            return;
        }
        if (z && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        if (i == 6) {
            i2 = this.mManager.mOptions.textChangeFlushingFrequencyMs;
        } else {
            if (i != 5 && android.view.contentcapture.ContentCaptureHelper.sDebug) {
                android.util.Log.d(TAG, "handleScheduleFlush(" + getDebugState(i) + "): not a timeout reason because mDirectServiceInterface is not ready yet");
            }
            i2 = this.mManager.mOptions.idleFlushingFrequencyMs;
        }
        long j = i2;
        this.mNextFlush = java.lang.System.currentTimeMillis() + j;
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "handleScheduleFlush(): scheduled to flush in " + i2 + "ms: " + android.util.TimeUtils.logTimeOfDay(this.mNextFlush));
        }
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$scheduleFlush$2(i);
            }
        }, 1, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: flushIfNeeded, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleFlush$2(int i) {
        checkOnUiThread();
        if (this.mEvents == null || this.mEvents.isEmpty()) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "Nothing to flush");
                return;
            }
            return;
        }
        flush(i);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void flush(int i) {
        checkOnUiThread();
        if (this.mEvents == null || this.mEvents.size() == 0) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "Don't flush for empty event buffer.");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            android.util.Log.e(TAG, "handleForceFlush(" + getDebugState(i) + "): should not be when disabled");
            return;
        }
        if (!isContentCaptureReceiverEnabled()) {
            return;
        }
        if (this.mDirectServiceInterface == null) {
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "handleForceFlush(" + getDebugState(i) + "): hold your horses, client not ready: " + this.mEvents);
            }
            if (!this.mHandler.hasMessages(1)) {
                scheduleFlush(i, false);
                return;
            }
            return;
        }
        this.mNextFlushForTextChanged = false;
        int size = this.mEvents.size();
        java.lang.String flushReasonAsString = getFlushReasonAsString(i);
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "Flushing " + size + " event(s) for " + getDebugState(i) + (i == 8 ? ". The force flush event " + android.view.contentcapture.ContentCaptureEvent.getTypeAsString(this.mEvents.get(size - 1).getType()) : ""));
        }
        if (this.mFlushHistory != null) {
            this.mFlushHistory.log("r=" + flushReasonAsString + " s=" + size + " m=" + this.mManager.mOptions.maxBufferSize + " i=" + this.mManager.mOptions.idleFlushingFrequencyMs);
        }
        try {
            this.mHandler.removeMessages(1);
            this.mDirectServiceInterface.sendEvents(clearEvents(), i, this.mManager.mOptions);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error sending " + size + " for " + getDebugState() + ": " + e);
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        internalNotifyContextUpdated(this.mId, contentCaptureContext);
    }

    private android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> clearEvents() {
        checkOnUiThread();
        if (this.mEvents == null) {
            return new android.content.pm.ParceledListSlice<>(java.util.Collections.EMPTY_LIST);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mEvents);
        this.mEvents.clear();
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    public void destroySession() {
        checkOnUiThread();
        if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
            android.util.Log.d(TAG, "Destroying session (ctx=" + this.mContext + ", id=" + this.mId + ") with " + (this.mEvents == null ? 0 : this.mEvents.size()) + " event(s) for " + getDebugState());
        }
        reportWrongThreadMetric();
        try {
            this.mSystemServerInterface.finishSession(this.mId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error destroying system-service session " + this.mId + " for " + getDebugState() + ": " + e);
        }
        if (this.mDirectServiceInterface != null) {
            this.mDirectServiceInterface.asBinder().unlinkToDeath(this.mDirectServiceVulture, 0);
        }
        this.mDirectServiceInterface = null;
        this.mContentProtectionEventProcessor = null;
    }

    public void resetSession(int i) {
        checkOnUiThread();
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "handleResetSession(" + getActivityName() + "): from " + getStateAsString(this.mState) + " to " + getStateAsString(i));
        }
        this.mState = i;
        this.mDisabled.set((i & 4) != 0);
        this.mApplicationToken = null;
        this.mShareableActivityToken = null;
        this.mComponentName = null;
        this.mEvents = null;
        if (this.mDirectServiceInterface != null) {
            try {
                this.mDirectServiceInterface.asBinder().unlinkToDeath(this.mDirectServiceVulture, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Log.w(TAG, "IContentCaptureDirectManager does not exist");
            }
        }
        this.mDirectServiceInterface = null;
        this.mContentProtectionEventProcessor = null;
        this.mHandler.removeMessages(1);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(int i, android.view.contentcapture.ViewNode.ViewStructureImpl viewStructureImpl) {
        final android.view.contentcapture.ContentCaptureEvent viewNode = new android.view.contentcapture.ContentCaptureEvent(i, 1).setViewNode(viewStructureImpl.mNode);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyViewAppeared$3(viewNode);
            }
        });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(int i, android.view.autofill.AutofillId autofillId) {
        final android.view.contentcapture.ContentCaptureEvent autofillId2 = new android.view.contentcapture.ContentCaptureEvent(i, 2).setAutofillId(autofillId);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyViewDisappeared$4(autofillId2);
            }
        });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(int i, android.view.autofill.AutofillId autofillId, java.lang.CharSequence charSequence) {
        int i2;
        int i3;
        java.lang.CharSequence trimToParcelableSize = android.text.TextUtils.trimToParcelableSize(charSequence);
        if (trimToParcelableSize != null && trimToParcelableSize == charSequence) {
            trimToParcelableSize = trimToParcelableSize.toString();
        }
        if (charSequence instanceof android.text.Spannable) {
            android.text.Spannable spannable = (android.text.Spannable) charSequence;
            i2 = android.view.inputmethod.BaseInputConnection.getComposingSpanStart(spannable);
            i3 = android.view.inputmethod.BaseInputConnection.getComposingSpanEnd(spannable);
        } else {
            i2 = -1;
            i3 = -1;
        }
        final android.view.contentcapture.ContentCaptureEvent selectionIndex = new android.view.contentcapture.ContentCaptureEvent(i, 3).setAutofillId(autofillId).setText(trimToParcelableSize).setComposingIndex(i2, i3).setSelectionIndex(android.text.Selection.getSelectionStart(charSequence), android.text.Selection.getSelectionEnd(charSequence));
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyViewTextChanged$5(selectionIndex);
            }
        });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewInsetsChanged(int i, android.graphics.Insets insets) {
        final android.view.contentcapture.ContentCaptureEvent insets2 = new android.view.contentcapture.ContentCaptureEvent(i, 9).setInsets(insets);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyViewInsetsChanged$6(insets2);
            }
        });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(int i, boolean z) {
        int i2 = z ? 4 : 5;
        this.mManager.getFlushViewTreeAppearingEventDisabled();
        final android.view.contentcapture.ContentCaptureEvent contentCaptureEvent = new android.view.contentcapture.ContentCaptureEvent(i, i2);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyViewTreeEvent$7(contentCaptureEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyViewTreeEvent$7(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifySessionResumed() {
        final android.view.contentcapture.ContentCaptureEvent contentCaptureEvent = new android.view.contentcapture.ContentCaptureEvent(this.mId, 7);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifySessionResumed$8(contentCaptureEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifySessionResumed$8(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifySessionPaused() {
        final android.view.contentcapture.ContentCaptureEvent contentCaptureEvent = new android.view.contentcapture.ContentCaptureEvent(this.mId, 8);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifySessionPaused$9(contentCaptureEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifySessionPaused$9(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isContentCaptureEnabled() {
        return super.isContentCaptureEnabled() && this.mManager.isContentCaptureEnabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isDisabled() {
        return this.mDisabled.get();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean setDisabled(boolean z) {
        return this.mDisabled.compareAndSet(!z, z);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionStarted(int i, int i2, android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        final android.view.contentcapture.ContentCaptureEvent clientContext = new android.view.contentcapture.ContentCaptureEvent(i2, -1).setParentSessionId(i).setClientContext(contentCaptureContext);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyChildSessionStarted$10(clientContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyChildSessionStarted$10(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionFinished(int i, int i2) {
        final android.view.contentcapture.ContentCaptureEvent parentSessionId = new android.view.contentcapture.ContentCaptureEvent(i2, -2).setParentSessionId(i);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyChildSessionFinished$11(parentSessionId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyChildSessionFinished$11(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyContextUpdated(int i, android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        final android.view.contentcapture.ContentCaptureEvent clientContext = new android.view.contentcapture.ContentCaptureEvent(i, 6).setClientContext(contentCaptureContext);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$internalNotifyContextUpdated$12(clientContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyContextUpdated$12(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyWindowBoundsChanged(int i, android.graphics.Rect rect) {
        final android.view.contentcapture.ContentCaptureEvent bounds = new android.view.contentcapture.ContentCaptureEvent(i, 10).setBounds(rect);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.view.contentcapture.MainContentCaptureSession.this.lambda$notifyWindowBoundsChanged$13(bounds);
            }
        });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyContentCaptureEvents(android.util.SparseArray<java.util.ArrayList<java.lang.Object>> sparseArray) {
        notifyContentCaptureEventsImpl(sparseArray);
    }

    private void notifyContentCaptureEventsImpl(android.util.SparseArray<java.util.ArrayList<java.lang.Object>> sparseArray) {
        checkOnUiThread();
        try {
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.traceBegin(8L, "notifyContentCaptureEvents");
            }
            for (int i = 0; i < sparseArray.size(); i++) {
                int keyAt = sparseArray.keyAt(i);
                internalNotifyViewTreeEvent(keyAt, true);
                java.util.ArrayList<java.lang.Object> valueAt = sparseArray.valueAt(i);
                for (int i2 = 0; i2 < valueAt.size(); i2++) {
                    java.lang.Object obj = valueAt.get(i2);
                    if (obj instanceof android.view.autofill.AutofillId) {
                        internalNotifyViewDisappeared(keyAt, (android.view.autofill.AutofillId) obj);
                    } else if (obj instanceof android.view.View) {
                        android.view.View view = (android.view.View) obj;
                        android.view.contentcapture.ContentCaptureSession contentCaptureSession = view.getContentCaptureSession();
                        if (contentCaptureSession == null) {
                            android.util.Log.w(TAG, "no content capture session on view: " + view);
                        } else {
                            int id = contentCaptureSession.getId();
                            if (id != keyAt) {
                                android.util.Log.w(TAG, "content capture session mismatch for view (" + view + "): was " + keyAt + " before, it's " + id + " now");
                            } else {
                                android.view.ViewStructure newViewStructure = contentCaptureSession.newViewStructure(view);
                                view.onProvideContentCaptureStructure(newViewStructure, 0);
                                contentCaptureSession.notifyViewAppeared(newViewStructure);
                            }
                        }
                    } else if (obj instanceof android.graphics.Insets) {
                        internalNotifyViewInsetsChanged(keyAt, (android.graphics.Insets) obj);
                    } else {
                        android.util.Log.w(TAG, "invalid content capture event: " + obj);
                    }
                }
                internalNotifyViewTreeEvent(keyAt, false);
            }
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dump(str, printWriter);
        printWriter.print(str);
        printWriter.print("mContext: ");
        printWriter.println(this.mContext);
        printWriter.print(str);
        printWriter.print("user: ");
        printWriter.println(this.mContext.getUserId());
        if (this.mDirectServiceInterface != null) {
            printWriter.print(str);
            printWriter.print("mDirectServiceInterface: ");
            printWriter.println(this.mDirectServiceInterface);
        }
        printWriter.print(str);
        printWriter.print("mDisabled: ");
        printWriter.println(this.mDisabled.get());
        printWriter.print(str);
        printWriter.print("isEnabled(): ");
        printWriter.println(isContentCaptureEnabled());
        printWriter.print(str);
        printWriter.print("state: ");
        printWriter.println(getStateAsString(this.mState));
        if (this.mApplicationToken != null) {
            printWriter.print(str);
            printWriter.print("app token: ");
            printWriter.println(this.mApplicationToken);
        }
        if (this.mShareableActivityToken != null) {
            printWriter.print(str);
            printWriter.print("sharable activity token: ");
            printWriter.println(this.mShareableActivityToken);
        }
        if (this.mComponentName != null) {
            printWriter.print(str);
            printWriter.print("component name: ");
            printWriter.println(this.mComponentName.flattenToShortString());
        }
        if (this.mEvents != null && !this.mEvents.isEmpty()) {
            int size = this.mEvents.size();
            printWriter.print(str);
            printWriter.print("buffered events: ");
            printWriter.print(size);
            printWriter.print('/');
            printWriter.println(this.mManager.mOptions.maxBufferSize);
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose && size > 0) {
                java.lang.String str2 = str + "  ";
                for (int i = 0; i < size; i++) {
                    android.view.contentcapture.ContentCaptureEvent contentCaptureEvent = this.mEvents.get(i);
                    printWriter.print(str2);
                    printWriter.print(i);
                    printWriter.print(": ");
                    contentCaptureEvent.dump(printWriter);
                    printWriter.println();
                }
            }
            printWriter.print(str);
            printWriter.print("mNextFlushForTextChanged: ");
            printWriter.println(this.mNextFlushForTextChanged);
            printWriter.print(str);
            printWriter.print("flush frequency: ");
            if (this.mNextFlushForTextChanged) {
                printWriter.println(this.mManager.mOptions.textChangeFlushingFrequencyMs);
            } else {
                printWriter.println(this.mManager.mOptions.idleFlushingFrequencyMs);
            }
            printWriter.print(str);
            printWriter.print("next flush: ");
            android.util.TimeUtils.formatDuration(this.mNextFlush - java.lang.System.currentTimeMillis(), printWriter);
            printWriter.print(" (");
            printWriter.print(android.util.TimeUtils.logTimeOfDay(this.mNextFlush));
            printWriter.println(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mFlushHistory != null) {
            printWriter.print(str);
            printWriter.println("flush history:");
            this.mFlushHistory.reverseDump(null, printWriter, null);
            printWriter.println();
        } else {
            printWriter.print(str);
            printWriter.println("not logging flush history");
        }
        super.dump(str, printWriter);
    }

    private java.lang.String getActivityName() {
        if (this.mComponentName == null) {
            return "pkg:" + this.mContext.getPackageName();
        }
        return "act:" + this.mComponentName.flattenToShortString();
    }

    private java.lang.String getDebugState() {
        return getActivityName() + " [state=" + getStateAsString(this.mState) + ", disabled=" + this.mDisabled.get() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    private java.lang.String getDebugState(int i) {
        return getDebugState() + ", reason=" + getFlushReasonAsString(i);
    }

    private boolean isContentProtectionReceiverEnabled() {
        return this.mManager.mOptions.contentProtectionOptions.enableReceiver;
    }

    private boolean isContentCaptureReceiverEnabled() {
        return this.mManager.mOptions.enableReceiver;
    }

    private boolean isContentProtectionEnabled() {
        return (!this.mManager.mOptions.contentProtectionOptions.enableReceiver || this.mManager.getContentProtectionEventBuffer() == null || this.mComponentName == null || (this.mManager.mOptions.contentProtectionOptions.requiredGroups.isEmpty() && this.mManager.mOptions.contentProtectionOptions.optionalGroups.isEmpty())) ? false : true;
    }

    private void checkOnUiThread() {
        if (!this.mHandler.getLooper().isCurrentThread()) {
            this.mWrongThreadCount.incrementAndGet();
            android.util.Log.e(TAG, "MainContentCaptureSession running on " + java.lang.Thread.currentThread());
        }
    }

    private void reportWrongThreadMetric() {
        com.android.modules.expresslog.Counter.logIncrement(CONTENT_CAPTURE_WRONG_THREAD_METRIC_ID, this.mWrongThreadCount.getAndSet(0));
    }
}
