package android.telephony.mbms;

/* loaded from: classes3.dex */
public class ServiceInfo {
    static final int MAP_LIMIT = 1000;
    private final java.lang.String className;
    private final java.util.List<java.util.Locale> locales;
    private final java.util.Map<java.util.Locale, java.lang.String> names;
    private final java.lang.String serviceId;
    private final java.util.Date sessionEndTime;
    private final java.util.Date sessionStartTime;

    public ServiceInfo(java.util.Map<java.util.Locale, java.lang.String> map, java.lang.String str, java.util.List<java.util.Locale> list, java.lang.String str2, java.util.Date date, java.util.Date date2) {
        if (map == null || str == null || list == null || str2 == null || date == null || date2 == null) {
            throw new java.lang.IllegalArgumentException("Bad ServiceInfo construction");
        }
        if (map.size() > 1000) {
            throw new java.lang.RuntimeException("bad map length " + map.size());
        }
        if (list.size() > 1000) {
            throw new java.lang.RuntimeException("bad locales length " + list.size());
        }
        this.names = new java.util.HashMap(map.size());
        this.names.putAll(map);
        this.className = str;
        this.locales = new java.util.ArrayList(list);
        this.serviceId = str2;
        this.sessionStartTime = (java.util.Date) date.clone();
        this.sessionEndTime = (java.util.Date) date2.clone();
    }

    protected ServiceInfo(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt > 1000 || readInt < 0) {
            throw new java.lang.RuntimeException("bad map length" + readInt);
        }
        this.names = new java.util.HashMap(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt <= 0) {
                break;
            }
            this.names.put((java.util.Locale) parcel.readSerializable(java.util.Locale.class.getClassLoader(), java.util.Locale.class), parcel.readString());
            readInt = i;
        }
        this.className = parcel.readString();
        int readInt2 = parcel.readInt();
        if (readInt2 > 1000 || readInt2 < 0) {
            throw new java.lang.RuntimeException("bad locale length " + readInt2);
        }
        this.locales = new java.util.ArrayList(readInt2);
        while (true) {
            int i2 = readInt2 - 1;
            if (readInt2 > 0) {
                this.locales.add((java.util.Locale) parcel.readSerializable(java.util.Locale.class.getClassLoader(), java.util.Locale.class));
                readInt2 = i2;
            } else {
                this.serviceId = parcel.readString();
                this.sessionStartTime = (java.util.Date) parcel.readSerializable(java.util.Date.class.getClassLoader(), java.util.Date.class);
                this.sessionEndTime = (java.util.Date) parcel.readSerializable(java.util.Date.class.getClassLoader(), java.util.Date.class);
                return;
            }
        }
    }

    public void writeToParcel(android.os.Parcel parcel, int i) {
        java.util.Set<java.util.Locale> keySet = this.names.keySet();
        parcel.writeInt(keySet.size());
        for (java.util.Locale locale : keySet) {
            parcel.writeSerializable(locale);
            parcel.writeString(this.names.get(locale));
        }
        parcel.writeString(this.className);
        parcel.writeInt(this.locales.size());
        java.util.Iterator<java.util.Locale> it = this.locales.iterator();
        while (it.hasNext()) {
            parcel.writeSerializable(it.next());
        }
        parcel.writeString(this.serviceId);
        parcel.writeSerializable(this.sessionStartTime);
        parcel.writeSerializable(this.sessionEndTime);
    }

    public java.lang.CharSequence getNameForLocale(java.util.Locale locale) {
        if (!this.names.containsKey(locale)) {
            throw new java.util.NoSuchElementException("Locale not supported");
        }
        return this.names.get(locale);
    }

    public java.util.Set<java.util.Locale> getNamedContentLocales() {
        return java.util.Collections.unmodifiableSet(this.names.keySet());
    }

    public java.lang.String getServiceClassName() {
        return this.className;
    }

    public java.util.List<java.util.Locale> getLocales() {
        return this.locales;
    }

    public java.lang.String getServiceId() {
        return this.serviceId;
    }

    public java.util.Date getSessionStartTime() {
        return this.sessionStartTime;
    }

    public java.util.Date getSessionEndTime() {
        return this.sessionEndTime;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.mbms.ServiceInfo)) {
            return false;
        }
        android.telephony.mbms.ServiceInfo serviceInfo = (android.telephony.mbms.ServiceInfo) obj;
        if (java.util.Objects.equals(this.names, serviceInfo.names) && java.util.Objects.equals(this.className, serviceInfo.className) && java.util.Objects.equals(this.locales, serviceInfo.locales) && java.util.Objects.equals(this.serviceId, serviceInfo.serviceId) && java.util.Objects.equals(this.sessionStartTime, serviceInfo.sessionStartTime) && java.util.Objects.equals(this.sessionEndTime, serviceInfo.sessionEndTime)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.names, this.className, this.locales, this.serviceId, this.sessionStartTime, this.sessionEndTime);
    }
}
