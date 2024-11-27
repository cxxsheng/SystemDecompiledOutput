package android.content.integrity;

/* loaded from: classes.dex */
public final class CompoundFormula extends android.content.integrity.IntegrityFormula implements android.os.Parcelable {
    public static final int AND = 0;
    public static final android.os.Parcelable.Creator<android.content.integrity.CompoundFormula> CREATOR = new android.os.Parcelable.Creator<android.content.integrity.CompoundFormula>() { // from class: android.content.integrity.CompoundFormula.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.integrity.CompoundFormula createFromParcel(android.os.Parcel parcel) {
            return new android.content.integrity.CompoundFormula(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.integrity.CompoundFormula[] newArray(int i) {
            return new android.content.integrity.CompoundFormula[i];
        }
    };
    public static final int NOT = 2;
    public static final int OR = 1;
    private final int mConnector;
    private final java.util.List<android.content.integrity.IntegrityFormula> mFormulas;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Connector {
    }

    public CompoundFormula(int i, java.util.List<android.content.integrity.IntegrityFormula> list) {
        com.android.internal.util.Preconditions.checkArgument(isValidConnector(i), "Unknown connector: %d", java.lang.Integer.valueOf(i));
        validateFormulas(i, list);
        this.mConnector = i;
        this.mFormulas = java.util.Collections.unmodifiableList(list);
    }

    CompoundFormula(android.os.Parcel parcel) {
        this.mConnector = parcel.readInt();
        int readInt = parcel.readInt();
        com.android.internal.util.Preconditions.checkArgument(readInt >= 0, "Must have non-negative length. Got %d", java.lang.Integer.valueOf(readInt));
        this.mFormulas = new java.util.ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mFormulas.add(android.content.integrity.IntegrityFormula.readFromParcel(parcel));
        }
        validateFormulas(this.mConnector, this.mFormulas);
    }

    public int getConnector() {
        return this.mConnector;
    }

    public java.util.List<android.content.integrity.IntegrityFormula> getFormulas() {
        return this.mFormulas;
    }

    @Override // android.content.integrity.IntegrityFormula
    public int getTag() {
        return 0;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean matches(final android.content.integrity.AppInstallMetadata appInstallMetadata) {
        switch (getConnector()) {
            case 0:
                return getFormulas().stream().allMatch(new java.util.function.Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean matches;
                        matches = ((android.content.integrity.IntegrityFormula) obj).matches(android.content.integrity.AppInstallMetadata.this);
                        return matches;
                    }
                });
            case 1:
                return getFormulas().stream().anyMatch(new java.util.function.Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean matches;
                        matches = ((android.content.integrity.IntegrityFormula) obj).matches(android.content.integrity.AppInstallMetadata.this);
                        return matches;
                    }
                });
            case 2:
                return !getFormulas().get(0).matches(appInstallMetadata);
            default:
                throw new java.lang.IllegalArgumentException("Unknown connector " + getConnector());
        }
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateFormula() {
        return getFormulas().stream().anyMatch(new java.util.function.Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isAppCertificateFormula;
                isAppCertificateFormula = ((android.content.integrity.IntegrityFormula) obj).isAppCertificateFormula();
                return isAppCertificateFormula;
            }
        });
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateLineageFormula() {
        return getFormulas().stream().anyMatch(new java.util.function.Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isAppCertificateLineageFormula;
                isAppCertificateLineageFormula = ((android.content.integrity.IntegrityFormula) obj).isAppCertificateLineageFormula();
                return isAppCertificateLineageFormula;
            }
        });
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isInstallerFormula() {
        return getFormulas().stream().anyMatch(new java.util.function.Predicate() { // from class: android.content.integrity.CompoundFormula$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isInstallerFormula;
                isInstallerFormula = ((android.content.integrity.IntegrityFormula) obj).isInstallerFormula();
                return isInstallerFormula;
            }
        });
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.mFormulas.size() == 1) {
            sb.append(java.lang.String.format("%s ", connectorToString(this.mConnector)));
            sb.append(this.mFormulas.get(0).toString());
        } else {
            for (int i = 0; i < this.mFormulas.size(); i++) {
                if (i > 0) {
                    sb.append(java.lang.String.format(" %s ", connectorToString(this.mConnector)));
                }
                sb.append(this.mFormulas.get(i).toString());
            }
        }
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.integrity.CompoundFormula compoundFormula = (android.content.integrity.CompoundFormula) obj;
        if (this.mConnector == compoundFormula.mConnector && this.mFormulas.equals(compoundFormula.mFormulas)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mConnector), this.mFormulas);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mConnector);
        parcel.writeInt(this.mFormulas.size());
        java.util.Iterator<android.content.integrity.IntegrityFormula> it = this.mFormulas.iterator();
        while (it.hasNext()) {
            android.content.integrity.IntegrityFormula.writeToParcel(it.next(), parcel, i);
        }
    }

    private static void validateFormulas(int i, java.util.List<android.content.integrity.IntegrityFormula> list) {
        switch (i) {
            case 0:
            case 1:
                com.android.internal.util.Preconditions.checkArgument(list.size() >= 2, "Connector %s must have at least 2 formulas", connectorToString(i));
                break;
            case 2:
                com.android.internal.util.Preconditions.checkArgument(list.size() == 1, "Connector %s must have 1 formula only", connectorToString(i));
                break;
        }
    }

    private static java.lang.String connectorToString(int i) {
        switch (i) {
            case 0:
                return "AND";
            case 1:
                return "OR";
            case 2:
                return "NOT";
            default:
                throw new java.lang.IllegalArgumentException("Unknown connector " + i);
        }
    }

    private static boolean isValidConnector(int i) {
        return i == 0 || i == 1 || i == 2;
    }
}
