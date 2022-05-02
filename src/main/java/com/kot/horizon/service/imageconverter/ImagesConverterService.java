package com.kot.horizon.service.imageconverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImagesConverterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesConverterService.class);

    public byte[] recoverImageFromUrl(String urlText) {
        URL url = getUrlFromString(urlText);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        if (url != null) {
            try (InputStream inputStream = url.openStream()) {
                int n = 0;
                byte[] buffer = new byte[1024];
                while (-1 != (n = inputStream.read(buffer))) {
                    output.write(buffer, 0, n);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to recovering image ", e);
            }
        }
        return output.toByteArray();
    }

    public URL getUrlFromString(String urlText) {
        URL url = null;
        try {
            url = new URL(urlText);
        } catch (MalformedURLException e) {
            LOGGER.error("Can't get URL ", e);
        }
        return url;
    }

}
