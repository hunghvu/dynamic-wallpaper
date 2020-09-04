package model;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
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
public class RandomFromNet extends RandomWpUpdate{

  private static final Double MY_RESOLUTION_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
  private static final Double MY_RESOLUTION_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
  private static final String MY_TMP_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();
  private static String myUri;
  private static HttpRequest myRequest;
  private static File myCachePath;
  
  public RandomFromNet() {
    
    myUri = "https://picsum.photos/" + MY_RESOLUTION_WIDTH.intValue() + "/" 
    + MY_RESOLUTION_HEIGHT.intValue();
    
  }
  
  private void getRegularPicture() {
    
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
      myCachePath = new File(MY_TMP_DIR + "cacheBg.jpg");
      
      JFrame frame = new JFrame();
      JLabel label = new JLabel(new ImageIcon(img));
      frame.getContentPane().add(label, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
      
    } catch (ConnectException e) {
      // Make a call to controller/view to update message or dialog.
      System.out.println(1);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 

    
    
  }
  
  public File getCacheFile() {
    return myCachePath;
  }
  
  public static void main(String[] args) {
    RandomFromNet client = new RandomFromNet();
    client.getRegularPicture();
    
  }

  @Override
  public void autoUpdate(boolean theSignal) {
    // TODO Auto-generated method stub
    
  }
}
