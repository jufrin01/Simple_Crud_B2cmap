package idb2camp.b2campjufrin.constant;

public enum GlobalMessage {

    SUCCESS(CaseCode.SUCCESS, "Sukses"),
    ERROR(CaseCode.UNDEFINED_ERROR , "error Proses") ,
    DATA_NOT_FOUND(CaseCode.INVALID_REQUEST, "Data tidak ditemukan"),
    DATA_RELATION_NOT_FOUND(CaseCode.INVALID_REQUEST, "data relasi tidak ditemukan"),
    DATA_ALREADY_EXIST(CaseCode.IDENTICAL_OLD_VALUE_AND_NEW_VALUE, "Data sudah ada"),
    DATA_WITH_SAME_PROPERTY_ALREADY_EXIST(CaseCode.IDENTICAL_OLD_VALUE_AND_NEW_VALUE, "yang sama sudah ada dalam database"),
    QUERY_PARAMETER_NOT_VALID(CaseCode.INVALID_REQUEST, "Parameter HTTP request tidak sesuai"),
    CONTACT_OUR_TEAM(CaseCode.UNDEFINED_ERROR, "Hubungi tim kami dan kirim kode ini ke tim kami : "),
    UNAUTHORIZED(CaseCode.INVALID_ACCESS, "User yang sedang login tidak punya otoritas untuk mengakses data"),
    FILE_MUST_BE_INCLUDED(CaseCode.INVALID_REQUEST, "File tidak boleh kosong"),
    INVALID_FIELD_FORMAT(CaseCode.INVALID_REQUEST, "Invalid Field Format "),
    INVALID_MANDATORY_FIELD(CaseCode.INVALID_REQUEST, "Invalid Mandatory Field "),
    INVALID_PARAMETER_FORMAT(CaseCode.INVALID_REQUEST, "Invalid Parameter Format "),
    INVALID_MANDATORY_PARAMETER(CaseCode.INVALID_REQUEST, "Invalid Mandatory Parameter "),
    INVALID_PATH_VARIABLE_FORMAT(CaseCode.INVALID_REQUEST, "Invalid Path Variable Format "),
    DEPARTMENT_NOT_FOUND(CaseCode.INVALID_REQUEST, "Department not found with id: "),
    DOCUMENT_FILE_NOT_SUPPORTED(CaseCode.INVALID_REQUEST, "dokumen tidak mendukung :");

    public final String code;
    public final String message;

    GlobalMessage(CaseCode caseCode, String message) {
        this.code = caseCode.code;
        this.message = message;
    }

}
