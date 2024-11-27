package android.view.contentcapture;

/* loaded from: classes4.dex */
final class ChildContentCaptureSession extends android.view.contentcapture.ContentCaptureSession {
    private final android.view.contentcapture.ContentCaptureSession mParent;

    protected ChildContentCaptureSession(android.view.contentcapture.ContentCaptureSession contentCaptureSession, android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        super(contentCaptureContext);
        this.mParent = contentCaptureSession;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    android.view.contentcapture.ContentCaptureSession getMainCaptureSession() {
        return this.mParent.getMainCaptureSession();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void start(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.ComponentName componentName, int i) {
        getMainCaptureSession().start(iBinder, iBinder2, componentName, i);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isDisabled() {
        return getMainCaptureSession().isDisabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean setDisabled(boolean z) {
        return getMainCaptureSession().setDisabled(z);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    android.view.contentcapture.ContentCaptureSession newChild(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        android.view.contentcapture.ChildContentCaptureSession childContentCaptureSession = new android.view.contentcapture.ChildContentCaptureSession(this, contentCaptureContext);
        internalNotifyChildSessionStarted(this.mId, childContentCaptureSession.mId, contentCaptureContext);
        return childContentCaptureSession;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void flush(int i) {
        this.mParent.flush(i);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        internalNotifyContextUpdated(this.mId, contentCaptureContext);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        internalNotifyChildSessionFinished(this.mParent.mId, this.mId);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionStarted(int i, int i2, android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        getMainCaptureSession().internalNotifyChildSessionStarted(i, i2, contentCaptureContext);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionFinished(int i, int i2) {
        getMainCaptureSession().internalNotifyChildSessionFinished(i, i2);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyContextUpdated(int i, android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        getMainCaptureSession().internalNotifyContextUpdated(i, contentCaptureContext);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(int i, android.view.contentcapture.ViewNode.ViewStructureImpl viewStructureImpl) {
        getMainCaptureSession().internalNotifyViewAppeared(i, viewStructureImpl);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(int i, android.view.autofill.AutofillId autofillId) {
        getMainCaptureSession().internalNotifyViewDisappeared(i, autofillId);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(int i, android.view.autofill.AutofillId autofillId, java.lang.CharSequence charSequence) {
        getMainCaptureSession().internalNotifyViewTextChanged(i, autofillId, charSequence);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewInsetsChanged(int i, android.graphics.Insets insets) {
        getMainCaptureSession().internalNotifyViewInsetsChanged(this.mId, insets);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(int i, boolean z) {
        getMainCaptureSession().internalNotifyViewTreeEvent(i, z);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifySessionResumed() {
        getMainCaptureSession().internalNotifySessionResumed();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifySessionPaused() {
        getMainCaptureSession().internalNotifySessionPaused();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isContentCaptureEnabled() {
        return getMainCaptureSession().isContentCaptureEnabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyWindowBoundsChanged(int i, android.graphics.Rect rect) {
        getMainCaptureSession().notifyWindowBoundsChanged(i, rect);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyContentCaptureEvents(android.util.SparseArray<java.util.ArrayList<java.lang.Object>> sparseArray) {
        getMainCaptureSession().notifyContentCaptureEvents(sparseArray);
    }
}
