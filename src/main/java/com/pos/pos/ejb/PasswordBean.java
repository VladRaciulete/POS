package com.pos.pos.ejb;

import jakarta.ejb.Stateless;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PasswordBean {
    private static final Logger LOG = Logger.getLogger(PasswordBean.class.getName());

    public String convertToSha256(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte[] digest = messageDigest.digest();
            String result = new BigInteger(1, digest).toString(16);
            return result;
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, "Could not convert password", ex);
        }
        return null;
    }
}
