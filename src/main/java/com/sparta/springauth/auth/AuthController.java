package com.sparta.springauth.auth;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

  @GetMapping("/create-session")
  public String createSession(HttpServletRequest req) {
    // 세션이 존재할 경우 세션 반환, 없을 경우 새로운 세션을 생성한 후 반환
    HttpSession session = req.getSession(true);

    // 세션에 저장될 정보 Name - Value를 추가합니다.
    session.setAttribute(AUTH_TOKEN_HEADER, "Yeon Auth");

    return "createSession";
  }

  @GetMapping("/get-session")
  public String getSession(HttpServletRequest req) {
    // 세션이 존재할 경우 세션 반환, 없을 경우 null 반환
    HttpSession session = req.getSession(false);

    // 세션에서 AUTH_TOKEN_HEADER 이름으로 저장된 값을 꺼냅니다.
    String value = (String) session.getAttribute(AUTH_TOKEN_HEADER); // 가져온 세션에 저장된 Value를 Name을 사용하여 가져옵니다.
    System.out.println("value: " + value);

    return "getSession : " + value;
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
