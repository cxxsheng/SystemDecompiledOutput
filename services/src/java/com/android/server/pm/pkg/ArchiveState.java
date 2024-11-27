package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public class ArchiveState {

    @android.annotation.NonNull
    private final java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> mActivityInfos;
    private final long mArchiveTimeMillis;

    @android.annotation.NonNull
    private final java.lang.String mInstallerTitle;

    public ArchiveState(@android.annotation.NonNull java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> list, @android.annotation.NonNull java.lang.String str) {
        this(list, str, java.lang.System.currentTimeMillis());
    }

    public static class ArchiveActivityInfo {

        @android.annotation.Nullable
        private final java.nio.file.Path mIconBitmap;

        @android.annotation.Nullable
        private final java.nio.file.Path mMonochromeIconBitmap;

        @android.annotation.NonNull
        private final android.content.ComponentName mOriginalComponentName;

        @android.annotation.NonNull
        private final java.lang.String mTitle;

        public ArchiveActivityInfo(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.Nullable java.nio.file.Path path, @android.annotation.Nullable java.nio.file.Path path2) {
            this.mTitle = str;
            com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mTitle);
            this.mOriginalComponentName = componentName;
            com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mOriginalComponentName);
            this.mIconBitmap = path;
            this.mMonochromeIconBitmap = path2;
        }

        @android.annotation.NonNull
        public java.lang.String getTitle() {
            return this.mTitle;
        }

        @android.annotation.NonNull
        public android.content.ComponentName getOriginalComponentName() {
            return this.mOriginalComponentName;
        }

        @android.annotation.Nullable
        public java.nio.file.Path getIconBitmap() {
            return this.mIconBitmap;
        }

        @android.annotation.Nullable
        public java.nio.file.Path getMonochromeIconBitmap() {
            return this.mMonochromeIconBitmap;
        }

        public java.lang.String toString() {
            return "ArchiveActivityInfo { title = " + this.mTitle + ", originalComponentName = " + this.mOriginalComponentName + ", iconBitmap = " + this.mIconBitmap + ", monochromeIconBitmap = " + this.mMonochromeIconBitmap + " }";
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo archiveActivityInfo = (com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo) obj;
            if (java.util.Objects.equals(this.mTitle, archiveActivityInfo.mTitle) && java.util.Objects.equals(this.mOriginalComponentName, archiveActivityInfo.mOriginalComponentName) && java.util.Objects.equals(this.mIconBitmap, archiveActivityInfo.mIconBitmap) && java.util.Objects.equals(this.mMonochromeIconBitmap, archiveActivityInfo.mMonochromeIconBitmap)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((((((java.util.Objects.hashCode(this.mTitle) + 31) * 31) + java.util.Objects.hashCode(this.mOriginalComponentName)) * 31) + java.util.Objects.hashCode(this.mIconBitmap)) * 31) + java.util.Objects.hashCode(this.mMonochromeIconBitmap);
        }

        @java.lang.Deprecated
        private void __metadata() {
        }
    }

    public ArchiveState(@android.annotation.NonNull java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> list, @android.annotation.NonNull java.lang.String str, long j) {
        this.mActivityInfos = list;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mActivityInfos);
        this.mInstallerTitle = str;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.NonNull.class, (android.annotation.NonNull) null, this.mInstallerTitle);
        this.mArchiveTimeMillis = j;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.CurrentTimeMillisLong.class, (java.lang.annotation.Annotation) null, this.mArchiveTimeMillis);
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> getActivityInfos() {
        return this.mActivityInfos;
    }

    @android.annotation.NonNull
    public java.lang.String getInstallerTitle() {
        return this.mInstallerTitle;
    }

    public long getArchiveTimeMillis() {
        return this.mArchiveTimeMillis;
    }

    public java.lang.String toString() {
        return "ArchiveState { activityInfos = " + this.mActivityInfos + ", installerTitle = " + this.mInstallerTitle + ", archiveTimeMillis = " + this.mArchiveTimeMillis + " }";
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.server.pm.pkg.ArchiveState archiveState = (com.android.server.pm.pkg.ArchiveState) obj;
        if (java.util.Objects.equals(this.mActivityInfos, archiveState.mActivityInfos) && java.util.Objects.equals(this.mInstallerTitle, archiveState.mInstallerTitle) && this.mArchiveTimeMillis == archiveState.mArchiveTimeMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mActivityInfos) + 31) * 31) + java.util.Objects.hashCode(this.mInstallerTitle)) * 31) + java.lang.Long.hashCode(this.mArchiveTimeMillis);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
