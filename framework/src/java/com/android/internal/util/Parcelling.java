package com.android.internal.util;

/* loaded from: classes5.dex */
public interface Parcelling<T> {
    void parcel(T t, android.os.Parcel parcel, int i);

    T unparcel(android.os.Parcel parcel);

    public static class Cache {
        private static android.util.ArrayMap<java.lang.Class, com.android.internal.util.Parcelling> sCache = new android.util.ArrayMap<>();

        private Cache() {
        }

        public static <P extends com.android.internal.util.Parcelling<?>> P get(java.lang.Class<P> cls) {
            return (P) sCache.get(cls);
        }

        public static <P extends com.android.internal.util.Parcelling<?>> P put(P p) {
            sCache.put(p.getClass(), p);
            return p;
        }

        public static <P extends com.android.internal.util.Parcelling<?>> P getOrCreate(java.lang.Class<P> cls) {
            P p = (P) get(cls);
            if (p != null) {
                return p;
            }
            try {
                return (P) put(cls.newInstance());
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public interface BuiltIn {

        public static class ForInternedString implements com.android.internal.util.Parcelling<java.lang.String> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.lang.String str, android.os.Parcel parcel, int i) {
                parcel.writeString(str);
            }

            @Override // com.android.internal.util.Parcelling
            public java.lang.String unparcel(android.os.Parcel parcel) {
                return android.text.TextUtils.safeIntern(parcel.readString());
            }
        }

        public static class ForInternedStringArray implements com.android.internal.util.Parcelling<java.lang.String[]> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.lang.String[] strArr, android.os.Parcel parcel, int i) {
                parcel.writeStringArray(strArr);
            }

            @Override // com.android.internal.util.Parcelling
            public java.lang.String[] unparcel(android.os.Parcel parcel) {
                java.lang.String[] readStringArray = parcel.readStringArray();
                if (readStringArray != null) {
                    int size = com.android.internal.util.ArrayUtils.size(readStringArray);
                    for (int i = 0; i < size; i++) {
                        readStringArray[i] = android.text.TextUtils.safeIntern(readStringArray[i]);
                    }
                }
                return readStringArray;
            }
        }

        public static class ForInternedStringList implements com.android.internal.util.Parcelling<java.util.List<java.lang.String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.util.List<java.lang.String> list, android.os.Parcel parcel, int i) {
                parcel.writeStringList(list);
            }

            @Override // com.android.internal.util.Parcelling
            public java.util.List<java.lang.String> unparcel(android.os.Parcel parcel) {
                java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                if (createStringArrayList != null) {
                    int size = createStringArrayList.size();
                    for (int i = 0; i < size; i++) {
                        createStringArrayList.set(i, createStringArrayList.get(i).intern());
                    }
                }
                return com.android.internal.util.CollectionUtils.emptyIfNull(createStringArrayList);
            }
        }

        public static class ForInternedStringValueMap implements com.android.internal.util.Parcelling<java.util.Map<java.lang.String, java.lang.String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.util.Map<java.lang.String, java.lang.String> map, android.os.Parcel parcel, int i) {
                parcel.writeMap(map);
            }

            @Override // com.android.internal.util.Parcelling
            public java.util.Map<java.lang.String, java.lang.String> unparcel(android.os.Parcel parcel) {
                android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                parcel.readMap(arrayMap, java.lang.String.class.getClassLoader());
                for (int i = 0; i < arrayMap.size(); i++) {
                    arrayMap.setValueAt(i, android.text.TextUtils.safeIntern((java.lang.String) arrayMap.valueAt(i)));
                }
                return arrayMap;
            }
        }

        public static class ForStringSet implements com.android.internal.util.Parcelling<java.util.Set<java.lang.String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.util.Set<java.lang.String> set, android.os.Parcel parcel, int i) {
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

            @Override // com.android.internal.util.Parcelling
            public java.util.Set<java.lang.String> unparcel(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt < 0) {
                    return java.util.Collections.emptySet();
                }
                android.util.ArraySet arraySet = new android.util.ArraySet();
                for (int i = 0; i < readInt; i++) {
                    arraySet.add(parcel.readString());
                }
                return arraySet;
            }
        }

        public static class ForInternedStringSet implements com.android.internal.util.Parcelling<java.util.Set<java.lang.String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.util.Set<java.lang.String> set, android.os.Parcel parcel, int i) {
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

            @Override // com.android.internal.util.Parcelling
            public java.util.Set<java.lang.String> unparcel(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt < 0) {
                    return java.util.Collections.emptySet();
                }
                android.util.ArraySet arraySet = new android.util.ArraySet();
                for (int i = 0; i < readInt; i++) {
                    arraySet.add(android.text.TextUtils.safeIntern(parcel.readString()));
                }
                return arraySet;
            }
        }

        public static class ForInternedStringArraySet implements com.android.internal.util.Parcelling<android.util.ArraySet<java.lang.String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(android.util.ArraySet<java.lang.String> arraySet, android.os.Parcel parcel, int i) {
                if (arraySet == null) {
                    parcel.writeInt(-1);
                    return;
                }
                parcel.writeInt(arraySet.size());
                java.util.Iterator<java.lang.String> it = arraySet.iterator();
                while (it.hasNext()) {
                    parcel.writeString(it.next());
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.util.Parcelling
            public android.util.ArraySet<java.lang.String> unparcel(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt < 0) {
                    return null;
                }
                android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
                for (int i = 0; i < readInt; i++) {
                    arraySet.add(android.text.TextUtils.safeIntern(parcel.readString()));
                }
                return arraySet;
            }
        }

        public static class ForBoolean implements com.android.internal.util.Parcelling<java.lang.Boolean> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.lang.Boolean bool, android.os.Parcel parcel, int i) {
                if (bool == null) {
                    parcel.writeInt(1);
                } else if (!bool.booleanValue()) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(-1);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.util.Parcelling
            public java.lang.Boolean unparcel(android.os.Parcel parcel) {
                switch (parcel.readInt()) {
                    case -1:
                        return java.lang.Boolean.TRUE;
                    case 0:
                        return java.lang.Boolean.FALSE;
                    case 1:
                        return null;
                    default:
                        throw new java.lang.IllegalStateException("Malformed Parcel reading Boolean: " + parcel);
                }
            }
        }

        public static class ForPattern implements com.android.internal.util.Parcelling<java.util.regex.Pattern> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.util.regex.Pattern pattern, android.os.Parcel parcel, int i) {
                parcel.writeString(pattern == null ? null : pattern.pattern());
            }

            @Override // com.android.internal.util.Parcelling
            public java.util.regex.Pattern unparcel(android.os.Parcel parcel) {
                java.lang.String readString = parcel.readString();
                if (readString == null) {
                    return null;
                }
                return java.util.regex.Pattern.compile(readString);
            }
        }

        public static class ForUUID implements com.android.internal.util.Parcelling<java.util.UUID> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.util.UUID uuid, android.os.Parcel parcel, int i) {
                parcel.writeString(uuid == null ? null : uuid.toString());
            }

            @Override // com.android.internal.util.Parcelling
            public java.util.UUID unparcel(android.os.Parcel parcel) {
                java.lang.String readString = parcel.readString();
                if (readString == null) {
                    return null;
                }
                return java.util.UUID.fromString(readString);
            }
        }

        public static class ForInstant implements com.android.internal.util.Parcelling<java.time.Instant> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(java.time.Instant instant, android.os.Parcel parcel, int i) {
                parcel.writeLong(instant == null ? Long.MIN_VALUE : instant.getEpochSecond());
                parcel.writeInt(instant == null ? Integer.MIN_VALUE : instant.getNano());
            }

            @Override // com.android.internal.util.Parcelling
            public java.time.Instant unparcel(android.os.Parcel parcel) {
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                if (readLong == Long.MIN_VALUE) {
                    return null;
                }
                return java.time.Instant.ofEpochSecond(readLong, readInt);
            }
        }
    }
}
