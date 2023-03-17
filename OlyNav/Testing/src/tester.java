import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class tester {
	public static void main(String[] args) throws AWTException, IOException {
		Robot b = new Robot();
		
		String[][] inputs = new String[50][2];
		for(int i=0;i<inputs.length;i++) {
			inputs[i]=new String[]{"a"+rand(0,29),"a"+rand(0,29)};
		}
		String path = "E:\\ColinStuff\\OlyNav\\testingImages\\";
		b.delay(5000);
		for(var i=0;i<inputs.length;i++) {
			b.delay(100);
			doKey(b,"v('"+inputs[i][0]+"','"+inputs[i][1]+"');");
			b.keyPress(10);
			b.delay(50);
			b.keyRelease(10);
			System.out.println("Next path @ "+i);
			BufferedImage img = b.createScreenCapture(new Rectangle(0,0,900,900));
			ImageIO.write(img,"jpg",new File(path+"img"+inputs[i][0]+"-"+inputs[i][1]+".jpg"));
		}
	}
	private static int rand(int min, int max) {
		return (int) Math.floor(Math.random()*(max-min)+min);
	}
	private static void doKey(Robot b, String msg) {
		String text = msg;
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		
		b.keyPress(KeyEvent.VK_CONTROL);
		b.keyPress(KeyEvent.VK_V);
		b.keyRelease(KeyEvent.VK_V);
		b.keyRelease(KeyEvent.VK_CONTROL);
	}
}
