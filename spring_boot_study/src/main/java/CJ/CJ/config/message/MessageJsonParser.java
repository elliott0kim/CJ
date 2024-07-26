package CJ.CJ.config.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
@Configuration
@Component
public class MessageJsonParser {
    private JSONObject messageArray;

    @Autowired
    private MessageComponent messageComponent;
    public MessageJsonParser(MessageComponent messageComponent) throws IOException, ParseException
    {
        this.messageComponent = messageComponent;
        JSONParser parser = new JSONParser();
        // JSON file read
        //Reader reader = new FileReader("/Users/kimdonghyun/Desktop/sumichan/message.json");
        // 하드 코딩인데이거... 시발 이걸 절대경로로 박아두네ㅠ 쩝....

        Reader reader = new FileReader("/home/ubuntu/rest_api_server/CJ/CJ/spring_boot_study/message.json");
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
