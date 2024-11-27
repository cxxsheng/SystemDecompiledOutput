package android.companion;

/* loaded from: classes.dex */
public final class DevicePresenceEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.DevicePresenceEvent> CREATOR = new android.os.Parcelable.Creator<android.companion.DevicePresenceEvent>() { // from class: android.companion.DevicePresenceEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.DevicePresenceEvent[] newArray(int i) {
            return new android.companion.DevicePresenceEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.DevicePresenceEvent createFromParcel(android.os.Parcel parcel) {
            return new android.companion.DevicePresenceEvent(parcel);
        }
    };
    public static final int EVENT_BLE_APPEARED = 0;
    public static final int EVENT_BLE_DISAPPEARED = 1;
    public static final int EVENT_BT_CONNECTED = 2;
    public static final int EVENT_BT_DISCONNECTED = 3;
    public static final int EVENT_SELF_MANAGED_APPEARED = 4;
    public static final int EVENT_SELF_MANAGED_DISAPPEARED = 5;
    public static final int NO_ASSOCIATION = -1;
    private static final int PARCEL_UUID_NOT_NULL = 1;
    private static final int PARCEL_UUID_NULL = 0;
    private final int mAssociationId;
    private final int mEvent;
    private final android.os.ParcelUuid mUuid;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Event {
    }

    public DevicePresenceEvent(int i, int i2, android.os.ParcelUuid parcelUuid) {
        this.mAssociationId = i;
        this.mEvent = i2;
        this.mUuid = parcelUuid;
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    public int getEvent() {
        return this.mEvent;
    }

    public android.os.ParcelUuid getUuid() {
        return this.mUuid;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAssociationId);
        parcel.writeInt(this.mEvent);
        if (this.mUuid == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mUuid.writeToParcel(parcel, i);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.companion.DevicePresenceEvent)) {
            return false;
        }
        android.companion.DevicePresenceEvent devicePresenceEvent = (android.companion.DevicePresenceEvent) obj;
        return java.util.Objects.equals(this.mUuid, devicePresenceEvent.mUuid) && this.mAssociationId == devicePresenceEvent.mAssociationId && this.mEvent == devicePresenceEvent.mEvent;
    }

    public java.lang.String toString() {
        return "ObservingDevicePresenceResult { Association Id= " + this.mAssociationId + ",ParcelUuid= " + this.mUuid + ",Event= " + this.mEvent + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAssociationId), java.lang.Integer.valueOf(this.mEvent), this.mUuid);
    }

    private DevicePresenceEvent(android.os.Parcel parcel) {
        this.mAssociationId = parcel.readInt();
        this.mEvent = parcel.readInt();
        if (parcel.readInt() == 0) {
            this.mUuid = null;
        } else {
            this.mUuid = android.os.ParcelUuid.CREATOR.createFromParcel(parcel);
        }
    }
}
