package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class Announcement implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.Announcement> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.Announcement>() { // from class: android.hardware.broadcastradio.Announcement.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.Announcement createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.Announcement announcement = new android.hardware.broadcastradio.Announcement();
            announcement.readFromParcel(parcel);
            return announcement;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.Announcement[] newArray(int i) {
            return new android.hardware.broadcastradio.Announcement[i];
        }
    };
    public android.hardware.broadcastradio.ProgramSelector selector;
    public byte type = 0;
    public android.hardware.broadcastradio.VendorKeyValue[] vendorInfo;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.selector, i);
        parcel.writeByte(this.type);
        parcel.writeTypedArray(this.vendorInfo, i);
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
            this.selector = (android.hardware.broadcastradio.ProgramSelector) parcel.readTypedObject(android.hardware.broadcastradio.ProgramSelector.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.type = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.vendorInfo = (android.hardware.broadcastradio.VendorKeyValue[]) parcel.createTypedArray(android.hardware.broadcastradio.VendorKeyValue.CREATOR);
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

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("selector: " + java.util.Objects.toString(this.selector));
        stringJoiner.add("type: " + android.hardware.broadcastradio.AnnouncementType$$.toString(this.type));
        stringJoiner.add("vendorInfo: " + java.util.Arrays.toString(this.vendorInfo));
        return "Announcement" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.Announcement)) {
            return false;
        }
        android.hardware.broadcastradio.Announcement announcement = (android.hardware.broadcastradio.Announcement) obj;
        if (java.util.Objects.deepEquals(this.selector, announcement.selector) && java.util.Objects.deepEquals(java.lang.Byte.valueOf(this.type), java.lang.Byte.valueOf(announcement.type)) && java.util.Objects.deepEquals(this.vendorInfo, announcement.vendorInfo)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.selector, java.lang.Byte.valueOf(this.type), this.vendorInfo).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.selector) | 0 | describeContents(this.vendorInfo);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
