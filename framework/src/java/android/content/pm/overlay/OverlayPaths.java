package android.content.pm.overlay;

/* loaded from: classes.dex */
public class OverlayPaths {
    private final java.util.List<java.lang.String> mOverlayPaths;
    private final java.util.List<java.lang.String> mResourceDirs;

    public static class Builder {
        final android.content.pm.overlay.OverlayPaths mPaths = new android.content.pm.overlay.OverlayPaths();

        public Builder() {
        }

        public Builder(android.content.pm.overlay.OverlayPaths overlayPaths) {
            this.mPaths.mResourceDirs.addAll(overlayPaths.getResourceDirs());
            this.mPaths.mOverlayPaths.addAll(overlayPaths.getOverlayPaths());
        }

        public android.content.pm.overlay.OverlayPaths.Builder addNonApkPath(java.lang.String str) {
            this.mPaths.mOverlayPaths.add(str);
            return this;
        }

        public android.content.pm.overlay.OverlayPaths.Builder addApkPath(java.lang.String str) {
            addUniquePath(this.mPaths.mResourceDirs, str);
            addUniquePath(this.mPaths.mOverlayPaths, str);
            return this;
        }

        public android.content.pm.overlay.OverlayPaths.Builder addAll(android.content.pm.overlay.OverlayPaths overlayPaths) {
            if (overlayPaths != null) {
                java.util.Iterator<java.lang.String> it = overlayPaths.getResourceDirs().iterator();
                while (it.hasNext()) {
                    addUniquePath(this.mPaths.mResourceDirs, it.next());
                }
                java.util.Iterator<java.lang.String> it2 = overlayPaths.getOverlayPaths().iterator();
                while (it2.hasNext()) {
                    addUniquePath(this.mPaths.mOverlayPaths, it2.next());
                }
            }
            return this;
        }

        public android.content.pm.overlay.OverlayPaths build() {
            return this.mPaths;
        }

        private static void addUniquePath(java.util.List<java.lang.String> list, java.lang.String str) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
    }

    public boolean isEmpty() {
        return this.mResourceDirs.isEmpty() && this.mOverlayPaths.isEmpty();
    }

    private OverlayPaths() {
        this.mResourceDirs = new java.util.ArrayList();
        this.mOverlayPaths = new java.util.ArrayList();
    }

    public java.util.List<java.lang.String> getResourceDirs() {
        return this.mResourceDirs;
    }

    public java.util.List<java.lang.String> getOverlayPaths() {
        return this.mOverlayPaths;
    }

    public java.lang.String toString() {
        return "OverlayPaths { resourceDirs = " + this.mResourceDirs + ", overlayPaths = " + this.mOverlayPaths + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.pm.overlay.OverlayPaths overlayPaths = (android.content.pm.overlay.OverlayPaths) obj;
        if (java.util.Objects.equals(this.mResourceDirs, overlayPaths.mResourceDirs) && java.util.Objects.equals(this.mOverlayPaths, overlayPaths.mOverlayPaths)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.mResourceDirs) + 31) * 31) + java.util.Objects.hashCode(this.mOverlayPaths);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
