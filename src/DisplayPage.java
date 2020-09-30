import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public void createPageImage(){
        final int width = DisplayConfig.DisplayWidth;
        final int height = DisplayConfig.DisplayHeight;
        final int halfWidth = width /2;
        final int halfHeight = height /2;

        BufferedImage image_black = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D black = (Graphics2D) image_black.getGraphics();


        black.setBackground(Color.WHITE);
        black.clearRect(0, 0, width, height);
        black.setColor(Color.BLACK);

        black.setFont(new Font("Arial", Font.PLAIN, 29));

        black.drawString(genre, 35, 62);

//        black.drawImage(ImageIO.read(new File("imagen.png")), , 0, 0);

//        top
        black.fillRect(20, 20, (width - 40), 3);
        black.fillRect(20, 80, (width - 40), 3);
//        bottom
        black.fillRect(20, 400, (width - 40), 3);
//        left
        black.fillRect(20, 20, 3, 380);
//        right
        black.fillRect(577, 20, 3, 380);

//        black.fillOval(halftwidth-5,height-27,10,10);

//        black.fillOval(halftwidth+5,height-27,10,10);
//        black.fillOval(halftwidth-15,height-27,10,10);

        black.fillOval(halfWidth - 25,height-27,10,10);
        black.fillOval(halfWidth - 5,height-27,10,10);
        black.fillOval(halfWidth + 15,height-27,10,10);


        try {
            ImageIO.write(image_black, "bmp", new File("./src/dynamicAssets/pages/page."+ pageNumber +".bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createQrCode(String url, String filename){
        try {
            String qrCodeData = url;
            String filePath = filename;
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel >();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));
            System.out.println("QR Code image created successfully!");
        } catch (Exception e) {
            System.err.println(e);
        }
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
