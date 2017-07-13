package com.lingdu.common.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtil {
	/**
	 * 加载本地图片
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getImage(String path) throws IOException{
		return ImageIO.read(new File(path));
	}
	/**
	 * 加载远程图片
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getImage(URL url) throws IOException{
		return ImageIO.read(url);
	}
	/**
	 * 保存指定格式的图片
	 * @param savepath
	 * @param image
	 * @param formatName
	 * @throws IOException
	 */
	public static void saveImage(String savepath,BufferedImage image,String formatName) throws IOException{
		ImageIO.write(image, formatName, new File(savepath));
	}
	
	/**
	 * 合并图片
	 * @param bgimage
	 * @param srcimage
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void merge(BufferedImage bgimage,BufferedImage srcimage,
			int x,int y,int width,int height){
		Graphics2D  g=bgimage.createGraphics();//创建画板
		g.drawImage(srcimage, x, y, width, height,null);//画图
		g.dispose();//释放资源
	}
	
	public static void main(String[] args) {
		try {
			BufferedImage bgimage=ImageUtil.getImage("d:/bg.jpg");
			BufferedImage headimage=ImageUtil.getImage("d:/head.jpg");
			BufferedImage qrimage=ImageUtil.getImage(new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFp7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyYXd1MlJNMG1mWTIxdHhkLXhvMUoAAgRhQFdYAwQAjScA"));
			ImageUtil.merge(bgimage, headimage,100,700,120,120);
			ImageUtil.merge(bgimage, qrimage,330,700,120,120);
			
			ImageUtil.saveImage("d:/result.jpg",bgimage,"jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
