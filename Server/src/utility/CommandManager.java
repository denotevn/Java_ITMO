package utility;

import com.sun.jdi.ObjectReference;
import commands.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<Commands> commandsList = new ArrayList<>();
    private Commands addCommand;
    private Commands addIfMinCommand;
    private Commands clearCommand;
    private Commands executeScriptCommand;
    private Commands exitCommand;
    private Commands helpCommand;
    private Commands infoCommand;
    private Commands maxByHealthCommand;
    private Commands printAscendingCommand;
    private Commands removeByHealthCommand;
    private Commands removeByIdCommand;
    private Commands removeLowerCommand;
    private Commands saveCommand;
    private Commands showCommand;
    private Commands sortCommand;
    private Commands updateIdCommand;


    public CommandManager(Commands addCommand, Commands addIfMinCommand, Commands clearCommand,
                          Commands executeScriptCommand, Commands exitCommand, Commands helpCommand,
                          Commands infoCommand, Commands maxByHealthCommand, Commands printAscendingCommand,
                          Commands removeByHealthCommand, Commands removeByIdCommand, Commands removeLowerCommand,
                          Commands saveCommand, Commands showCommand, Commands sortCommand, Commands updateIdCommand) {
        this.addCommand = addCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.maxByHealthCommand = maxByHealthCommand;
        this.printAscendingCommand = printAscendingCommand;
        this.removeByHealthCommand = removeByHealthCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.sortCommand = sortCommand;
        this.updateIdCommand = updateIdCommand;

        commandsList.add(addCommand);
        commandsList.add(addIfMinCommand);
        commandsList.add(clearCommand);
        commandsList.add(executeScriptCommand);
        commandsList.add(exitCommand);
        commandsList.add(helpCommand);
        commandsList.add(infoCommand);
        commandsList.add(maxByHealthCommand);
        commandsList.add(printAscendingCommand);
        commandsList.add(removeByHealthCommand);
        commandsList.add(removeByIdCommand);
        commandsList.add(removeLowerCommand);
        commandsList.add(saveCommand);
        commandsList.add(showCommand);
        commandsList.add(sortCommand);
        commandsList.add(updateIdCommand);
    }


    /**
     * @return List of manager's commands.
     */
    public List<Commands> getCommandsList(){
        return commandsList;
    }
    /***
     * @param stringArgument it's String
     * @param objectArgument it's Object
     * @return Command exit status
     */
    public boolean add(String stringArgument,Object objectArgument){
        return addCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean addIfMin(String stringArgument, Object objectArgument){
        return addIfMinCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument,Object objectArgument){
        return clearCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument){
        return executeScriptCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument,Object objectArgument){
        return executeScriptCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Prints info about the all commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean help(String stringArgument,Object objectArgument){
        if (helpCommand.executed(stringArgument, objectArgument)) {
            for (Commands command: commandsList){
                ResponseOutputer.appendtable(command.getName()+ " "+ command.getUsage(),command.getDescription());
            }
            return true;
        }else return false;
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean info(String stringArgument,Object objectArgument){
        return infoCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean maxByHealth(String stringArgument,Object objectArgument){
        return maxByHealthCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean printAscending(String stringArgument,Object objectArgument){
        return printAscendingCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeByHealth(String stringArgument,Object objectArgument){
        return removeByHealthCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeById(String stringArgument,Object objectArgument){
        return removeByIdCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeLower(String stringArgument,Object objectArgument){
        return  removeLowerCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean save(String stringArgument,Object objectArgument){
        return saveCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean show(String stringArgument,Object objectArgument){
        return showCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean sort(String stringArgument,Object objectArgument){
        return sortCommand.executed(stringArgument,objectArgument);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean updateById(String stringArgument, Object objectArgument){
        return updateIdCommand.executed(stringArgument,objectArgument);
    }
}
