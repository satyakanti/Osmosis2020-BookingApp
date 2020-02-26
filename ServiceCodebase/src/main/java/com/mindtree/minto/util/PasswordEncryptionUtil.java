/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 
 * PasswordEncryptionUtil.java Created On: Feb 22, 2020 Created By: M1026329
 */
public class PasswordEncryptionUtil {

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param password2
     * @return
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, ConfigReader.getPwdEncryption());
    }

}
