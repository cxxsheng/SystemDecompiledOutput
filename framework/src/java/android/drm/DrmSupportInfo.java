package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmSupportInfo {
    private final java.util.ArrayList<java.lang.String> mFileSuffixList = new java.util.ArrayList<>();
    private final java.util.ArrayList<java.lang.String> mMimeTypeList = new java.util.ArrayList<>();
    private java.lang.String mDescription = "";

    public void addMimeType(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("mimeType is null");
        }
        if (str == "") {
            throw new java.lang.IllegalArgumentException("mimeType is an empty string");
        }
        this.mMimeTypeList.add(str);
    }

    public void addFileSuffix(java.lang.String str) {
        if (str == "") {
            throw new java.lang.IllegalArgumentException("fileSuffix is an empty string");
        }
        this.mFileSuffixList.add(str);
    }

    public java.util.Iterator<java.lang.String> getMimeTypeIterator() {
        return this.mMimeTypeList.iterator();
    }

    public java.util.Iterator<java.lang.String> getFileSuffixIterator() {
        return this.mFileSuffixList.iterator();
    }

    public void setDescription(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("description is null");
        }
        if (str == "") {
            throw new java.lang.IllegalArgumentException("description is an empty string");
        }
        this.mDescription = str;
    }

    public java.lang.String getDescriprition() {
        return this.mDescription;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public int hashCode() {
        return this.mFileSuffixList.hashCode() + this.mMimeTypeList.hashCode() + this.mDescription.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.drm.DrmSupportInfo)) {
            return false;
        }
        android.drm.DrmSupportInfo drmSupportInfo = (android.drm.DrmSupportInfo) obj;
        return this.mFileSuffixList.equals(drmSupportInfo.mFileSuffixList) && this.mMimeTypeList.equals(drmSupportInfo.mMimeTypeList) && this.mDescription.equals(drmSupportInfo.mDescription);
    }

    boolean isSupportedMimeType(java.lang.String str) {
        if (str != null && !str.equals("")) {
            for (int i = 0; i < this.mMimeTypeList.size(); i++) {
                if (this.mMimeTypeList.get(i).startsWith(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isSupportedFileSuffix(java.lang.String str) {
        return this.mFileSuffixList.contains(str);
    }
}
