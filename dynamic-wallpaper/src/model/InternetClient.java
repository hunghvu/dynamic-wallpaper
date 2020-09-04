package model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Builder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class will provide an HTTP client to get
 * a picture from Internet.
 * @author Hung Vu
 *
 */
public class InternetClient {

  private static final Double MY_RESOLUTION_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
  private static final Double MY_RESOLUTION_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
  private static final String MY_TMP_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();
  private static String myUri;
  private static HttpRequest myRequest;
  private static File myCache;
  
  public InternetClient() {
    
    myUri = "https://picsum.photos/" + MY_RESOLUTION_WIDTH.intValue() + "/" 
    + MY_RESOLUTION_HEIGHT.intValue();
    
  }
  
  public void getRegularPicture() {
    
    myRequest = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(myUri))
        .build();
    
    HttpResponse<InputStream> response;
    try {
      response = HttpClient.newBuilder()
          .followRedirects(HttpClient.Redirect.ALWAYS)
          .version(HttpClient.Version.HTTP_2)
          .build()
          .send(myRequest, HttpResponse.BodyHandlers.ofInputStream());
      Image img = ImageIO.read(response.body());
      myCache = new File(MY_TMP_DIR + "cacheBg.jpg");
      
      JFrame frame = new JFrame();
      JLabel label = new JLabel(new ImageIcon(img));
      frame.getContentPane().add(label, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
//    catch (ConnectionException e) {
//      
//    }
    
    
  }
  
  public File getCacheFile() {
    return myCache;
  }
  
  public static void main(String[] args) {
    InternetClient client = new InternetClient();
    client.getRegularPicture();
    
  }
}
