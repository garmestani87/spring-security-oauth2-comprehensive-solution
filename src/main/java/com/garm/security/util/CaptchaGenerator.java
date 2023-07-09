package com.garm.security.util;

import com.garm.security.cache.CaptchaCacheService;
import com.garm.security.domain.dto.CaptchaDto;
import com.garm.security.domain.dto.ImageCaptchaResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CaptchaGenerator {

    private final CaptchaCacheService captchaCache;

    public ImageCaptchaResultDto getCaptchaImage() {
        ImageCaptchaResultDto result = new ImageCaptchaResultDto();
        try {
            Color backgroundColor = Color.white;
            Color borderColor = Color.white;
            Color textColor = Color.black;
            Color circleColor = new Color(190, 160, 150);
            Font textFont = new Font("Verdana", Font.BOLD, 24);
            int charsToPrint = 4;
            int width = 115;//160
            int height = 30;//50
            int circlesToDraw = 25;
            float horizMargin = 10.0f;
            double rotationRange = 0.7;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);

            g.setColor(circleColor);
            for (int i = 0; i < circlesToDraw; i++) {
                int L = (int) (Math.random() * height / 2.0);
                int X = (int) (Math.random() * width - L);
                int Y = (int) (Math.random() * height - L);
                g.draw3DRect(X, Y, L * 2, L * 2, true);
            }
            g.setColor(textColor);
            g.setFont(textFont);
            FontMetrics fontMetrics = g.getFontMetrics();
            int maxAdvance = fontMetrics.getMaxAdvance();
            int fontHeight = fontMetrics.getHeight();

            /*String eligibleChars = "ABCDEFGHJKLMNPQRSTUVWXYabcdefghjkmnpqrstuvwxy23456789";*/
            String eligibleChars = "123456789";
            char[] chars = eligibleChars.toCharArray();
            float spaceForLetters = -horizMargin * 2 + width;
            float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
            StringBuilder finalString = new StringBuilder();
            for (int i = 0; i < charsToPrint; i++) {
                double randomValue = Math.random();
                int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
                char characterToShow = chars[randomIndex];
                finalString.append(characterToShow);

                int charWidth = fontMetrics.charWidth(characterToShow);
                int charDim = Math.max(maxAdvance, fontHeight);
                int halfCharDim = charDim / 2;
                BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
                Graphics2D charGraphics = charImage.createGraphics();
                charGraphics.translate(halfCharDim, halfCharDim);
                double angle = (Math.random() - 0.5) * rotationRange;
                charGraphics.transform(AffineTransform.getRotateInstance(angle));
                charGraphics.translate(-halfCharDim, -halfCharDim);
                charGraphics.setColor(textColor);
                charGraphics.setFont(textFont);
                int charX = (int) (0.5 * charDim - 0.5 * charWidth);
                charGraphics.drawString("" + characterToShow, charX, (charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent());
                float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
                int y = (height - charDim) / 2;
                g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
                charGraphics.dispose();
            }
            g.setColor(borderColor);
            g.drawRect(0, 0, width - 1, height - 1);
            g.dispose();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

            result.setImage(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
            result.setUniqueId(storeToCatch(finalString.toString()));

        } catch (Exception ioe) {
            throw new RuntimeException("Unable to build image", ioe);
        }
        return result;
    }

    private String storeToCatch(String answer) {
        String uniqueId = UUID.randomUUID().toString();
        CaptchaDto captchaDto = new CaptchaDto(uniqueId, answer);
        captchaCache.put(uniqueId, captchaDto, 3);
        return uniqueId;
    }
}
