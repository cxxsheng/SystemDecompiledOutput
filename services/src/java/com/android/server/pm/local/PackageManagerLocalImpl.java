package com.android.server.pm.local;

/* loaded from: classes2.dex */
public class PackageManagerLocalImpl implements com.android.server.pm.PackageManagerLocal {
    private final com.android.server.pm.PackageManagerService mService;

    public PackageManagerLocalImpl(com.android.server.pm.PackageManagerService packageManagerService) {
        this.mService = packageManagerService;
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public void reconcileSdkData(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<java.lang.String> list, int i, int i2, int i3, @android.annotation.NonNull java.lang.String str3, int i4) throws java.io.IOException {
        this.mService.reconcileSdkData(str, str2, list, i, i2, i3, str3, i4);
    }

    @Override // com.android.server.pm.PackageManagerLocal
    @android.annotation.NonNull
    public com.android.server.pm.local.PackageManagerLocalImpl.UnfilteredSnapshotImpl withUnfilteredSnapshot() {
        return new com.android.server.pm.local.PackageManagerLocalImpl.UnfilteredSnapshotImpl(this.mService.snapshotComputer(false));
    }

    @Override // com.android.server.pm.PackageManagerLocal
    @android.annotation.NonNull
    public com.android.server.pm.local.PackageManagerLocalImpl.FilteredSnapshotImpl withFilteredSnapshot() {
        return withFilteredSnapshot(android.os.Binder.getCallingUid(), android.os.Binder.getCallingUserHandle());
    }

    @Override // com.android.server.pm.PackageManagerLocal
    @android.annotation.NonNull
    public com.android.server.pm.local.PackageManagerLocalImpl.FilteredSnapshotImpl withFilteredSnapshot(int i, @android.annotation.NonNull android.os.UserHandle userHandle) {
        return new com.android.server.pm.local.PackageManagerLocalImpl.FilteredSnapshotImpl(i, userHandle, this.mService.snapshotComputer(false), null);
    }

    private static abstract class BaseSnapshotImpl implements java.lang.AutoCloseable {
        private boolean mClosed;

        @android.annotation.NonNull
        protected com.android.server.pm.Computer mSnapshot;

        private BaseSnapshotImpl(@android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot) {
            this.mSnapshot = (com.android.server.pm.Computer) packageDataSnapshot;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mClosed = true;
            this.mSnapshot = null;
        }

        protected void checkClosed() {
            if (this.mClosed) {
                throw new java.lang.IllegalStateException("Snapshot already closed");
            }
        }
    }

    private static class UnfilteredSnapshotImpl extends com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl implements com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot {

        @android.annotation.Nullable
        private java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> mCachedUnmodifiableDisabledSystemPackageStates;

        @android.annotation.Nullable
        private java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> mCachedUnmodifiablePackageStates;

        @android.annotation.Nullable
        private java.util.Map<java.lang.String, com.android.server.pm.pkg.SharedUserApi> mCachedUnmodifiableSharedUsers;

        private UnfilteredSnapshotImpl(@android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot) {
            super(packageDataSnapshot);
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public com.android.server.pm.PackageManagerLocal.FilteredSnapshot filtered(int i, @android.annotation.NonNull android.os.UserHandle userHandle) {
            return new com.android.server.pm.local.PackageManagerLocalImpl.FilteredSnapshotImpl(i, userHandle, this.mSnapshot, this);
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        @android.annotation.NonNull
        public java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getPackageStates() {
            checkClosed();
            if (this.mCachedUnmodifiablePackageStates == null) {
                this.mCachedUnmodifiablePackageStates = java.util.Collections.unmodifiableMap(this.mSnapshot.getPackageStates());
            }
            return this.mCachedUnmodifiablePackageStates;
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        @android.annotation.NonNull
        public java.util.Map<java.lang.String, com.android.server.pm.pkg.SharedUserApi> getSharedUsers() {
            checkClosed();
            if (this.mCachedUnmodifiableSharedUsers == null) {
                this.mCachedUnmodifiableSharedUsers = java.util.Collections.unmodifiableMap(this.mSnapshot.getSharedUsers());
            }
            return this.mCachedUnmodifiableSharedUsers;
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        @android.annotation.NonNull
        public java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getDisabledSystemPackageStates() {
            checkClosed();
            if (this.mCachedUnmodifiableDisabledSystemPackageStates == null) {
                this.mCachedUnmodifiableDisabledSystemPackageStates = java.util.Collections.unmodifiableMap(this.mSnapshot.getDisabledSystemPackageStates());
            }
            return this.mCachedUnmodifiableDisabledSystemPackageStates;
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl, java.lang.AutoCloseable
        public void close() {
            super.close();
            this.mCachedUnmodifiablePackageStates = null;
            this.mCachedUnmodifiableDisabledSystemPackageStates = null;
        }
    }

    private static class FilteredSnapshotImpl extends com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl implements com.android.server.pm.PackageManagerLocal.FilteredSnapshot {
        private final int mCallingUid;

        @android.annotation.Nullable
        private java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> mFilteredPackageStates;

        @android.annotation.Nullable
        private final com.android.server.pm.local.PackageManagerLocalImpl.UnfilteredSnapshotImpl mParentSnapshot;
        private final int mUserId;

        private FilteredSnapshotImpl(int i, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, @android.annotation.Nullable com.android.server.pm.local.PackageManagerLocalImpl.UnfilteredSnapshotImpl unfilteredSnapshotImpl) {
            super(packageDataSnapshot);
            this.mCallingUid = i;
            this.mUserId = userHandle.getIdentifier();
            this.mParentSnapshot = unfilteredSnapshotImpl;
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl
        protected void checkClosed() {
            if (this.mParentSnapshot != null) {
                this.mParentSnapshot.checkClosed();
            }
            super.checkClosed();
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl, java.lang.AutoCloseable
        public void close() {
            super.close();
            this.mFilteredPackageStates = null;
        }

        @Override // com.android.server.pm.PackageManagerLocal.FilteredSnapshot
        @android.annotation.Nullable
        public com.android.server.pm.pkg.PackageState getPackageState(@android.annotation.NonNull java.lang.String str) {
            checkClosed();
            return this.mSnapshot.getPackageStateFiltered(str, this.mCallingUid, this.mUserId);
        }

        @Override // com.android.server.pm.PackageManagerLocal.FilteredSnapshot
        @android.annotation.NonNull
        public java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getPackageStates() {
            checkClosed();
            if (this.mFilteredPackageStates == null) {
                android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = this.mSnapshot.getPackageStates();
                android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                int size = packageStates.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i);
                    if (!this.mSnapshot.shouldFilterApplication(valueAt, this.mCallingUid, this.mUserId)) {
                        arrayMap.put(packageStates.keyAt(i), valueAt);
                    }
                }
                this.mFilteredPackageStates = java.util.Collections.unmodifiableMap(arrayMap);
            }
            return this.mFilteredPackageStates;
        }
    }
}
