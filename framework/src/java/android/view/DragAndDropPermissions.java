package android.view;

/* loaded from: classes4.dex */
public final class DragAndDropPermissions implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.DragAndDropPermissions> CREATOR = new android.os.Parcelable.Creator<android.view.DragAndDropPermissions>() { // from class: android.view.DragAndDropPermissions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DragAndDropPermissions createFromParcel(android.os.Parcel parcel) {
            return new android.view.DragAndDropPermissions(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DragAndDropPermissions[] newArray(int i) {
            return new android.view.DragAndDropPermissions[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DragAndDrop";
    private final com.android.internal.view.IDragAndDropPermissions mDragAndDropPermissions;

    public static android.view.DragAndDropPermissions obtain(android.view.DragEvent dragEvent) {
        if (dragEvent.getDragAndDropPermissions() == null) {
            return null;
        }
        return new android.view.DragAndDropPermissions(dragEvent.getDragAndDropPermissions());
    }

    private DragAndDropPermissions(com.android.internal.view.IDragAndDropPermissions iDragAndDropPermissions) {
        this.mDragAndDropPermissions = iDragAndDropPermissions;
    }

    public boolean take(android.os.IBinder iBinder) {
        try {
            this.mDragAndDropPermissions.take(iBinder);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, this + ": take() failed with a RemoteException", e);
            return false;
        }
    }

    public boolean takeTransient() {
        try {
            this.mDragAndDropPermissions.takeTransient();
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, this + ": takeTransient() failed with a RemoteException", e);
            return false;
        }
    }

    public void release() {
        try {
            this.mDragAndDropPermissions.release();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mDragAndDropPermissions);
    }

    private DragAndDropPermissions(android.os.Parcel parcel) {
        this.mDragAndDropPermissions = com.android.internal.view.IDragAndDropPermissions.Stub.asInterface(parcel.readStrongBinder());
    }
}
