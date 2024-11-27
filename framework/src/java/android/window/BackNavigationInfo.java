package android.window;

/* loaded from: classes4.dex */
public final class BackNavigationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.BackNavigationInfo> CREATOR = new android.os.Parcelable.Creator<android.window.BackNavigationInfo>() { // from class: android.window.BackNavigationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.BackNavigationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.BackNavigationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.BackNavigationInfo[] newArray(int i) {
            return new android.window.BackNavigationInfo[i];
        }
    };
    public static final java.lang.String KEY_TRIGGER_BACK = "TriggerBack";
    public static final int TYPE_CALLBACK = 4;
    public static final int TYPE_CROSS_ACTIVITY = 2;
    public static final int TYPE_CROSS_TASK = 3;
    public static final int TYPE_DIALOG_CLOSE = 0;
    public static final int TYPE_RETURN_TO_HOME = 1;
    public static final int TYPE_UNDEFINED = -1;
    private final boolean mAnimationCallback;
    private final android.window.BackNavigationInfo.CustomAnimationInfo mCustomAnimationInfo;
    private final android.window.IOnBackInvokedCallback mOnBackInvokedCallback;
    private final android.os.RemoteCallback mOnBackNavigationDone;
    private final boolean mPrepareRemoteAnimation;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BackTargetType {
    }

    private BackNavigationInfo(int i, android.os.RemoteCallback remoteCallback, android.window.IOnBackInvokedCallback iOnBackInvokedCallback, boolean z, boolean z2, android.window.BackNavigationInfo.CustomAnimationInfo customAnimationInfo) {
        this.mType = i;
        this.mOnBackNavigationDone = remoteCallback;
        this.mOnBackInvokedCallback = iOnBackInvokedCallback;
        this.mPrepareRemoteAnimation = z;
        this.mAnimationCallback = z2;
        this.mCustomAnimationInfo = customAnimationInfo;
    }

    private BackNavigationInfo(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mOnBackNavigationDone = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
        this.mOnBackInvokedCallback = android.window.IOnBackInvokedCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mPrepareRemoteAnimation = parcel.readBoolean();
        this.mAnimationCallback = parcel.readBoolean();
        this.mCustomAnimationInfo = (android.window.BackNavigationInfo.CustomAnimationInfo) parcel.readTypedObject(android.window.BackNavigationInfo.CustomAnimationInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeTypedObject(this.mOnBackNavigationDone, i);
        parcel.writeStrongInterface(this.mOnBackInvokedCallback);
        parcel.writeBoolean(this.mPrepareRemoteAnimation);
        parcel.writeBoolean(this.mAnimationCallback);
        parcel.writeTypedObject(this.mCustomAnimationInfo, i);
    }

    public int getType() {
        return this.mType;
    }

    public android.window.IOnBackInvokedCallback getOnBackInvokedCallback() {
        return this.mOnBackInvokedCallback;
    }

    public boolean isPrepareRemoteAnimation() {
        return this.mPrepareRemoteAnimation;
    }

    public boolean isAnimationCallback() {
        return this.mAnimationCallback;
    }

    public void onBackNavigationFinished(boolean z) {
        if (this.mOnBackNavigationDone != null) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putBoolean(KEY_TRIGGER_BACK, z);
            this.mOnBackNavigationDone.sendResult(bundle);
        }
    }

    public android.window.BackNavigationInfo.CustomAnimationInfo getCustomAnimationInfo() {
        return this.mCustomAnimationInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "BackNavigationInfo{mType=" + typeToString(this.mType) + " (" + this.mType + "), mOnBackNavigationDone=" + this.mOnBackNavigationDone + ", mOnBackInvokedCallback=" + this.mOnBackInvokedCallback + ", mPrepareRemoteAnimation=" + this.mPrepareRemoteAnimation + ", mAnimationCallback=" + this.mAnimationCallback + ", mCustomizeAnimationInfo=" + this.mCustomAnimationInfo + '}';
    }

    public static java.lang.String typeToString(int i) {
        switch (i) {
            case -1:
                return "TYPE_UNDEFINED";
            case 0:
                return "TYPE_DIALOG_CLOSE";
            case 1:
                return "TYPE_RETURN_TO_HOME";
            case 2:
                return "TYPE_CROSS_ACTIVITY";
            case 3:
                return "TYPE_CROSS_TASK";
            case 4:
                return "TYPE_CALLBACK";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    public static final class CustomAnimationInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.BackNavigationInfo.CustomAnimationInfo> CREATOR = new android.os.Parcelable.Creator<android.window.BackNavigationInfo.CustomAnimationInfo>() { // from class: android.window.BackNavigationInfo.CustomAnimationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.BackNavigationInfo.CustomAnimationInfo createFromParcel(android.os.Parcel parcel) {
                return new android.window.BackNavigationInfo.CustomAnimationInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.BackNavigationInfo.CustomAnimationInfo[] newArray(int i) {
                return new android.window.BackNavigationInfo.CustomAnimationInfo[i];
            }
        };
        private int mCustomBackground;
        private int mCustomEnterAnim;
        private int mCustomExitAnim;
        private final java.lang.String mPackageName;
        private int mWindowAnimations;

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public int getWindowAnimations() {
            return this.mWindowAnimations;
        }

        public int getCustomExitAnim() {
            return this.mCustomExitAnim;
        }

        public int getCustomEnterAnim() {
            return this.mCustomEnterAnim;
        }

        public int getCustomBackground() {
            return this.mCustomBackground;
        }

        public CustomAnimationInfo(java.lang.String str) {
            this.mPackageName = str;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.mPackageName);
            parcel.writeInt(this.mWindowAnimations);
            parcel.writeInt(this.mCustomEnterAnim);
            parcel.writeInt(this.mCustomExitAnim);
            parcel.writeInt(this.mCustomBackground);
        }

        private CustomAnimationInfo(android.os.Parcel parcel) {
            this.mPackageName = parcel.readString8();
            this.mWindowAnimations = parcel.readInt();
            this.mCustomEnterAnim = parcel.readInt();
            this.mCustomExitAnim = parcel.readInt();
            this.mCustomBackground = parcel.readInt();
        }

        public java.lang.String toString() {
            return "CustomAnimationInfo, package name= " + this.mPackageName;
        }
    }

    public static class Builder {
        private android.window.BackNavigationInfo.CustomAnimationInfo mCustomAnimationInfo;
        private boolean mPrepareRemoteAnimation;
        private int mType = -1;
        private android.os.RemoteCallback mOnBackNavigationDone = null;
        private android.window.IOnBackInvokedCallback mOnBackInvokedCallback = null;
        private boolean mAnimationCallback = false;

        public android.window.BackNavigationInfo.Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public android.window.BackNavigationInfo.Builder setOnBackNavigationDone(android.os.RemoteCallback remoteCallback) {
            this.mOnBackNavigationDone = remoteCallback;
            return this;
        }

        public android.window.BackNavigationInfo.Builder setOnBackInvokedCallback(android.window.IOnBackInvokedCallback iOnBackInvokedCallback) {
            this.mOnBackInvokedCallback = iOnBackInvokedCallback;
            return this;
        }

        public android.window.BackNavigationInfo.Builder setPrepareRemoteAnimation(boolean z) {
            this.mPrepareRemoteAnimation = z;
            return this;
        }

        public android.window.BackNavigationInfo.Builder setWindowAnimations(java.lang.String str, int i) {
            if (this.mCustomAnimationInfo == null) {
                this.mCustomAnimationInfo = new android.window.BackNavigationInfo.CustomAnimationInfo(str);
            }
            this.mCustomAnimationInfo.mWindowAnimations = i;
            return this;
        }

        public android.window.BackNavigationInfo.Builder setCustomAnimation(java.lang.String str, int i, int i2, int i3) {
            if (this.mCustomAnimationInfo == null) {
                this.mCustomAnimationInfo = new android.window.BackNavigationInfo.CustomAnimationInfo(str);
            }
            this.mCustomAnimationInfo.mCustomExitAnim = i2;
            this.mCustomAnimationInfo.mCustomEnterAnim = i;
            this.mCustomAnimationInfo.mCustomBackground = i3;
            return this;
        }

        public android.window.BackNavigationInfo.Builder setAnimationCallback(boolean z) {
            this.mAnimationCallback = z;
            return this;
        }

        public android.window.BackNavigationInfo build() {
            return new android.window.BackNavigationInfo(this.mType, this.mOnBackNavigationDone, this.mOnBackInvokedCallback, this.mPrepareRemoteAnimation, this.mAnimationCallback, this.mCustomAnimationInfo);
        }
    }
}
