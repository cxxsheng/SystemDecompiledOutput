package android.view;

/* loaded from: classes4.dex */
public class RemoteAnimationDefinition implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.RemoteAnimationDefinition> CREATOR = new android.os.Parcelable.Creator<android.view.RemoteAnimationDefinition>() { // from class: android.view.RemoteAnimationDefinition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RemoteAnimationDefinition createFromParcel(android.os.Parcel parcel) {
            return new android.view.RemoteAnimationDefinition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RemoteAnimationDefinition[] newArray(int i) {
            return new android.view.RemoteAnimationDefinition[i];
        }
    };
    private final android.util.SparseArray<android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry> mTransitionAnimationMap;

    public RemoteAnimationDefinition() {
        this.mTransitionAnimationMap = new android.util.SparseArray<>();
    }

    public void addRemoteAnimation(int i, int i2, android.view.RemoteAnimationAdapter remoteAnimationAdapter) {
        this.mTransitionAnimationMap.put(i, new android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry(remoteAnimationAdapter, i2));
    }

    public void addRemoteAnimation(int i, android.view.RemoteAnimationAdapter remoteAnimationAdapter) {
        addRemoteAnimation(i, 0, remoteAnimationAdapter);
    }

    public boolean hasTransition(int i, android.util.ArraySet<java.lang.Integer> arraySet) {
        return getAdapter(i, arraySet) != null;
    }

    public android.view.RemoteAnimationAdapter getAdapter(int i, android.util.ArraySet<java.lang.Integer> arraySet) {
        android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry remoteAnimationAdapterEntry = this.mTransitionAnimationMap.get(i);
        if (remoteAnimationAdapterEntry == null) {
            return null;
        }
        if (remoteAnimationAdapterEntry.activityTypeFilter != 0 && !arraySet.contains(java.lang.Integer.valueOf(remoteAnimationAdapterEntry.activityTypeFilter))) {
            return null;
        }
        return remoteAnimationAdapterEntry.adapter;
    }

    public RemoteAnimationDefinition(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mTransitionAnimationMap = new android.util.SparseArray<>(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mTransitionAnimationMap.put(parcel.readInt(), (android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry) parcel.readTypedObject(android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry.CREATOR));
        }
    }

    public void setCallingPidUid(int i, int i2) {
        for (int size = this.mTransitionAnimationMap.size() - 1; size >= 0; size--) {
            this.mTransitionAnimationMap.valueAt(size).adapter.setCallingPidUid(i, i2);
        }
    }

    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        for (int i = 0; i < this.mTransitionAnimationMap.size(); i++) {
            try {
                this.mTransitionAnimationMap.valueAt(i).adapter.getRunner().asBinder().linkToDeath(deathRecipient, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e("RemoteAnimationDefinition", "Failed to link to death recipient");
                return;
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int size = this.mTransitionAnimationMap.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeInt(this.mTransitionAnimationMap.keyAt(i2));
            parcel.writeTypedObject(this.mTransitionAnimationMap.valueAt(i2), i);
        }
    }

    private static class RemoteAnimationAdapterEntry implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry> CREATOR = new android.os.Parcelable.Creator<android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry>() { // from class: android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry createFromParcel(android.os.Parcel parcel) {
                return new android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry[] newArray(int i) {
                return new android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry[i];
            }
        };
        final int activityTypeFilter;
        final android.view.RemoteAnimationAdapter adapter;

        RemoteAnimationAdapterEntry(android.view.RemoteAnimationAdapter remoteAnimationAdapter, int i) {
            this.adapter = remoteAnimationAdapter;
            this.activityTypeFilter = i;
        }

        private RemoteAnimationAdapterEntry(android.os.Parcel parcel) {
            this.adapter = (android.view.RemoteAnimationAdapter) parcel.readTypedObject(android.view.RemoteAnimationAdapter.CREATOR);
            this.activityTypeFilter = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedObject(this.adapter, i);
            parcel.writeInt(this.activityTypeFilter);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
