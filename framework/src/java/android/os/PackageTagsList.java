package android.os;

/* loaded from: classes3.dex */
public final class PackageTagsList implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.PackageTagsList> CREATOR = new android.os.Parcelable.Creator<android.os.PackageTagsList>() { // from class: android.os.PackageTagsList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PackageTagsList createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(readInt);
            int i = 0;
            while (true) {
                if (i < readInt) {
                    arrayMap.append(parcel.readString8(), parcel.readArraySet(null));
                    i++;
                } else {
                    return new android.os.PackageTagsList(arrayMap);
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PackageTagsList[] newArray(int i) {
            return new android.os.PackageTagsList[i];
        }
    };
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mPackageTags;

    private PackageTagsList(android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> arrayMap) {
        this.mPackageTags = (android.util.ArrayMap) java.util.Objects.requireNonNull(arrayMap);
    }

    public boolean isEmpty() {
        return this.mPackageTags.isEmpty();
    }

    public boolean includes(java.lang.String str) {
        return this.mPackageTags.containsKey(str);
    }

    public boolean includesTag(java.lang.String str) {
        int size = this.mPackageTags.size();
        for (int i = 0; i < size; i++) {
            if (this.mPackageTags.valueAt(i).contains(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(java.lang.String str) {
        android.util.ArraySet<java.lang.String> arraySet = this.mPackageTags.get(str);
        return arraySet != null && arraySet.isEmpty();
    }

    public boolean contains(java.lang.String str, java.lang.String str2) {
        android.util.ArraySet<java.lang.String> arraySet = this.mPackageTags.get(str);
        if (arraySet == null) {
            return false;
        }
        if (arraySet.isEmpty()) {
            return true;
        }
        return arraySet.contains(str2);
    }

    public boolean contains(android.os.PackageTagsList packageTagsList) {
        int size = packageTagsList.mPackageTags.size();
        if (size > this.mPackageTags.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            android.util.ArraySet<java.lang.String> arraySet = this.mPackageTags.get(packageTagsList.mPackageTags.keyAt(i));
            if (arraySet == null) {
                return false;
            }
            if (!arraySet.isEmpty()) {
                android.util.ArraySet<java.lang.String> valueAt = packageTagsList.mPackageTags.valueAt(i);
                if (valueAt.isEmpty() || !arraySet.containsAll(valueAt)) {
                    return false;
                }
            }
        }
        return true;
    }

    @java.lang.Deprecated
    public java.util.Collection<java.lang.String> getPackages() {
        return new java.util.ArrayList(this.mPackageTags.keySet());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int size = this.mPackageTags.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString8(this.mPackageTags.keyAt(i2));
            parcel.writeArraySet(this.mPackageTags.valueAt(i2));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.os.PackageTagsList)) {
            return false;
        }
        return this.mPackageTags.equals(((android.os.PackageTagsList) obj).mPackageTags);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPackageTags);
    }

    public java.lang.String toString() {
        return this.mPackageTags.toString();
    }

    public void dump(java.io.PrintWriter printWriter) {
        int size = this.mPackageTags.size();
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = this.mPackageTags.keyAt(i);
            printWriter.print(keyAt);
            printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            int size2 = this.mPackageTags.valueAt(i).size();
            if (size2 == 0) {
                printWriter.print("*");
            } else {
                for (int i2 = 0; i2 < size2; i2++) {
                    java.lang.String valueAt = this.mPackageTags.valueAt(i).valueAt(i2);
                    if (i2 > 0) {
                        printWriter.print(", ");
                    }
                    if (valueAt != null && valueAt.startsWith(keyAt)) {
                        printWriter.print(valueAt.substring(keyAt.length()));
                    } else {
                        printWriter.print(valueAt);
                    }
                }
            }
            printWriter.println(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
    }

    public static final class Builder {
        private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mPackageTags;

        public Builder() {
            this.mPackageTags = new android.util.ArrayMap<>();
        }

        public Builder(int i) {
            this.mPackageTags = new android.util.ArrayMap<>(i);
        }

        static /* synthetic */ android.util.ArraySet lambda$add$0(java.lang.String str) {
            return new android.util.ArraySet();
        }

        public android.os.PackageTagsList.Builder add(java.lang.String str) {
            this.mPackageTags.computeIfAbsent(str, new java.util.function.Function() { // from class: android.os.PackageTagsList$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.os.PackageTagsList.Builder.lambda$add$0((java.lang.String) obj);
                }
            }).clear();
            return this;
        }

        public android.os.PackageTagsList.Builder add(java.lang.String str, java.lang.String str2) {
            android.util.ArraySet<java.lang.String> arraySet = this.mPackageTags.get(str);
            if (arraySet == null) {
                android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>(1);
                arraySet2.add(str2);
                this.mPackageTags.put(str, arraySet2);
            } else if (!arraySet.isEmpty()) {
                arraySet.add(str2);
            }
            return this;
        }

        public android.os.PackageTagsList.Builder add(java.lang.String str, java.util.Collection<java.lang.String> collection) {
            if (collection.isEmpty()) {
                return this;
            }
            android.util.ArraySet<java.lang.String> arraySet = this.mPackageTags.get(str);
            if (arraySet == null) {
                this.mPackageTags.put(str, new android.util.ArraySet<>(collection));
            } else if (!arraySet.isEmpty()) {
                arraySet.addAll(collection);
            }
            return this;
        }

        public android.os.PackageTagsList.Builder add(android.os.PackageTagsList packageTagsList) {
            return add(packageTagsList.mPackageTags);
        }

        public android.os.PackageTagsList.Builder add(java.util.Map<java.lang.String, ? extends java.util.Set<java.lang.String>> map) {
            this.mPackageTags.ensureCapacity(map.size());
            for (java.util.Map.Entry<java.lang.String, ? extends java.util.Set<java.lang.String>> entry : map.entrySet()) {
                java.util.Set<java.lang.String> value = entry.getValue();
                if (value.isEmpty()) {
                    add(entry.getKey());
                } else {
                    add(entry.getKey(), value);
                }
            }
            return this;
        }

        public android.os.PackageTagsList.Builder remove(java.lang.String str) {
            this.mPackageTags.remove(str);
            return this;
        }

        public android.os.PackageTagsList.Builder remove(java.lang.String str, java.lang.String str2) {
            android.util.ArraySet<java.lang.String> arraySet = this.mPackageTags.get(str);
            if (arraySet != null && arraySet.remove(str2) && arraySet.isEmpty()) {
                this.mPackageTags.remove(str);
            }
            return this;
        }

        public android.os.PackageTagsList.Builder remove(java.lang.String str, java.util.Collection<java.lang.String> collection) {
            if (collection.isEmpty()) {
                return this;
            }
            android.util.ArraySet<java.lang.String> arraySet = this.mPackageTags.get(str);
            if (arraySet != null && arraySet.removeAll(collection) && arraySet.isEmpty()) {
                this.mPackageTags.remove(str);
            }
            return this;
        }

        public android.os.PackageTagsList.Builder remove(android.os.PackageTagsList packageTagsList) {
            return remove(packageTagsList.mPackageTags);
        }

        public android.os.PackageTagsList.Builder remove(java.util.Map<java.lang.String, ? extends java.util.Set<java.lang.String>> map) {
            for (java.util.Map.Entry<java.lang.String, ? extends java.util.Set<java.lang.String>> entry : map.entrySet()) {
                java.util.Set<java.lang.String> value = entry.getValue();
                if (value.isEmpty()) {
                    remove(entry.getKey());
                } else {
                    remove(entry.getKey(), value);
                }
            }
            return this;
        }

        public android.os.PackageTagsList.Builder clear() {
            this.mPackageTags.clear();
            return this;
        }

        public android.os.PackageTagsList build() {
            return new android.os.PackageTagsList(copy(this.mPackageTags));
        }

        private static android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> copy(android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> arrayMap) {
            int size = arrayMap.size();
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> arrayMap2 = new android.util.ArrayMap<>(size);
            for (int i = 0; i < size; i++) {
                arrayMap2.append(arrayMap.keyAt(i), new android.util.ArraySet<>((android.util.ArraySet) java.util.Objects.requireNonNull(arrayMap.valueAt(i))));
            }
            return arrayMap2;
        }
    }
}
