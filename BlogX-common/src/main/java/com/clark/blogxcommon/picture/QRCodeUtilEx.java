package com.clark.blogxcommon.picture;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.font.FontDesignMetrics;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * @author Ford Wang
 * @Description 生成企业二维码
 */
public class QRCodeUtilEx {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtilEx.class);
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;
    // 字体大小
    private static final int FONT_SIZE = 18;

    public static void main(String[] args) {
        String url = "https://www.baidu.com/"; // 跳转地址
        String text = "百度一下"; // 描述文字
        String imagePath = "D:/AHeading/pictures/HDCRM.png"; // logo图片
        try {
            createImage(url,text,imagePath,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static BufferedImage createImage(String content, String bottomDes, String imgPath, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int tempHeight = height;
        boolean needDescription = (null != bottomDes && !"".equals(bottomDes));
        if (needDescription) {
            tempHeight += 30;
        }
        BufferedImage image = new BufferedImage(width, tempHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        // 插入图片
        if (imgPath != null && !"".equals(imgPath)) {
            QRCodeUtilEx.insertImage(image, imgPath, needCompress);
        }
        //添加底部文字
        if (needDescription) {
            QRCodeUtilEx.addFontImage(image, bottomDes);
        }
        return image;
    }

    private static void save(String path, BufferedImage source) {
        try {
            FileOutputStream out = new FileOutputStream(path);
            ImageIO.write(source,"png",out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加 底部图片文字
     *
     * @param source      图片源
     * @param declareText 文字本文
     */
    private static void addFontImage(BufferedImage source, String declareText) {
        String path1 = "D:/AHeading/pictures/source.png";
        save(path1, source);
        BufferedImage textImage = strToImage(declareText, source);

        String path2 = "D:/AHeading/pictures/textImage.png";
        save(path2, textImage);

        Graphics2D graph = source.createGraphics();
        //开启文字抗锯齿
        graph.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = textImage.getWidth(null);
        int height = textImage.getHeight(null);

        Image src = textImage;
        graph.drawImage(src, 0, QRCODE_SIZE - 20, width, height, null);
        String path3 = "D:/AHeading/pictures/src.png";
        save(path3, (BufferedImage) src);

        graph.dispose();
    }

    private static BufferedImage strToImage(String str, int width, int height) {
        BufferedImage textImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)textImage.getGraphics();
        //开启文字抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.BLACK);
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font("微软雅黑", Font.BOLD, FONT_SIZE);
        g2.setFont(font);
        LineMetrics lineMetrics = font.getLineMetrics(str, context);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        float offset = (width - fontMetrics.stringWidth(str)) / 2;
        float y = (height + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;

        g2.drawString(str, (int)offset, (int)y);

        return textImage;
    }

    private static BufferedImage strToImage(String str, BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage textImage = source;
        Graphics2D g2 = (Graphics2D)textImage.getGraphics();
        //开启文字抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, height-30, width, 50);
        g2.setPaint(Color.BLACK);
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font("微软雅黑", Font.BOLD, FONT_SIZE);
        g2.setFont(font);
        LineMetrics lineMetrics = font.getLineMetrics(str, context);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        float offset = (width - fontMetrics.stringWidth(str)) / 2;
        float y = (height + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;

        g2.drawString(str, (int)offset, height - 20);

        return textImage;
    }


    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static void encode(String content, String bottomDes, String imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtilEx.createImage(content, bottomDes, imgPath, needCompress);
        mkdirs(destPath);
        // String file = new Random().nextInt(99999999)+".jpg";
        // ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }
    //获取二维码base64数据
    public static  String encodeStr(String content, String bottomDes) throws Exception{
        BufferedImage image = QRCodeUtilEx.createImage(content, bottomDes, null, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        ImageIO.write(image, FORMAT_NAME,baos);//写入流中
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String jpg_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        jpg_base64 = jpg_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        //System.out.println("值为："+"data:image/jpg;base64,"+png_base64);
        return  jpg_base64;
    }

    public static BufferedImage encode(String content, String bottomDes, String imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtilEx.createImage(content, bottomDes, imgPath, needCompress);
        return image;
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encode(String content, String bottomDes, String imgPath, String destPath) throws Exception {
        QRCodeUtilEx.encode(content, bottomDes, imgPath, destPath, false);
    }
    // 被注释的方法
    /*
     * public static void encode(String content, String destPath, boolean
     * needCompress) throws Exception { QRCodeUtilEx.encode(content, null, destPath,
     * needCompress); }
     */

    public static void encode(String content, String bottomDes, String destPath) throws Exception {
        QRCodeUtilEx.encode(content, bottomDes, null, destPath, false);
    }

    public static void encode(String content, String bottomDes, String imgPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCodeUtilEx.createImage(content, bottomDes, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static void encode(String content, String bottomDes, OutputStream output) throws Exception {
        QRCodeUtilEx.encode(content, bottomDes, null, output, false);
    }

    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    public static String decode(String path) throws Exception {
        return QRCodeUtilEx.decode(new File(path));
    }

}
