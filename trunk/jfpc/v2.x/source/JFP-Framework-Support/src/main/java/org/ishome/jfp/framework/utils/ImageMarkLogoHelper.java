package org.ishome.jfp.framework.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
 
/**
 * 图片水印工具类
 * @author Spook
 * @version 1.2 2014/11/3
 * @version 1.1 2014/10/8
 * @since 1.1.4
 */
public class ImageMarkLogoHelper {

    private static int degree = 0;
    
    // 水印透明度 
    private static float alpha = 0.5f;
    // 水印横向位置
 //   private static int positionWidth = 150;
    // 水印纵向位置
 //   private static int positionHeight = 300;
    // 水印文字字体
 //   private static Font font = new Font("宋体", Font.BOLD, 30);
    // 水印文字颜色
 //   private static Color color = Color.red;
    
    private static int x = 45;
  //  private static int y = 1;
//    /**
//     * 
//     * @param alpha 
//     *          水印透明度
//     * @param positionWidth 
//     *          水印横向位置
//     * @param positionHeight 
//     *          水印纵向位置
//     * @param font 
//     *          水印文字字体
//     * @param color 
//     *          水印文字颜色
//     */
//    public static void setImageMarkOptions(float alpha , int positionWidth , int positionHeight ,Font font,Color color){
//        if(alpha!=0.0f)ImageMarkLogoUtil.alpha = alpha;
//        if(positionWidth!=0)ImageMarkLogoUtil.positionWidth = positionWidth;
//        if(positionHeight!=0)ImageMarkLogoUtil.positionHeight = positionHeight;
//        if(font!=null)ImageMarkLogoUtil.font = font;
//        if(color!=null)ImageMarkLogoUtil.color = color;
//    }
//     
//    /**
//     * 给图片添加水印图片
//     * 
//     * @param iconPath
//     *            水印图片路径
//     * @param srcImgPath
//     *            源图片路径
//     * @param targerPath
//     *            目标图片路径
//     */
//    public static void markImageByIcon(String iconPath, String srcImgPath,
//            String targerPath) {
//        markImageByIcon(iconPath, srcImgPath, targerPath, null);
//    }
 
    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * 
     * @param imgInputStream 图片源
     */
    public static InputStream markImageByIcon(InputStream imgInputStream, ImageIcon imgIcon) {
        try {
             
            Image srcImg = ImageIO.read(imgInputStream);
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            BufferedImage buffImg = new BufferedImage(width,
            		height, BufferedImage.TYPE_INT_RGB);
 
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
 
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
           
            // 3、设置水印旋转          
            g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
           
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            //ImageIcon imgIcon = new ImageIcon(iconPath);
 
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
			int width_1 = img.getWidth(null);
			int height_1 = img.getHeight(null);             
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
 
			//int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			
/*			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}*/

            // 6、水印图片的位置
            g.drawImage(img, x, heightDiff-45 ,width_1 ,height_1, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            
            // 8、生成图片
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "JPG", os); 
            //System.out.println("图片完成添加水印图片");
            return new ByteArrayInputStream(os.toByteArray()); 
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        } 
        return imgInputStream;
    }
 
    public static InputStream markImageByText(InputStream imgInputStream) {
    	return imgInputStream;
    }
//    /**
//     * 给图片添加水印文字
//     * 
//     * @param logoText 
//     *          水印文字
//     * @param srcImgPath
//     *          源图片路径
//     * @param targerPath
//     *          目标图片路径
//     */
//    public static void markImageByText(String logoText, String srcImgPath,
//            String targerPath) {
//        markImageByText(logoText, srcImgPath, targerPath, null);
//    }
// 
//    /**
//     * 给图片添加水印文字、可设置水印文字的旋转角度
//     * 
//     * @param logoText
//     * @param srcImgPath
//     * @param targerPath
//     * @param degree
//     */
//    public static void markImageByText(String logoText, String srcImgPath,
//            String targerPath, Integer degree) {
//         
//        InputStream is = null;
//        OutputStream os = null;
//        try {
//            // 1、源图片
//            Image srcImg = ImageIO.read(new File(srcImgPath));
//            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
// 
//            // 2、得到画笔对象
//            Graphics2D g = buffImg.createGraphics();
//            // 3、设置对线段的锯齿状边缘处理
//            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
//            // 4、设置水印旋转
//            if (null != degree) {
//                g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
//            }
//            // 5、设置水印文字颜色
//            g.setColor(color);
//            // 6、设置水印文字Font
//            g.setFont(font);
//            // 7、设置水印文字透明度
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
//            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
//            g.drawString(logoText, positionWidth, positionHeight);
//            // 9、释放资源
//            g.dispose();
//            // 10、生成图片
//            os = new FileOutputStream(targerPath);
//            ImageIO.write(buffImg, "JPG", os);
// 
//            System.out.println("图片完成添加水印文字");
//             
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != is)
//                    is.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                if (null != os)
//                    os.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//     
//    public static void main(String [] args){
//        String iconPath = "E:/pic/a.png";//水印文件  
//        String srcImgPath = "E:/pic/1.jpg";  
//        String logoText = "[ I love Qie]";
//         
//        String targerTextPath = "E:/pic/2.jpg";  
//        String targerTextPath2 = "E:/pic/3.jpg";
//         
//        String targerIconPath = "E:/pic/4.jpg";  
//        String targerIconPath2 = "E:/pic/1.jpg";
//         
//        System.out.println("给图片添加水印文字开始...");
//        // 给图片添加水印文字  
//        markImageByText(logoText, srcImgPath, targerTextPath);  
//        // 给图片添加水印文字,水印文字旋转-45  
//        markImageByText(logoText, srcImgPath, targerTextPath2, -45);  
//        System.out.println("给图片添加水印文字结束...");
//         
//        System.out.println("给图片添加水印图片开始...");
//        setImageMarkOptions(0.3f,1,1,null,null);
//        // 给图片添加水印图片  
//        markImageByIcon(iconPath, srcImgPath, targerIconPath);  
//        // 给图片添加水印图片,水印图片旋转-45  
//        markImageByIcon(iconPath, srcImgPath, targerIconPath2, -45); 
//        System.out.println("给图片添加水印图片结束...");
//    }
 
}