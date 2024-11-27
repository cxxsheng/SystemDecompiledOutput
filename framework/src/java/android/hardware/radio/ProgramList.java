package android.hardware.radio;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class ProgramList implements java.lang.AutoCloseable {
    private boolean mIsClosed;
    private boolean mIsComplete;
    private android.hardware.radio.ProgramList.OnCloseListener mOnCloseListener;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<android.hardware.radio.ProgramSelector.Identifier, android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo>> mPrograms = new android.util.ArrayMap<>();
    private final java.util.List<android.hardware.radio.ProgramList.ListCallback> mListCallbacks = new java.util.ArrayList();
    private final java.util.List<android.hardware.radio.ProgramList.OnCompleteListener> mOnCompleteListeners = new java.util.ArrayList();

    interface OnCloseListener {
        void onClose();
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    ProgramList() {
    }

    public static abstract class ListCallback {
        public void onItemChanged(android.hardware.radio.ProgramSelector.Identifier identifier) {
        }

        public void onItemRemoved(android.hardware.radio.ProgramSelector.Identifier identifier) {
        }
    }

    /* renamed from: android.hardware.radio.ProgramList$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.radio.ProgramList.ListCallback {
        final /* synthetic */ android.hardware.radio.ProgramList.ListCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass1(java.util.concurrent.Executor executor, android.hardware.radio.ProgramList.ListCallback listCallback) {
            this.val$executor = executor;
            this.val$callback = listCallback;
        }

        @Override // android.hardware.radio.ProgramList.ListCallback
        public void onItemChanged(final android.hardware.radio.ProgramSelector.Identifier identifier) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.radio.ProgramList.ListCallback listCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.radio.ProgramList$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.radio.ProgramList.ListCallback.this.onItemChanged(identifier);
                }
            });
        }

        @Override // android.hardware.radio.ProgramList.ListCallback
        public void onItemRemoved(final android.hardware.radio.ProgramSelector.Identifier identifier) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.radio.ProgramList.ListCallback listCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.radio.ProgramList$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.radio.ProgramList.ListCallback.this.onItemRemoved(identifier);
                }
            });
        }
    }

    public void registerListCallback(java.util.concurrent.Executor executor, android.hardware.radio.ProgramList.ListCallback listCallback) {
        registerListCallback(new android.hardware.radio.ProgramList.AnonymousClass1(executor, listCallback));
    }

    public void registerListCallback(android.hardware.radio.ProgramList.ListCallback listCallback) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.add((android.hardware.radio.ProgramList.ListCallback) java.util.Objects.requireNonNull(listCallback));
        }
    }

    public void unregisterListCallback(android.hardware.radio.ProgramList.ListCallback listCallback) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.remove(java.util.Objects.requireNonNull(listCallback));
        }
    }

    static /* synthetic */ void lambda$addOnCompleteListener$0(java.util.concurrent.Executor executor, final android.hardware.radio.ProgramList.OnCompleteListener onCompleteListener) {
        java.util.Objects.requireNonNull(onCompleteListener);
        executor.execute(new java.lang.Runnable() { // from class: android.hardware.radio.ProgramList$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.ProgramList.OnCompleteListener.this.onComplete();
            }
        });
    }

    public void addOnCompleteListener(final java.util.concurrent.Executor executor, final android.hardware.radio.ProgramList.OnCompleteListener onCompleteListener) {
        addOnCompleteListener(new android.hardware.radio.ProgramList.OnCompleteListener() { // from class: android.hardware.radio.ProgramList$$ExternalSyntheticLambda1
            @Override // android.hardware.radio.ProgramList.OnCompleteListener
            public final void onComplete() {
                android.hardware.radio.ProgramList.lambda$addOnCompleteListener$0(executor, onCompleteListener);
            }
        });
    }

    public void addOnCompleteListener(android.hardware.radio.ProgramList.OnCompleteListener onCompleteListener) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.add((android.hardware.radio.ProgramList.OnCompleteListener) java.util.Objects.requireNonNull(onCompleteListener));
            if (this.mIsComplete) {
                onCompleteListener.onComplete();
            }
        }
    }

    public void removeOnCompleteListener(android.hardware.radio.ProgramList.OnCompleteListener onCompleteListener) {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.remove(java.util.Objects.requireNonNull(onCompleteListener));
        }
    }

    void setOnCloseListener(android.hardware.radio.ProgramList.OnCloseListener onCloseListener) {
        synchronized (this.mLock) {
            if (this.mOnCloseListener != null) {
                throw new java.lang.IllegalStateException("Close callback is already set");
            }
            this.mOnCloseListener = onCloseListener;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsClosed = true;
            this.mPrograms.clear();
            this.mListCallbacks.clear();
            this.mOnCompleteListeners.clear();
            android.hardware.radio.ProgramList.OnCloseListener onCloseListener = null;
            if (this.mOnCloseListener != null) {
                android.hardware.radio.ProgramList.OnCloseListener onCloseListener2 = this.mOnCloseListener;
                this.mOnCloseListener = null;
                onCloseListener = onCloseListener2;
            }
            if (onCloseListener != null) {
                onCloseListener.onClose();
            }
        }
    }

    void apply(android.hardware.radio.ProgramList.Chunk chunk) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsComplete = false;
            java.util.ArrayList arrayList3 = new java.util.ArrayList(this.mListCallbacks);
            if (chunk.isPurge()) {
                java.util.Iterator<java.util.Map.Entry<android.hardware.radio.ProgramSelector.Identifier, android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo>>> it = this.mPrograms.entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry<android.hardware.radio.ProgramSelector.Identifier, android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo>> next = it.next();
                    if (next.getValue() != null) {
                        arrayList.add(next.getKey());
                    }
                    it.remove();
                }
            }
            java.util.Iterator<android.hardware.radio.UniqueProgramIdentifier> it2 = chunk.getRemoved().iterator();
            while (it2.hasNext()) {
                removeLocked(it2.next(), arrayList);
            }
            java.util.Iterator<android.hardware.radio.RadioManager.ProgramInfo> it3 = chunk.getModified().iterator();
            while (it3.hasNext()) {
                putLocked(it3.next(), arraySet);
            }
            if (chunk.isComplete()) {
                this.mIsComplete = true;
                arrayList2 = new java.util.ArrayList(this.mOnCompleteListeners);
            }
            for (int i = 0; i < arrayList.size(); i++) {
                for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                    ((android.hardware.radio.ProgramList.ListCallback) arrayList3.get(i2)).onItemRemoved(arrayList.get(i));
                }
            }
            for (android.hardware.radio.ProgramSelector.Identifier identifier : arraySet) {
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    ((android.hardware.radio.ProgramList.ListCallback) arrayList3.get(i3)).onItemChanged(identifier);
                }
            }
            if (chunk.isComplete()) {
                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                    ((android.hardware.radio.ProgramList.OnCompleteListener) arrayList2.get(i4)).onComplete();
                }
            }
        }
    }

    private void putLocked(android.hardware.radio.RadioManager.ProgramInfo programInfo, java.util.Set<android.hardware.radio.ProgramSelector.Identifier> set) {
        android.hardware.radio.UniqueProgramIdentifier uniqueProgramIdentifier = new android.hardware.radio.UniqueProgramIdentifier(programInfo.getSelector());
        android.hardware.radio.ProgramSelector.Identifier identifier = (android.hardware.radio.ProgramSelector.Identifier) java.util.Objects.requireNonNull(uniqueProgramIdentifier.getPrimaryId());
        if (!this.mPrograms.containsKey(identifier)) {
            this.mPrograms.put(identifier, new android.util.ArrayMap<>());
        }
        this.mPrograms.get(identifier).put(uniqueProgramIdentifier, programInfo);
        set.add(identifier);
    }

    private void removeLocked(android.hardware.radio.UniqueProgramIdentifier uniqueProgramIdentifier, java.util.List<android.hardware.radio.ProgramSelector.Identifier> list) {
        android.hardware.radio.ProgramSelector.Identifier identifier = (android.hardware.radio.ProgramSelector.Identifier) java.util.Objects.requireNonNull(uniqueProgramIdentifier.getPrimaryId());
        if (!this.mPrograms.containsKey(identifier)) {
            return;
        }
        android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo> arrayMap = this.mPrograms.get(identifier);
        if (arrayMap.remove(java.util.Objects.requireNonNull(uniqueProgramIdentifier)) != null && arrayMap.size() == 0) {
            list.add(identifier);
        }
    }

    public java.util.List<android.hardware.radio.RadioManager.ProgramInfo> toList() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mPrograms.size(); i++) {
                arrayList.addAll(this.mPrograms.valueAt(i).values());
            }
        }
        return arrayList;
    }

    public android.hardware.radio.RadioManager.ProgramInfo get(android.hardware.radio.ProgramSelector.Identifier identifier) {
        android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo> arrayMap;
        synchronized (this.mLock) {
            arrayMap = this.mPrograms.get(java.util.Objects.requireNonNull(identifier, "Primary identifier can not be null"));
        }
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.entrySet().iterator().next().getValue();
    }

    public java.util.List<android.hardware.radio.RadioManager.ProgramInfo> getProgramInfos(android.hardware.radio.ProgramSelector.Identifier identifier) {
        android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo> arrayMap;
        java.util.Objects.requireNonNull(identifier, "Primary identifier can not be null");
        synchronized (this.mLock) {
            arrayMap = this.mPrograms.get(identifier);
        }
        if (arrayMap == null) {
            return new java.util.ArrayList();
        }
        return new java.util.ArrayList(arrayMap.values());
    }

    public static final class Filter implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.ProgramList.Filter> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.ProgramList.Filter>() { // from class: android.hardware.radio.ProgramList.Filter.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.ProgramList.Filter createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.ProgramList.Filter(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.ProgramList.Filter[] newArray(int i) {
                return new android.hardware.radio.ProgramList.Filter[i];
            }
        };
        private final boolean mExcludeModifications;
        private final java.util.Set<java.lang.Integer> mIdentifierTypes;
        private final java.util.Set<android.hardware.radio.ProgramSelector.Identifier> mIdentifiers;
        private final boolean mIncludeCategories;
        private final java.util.Map<java.lang.String, java.lang.String> mVendorFilter;

        public Filter(java.util.Set<java.lang.Integer> set, java.util.Set<android.hardware.radio.ProgramSelector.Identifier> set2, boolean z, boolean z2) {
            this.mIdentifierTypes = (java.util.Set) java.util.Objects.requireNonNull(set);
            this.mIdentifiers = (java.util.Set) java.util.Objects.requireNonNull(set2);
            this.mIncludeCategories = z;
            this.mExcludeModifications = z2;
            this.mVendorFilter = null;
        }

        public Filter() {
            this.mIdentifierTypes = java.util.Collections.emptySet();
            this.mIdentifiers = java.util.Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = null;
        }

        public Filter(java.util.Map<java.lang.String, java.lang.String> map) {
            this.mIdentifierTypes = java.util.Collections.emptySet();
            this.mIdentifiers = java.util.Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = map;
        }

        private Filter(android.os.Parcel parcel) {
            this.mIdentifierTypes = android.hardware.radio.Utils.createIntSet(parcel);
            this.mIdentifiers = android.hardware.radio.Utils.createSet(parcel, android.hardware.radio.ProgramSelector.Identifier.CREATOR);
            this.mIncludeCategories = parcel.readByte() != 0;
            this.mExcludeModifications = parcel.readByte() != 0;
            this.mVendorFilter = android.hardware.radio.Utils.readStringMap(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            android.hardware.radio.Utils.writeIntSet(parcel, this.mIdentifierTypes);
            android.hardware.radio.Utils.writeSet(parcel, this.mIdentifiers);
            parcel.writeByte(this.mIncludeCategories ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mExcludeModifications ? (byte) 1 : (byte) 0);
            android.hardware.radio.Utils.writeStringMap(parcel, this.mVendorFilter);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.util.Map<java.lang.String, java.lang.String> getVendorFilter() {
            return this.mVendorFilter;
        }

        public java.util.Set<java.lang.Integer> getIdentifierTypes() {
            return this.mIdentifierTypes;
        }

        public java.util.Set<android.hardware.radio.ProgramSelector.Identifier> getIdentifiers() {
            return this.mIdentifiers;
        }

        public boolean areCategoriesIncluded() {
            return this.mIncludeCategories;
        }

        public boolean areModificationsExcluded() {
            return this.mExcludeModifications;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mIdentifierTypes, this.mIdentifiers, java.lang.Boolean.valueOf(this.mIncludeCategories), java.lang.Boolean.valueOf(this.mExcludeModifications));
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.radio.ProgramList.Filter)) {
                return false;
            }
            android.hardware.radio.ProgramList.Filter filter = (android.hardware.radio.ProgramList.Filter) obj;
            return this.mIncludeCategories == filter.mIncludeCategories && this.mExcludeModifications == filter.mExcludeModifications && java.util.Objects.equals(this.mIdentifierTypes, filter.mIdentifierTypes) && java.util.Objects.equals(this.mIdentifiers, filter.mIdentifiers);
        }

        public java.lang.String toString() {
            return "Filter [mIdentifierTypes=" + this.mIdentifierTypes + ", mIdentifiers=" + this.mIdentifiers + ", mIncludeCategories=" + this.mIncludeCategories + ", mExcludeModifications=" + this.mExcludeModifications + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static final class Chunk implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.ProgramList.Chunk> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.ProgramList.Chunk>() { // from class: android.hardware.radio.ProgramList.Chunk.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.ProgramList.Chunk createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.ProgramList.Chunk(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.ProgramList.Chunk[] newArray(int i) {
                return new android.hardware.radio.ProgramList.Chunk[i];
            }
        };
        private final boolean mComplete;
        private final java.util.Set<android.hardware.radio.RadioManager.ProgramInfo> mModified;
        private final boolean mPurge;
        private final java.util.Set<android.hardware.radio.UniqueProgramIdentifier> mRemoved;

        public Chunk(boolean z, boolean z2, java.util.Set<android.hardware.radio.RadioManager.ProgramInfo> set, java.util.Set<android.hardware.radio.UniqueProgramIdentifier> set2) {
            this.mPurge = z;
            this.mComplete = z2;
            this.mModified = set == null ? java.util.Collections.emptySet() : set;
            this.mRemoved = set2 == null ? java.util.Collections.emptySet() : set2;
        }

        private Chunk(android.os.Parcel parcel) {
            this.mPurge = parcel.readByte() != 0;
            this.mComplete = parcel.readByte() != 0;
            this.mModified = android.hardware.radio.Utils.createSet(parcel, android.hardware.radio.RadioManager.ProgramInfo.CREATOR);
            this.mRemoved = android.hardware.radio.Utils.createSet(parcel, android.hardware.radio.UniqueProgramIdentifier.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeByte(this.mPurge ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mComplete ? (byte) 1 : (byte) 0);
            android.hardware.radio.Utils.writeSet(parcel, this.mModified);
            android.hardware.radio.Utils.writeSet(parcel, this.mRemoved);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean isPurge() {
            return this.mPurge;
        }

        public boolean isComplete() {
            return this.mComplete;
        }

        public java.util.Set<android.hardware.radio.RadioManager.ProgramInfo> getModified() {
            return this.mModified;
        }

        public java.util.Set<android.hardware.radio.UniqueProgramIdentifier> getRemoved() {
            return this.mRemoved;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.radio.ProgramList.Chunk)) {
                return false;
            }
            android.hardware.radio.ProgramList.Chunk chunk = (android.hardware.radio.ProgramList.Chunk) obj;
            return this.mPurge == chunk.mPurge && this.mComplete == chunk.mComplete && java.util.Objects.equals(this.mModified, chunk.mModified) && java.util.Objects.equals(this.mRemoved, chunk.mRemoved);
        }

        public java.lang.String toString() {
            return "Chunk [mPurge=" + this.mPurge + ", mComplete=" + this.mComplete + ", mModified=" + this.mModified + ", mRemoved=" + this.mRemoved + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }
}
