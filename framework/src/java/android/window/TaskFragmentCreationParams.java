package android.window;

/* loaded from: classes4.dex */
public final class TaskFragmentCreationParams implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskFragmentCreationParams> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentCreationParams>() { // from class: android.window.TaskFragmentCreationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentCreationParams createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskFragmentCreationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentCreationParams[] newArray(int i) {
            return new android.window.TaskFragmentCreationParams[i];
        }
    };
    private final boolean mAllowTransitionWhenEmpty;
    private final android.os.IBinder mFragmentToken;
    private final android.graphics.Rect mInitialRelativeBounds;
    private final android.window.TaskFragmentOrganizerToken mOrganizer;
    private final android.os.IBinder mOwnerToken;
    private final android.os.IBinder mPairedActivityToken;
    private final android.os.IBinder mPairedPrimaryFragmentToken;
    private final int mWindowingMode;

    private TaskFragmentCreationParams(android.window.TaskFragmentOrganizerToken taskFragmentOrganizerToken, android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i, android.os.IBinder iBinder3, android.os.IBinder iBinder4, boolean z) {
        this.mInitialRelativeBounds = new android.graphics.Rect();
        if (iBinder3 != null && iBinder4 != null) {
            throw new java.lang.IllegalArgumentException("pairedPrimaryFragmentToken and pairedActivityToken should not be set at the same time.");
        }
        this.mOrganizer = taskFragmentOrganizerToken;
        this.mFragmentToken = iBinder;
        this.mOwnerToken = iBinder2;
        this.mInitialRelativeBounds.set(rect);
        this.mWindowingMode = i;
        this.mPairedPrimaryFragmentToken = iBinder3;
        this.mPairedActivityToken = iBinder4;
        this.mAllowTransitionWhenEmpty = z;
    }

    public android.window.TaskFragmentOrganizerToken getOrganizer() {
        return this.mOrganizer;
    }

    public android.os.IBinder getFragmentToken() {
        return this.mFragmentToken;
    }

    public android.os.IBinder getOwnerToken() {
        return this.mOwnerToken;
    }

    public android.graphics.Rect getInitialRelativeBounds() {
        return this.mInitialRelativeBounds;
    }

    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public android.os.IBinder getPairedPrimaryFragmentToken() {
        return this.mPairedPrimaryFragmentToken;
    }

    public android.os.IBinder getPairedActivityToken() {
        return this.mPairedActivityToken;
    }

    public boolean getAllowTransitionWhenEmpty() {
        return this.mAllowTransitionWhenEmpty;
    }

    private TaskFragmentCreationParams(android.os.Parcel parcel) {
        this.mInitialRelativeBounds = new android.graphics.Rect();
        this.mOrganizer = android.window.TaskFragmentOrganizerToken.CREATOR.createFromParcel(parcel);
        this.mFragmentToken = parcel.readStrongBinder();
        this.mOwnerToken = parcel.readStrongBinder();
        this.mInitialRelativeBounds.readFromParcel(parcel);
        this.mWindowingMode = parcel.readInt();
        this.mPairedPrimaryFragmentToken = parcel.readStrongBinder();
        this.mPairedActivityToken = parcel.readStrongBinder();
        this.mAllowTransitionWhenEmpty = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mOrganizer.writeToParcel(parcel, i);
        parcel.writeStrongBinder(this.mFragmentToken);
        parcel.writeStrongBinder(this.mOwnerToken);
        this.mInitialRelativeBounds.writeToParcel(parcel, i);
        parcel.writeInt(this.mWindowingMode);
        parcel.writeStrongBinder(this.mPairedPrimaryFragmentToken);
        parcel.writeStrongBinder(this.mPairedActivityToken);
        parcel.writeBoolean(this.mAllowTransitionWhenEmpty);
    }

    public java.lang.String toString() {
        return "TaskFragmentCreationParams{ organizer=" + this.mOrganizer + " fragmentToken=" + this.mFragmentToken + " ownerToken=" + this.mOwnerToken + " initialRelativeBounds=" + this.mInitialRelativeBounds + " windowingMode=" + this.mWindowingMode + " pairedFragmentToken=" + this.mPairedPrimaryFragmentToken + " pairedActivityToken=" + this.mPairedActivityToken + " allowTransitionWhenEmpty=" + this.mAllowTransitionWhenEmpty + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private boolean mAllowTransitionWhenEmpty;
        private final android.os.IBinder mFragmentToken;
        private final android.window.TaskFragmentOrganizerToken mOrganizer;
        private final android.os.IBinder mOwnerToken;
        private android.os.IBinder mPairedActivityToken;
        private android.os.IBinder mPairedPrimaryFragmentToken;
        private final android.graphics.Rect mInitialRelativeBounds = new android.graphics.Rect();
        private int mWindowingMode = 0;

        public Builder(android.window.TaskFragmentOrganizerToken taskFragmentOrganizerToken, android.os.IBinder iBinder, android.os.IBinder iBinder2) {
            this.mOrganizer = taskFragmentOrganizerToken;
            this.mFragmentToken = iBinder;
            this.mOwnerToken = iBinder2;
        }

        public android.window.TaskFragmentCreationParams.Builder setInitialRelativeBounds(android.graphics.Rect rect) {
            this.mInitialRelativeBounds.set(rect);
            return this;
        }

        public android.window.TaskFragmentCreationParams.Builder setWindowingMode(int i) {
            this.mWindowingMode = i;
            return this;
        }

        public android.window.TaskFragmentCreationParams.Builder setPairedPrimaryFragmentToken(android.os.IBinder iBinder) {
            this.mPairedPrimaryFragmentToken = iBinder;
            return this;
        }

        public android.window.TaskFragmentCreationParams.Builder setPairedActivityToken(android.os.IBinder iBinder) {
            this.mPairedActivityToken = iBinder;
            return this;
        }

        public android.window.TaskFragmentCreationParams.Builder setAllowTransitionWhenEmpty(boolean z) {
            this.mAllowTransitionWhenEmpty = z;
            return this;
        }

        public android.window.TaskFragmentCreationParams build() {
            return new android.window.TaskFragmentCreationParams(this.mOrganizer, this.mFragmentToken, this.mOwnerToken, this.mInitialRelativeBounds, this.mWindowingMode, this.mPairedPrimaryFragmentToken, this.mPairedActivityToken, this.mAllowTransitionWhenEmpty);
        }
    }
}
