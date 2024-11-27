package android.view.contentcapture;

/* loaded from: classes4.dex */
public abstract class ContentCaptureSession implements java.lang.AutoCloseable {
    public static final java.lang.String EXTRA_BINDER = "binder";
    public static final java.lang.String EXTRA_ENABLED_STATE = "enabled";
    public static final int FLUSH_REASON_FORCE_FLUSH = 8;
    public static final int FLUSH_REASON_FULL = 1;
    public static final int FLUSH_REASON_IDLE_TIMEOUT = 5;
    public static final int FLUSH_REASON_SESSION_CONNECTED = 7;
    public static final int FLUSH_REASON_SESSION_FINISHED = 4;
    public static final int FLUSH_REASON_SESSION_STARTED = 3;
    public static final int FLUSH_REASON_TEXT_CHANGE_TIMEOUT = 6;
    public static final int FLUSH_REASON_VIEW_ROOT_ENTERED = 2;
    public static final int FLUSH_REASON_VIEW_TREE_APPEARED = 10;
    public static final int FLUSH_REASON_VIEW_TREE_APPEARING = 9;
    private static final int INITIAL_CHILDREN_CAPACITY = 5;
    static final long NOTIFY_NODES_DISAPPEAR_NOW_SENDS_TREE_EVENTS = 258825825;
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_BY_APP = 64;
    public static final int STATE_DISABLED = 4;
    public static final int STATE_DUPLICATED_ID = 8;
    public static final int STATE_FLAG_SECURE = 32;
    public static final int STATE_INTERNAL_ERROR = 256;
    public static final int STATE_NOT_WHITELISTED = 512;
    public static final int STATE_NO_RESPONSE = 128;
    public static final int STATE_NO_SERVICE = 16;
    public static final int STATE_SERVICE_DIED = 1024;
    public static final int STATE_SERVICE_RESURRECTED = 4096;
    public static final int STATE_SERVICE_UPDATING = 2048;
    public static final int STATE_WAITING_FOR_SERVER = 1;
    public static final int UNKNOWN_STATE = 0;
    private java.util.ArrayList<android.view.contentcapture.ContentCaptureSession> mChildren;
    private android.view.contentcapture.ContentCaptureContext mClientContext;
    private android.view.contentcapture.ContentCaptureSessionId mContentCaptureSessionId;
    private boolean mDestroyed;
    protected final int mId;
    private final java.lang.Object mLock;
    private int mState;
    private static final java.lang.String TAG = android.view.contentcapture.ContentCaptureSession.class.getSimpleName();
    private static final java.security.SecureRandom ID_GENERATOR = new java.security.SecureRandom();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FlushReason {
    }

    abstract void flush(int i);

    abstract android.view.contentcapture.ContentCaptureSession getMainCaptureSession();

    abstract void internalNotifyChildSessionFinished(int i, int i2);

    abstract void internalNotifyChildSessionStarted(int i, int i2, android.view.contentcapture.ContentCaptureContext contentCaptureContext);

    abstract void internalNotifyContextUpdated(int i, android.view.contentcapture.ContentCaptureContext contentCaptureContext);

    abstract void internalNotifySessionPaused();

    abstract void internalNotifySessionResumed();

    abstract void internalNotifyViewAppeared(int i, android.view.contentcapture.ViewNode.ViewStructureImpl viewStructureImpl);

    abstract void internalNotifyViewDisappeared(int i, android.view.autofill.AutofillId autofillId);

    abstract void internalNotifyViewInsetsChanged(int i, android.graphics.Insets insets);

    abstract void internalNotifyViewTextChanged(int i, android.view.autofill.AutofillId autofillId, java.lang.CharSequence charSequence);

    abstract void internalNotifyViewTreeEvent(int i, boolean z);

    abstract boolean isDisabled();

    abstract android.view.contentcapture.ContentCaptureSession newChild(android.view.contentcapture.ContentCaptureContext contentCaptureContext);

    public abstract void notifyContentCaptureEvents(android.util.SparseArray<java.util.ArrayList<java.lang.Object>> sparseArray);

    public abstract void notifyWindowBoundsChanged(int i, android.graphics.Rect rect);

    abstract void onDestroy();

    abstract boolean setDisabled(boolean z);

    abstract void start(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.ComponentName componentName, int i);

    abstract void updateContentCaptureContext(android.view.contentcapture.ContentCaptureContext contentCaptureContext);

    protected ContentCaptureSession() {
        this(getRandomSessionId());
    }

    public ContentCaptureSession(int i) {
        this.mLock = new java.lang.Object();
        this.mState = 0;
        com.android.internal.util.Preconditions.checkArgument(i != 0);
        this.mId = i;
    }

    ContentCaptureSession(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        this();
        this.mClientContext = (android.view.contentcapture.ContentCaptureContext) java.util.Objects.requireNonNull(contentCaptureContext);
    }

    public final android.view.contentcapture.ContentCaptureSessionId getContentCaptureSessionId() {
        if (this.mContentCaptureSessionId == null) {
            this.mContentCaptureSessionId = new android.view.contentcapture.ContentCaptureSessionId(this.mId);
        }
        return this.mContentCaptureSessionId;
    }

    public int getId() {
        return this.mId;
    }

    public final android.view.contentcapture.ContentCaptureSession createContentCaptureSession(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        android.view.contentcapture.ContentCaptureSession newChild = newChild(contentCaptureContext);
        if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
            android.util.Log.d(TAG, "createContentCaptureSession(" + contentCaptureContext + ": parent=" + this.mId + ", child=" + newChild.mId);
        }
        synchronized (this.mLock) {
            if (this.mChildren == null) {
                this.mChildren = new java.util.ArrayList<>(5);
            }
            this.mChildren.add(newChild);
        }
        return newChild;
    }

    public final void setContentCaptureContext(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        if (isContentCaptureEnabled()) {
            this.mClientContext = contentCaptureContext;
            updateContentCaptureContext(contentCaptureContext);
        }
    }

    public final android.view.contentcapture.ContentCaptureContext getContentCaptureContext() {
        return this.mClientContext;
    }

    public final void destroy() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
                    android.util.Log.d(TAG, "destroy(" + this.mId + "): already destroyed");
                }
                return;
            }
            this.mDestroyed = true;
            if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                android.util.Log.v(TAG, "destroy(): state=" + getStateAsString(this.mState) + ", mId=" + this.mId);
            }
            if (this.mChildren != null) {
                int size = this.mChildren.size();
                if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                    android.util.Log.v(TAG, "Destroying " + size + " children first");
                }
                for (int i = 0; i < size; i++) {
                    try {
                        this.mChildren.get(i).destroy();
                    } catch (java.lang.Exception e) {
                        android.util.Log.w(TAG, "exception destroying child session #" + i + ": " + e);
                    }
                }
            }
            onDestroy();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        destroy();
    }

    public final void notifyViewAppeared(android.view.ViewStructure viewStructure) {
        java.util.Objects.requireNonNull(viewStructure);
        if (isContentCaptureEnabled()) {
            if (!(viewStructure instanceof android.view.contentcapture.ViewNode.ViewStructureImpl)) {
                throw new java.lang.IllegalArgumentException("Invalid node class: " + viewStructure.getClass());
            }
            internalNotifyViewAppeared(this.mId, (android.view.contentcapture.ViewNode.ViewStructureImpl) viewStructure);
        }
    }

    public final void notifyViewDisappeared(android.view.autofill.AutofillId autofillId) {
        java.util.Objects.requireNonNull(autofillId);
        if (isContentCaptureEnabled()) {
            internalNotifyViewDisappeared(this.mId, autofillId);
        }
    }

    public final void notifyViewsAppeared(java.util.List<android.view.ViewStructure> list) {
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "appearedNodes");
        if (isContentCaptureEnabled()) {
            for (int i = 0; i < list.size(); i++) {
                android.view.ViewStructure viewStructure = list.get(i);
                if (!(viewStructure instanceof android.view.contentcapture.ViewNode.ViewStructureImpl)) {
                    throw new java.lang.IllegalArgumentException("Invalid class: " + viewStructure.getClass());
                }
            }
            internalNotifyViewTreeEvent(this.mId, true);
            for (int i2 = 0; i2 < list.size(); i2++) {
                internalNotifyViewAppeared(this.mId, (android.view.contentcapture.ViewNode.ViewStructureImpl) list.get(i2));
            }
            internalNotifyViewTreeEvent(this.mId, false);
        }
    }

    public final void notifyViewsDisappeared(android.view.autofill.AutofillId autofillId, long[] jArr) {
        com.android.internal.util.Preconditions.checkArgument(autofillId.isNonVirtual(), "hostId cannot be virtual: %s", autofillId);
        com.android.internal.util.Preconditions.checkArgument(!com.android.internal.util.ArrayUtils.isEmpty(jArr), "virtual ids cannot be empty");
        if (isContentCaptureEnabled()) {
            if (android.app.compat.CompatChanges.isChangeEnabled(NOTIFY_NODES_DISAPPEAR_NOW_SENDS_TREE_EVENTS)) {
                internalNotifyViewTreeEvent(this.mId, true);
            }
            for (long j : jArr) {
                internalNotifyViewDisappeared(this.mId, new android.view.autofill.AutofillId(autofillId, j, this.mId));
            }
            if (android.app.compat.CompatChanges.isChangeEnabled(NOTIFY_NODES_DISAPPEAR_NOW_SENDS_TREE_EVENTS)) {
                internalNotifyViewTreeEvent(this.mId, false);
            }
        }
    }

    public final void notifyViewTextChanged(android.view.autofill.AutofillId autofillId, java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(autofillId);
        if (isContentCaptureEnabled()) {
            internalNotifyViewTextChanged(this.mId, autofillId, charSequence);
        }
    }

    public final void notifyViewInsetsChanged(android.graphics.Insets insets) {
        java.util.Objects.requireNonNull(insets);
        if (isContentCaptureEnabled()) {
            internalNotifyViewInsetsChanged(this.mId, insets);
        }
    }

    public void notifyViewTreeEvent(boolean z) {
        internalNotifyViewTreeEvent(this.mId, z);
    }

    public final void notifySessionResumed() {
        if (isContentCaptureEnabled()) {
            internalNotifySessionResumed();
        }
    }

    public final void notifySessionPaused() {
        if (isContentCaptureEnabled()) {
            internalNotifySessionPaused();
        }
    }

    public final android.view.ViewStructure newViewStructure(android.view.View view) {
        return new android.view.contentcapture.ViewNode.ViewStructureImpl(view);
    }

    public android.view.autofill.AutofillId newAutofillId(android.view.autofill.AutofillId autofillId, long j) {
        java.util.Objects.requireNonNull(autofillId);
        com.android.internal.util.Preconditions.checkArgument(autofillId.isNonVirtual(), "hostId cannot be virtual: %s", autofillId);
        return new android.view.autofill.AutofillId(autofillId, j, this.mId);
    }

    public final android.view.ViewStructure newVirtualViewStructure(android.view.autofill.AutofillId autofillId, long j) {
        return new android.view.contentcapture.ViewNode.ViewStructureImpl(autofillId, j, this.mId);
    }

    boolean isContentCaptureEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mDestroyed;
        }
        return z;
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("id: ");
        printWriter.println(this.mId);
        if (this.mClientContext != null) {
            printWriter.print(str);
            this.mClientContext.dump(printWriter);
            printWriter.println();
        }
        synchronized (this.mLock) {
            printWriter.print(str);
            printWriter.print("destroyed: ");
            printWriter.println(this.mDestroyed);
            if (this.mChildren != null && !this.mChildren.isEmpty()) {
                java.lang.String str2 = str + "  ";
                int size = this.mChildren.size();
                printWriter.print(str);
                printWriter.print("number children: ");
                printWriter.println(size);
                for (int i = 0; i < size; i++) {
                    android.view.contentcapture.ContentCaptureSession contentCaptureSession = this.mChildren.get(i);
                    printWriter.print(str);
                    printWriter.print(i);
                    printWriter.println(": ");
                    contentCaptureSession.dump(str2, printWriter);
                }
            }
        }
    }

    public java.lang.String toString() {
        return java.lang.Integer.toString(this.mId);
    }

    protected static java.lang.String getStateAsString(int i) {
        return i + " (" + (i == 0 ? "UNKNOWN" : android.util.DebugUtils.flagsToString(android.view.contentcapture.ContentCaptureSession.class, "STATE_", i)) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public static java.lang.String getFlushReasonAsString(int i) {
        switch (i) {
            case 1:
                return "FULL";
            case 2:
                return "VIEW_ROOT";
            case 3:
                return "STARTED";
            case 4:
                return "FINISHED";
            case 5:
                return "IDLE";
            case 6:
                return "TEXT_CHANGE";
            case 7:
                return "CONNECTED";
            case 8:
                return "FORCE_FLUSH";
            case 9:
                return "VIEW_TREE_APPEARING";
            case 10:
                return "VIEW_TREE_APPEARED";
            default:
                return "UNKNOWN-" + i;
        }
    }

    private static int getRandomSessionId() {
        int nextInt;
        do {
            nextInt = ID_GENERATOR.nextInt();
        } while (nextInt == 0);
        return nextInt;
    }
}
