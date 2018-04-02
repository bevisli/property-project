package app.payment.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import core.framework.util.Files;
import core.framework.util.Maps;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author mort
 */
public class QRCodes {
    private static final Integer IMAGE_WIDTH = 400;
    private static final Integer IMAGE_HEIGHT = 400;
    private static final Path LOCAL_FILE_PATH = Files.tempDir();

    public static File weiPayCode(String paymentId, String imageFormat, String url) {
        Path filePath = LOCAL_FILE_PATH.resolve(Encoders.encodeKey(paymentId));
        if (java.nio.file.Files.exists(filePath)) {
            Files.deleteFile(filePath);
        }

        Map<EncodeHintType, String> hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, IMAGE_WIDTH, IMAGE_HEIGHT, hints);
            // Make the BufferedImage that are to hold the QRCode
            int matrixWidth = byteMatrix.getWidth();
            int matrixHeight = byteMatrix.getHeight();
            BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixHeight);
            // Paint and save the image using the ByteMatrix
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            saveToFile(filePath, imageFormat, image);
            return filePath.toFile();
        } catch (WriterException | IOException e) {
            throw new QRCodeException(e.getMessage(), e);
        }
    }

    private static void saveToFile(Path filePath, String imageFormat, BufferedImage image) throws IOException {
        // save to temp file
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, imageFormat, output);
        Path originalFilePath = Files.tempFile();
        try (InputStream stream = new ByteArrayInputStream(output.toByteArray())) {
            java.nio.file.Files.copy(stream, originalFilePath);
        }

        // move file to target path and clean up
        Files.createDir(filePath.getParent());
        Files.moveFile(originalFilePath, filePath);
    }
}
