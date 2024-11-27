package android.app;

/* loaded from: classes.dex */
public final class ProcessMemoryState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ProcessMemoryState> CREATOR = new android.os.Parcelable.Creator<android.app.ProcessMemoryState>() { // from class: android.app.ProcessMemoryState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ProcessMemoryState createFromParcel(android.os.Parcel parcel) {
            return new android.app.ProcessMemoryState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ProcessMemoryState[] newArray(int i) {
            return new android.app.ProcessMemoryState[i];
        }
    };
    public static final int HOSTING_COMPONENT_TYPE_ACTIVITY = 16;
    public static final int HOSTING_COMPONENT_TYPE_BACKUP = 4;
    public static final int HOSTING_COMPONENT_TYPE_BOUND_SERVICE = 512;
    public static final int HOSTING_COMPONENT_TYPE_BROADCAST_RECEIVER = 32;
    public static final int HOSTING_COMPONENT_TYPE_EMPTY = 0;
    public static final int HOSTING_COMPONENT_TYPE_FOREGROUND_SERVICE = 256;
    public static final int HOSTING_COMPONENT_TYPE_INSTRUMENTATION = 8;
    public static final int HOSTING_COMPONENT_TYPE_PERSISTENT = 2;
    public static final int HOSTING_COMPONENT_TYPE_PROVIDER = 64;
    public static final int HOSTING_COMPONENT_TYPE_STARTED_SERVICE = 128;
    public static final int HOSTING_COMPONENT_TYPE_SYSTEM = 1;
    public final boolean hasForegroundServices;
    public final int mHistoricalHostingComponentTypes;
    public final int mHostingComponentTypes;
    public final int oomScore;
    public final int pid;
    public final java.lang.String processName;
    public final int uid;

    public @interface HostingComponentType {
    }

    public ProcessMemoryState(int i, int i2, java.lang.String str, int i3, boolean z, int i4, int i5) {
        this.uid = i;
        this.pid = i2;
        this.processName = str;
        this.oomScore = i3;
        this.hasForegroundServices = z;
        this.mHostingComponentTypes = i4;
        this.mHistoricalHostingComponentTypes = i5;
    }

    private ProcessMemoryState(android.os.Parcel parcel) {
        this.uid = parcel.readInt();
        this.pid = parcel.readInt();
        this.processName = parcel.readString();
        this.oomScore = parcel.readInt();
        this.hasForegroundServices = parcel.readInt() == 1;
        this.mHostingComponentTypes = parcel.readInt();
        this.mHistoricalHostingComponentTypes = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.uid);
        parcel.writeInt(this.pid);
        parcel.writeString(this.processName);
        parcel.writeInt(this.oomScore);
        parcel.writeInt(this.hasForegroundServices ? 1 : 0);
        parcel.writeInt(this.mHostingComponentTypes);
        parcel.writeInt(this.mHistoricalHostingComponentTypes);
    }
}
