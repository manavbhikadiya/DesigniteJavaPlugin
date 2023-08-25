package designite.logger;

import designite.filesManager.SettingsFolder;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DesigniteLogger {
    private final Logger logger;
    private static DesigniteLogger designiteLogger;

    private DesigniteLogger() {
        DateTimeFormatter dateAndTime = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm-ss");
        LocalDateTime current = LocalDateTime.now();
        java.util.logging.Logger logger = Logger.getLogger("designiteLogs: ");
        java.util.logging.FileHandler handler = null;
        try {
            String filePath = Paths.get(SettingsFolder.getDesigniteLogDirectoryPath(),
                    "dj_" + dateAndTime.format(current) + ".log").toString();
            handler = new java.util.logging.FileHandler(filePath);
        } catch (IOException e) {
        }
        if (handler != null) {
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        }
        this.logger = logger;
    }

    public static Logger getLogger() {
        if (designiteLogger == null)
            designiteLogger = new DesigniteLogger();
        return designiteLogger.logger;
    }
}
