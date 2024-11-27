package android.graphics;

/* loaded from: classes.dex */
public final class ParcelableColorSpace implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.graphics.ParcelableColorSpace> CREATOR = new android.os.Parcelable.Creator<android.graphics.ParcelableColorSpace>() { // from class: android.graphics.ParcelableColorSpace.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.ParcelableColorSpace createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return new android.graphics.ParcelableColorSpace(new android.graphics.ColorSpace.Rgb(parcel.readString(), parcel.createFloatArray(), parcel.createFloatArray(), new android.graphics.ColorSpace.Rgb.TransferParameters(parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble())));
            }
            return new android.graphics.ParcelableColorSpace(android.graphics.ColorSpace.get(readInt));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.ParcelableColorSpace[] newArray(int i) {
            return new android.graphics.ParcelableColorSpace[i];
        }
    };
    private final android.graphics.ColorSpace mColorSpace;

    public static boolean isParcelable(android.graphics.ColorSpace colorSpace) {
        if (colorSpace.getId() == -1) {
            return (colorSpace instanceof android.graphics.ColorSpace.Rgb) && ((android.graphics.ColorSpace.Rgb) colorSpace).getTransferParameters() != null;
        }
        return true;
    }

    public ParcelableColorSpace(android.graphics.ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        if (this.mColorSpace.getId() == -1) {
            if (!(this.mColorSpace instanceof android.graphics.ColorSpace.Rgb)) {
                throw new java.lang.IllegalArgumentException("Unable to parcel unknown ColorSpaces that are not ColorSpace.Rgb");
            }
            if (((android.graphics.ColorSpace.Rgb) this.mColorSpace).getTransferParameters() == null) {
                throw new java.lang.IllegalArgumentException("ColorSpace must use an ICC parametric transfer function to be parcelable");
            }
        }
    }

    public android.graphics.ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int id = this.mColorSpace.getId();
        parcel.writeInt(id);
        if (id == -1) {
            android.graphics.ColorSpace.Rgb rgb = (android.graphics.ColorSpace.Rgb) this.mColorSpace;
            parcel.writeString(rgb.getName());
            parcel.writeFloatArray(rgb.getPrimaries());
            parcel.writeFloatArray(rgb.getWhitePoint());
            android.graphics.ColorSpace.Rgb.TransferParameters transferParameters = rgb.getTransferParameters();
            parcel.writeDouble(transferParameters.a);
            parcel.writeDouble(transferParameters.b);
            parcel.writeDouble(transferParameters.c);
            parcel.writeDouble(transferParameters.d);
            parcel.writeDouble(transferParameters.e);
            parcel.writeDouble(transferParameters.f);
            parcel.writeDouble(transferParameters.g);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mColorSpace.equals(((android.graphics.ParcelableColorSpace) obj).mColorSpace);
    }

    public int hashCode() {
        return this.mColorSpace.hashCode();
    }
}
