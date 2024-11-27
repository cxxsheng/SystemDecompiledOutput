package android.content;

/* loaded from: classes.dex */
public class UndoOwner {
    java.lang.Object mData;
    final android.content.UndoManager mManager;
    int mOpCount;
    int mSavedIdx;
    int mStateSeq;
    final java.lang.String mTag;

    UndoOwner(java.lang.String str, android.content.UndoManager undoManager) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag can't be null");
        }
        if (undoManager == null) {
            throw new java.lang.NullPointerException("manager can't be null");
        }
        this.mTag = str;
        this.mManager = undoManager;
    }

    public java.lang.String getTag() {
        return this.mTag;
    }

    public java.lang.Object getData() {
        return this.mData;
    }

    public java.lang.String toString() {
        return "UndoOwner:[mTag=" + this.mTag + " mManager=" + this.mManager + " mData=" + this.mData + " mData=" + this.mData + " mOpCount=" + this.mOpCount + " mStateSeq=" + this.mStateSeq + " mSavedIdx=" + this.mSavedIdx + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
