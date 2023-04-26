package com.progcreek.otpapplication.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger log = LogManager.getLogger(SmsServiceImpl.class);
    private final static String ACCOUNT_SID = "AC9a4b83633a991dcd60d3306fcbfa32bd";
    private final static String AUTH_ID = "b4be8053d8dd6a5454256be7f6f2a707";
    private final static String SENDER_NO = "+13238701569";

    @PostConstruct
    void init() {
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }

    @Async
    @Override
    public boolean sendSms(String recipientPhone, String otpMsg) throws IOException {
        try {
            Message message = Message.creator(
                            new PhoneNumber(recipientPhone),
                            new PhoneNumber(SENDER_NO),
                            otpMsg)
                    .create();
            log.info("SMS successfully sent! Recipient: {}", recipientPhone);
            return true;

        } catch (Exception ex) {
            log.error("error {}", ex);
        }
        return false;
    }
}
