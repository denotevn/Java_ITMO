package commands;

import data.SpaceMarine;
import exception.WrongAmountOfElementException;
import exception.WrongFormatCommandException;
import utility.CollectionManager;
import utility.InputChek;
import utility.ResponseOutputer;

public class RemoveByIdCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    private InputChek inPutCheck;
    public RemoveByIdCommand(CollectionManager collectionManager1, InputChek inPutCheck) {
        super("remove_by_id id", "", "remove an item from the collection by its id");
        this.collectionManager = collectionManager1;
        this.inPutCheck = inPutCheck;
    }
    @Override
    public boolean executed(String argument, Object commandObjectArgument) {
        try{
            if (argument.isEmpty() || commandObjectArgument !=null) throw new WrongFormatCommandException();
            if (inPutCheck.longInValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
                Long id = Long.parseLong(argument);
                SpaceMarine marineToRemove = collectionManager.getById(id);
                collectionManager.removeFromCollection(marineToRemove);
                ResponseOutputer.appendln("Successfully deleted !");
                return true;
            }
        } catch (WrongFormatCommandException e) {
            e.printStackTrace();
        }
        ResponseOutputer.appenderror("The inserting ID is not in valid range! Please insert Id greater than 0!");
        return false;
    }
}
