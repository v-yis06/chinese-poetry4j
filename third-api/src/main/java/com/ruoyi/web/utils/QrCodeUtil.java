package com.ruoyi.web.utils;
import com.google.common.collect.Lists;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.alibaba.druid.util.FnvHash.Constants.CHARSET;

/**
 *  二维码创建工具类（支持自定义logo图片背景）
 */
public class QrCodeUtil {
    private static Map<String, Image> picCache = new HashMap<String, Image>();
    // 容错率
    private static final double rate = 0.30;
    private static final ErrorCorrectionLevel E_RATE = ErrorCorrectionLevel.L;
    // 二维码宽
    private static final int QRCODE_WIDTH = 200;
    // 二维码高
    private static final int QRCODE_HEIGHT = 200;// 默认宽高相同
    // logo地址
    private static final String LogoPath = null;
    // 是否debug
    private static final boolean DEBUG = true;

    /**
     * 二维码生成以及输出实现线程
     */
    private final static class QrCodeWorker implements Runnable{
        private File file;
        private String content;
        private int height;
        private int width;
        private int dw;
        @Override
        public void run() {
            BufferedImage image;
            long start = 0;
            try {
                start = System.currentTimeMillis();
                image = QrCodeUtil.createImage(content,width,height,QrCodeUtil.LogoPath,dw);
                ImageIO.write(image,"png",file);
                file=null;
                image=null;
                if(QrCodeUtil.DEBUG){
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public File getFile() {
            return file;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public void setFile(File file) {
            this.file = file;
        }
        public int getDw() {
            return dw;
        }
        public void setDw(int dw) {
            this.dw = dw;
        }
        public int getHeight() {
            return height;
        }
        public void setHeight(int height) {
            this.height = height;
        }
        public int getWidth() {
            return width;
        }
        public void setWidth(int width) {
            this.width = width;
        }

    }

    private static void mkdirs(String destPath ,Boolean delFlag) {
        File dir = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        // 支持清理原有文件
        if( dir.exists() && dir.isDirectory() && delFlag){
            List<File> files = Lists.newArrayList(dir.listFiles());
            for (int i = 0; i < files.size(); i++) {
                files.get(i).delete();
            }
        }
    }

    public static void generateIceQrFiles(List<String> qrCodeList ,String rootPath, String deptId) throws InterruptedException {
        String destDir = rootPath + File.separator + deptId;
        mkdirs(destDir,true);

        BlockingQueue queue = new ArrayBlockingQueue(40);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 1,
                TimeUnit.DAYS, queue);
        for (int i = 0; i < qrCodeList.size(); i++) {

            QrCodeWorker qrCodeWorker = new QrCodeWorker();
            String content = qrCodeList.get(i);
            File file = new File(destDir, content + ".png");
            qrCodeWorker.setFile(file);
            qrCodeWorker.setContent(content);
            qrCodeWorker.setWidth(QRCODE_WIDTH);
            qrCodeWorker.setHeight(QRCODE_HEIGHT);
            executor.execute(qrCodeWorker);
            Thread.sleep(20);
        }

        executor.shutdown();
    }

    /**
     * 创建二维码
     *
     * @param content
     *            文字内容
     * @param logoPath
     *            读取logo图片路径
     * @param QRCODE_WIDTH
     *            二维码宽
     * @param QRCODE_HEIGHT
     *            二维码高
     * @param dw
     *            内部logo宽
     * @return
     * @throws Exception
     */
    private static BufferedImage createImage(String content,
                                             int QRCODE_WIDTH, int QRCODE_HEIGHT, String logoPath, int dw) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, E_RATE);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }

        // 插入logo
        if(!StringUtils.isEmpty(logoPath)){
            insertImage(image, logoPath, QRCODE_WIDTH, QRCODE_HEIGHT, dw);
        }
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source
     *            二维码图片
     * @param imgPath
     *            LOGO图片地址
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    int QRCODE_WIDTH, int QRCODE_HEIGHT, int dw) throws Exception {
        Image src;
        if (picCache.get("cache") != null) {
            src = picCache.get("cache");
        } else {
            File file = new File(imgPath);
            if (!file.exists()) {
                System.err.println("" + imgPath + "   该文件不存在！");
                return;
            }
            src = ImageIO.read(new File(imgPath));
            picCache.put("cache", src);// 缓存
            System.out.println("未读缓存");
        }

        Image image = src.getScaledInstance(dw, dw, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(dw, dw,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        src = image;
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_WIDTH - dw) / 2;

        graph.drawImage(src, x, x, dw, dw, null); // y
        Shape shape = new RoundRectangle2D.Float(x, x, dw, dw, 6, 6);// y
        graph.setStroke(new BasicStroke(12f));
        graph.draw(shape);
        graph.dispose();
    }

    public static void main(String[] args) throws IOException,
            InterruptedException {

        List<String> qrCodeList = Lists.newArrayList("DKLKD-BP-0001,DKLKD-BP-0002,DKLKD-BP-0013,44".split(","));
        String deptId = "123";
        String rootPath = "/Users/eason/Desktop" + File.separator + "tmp";
        generateIceQrFiles(qrCodeList, rootPath, deptId);
    }

}
