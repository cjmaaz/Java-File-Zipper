import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.regex.*;

public class filezipper {
    private static void zipFiles(String filename, String... filePaths) {
        try {
            String zipFileName = filename.concat(".zip");

            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (String aFile : filePaths) {
                zos.putNextEntry(new ZipEntry(new File(aFile).getName()));
                byte[] bytes = Files.readAllBytes(Paths.get(aFile));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }
            zos.close();

        } catch (FileNotFoundException ex) {
            System.err.println("A file does not exist: " + ex);
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }
    }

    public static void main(String[] args) {
        // ! Initialising Input Files
        String dirPath = args[0];
        String filename = args[1];
        String filenameFor_veera = filename.concat("_Veera"); // Filename for the Output
        String filenameFor_maaz = filename.concat("_Maaz"); // Filename for the Output
        Path sourceDir = Paths.get(dirPath);
        Path absolutePath = sourceDir.toAbsolutePath();
        String absoluteAsString = absolutePath.toString();
        System.out.println("Complete Path: " + absoluteAsString);

        // ! Initialising Output Files
        String[] pathnames_veera;
        String[] pathnames_maaz;
        String osBasedSlash = "\\";
        int i = 0;
        File f = new File(absoluteAsString);

        // ! Filter the files with [Veera at the End]
        FilenameFilter filterVeera = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern Extpattern = Pattern.compile("\\.", Pattern.CASE_INSENSITIVE);
                Matcher Extmatcher = Extpattern.matcher(name);
                Boolean Extmatched = Extmatcher.find();
                Pattern Namepattern = Pattern.compile("[a-z]*_veera", Pattern.CASE_INSENSITIVE); // *PATTERN HERE
                Matcher Namematcher = Namepattern.matcher(name);
                Boolean Namematched = Namematcher.find();
                System.out.println(">>> " + Namematched + " for " + name);
                if (Extmatched && Namematched) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        // ! Filter the files with [Maaz at the End]
        FilenameFilter filterMaaz = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern Extpattern = Pattern.compile("\\.", Pattern.CASE_INSENSITIVE);
                Matcher Extmatcher = Extpattern.matcher(name);
                Boolean Extmatched = Extmatcher.find();
                Pattern Namepattern = Pattern.compile("[a-z]*_maaz.", Pattern.CASE_INSENSITIVE); // *PATTERN HERE
                Matcher Namematcher = Namepattern.matcher(name);
                Boolean Namematched = Namematcher.find();
                System.out.println(">>> " + Namematched + " for " + name);
                if (Extmatched && Namematched) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        pathnames_veera = f.list(filterVeera);
        // ! Getting all files and prinitng them
        String[] pathnameWithDir_veera = pathnames_veera;
        System.out.println("Zipping these files:");
        for (String pathname : pathnames_veera) {
            System.out.println(pathname);
            pathnameWithDir_veera[i++] = absoluteAsString.concat(osBasedSlash).concat(pathname);
        }
        i = 0;
        pathnames_maaz = f.list(filterMaaz);
        String[] pathnameWithDir_maaz = pathnames_maaz;
        for (String pathname : pathnames_maaz) {
            System.out.println(pathname);
            pathnameWithDir_maaz[i++] = absoluteAsString.concat(osBasedSlash).concat(pathname);
        }

        // ! Calling methods to zip
        zipFiles(filenameFor_veera, pathnameWithDir_veera);
        zipFiles(filenameFor_maaz, pathnameWithDir_maaz);
    }
}