package android.app.admin;

/* loaded from: classes.dex */
public final class TopPriority<V> extends android.app.admin.ResolutionMechanism<V> {
    public static final android.os.Parcelable.Creator<android.app.admin.TopPriority<?>> CREATOR = new android.os.Parcelable.Creator<android.app.admin.TopPriority<?>>() { // from class: android.app.admin.TopPriority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.TopPriority<?> createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.TopPriority<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.TopPriority<?>[] newArray(int i) {
            return new android.app.admin.TopPriority[i];
        }
    };
    private final java.util.List<android.app.admin.Authority> mHighestToLowestPriorityAuthorities;

    public TopPriority(java.util.List<android.app.admin.Authority> list) {
        this.mHighestToLowestPriorityAuthorities = (java.util.List) java.util.Objects.requireNonNull(list);
    }

    private TopPriority(android.os.Parcel parcel) {
        this.mHighestToLowestPriorityAuthorities = new java.util.ArrayList();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mHighestToLowestPriorityAuthorities.add((android.app.admin.Authority) parcel.readParcelable(android.app.admin.Authority.class.getClassLoader()));
        }
    }

    public java.util.List<android.app.admin.Authority> getHighestToLowestPriorityAuthorities() {
        return this.mHighestToLowestPriorityAuthorities;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        try {
            return java.util.Objects.equals(this.mHighestToLowestPriorityAuthorities, ((android.app.admin.TopPriority) obj).mHighestToLowestPriorityAuthorities);
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return this.mHighestToLowestPriorityAuthorities.hashCode();
    }

    public java.lang.String toString() {
        return "TopPriority { mHighestToLowestPriorityAuthorities= " + this.mHighestToLowestPriorityAuthorities + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHighestToLowestPriorityAuthorities.size());
        java.util.Iterator<android.app.admin.Authority> it = this.mHighestToLowestPriorityAuthorities.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable(it.next(), i);
        }
    }
}
