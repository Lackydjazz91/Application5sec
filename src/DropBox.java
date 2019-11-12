import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DropBox {
    public static void main(String[] args) {
        String ACCESS_TOKEN = "Здесь могла быть ваша реклама";

        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

        for(int i = 0; i < 10; i++){
            try {
                Date currentTime = new Date();

                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", byteArrayOutputStream);

                InputStream in = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                String filename = dateFormat.format(currentTime);
                client.files().uploadBuilder("/" + filename + ".png")
                        .uploadAndFinish(in);

                byteArrayOutputStream.close();
                in.close();

                Date endTime = new Date();
                Thread.sleep(5000 - (endTime.getTime() - currentTime.getTime()));
            } catch (AWTException | DbxException | InterruptedException | IOException e) {
                e.printStackTrace();
            }

        }
    }
}
