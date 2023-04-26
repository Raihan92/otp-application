package com.progcreek.otpapplication.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Component
public class OneTimePasswordHelper {

    private final static Logger log = LogManager.getLogger(OneTimePasswordHelper.class);

    private static final Integer rndNumLength = 6;

    public Integer createRandomOneTimePassword() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=1; i<=rndNumLength; i++) {
            int randomNumber = random.nextInt(10);
            stringBuilder.append(randomNumber);
        }
        return Integer.parseInt(stringBuilder.toString().trim());
    }

    public static String getMessageBody(String otp, Date expiresOn) throws IOException {
        try {
            String messageBody = "One Time Password (OTP) has been generated.\n" +
                    "OTP for your transaction is: " + otp + "\n" +
                    "The OTP will be Valid till: " + expiresOn + "\n" +
                    "Please do not share OTP with anyone.";
            return messageBody;
        } catch (Exception ex) {
            log.error("error: {}", ex);
            throw new IOException(ex);
        }
    }

}
