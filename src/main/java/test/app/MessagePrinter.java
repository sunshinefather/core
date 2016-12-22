package test.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.service.MessageService;

@Component
public class MessagePrinter {

    private final MessageService service;
    
    @Autowired
    public MessagePrinter(MessageService mockMessageService) {
        service = mockMessageService;
    }
    
    public void printMessage() {
        System.out.println(this.service.getMessage());
    }
}
