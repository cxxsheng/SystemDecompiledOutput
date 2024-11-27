package android.hardware.radio;

/* loaded from: classes2.dex */
final class Utils {
    private static final java.lang.String TAG = "BroadcastRadio.utils";

    Utils() {
    }

    static void writeStringMap(android.os.Parcel parcel, java.util.Map<java.lang.String, java.lang.String> map) {
        if (map == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(map.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }

    static java.util.Map<java.lang.String, java.lang.String> readStringMap(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.util.HashMap hashMap = new java.util.HashMap(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt > 0) {
                hashMap.put(parcel.readString(), parcel.readString());
                readInt = i;
            } else {
                return hashMap;
            }
        }
    }

    static void writeStringIntMap(android.os.Parcel parcel, java.util.Map<java.lang.String, java.lang.Integer> map) {
        if (map == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(map.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : map.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeInt(entry.getValue().intValue());
        }
    }

    static java.util.Map<java.lang.String, java.lang.Integer> readStringIntMap(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.util.HashMap hashMap = new java.util.HashMap(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt > 0) {
                hashMap.put(parcel.readString(), java.lang.Integer.valueOf(parcel.readInt()));
                readInt = i;
            } else {
                return hashMap;
            }
        }
    }

    static <T extends android.os.Parcelable> void writeSet(final android.os.Parcel parcel, java.util.Set<T> set) {
        if (set == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(set.size());
            set.stream().forEach(new java.util.function.Consumer() { // from class: android.hardware.radio.Utils$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.os.Parcel.this.writeTypedObject((android.os.Parcelable) obj, 0);
                }
            });
        }
    }

    static <T> java.util.Set<T> createSet(android.os.Parcel parcel, android.os.Parcelable.Creator<T> creator) {
        int readInt = parcel.readInt();
        java.util.HashSet hashSet = new java.util.HashSet(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt > 0) {
                hashSet.add(parcel.readTypedObject(creator));
                readInt = i;
            } else {
                return hashSet;
            }
        }
    }

    static void writeIntSet(final android.os.Parcel parcel, java.util.Set<java.lang.Integer> set) {
        if (set == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(set.size());
            set.stream().forEach(new java.util.function.Consumer() { // from class: android.hardware.radio.Utils$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.os.Parcel.this.writeInt(((java.lang.Integer) java.util.Objects.requireNonNull((java.lang.Integer) obj)).intValue());
                }
            });
        }
    }

    static java.util.Set<java.lang.Integer> createIntSet(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.util.HashSet hashSet = new java.util.HashSet(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt > 0) {
                hashSet.add(java.lang.Integer.valueOf(parcel.readInt()));
                readInt = i;
            } else {
                return hashSet;
            }
        }
    }

    static <T extends android.os.Parcelable> void writeTypedCollection(android.os.Parcel parcel, java.util.Collection<T> collection) {
        java.util.ArrayList arrayList;
        if (collection == null) {
            arrayList = null;
        } else if (collection instanceof java.util.ArrayList) {
            arrayList = (java.util.ArrayList) collection;
        } else {
            arrayList = new java.util.ArrayList(collection);
        }
        parcel.writeTypedList(arrayList);
    }

    static void close(android.hardware.radio.ICloseHandle iCloseHandle) {
        try {
            iCloseHandle.close();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
