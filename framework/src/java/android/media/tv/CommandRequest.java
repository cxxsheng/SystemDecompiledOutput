package android.media.tv;

/* loaded from: classes2.dex */
public final class CommandRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final java.lang.String ARGUMENT_TYPE_JSON = "json";
    public static final java.lang.String ARGUMENT_TYPE_XML = "xml";
    public static final android.os.Parcelable.Creator<android.media.tv.CommandRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.CommandRequest>() { // from class: android.media.tv.CommandRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.CommandRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.CommandRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.CommandRequest[] newArray(int i) {
            return new android.media.tv.CommandRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 7;
    private final java.lang.String mArgumentType;
    private final java.lang.String mArguments;
    private final java.lang.String mName;
    private final java.lang.String mNamespace;

    static android.media.tv.CommandRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.CommandRequest(parcel);
    }

    public CommandRequest(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        super(7, i, i2);
        this.mNamespace = str;
        this.mName = str2;
        this.mArguments = str3;
        this.mArgumentType = str4;
    }

    CommandRequest(android.os.Parcel parcel) {
        super(7, parcel);
        this.mNamespace = parcel.readString();
        this.mName = parcel.readString();
        this.mArguments = parcel.readString();
        this.mArgumentType = parcel.readString();
    }

    public java.lang.String getNamespace() {
        return this.mNamespace;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getArguments() {
        return this.mArguments;
    }

    public java.lang.String getArgumentType() {
        return this.mArgumentType;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mNamespace);
        parcel.writeString(this.mName);
        parcel.writeString(this.mArguments);
        parcel.writeString(this.mArgumentType);
    }
}
