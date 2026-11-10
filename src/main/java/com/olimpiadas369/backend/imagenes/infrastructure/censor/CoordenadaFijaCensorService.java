package com.olimpiadas369.backend.imagenes.infrastructure.censor;

import com.olimpiadas369.backend.imagenes.domain.services.CensorService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Implementación básica: pinta rectángulos negros sobre coordenadas fijas conocidas de la ficha
 * (zona del DNI y zona del celular). Las coordenadas exactas se ajustan contra la plantilla real.
 */
@Service
public class CoordenadaFijaCensorService implements CensorService {

    // TODO: calibrar contra la plantilla real de la ficha (porcentajes del alto/ancho).
    private static final double DNI_X_PCT = 0.06, DNI_Y_PCT = 0.10, DNI_W_PCT = 0.30, DNI_H_PCT = 0.05;
    private static final double CEL_X_PCT = 0.60, CEL_Y_PCT = 0.10, CEL_W_PCT = 0.30, CEL_H_PCT = 0.05;

    @Override
    public byte[] censurar(byte[] original, String contentType) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(original));
            if (img == null) return original;
            Graphics2D g = img.createGraphics();
            try {
                g.setColor(Color.BLACK);
                g.fillRect(pct(img.getWidth(), DNI_X_PCT), pct(img.getHeight(), DNI_Y_PCT),
                        pct(img.getWidth(), DNI_W_PCT), pct(img.getHeight(), DNI_H_PCT));
                g.fillRect(pct(img.getWidth(), CEL_X_PCT), pct(img.getHeight(), CEL_Y_PCT),
                        pct(img.getWidth(), CEL_W_PCT), pct(img.getHeight(), CEL_H_PCT));
            } finally { g.dispose(); }
            ByteArrayOutputStream out = new ByteArrayOutputStream(original.length);
            String format = contentType != null && contentType.contains("png") ? "png" : "jpg";
            ImageIO.write(img, format, out);
            return out.toByteArray();
        } catch (Exception e) { return original; }
    }

    private static int pct(int size, double pct) { return (int) Math.round(size * pct); }
}
