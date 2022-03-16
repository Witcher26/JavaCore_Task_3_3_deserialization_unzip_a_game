package reload;

import reload.gameProgress.GameProgress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String unZipFIle = "C://Users//Asus//Desktop//JavaCore//Games//saveGames//Zip_Files.zip";
        String extractFolder = "C://Users//Asus//Desktop//JavaCore//Games//saveGames";
        openZip(unZipFIle, extractFolder);
        String pathFile = "C://Users//Asus//Desktop//JavaCore//Games//saveGames//save1.dat";
        openProgress(pathFile);
    }

    public static void openZip(String pathZipToExtract, String unZipFolder) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZipToExtract))) {
            ZipEntry entry;
            String name;

            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName();
                String newPathDirectory = unZipFolder + "//" + name;

                FileOutputStream fout = new FileOutputStream(newPathDirectory);
                for (int c = zin.read(); c != -1; c = zin.read()) {  //прохождение по всем файлам в архиве
                    fout.write(c);
                }

                fout.flush();
                zin.closeEntry();
                zin.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String pathFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathFile))) {
            GameProgress gameProgress = (GameProgress) ois.readObject();
            System.out.println(gameProgress.toString());
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
