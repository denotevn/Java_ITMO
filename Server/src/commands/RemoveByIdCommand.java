package commands;

import data.SpaceMarine;
import exception.*;
import interaction.User;
import utility.CollectionManager;
import utility.DatabaseCollectionManager;
import utility.InputChek;
import utility.ResponseOutputer;

public class RemoveByIdCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    private InputChek inPutCheck;
    private DatabaseCollectionManager databaseCollectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager1, InputChek inPutCheck,
                             DatabaseCollectionManager databaseCollectionManager) {
        super("remove_by_id id", "", "remove an item from the collection by its id");
        this.collectionManager = collectionManager1;
        this.inPutCheck = inPutCheck;
        this.databaseCollectionManager = databaseCollectionManager;
    }
    @Override
    public boolean executed(String argument, Object commandObjectArgument, User user) {
        try{
            if (argument.isEmpty() || commandObjectArgument !=null) throw new WrongFormatCommandException();
            if (inPutCheck.longInValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
                Long id = Long.parseLong(argument);
                SpaceMarine marineToRemove = collectionManager.getById(id);
                if(!marineToRemove.getOwner().equals(user)) throw new PermissionDeniedException();
                if (databaseCollectionManager.checkMarineUserId(id,user)) throw new ManualDatabaseEditException();
                databaseCollectionManager.deleteMarineById(id);
                collectionManager.removeFromCollection(marineToRemove);
                ResponseOutputer.appendln("Successfully deleted !");
                return true;
            }
        } catch (WrongFormatCommandException | PermissionDeniedException e) {
            e.printStackTrace();
        } catch (ManualDatabaseEditException e) {
            e.printStackTrace();
        } catch (DatabaseHandlingException e) {
            e.printStackTrace();
        }
        ResponseOutputer.appenderror("The inserting ID is not in valid range! Please insert Id greater than 0!");
        return false;
    }
}
