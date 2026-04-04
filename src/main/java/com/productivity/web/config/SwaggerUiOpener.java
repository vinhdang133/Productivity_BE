package com.productivity.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.web.context.WebServerApplicationContext;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.Arrays;

@Component
public class SwaggerUiOpener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(SwaggerUiOpener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            var ctx = event.getApplicationContext();

            // Don't open during tests
            String[] active = ctx.getEnvironment().getActiveProfiles();
            if (active != null && Arrays.asList(active).contains("test")) {
                log.info("Test profile active - not opening browser.");
                return;
            }

            int port = 8080;
            if (ctx instanceof WebServerApplicationContext) {
                port = ((WebServerApplicationContext) ctx).getWebServer().getPort();
            }

            String contextPath = ctx.getEnvironment().getProperty("server.servlet.context-path", "");
            if (contextPath == null || contextPath.equals("/")) contextPath = "";

            String swaggerPath = "/swagger-ui/index.html"; // springdoc default
            String url = "http://localhost:" + port + contextPath + swaggerPath;

            log.info("Swagger UI URL: {}", url);

            // Try to open Google Chrome on Windows first (common install paths), else fallback to system browser
            if (isWindows()) {
                String[] chromePaths = new String[] {
                        "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                        "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
                };
                boolean opened = false;
                for (String p : chromePaths) {
                    File f = new File(p);
                    if (f.exists() && f.canExecute()) {
                        new ProcessBuilder(p, url).start();
                        opened = true;
                        log.info("Opened Swagger UI in Chrome: {}", p);
                        break;
                    }
                }
                if (!opened) {
                    openDefaultBrowser(url);
                }
            } else {
                openDefaultBrowser(url);
            }

        } catch (Exception e) {
            log.warn("Failed to open Swagger UI URL automatically: {}", e.getMessage());
        }
    }

    private static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os != null && os.toLowerCase().contains("win");
    }

    private static void openDefaultBrowser(String url) throws Exception {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            // Last resort: try runtime exec (cross-platform best-effort)
            Runtime.getRuntime().exec(new String[]{"xdg-open", url});
        }
    }
}

