package android.content.integrity;

/* loaded from: classes.dex */
public class InstallerAllowedByManifestFormula extends android.content.integrity.IntegrityFormula implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.integrity.InstallerAllowedByManifestFormula> CREATOR = new android.os.Parcelable.Creator<android.content.integrity.InstallerAllowedByManifestFormula>() { // from class: android.content.integrity.InstallerAllowedByManifestFormula.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.integrity.InstallerAllowedByManifestFormula createFromParcel(android.os.Parcel parcel) {
            return new android.content.integrity.InstallerAllowedByManifestFormula(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.integrity.InstallerAllowedByManifestFormula[] newArray(int i) {
            return new android.content.integrity.InstallerAllowedByManifestFormula[i];
        }
    };
    public static final java.lang.String INSTALLER_CERTIFICATE_NOT_EVALUATED = "";

    public InstallerAllowedByManifestFormula() {
    }

    private InstallerAllowedByManifestFormula(android.os.Parcel parcel) {
    }

    @Override // android.content.integrity.IntegrityFormula
    public int getTag() {
        return 4;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean matches(android.content.integrity.AppInstallMetadata appInstallMetadata) {
        java.util.Map<java.lang.String, java.lang.String> allowedInstallersAndCertificates = appInstallMetadata.getAllowedInstallersAndCertificates();
        return allowedInstallersAndCertificates.isEmpty() || installerInAllowedInstallersFromManifest(appInstallMetadata, allowedInstallersAndCertificates);
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateFormula() {
        return false;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateLineageFormula() {
        return false;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isInstallerFormula() {
        return true;
    }

    private static boolean installerInAllowedInstallersFromManifest(android.content.integrity.AppInstallMetadata appInstallMetadata, java.util.Map<java.lang.String, java.lang.String> map) {
        java.lang.String installerName = appInstallMetadata.getInstallerName();
        if (!map.containsKey(installerName)) {
            return false;
        }
        if (!map.get(installerName).equals("")) {
            return appInstallMetadata.getInstallerCertificates().contains(map.get(appInstallMetadata.getInstallerName()));
        }
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }
}
