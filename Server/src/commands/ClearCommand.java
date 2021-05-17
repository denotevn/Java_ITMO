package commands;

import exception.WrongAmountOfElementException;
import utility.CollectionManager;
import utility.Outputer;
import utility.ResponseOutputer;
/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager1){
        super("clear","","clear collection");
        this.collectionManager = collectionManager1;
    }
    @Override
    public boolean executed(String argument, Object commandObjectArgument) {
        try{
            if (!argument.isEmpty()) throw new WrongAmountOfElementException();
            collectionManager.clear();
            ResponseOutputer.appendln("Collection is clear !");
            return true;
        } catch (Exception e) {
            ResponseOutputer.appenderror("The command entered was incorrect");
            e.printStackTrace();
        }
        return false;
    }
}
