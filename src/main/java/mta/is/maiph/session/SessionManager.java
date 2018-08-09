/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.session;

import java.util.HashMap;
import java.util.Map;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.exception.ApplicationException;

/**
 *
 * @author MaiPH
 */
public class SessionManager {

    private static final Map<String, String> pool = new HashMap<>();

    public static void add(String token, String userId) {
        pool.put(token, userId);
    }

    public static String check(String token) throws ApplicationException {
        if (pool.containsKey(token)) {
            return pool.get(token);
        }
        throw new ApplicationException(ErrorCode.TOKEN_EXPIRE);
    }
}
