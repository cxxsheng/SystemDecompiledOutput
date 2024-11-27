package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public class SizeList implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.extension.SizeList> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.extension.SizeList>() { // from class: android.hardware.camera2.extension.SizeList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.SizeList createFromParcel(android.os.Parcel parcel) {
            android.hardware.camera2.extension.SizeList sizeList = new android.hardware.camera2.extension.SizeList();
            sizeList.readFromParcel(parcel);
            return sizeList;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.SizeList[] newArray(int i) {
            return new android.hardware.camera2.extension.SizeList[i];
        }
    };
    public int format = 0;
    public java.util.List<android.hardware.camera2.extension.Size> sizes;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.format);
        parcel.writeTypedList(this.sizes, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.format = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.sizes = parcel.createTypedArrayList(android.hardware.camera2.extension.Size.CREATOR);
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.sizes) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.util.Collection) {
            java.util.Iterator it = ((java.util.Collection) obj).iterator();
            while (it.hasNext()) {
                i |= describeContents(it.next());
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
