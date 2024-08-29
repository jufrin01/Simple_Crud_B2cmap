package idb2camp.b2campjufrin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailSenderRequest {

    private String destinationMail;
    private String subject;
    private String recipientName;
    private String message;
    private Map<String, String> cardMap;

}

