package com.yh.cloud.base.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 *
 * @author yanghan
 * @date 2019/7/5
 */
@Slf4j
public class QRCodeUtil {

    /** 可设置范围为0-10，但仅四个变化0 1(2) 3(4 5 6) 7(8 9 10) */
    public static final int DEFAULT_MARGIN = 0;
    /** logo默认比例大小等于3 */
    public static final int DEFAULT_LOGO_SIZE_MULTIPLE = 3;

    public static void main(String[] args) throws Exception {
        String contents = "http://www.baidu.com";
        int width = 300;
        int height = 300;
        int margin = DEFAULT_MARGIN;
        String filePath = "D:\\YH\\QRCodeImg.jpg";
        String logoPath = "D:\\YH\\logo.jpg";
        int logoSizeMultiple = DEFAULT_LOGO_SIZE_MULTIPLE;
        try {
            // 生成二维码
            BitMatrix bitMatrix = QRCodeUtil.createQRCode(contents, width, height, margin);
//            QRCodeUtil.generateQRCodeToPath(bitMatrix, filePath);
            // 添加LOGO
            BufferedImage bufferedImage = QRCodeUtil.createQRCodeWithLogo(bitMatrix, logoPath, logoSizeMultiple);
            // 导出到指定路径
            QRCodeUtil.generateQRCodeToPath(bufferedImage, filePath);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 二维码BufferedImage对象生成方法
     * @param contents
     * @param width
     * @param height
     * @return
     * @throws WriterException
     */
    public static BitMatrix createQRCode(String contents, int width, int height) throws WriterException {
        return createQRCode(contents, width, height, DEFAULT_LOGO_SIZE_MULTIPLE);
    }

    /**
     * 二维码BufferedImage对象生成方法
     *
     * @param contents 二维码内容
     * @param width    二维码图片宽度
     * @param height   二维码图片高度
     * @param margin   二维码边框(0,2,4,8)
     * @author yanghan
     * @return: BitMatrix
     */
    public static BitMatrix createQRCode(String contents, int width, int height, int margin) throws WriterException {
        if (contents == null || contents.equals("")) {
            contents = "contents is empty!";
        }
        // 二维码基本参数设置
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 设置编码字符集utf-8
        hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        // 设置纠错等级L/M/Q/H,纠错等级越高越不易识别，当前设置等级为最高等级H
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 可设置范围为0-10，但仅四个变化0 1(2) 3(4 5 6) 7(8 9 10)
        hints.put(EncodeHintType.MARGIN, margin);
        // 生成图片类型为QRCode
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        // 创建位矩阵对象
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height, hints);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
        return bitMatrix;
    }

    /**
     * BitMatrix转BufferedImage
     *
     * @param bitMatrix
     * @return BufferedImage
     */
    public static BufferedImage toBufferedImage(BitMatrix bitMatrix) {
        // 设置位矩阵转图片的参数
        MatrixToImageConfig config = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());
        // 位矩阵对象转BufferedImage对象
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
        return bufferedImage;
    }

    /**
     * 二维码添加LOGO
     *
     * @param bitMatrix        图标LOGO路径
     * @param logoPath         图标LOGO路径
     * @param logoSizeMultiple 二维码与LOGO的大小比例
     * @throws Exception
     * @author yanghan
     * @return: BufferedImage
     */
    public static BufferedImage createQRCodeWithLogo(BitMatrix bitMatrix, String logoPath, int logoSizeMultiple) throws Exception {
        BufferedImage qrCode = toBufferedImage(bitMatrix);
        try {
            // 读取LOGO
            BufferedImage logo = ImageIO.read(new File(logoPath));
            // 设置LOGO宽高
            int logoHeight = qrCode.getHeight() / logoSizeMultiple;
            int logoWidth = qrCode.getWidth() / logoSizeMultiple;
            // 设置放置LOGO的二维码图片起始位置
            int x = (qrCode.getWidth() - logoWidth) / 2;
            int y = (qrCode.getHeight() - logoHeight) / 2;
            // 新建空画板
            BufferedImage combined = new BufferedImage(qrCode.getWidth(), qrCode.getHeight(), BufferedImage.TYPE_INT_RGB);
            // 新建画笔
            Graphics2D g = (Graphics2D) combined.getGraphics();
            // 将二维码绘制到画板
            g.drawImage(qrCode, 0, 0, null);
            // 设置不透明度，完全不透明1f,可设置范围0.0f-1.0f
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            // 绘制LOGO
            g.drawImage(logo, x, y, logoWidth, logoHeight, null);
            return combined;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 导出到文件路径
     *
     * @param bitMatrix 位矩阵对象
     * @param filePath  图片路径
     * @throws IOException
     */
    public static void generateQRCodeToPath(BitMatrix bitMatrix, String filePath) throws IOException {
        Path path = FileSystems.getDefault().getPath(filePath);
        String format = filePath.substring(filePath.lastIndexOf(".") + 1);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
    }

    /**
     * 导出到文件路径
     *
     * @param image    图片缓存
     * @param filePath 图片路径
     * @throws IOException
     */
    public static void generateQRCodeToPath(BufferedImage image, String filePath) throws IOException {
        Path path = FileSystems.getDefault().getPath(filePath);
        String format = filePath.substring(filePath.lastIndexOf(".") + 1);
        if (!ImageIO.write(image, format, path.toFile())) {
            throw new IOException("Could not write an image of format " + format + " to " + path);
        }
    }

}
