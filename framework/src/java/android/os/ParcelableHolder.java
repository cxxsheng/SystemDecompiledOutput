package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ParcelableHolder implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ParcelableHolder> CREATOR = new android.os.Parcelable.Creator<android.os.ParcelableHolder>() { // from class: android.os.ParcelableHolder.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelableHolder createFromParcel(android.os.Parcel parcel) {
            android.os.ParcelableHolder parcelableHolder = new android.os.ParcelableHolder();
            parcelableHolder.readFromParcel(parcel);
            return parcelableHolder;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelableHolder[] newArray(int i) {
            return new android.os.ParcelableHolder[i];
        }
    };
    private android.os.Parcel mParcel;
    private android.os.Parcelable mParcelable;
    private int mStability;

    public ParcelableHolder(int i) {
        this.mStability = 0;
        this.mStability = i;
    }

    private ParcelableHolder() {
        this.mStability = 0;
    }

    @Override // android.os.Parcelable
    public int getStability() {
        return this.mStability;
    }

    public void setParcelable(android.os.Parcelable parcelable) {
        if (parcelable != null && getStability() > parcelable.getStability()) {
            throw new android.os.BadParcelableException("A ParcelableHolder can only hold things at its stability or higher. The ParcelableHolder's stability is " + getStability() + ", but the parcelable's stability is " + parcelable.getStability());
        }
        this.mParcelable = parcelable;
        if (this.mParcel != null) {
            this.mParcel.recycle();
            this.mParcel = null;
        }
    }

    public <T extends android.os.Parcelable> T getParcelable(java.lang.Class<T> cls) {
        if (this.mParcel == null) {
            if (this.mParcelable != null && !cls.isInstance(this.mParcelable)) {
                throw new android.os.BadParcelableException("The ParcelableHolder has " + this.mParcelable.getClass().getName() + ", but the requested type is " + cls.getName());
            }
            return (T) this.mParcelable;
        }
        this.mParcel.setDataPosition(0);
        T t = (T) this.mParcel.readParcelable(cls.getClassLoader());
        if (t != null && !cls.isInstance(t)) {
            throw new android.os.BadParcelableException("The ParcelableHolder has " + t.getClass().getName() + ", but the requested type is " + cls.getName());
        }
        this.mParcelable = t;
        this.mParcel.recycle();
        this.mParcel = null;
        return t;
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (this.mStability != readInt) {
            throw new java.lang.IllegalArgumentException("Expected stability " + this.mStability + " but got " + readInt);
        }
        this.mParcelable = null;
        int readInt2 = parcel.readInt();
        if (readInt2 < 0) {
            throw new java.lang.IllegalArgumentException("dataSize from parcel is negative");
        }
        if (readInt2 == 0) {
            if (this.mParcel != null) {
                this.mParcel.recycle();
                this.mParcel = null;
                return;
            }
            return;
        }
        if (this.mParcel == null) {
            this.mParcel = android.os.Parcel.obtain();
        }
        this.mParcel.setDataPosition(0);
        this.mParcel.setDataSize(0);
        int dataPosition = parcel.dataPosition();
        this.mParcel.appendFrom(parcel, dataPosition, readInt2);
        parcel.setDataPosition(android.util.MathUtils.addOrThrow(dataPosition, readInt2));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStability);
        if (this.mParcel != null) {
            parcel.writeInt(this.mParcel.dataSize());
            parcel.appendFrom(this.mParcel, 0, this.mParcel.dataSize());
            return;
        }
        if (this.mParcelable == null) {
            parcel.writeInt(0);
            return;
        }
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.writeParcelable(this.mParcelable, 0);
        int dataPosition3 = parcel.dataPosition() - dataPosition2;
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3);
        parcel.setDataPosition(android.util.MathUtils.addOrThrow(parcel.dataPosition(), dataPosition3));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.mParcel != null) {
            return this.mParcel.hasFileDescriptors() ? 1 : 0;
        }
        if (this.mParcelable != null) {
            return this.mParcelable.describeContents();
        }
        return 0;
    }
}
