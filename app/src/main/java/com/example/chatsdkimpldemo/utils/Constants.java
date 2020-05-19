package com.example.chatsdkimpldemo.utils;

public final class Constants {

    public static class BundleKeys {
        public static final String IS_GROUP = "is_group";
        public static final String CHATROOM_ID = "chatroom_id";
        public static final String CHATROOM_NAME = "chatroom_name";
        public static final String OTHER_USER_ID = "other_user_id";
        public static final String OTHER_USER_NAME = "other_user_name";
        public static final String SELECTED_USERS = "selected_users";
        public static final String PHOTO_FILE = "photo_file";
        public static final String SHARED_FILE_URI = "shared_file_uri";
        public static final String SHARED_TEXT = "shared_text";
        public static final String IS_TEXT_SHARED = "is_text_shared";
        public static final String IS_FROM_SHARING = "is_from_sharing";
    }

    public static class RequestCodes {
        public static final int OPEN_DOCUMENT = 1111;
        public static final int OPEN_CAMERA = 1112;
    }

    public static class PermissionCodes {
        public static final int RECORD_AUDIO = 1000;
        public static final int WRITE_EXTERNAL_STORAGE = 1001;
    }

    public enum MediaIdentifier {

        IMAGE("image"),
        VIDEO("video"),
        AUDIO("audio"),
        DOCUMENT("application");
        private String value;

        MediaIdentifier(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ViewHolderIdentifier {
        SENDER_TYPE1(1),
        SENDER_TYPE2(2),
        RECEIVER_TYPE1(3),
        RECEIVER_TYPE2(4),
        SENDER_MEDIA_TYPE_IV1(5),
        SENDER_MEDIA_TYPE_IV2(6),
        RECEIVER_MEDIA_TYPE_IV1(7),
        RECEIVER_MEDIA_TYPE_IV2(8),
        SENDER_MEDIA_DOCTYPE1(9),
        SENDER_MEDIA_DOCTYPE2(10),
        RECEIVER_MEDIA_DOCTYPE1(11),
        RECEIVER_MEDIA_DOCTYPE2(12),
        SENDER_MEDIA_AUDIO1(13),
        SENDER_MEDIA_AUDIO2(14),
        RECEIVER_MEDIA_AUDIO1(15),
        RECEIVER_MEDIA_AUDIO2(16);

        private int value;

        ViewHolderIdentifier(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
