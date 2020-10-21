package analyzer;

public class Pattern {
    private int priority;
    private String pattern,definition;

    public Pattern(int priority, String pattern, String definition) {
        this.priority = priority;
        this.pattern = pattern;
        this.definition = definition;
    }

    public int getPriority() {
        return priority;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDefinition() {
        return definition;
    }
}
