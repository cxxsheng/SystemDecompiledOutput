package android.content.pm.verify.domain;

/* loaded from: classes.dex */
public class DomainVerificationUtils {
    private static final int STRINGS_TARGET_BYTE_SIZE = android.os.IBinder.getSuggestedMaxIpcSizeBytes() / 2;

    public static void writeHostMap(android.os.Parcel parcel, java.util.Map<java.lang.String, ?> map) {
        boolean z;
        int dataSize = parcel.dataSize();
        java.util.Iterator<java.lang.String> it = map.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            dataSize += estimatedByteSizeOf(it.next());
            if (dataSize > STRINGS_TARGET_BYTE_SIZE) {
                z = true;
                break;
            }
        }
        parcel.writeBoolean(z);
        if (!z) {
            parcel.writeMap(map);
            return;
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.writeMap(map);
            parcel.writeBlob(obtain.marshall());
        } finally {
            obtain.recycle();
        }
    }

    public static <T extends java.util.Map> T readHostMap(android.os.Parcel parcel, T t, java.lang.ClassLoader classLoader) {
        if (!parcel.readBoolean()) {
            parcel.readMap(t, classLoader);
            return t;
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            byte[] readBlob = parcel.readBlob();
            obtain.unmarshall(readBlob, 0, readBlob.length);
            obtain.setDataPosition(0);
            obtain.readMap(t, classLoader);
            return t;
        } finally {
            obtain.recycle();
        }
    }

    public static void writeHostSet(android.os.Parcel parcel, java.util.Set<java.lang.String> set) {
        boolean z;
        int dataSize = parcel.dataSize();
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            dataSize += estimatedByteSizeOf(it.next());
            if (dataSize > STRINGS_TARGET_BYTE_SIZE) {
                z = true;
                break;
            }
        }
        parcel.writeBoolean(z);
        if (!z) {
            writeSet(parcel, set);
            return;
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            writeSet(obtain, set);
            parcel.writeBlob(obtain.marshall());
        } finally {
            obtain.recycle();
        }
    }

    public static java.util.Set<java.lang.String> readHostSet(android.os.Parcel parcel) {
        if (!parcel.readBoolean()) {
            return readSet(parcel);
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            byte[] readBlob = parcel.readBlob();
            obtain.unmarshall(readBlob, 0, readBlob.length);
            obtain.setDataPosition(0);
            return readSet(obtain);
        } finally {
            obtain.recycle();
        }
    }

    private static void writeSet(android.os.Parcel parcel, java.util.Set<java.lang.String> set) {
        if (set == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(set.size());
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }

    private static java.util.Set<java.lang.String> readSet(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == -1) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(readInt);
        for (int i = 0; i < readInt; i++) {
            arraySet.add(parcel.readString());
        }
        return arraySet;
    }

    public static int estimatedByteSizeOf(java.lang.String str) {
        return (str.length() * 2) + 12;
    }
}
