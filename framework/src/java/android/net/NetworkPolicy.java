package android.net;

/* loaded from: classes2.dex */
public class NetworkPolicy implements android.os.Parcelable, java.lang.Comparable<android.net.NetworkPolicy> {
    public static final int CYCLE_NONE = -1;
    private static final long DEFAULT_MTU = 1500;
    public static final long LIMIT_DISABLED = -1;
    public static final long SNOOZE_NEVER = -1;
    private static final int TEMPLATE_BACKUP_VERSION_1_INIT = 1;
    private static final int TEMPLATE_BACKUP_VERSION_2_UNSUPPORTED = 2;
    private static final int TEMPLATE_BACKUP_VERSION_3_SUPPORT_CARRIER_TEMPLATE = 3;
    private static final int TEMPLATE_BACKUP_VERSION_LATEST = 3;
    private static final int VERSION_INIT = 1;
    private static final int VERSION_RAPID = 3;
    private static final int VERSION_RULE = 2;
    public static final long WARNING_DISABLED = -1;
    public android.util.RecurrenceRule cycleRule;
    public boolean inferred;
    public long lastLimitSnooze;
    public long lastRapidSnooze;
    public long lastWarningSnooze;
    public long limitBytes;

    @java.lang.Deprecated
    public boolean metered;
    public android.net.NetworkTemplate template;
    public long warningBytes;
    private static final java.lang.String TAG = android.net.NetworkPolicy.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.net.NetworkPolicy> CREATOR = new android.os.Parcelable.Creator<android.net.NetworkPolicy>() { // from class: android.net.NetworkPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkPolicy createFromParcel(android.os.Parcel parcel) {
            return new android.net.NetworkPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkPolicy[] newArray(int i) {
            return new android.net.NetworkPolicy[i];
        }
    };

    public static android.util.RecurrenceRule buildRule(int i, java.time.ZoneId zoneId) {
        if (i != -1) {
            return android.util.RecurrenceRule.buildRecurringMonthly(i, zoneId);
        }
        return android.util.RecurrenceRule.buildNever();
    }

    @java.lang.Deprecated
    public NetworkPolicy(android.net.NetworkTemplate networkTemplate, int i, java.lang.String str, long j, long j2, boolean z) {
        this(networkTemplate, i, str, j, j2, -1L, -1L, z, false);
    }

    @java.lang.Deprecated
    public NetworkPolicy(android.net.NetworkTemplate networkTemplate, int i, java.lang.String str, long j, long j2, long j3, long j4, boolean z, boolean z2) {
        this(networkTemplate, buildRule(i, java.time.ZoneId.of(str)), j, j2, j3, j4, z, z2);
    }

    @java.lang.Deprecated
    public NetworkPolicy(android.net.NetworkTemplate networkTemplate, android.util.RecurrenceRule recurrenceRule, long j, long j2, long j3, long j4, boolean z, boolean z2) {
        this(networkTemplate, recurrenceRule, j, j2, j3, j4, -1L, z, z2);
    }

    public NetworkPolicy(android.net.NetworkTemplate networkTemplate, android.util.RecurrenceRule recurrenceRule, long j, long j2, long j3, long j4, long j5, boolean z, boolean z2) {
        this.warningBytes = -1L;
        this.limitBytes = -1L;
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
        this.metered = true;
        this.inferred = false;
        this.template = (android.net.NetworkTemplate) com.android.internal.util.Preconditions.checkNotNull(networkTemplate, "missing NetworkTemplate");
        this.cycleRule = (android.util.RecurrenceRule) com.android.internal.util.Preconditions.checkNotNull(recurrenceRule, "missing RecurrenceRule");
        this.warningBytes = j;
        this.limitBytes = j2;
        this.lastWarningSnooze = j3;
        this.lastLimitSnooze = j4;
        this.lastRapidSnooze = j5;
        this.metered = z;
        this.inferred = z2;
    }

    private NetworkPolicy(android.os.Parcel parcel) {
        boolean z;
        this.warningBytes = -1L;
        this.limitBytes = -1L;
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
        this.metered = true;
        this.inferred = false;
        this.template = (android.net.NetworkTemplate) parcel.readParcelable(null, android.net.NetworkTemplate.class);
        this.cycleRule = (android.util.RecurrenceRule) parcel.readParcelable(null, android.util.RecurrenceRule.class);
        this.warningBytes = parcel.readLong();
        this.limitBytes = parcel.readLong();
        this.lastWarningSnooze = parcel.readLong();
        this.lastLimitSnooze = parcel.readLong();
        this.lastRapidSnooze = parcel.readLong();
        if (parcel.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.metered = z;
        this.inferred = parcel.readInt() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.template, i);
        parcel.writeParcelable(this.cycleRule, i);
        parcel.writeLong(this.warningBytes);
        parcel.writeLong(this.limitBytes);
        parcel.writeLong(this.lastWarningSnooze);
        parcel.writeLong(this.lastLimitSnooze);
        parcel.writeLong(this.lastRapidSnooze);
        parcel.writeInt(this.metered ? 1 : 0);
        parcel.writeInt(this.inferred ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.util.Iterator<android.util.Range<java.time.ZonedDateTime>> cycleIterator() {
        return this.cycleRule.cycleIterator();
    }

    public boolean isOverWarning(long j) {
        return this.warningBytes != -1 && j >= this.warningBytes;
    }

    public boolean isOverLimit(long j) {
        return this.limitBytes != -1 && j + 3000 >= this.limitBytes;
    }

    public void clearSnooze() {
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
    }

    public boolean hasCycle() {
        return this.cycleRule.cycleIterator().hasNext();
    }

    @Override // java.lang.Comparable
    public int compareTo(android.net.NetworkPolicy networkPolicy) {
        if (networkPolicy == null || networkPolicy.limitBytes == -1) {
            return -1;
        }
        if (this.limitBytes == -1 || networkPolicy.limitBytes < this.limitBytes) {
            return 1;
        }
        return 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.template, this.cycleRule, java.lang.Long.valueOf(this.warningBytes), java.lang.Long.valueOf(this.limitBytes), java.lang.Long.valueOf(this.lastWarningSnooze), java.lang.Long.valueOf(this.lastLimitSnooze), java.lang.Long.valueOf(this.lastRapidSnooze), java.lang.Boolean.valueOf(this.metered), java.lang.Boolean.valueOf(this.inferred));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.NetworkPolicy)) {
            return false;
        }
        android.net.NetworkPolicy networkPolicy = (android.net.NetworkPolicy) obj;
        return this.warningBytes == networkPolicy.warningBytes && this.limitBytes == networkPolicy.limitBytes && this.lastWarningSnooze == networkPolicy.lastWarningSnooze && this.lastLimitSnooze == networkPolicy.lastLimitSnooze && this.lastRapidSnooze == networkPolicy.lastRapidSnooze && this.metered == networkPolicy.metered && this.inferred == networkPolicy.inferred && java.util.Objects.equals(this.template, networkPolicy.template) && java.util.Objects.equals(this.cycleRule, networkPolicy.cycleRule);
    }

    public java.lang.String toString() {
        return "NetworkPolicy{template=" + this.template + " cycleRule=" + this.cycleRule + " warningBytes=" + this.warningBytes + " limitBytes=" + this.limitBytes + " lastWarningSnooze=" + this.lastWarningSnooze + " lastLimitSnooze=" + this.lastLimitSnooze + " lastRapidSnooze=" + this.lastRapidSnooze + " metered=" + this.metered + " inferred=" + this.inferred + "}";
    }

    public byte[] getBytesForBackup() throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(3);
        dataOutputStream.write(getNetworkTemplateBytesForBackup());
        this.cycleRule.writeToStream(dataOutputStream);
        dataOutputStream.writeLong(this.warningBytes);
        dataOutputStream.writeLong(this.limitBytes);
        dataOutputStream.writeLong(this.lastWarningSnooze);
        dataOutputStream.writeLong(this.lastLimitSnooze);
        dataOutputStream.writeLong(this.lastRapidSnooze);
        dataOutputStream.writeInt(this.metered ? 1 : 0);
        dataOutputStream.writeInt(this.inferred ? 1 : 0);
        return byteArrayOutputStream.toByteArray();
    }

    public static android.net.NetworkPolicy getNetworkPolicyFromBackup(java.io.DataInputStream dataInputStream) throws java.io.IOException, android.util.BackupUtils.BadVersionException {
        android.util.RecurrenceRule buildRule;
        long j;
        int readInt = dataInputStream.readInt();
        if (readInt < 1 || readInt > 3) {
            throw new android.util.BackupUtils.BadVersionException("Unknown backup version: " + readInt);
        }
        android.net.NetworkTemplate networkTemplateFromBackup = getNetworkTemplateFromBackup(dataInputStream);
        if (readInt >= 2) {
            buildRule = new android.util.RecurrenceRule(dataInputStream);
        } else {
            buildRule = buildRule(dataInputStream.readInt(), java.time.ZoneId.of(android.util.BackupUtils.readString(dataInputStream)));
        }
        long readLong = dataInputStream.readLong();
        long readLong2 = dataInputStream.readLong();
        long readLong3 = dataInputStream.readLong();
        long readLong4 = dataInputStream.readLong();
        if (readInt >= 3) {
            j = dataInputStream.readLong();
        } else {
            j = -1;
        }
        return new android.net.NetworkPolicy(networkTemplateFromBackup, buildRule, readLong, readLong2, readLong3, readLong4, j, dataInputStream.readInt() == 1, dataInputStream.readInt() == 1);
    }

    private byte[] getNetworkTemplateBytesForBackup() throws java.io.IOException {
        if (!isTemplatePersistable(this.template)) {
            android.util.Log.wtf(TAG, "Trying to backup non-persistable template: " + this);
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(3);
        dataOutputStream.writeInt(this.template.getMatchRule());
        java.util.Set subscriberIds = this.template.getSubscriberIds();
        android.util.BackupUtils.writeString(dataOutputStream, subscriberIds.isEmpty() ? null : (java.lang.String) subscriberIds.iterator().next());
        android.util.BackupUtils.writeString(dataOutputStream, this.template.getWifiNetworkKeys().isEmpty() ? null : (java.lang.String) this.template.getWifiNetworkKeys().iterator().next());
        dataOutputStream.writeInt(this.template.getMeteredness());
        return byteArrayOutputStream.toByteArray();
    }

    private static android.net.NetworkTemplate getNetworkTemplateFromBackup(java.io.DataInputStream dataInputStream) throws java.io.IOException, android.util.BackupUtils.BadVersionException {
        int i;
        int readInt = dataInputStream.readInt();
        int i2 = 1;
        if (readInt < 1 || readInt > 3 || readInt == 2) {
            throw new android.util.BackupUtils.BadVersionException("Unknown Backup Serialization Version");
        }
        int readInt2 = dataInputStream.readInt();
        java.lang.String readString = android.util.BackupUtils.readString(dataInputStream);
        java.lang.String readString2 = android.util.BackupUtils.readString(dataInputStream);
        if (readInt >= 3) {
            i = dataInputStream.readInt();
        } else {
            if (readInt2 != 1 && readInt2 != 10) {
                i2 = -1;
            }
            i = i2;
        }
        try {
            android.net.NetworkTemplate.Builder meteredness = new android.net.NetworkTemplate.Builder(readInt2).setMeteredness(i);
            if (readString != null) {
                meteredness.setSubscriberIds(java.util.Set.of(readString));
            }
            if (readString2 != null) {
                meteredness.setWifiNetworkKeys(java.util.Set.of(readString2));
            }
            return meteredness.build();
        } catch (java.lang.IllegalArgumentException e) {
            throw new android.util.BackupUtils.BadVersionException("Restored network template contains unknown match rule " + readInt2, e);
        }
    }

    public static boolean isTemplatePersistable(android.net.NetworkTemplate networkTemplate) {
        switch (networkTemplate.getMatchRule()) {
            case 1:
            case 10:
                return !networkTemplate.getSubscriberIds().isEmpty() && networkTemplate.getMeteredness() == 1;
            case 4:
                return (networkTemplate.getWifiNetworkKeys().isEmpty() && networkTemplate.getSubscriberIds().isEmpty()) ? false : true;
            case 5:
            case 8:
                return true;
            default:
                return false;
        }
    }
}
