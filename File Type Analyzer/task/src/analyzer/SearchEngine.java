package analyzer;

import java.util.ArrayList;

public class SearchEngine {
    private SearchAlgorithm searchAlgorithm;

    public void setSearchAlgorithm(String algorithmName){
        switch (algorithmName){
            case "--KMP":
                this.searchAlgorithm = new KMPSearch();
                break;
            case "--naive":
                this.searchAlgorithm = new NaiveSearch();
                break;
            case "--RabK":
                this.searchAlgorithm = new RKSearch();
                break;
            default:
                System.out.println("Unknown search algorithm");
        }
    }

    public void search(ArrayList<String> data, String name, ArrayList<Pattern> patterns){
        String definition = null;
        int priority = 0;
        for (Pattern pattern : patterns) {
            if(searchAlgorithm.search(data, pattern.getPattern()) && pattern.getPriority()>=priority){
                definition = pattern.getDefinition();
                priority = pattern.getPriority();
            }
        }
        System.out.println(name+": "+(definition==null ? "Unknown file type" : definition));
    }

}
