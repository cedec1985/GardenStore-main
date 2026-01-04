/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package app.MyBackendApp.main.config;

import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;

public interface JWTService {
    String generateJwt(String username) throws ParseException;
    Authentication validateJwt(String jwt);
}
