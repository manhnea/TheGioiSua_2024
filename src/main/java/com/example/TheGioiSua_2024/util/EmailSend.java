package com.example.TheGioiSua_2024.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSend {

  private final JavaMailSender mailSender;

  public void sendVerificationEmail(String email, String token) {
    String subject = "Xác minh tài khoản của bạn";
    String verificationUrl = "http://160.30.21.47:3000/login/" + token;
    String message = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "\n" +
        "    <!-- Google Font css -->\n" +
        "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com/\">\n" +
        "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css2?family=Public+Sans:wght@100;200;300;400;500;600;700;800;900&display=swap\">\n"
        +
        "    <link href=\"https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200;300;400;600;700;800;900&display=swap\" rel=\"stylesheet\">\n"
        +
        "\n" +
        "    <style type=\"text/css\">\n" +
        "        body {\n" +
        "            text-align: center;\n" +
        "            margin: 0 auto;\n" +
        "            width: 650px;\n" +
        "            font-family: 'Public Sans', sans-serif;\n" +
        "            background-color: #e2e2e2;\n" +
        "            display: block;\n" +
        "        }\n" +
        "\n" +
        "        .mb-3 {\n" +
        "            margin-bottom: 30px;\n" +
        "        }\n" +
        "\n" +
        "        ul {\n" +
        "            margin: 0;\n" +
        "            padding: 0;\n" +
        "        }\n" +
        "\n" +
        "        li {\n" +
        "            display: inline-block;\n" +
        "            text-decoration: unset;\n" +
        "        }\n" +
        "\n" +
        "        a {\n" +
        "            text-decoration: none;\n" +
        "        }\n" +
        "\n" +
        "        h5 {\n" +
        "            margin: 10px;\n" +
        "            color: #777;\n" +
        "        }\n" +
        "\n" +
        "        .text-center {\n" +
        "            text-align: center;\n" +
        "        }\n" +
        "\n" +
        "        .header-menu ul li+li {\n" +
        "            margin-left: 20px;\n" +
        "        }\n" +
        "\n" +
        "        .header-menu ul li a {\n" +
        "            font-size: 14px;\n" +
        "            color: #252525;\n" +
        "            font-weight: 500;\n" +
        "        }\n" +
        "\n" +
        "        .password-button {\n" +
        "            background-color: #0DA487;\n" +
        "            border: none;\n" +
        "            color: #fff;\n" +
        "            padding: 14px 26px;\n" +
        "            font-size: 18px;\n" +
        "            border-radius: 6px;\n" +
        "            font-weight: 700;\n" +
        "            font-family: 'Nunito Sans', sans-serif;\n" +
        "        }\n" +
        "\n" +
        "        .footer-table {\n" +
        "            position: relative;\n" +
        "        }\n" +
        "\n" +
        "        .footer-table::before {\n" +
        "            position: absolute;\n" +
        "            content: \"\";\n" +
        "            background-image: url(images/footer-left.svg);\n" +
        "            background-position: top right;\n" +
        "            top: 0;\n" +
        "            left: -71%;\n" +
        "            width: 100%;\n" +
        "            height: 100%;\n" +
        "            background-repeat: no-repeat;\n" +
        "            z-index: -1;\n" +
        "            background-size: contain;\n" +
        "            opacity: 0.3;\n" +
        "        }\n" +
        "\n" +
        "        .footer-table::after {\n" +
        "            position: absolute;\n" +
        "            content: \"\";\n" +
        "            background-image: url(images/footer-right.svg);\n" +
        "            background-position: top right;\n" +
        "            top: 0;\n" +
        "            right: 0;\n" +
        "            width: 100%;\n" +
        "            height: 100%;\n" +
        "            background-repeat: no-repeat;\n" +
        "            z-index: -1;\n" +
        "            background-size: contain;\n" +
        "            opacity: 0.3;\n" +
        "        }\n" +
        "\n" +
        "        .theme-color {\n" +
        "            color: #0DA487;\n" +
        "        }\n" +
        "    </style>\n" +
        "</head>\n" +
        "\n" +
        "<body style=\"margin: 20px auto;\">\n" +
        "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: white; width: 100%; box-shadow: 0px 0px 14px -4px rgba(0, 0, 0, 0.27);\">\n"
        +
        "        <tbody>\n" +
        "            <tr>\n" +
        "                <td>\n" +
        "                    <table class=\"content-table\" style=\"margin-bottom: -6px;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
        +
        "                        <thead>\n" +
        "                            <tr>\n" +
        "                                <td>\n" +
        "                                    <img src=\"https://i.ibb.co/6ZR8nsm/welcome-poster.jpg\" alt=\"Welcome Poster\" style=\"display: block; margin: 0 auto;\">\n"
        +
        "                                </td>\n" +
        "                            </tr>\n" +
        "                        </thead>\n" +
        "                    </table>\n" +
        "\n" +
        "                    <table class=\"content-table\" style=\"margin-top: 40px;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
        +
        "                        <thead>\n" +
        "                            <tr style=\"display: block;\">\n" +
        "                                <td style=\"display: block;\">\n" +
        "                                    <h3 style=\"font-weight: 700; font-size: 20px; margin: 0; text-transform: uppercase;text-align: center;\">Xin chào bạn và Chào mừng đến với SuaFpoly!</h3>\n"
        +
        "                                </td>\n" +
        "\n" +
        "                                <td>\n" +
        "                                    <p style=\"font-size: 14px; font-weight: 600; width: 82%; margin: 8px auto 0; line-height: 1.5; color: #939393; font-family: 'Nunito Sans', sans-serif;\">\n"
        +
        "                                        Chúng tôi hy vọng sản phẩm của chúng tôi sẽ dẫn bạn, như nhiều người trước bạn, đến một nơi mà ý tưởng của bạn có thể nảy sinh và phát triển. Trước khi bắt đầu, chúng tôi cần bạn xác minh email của mình.\n"
        +
        "                                    </p>\n" +
        "                                </td>\n" +
        "                            </tr>\n" +
        "                        </thead>\n" +
        "                    </table>\n" +
        "\n" +
        "                    <table class=\"button-table\" style=\"margin: 34px 0;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
        +
        "                        <thead>\n" +
        "                            <tr style=\"display: block;\">\n" +
        "                                <td style=\"display: block; text-align: center;\">\n" +
        "                    <a href=\"" + verificationUrl
        + "\" class=\"password-button\">Xác minh Email</a>\n" +
        "                                </td>\n" +
        "                            </tr>\n" +
        "                        </thead>\n" +
        "                    </table>\n" +
        "\n" +
        "                    <table class=\"content-table\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
        +
        "                        <thead>\n" +
        "                            <tr style=\"display: block;\">\n" +
        "                                <td style=\"display: block;\">\n" +
        "                                    <p style=\"font-size: 14px; font-weight: 600; width: 82%; margin: 0 auto; line-height: 1.5; color: #939393; font-family: 'Nunito Sans', sans-serif;\">\n"
        +
        "                                        Nếu bạn có bất kỳ câu hỏi nào, vui lòng gửi email cho chúng tôi tại <span class=\"theme-color\">suaFpoly</span> hoặc truy cập vào <span class=\"theme-color\">Câu hỏi thường gặp</span>. Bạn cũng có thể trò chuyện với nhân viên trong giờ làm việc để được hỗ trợ.\n"
        +
        "                                    </p>\n" +
        "                                </td>\n" +
        "                            </tr>\n" +
        "                        </thead>\n" +
        "                    </table>\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "        </tbody>\n" +
        "    </table>\n" +
        "</body>\n" +
        "</html>\n";

    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText(message, true);
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new IllegalStateException("Không thể gửi email xác minh", e);
    }
  }

  public void sendRecoveryEmail(String email, String token) {
    String subject = "Khôi phục mật khẩu của bạn";
    String recoveryUrl = "http://160.30.21.47:3000/newpass/" + token;
    String message =
        "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "\n"
            + "\n"
            + "<!-- Mirrored from themes.pixelstrap.com/fastkart/email-templete/reset-password.html by HTTrack Website Copier/3.x [XR&CO'2014], Sun, 27 Oct 2024 03:44:57 GMT -->\n"
            + "<head>\n"
            + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
            + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    <link rel=\"icon\" href=\"images/favicon.png\" type=\"image/x-icon\">\n"
            + "    <title>Fastkart | Email template </title>\n"
            + "\n"
            + "    <!-- Google Font css -->\n"
            + "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com/\">\n"
            + "    <link rel=\"stylesheet\"\n"
            + "        href=\"https://fonts.googleapis.com/css2?family=Public+Sans:wght@100;200;300;400;500;600;700;800;900&amp;display=swap\">\n"
            + "    <link href=\"https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200;300;400;600;700;800;900&amp;display=swap\"\n"
            + "        rel=\"stylesheet\">\n"
            + "\n"
            + "    <style type=\"text/css\">\n"
            + "        body {\n"
            + "            text-align: center;\n"
            + "            margin: 0 auto;\n"
            + "            width: 650px;\n"
            + "            font-family: 'Public Sans', sans-serif;\n"
            + "            background-color: #e2e2e2;\n"
            + "            display: block;\n"
            + "        }\n"
            + "\n"
            + "        ul {\n"
            + "            margin: 0;\n"
            + "            padding: 0;\n"
            + "        }\n"
            + "\n"
            + "        li {\n"
            + "            display: inline-block;\n"
            + "            text-decoration: unset;\n"
            + "        }\n"
            + "\n"
            + "        a {\n"
            + "            text-decoration: none;\n"
            + "        }\n"
            + "\n"
            + "        h5 {\n"
            + "            margin: 10px;\n"
            + "            color: #777;\n"
            + "        }\n"
            + "\n"
            + "        .text-center {\n"
            + "            text-align: center\n"
            + "        }\n"
            + "\n"
            + "        .header-menu ul li+li {\n"
            + "            margin-left: 20px;\n"
            + "        }\n"
            + "\n"
            + "        .header-menu ul li a {\n"
            + "            font-size: 14px;\n"
            + "            color: #252525;\n"
            + "            font-weight: 500;\n"
            + "        }\n"
            + "\n"
            + "        .password-button {\n"
            + "            background-color: #0DA487;\n"
            + "            border: none;\n"
            + "            color: #fff;\n"
            + "            padding: 14px 26px;\n"
            + "            font-size: 18px;\n"
            + "            border-radius: 6px;\n"
            + "            font-weight: 600;\n"
            + "        }\n"
            + "\n"
            + "        .footer-table {\n"
            + "            position: relative;\n"
            + "        }\n"
            + "\n"
            + "        .footer-table::before {\n"
            + "            position: absolute;\n"
            + "            content: \"\";\n"
            + "            background-image: url(images/footer-left.svg);\n"
            + "            background-position: top right;\n"
            + "            top: 0;\n"
            + "            left: -71%;\n"
            + "            width: 100%;\n"
            + "            height: 100%;\n"
            + "            background-repeat: no-repeat;\n"
            + "            z-index: -1;\n"
            + "            background-size: contain;\n"
            + "            opacity: 0.3;\n"
            + "        }\n"
            + "\n"
            + "        .footer-table::after {\n"
            + "            position: absolute;\n"
            + "            content: \"\";\n"
            + "            background-image: url(images/footer-right.svg);\n"
            + "            background-position: top right;\n"
            + "            top: 0;\n"
            + "            right: 0;\n"
            + "            width: 100%;\n"
            + "            height: 100%;\n"
            + "            background-repeat: no-repeat;\n"
            + "            z-index: -1;\n"
            + "            background-size: contain;\n"
            + "            opacity: 0.3;\n"
            + "        }\n"
            + "\n"
            + "        .theme-color {\n"
            + "            color: #0DA487;\n"
            + "        }\n"
            + "    </style>\n"
            + "</head>\n"
            + "\n"
            + "<body style=\"margin: 20px auto;\">\n"
            + "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n"
            + "        style=\"background-color: white; width: 100%; box-shadow: 0px 0px 14px -4px rgba(0, 0, 0, 0.2705882353);-webkit-box-shadow: 0px 0px 14px -4px rgba(0, 0, 0, 0.2705882353);\">\n"
            + "        <tbody>\n"
            + "            <tr>\n"
            + "                <td>\n"
            + "                    <table class=\"header-table\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
            + "                        <tr class=\"header\"\n"
            + "                            style=\"background-color: #f7f7f7;display: flex;align-items: center;justify-content: space-between;width: 100%;\">\n"

            + "                    </table>\n"
            + "\n"
            + "                    <table class=\"content-table\" style=\"margin-top: 40px;\" align=\"center\" border=\"0\" cellpadding=\"0\"\n"
            + "                        cellspacing=\"0\" width=\"100%\">\n"
            + "                        <thead>\n"
            + "                            <tr>\n"
            + "                                <td style=\"text-align: center;\">\n"
            + "                                    <img src=\"https://i.ibb.co/BCY0K5V/reset-1.png\" alt=\"\">\n"
            + "                                </td>\n"
            + "                            </tr>\n"
            + "                        </thead>\n"
            + "                    </table>\n"
            + "\n"
            + "                    <table class=\"content-table\" style=\"margin-top: 40px;\" align=\"center\" border=\"0\" cellpadding=\"0\"\n"
            + "                        cellspacing=\"0\" width=\"100%\">\n"
            + "                        <thead>\n"
            + "                            <tr style=\"display: block;\">\n"
            + "                                <td style=\"display: block;\">\n"
            + "                                    <h3 style=\"font-weight: 700; font-size: 20px; margin: 0;text-align: center;\">Reset Password</h3>\n"
            + "                                </td>\n"
            + "\n"
            + "                                <td style=\"display: block;\">\n"
            + "                                </td>\n"
            + "\n"
            + "                                <td style=\"text-align: center;\">\n"
            + "                                    <p\n"
            + "                                        style=\"font-size: 17px;font-weight: 600;width: 74%;margin: 8px auto 0;line-height: 1.5;color: #939393;\">\n"
            + "                                        We’re Sending you this email because You requested a password reset. this link to create a new password:</p>\n"
            + "                                </td>\n"
            + "                            </tr>\n"
            + "                        </thead>\n"
            + "                    </table>\n"
            + "\n"
            + "                    <table class=\"button-table\" style=\"margin-top: 27px;\" align=\"center\" border=\"0\" cellpadding=\"0\"\n"
            + "                        cellspacing=\"0\" width=\"100%\">\n"
            + "                        <thead>\n"
            + "                            <tr style=\"text-align: center;\">\n"
            + "                                <td style=\"text-align: center;\">\n"
            +
            "                    <a href=\"" + recoveryUrl
            + "\" class=\"password-button\">Xác minh Email</a>\n"
            + "                                </td>\n"
            + "                            </tr>\n"
            + "                        </thead>\n"
            + "                    </table>\n"
            + "\n"
            + "                    <table class=\"content-table\" style=\"margin-top: 27px;\" align=\"center\" border=\"0\" cellpadding=\"0\"\n"
            + "                        cellspacing=\"0\" width=\"100%\">\n"
            + "                        <thead>\n"
            + "                            <tr style=\"display: block;\">\n"
            + "                                <td style=\"display: block;\">\n"
            + "                                    <p\n"
            + "                                        style=\"font-size: 17px;font-weight: 600;width: 74%;margin: 8px auto 0;line-height: 1.5;color: #939393;\">\n"
            + "                                        If you didn’t request a password reset, you can ignore this email. your password\n"
            + "                                        will not be changed.</p>\n"
            + "                                </td>\n"
            + "                            </tr>\n"
            + "                        </thead>\n"
            + "                    </table>\n"
            + "                            </td>\n"
            + "                        </tr>\n"
            + "                    </table>\n"
            + "                </td>\n"
            + "            </tr>\n"
            + "        </tbody>\n"
            + "    </table>\n"
            + "</body>\n"
            + "\n"
            + "\n"
            + "</html>";

    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText(message, true);
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new IllegalStateException("Không thể gửi email khôi phục mật khẩu", e);
    }
  }

}
