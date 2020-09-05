package model;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
public class RandomFromNet extends AbstractUpdater {
  
  /**
   * User's screen width.
   */
  private static final Double MY_RESOLUTION_WIDTH = Toolkit
      .getDefaultToolkit()
      .getScreenSize()
      .getWidth();
  
  /**
   * User's screen height.
   */
  private static final Double MY_RESOLUTION_HEIGHT = Toolkit
      .getDefaultToolkit()
      .getScreenSize()
      .getHeight();
  
  /**
   * Direction to temporary folder, provided by the OS.
   */
  private static final String MY_TMP_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();
  
  /**
   * URI of the image server.
   */
  private static String myUri;
  
  /**
   * Request to image server.
   */
  private static HttpRequest myRequest;
  
  /**
   * Cache the image on local system.
   */
  private static File myCachePath;
  
  /**
   * Change notifier to catch error.
   */
  private static PropertyChangeSupport myNetNotifier;
  
  /**
   * Constructor.
   */
  public RandomFromNet() {
    
    myNetNotifier = new PropertyChangeSupport(this);
    
    // A base URI to Lorem Picsum server.
    myUri = "https://picsum.photos/" 
        + MY_RESOLUTION_WIDTH.intValue() 
        + "/" 
        + MY_RESOLUTION_HEIGHT.intValue();
    
  }
  
  public void addListener(final PropertyChangeListener theListener) {
    myNetNotifier.addPropertyChangeListener(theListener);
  }
  
  public static void main(String[] args) {
    RandomFromNet client = new RandomFromNet();
    client.getRegularPicture();
    
  }

  @Override
  public void autoUpdate(boolean theSignal) {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * Get a random picture with the same resolution as 
   * user's screen, then cache it on local system.
   */ 
  private void getRegularPicture() {
    
    // Create a request to image server.
    myRequest = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(myUri))
        .build();
    
    // A response from server.
    HttpResponse<InputStream> response;
    try {
      
      // Generate a request and get a response from it.
      response = HttpClient.newBuilder()
          .followRedirects(HttpClient.Redirect.ALWAYS)
          .version(HttpClient.Version.HTTP_2)
          .build()
          .send(myRequest, HttpResponse.BodyHandlers.ofInputStream());
      
      // Buffer the received image.
      BufferedImage img = ImageIO.read(response.body());
      
      // Path to cache the image.
      myCachePath = new File(MY_TMP_DIR + "\\cacheBg.png");
      
      // Cache the image.
      ImageIO.write(img, "png", myCachePath);
      
      
      // For testing purpose only.
      JFrame frame = new JFrame();
      JLabel label = new JLabel(new ImageIcon(img));
      frame.getContentPane().add(label, BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
      
    } catch (ConnectException e) {
      
      // Make a call to controller/view to update message or dialog
      // when there is no Internet connection.
      myNetNotifier.firePropertyChange(null, null, CodeError.NET_NO_CONNECTION);
      
    } catch (IOException e) {
      // For internal use only
      
      System.out.println("Something is wrong with IO in RandomFromNet.");
      
      e.printStackTrace();
    } catch (InterruptedException e) {
      // For internal use only
      
      System.out.println("Something is wrong with a connection made by RandomFromNet");
    } 

    
    
  }
  
}
