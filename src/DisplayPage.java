import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// Qr Code creator
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

// Draw Image
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayPage {
    private final String genre;
    private final String title;
    private final String description;
    private final String link;
    private final int pageNumber;
    private final int numberPages;

    public DisplayPage(String genre, String title, String description, String link, int pageNumber, int numberPages) {
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.link = link;
        this.pageNumber = pageNumber;
        this.numberPages = numberPages;
        createPageImage();
    }

    public void createPageImage() {
        final int width = DisplayConfig.DisplayWidth;
        final int height = DisplayConfig.DisplayHeight;
        final int halfWidth = width / 2;

        BufferedImage image_black = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D img = (Graphics2D) image_black.getGraphics();


        img.setBackground(Color.WHITE);
        img.clearRect(0, 0, width, height);
        img.setColor(Color.BLACK);

        img.setFont(new Font("Arial", Font.PLAIN, 29));

        img.drawString(genre, 35, 62);

        img.setFont(new Font("Arial", Font.PLAIN, 24));
        img.drawString(title, 35, 130);
        img.setFont(new Font("Arial", Font.PLAIN, 17));

        ArrayList<String> descriptionWords = new ArrayList<>(Arrays.asList(description.split(" ")));
        ArrayList<StringBuilder> descriptionLines = new ArrayList<>();
        int maxWidth = 300;
        AtomicInteger line = new AtomicInteger(0);
        descriptionWords.forEach(word -> {
            if (descriptionLines.size() == 0) {
                descriptionLines.add(new StringBuilder(word + " "));
            } else if (img.getFontMetrics().stringWidth(descriptionLines.get(line.get()).toString() + word) <= maxWidth) {
                descriptionLines.get(line.get()).append(word).append(" ");
            } else {
                line.getAndIncrement();
                descriptionLines.add(new StringBuilder(word + " "));
            }
        });

        int topMargin = 170;
        for (int i = 0; i < descriptionLines.size(); i++) {
            img.drawString(descriptionLines.get(i).toString(), 35, (topMargin + i * (img.getFontMetrics().getFont().getSize() + 5)));
        }

//        logo
        try {
            img.drawImage(ImageIO.read(new File("./src/assets/img/logo.herder.bmp")), 520, 32, 40, 40, null);
        } catch (IOException e) {
            System.out.println(e);
        }
//        qrcode
        try {
            img.drawImage(createQrCode(link), 350, 150, 200, 200, null);
        } catch (
                Exception e) {
            e.printStackTrace();
        }

//        top
        img.fillRect(20, 20, (width - 40), 3);
        img.fillRect(20, 80, (width - 40), 3);
//        bottom
        img.fillRect(20, 400, (width - 40), 3);
//        left
        img.fillRect(20, 20, 3, 380);
//        right
        img.fillRect(577, 20, 3, 380);

        System.out.println(pageNumber);
//        page indicator
        if (pageNumber == 1) {
            img.drawOval(halfWidth - 55, height - 27, 10, 10);
        } else {
            img.fillOval(halfWidth - 55, height - 27, 10, 10);
        }

        if (pageNumber == 2) {
            img.drawOval(halfWidth - 35, height - 27, 10, 10);
        } else {
            img.fillOval(halfWidth - 35, height - 27, 10, 10);
        }
        if (pageNumber == 3) {
            img.drawOval(halfWidth - 15, height - 27, 10, 10);
        } else {
            img.fillOval(halfWidth - 15, height - 27, 10, 10);
        }
        if (pageNumber == 4) {
            img.drawOval(halfWidth + 5, height - 27, 10, 10);
        } else {
            img.fillOval(halfWidth + 5, height - 27, 10, 10);
        }

        if (pageNumber == 5) {
            img.drawOval(halfWidth + 25, height - 27, 10, 10);
        } else {
            img.fillOval(halfWidth + 25, height - 27, 10, 10);
        }
        if (pageNumber == 6) {
            img.drawOval(halfWidth + 45, height - 27, 10, 10);
        } else {
            img.fillOval(halfWidth + 45, height - 27, 10, 10);
        }

        try {
            ImageIO.write(image_black, "bmp", new File("./src/assets/dyn/page." + pageNumber + ".bmp"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedImage createQrCode(String url) {
        try {
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(url.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            return MatrixToImageWriter.toBufferedImage(matrix);
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "DisplayPage{" +
                "genre='" + genre + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pageNumber=" + pageNumber +
                ", numberPages=" + numberPages +
                '}';
    }
}
