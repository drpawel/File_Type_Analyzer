package analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    private String path;

    public FileReader(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile(){
        return new File(path);
    }

    public File[] getFiles(){
        File source = new File(path);
        return source.listFiles();
    }

    public ArrayList<String> getData(File file){
        ArrayList<String> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()){
                data.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
            return null;
        }
        return data;
    }

    public ArrayList<Pattern> getPatterns(File file){
        ArrayList<Pattern> data = new ArrayList<>();
        getData(file).forEach( source ->{
            String[] tmp = source.replace("\"","").split(";");
            data.add(new Pattern(Integer.parseInt(tmp[0]),tmp[1],tmp[2]));
        });
        return data;
    }
}
