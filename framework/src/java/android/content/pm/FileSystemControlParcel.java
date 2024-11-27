package android.content.pm;

/* loaded from: classes.dex */
public class FileSystemControlParcel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.FileSystemControlParcel> CREATOR = new android.os.Parcelable.Creator<android.content.pm.FileSystemControlParcel>() { // from class: android.content.pm.FileSystemControlParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.FileSystemControlParcel createFromParcel(android.os.Parcel parcel) {
            android.content.pm.FileSystemControlParcel fileSystemControlParcel = new android.content.pm.FileSystemControlParcel();
            fileSystemControlParcel.readFromParcel(parcel);
            return fileSystemControlParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.FileSystemControlParcel[] newArray(int i) {
            return new android.content.pm.FileSystemControlParcel[i];
        }
    };
    public android.content.pm.IPackageInstallerSessionFileSystemConnector callback;
    public android.os.incremental.IncrementalFileSystemControlParcel incremental;
    public android.os.incremental.IIncrementalServiceConnector service;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.incremental, i);
        parcel.writeStrongInterface(this.service);
        parcel.writeStrongInterface(this.callback);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.incremental = (android.os.incremental.IncrementalFileSystemControlParcel) parcel.readTypedObject(android.os.incremental.IncrementalFileSystemControlParcel.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.service = android.os.incremental.IIncrementalServiceConnector.Stub.asInterface(parcel.readStrongBinder());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.callback = android.content.pm.IPackageInstallerSessionFileSystemConnector.Stub.asInterface(parcel.readStrongBinder());
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.incremental) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
