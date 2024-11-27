package android.app.admin;

/* loaded from: classes.dex */
public final class MostRestrictive<V> extends android.app.admin.ResolutionMechanism<V> {
    public static final android.os.Parcelable.Creator<android.app.admin.MostRestrictive<?>> CREATOR = new android.os.Parcelable.Creator<android.app.admin.MostRestrictive<?>>() { // from class: android.app.admin.MostRestrictive.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.MostRestrictive<?> createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.MostRestrictive<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.MostRestrictive<?>[] newArray(int i) {
            return new android.app.admin.MostRestrictive[i];
        }
    };
    private final java.util.List<android.app.admin.PolicyValue<V>> mMostToLeastRestrictive;

    public MostRestrictive(java.util.List<android.app.admin.PolicyValue<V>> list) {
        this.mMostToLeastRestrictive = new java.util.ArrayList(list);
    }

    public java.util.List<V> getMostToLeastRestrictiveValues() {
        return this.mMostToLeastRestrictive.stream().map(new java.util.function.Function() { // from class: android.app.admin.MostRestrictive$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.app.admin.PolicyValue) obj).getValue();
            }
        }).toList();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        try {
            return java.util.Objects.equals(this.mMostToLeastRestrictive, ((android.app.admin.MostRestrictive) obj).mMostToLeastRestrictive);
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return this.mMostToLeastRestrictive.hashCode();
    }

    public MostRestrictive(android.os.Parcel parcel) {
        this.mMostToLeastRestrictive = new java.util.ArrayList();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mMostToLeastRestrictive.add((android.app.admin.PolicyValue) parcel.readParcelable(android.app.admin.PolicyValue.class.getClassLoader()));
        }
    }

    public java.lang.String toString() {
        return "MostRestrictive { mMostToLeastRestrictive= " + this.mMostToLeastRestrictive + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMostToLeastRestrictive.size());
        java.util.Iterator<android.app.admin.PolicyValue<V>> it = this.mMostToLeastRestrictive.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable(it.next(), i);
        }
    }
}
