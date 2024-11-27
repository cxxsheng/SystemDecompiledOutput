package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class LegacyPermissionState {

    @android.annotation.NonNull
    private final android.util.SparseArray<com.android.server.pm.permission.LegacyPermissionState.UserState> mUserStates = new android.util.SparseArray<>();

    @android.annotation.NonNull
    private final android.util.SparseBooleanArray mMissing = new android.util.SparseBooleanArray();

    public void copyFrom(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionState legacyPermissionState) {
        if (legacyPermissionState == this) {
            return;
        }
        this.mUserStates.clear();
        int size = legacyPermissionState.mUserStates.size();
        for (int i = 0; i < size; i++) {
            this.mUserStates.put(legacyPermissionState.mUserStates.keyAt(i), new com.android.server.pm.permission.LegacyPermissionState.UserState(legacyPermissionState.mUserStates.valueAt(i)));
        }
        this.mMissing.clear();
        int size2 = legacyPermissionState.mMissing.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mMissing.put(legacyPermissionState.mMissing.keyAt(i2), legacyPermissionState.mMissing.valueAt(i2));
        }
    }

    public void reset() {
        this.mUserStates.clear();
        this.mMissing.clear();
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.pm.permission.LegacyPermissionState.class != obj.getClass()) {
            return false;
        }
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState = (com.android.server.pm.permission.LegacyPermissionState) obj;
        int size = this.mUserStates.size();
        if (size != legacyPermissionState.mUserStates.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            int keyAt = this.mUserStates.keyAt(i);
            if (!java.util.Objects.equals(this.mUserStates.get(keyAt), legacyPermissionState.mUserStates.get(keyAt))) {
                return false;
            }
        }
        return java.util.Objects.equals(this.mMissing, legacyPermissionState.mMissing);
    }

    @android.annotation.Nullable
    public com.android.server.pm.permission.LegacyPermissionState.PermissionState getPermissionState(@android.annotation.NonNull java.lang.String str, int i) {
        checkUserId(i);
        com.android.server.pm.permission.LegacyPermissionState.UserState userState = this.mUserStates.get(i);
        if (userState == null) {
            return null;
        }
        return userState.getPermissionState(str);
    }

    public void putPermissionState(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState, int i) {
        checkUserId(i);
        com.android.server.pm.permission.LegacyPermissionState.UserState userState = this.mUserStates.get(i);
        if (userState == null) {
            userState = new com.android.server.pm.permission.LegacyPermissionState.UserState();
            this.mUserStates.put(i, userState);
        }
        userState.putPermissionState(permissionState);
    }

    public boolean hasPermissionState(@android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        int size = this.mUserStates.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.permission.LegacyPermissionState.UserState valueAt = this.mUserStates.valueAt(i);
            java.util.Iterator<java.lang.String> it = collection.iterator();
            while (it.hasNext()) {
                if (valueAt.getPermissionState(it.next()) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @android.annotation.NonNull
    public java.util.Collection<com.android.server.pm.permission.LegacyPermissionState.PermissionState> getPermissionStates(int i) {
        checkUserId(i);
        com.android.server.pm.permission.LegacyPermissionState.UserState userState = this.mUserStates.get(i);
        if (userState == null) {
            return java.util.Collections.emptyList();
        }
        return userState.getPermissionStates();
    }

    public boolean isMissing(int i) {
        checkUserId(i);
        return this.mMissing.get(i);
    }

    public void setMissing(boolean z, int i) {
        checkUserId(i);
        if (z) {
            this.mMissing.put(i, true);
        } else {
            this.mMissing.delete(i);
        }
    }

    private static void checkUserId(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Invalid user ID " + i);
        }
    }

    private static final class UserState {

        @android.annotation.NonNull
        private final android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.LegacyPermissionState.PermissionState> mPermissionStates = new android.util.ArrayMap<>();

        public UserState() {
        }

        public UserState(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionState.UserState userState) {
            int size = userState.mPermissionStates.size();
            for (int i = 0; i < size; i++) {
                this.mPermissionStates.put(userState.mPermissionStates.keyAt(i), new com.android.server.pm.permission.LegacyPermissionState.PermissionState(userState.mPermissionStates.valueAt(i)));
            }
        }

        @android.annotation.Nullable
        public com.android.server.pm.permission.LegacyPermissionState.PermissionState getPermissionState(@android.annotation.NonNull java.lang.String str) {
            return this.mPermissionStates.get(str);
        }

        public void putPermissionState(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState) {
            this.mPermissionStates.put(permissionState.getName(), permissionState);
        }

        @android.annotation.NonNull
        public java.util.Collection<com.android.server.pm.permission.LegacyPermissionState.PermissionState> getPermissionStates() {
            return java.util.Collections.unmodifiableCollection(this.mPermissionStates.values());
        }
    }

    public static final class PermissionState {
        private final int mFlags;
        private final boolean mGranted;

        @android.annotation.NonNull
        private final java.lang.String mName;
        private final boolean mRuntime;

        public PermissionState(@android.annotation.NonNull java.lang.String str, boolean z, boolean z2, int i) {
            this.mName = str;
            this.mRuntime = z;
            this.mGranted = z2;
            this.mFlags = i;
        }

        private PermissionState(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState) {
            this.mName = permissionState.mName;
            this.mRuntime = permissionState.mRuntime;
            this.mGranted = permissionState.mGranted;
            this.mFlags = permissionState.mFlags;
        }

        @android.annotation.NonNull
        public java.lang.String getName() {
            return this.mName;
        }

        public boolean isRuntime() {
            return this.mRuntime;
        }

        @android.annotation.NonNull
        public boolean isGranted() {
            return this.mGranted;
        }

        @android.annotation.NonNull
        public int getFlags() {
            return this.mFlags;
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.pm.permission.LegacyPermissionState.PermissionState.class != obj.getClass()) {
                return false;
            }
            com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState = (com.android.server.pm.permission.LegacyPermissionState.PermissionState) obj;
            if (this.mRuntime == permissionState.mRuntime && this.mGranted == permissionState.mGranted && this.mFlags == permissionState.mFlags && java.util.Objects.equals(this.mName, permissionState.mName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mName, java.lang.Boolean.valueOf(this.mRuntime), java.lang.Boolean.valueOf(this.mGranted), java.lang.Integer.valueOf(this.mFlags));
        }
    }
}
