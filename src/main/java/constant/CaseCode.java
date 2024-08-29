package idb2camp.b2campjufrin.constant;

public enum CaseCode {
    SUCCESS("00", "Sukses"),
    SYSTEM_MALFUNCTION("01", "System Malfunction"),
    INVALID_CREDENTIAL("02", "Invalid Credential"),
    INVALID_ACCESS("03", "Invalid Access"),
    INVALID_REQUEST("04", "Invalid Request"),
    INVALID_CUSTOMER("05", "Invalid Customer"),
    TIMEOUT("06", "Timeout"),
    INVALID_CURRENCY("07", "Invalid Currency"),
    GENERAL_BUSINESS_ERROR("08", "General Business Error"),
    UNDEFINED_ERROR("99", "Undefined Error"),

    INVALID_OLD_VALUE("A0", "Invalid old value"),
    IDENTICAL_OLD_VALUE_AND_NEW_VALUE("A1", "Identical old and new value"),
    ;

    public final String code;
    public final String desc;

    CaseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
