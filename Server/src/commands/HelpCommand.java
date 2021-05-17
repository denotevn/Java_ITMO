package commands;

import exception.WrongAmountOfElementException;
import utility.CollectionManager;
import utility.Outputer;
import utility.ResponseOutputer;

public class HelpCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public HelpCommand(CollectionManager collectionManager){
        super("help", "","display help for available commands");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean executed(String argument, Object objectArgument) {
        try{
            if (!argument.isEmpty()) throw new WrongAmountOfElementException();
            return true;
        } catch (WrongAmountOfElementException e) {
            e.printStackTrace();
            ResponseOutputer.appendln("Using: "+getName()+" "+getUsage()+" ");
        }
        return false;
    }
}
