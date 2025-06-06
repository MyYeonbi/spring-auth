package com.sparta.springauth.auth;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

  public static final String AUTH_TOKEN_HEADER = "Authorization";

  @GetMapping("/create-cookie")
  public String createCookie(HttpServletResponse res) {
    addCookie("Yeon Auth",res);

    return "createCookie";
  }

  @GetMapping("/get-cookie")
  public String getCookie(@CookieValue(AUTH_TOKEN_HEADER
  ) String value) {
    System.out.println("value: " + value);

    return "getCookie : " + value;
  }

  public static void addCookie(String cookieValue, HttpServletResponse res) {
    try {
      cookieValue = URLEncoder.encode(cookieValue, "utf-8").replaceAll("\\+", "%20"); // Cookie Value에서는 공백이 았으면 안되서 인코딩이 필요함

      Cookie cookie = new Cookie(AUTH_TOKEN_HEADER
          , cookieValue);
      cookie.setPath("/");
      cookie.setMaxAge(30 * 60);

      // Response 객체에 Cookie 추가
      res.addCookie(cookie);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }


}
