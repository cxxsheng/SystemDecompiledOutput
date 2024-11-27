package android.graphics;

/* loaded from: classes.dex */
public class LeakyTypefaceStorage {
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final java.util.ArrayList<android.graphics.Typeface> sStorage = new java.util.ArrayList<>();
    private static final android.util.ArrayMap<android.graphics.Typeface, java.lang.Integer> sTypefaceMap = new android.util.ArrayMap<>();

    public static void writeTypefaceToParcel(android.graphics.Typeface typeface, android.os.Parcel parcel) {
        int i;
        parcel.writeInt(android.os.Process.myPid());
        synchronized (sLock) {
            java.lang.Integer num = sTypefaceMap.get(typeface);
            if (num != null) {
                i = num.intValue();
            } else {
                int size = sStorage.size();
                sStorage.add(typeface);
                sTypefaceMap.put(typeface, java.lang.Integer.valueOf(size));
                i = size;
            }
            parcel.writeInt(i);
        }
    }

    public static android.graphics.Typeface readTypefaceFromParcel(android.os.Parcel parcel) {
        android.graphics.Typeface typeface;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        if (readInt != android.os.Process.myPid()) {
            return null;
        }
        synchronized (sLock) {
            typeface = sStorage.get(readInt2);
        }
        return typeface;
    }
}
