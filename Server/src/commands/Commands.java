package commands;

public interface Commands {
    String getDescription();
    String getName();
    String getUsage();
    boolean executed(String argument,Object commandObjectArgument);
}
