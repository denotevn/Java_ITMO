package commands;

import exception.MarineNotFoundException;
import exception.WrongAmountOfElementException;
import utility.CollectionManager;
import utility.ResponseOutputer;

public class SortCommand  extends AbstractCommand{
    private CollectionManager collectionManager;
    public SortCommand(CollectionManager collectionManager){
        super("sort","","sort the collection in natural order.");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean executed(String argument, Object ObjectArgument) {
        try{
            if (!argument.isEmpty() || ObjectArgument != null) throw new WrongAmountOfElementException();
            if (collectionManager.collectionSize() == 0) throw new MarineNotFoundException();
            collectionManager.sort();
            ResponseOutputer.appendln("Success use command sort.");
            return true;
        } catch (MarineNotFoundException | WrongAmountOfElementException e) {
            ResponseOutputer.appendln("Using: "+ getName() +" "+ getUsage() + " ");
            ResponseOutputer.appenderror("Marine is not found !");
            e.printStackTrace();
        }
        return false;
    }
}
