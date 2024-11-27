package android.content;

/* loaded from: classes.dex */
public abstract class UndoOperation<DATA> implements android.os.Parcelable {
    android.content.UndoOwner mOwner;

    public abstract void commit();

    public abstract void redo();

    public abstract void undo();

    public UndoOperation(android.content.UndoOwner undoOwner) {
        this.mOwner = undoOwner;
    }

    protected UndoOperation(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
    }

    public android.content.UndoOwner getOwner() {
        return this.mOwner;
    }

    public DATA getOwnerData() {
        return (DATA) this.mOwner.getData();
    }

    public boolean matchOwner(android.content.UndoOwner undoOwner) {
        return undoOwner == getOwner();
    }

    public boolean hasData() {
        return true;
    }

    public boolean allowMerge() {
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
