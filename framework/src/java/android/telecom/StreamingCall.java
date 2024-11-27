package android.telecom;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class StreamingCall implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.StreamingCall> CREATOR = new android.os.Parcelable.Creator<android.telecom.StreamingCall>() { // from class: android.telecom.StreamingCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.StreamingCall createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.StreamingCall(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.StreamingCall[] newArray(int i) {
            return new android.telecom.StreamingCall[i];
        }
    };
    public static final java.lang.String EXTRA_CALL_ID = "android.telecom.extra.CALL_ID";
    public static final int STATE_DISCONNECTED = 3;
    public static final int STATE_HOLDING = 2;
    public static final int STATE_STREAMING = 1;
    private android.telecom.StreamingCallAdapter mAdapter;
    private final android.net.Uri mAddress;
    private final android.content.ComponentName mComponentName;
    private final java.lang.CharSequence mDisplayName;
    private final android.os.Bundle mExtras;
    private int mState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamingCallState {
    }

    private StreamingCall(android.os.Parcel parcel) {
        this.mAdapter = null;
        this.mComponentName = (android.content.ComponentName) parcel.readParcelable(android.content.ComponentName.class.getClassLoader());
        this.mDisplayName = parcel.readCharSequence();
        this.mAddress = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader());
        this.mExtras = parcel.readBundle();
        this.mState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mComponentName, i);
        parcel.writeCharSequence(this.mDisplayName);
        parcel.writeParcelable(this.mAddress, i);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mState);
    }

    public StreamingCall(android.content.ComponentName componentName, java.lang.CharSequence charSequence, android.net.Uri uri, android.os.Bundle bundle) {
        this.mAdapter = null;
        this.mComponentName = componentName;
        this.mDisplayName = charSequence;
        this.mAddress = uri;
        this.mExtras = bundle;
        this.mState = 1;
    }

    public void setAdapter(android.telecom.StreamingCallAdapter streamingCallAdapter) {
        this.mAdapter = streamingCallAdapter;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public java.lang.CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public android.net.Uri getAddress() {
        return this.mAddress;
    }

    public int getState() {
        return this.mState;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public void requestStreamingState(int i) {
        this.mAdapter.setStreamingState(i);
    }
}
