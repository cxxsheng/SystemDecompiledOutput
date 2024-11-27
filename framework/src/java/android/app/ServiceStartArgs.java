package android.app;

/* loaded from: classes.dex */
public class ServiceStartArgs implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ServiceStartArgs> CREATOR = new android.os.Parcelable.Creator<android.app.ServiceStartArgs>() { // from class: android.app.ServiceStartArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ServiceStartArgs createFromParcel(android.os.Parcel parcel) {
            return new android.app.ServiceStartArgs(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ServiceStartArgs[] newArray(int i) {
            return new android.app.ServiceStartArgs[i];
        }
    };
    public final android.content.Intent args;
    public final int flags;
    public final int startId;
    public final boolean taskRemoved;

    public ServiceStartArgs(boolean z, int i, int i2, android.content.Intent intent) {
        this.taskRemoved = z;
        this.startId = i;
        this.flags = i2;
        this.args = intent;
    }

    public java.lang.String toString() {
        return "ServiceStartArgs{taskRemoved=" + this.taskRemoved + ", startId=" + this.startId + ", flags=0x" + java.lang.Integer.toHexString(this.flags) + ", args=" + this.args + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.taskRemoved ? 1 : 0);
        parcel.writeInt(this.startId);
        parcel.writeInt(this.flags);
        if (this.args != null) {
            parcel.writeInt(1);
            this.args.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
    }

    public ServiceStartArgs(android.os.Parcel parcel) {
        this.taskRemoved = parcel.readInt() != 0;
        this.startId = parcel.readInt();
        this.flags = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.args = android.content.Intent.CREATOR.createFromParcel(parcel);
        } else {
            this.args = null;
        }
    }
}
