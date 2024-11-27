package android.hardware.usb.gadget;

/* loaded from: classes2.dex */
public class GadgetFunction implements android.os.Parcelable {
    public static final long ACCESSORY = 2;
    public static final long ADB = 1;
    public static final long AUDIO_SOURCE = 64;
    public static final android.os.Parcelable.Creator<android.hardware.usb.gadget.GadgetFunction> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.gadget.GadgetFunction>() { // from class: android.hardware.usb.gadget.GadgetFunction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.gadget.GadgetFunction createFromParcel(android.os.Parcel parcel) {
            android.hardware.usb.gadget.GadgetFunction gadgetFunction = new android.hardware.usb.gadget.GadgetFunction();
            gadgetFunction.readFromParcel(parcel);
            return gadgetFunction;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.gadget.GadgetFunction[] newArray(int i) {
            return new android.hardware.usb.gadget.GadgetFunction[i];
        }
    };
    public static final long MIDI = 8;
    public static final long MTP = 4;
    public static final long NCM = 1024;
    public static final long NONE = 0;
    public static final long PTP = 16;
    public static final long RNDIS = 32;
    public static final long UVC = 128;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt < 4) {
            try {
                throw new android.os.BadParcelableException("Parcelable too small");
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
        if (dataPosition > Integer.MAX_VALUE - readInt) {
            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
        }
        parcel.setDataPosition(dataPosition + readInt);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
