package android.view;

/* loaded from: classes4.dex */
public abstract class AbsSavedState implements android.os.Parcelable {
    private final android.os.Parcelable mSuperState;
    public static final android.view.AbsSavedState EMPTY_STATE = new android.view.AbsSavedState() { // from class: android.view.AbsSavedState.1
    };
    public static final android.os.Parcelable.Creator<android.view.AbsSavedState> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.view.AbsSavedState>() { // from class: android.view.AbsSavedState.2
        @Override // android.os.Parcelable.Creator
        public android.view.AbsSavedState createFromParcel(android.os.Parcel parcel) {
            return createFromParcel(parcel, (java.lang.ClassLoader) null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public android.view.AbsSavedState createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            if (parcel.readParcelable(classLoader) != null) {
                throw new java.lang.IllegalStateException("superState must be null");
            }
            return android.view.AbsSavedState.EMPTY_STATE;
        }

        @Override // android.os.Parcelable.Creator
        public android.view.AbsSavedState[] newArray(int i) {
            return new android.view.AbsSavedState[i];
        }
    };

    private AbsSavedState() {
        this.mSuperState = null;
    }

    protected AbsSavedState(android.os.Parcelable parcelable) {
        if (parcelable == null) {
            throw new java.lang.IllegalArgumentException("superState must not be null");
        }
        this.mSuperState = parcelable == EMPTY_STATE ? null : parcelable;
    }

    protected AbsSavedState(android.os.Parcel parcel) {
        this(parcel, null);
    }

    protected AbsSavedState(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        android.os.Parcelable readParcelable = parcel.readParcelable(classLoader);
        this.mSuperState = readParcelable == null ? EMPTY_STATE : readParcelable;
    }

    public final android.os.Parcelable getSuperState() {
        return this.mSuperState;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mSuperState, i);
    }
}
