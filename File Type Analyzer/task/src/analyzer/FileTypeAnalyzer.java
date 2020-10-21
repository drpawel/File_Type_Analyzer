package analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileTypeAnalyzer {
    public FileTypeAnalyzer(String args[]){
        FileReader fileReader = new FileReader(args[1]);
        File patterns_source = fileReader.getFile();
        ArrayList<Pattern> patterns = fileReader.getPatterns(patterns_source);

        fileReader.setPath(args[0]);
        File[] files = fileReader.getFiles();

        SearchEngine searchEngine = new SearchEngine();
        searchEngine.setSearchAlgorithm("--KMP");

        ExecutorService executor = Executors.newCachedThreadPool();
        for (File file : files) {
            executor.submit(()->{
                searchEngine.search(fileReader.getData(file),file.getName(),patterns);
            });
        }

        try {
            executor.shutdown();
            executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
