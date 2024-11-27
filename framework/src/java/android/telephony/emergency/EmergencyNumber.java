package android.telephony.emergency;

/* loaded from: classes3.dex */
public final class EmergencyNumber implements android.os.Parcelable, java.lang.Comparable<android.telephony.emergency.EmergencyNumber> {
    public static final android.os.Parcelable.Creator<android.telephony.emergency.EmergencyNumber> CREATOR;
    public static final int EMERGENCY_CALL_ROUTING_EMERGENCY = 1;
    public static final int EMERGENCY_CALL_ROUTING_NORMAL = 2;
    public static final int EMERGENCY_CALL_ROUTING_UNKNOWN = 0;
    public static final int EMERGENCY_NUMBER_SOURCE_DATABASE = 16;
    public static final int EMERGENCY_NUMBER_SOURCE_DEFAULT = 8;
    public static final int EMERGENCY_NUMBER_SOURCE_MODEM_CONFIG = 4;
    public static final int EMERGENCY_NUMBER_SOURCE_NETWORK_SIGNALING = 1;
    private static final int[] EMERGENCY_NUMBER_SOURCE_PRECEDENCE;
    private static final java.util.Set<java.lang.Integer> EMERGENCY_NUMBER_SOURCE_SET;
    public static final int EMERGENCY_NUMBER_SOURCE_SIM = 2;
    public static final int EMERGENCY_NUMBER_SOURCE_TEST = 32;
    public static final int EMERGENCY_SERVICE_CATEGORY_AIEC = 64;
    public static final int EMERGENCY_SERVICE_CATEGORY_AMBULANCE = 2;
    public static final int EMERGENCY_SERVICE_CATEGORY_FIRE_BRIGADE = 4;
    public static final int EMERGENCY_SERVICE_CATEGORY_MARINE_GUARD = 8;
    public static final int EMERGENCY_SERVICE_CATEGORY_MIEC = 32;
    public static final int EMERGENCY_SERVICE_CATEGORY_MOUNTAIN_RESCUE = 16;
    public static final int EMERGENCY_SERVICE_CATEGORY_POLICE = 1;
    private static final java.util.Set<java.lang.Integer> EMERGENCY_SERVICE_CATEGORY_SET = new java.util.HashSet();
    public static final int EMERGENCY_SERVICE_CATEGORY_UNSPECIFIED = 0;
    private static final java.lang.String LOG_TAG = "EmergencyNumber";
    private final java.lang.String mCountryIso;
    private final int mEmergencyCallRouting;
    private final int mEmergencyNumberSourceBitmask;
    private final int mEmergencyServiceCategoryBitmask;
    private final java.util.List<java.lang.String> mEmergencyUrns;
    private final java.lang.String mMnc;
    private final java.lang.String mNumber;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EmergencyCallRouting {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EmergencyNumberSources {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EmergencyServiceCategories {
    }

    static {
        EMERGENCY_SERVICE_CATEGORY_SET.add(1);
        EMERGENCY_SERVICE_CATEGORY_SET.add(2);
        EMERGENCY_SERVICE_CATEGORY_SET.add(4);
        EMERGENCY_SERVICE_CATEGORY_SET.add(8);
        EMERGENCY_SERVICE_CATEGORY_SET.add(16);
        EMERGENCY_SERVICE_CATEGORY_SET.add(32);
        EMERGENCY_SERVICE_CATEGORY_SET.add(64);
        EMERGENCY_NUMBER_SOURCE_SET = new java.util.HashSet();
        EMERGENCY_NUMBER_SOURCE_SET.add(1);
        EMERGENCY_NUMBER_SOURCE_SET.add(2);
        EMERGENCY_NUMBER_SOURCE_SET.add(16);
        EMERGENCY_NUMBER_SOURCE_SET.add(4);
        EMERGENCY_NUMBER_SOURCE_SET.add(8);
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE = new int[4];
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[0] = 1;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[1] = 2;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[2] = 16;
        EMERGENCY_NUMBER_SOURCE_PRECEDENCE[3] = 4;
        CREATOR = new android.os.Parcelable.Creator<android.telephony.emergency.EmergencyNumber>() { // from class: android.telephony.emergency.EmergencyNumber.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.emergency.EmergencyNumber createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.emergency.EmergencyNumber(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.emergency.EmergencyNumber[] newArray(int i) {
                return new android.telephony.emergency.EmergencyNumber[i];
            }
        };
    }

    public EmergencyNumber(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.util.List<java.lang.String> list, int i2, int i3) {
        this.mNumber = str;
        this.mCountryIso = str2;
        this.mMnc = str3;
        this.mEmergencyServiceCategoryBitmask = i;
        this.mEmergencyUrns = list;
        this.mEmergencyNumberSourceBitmask = i2;
        this.mEmergencyCallRouting = i3;
    }

    public EmergencyNumber(android.os.Parcel parcel) {
        this.mNumber = parcel.readString();
        this.mCountryIso = parcel.readString();
        this.mMnc = parcel.readString();
        this.mEmergencyServiceCategoryBitmask = parcel.readInt();
        this.mEmergencyUrns = parcel.createStringArrayList();
        this.mEmergencyNumberSourceBitmask = parcel.readInt();
        this.mEmergencyCallRouting = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mNumber);
        parcel.writeString(this.mCountryIso);
        parcel.writeString(this.mMnc);
        parcel.writeInt(this.mEmergencyServiceCategoryBitmask);
        parcel.writeStringList(this.mEmergencyUrns);
        parcel.writeInt(this.mEmergencyNumberSourceBitmask);
        parcel.writeInt(this.mEmergencyCallRouting);
    }

    public java.lang.String getNumber() {
        return this.mNumber;
    }

    public java.lang.String getCountryIso() {
        return this.mCountryIso;
    }

    public java.lang.String getMnc() {
        return this.mMnc;
    }

    public int getEmergencyServiceCategoryBitmask() {
        return this.mEmergencyServiceCategoryBitmask;
    }

    public int getEmergencyServiceCategoryBitmaskInternalDial() {
        if (this.mEmergencyNumberSourceBitmask == 16) {
            return 0;
        }
        return this.mEmergencyServiceCategoryBitmask;
    }

    public java.util.List<java.lang.Integer> getEmergencyServiceCategories() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (serviceUnspecified()) {
            arrayList.add(0);
            return arrayList;
        }
        for (java.lang.Integer num : EMERGENCY_SERVICE_CATEGORY_SET) {
            if (isInEmergencyServiceCategories(num.intValue())) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public java.util.List<java.lang.String> getEmergencyUrns() {
        return java.util.Collections.unmodifiableList(this.mEmergencyUrns);
    }

    private boolean serviceUnspecified() {
        return this.mEmergencyServiceCategoryBitmask == 0;
    }

    public boolean isInEmergencyServiceCategories(int i) {
        if (i == 0) {
            return serviceUnspecified();
        }
        return serviceUnspecified() || (this.mEmergencyServiceCategoryBitmask & i) == i;
    }

    public int getEmergencyNumberSourceBitmask() {
        return this.mEmergencyNumberSourceBitmask;
    }

    public java.util.List<java.lang.Integer> getEmergencyNumberSources() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.Integer num : EMERGENCY_NUMBER_SOURCE_SET) {
            if ((this.mEmergencyNumberSourceBitmask & num.intValue()) == num.intValue()) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public boolean isFromSources(int i) {
        return (this.mEmergencyNumberSourceBitmask & i) == i;
    }

    public int getEmergencyCallRouting() {
        return this.mEmergencyCallRouting;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return java.lang.String.format("[EmergencyNumber: %s, countryIso=%s, mnc=%s, src=%s, routing=%s, categories=%s, urns=%s]", this.mNumber, this.mCountryIso, this.mMnc, sourceBitmaskToString(this.mEmergencyNumberSourceBitmask), routingToString(this.mEmergencyCallRouting), categoriesToString(this.mEmergencyServiceCategoryBitmask), this.mEmergencyUrns == null ? "" : this.mEmergencyUrns.stream().collect(java.util.stream.Collectors.joining(",")));
    }

    private java.lang.String categoriesToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 64) == 64) {
            sb.append("auto ");
        }
        if ((i & 2) == 2) {
            sb.append("ambulance ");
        }
        if ((i & 4) == 4) {
            sb.append("fire ");
        }
        if ((i & 8) == 8) {
            sb.append("marine ");
        }
        if ((i & 16) == 16) {
            sb.append("mountain ");
        }
        if ((i & 1) == 1) {
            sb.append("police ");
        }
        if ((i & 32) == 32) {
            sb.append("manual ");
        }
        return sb.toString();
    }

    private java.lang.String routingToString(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "emergency";
            case 2:
                return android.graphics.FontListParser.STYLE_NORMAL;
            default:
                return "🤷";
        }
    }

    private java.lang.String sourceBitmaskToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) == 1) {
            sb.append("net ");
        }
        if ((i & 2) == 2) {
            sb.append("sim ");
        }
        if ((i & 16) == 16) {
            sb.append("db ");
        }
        if ((i & 4) == 4) {
            sb.append("mdm ");
        }
        if ((i & 8) == 8) {
            sb.append("def ");
        }
        if ((i & 32) == 32) {
            sb.append("tst ");
        }
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!android.telephony.emergency.EmergencyNumber.class.isInstance(obj)) {
            return false;
        }
        android.telephony.emergency.EmergencyNumber emergencyNumber = (android.telephony.emergency.EmergencyNumber) obj;
        return this.mNumber.equals(emergencyNumber.mNumber) && this.mCountryIso.equals(emergencyNumber.mCountryIso) && this.mMnc.equals(emergencyNumber.mMnc) && this.mEmergencyServiceCategoryBitmask == emergencyNumber.mEmergencyServiceCategoryBitmask && this.mEmergencyUrns.equals(emergencyNumber.mEmergencyUrns) && this.mEmergencyNumberSourceBitmask == emergencyNumber.mEmergencyNumberSourceBitmask && this.mEmergencyCallRouting == emergencyNumber.mEmergencyCallRouting;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mNumber, this.mCountryIso, this.mMnc, java.lang.Integer.valueOf(this.mEmergencyServiceCategoryBitmask), this.mEmergencyUrns, java.lang.Integer.valueOf(this.mEmergencyNumberSourceBitmask), java.lang.Integer.valueOf(this.mEmergencyCallRouting));
    }

    private int getDisplayPriorityScore() {
        int i;
        if (!isFromSources(1)) {
            i = 0;
        } else {
            i = 16;
        }
        if (isFromSources(2)) {
            i += 8;
        }
        if (isFromSources(16)) {
            i += 4;
        }
        if (isFromSources(8)) {
            i += 2;
        }
        if (isFromSources(4)) {
            return i + 1;
        }
        return i;
    }

    @Override // java.lang.Comparable
    public int compareTo(android.telephony.emergency.EmergencyNumber emergencyNumber) {
        if (getDisplayPriorityScore() > emergencyNumber.getDisplayPriorityScore()) {
            return -1;
        }
        if (getDisplayPriorityScore() < emergencyNumber.getDisplayPriorityScore()) {
            return 1;
        }
        if (getNumber().compareTo(emergencyNumber.getNumber()) != 0) {
            return getNumber().compareTo(emergencyNumber.getNumber());
        }
        if (getCountryIso().compareTo(emergencyNumber.getCountryIso()) != 0) {
            return getCountryIso().compareTo(emergencyNumber.getCountryIso());
        }
        if (getMnc().compareTo(emergencyNumber.getMnc()) != 0) {
            return getMnc().compareTo(emergencyNumber.getMnc());
        }
        if (getEmergencyServiceCategoryBitmask() != emergencyNumber.getEmergencyServiceCategoryBitmask()) {
            return getEmergencyServiceCategoryBitmask() > emergencyNumber.getEmergencyServiceCategoryBitmask() ? -1 : 1;
        }
        if (getEmergencyUrns().toString().compareTo(emergencyNumber.getEmergencyUrns().toString()) != 0) {
            return getEmergencyUrns().toString().compareTo(emergencyNumber.getEmergencyUrns().toString());
        }
        if (getEmergencyCallRouting() != emergencyNumber.getEmergencyCallRouting()) {
            return getEmergencyCallRouting() > emergencyNumber.getEmergencyCallRouting() ? -1 : 1;
        }
        return 0;
    }

    public static void mergeSameNumbersInEmergencyNumberList(java.util.List<android.telephony.emergency.EmergencyNumber> list) {
        mergeSameNumbersInEmergencyNumberList(list, false);
    }

    public static void mergeSameNumbersInEmergencyNumberList(java.util.List<android.telephony.emergency.EmergencyNumber> list, boolean z) {
        if (list == null) {
            return;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i = 0; i < list.size(); i++) {
            for (int i2 = 0; i2 < i; i2++) {
                if (areSameEmergencyNumbers(list.get(i), list.get(i2), z)) {
                    com.android.telephony.Rlog.e(LOG_TAG, "Found unexpected duplicate numbers " + list.get(i) + " vs " + list.get(i2));
                    list.set(i, mergeSameEmergencyNumbers(list.get(i), list.get(i2), z));
                    hashSet.add(java.lang.Integer.valueOf(i2));
                }
            }
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            if (hashSet.contains(java.lang.Integer.valueOf(size))) {
                list.remove(size);
            }
        }
        java.util.Collections.sort(list);
    }

    public static boolean areSameEmergencyNumbers(android.telephony.emergency.EmergencyNumber emergencyNumber, android.telephony.emergency.EmergencyNumber emergencyNumber2, boolean z) {
        if (emergencyNumber.getNumber().equals(emergencyNumber2.getNumber()) && emergencyNumber.getCountryIso().equals(emergencyNumber2.getCountryIso()) && emergencyNumber.getMnc().equals(emergencyNumber2.getMnc())) {
            return (z || (emergencyNumber.getEmergencyServiceCategoryBitmask() == emergencyNumber2.getEmergencyServiceCategoryBitmask() && emergencyNumber.getEmergencyUrns().equals(emergencyNumber2.getEmergencyUrns()))) && !(emergencyNumber.isFromSources(32) ^ emergencyNumber2.isFromSources(32));
        }
        return false;
    }

    public static android.telephony.emergency.EmergencyNumber mergeSameEmergencyNumbers(android.telephony.emergency.EmergencyNumber emergencyNumber, android.telephony.emergency.EmergencyNumber emergencyNumber2) {
        int i;
        if (areSameEmergencyNumbers(emergencyNumber, emergencyNumber2, false)) {
            int emergencyCallRouting = emergencyNumber.getEmergencyCallRouting();
            if (!emergencyNumber2.isFromSources(16)) {
                i = emergencyCallRouting;
            } else {
                i = emergencyNumber2.getEmergencyCallRouting();
            }
            return new android.telephony.emergency.EmergencyNumber(emergencyNumber.getNumber(), emergencyNumber.getCountryIso(), emergencyNumber.getMnc(), emergencyNumber.getEmergencyServiceCategoryBitmask(), emergencyNumber.getEmergencyUrns(), emergencyNumber.getEmergencyNumberSourceBitmask() | emergencyNumber2.getEmergencyNumberSourceBitmask(), i);
        }
        return null;
    }

    private static java.util.List<java.lang.String> mergeEmergencyUrns(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(list);
        for (java.lang.String str : list2) {
            if (!list.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private static void fillServiceCategoryAndUrns(android.telephony.emergency.EmergencyNumber emergencyNumber, android.util.SparseIntArray sparseIntArray, android.util.SparseArray<java.util.List<java.lang.String>> sparseArray) {
        int emergencyNumberSourceBitmask = emergencyNumber.getEmergencyNumberSourceBitmask();
        for (int i : EMERGENCY_NUMBER_SOURCE_PRECEDENCE) {
            java.lang.Integer valueOf = java.lang.Integer.valueOf(i);
            if ((valueOf.intValue() & emergencyNumberSourceBitmask) == valueOf.intValue()) {
                if (!emergencyNumber.isInEmergencyServiceCategories(0)) {
                    sparseIntArray.put(valueOf.intValue(), emergencyNumber.getEmergencyServiceCategoryBitmask());
                }
                sparseArray.put(valueOf.intValue(), emergencyNumber.getEmergencyUrns());
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static android.telephony.emergency.EmergencyNumber mergeSameEmergencyNumbers(android.telephony.emergency.EmergencyNumber emergencyNumber, android.telephony.emergency.EmergencyNumber emergencyNumber2, boolean z) {
        int i;
        int i2;
        if (!z) {
            return mergeSameEmergencyNumbers(emergencyNumber, emergencyNumber2);
        }
        int emergencyCallRouting = emergencyNumber.getEmergencyCallRouting();
        int emergencyServiceCategoryBitmask = emergencyNumber.getEmergencyServiceCategoryBitmask();
        java.util.List arrayList = new java.util.ArrayList();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(2);
        android.util.SparseArray sparseArray = new android.util.SparseArray(2);
        fillServiceCategoryAndUrns(emergencyNumber, sparseIntArray, sparseArray);
        fillServiceCategoryAndUrns(emergencyNumber2, sparseIntArray, sparseArray);
        if (!emergencyNumber2.isFromSources(16)) {
            i = emergencyCallRouting;
        } else {
            i = emergencyNumber2.getEmergencyCallRouting();
        }
        int[] iArr = EMERGENCY_NUMBER_SOURCE_PRECEDENCE;
        int length = iArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                i2 = emergencyServiceCategoryBitmask;
                break;
            }
            int i4 = iArr[i3];
            if (sparseIntArray.indexOfKey(i4) < 0) {
                i3++;
            } else {
                i2 = sparseIntArray.get(i4);
                break;
            }
        }
        java.util.List list = arrayList;
        for (int i5 : EMERGENCY_NUMBER_SOURCE_PRECEDENCE) {
            if (sparseArray.contains(i5)) {
                list = mergeEmergencyUrns(list, (java.util.List) sparseArray.get(i5));
            }
        }
        return new android.telephony.emergency.EmergencyNumber(emergencyNumber.getNumber(), emergencyNumber.getCountryIso(), emergencyNumber.getMnc(), i2, list, emergencyNumber.getEmergencyNumberSourceBitmask() | emergencyNumber2.getEmergencyNumberSourceBitmask(), i);
    }

    public static boolean validateEmergencyNumberAddress(java.lang.String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!android.telephony.PhoneNumberUtils.isDialable(c)) {
                return false;
            }
        }
        return true;
    }
}
