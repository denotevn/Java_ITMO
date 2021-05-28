package commands;

import data.SpaceMarine;
import exception.DatabaseHandlingException;
import exception.WrongAmountOfElementException;
import interaction.User;
import utility.CollectionManager;
import utility.DatabaseCollectionManager;
import utility.Outputer;
import utility.ResponseOutputer;
/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public ClearCommand(CollectionManager collectionManager1,DatabaseCollectionManager databaseCollectionManager){
        super("clear","","clear collection");
        this.collectionManager = collectionManager1;
        this.databaseCollectionManager = databaseCollectionManager;
    }
    @Override
    public boolean executed(String argument, Object commandObjectArgument, User user) {
        try{
            if (!argument.isEmpty()) throw new WrongAmountOfElementException();
            for (SpaceMarine marine : collectionManager.getListMarine()){

            }
            databaseCollectionManager.clearCollection();
            collectionManager.clear();
            ResponseOutputer.appendln("Collection is clear !");
            return true;
        } catch (DatabaseHandlingException e) {
            ResponseOutputer.appenderror ("An error occurred while accessing the database!");
        } catch (WrongAmountOfElementException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
