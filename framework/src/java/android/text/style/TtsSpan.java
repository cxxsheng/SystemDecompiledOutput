package android.text.style;

/* loaded from: classes3.dex */
public class TtsSpan implements android.text.ParcelableSpan {
    public static final java.lang.String ANIMACY_ANIMATE = "android.animate";
    public static final java.lang.String ANIMACY_INANIMATE = "android.inanimate";
    public static final java.lang.String ARG_ANIMACY = "android.arg.animacy";
    public static final java.lang.String ARG_CASE = "android.arg.case";
    public static final java.lang.String ARG_COUNTRY_CODE = "android.arg.country_code";
    public static final java.lang.String ARG_CURRENCY = "android.arg.money";
    public static final java.lang.String ARG_DAY = "android.arg.day";
    public static final java.lang.String ARG_DENOMINATOR = "android.arg.denominator";
    public static final java.lang.String ARG_DIGITS = "android.arg.digits";
    public static final java.lang.String ARG_DOMAIN = "android.arg.domain";
    public static final java.lang.String ARG_EXTENSION = "android.arg.extension";
    public static final java.lang.String ARG_FRACTIONAL_PART = "android.arg.fractional_part";
    public static final java.lang.String ARG_FRAGMENT_ID = "android.arg.fragment_id";
    public static final java.lang.String ARG_GENDER = "android.arg.gender";
    public static final java.lang.String ARG_HOURS = "android.arg.hours";
    public static final java.lang.String ARG_INTEGER_PART = "android.arg.integer_part";
    public static final java.lang.String ARG_MINUTES = "android.arg.minutes";
    public static final java.lang.String ARG_MONTH = "android.arg.month";
    public static final java.lang.String ARG_MULTIPLICITY = "android.arg.multiplicity";
    public static final java.lang.String ARG_NUMBER = "android.arg.number";
    public static final java.lang.String ARG_NUMBER_PARTS = "android.arg.number_parts";
    public static final java.lang.String ARG_NUMERATOR = "android.arg.numerator";
    public static final java.lang.String ARG_PASSWORD = "android.arg.password";
    public static final java.lang.String ARG_PATH = "android.arg.path";
    public static final java.lang.String ARG_PORT = "android.arg.port";
    public static final java.lang.String ARG_PROTOCOL = "android.arg.protocol";
    public static final java.lang.String ARG_QUANTITY = "android.arg.quantity";
    public static final java.lang.String ARG_QUERY_STRING = "android.arg.query_string";
    public static final java.lang.String ARG_TEXT = "android.arg.text";
    public static final java.lang.String ARG_UNIT = "android.arg.unit";
    public static final java.lang.String ARG_USERNAME = "android.arg.username";
    public static final java.lang.String ARG_VERBATIM = "android.arg.verbatim";
    public static final java.lang.String ARG_WEEKDAY = "android.arg.weekday";
    public static final java.lang.String ARG_YEAR = "android.arg.year";
    public static final java.lang.String CASE_ABLATIVE = "android.ablative";
    public static final java.lang.String CASE_ACCUSATIVE = "android.accusative";
    public static final java.lang.String CASE_DATIVE = "android.dative";
    public static final java.lang.String CASE_GENITIVE = "android.genitive";
    public static final java.lang.String CASE_INSTRUMENTAL = "android.instrumental";
    public static final java.lang.String CASE_LOCATIVE = "android.locative";
    public static final java.lang.String CASE_NOMINATIVE = "android.nominative";
    public static final java.lang.String CASE_VOCATIVE = "android.vocative";
    public static final java.lang.String GENDER_FEMALE = "android.female";
    public static final java.lang.String GENDER_MALE = "android.male";
    public static final java.lang.String GENDER_NEUTRAL = "android.neutral";
    public static final int MONTH_APRIL = 3;
    public static final int MONTH_AUGUST = 7;
    public static final int MONTH_DECEMBER = 11;
    public static final int MONTH_FEBRUARY = 1;
    public static final int MONTH_JANUARY = 0;
    public static final int MONTH_JULY = 6;
    public static final int MONTH_JUNE = 5;
    public static final int MONTH_MARCH = 2;
    public static final int MONTH_MAY = 4;
    public static final int MONTH_NOVEMBER = 10;
    public static final int MONTH_OCTOBER = 9;
    public static final int MONTH_SEPTEMBER = 8;
    public static final java.lang.String MULTIPLICITY_DUAL = "android.dual";
    public static final java.lang.String MULTIPLICITY_PLURAL = "android.plural";
    public static final java.lang.String MULTIPLICITY_SINGLE = "android.single";
    public static final java.lang.String TYPE_CARDINAL = "android.type.cardinal";
    public static final java.lang.String TYPE_DATE = "android.type.date";
    public static final java.lang.String TYPE_DECIMAL = "android.type.decimal";
    public static final java.lang.String TYPE_DIGITS = "android.type.digits";
    public static final java.lang.String TYPE_ELECTRONIC = "android.type.electronic";
    public static final java.lang.String TYPE_FRACTION = "android.type.fraction";
    public static final java.lang.String TYPE_MEASURE = "android.type.measure";
    public static final java.lang.String TYPE_MONEY = "android.type.money";
    public static final java.lang.String TYPE_ORDINAL = "android.type.ordinal";
    public static final java.lang.String TYPE_TELEPHONE = "android.type.telephone";
    public static final java.lang.String TYPE_TEXT = "android.type.text";
    public static final java.lang.String TYPE_TIME = "android.type.time";
    public static final java.lang.String TYPE_VERBATIM = "android.type.verbatim";
    public static final int WEEKDAY_FRIDAY = 6;
    public static final int WEEKDAY_MONDAY = 2;
    public static final int WEEKDAY_SATURDAY = 7;
    public static final int WEEKDAY_SUNDAY = 1;
    public static final int WEEKDAY_THURSDAY = 5;
    public static final int WEEKDAY_TUESDAY = 3;
    public static final int WEEKDAY_WEDNESDAY = 4;
    private final android.os.PersistableBundle mArgs;
    private final java.lang.String mType;

    public TtsSpan(java.lang.String str, android.os.PersistableBundle persistableBundle) {
        this.mType = str;
        this.mArgs = persistableBundle;
    }

    public TtsSpan(android.os.Parcel parcel) {
        this.mType = parcel.readString();
        this.mArgs = parcel.readPersistableBundle();
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public android.os.PersistableBundle getArgs() {
        return this.mArgs;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mType);
        parcel.writePersistableBundle(this.mArgs);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 24;
    }

    public static class Builder<C extends android.text.style.TtsSpan.Builder<?>> {
        private android.os.PersistableBundle mArgs = new android.os.PersistableBundle();
        private final java.lang.String mType;

        public Builder(java.lang.String str) {
            this.mType = str;
        }

        public android.text.style.TtsSpan build() {
            return new android.text.style.TtsSpan(this.mType, this.mArgs);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public C setStringArgument(java.lang.String str, java.lang.String str2) {
            this.mArgs.putString(str, str2);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public C setIntArgument(java.lang.String str, int i) {
            this.mArgs.putInt(str, i);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public C setLongArgument(java.lang.String str, long j) {
            this.mArgs.putLong(str, j);
            return this;
        }
    }

    public static class SemioticClassBuilder<C extends android.text.style.TtsSpan.SemioticClassBuilder<?>> extends android.text.style.TtsSpan.Builder<C> {
        public SemioticClassBuilder(java.lang.String str) {
            super(str);
        }

        public C setGender(java.lang.String str) {
            return (C) setStringArgument(android.text.style.TtsSpan.ARG_GENDER, str);
        }

        public C setAnimacy(java.lang.String str) {
            return (C) setStringArgument(android.text.style.TtsSpan.ARG_ANIMACY, str);
        }

        public C setMultiplicity(java.lang.String str) {
            return (C) setStringArgument(android.text.style.TtsSpan.ARG_MULTIPLICITY, str);
        }

        public C setCase(java.lang.String str) {
            return (C) setStringArgument(android.text.style.TtsSpan.ARG_CASE, str);
        }
    }

    public static class TextBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.TextBuilder> {
        public TextBuilder() {
            super(android.text.style.TtsSpan.TYPE_TEXT);
        }

        public TextBuilder(java.lang.String str) {
            this();
            setText(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.TextBuilder setText(java.lang.String str) {
            return (android.text.style.TtsSpan.TextBuilder) setStringArgument(android.text.style.TtsSpan.ARG_TEXT, str);
        }
    }

    public static class CardinalBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.CardinalBuilder> {
        public CardinalBuilder() {
            super(android.text.style.TtsSpan.TYPE_CARDINAL);
        }

        public CardinalBuilder(long j) {
            this();
            setNumber(j);
        }

        public CardinalBuilder(java.lang.String str) {
            this();
            setNumber(str);
        }

        public android.text.style.TtsSpan.CardinalBuilder setNumber(long j) {
            return setNumber(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.CardinalBuilder setNumber(java.lang.String str) {
            return (android.text.style.TtsSpan.CardinalBuilder) setStringArgument(android.text.style.TtsSpan.ARG_NUMBER, str);
        }
    }

    public static class OrdinalBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.OrdinalBuilder> {
        public OrdinalBuilder() {
            super(android.text.style.TtsSpan.TYPE_ORDINAL);
        }

        public OrdinalBuilder(long j) {
            this();
            setNumber(j);
        }

        public OrdinalBuilder(java.lang.String str) {
            this();
            setNumber(str);
        }

        public android.text.style.TtsSpan.OrdinalBuilder setNumber(long j) {
            return setNumber(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.OrdinalBuilder setNumber(java.lang.String str) {
            return (android.text.style.TtsSpan.OrdinalBuilder) setStringArgument(android.text.style.TtsSpan.ARG_NUMBER, str);
        }
    }

    public static class DecimalBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.DecimalBuilder> {
        public DecimalBuilder() {
            super(android.text.style.TtsSpan.TYPE_DECIMAL);
        }

        public DecimalBuilder(double d, int i, int i2) {
            this();
            setArgumentsFromDouble(d, i, i2);
        }

        public DecimalBuilder(java.lang.String str, java.lang.String str2) {
            this();
            setIntegerPart(str);
            setFractionalPart(str2);
        }

        public android.text.style.TtsSpan.DecimalBuilder setArgumentsFromDouble(double d, int i, int i2) {
            java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(java.util.Locale.US);
            numberFormat.setMinimumFractionDigits(i2);
            numberFormat.setMaximumFractionDigits(i2);
            numberFormat.setGroupingUsed(false);
            java.lang.String format = numberFormat.format(d);
            int indexOf = format.indexOf(46);
            if (indexOf >= 0) {
                setIntegerPart(format.substring(0, indexOf));
                setFractionalPart(format.substring(indexOf + 1));
            } else {
                setIntegerPart(format);
            }
            return this;
        }

        public android.text.style.TtsSpan.DecimalBuilder setIntegerPart(long j) {
            return setIntegerPart(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.DecimalBuilder setIntegerPart(java.lang.String str) {
            return (android.text.style.TtsSpan.DecimalBuilder) setStringArgument(android.text.style.TtsSpan.ARG_INTEGER_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.DecimalBuilder setFractionalPart(java.lang.String str) {
            return (android.text.style.TtsSpan.DecimalBuilder) setStringArgument(android.text.style.TtsSpan.ARG_FRACTIONAL_PART, str);
        }
    }

    public static class FractionBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.FractionBuilder> {
        public FractionBuilder() {
            super(android.text.style.TtsSpan.TYPE_FRACTION);
        }

        public FractionBuilder(long j, long j2, long j3) {
            this();
            setIntegerPart(j);
            setNumerator(j2);
            setDenominator(j3);
        }

        public android.text.style.TtsSpan.FractionBuilder setIntegerPart(long j) {
            return setIntegerPart(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.FractionBuilder setIntegerPart(java.lang.String str) {
            return (android.text.style.TtsSpan.FractionBuilder) setStringArgument(android.text.style.TtsSpan.ARG_INTEGER_PART, str);
        }

        public android.text.style.TtsSpan.FractionBuilder setNumerator(long j) {
            return setNumerator(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.FractionBuilder setNumerator(java.lang.String str) {
            return (android.text.style.TtsSpan.FractionBuilder) setStringArgument(android.text.style.TtsSpan.ARG_NUMERATOR, str);
        }

        public android.text.style.TtsSpan.FractionBuilder setDenominator(long j) {
            return setDenominator(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.FractionBuilder setDenominator(java.lang.String str) {
            return (android.text.style.TtsSpan.FractionBuilder) setStringArgument(android.text.style.TtsSpan.ARG_DENOMINATOR, str);
        }
    }

    public static class MeasureBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.MeasureBuilder> {
        public MeasureBuilder() {
            super(android.text.style.TtsSpan.TYPE_MEASURE);
        }

        public android.text.style.TtsSpan.MeasureBuilder setNumber(long j) {
            return setNumber(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MeasureBuilder setNumber(java.lang.String str) {
            return (android.text.style.TtsSpan.MeasureBuilder) setStringArgument(android.text.style.TtsSpan.ARG_NUMBER, str);
        }

        public android.text.style.TtsSpan.MeasureBuilder setIntegerPart(long j) {
            return setIntegerPart(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MeasureBuilder setIntegerPart(java.lang.String str) {
            return (android.text.style.TtsSpan.MeasureBuilder) setStringArgument(android.text.style.TtsSpan.ARG_INTEGER_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MeasureBuilder setFractionalPart(java.lang.String str) {
            return (android.text.style.TtsSpan.MeasureBuilder) setStringArgument(android.text.style.TtsSpan.ARG_FRACTIONAL_PART, str);
        }

        public android.text.style.TtsSpan.MeasureBuilder setNumerator(long j) {
            return setNumerator(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MeasureBuilder setNumerator(java.lang.String str) {
            return (android.text.style.TtsSpan.MeasureBuilder) setStringArgument(android.text.style.TtsSpan.ARG_NUMERATOR, str);
        }

        public android.text.style.TtsSpan.MeasureBuilder setDenominator(long j) {
            return setDenominator(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MeasureBuilder setDenominator(java.lang.String str) {
            return (android.text.style.TtsSpan.MeasureBuilder) setStringArgument(android.text.style.TtsSpan.ARG_DENOMINATOR, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MeasureBuilder setUnit(java.lang.String str) {
            return (android.text.style.TtsSpan.MeasureBuilder) setStringArgument(android.text.style.TtsSpan.ARG_UNIT, str);
        }
    }

    public static class TimeBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.TimeBuilder> {
        public TimeBuilder() {
            super(android.text.style.TtsSpan.TYPE_TIME);
        }

        public TimeBuilder(int i, int i2) {
            this();
            setHours(i);
            setMinutes(i2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.TimeBuilder setHours(int i) {
            return (android.text.style.TtsSpan.TimeBuilder) setIntArgument(android.text.style.TtsSpan.ARG_HOURS, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.TimeBuilder setMinutes(int i) {
            return (android.text.style.TtsSpan.TimeBuilder) setIntArgument(android.text.style.TtsSpan.ARG_MINUTES, i);
        }
    }

    public static class DateBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.DateBuilder> {
        public DateBuilder() {
            super(android.text.style.TtsSpan.TYPE_DATE);
        }

        public DateBuilder(java.lang.Integer num, java.lang.Integer num2, java.lang.Integer num3, java.lang.Integer num4) {
            this();
            if (num != null) {
                setWeekday(num.intValue());
            }
            if (num2 != null) {
                setDay(num2.intValue());
            }
            if (num3 != null) {
                setMonth(num3.intValue());
            }
            if (num4 != null) {
                setYear(num4.intValue());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.DateBuilder setWeekday(int i) {
            return (android.text.style.TtsSpan.DateBuilder) setIntArgument(android.text.style.TtsSpan.ARG_WEEKDAY, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.DateBuilder setDay(int i) {
            return (android.text.style.TtsSpan.DateBuilder) setIntArgument(android.text.style.TtsSpan.ARG_DAY, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.DateBuilder setMonth(int i) {
            return (android.text.style.TtsSpan.DateBuilder) setIntArgument(android.text.style.TtsSpan.ARG_MONTH, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.DateBuilder setYear(int i) {
            return (android.text.style.TtsSpan.DateBuilder) setIntArgument(android.text.style.TtsSpan.ARG_YEAR, i);
        }
    }

    public static class MoneyBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.MoneyBuilder> {
        public MoneyBuilder() {
            super(android.text.style.TtsSpan.TYPE_MONEY);
        }

        public android.text.style.TtsSpan.MoneyBuilder setIntegerPart(long j) {
            return setIntegerPart(java.lang.String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MoneyBuilder setIntegerPart(java.lang.String str) {
            return (android.text.style.TtsSpan.MoneyBuilder) setStringArgument(android.text.style.TtsSpan.ARG_INTEGER_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MoneyBuilder setFractionalPart(java.lang.String str) {
            return (android.text.style.TtsSpan.MoneyBuilder) setStringArgument(android.text.style.TtsSpan.ARG_FRACTIONAL_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MoneyBuilder setCurrency(java.lang.String str) {
            return (android.text.style.TtsSpan.MoneyBuilder) setStringArgument(android.text.style.TtsSpan.ARG_CURRENCY, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.MoneyBuilder setQuantity(java.lang.String str) {
            return (android.text.style.TtsSpan.MoneyBuilder) setStringArgument(android.text.style.TtsSpan.ARG_QUANTITY, str);
        }
    }

    public static class TelephoneBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.TelephoneBuilder> {
        public TelephoneBuilder() {
            super(android.text.style.TtsSpan.TYPE_TELEPHONE);
        }

        public TelephoneBuilder(java.lang.String str) {
            this();
            setNumberParts(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.TelephoneBuilder setCountryCode(java.lang.String str) {
            return (android.text.style.TtsSpan.TelephoneBuilder) setStringArgument(android.text.style.TtsSpan.ARG_COUNTRY_CODE, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.TelephoneBuilder setNumberParts(java.lang.String str) {
            return (android.text.style.TtsSpan.TelephoneBuilder) setStringArgument(android.text.style.TtsSpan.ARG_NUMBER_PARTS, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.TelephoneBuilder setExtension(java.lang.String str) {
            return (android.text.style.TtsSpan.TelephoneBuilder) setStringArgument(android.text.style.TtsSpan.ARG_EXTENSION, str);
        }
    }

    public static class ElectronicBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.ElectronicBuilder> {
        public ElectronicBuilder() {
            super(android.text.style.TtsSpan.TYPE_ELECTRONIC);
        }

        public android.text.style.TtsSpan.ElectronicBuilder setEmailArguments(java.lang.String str, java.lang.String str2) {
            return setDomain(str2).setUsername(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setProtocol(java.lang.String str) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setStringArgument(android.text.style.TtsSpan.ARG_PROTOCOL, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setUsername(java.lang.String str) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setStringArgument(android.text.style.TtsSpan.ARG_USERNAME, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setPassword(java.lang.String str) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setStringArgument(android.text.style.TtsSpan.ARG_PASSWORD, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setDomain(java.lang.String str) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setStringArgument(android.text.style.TtsSpan.ARG_DOMAIN, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setPort(int i) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setIntArgument(android.text.style.TtsSpan.ARG_PORT, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setPath(java.lang.String str) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setStringArgument(android.text.style.TtsSpan.ARG_PATH, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setQueryString(java.lang.String str) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setStringArgument(android.text.style.TtsSpan.ARG_QUERY_STRING, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.ElectronicBuilder setFragmentId(java.lang.String str) {
            return (android.text.style.TtsSpan.ElectronicBuilder) setStringArgument(android.text.style.TtsSpan.ARG_FRAGMENT_ID, str);
        }
    }

    public static class DigitsBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.DigitsBuilder> {
        public DigitsBuilder() {
            super(android.text.style.TtsSpan.TYPE_DIGITS);
        }

        public DigitsBuilder(java.lang.String str) {
            this();
            setDigits(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.DigitsBuilder setDigits(java.lang.String str) {
            return (android.text.style.TtsSpan.DigitsBuilder) setStringArgument(android.text.style.TtsSpan.ARG_DIGITS, str);
        }
    }

    public static class VerbatimBuilder extends android.text.style.TtsSpan.SemioticClassBuilder<android.text.style.TtsSpan.VerbatimBuilder> {
        public VerbatimBuilder() {
            super(android.text.style.TtsSpan.TYPE_VERBATIM);
        }

        public VerbatimBuilder(java.lang.String str) {
            this();
            setVerbatim(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.text.style.TtsSpan.VerbatimBuilder setVerbatim(java.lang.String str) {
            return (android.text.style.TtsSpan.VerbatimBuilder) setStringArgument(android.text.style.TtsSpan.ARG_VERBATIM, str);
        }
    }
}
