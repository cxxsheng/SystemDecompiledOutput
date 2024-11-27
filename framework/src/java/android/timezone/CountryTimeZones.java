package android.timezone;

/* loaded from: classes3.dex */
public final class CountryTimeZones {
    private final com.android.i18n.timezone.CountryTimeZones mDelegate;

    public static final class TimeZoneMapping {
        private com.android.i18n.timezone.CountryTimeZones.TimeZoneMapping mDelegate;

        TimeZoneMapping(com.android.i18n.timezone.CountryTimeZones.TimeZoneMapping timeZoneMapping) {
            this.mDelegate = (com.android.i18n.timezone.CountryTimeZones.TimeZoneMapping) java.util.Objects.requireNonNull(timeZoneMapping);
        }

        public java.lang.String getTimeZoneId() {
            return this.mDelegate.getTimeZoneId();
        }

        public android.icu.util.TimeZone getTimeZone() {
            return this.mDelegate.getTimeZone();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mDelegate.equals(((android.timezone.CountryTimeZones.TimeZoneMapping) obj).mDelegate);
        }

        public int hashCode() {
            return this.mDelegate.hashCode();
        }

        public java.lang.String toString() {
            return this.mDelegate.toString();
        }
    }

    public static final class OffsetResult {
        private final boolean mIsOnlyMatch;
        private final android.icu.util.TimeZone mTimeZone;

        public OffsetResult(android.icu.util.TimeZone timeZone, boolean z) {
            this.mTimeZone = (android.icu.util.TimeZone) java.util.Objects.requireNonNull(timeZone);
            this.mIsOnlyMatch = z;
        }

        public android.icu.util.TimeZone getTimeZone() {
            return this.mTimeZone;
        }

        public boolean isOnlyMatch() {
            return this.mIsOnlyMatch;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.timezone.CountryTimeZones.OffsetResult offsetResult = (android.timezone.CountryTimeZones.OffsetResult) obj;
            if (this.mIsOnlyMatch == offsetResult.mIsOnlyMatch && this.mTimeZone.getID().equals(offsetResult.mTimeZone.getID())) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mTimeZone, java.lang.Boolean.valueOf(this.mIsOnlyMatch));
        }

        public java.lang.String toString() {
            return "OffsetResult{mTimeZone(ID)=" + this.mTimeZone.getID() + ", mIsOnlyMatch=" + this.mIsOnlyMatch + '}';
        }
    }

    CountryTimeZones(com.android.i18n.timezone.CountryTimeZones countryTimeZones) {
        this.mDelegate = countryTimeZones;
    }

    public boolean matchesCountryCode(java.lang.String str) {
        return this.mDelegate.matchesCountryCode(str);
    }

    public java.lang.String getDefaultTimeZoneId() {
        return this.mDelegate.getDefaultTimeZoneId();
    }

    public android.icu.util.TimeZone getDefaultTimeZone() {
        return this.mDelegate.getDefaultTimeZone();
    }

    public boolean isDefaultTimeZoneBoosted() {
        return this.mDelegate.isDefaultTimeZoneBoosted();
    }

    public boolean hasUtcZone(long j) {
        return this.mDelegate.hasUtcZone(j);
    }

    public android.timezone.CountryTimeZones.OffsetResult lookupByOffsetWithBias(long j, android.icu.util.TimeZone timeZone, int i, boolean z) {
        com.android.i18n.timezone.CountryTimeZones.OffsetResult lookupByOffsetWithBias = this.mDelegate.lookupByOffsetWithBias(j, timeZone, i, z);
        if (lookupByOffsetWithBias == null) {
            return null;
        }
        return new android.timezone.CountryTimeZones.OffsetResult(lookupByOffsetWithBias.getTimeZone(), lookupByOffsetWithBias.isOnlyMatch());
    }

    public android.timezone.CountryTimeZones.OffsetResult lookupByOffsetWithBias(long j, android.icu.util.TimeZone timeZone, int i) {
        com.android.i18n.timezone.CountryTimeZones.OffsetResult lookupByOffsetWithBias = this.mDelegate.lookupByOffsetWithBias(j, timeZone, i);
        if (lookupByOffsetWithBias == null) {
            return null;
        }
        return new android.timezone.CountryTimeZones.OffsetResult(lookupByOffsetWithBias.getTimeZone(), lookupByOffsetWithBias.isOnlyMatch());
    }

    public java.util.List<android.timezone.CountryTimeZones.TimeZoneMapping> getEffectiveTimeZoneMappingsAt(long j) {
        java.util.List effectiveTimeZoneMappingsAt = this.mDelegate.getEffectiveTimeZoneMappingsAt(j);
        java.util.ArrayList arrayList = new java.util.ArrayList(effectiveTimeZoneMappingsAt.size());
        java.util.Iterator it = effectiveTimeZoneMappingsAt.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.timezone.CountryTimeZones.TimeZoneMapping((com.android.i18n.timezone.CountryTimeZones.TimeZoneMapping) it.next()));
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mDelegate.equals(((android.timezone.CountryTimeZones) obj).mDelegate);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDelegate);
    }

    public java.lang.String toString() {
        return this.mDelegate.toString();
    }
}
