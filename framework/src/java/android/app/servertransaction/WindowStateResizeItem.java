package android.app.servertransaction;

/* loaded from: classes.dex */
public class WindowStateResizeItem extends android.app.servertransaction.ClientTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.WindowStateResizeItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.WindowStateResizeItem>() { // from class: android.app.servertransaction.WindowStateResizeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.WindowStateResizeItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.WindowStateResizeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.WindowStateResizeItem[] newArray(int i) {
            return new android.app.servertransaction.WindowStateResizeItem[i];
        }
    };
    private boolean mAlwaysConsumeSystemBars;
    private android.util.MergedConfiguration mConfiguration;
    private int mDisplayId;
    private boolean mDragResizing;
    private boolean mForceLayout;
    private android.window.ClientWindowFrames mFrames;
    private android.view.InsetsState mInsetsState;
    private boolean mReportDraw;
    private int mSyncSeqId;
    private android.view.IWindow mWindow;

    public interface ResizeListener {
        void onExecutingWindowStateResizeItem();
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(32L, this.mReportDraw ? "windowResizedReport" : "windowResized");
        android.view.IWindow iWindow = this.mWindow;
        if (iWindow instanceof android.app.servertransaction.WindowStateResizeItem.ResizeListener) {
            ((android.app.servertransaction.WindowStateResizeItem.ResizeListener) iWindow).onExecutingWindowStateResizeItem();
        }
        try {
            this.mWindow.resized(this.mFrames, this.mReportDraw, this.mConfiguration, this.mInsetsState, this.mForceLayout, this.mAlwaysConsumeSystemBars, this.mDisplayId, this.mSyncSeqId, this.mDragResizing);
            android.os.Trace.traceEnd(32L);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return android.app.ActivityThread.currentApplication();
    }

    private WindowStateResizeItem() {
    }

    public static android.app.servertransaction.WindowStateResizeItem obtain(android.view.IWindow iWindow, android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
        android.app.servertransaction.WindowStateResizeItem windowStateResizeItem = (android.app.servertransaction.WindowStateResizeItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.WindowStateResizeItem.class);
        if (windowStateResizeItem == null) {
            windowStateResizeItem = new android.app.servertransaction.WindowStateResizeItem();
        }
        windowStateResizeItem.mWindow = (android.view.IWindow) java.util.Objects.requireNonNull(iWindow);
        windowStateResizeItem.mFrames = new android.window.ClientWindowFrames(clientWindowFrames);
        windowStateResizeItem.mReportDraw = z;
        windowStateResizeItem.mConfiguration = new android.util.MergedConfiguration(mergedConfiguration);
        windowStateResizeItem.mInsetsState = new android.view.InsetsState(insetsState, true);
        windowStateResizeItem.mForceLayout = z2;
        windowStateResizeItem.mAlwaysConsumeSystemBars = z3;
        windowStateResizeItem.mDisplayId = i;
        windowStateResizeItem.mSyncSeqId = i2;
        windowStateResizeItem.mDragResizing = z4;
        return windowStateResizeItem;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mWindow = null;
        this.mFrames = null;
        this.mReportDraw = false;
        this.mConfiguration = null;
        this.mInsetsState = null;
        this.mForceLayout = false;
        this.mAlwaysConsumeSystemBars = false;
        this.mDisplayId = -1;
        this.mSyncSeqId = -1;
        this.mDragResizing = false;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mWindow.asBinder());
        parcel.writeTypedObject(this.mFrames, i);
        parcel.writeBoolean(this.mReportDraw);
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeTypedObject(this.mInsetsState, i);
        parcel.writeBoolean(this.mForceLayout);
        parcel.writeBoolean(this.mAlwaysConsumeSystemBars);
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mSyncSeqId);
        parcel.writeBoolean(this.mDragResizing);
    }

    private WindowStateResizeItem(android.os.Parcel parcel) {
        this.mWindow = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
        this.mFrames = (android.window.ClientWindowFrames) parcel.readTypedObject(android.window.ClientWindowFrames.CREATOR);
        this.mReportDraw = parcel.readBoolean();
        this.mConfiguration = (android.util.MergedConfiguration) parcel.readTypedObject(android.util.MergedConfiguration.CREATOR);
        this.mInsetsState = (android.view.InsetsState) parcel.readTypedObject(android.view.InsetsState.CREATOR);
        this.mForceLayout = parcel.readBoolean();
        this.mAlwaysConsumeSystemBars = parcel.readBoolean();
        this.mDisplayId = parcel.readInt();
        this.mSyncSeqId = parcel.readInt();
        this.mDragResizing = parcel.readBoolean();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.servertransaction.WindowStateResizeItem windowStateResizeItem = (android.app.servertransaction.WindowStateResizeItem) obj;
        if (java.util.Objects.equals(this.mWindow, windowStateResizeItem.mWindow) && java.util.Objects.equals(this.mFrames, windowStateResizeItem.mFrames) && this.mReportDraw == windowStateResizeItem.mReportDraw && java.util.Objects.equals(this.mConfiguration, windowStateResizeItem.mConfiguration) && java.util.Objects.equals(this.mInsetsState, windowStateResizeItem.mInsetsState) && this.mForceLayout == windowStateResizeItem.mForceLayout && this.mAlwaysConsumeSystemBars == windowStateResizeItem.mAlwaysConsumeSystemBars && this.mDisplayId == windowStateResizeItem.mDisplayId && this.mSyncSeqId == windowStateResizeItem.mSyncSeqId && this.mDragResizing == windowStateResizeItem.mDragResizing) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((527 + java.util.Objects.hashCode(this.mWindow)) * 31) + java.util.Objects.hashCode(this.mFrames)) * 31) + (this.mReportDraw ? 1 : 0)) * 31) + java.util.Objects.hashCode(this.mConfiguration)) * 31) + java.util.Objects.hashCode(this.mInsetsState)) * 31) + (this.mForceLayout ? 1 : 0)) * 31) + (this.mAlwaysConsumeSystemBars ? 1 : 0)) * 31) + this.mDisplayId) * 31) + this.mSyncSeqId) * 31) + (this.mDragResizing ? 1 : 0);
    }

    public java.lang.String toString() {
        return "WindowStateResizeItem{window=" + this.mWindow + ", reportDrawn=" + this.mReportDraw + ", configuration=" + this.mConfiguration + "}";
    }
}
