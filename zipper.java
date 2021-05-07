import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.regex.*;

public class zipper {
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
        String dirPath = args[0];
        String filename = args[1];
        Path sourceDir = Paths.get(dirPath);
        Path absolutePath = sourceDir.toAbsolutePath();
        String absoluteAsString = absolutePath.toString();
        System.out.println("Complete Path: " + absoluteAsString);

        String[] pathnames;
        String osBasedSlash = "\\";
        int i = 0;
        File f = new File(absoluteAsString);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern pattern = Pattern.compile("\\.", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(name);
                Boolean matched = matcher.find();
                if (matched) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        pathnames = f.list(filter);
        String[] pathnameWithDir = pathnames;
        System.out.println("Zipping these files:");
        for (String pathname : pathnames) {
            System.out.println(pathname);
            pathnameWithDir[i++] = absoluteAsString.concat(osBasedSlash).concat(pathname);
        }
        zipFiles(filename, pathnameWithDir);
    }
}