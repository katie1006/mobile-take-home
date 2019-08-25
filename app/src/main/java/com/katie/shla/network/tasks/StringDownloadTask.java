package com.katie.shla.network.tasks;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class StringDownloadTask extends DownloadTask<String> {
    
    private static final int DEFAULT_MAX_READ_SIZE = 500;

    @Nullable
    @Override
    String processInputStream(InputStream stream) {
        try {
            return readStream(stream);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    private String readStream(InputStream stream)
            throws IOException {
        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        char[] rawBuffer = new char[DEFAULT_MAX_READ_SIZE];
        int readSize;
        StringBuilder buffer = new StringBuilder();
        while (((readSize = reader.read(rawBuffer)) != -1)) {
            buffer.append(rawBuffer, 0, readSize);
        }
        return buffer.toString();
    }
}
