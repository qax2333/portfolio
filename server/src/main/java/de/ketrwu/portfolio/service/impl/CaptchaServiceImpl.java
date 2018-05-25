package de.ketrwu.portfolio.service.impl;

import de.ketrwu.portfolio.forms.CaptchaForm;
import de.ketrwu.portfolio.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final Random RANDOM = new Random();
    private static final int[] IMG_SIZE = {150, 40};
    private static final int[] SOLUTION_LENGTH = {4, 8};
    private static final float FONT_SIZE = 20F;

    private HashMap<String, String> solutions = new HashMap<>();
    private List<Font> fonts = new ArrayList<>();

    @Override
    public String createCaptcha(CaptchaForm captchaForm) throws IOException {
        String solution = getRandomString();

        captchaForm.setCaptchaToken(UUID.randomUUID().toString());
        solutions.put(captchaForm.getCaptchaToken(), solution);

        BufferedImage img = new BufferedImage(IMG_SIZE[0], IMG_SIZE[1], BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setPaint(Color.white);
        g2d.setFont(getRandomFont());
        FontMetrics fm = g2d.getFontMetrics();
        int x = (img.getWidth() / 2) - (fm.stringWidth(solution) / 2);
        int y = fm.getHeight();
        g2d.drawString(solution, x, y);
        g2d.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "png", os);

        captchaForm.setCaptchaImage(Base64.getEncoder().encodeToString(os.toByteArray()));
        return captchaForm.getCaptchaImage();
    }

    @Override
    public boolean checkCaptcha(CaptchaForm captchaForm) {
        if (captchaForm == null) {
            return false;
        }
        if (captchaForm.getCaptchaToken() == null || captchaForm.getCaptchaToken().trim().length() == 0) {
            return false;
        }
        if (captchaForm.getCaptchaResponse() == null || captchaForm.getCaptchaResponse().trim().length() == 0) {
            return false;
        }
        if (solutions.get(captchaForm.getCaptchaToken()) == null) {
            return false;
        }

        boolean result = solutions.get(captchaForm.getCaptchaToken()).equalsIgnoreCase(captchaForm.getCaptchaResponse());
        solutions.remove(captchaForm.getCaptchaToken());

        return result;
    }

    @Override
    public void loadFonts() {
        fonts.clear();
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        try {
            for (Resource resource : resolver.getResources("classpath*:/static/captcha-fonts/*.ttf")) {
                fonts.add(createFont(resource));
            }
        } catch (IOException e) {
            log.error("Failed to find fonts in classpath", e);
        }
    }

    private Font getRandomFont() {
        return fonts.get(RANDOM.nextInt(fonts.size()));
    }

    private String getRandomString() {
        return RandomStringUtils.random(
                RANDOM.nextInt((SOLUTION_LENGTH[1] - SOLUTION_LENGTH[0]) + 1) + SOLUTION_LENGTH[0],
                true,
                true
        ).toLowerCase();
    }

    private Font createFont(Resource resource) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, resource.getInputStream());
            font = font.deriveFont(FONT_SIZE);
        } catch (FontFormatException | IOException e) {
            log.error("Failed to create font of file \"{}\"", resource.getFilename(), e);
        }
        return font;
    }
}
