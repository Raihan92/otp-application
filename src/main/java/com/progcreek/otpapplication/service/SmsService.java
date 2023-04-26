package com.progcreek.otpapplication.service;

import java.io.IOException;
import java.util.Date;

public interface SmsService {

    boolean sendSms(String recipientPhone, String otpMsg) throws IOException;

}
