package idb2camp.b2campjufrin.expection;

import idb2camp.b2campjufrin.constant.GlobalMessage;

public class BusinessException extends RuntimeException{

    private final String message;
    public BusinessException(String customMessage) {
        super(customMessage);
        this.message = customMessage;
    }
    public BusinessException(GlobalMessage globalMessage) {
        super(globalMessage.message);
        this.message = globalMessage.message;
    }
    public static BusinessException dataNotFound() {
        return new BusinessException(GlobalMessage.DATA_NOT_FOUND);
    }
    public BusinessException(GlobalMessage globalMessage, String suffixAdditionalMessage) {
        super(globalMessage.message + " " + suffixAdditionalMessage);
        this.message = globalMessage.message + " " + suffixAdditionalMessage;
    }
    public BusinessException(String prefixAdditionalMessage, GlobalMessage globalMessage) {
        super(globalMessage.message + " " + prefixAdditionalMessage);
        this.message = prefixAdditionalMessage + " " + globalMessage.message;
    }
    public static BusinessException dataRelationNotFound(String entity) {
        return new BusinessException(entity, GlobalMessage.DATA_RELATION_NOT_FOUND);
    }
    public static BusinessException dataWithSamePropertyAlreadyExist(String property, String value) {
        return new BusinessException("Data yang sama dengan " + property + " " + value + " sudah ada dalam database");
    }
}
