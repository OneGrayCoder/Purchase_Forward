package com.aki.purchaseforward.pojo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/qiantai/SecurityCode")
public class ValidationCode extends HttpServlet
{
	//所生成的验证码就从codeChars随机生成
	private static String codeChars = "1234567890abcdefghijklmnopqrstuvwxyz";

	private static Color getRandomColor(int minColor,int maxColor){
		Random random = new Random();
		if(minColor>255)
			minColor = 255;
		if(maxColor>255)
			maxColor = 255;
		int red = minColor + random.nextInt(maxColor - minColor);
		int green = minColor + random.nextInt(maxColor - minColor);
		int blue = minColor + random.nextInt(maxColor - minColor);
		return new Color(red,green,blue);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		//获得验证码集合的长度
		int charsLength = codeChars.length();
		//下面3条是关闭客户端浏览器的缓冲区
		response.setHeader("ragma", "No-cache");
		response.setHeader("Cach-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//设置图形验证码的长宽
		int width = 90, height = 20;
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();//获得输出文字的graphics对象
		Random random = new Random();
		g.setColor(getRandomColor(180, 250));//背景颜色
		g.fillRect(0, 0, width, height);
		//设置初始字体
		g.setFont(new Font("Times New Roman",Font.ITALIC,height));
		g.setColor(getRandomColor(120, 180));//字体颜色
		StringBuilder validationCode = new StringBuilder();
		//验证码的随机字体
		String[] fontNames = {"Times New Roman","Book antiqua","Arial"};
		//随机生成3-5个验证码
		for (int i = 0; i < 3+random.nextInt(3); i++) {
			//随机设置当前验证码的字符的字体
			g.setFont(new Font(fontNames[random.nextInt(3)],Font.ITALIC,height));
			//随机获得当前验证码的字符
			char codeChar = codeChars.charAt(random.nextInt(charsLength));
			validationCode.append(codeChar);
			//随机设置当前验证码字符的颜色
			g.setColor(getRandomColor(10, 100));
			//在图形上输出验证码字符，x y随机生成
			g.drawString(String.valueOf(codeChar), 16*i+random.nextInt(7), height-random.nextInt(6));
		}
		//获得session对象
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(5*60);
		//将验证码保存在session对象中，key为validation_code
		session.setAttribute("validation_code", validationCode.toString());
		g.dispose();
		OutputStream os = response.getOutputStream();
		ImageIO.write(image,"JPEG",os);//以JPEG格式向客户端发送图形验证码

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		doGet(request, response);
	}

}
