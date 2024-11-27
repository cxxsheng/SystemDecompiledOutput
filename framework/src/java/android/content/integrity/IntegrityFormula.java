package android.content.integrity;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class IntegrityFormula {
    public static final int BOOLEAN_ATOMIC_FORMULA_TAG = 3;
    public static final int COMPOUND_FORMULA_TAG = 0;
    public static final int INSTALLER_ALLOWED_BY_MANIFEST_FORMULA_TAG = 4;
    public static final int LONG_ATOMIC_FORMULA_TAG = 2;
    public static final int STRING_ATOMIC_FORMULA_TAG = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Tag {
    }

    public abstract int getTag();

    public abstract boolean isAppCertificateFormula();

    public abstract boolean isAppCertificateLineageFormula();

    public abstract boolean isInstallerFormula();

    public abstract boolean matches(android.content.integrity.AppInstallMetadata appInstallMetadata);

    public static final class Application {
        public static android.content.integrity.IntegrityFormula packageNameEquals(java.lang.String str) {
            return new android.content.integrity.AtomicFormula.StringAtomicFormula(0, str);
        }

        public static android.content.integrity.IntegrityFormula certificatesContain(java.lang.String str) {
            return new android.content.integrity.AtomicFormula.StringAtomicFormula(1, str);
        }

        public static android.content.integrity.IntegrityFormula certificateLineageContains(java.lang.String str) {
            return new android.content.integrity.AtomicFormula.StringAtomicFormula(8, str);
        }

        public static android.content.integrity.IntegrityFormula versionCodeEquals(long j) {
            return new android.content.integrity.AtomicFormula.LongAtomicFormula(4, 0, j);
        }

        public static android.content.integrity.IntegrityFormula versionCodeGreaterThan(long j) {
            return new android.content.integrity.AtomicFormula.LongAtomicFormula(4, 1, j);
        }

        public static android.content.integrity.IntegrityFormula versionCodeGreaterThanOrEqualTo(long j) {
            return new android.content.integrity.AtomicFormula.LongAtomicFormula(4, 2, j);
        }

        public static android.content.integrity.IntegrityFormula isPreInstalled() {
            return new android.content.integrity.AtomicFormula.BooleanAtomicFormula(5, true);
        }

        private Application() {
        }
    }

    public static final class Installer {
        public static android.content.integrity.IntegrityFormula packageNameEquals(java.lang.String str) {
            return new android.content.integrity.AtomicFormula.StringAtomicFormula(2, str);
        }

        public static android.content.integrity.IntegrityFormula notAllowedByManifest() {
            return android.content.integrity.IntegrityFormula.not(new android.content.integrity.InstallerAllowedByManifestFormula());
        }

        public static android.content.integrity.IntegrityFormula certificatesContain(java.lang.String str) {
            return new android.content.integrity.AtomicFormula.StringAtomicFormula(3, str);
        }

        private Installer() {
        }
    }

    public static final class SourceStamp {
        public static android.content.integrity.IntegrityFormula stampCertificateHashEquals(java.lang.String str) {
            return new android.content.integrity.AtomicFormula.StringAtomicFormula(7, str);
        }

        public static android.content.integrity.IntegrityFormula notTrusted() {
            return new android.content.integrity.AtomicFormula.BooleanAtomicFormula(6, false);
        }

        private SourceStamp() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void writeToParcel(android.content.integrity.IntegrityFormula integrityFormula, android.os.Parcel parcel, int i) {
        parcel.writeInt(integrityFormula.getTag());
        ((android.os.Parcelable) integrityFormula).writeToParcel(parcel, i);
    }

    public static android.content.integrity.IntegrityFormula readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                return android.content.integrity.CompoundFormula.CREATOR.createFromParcel(parcel);
            case 1:
                return android.content.integrity.AtomicFormula.StringAtomicFormula.CREATOR.createFromParcel(parcel);
            case 2:
                return android.content.integrity.AtomicFormula.LongAtomicFormula.CREATOR.createFromParcel(parcel);
            case 3:
                return android.content.integrity.AtomicFormula.BooleanAtomicFormula.CREATOR.createFromParcel(parcel);
            case 4:
                return android.content.integrity.InstallerAllowedByManifestFormula.CREATOR.createFromParcel(parcel);
            default:
                throw new java.lang.IllegalArgumentException("Unknown formula tag " + readInt);
        }
    }

    public static android.content.integrity.IntegrityFormula any(android.content.integrity.IntegrityFormula... integrityFormulaArr) {
        return new android.content.integrity.CompoundFormula(1, java.util.Arrays.asList(integrityFormulaArr));
    }

    public static android.content.integrity.IntegrityFormula all(android.content.integrity.IntegrityFormula... integrityFormulaArr) {
        return new android.content.integrity.CompoundFormula(0, java.util.Arrays.asList(integrityFormulaArr));
    }

    public static android.content.integrity.IntegrityFormula not(android.content.integrity.IntegrityFormula integrityFormula) {
        return new android.content.integrity.CompoundFormula(2, java.util.Arrays.asList(integrityFormula));
    }

    IntegrityFormula() {
    }
}
