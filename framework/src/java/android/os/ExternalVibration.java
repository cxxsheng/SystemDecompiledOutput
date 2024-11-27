package android.os;

/* loaded from: classes3.dex */
public class ExternalVibration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ExternalVibration> CREATOR = new android.os.Parcelable.Creator<android.os.ExternalVibration>() { // from class: android.os.ExternalVibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ExternalVibration createFromParcel(android.os.Parcel parcel) {
            return new android.os.ExternalVibration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ExternalVibration[] newArray(int i) {
            return new android.os.ExternalVibration[i];
        }
    };
    private static final java.lang.String TAG = "ExternalVibration";
    private android.media.AudioAttributes mAttrs;
    private android.os.IExternalVibrationController mController;
    private java.lang.String mPkg;
    private android.os.IBinder mToken;
    private int mUid;

    public ExternalVibration(int i, java.lang.String str, android.media.AudioAttributes audioAttributes, android.os.IExternalVibrationController iExternalVibrationController) {
        this(i, str, audioAttributes, iExternalVibrationController, new android.os.Binder());
    }

    public ExternalVibration(int i, java.lang.String str, android.media.AudioAttributes audioAttributes, android.os.IExternalVibrationController iExternalVibrationController, android.os.IBinder iBinder) {
        this.mUid = i;
        this.mPkg = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
        this.mAttrs = (android.media.AudioAttributes) com.android.internal.util.Preconditions.checkNotNull(audioAttributes);
        this.mController = (android.os.IExternalVibrationController) com.android.internal.util.Preconditions.checkNotNull(iExternalVibrationController);
        this.mToken = (android.os.IBinder) com.android.internal.util.Preconditions.checkNotNull(iBinder);
        android.os.Binder.allowBlocking(this.mController.asBinder());
    }

    private ExternalVibration(android.os.Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), readAudioAttributes(parcel), android.os.IExternalVibrationController.Stub.asInterface(parcel.readStrongBinder()), parcel.readStrongBinder());
    }

    private static android.media.AudioAttributes readAudioAttributes(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        return new android.media.AudioAttributes.Builder().setUsage(readInt).setContentType(readInt2).setCapturePreset(readInt3).setFlags(parcel.readInt()).build();
    }

    public int getUid() {
        return this.mUid;
    }

    public java.lang.String getPackage() {
        return this.mPkg;
    }

    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAttrs;
    }

    public android.os.IBinder getToken() {
        return this.mToken;
    }

    public android.os.VibrationAttributes getVibrationAttributes() {
        return new android.os.VibrationAttributes.Builder(this.mAttrs).build();
    }

    public boolean mute() {
        try {
            this.mController.mute();
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.wtf(TAG, "Failed to mute vibration stream: " + this, e);
            return false;
        }
    }

    public boolean unmute() {
        try {
            this.mController.unmute();
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.wtf(TAG, "Failed to unmute vibration stream: " + this, e);
            return false;
        }
    }

    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        try {
            this.mToken.linkToDeath(deathRecipient, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.wtf(TAG, "Failed to link to token death: " + this, e);
        }
    }

    public void unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        try {
            this.mToken.unlinkToDeath(deathRecipient, 0);
        } catch (java.util.NoSuchElementException e) {
            android.util.Slog.wtf(TAG, "Failed to unlink to token death", e);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.ExternalVibration)) {
            return false;
        }
        return this.mToken.equals(((android.os.ExternalVibration) obj).mToken);
    }

    public java.lang.String toString() {
        return "ExternalVibration{uid=" + this.mUid + ", pkg=" + this.mPkg + ", attrs=" + this.mAttrs + ", controller=" + this.mController + "token=" + this.mToken + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mPkg);
        writeAudioAttributes(this.mAttrs, parcel);
        parcel.writeStrongBinder(this.mController.asBinder());
        parcel.writeStrongBinder(this.mToken);
    }

    private static void writeAudioAttributes(android.media.AudioAttributes audioAttributes, android.os.Parcel parcel) {
        parcel.writeInt(audioAttributes.getUsage());
        parcel.writeInt(audioAttributes.getContentType());
        parcel.writeInt(audioAttributes.getCapturePreset());
        parcel.writeInt(audioAttributes.getAllFlags());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
