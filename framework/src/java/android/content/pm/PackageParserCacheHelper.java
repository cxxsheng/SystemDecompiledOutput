package android.content.pm;

/* loaded from: classes.dex */
public class PackageParserCacheHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PackageParserCacheHelper";

    private PackageParserCacheHelper() {
    }

    public static class ReadHelper extends android.os.Parcel.ReadWriteHelper {
        private final android.os.Parcel mParcel;
        private final java.util.ArrayList<java.lang.String> mStrings = new java.util.ArrayList<>();

        public ReadHelper(android.os.Parcel parcel) {
            this.mParcel = parcel;
        }

        public void startAndInstall() {
            this.mStrings.clear();
            int readInt = this.mParcel.readInt();
            if (readInt < 0) {
                throw new java.lang.IllegalStateException("Invalid string pool position: " + readInt);
            }
            int dataPosition = this.mParcel.dataPosition();
            this.mParcel.setDataPosition(readInt);
            this.mParcel.readStringList(this.mStrings);
            this.mParcel.setDataPosition(dataPosition);
            this.mParcel.setReadWriteHelper(this);
        }

        public java.lang.String readString(android.os.Parcel parcel) {
            return this.mStrings.get(parcel.readInt());
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public java.lang.String readString8(android.os.Parcel parcel) {
            return readString(parcel);
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public java.lang.String readString16(android.os.Parcel parcel) {
            return readString(parcel);
        }
    }

    public static class WriteHelper extends android.os.Parcel.ReadWriteHelper {
        private final android.os.Parcel mParcel;
        private final int mStartPos;
        private final java.util.ArrayList<java.lang.String> mStrings = new java.util.ArrayList<>();
        private final java.util.HashMap<java.lang.String, java.lang.Integer> mIndexes = new java.util.HashMap<>();

        public WriteHelper(android.os.Parcel parcel) {
            this.mParcel = parcel;
            this.mStartPos = parcel.dataPosition();
            this.mParcel.writeInt(0);
            this.mParcel.setReadWriteHelper(this);
        }

        public void writeString(android.os.Parcel parcel, java.lang.String str) {
            java.lang.Integer num = this.mIndexes.get(str);
            if (num != null) {
                parcel.writeInt(num.intValue());
                return;
            }
            int size = this.mStrings.size();
            this.mIndexes.put(str, java.lang.Integer.valueOf(size));
            this.mStrings.add(str);
            parcel.writeInt(size);
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public void writeString8(android.os.Parcel parcel, java.lang.String str) {
            writeString(parcel, str);
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public void writeString16(android.os.Parcel parcel, java.lang.String str) {
            writeString(parcel, str);
        }

        public void finishAndUninstall() {
            this.mParcel.setReadWriteHelper(null);
            int dataPosition = this.mParcel.dataPosition();
            this.mParcel.writeStringList(this.mStrings);
            this.mParcel.setDataPosition(this.mStartPos);
            this.mParcel.writeInt(dataPosition);
            this.mParcel.setDataPosition(this.mParcel.dataSize());
        }
    }
}
