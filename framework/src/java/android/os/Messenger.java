package android.os;

/* loaded from: classes3.dex */
public final class Messenger implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.Messenger> CREATOR = new android.os.Parcelable.Creator<android.os.Messenger>() { // from class: android.os.Messenger.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.Messenger createFromParcel(android.os.Parcel parcel) {
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                return new android.os.Messenger(readStrongBinder);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.Messenger[] newArray(int i) {
            return new android.os.Messenger[i];
        }
    };
    private final android.os.IMessenger mTarget;

    public Messenger(android.os.Handler handler) {
        this.mTarget = handler.getIMessenger();
    }

    public void send(android.os.Message message) throws android.os.RemoteException {
        this.mTarget.send(message);
    }

    public android.os.IBinder getBinder() {
        return this.mTarget.asBinder();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return this.mTarget.asBinder().equals(((android.os.Messenger) obj).mTarget.asBinder());
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
    }

    public static void writeMessengerOrNullToParcel(android.os.Messenger messenger, android.os.Parcel parcel) {
        parcel.writeStrongBinder(messenger != null ? messenger.mTarget.asBinder() : null);
    }

    public static android.os.Messenger readMessengerOrNullFromParcel(android.os.Parcel parcel) {
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder != null) {
            return new android.os.Messenger(readStrongBinder);
        }
        return null;
    }

    public Messenger(android.os.IBinder iBinder) {
        this.mTarget = android.os.IMessenger.Stub.asInterface(iBinder);
    }
}
