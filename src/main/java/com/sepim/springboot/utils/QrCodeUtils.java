package com.sepim.springboot.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/10/9
 * 二维码工具类
 */
public class QrCodeUtils {
    private static final String CHARSET = "utf-8";
    public static final String IMAGE_FORMAT = "jpg";
    //二维码尺寸
    private static final int QRCODE_SIZE = 300;
    //logo宽度
    private static final int LOGO_WIDTH = 60;
    //logo高度
    private static final int LOGO_HEIGHT = 60;

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeUtils.class);

    /**
     * 生成二维码
     * @param content 二维码内容
     * @param logoPath logo地址
     * @param needCompress 是否压缩logo
     * @return 图片
     */
    public static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //设置图片边距
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        QrCodeUtils.insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     * @param image 二维码图片
     * @param logoPath logo图片地址
     * @param needCompress 是否压缩
     * @throws IOException 异常处理--向上传
     */
    private static void insertImage(BufferedImage image, String logoPath, boolean needCompress) throws IOException {
        File file = new File(logoPath);
        if (!file.exists()) {
            LOGGER.error(logoPath + "不存在");
            return;
        }
        Image src = ImageIO.read(new File(logoPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) {
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image logoImage = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = tag.getGraphics();
            graphics.drawImage(logoImage, 0, 0, null);
            graphics.dispose();
            src = logoImage;
        }
        Graphics2D graphics = image.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graphics.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 6, 6);
        graphics.setStroke(new BasicStroke(3f));
        graphics.draw(shape);
        graphics.dispose();
    }

    /**
     * 生成二维码（指定路径保存）
     * @param content 内容
     * @param imagePath logo图片地址
     * @param destPath 二维码存放地址
     * @param needCompress 是否压缩logo
     * @throws IOException
     * @throws WriterException
     */
    public static void encode(String content, String imagePath, String destPath, boolean needCompress) throws IOException, WriterException {
        BufferedImage image = QrCodeUtils.createImage(content, imagePath, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, IMAGE_FORMAT, new File(destPath));
    }

    private static void mkdirs(String destPath) {
        File file = new File(destPath);
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别与mkdir
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码（直接将二维码以图片输出流输出）
     * @param content 内容
     * @param imagePath logo图片地址
     * @param needCompress 是否压缩图片
     * @return
     * @throws IOException
     * @throws WriterException
     */
    public static BufferedImage encode(String content, String imagePath, boolean needCompress) throws IOException, WriterException {
        return QrCodeUtils.createImage(content, imagePath, needCompress);
    }

    /**
     * 生成二维码
     * @param content 内容
     * @param imagePath logo地址
     * @param outputStream 输出流
     * @param needCompress 是否压缩logo
     * @throws IOException
     * @throws WriterException
     */
    public static void encode(String content, String imagePath, OutputStream outputStream, boolean needCompress) throws IOException, WriterException {
        BufferedImage image = QrCodeUtils.createImage(content, imagePath, needCompress);
        ImageIO.write(image, IMAGE_FORMAT, outputStream);
    }

    /**
     * 获取指定文件的输入流
     * @param logoImage 文件的地址
     * @return
     */
    public static InputStream getResourceAsStream(String logoImage) {
        return QrCodeUtils.class.getResourceAsStream(logoImage);
    }

    /**
     * 解析二维码
     * @param file 二维码图片
     * @return
     * @throws IOException
     * @throws NotFoundException
     */
    public static String decode(File file) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);
        return result.getText();
    }

    /**
     * 解析二维码
     * @param path 二维码地址
     * @return
     * @throws NotFoundException
     * @throws IOException
     */
    public static String decode(String path) throws NotFoundException, IOException {
        return QrCodeUtils.decode(new File(path));
    }


}
