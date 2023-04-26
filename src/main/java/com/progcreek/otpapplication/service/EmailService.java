package com.progcreek.otpapplication.service;

import java.io.IOException;

public interface EmailService {
    boolean sendEmail(String email, String subject, String messageBody);
}
