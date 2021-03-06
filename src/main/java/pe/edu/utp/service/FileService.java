package pe.edu.utp.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileService {
    public static boolean exportResourceFile(String archOrigen, String archDestino){
        boolean ok = true;
        try {
            InputStream src = FileService.class.getClassLoader().getResourceAsStream(archOrigen);
            Files.copy(src, Paths.get(archDestino), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ok = false;
        }
        return ok;
    }
}
