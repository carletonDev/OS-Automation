package generalUtilities;

import java.io.File;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DeleteRunner {


  private int daysFromCurrentDate = BigDecimal.ONE.intValue();
  @Getter
  private boolean deleted;


  public void deleteFiles(File dir) {
    long purgeTime = System.currentTimeMillis() - (daysFromCurrentDate * 24 * 60 * 60 * 1000);
    if (dir.exists()) {
      if (!dir.isDirectory() && dir.lastModified() < purgeTime) {
        deleted = dir.delete();
      } else if (dir.isDirectory() && dir.lastModified() < purgeTime) {
        recursiveDelete(dir);
      } else if (dir.isDirectory()) {
        File[] files = dir.listFiles();
        if (files != null) {
          for (File aFile : files) {
            deleteFiles(aFile);
          }
        }
      }
    }
  }

  private void recursiveDelete(File file) {
    if (!file.exists()) {
      return;
    }

    if (file.isDirectory()) {
      for (File f : Objects.requireNonNull(file.listFiles())) {
        recursiveDelete(f);
      }
    }

    deleted = file.delete();
  }
}
