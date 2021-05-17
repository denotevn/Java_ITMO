package commands;

import data.SpaceMarine;
import exception.MarineNotFoundException;
import exception.WrongAmountOfElementException;
import utility.CollectionManager;
import utility.InputChek;
import utility.Outputer;
import utility.ResponseOutputer;

public class RemoveByHealthCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private InputChek inPutCheck;
    public RemoveByHealthCommand(CollectionManager collectionManager, InputChek inPutCheck){
        super("remove_all_by_health health"," ",
                " health field value is equivalent to the specified one");
        this.inPutCheck = inPutCheck;
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean executed(String argument, Object commandObjectArgument) {
        try{
            if (argument.isEmpty() || commandObjectArgument != null) throw new WrongAmountOfElementException();
            System.out.println(argument);
            Long health = Long.parseLong(argument);
            SpaceMarine marineToRemove = collectionManager.getByHealth(health);
            if (marineToRemove == null) throw new MarineNotFoundException();
            if (inPutCheck.longInValidCheck(argument,(long)0,Long.MAX_VALUE)){
                collectionManager.removeFromCollection(marineToRemove);
                ResponseOutputer.appendln("Marine health of "+ health + " has been removed");
                return true;
            }
        } catch (MarineNotFoundException e) {
            ResponseOutputer.appenderror("Marine is not found.");
        } catch (WrongAmountOfElementException e) {
            ResponseOutputer.appenderror("The command entered was incorrect. Click help for more details");
            e.printStackTrace();
        }
        return false;
    }
}
