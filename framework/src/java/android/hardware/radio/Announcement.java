package android.hardware.radio;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class Announcement implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.Announcement> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.Announcement>() { // from class: android.hardware.radio.Announcement.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.Announcement createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.Announcement(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.Announcement[] newArray(int i) {
            return new android.hardware.radio.Announcement[i];
        }
    };
    public static final int TYPE_EMERGENCY = 1;
    public static final int TYPE_EVENT = 6;
    public static final int TYPE_MISC = 8;
    public static final int TYPE_NEWS = 5;
    public static final int TYPE_SPORT = 7;
    public static final int TYPE_TRAFFIC = 3;
    public static final int TYPE_WARNING = 2;
    public static final int TYPE_WEATHER = 4;
    private final android.hardware.radio.ProgramSelector mSelector;
    private final int mType;
    private final java.util.Map<java.lang.String, java.lang.String> mVendorInfo;

    public interface OnListUpdatedListener {
        void onListUpdated(java.util.Collection<android.hardware.radio.Announcement> collection);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public Announcement(android.hardware.radio.ProgramSelector programSelector, int i, java.util.Map<java.lang.String, java.lang.String> map) {
        this.mSelector = (android.hardware.radio.ProgramSelector) java.util.Objects.requireNonNull(programSelector, "Program selector cannot be null");
        this.mType = i;
        this.mVendorInfo = (java.util.Map) java.util.Objects.requireNonNull(map, "Vendor info cannot be null");
    }

    private Announcement(android.os.Parcel parcel) {
        this.mSelector = (android.hardware.radio.ProgramSelector) parcel.readTypedObject(android.hardware.radio.ProgramSelector.CREATOR);
        this.mType = parcel.readInt();
        this.mVendorInfo = android.hardware.radio.Utils.readStringMap(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mSelector, 0);
        parcel.writeInt(this.mType);
        android.hardware.radio.Utils.writeStringMap(parcel, this.mVendorInfo);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.hardware.radio.ProgramSelector getSelector() {
        return this.mSelector;
    }

    public int getType() {
        return this.mType;
    }

    public java.util.Map<java.lang.String, java.lang.String> getVendorInfo() {
        return this.mVendorInfo;
    }
}
