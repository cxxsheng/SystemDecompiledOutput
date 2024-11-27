package android.view.textclassifier;

/* loaded from: classes4.dex */
final class EntityConfidence implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.EntityConfidence> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.EntityConfidence>() { // from class: android.view.textclassifier.EntityConfidence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.EntityConfidence createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.EntityConfidence(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.EntityConfidence[] newArray(int i) {
            return new android.view.textclassifier.EntityConfidence[i];
        }
    };
    private final android.util.ArrayMap<java.lang.String, java.lang.Float> mEntityConfidence;
    private final java.util.ArrayList<java.lang.String> mSortedEntities;

    EntityConfidence() {
        this.mEntityConfidence = new android.util.ArrayMap<>();
        this.mSortedEntities = new java.util.ArrayList<>();
    }

    EntityConfidence(android.view.textclassifier.EntityConfidence entityConfidence) {
        this.mEntityConfidence = new android.util.ArrayMap<>();
        this.mSortedEntities = new java.util.ArrayList<>();
        java.util.Objects.requireNonNull(entityConfidence);
        this.mEntityConfidence.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Float>) entityConfidence.mEntityConfidence);
        this.mSortedEntities.addAll(entityConfidence.mSortedEntities);
    }

    EntityConfidence(java.util.Map<java.lang.String, java.lang.Float> map) {
        this.mEntityConfidence = new android.util.ArrayMap<>();
        this.mSortedEntities = new java.util.ArrayList<>();
        java.util.Objects.requireNonNull(map);
        this.mEntityConfidence.ensureCapacity(map.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.Float> entry : map.entrySet()) {
            if (entry.getValue().floatValue() > 0.0f) {
                this.mEntityConfidence.put(entry.getKey(), java.lang.Float.valueOf(java.lang.Math.min(1.0f, entry.getValue().floatValue())));
            }
        }
        resetSortedEntitiesFromMap();
    }

    public java.util.List<java.lang.String> getEntities() {
        return java.util.Collections.unmodifiableList(this.mSortedEntities);
    }

    public float getConfidenceScore(java.lang.String str) {
        if (this.mEntityConfidence.containsKey(str)) {
            return this.mEntityConfidence.get(str).floatValue();
        }
        return 0.0f;
    }

    public java.util.Map<java.lang.String, java.lang.Float> toMap() {
        return new android.util.ArrayMap(this.mEntityConfidence);
    }

    public java.lang.String toString() {
        return this.mEntityConfidence.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mEntityConfidence.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.Float> entry : this.mEntityConfidence.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeFloat(entry.getValue().floatValue());
        }
    }

    private EntityConfidence(android.os.Parcel parcel) {
        this.mEntityConfidence = new android.util.ArrayMap<>();
        this.mSortedEntities = new java.util.ArrayList<>();
        int readInt = parcel.readInt();
        this.mEntityConfidence.ensureCapacity(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mEntityConfidence.put(parcel.readString(), java.lang.Float.valueOf(parcel.readFloat()));
        }
        resetSortedEntitiesFromMap();
    }

    private void resetSortedEntitiesFromMap() {
        this.mSortedEntities.clear();
        this.mSortedEntities.ensureCapacity(this.mEntityConfidence.size());
        this.mSortedEntities.addAll(this.mEntityConfidence.keySet());
        this.mSortedEntities.sort(new java.util.Comparator() { // from class: android.view.textclassifier.EntityConfidence$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$resetSortedEntitiesFromMap$0;
                lambda$resetSortedEntitiesFromMap$0 = android.view.textclassifier.EntityConfidence.this.lambda$resetSortedEntitiesFromMap$0((java.lang.String) obj, (java.lang.String) obj2);
                return lambda$resetSortedEntitiesFromMap$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$resetSortedEntitiesFromMap$0(java.lang.String str, java.lang.String str2) {
        return java.lang.Float.compare(this.mEntityConfidence.get(str2).floatValue(), this.mEntityConfidence.get(str).floatValue());
    }
}
