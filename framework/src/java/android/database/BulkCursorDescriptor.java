package android.database;

/* loaded from: classes.dex */
public final class BulkCursorDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.database.BulkCursorDescriptor> CREATOR = new android.os.Parcelable.Creator<android.database.BulkCursorDescriptor>() { // from class: android.database.BulkCursorDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.database.BulkCursorDescriptor createFromParcel(android.os.Parcel parcel) {
            android.database.BulkCursorDescriptor bulkCursorDescriptor = new android.database.BulkCursorDescriptor();
            bulkCursorDescriptor.readFromParcel(parcel);
            return bulkCursorDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.database.BulkCursorDescriptor[] newArray(int i) {
            return new android.database.BulkCursorDescriptor[i];
        }
    };
    public java.lang.String[] columnNames;
    public int count;
    public android.database.IBulkCursor cursor;
    public boolean wantsAllOnMoveCalls;
    public android.database.CursorWindow window;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.cursor.asBinder());
        parcel.writeStringArray(this.columnNames);
        parcel.writeInt(this.wantsAllOnMoveCalls ? 1 : 0);
        parcel.writeInt(this.count);
        if (this.window != null) {
            parcel.writeInt(1);
            this.window.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.cursor = android.database.BulkCursorNative.asInterface(parcel.readStrongBinder());
        this.columnNames = parcel.readStringArray();
        this.wantsAllOnMoveCalls = parcel.readInt() != 0;
        this.count = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.window = android.database.CursorWindow.CREATOR.createFromParcel(parcel);
        }
    }
}
