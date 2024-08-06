package CJ.CJ.config.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
@Configuration
@Component
public class MessageJsonParser {
    private JSONObject messageArray;
    private String messagePath;
    @Autowired
    private MessageComponent messageComponent;
    public MessageJsonParser(MessageComponent messageComponent, @Value("${message.path}") String messagePath) throws IOException, ParseException
    {
        this.messageComponent = messageComponent;
        JSONParser parser = new JSONParser();

        // 하드 코딩인데이거... 시발 이걸 절대경로로 박아두네ㅠ 쩝....
        Reader reader = new FileReader(messagePath);
        messageArray = (JSONObject) parser.parse(reader);
        this.setMessageData();
    }

    public void setMessageData()
    {
        messageComponent.setSUCCESS((String) messageArray.get("SUCCESS"));
        messageComponent.setUNKNOWN_ERROR((String) messageArray.get("UNKNOWN_ERROR"));
        messageComponent.setDUPLICATED_NAME((String) messageArray.get("DUPLICATED_NAME"));
        messageComponent.setINVALID_PASSWORD((String) messageArray.get("INVALID_PASSWORD"));
        messageComponent.setNO_DATA((String) messageArray.get("NO_DATA"));
        messageComponent.setINVALID_ACCESS((String) messageArray.get("INVALID_ACCESS"));
        messageComponent.setEXPIRED_TOKEN((String) messageArray.get("EXPIRED_TOKEN"));
    }
}
