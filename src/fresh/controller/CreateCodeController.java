package fresh.controller;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/code")
public class CreateCodeController extends BasicServlet{
    private static final long serialVersionUID = 6265385251642736968L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        // 把验证码存到Session中
        String code = getRamdomCode();

        HttpSession session = req.getSession();
        session.setAttribute("validatecode", code);

        BufferedImage bi = getCodeImage(code, 110, 28);
        ImageIO.write(bi, "JPEG", resp.getOutputStream());
    }

    private String getRamdomCode() {
        Random rd = new Random();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int flag = rd.nextInt(3);
            switch (flag) {
                case 0:
                    sbf.append((rd.nextInt(10)));
                    break;
                case 1:
                    sbf.append((char)(rd.nextInt(26) + 65));
                    break;
                case 2:
                    sbf.append((char)(rd.nextInt(26) + 97));
                    break;
                default:
                    break;
            }
        }
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    private Color getRandomColor(int start, int bound) {
        Random rd = new Random();
        int r = start + rd.nextInt(bound);
        int g = start + rd.nextInt(bound);
        int b = start + rd.nextInt(bound);
        return new Color(r, g, b);
    }

    private BufferedImage getCodeImage(String code, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 背景色
        g.setColor(getRandomColor(225, 30));
        g.fillRect(0, 0, width, height);

        

        // 随机的干扰线
        Random rd = new Random();
        for (int i = 0; i < 30; i++) {
            g.setColor(getRandomColor(150, 40));
            int x1 = rd.nextInt(width);
            int x2 = rd.nextInt(width);
            int y1 = rd.nextInt(height);
            int y2 = rd.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        // 文字
        g.setFont(new Font("Courier New", Font.ITALIC, 20));
        char[] codes = code.toCharArray();
        for (int i = 0; i < codes.length; i++) {
            // 每个字的颜色
            g.setColor(getRandomColor(50, 80));
            g.drawString(String.valueOf(codes[i]), 10 + 24 * i, 20);
        }
        g.dispose();
        return image;
    }
}