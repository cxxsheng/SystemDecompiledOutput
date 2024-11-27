package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
public class TestModelEnrollmentDatabase implements com.android.server.voiceinteraction.IEnrolledModelDb {
    private final java.util.Map<com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey, android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel> mModelMap = new java.util.HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    static final class EnrollmentKey {
        private final int mKeyphraseId;
        private final java.lang.String mLocale;
        private final java.util.List<java.lang.Integer> mUserIds;

        EnrollmentKey(int i, @android.annotation.NonNull java.util.List<java.lang.Integer> list, @android.annotation.NonNull java.lang.String str) {
            this.mKeyphraseId = i;
            java.util.Objects.requireNonNull(list);
            this.mUserIds = list;
            java.util.Objects.requireNonNull(str);
            this.mLocale = str;
        }

        int keyphraseId() {
            return this.mKeyphraseId;
        }

        java.util.List<java.lang.Integer> userIds() {
            return this.mUserIds;
        }

        java.lang.String locale() {
            return this.mLocale;
        }

        public java.lang.String toString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
            stringJoiner.add("keyphraseId: " + this.mKeyphraseId);
            stringJoiner.add("userIds: " + this.mUserIds.toString());
            stringJoiner.add("locale: " + this.mLocale.toString());
            return "EnrollmentKey: " + stringJoiner.toString();
        }

        public int hashCode() {
            return ((((this.mKeyphraseId + 31) * 31) + this.mUserIds.hashCode()) * 31) + this.mLocale.hashCode();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey)) {
                return false;
            }
            com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey enrollmentKey = (com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey) obj;
            if (this.mKeyphraseId == enrollmentKey.mKeyphraseId && this.mUserIds.equals(enrollmentKey.mUserIds) && this.mLocale.equals(enrollmentKey.mLocale)) {
                return true;
            }
            return false;
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public boolean updateKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        android.hardware.soundtrigger.SoundTrigger.Keyphrase keyphrase = keyphraseSoundModel.getKeyphrases()[0];
        this.mModelMap.put(new com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey(keyphrase.getId(), java.util.Arrays.stream(keyphrase.getUsers()).boxed().toList(), keyphrase.getLocale().toLanguageTag()), keyphraseSoundModel);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$deleteKeyphraseSoundModel$0(int i, java.lang.String str, int i2, com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey enrollmentKey) {
        return enrollmentKey.keyphraseId() == i && enrollmentKey.locale().equals(str) && enrollmentKey.userIds().contains(java.lang.Integer.valueOf(i2));
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public boolean deleteKeyphraseSoundModel(final int i, final int i2, final java.lang.String str) {
        return this.mModelMap.keySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.voiceinteraction.TestModelEnrollmentDatabase$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$deleteKeyphraseSoundModel$0;
                lambda$deleteKeyphraseSoundModel$0 = com.android.server.voiceinteraction.TestModelEnrollmentDatabase.lambda$deleteKeyphraseSoundModel$0(i, str, i2, (com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey) obj);
                return lambda$deleteKeyphraseSoundModel$0;
            }
        });
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(final int i, final int i2, final java.lang.String str) {
        return (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) this.mModelMap.entrySet().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.voiceinteraction.TestModelEnrollmentDatabase$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getKeyphraseSoundModel$1;
                lambda$getKeyphraseSoundModel$1 = com.android.server.voiceinteraction.TestModelEnrollmentDatabase.lambda$getKeyphraseSoundModel$1(i, str, i2, (java.util.Map.Entry) obj);
                return lambda$getKeyphraseSoundModel$1;
            }
        }).findFirst().map(new java.util.function.Function() { // from class: com.android.server.voiceinteraction.TestModelEnrollmentDatabase$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel lambda$getKeyphraseSoundModel$2;
                lambda$getKeyphraseSoundModel$2 = com.android.server.voiceinteraction.TestModelEnrollmentDatabase.lambda$getKeyphraseSoundModel$2((java.util.Map.Entry) obj);
                return lambda$getKeyphraseSoundModel$2;
            }
        }).orElse(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getKeyphraseSoundModel$1(int i, java.lang.String str, int i2, java.util.Map.Entry entry) {
        return ((com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).keyphraseId() == i && ((com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).locale().equals(str) && ((com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).userIds().contains(java.lang.Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel lambda$getKeyphraseSoundModel$2(java.util.Map.Entry entry) {
        return (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) entry.getValue();
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(final java.lang.String str, final int i, final java.lang.String str2) {
        return (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) this.mModelMap.entrySet().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.voiceinteraction.TestModelEnrollmentDatabase$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getKeyphraseSoundModel$3;
                lambda$getKeyphraseSoundModel$3 = com.android.server.voiceinteraction.TestModelEnrollmentDatabase.lambda$getKeyphraseSoundModel$3(str, str2, i, (java.util.Map.Entry) obj);
                return lambda$getKeyphraseSoundModel$3;
            }
        }).findFirst().map(new java.util.function.Function() { // from class: com.android.server.voiceinteraction.TestModelEnrollmentDatabase$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel lambda$getKeyphraseSoundModel$4;
                lambda$getKeyphraseSoundModel$4 = com.android.server.voiceinteraction.TestModelEnrollmentDatabase.lambda$getKeyphraseSoundModel$4((java.util.Map.Entry) obj);
                return lambda$getKeyphraseSoundModel$4;
            }
        }).orElse(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getKeyphraseSoundModel$3(java.lang.String str, java.lang.String str2, int i, java.util.Map.Entry entry) {
        return ((android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) entry.getValue()).getKeyphrases()[0].getText().equals(str) && ((com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).locale().equals(str2) && ((com.android.server.voiceinteraction.TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).userIds().contains(java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel lambda$getKeyphraseSoundModel$4(java.util.Map.Entry entry) {
        return (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) entry.getValue();
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Using test enrollment database, with enrolled models:");
        printWriter.println(this.mModelMap);
    }
}
