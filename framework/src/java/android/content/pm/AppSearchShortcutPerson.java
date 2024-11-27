package android.content.pm;

/* loaded from: classes.dex */
public class AppSearchShortcutPerson extends android.app.appsearch.GenericDocument {
    private static final java.lang.String KEY_ICON = "icon";
    private static final java.lang.String KEY_KEY = "key";
    private static final java.lang.String KEY_NAME = "name";
    public static final java.lang.String SCHEMA_TYPE = "ShortcutPerson";
    private static final java.lang.String KEY_IS_BOT = "isBot";
    private static final java.lang.String KEY_IS_IMPORTANT = "isImportant";
    public static final android.app.appsearch.AppSearchSchema SCHEMA = new android.app.appsearch.AppSearchSchema.Builder(SCHEMA_TYPE).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder("key").setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new android.app.appsearch.AppSearchSchema.BooleanPropertyConfig.Builder(KEY_IS_BOT).setCardinality(3).build()).addProperty(new android.app.appsearch.AppSearchSchema.BooleanPropertyConfig.Builder(KEY_IS_IMPORTANT).setCardinality(3).build()).addProperty(new android.app.appsearch.AppSearchSchema.BytesPropertyConfig.Builder("icon").setCardinality(2).build()).build();

    public AppSearchShortcutPerson(android.app.appsearch.GenericDocument genericDocument) {
        super(genericDocument);
    }

    public static android.content.pm.AppSearchShortcutPerson instance(android.app.Person person) {
        java.lang.String obj;
        java.util.Objects.requireNonNull(person);
        if (person.getUri() != null) {
            obj = person.getUri();
        } else {
            obj = java.util.UUID.randomUUID().toString();
        }
        return new android.content.pm.AppSearchShortcutPerson.Builder(obj).setName(person.getName()).setKey(person.getKey()).setIsBot(person.isBot()).setIsImportant(person.isImportant()).setIcon(transformToByteArray(person.getIcon())).build();
    }

    public android.app.Person toPerson() {
        java.lang.String str;
        try {
            str = android.net.UriCodec.decode(getId(), false, java.nio.charset.StandardCharsets.UTF_8, true);
        } catch (java.lang.IllegalArgumentException e) {
            str = null;
        }
        return new android.app.Person.Builder().setName(getPropertyString("name")).setUri(str).setKey(getPropertyString("key")).setBot(getPropertyBoolean(KEY_IS_BOT)).setImportant(getPropertyBoolean(KEY_IS_IMPORTANT)).setIcon(transformToIcon(getPropertyBytes("icon"))).build();
    }

    public static class Builder extends android.app.appsearch.GenericDocument.Builder<android.content.pm.AppSearchShortcutPerson.Builder> {
        public Builder(java.lang.String str) {
            super("", str, android.content.pm.AppSearchShortcutPerson.SCHEMA_TYPE);
        }

        public android.content.pm.AppSearchShortcutPerson.Builder setName(java.lang.CharSequence charSequence) {
            if (charSequence != null) {
                setPropertyString("name", charSequence.toString());
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutPerson.Builder setKey(java.lang.String str) {
            if (str != null) {
                setPropertyString("key", str);
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutPerson.Builder setIsBot(boolean z) {
            setPropertyBoolean(android.content.pm.AppSearchShortcutPerson.KEY_IS_BOT, z);
            return this;
        }

        public android.content.pm.AppSearchShortcutPerson.Builder setIsImportant(boolean z) {
            setPropertyBoolean(android.content.pm.AppSearchShortcutPerson.KEY_IS_IMPORTANT, z);
            return this;
        }

        public android.content.pm.AppSearchShortcutPerson.Builder setIcon(byte[] bArr) {
            if (bArr != null) {
                setPropertyBytes("icon", bArr);
            }
            return this;
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public android.content.pm.AppSearchShortcutPerson build() {
            return new android.content.pm.AppSearchShortcutPerson(super.build());
        }
    }

    private static byte[] transformToByteArray(android.graphics.drawable.Icon icon) {
        if (icon == null) {
            return null;
        }
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                icon.writeToStream(byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private android.graphics.drawable.Icon transformToIcon(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                android.graphics.drawable.Icon createFromStream = android.graphics.drawable.Icon.createFromStream(byteArrayInputStream);
                byteArrayInputStream.close();
                return createFromStream;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }
}
