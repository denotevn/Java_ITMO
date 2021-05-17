package utility;

import data.SpaceMarine;
import exception.MarineIsEmptyCollection;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private Stack<SpaceMarine> listMarine = new Stack<>();
    private FileManager fileManager = new FileManager();
    public void loadCollection(String fileName){
        listMarine.addAll(FileManager.readCollection("fileInput.json"));
    }
    /**Add all objects to the list
     * @param marineStack */
    public Stack<SpaceMarine> addToCollection(Stack<SpaceMarine> marineStack)
    {
        marineStack.addAll(listMarine);
        return marineStack;
    }
    /** delete an element from the collection */
    /** outputs the collection element number */
    public int collectionSize() {
        return listMarine.size();
    }
    public Long generateNextId() {
        if (listMarine.isEmpty()) return 1L;
        return listMarine.lastElement().getId() + 1;
    }

    /**Get by Id
     * @param id
     */
    public SpaceMarine getById(Long id){
        for (SpaceMarine s: listMarine){
            if (s.getId() == id){
                return s;
            }
        }
        return null;
    }
    /**Add a Space Marine object
     * @param spaceMarine*/
    public void add(SpaceMarine spaceMarine) {
        listMarine.add(spaceMarine);
    }

    public SpaceMarine getFirstMarine(){
        Collections.sort(listMarine,Collections.reverseOrder());
        return listMarine.stream().findFirst().orElse(null);
    }
    /**
     * @return Collection content or corresponding string if collection is empty.
     */
    public String showCollection() {
        if (listMarine.isEmpty()) return "Коллекция пуста!";
        return listMarine.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }
    /**delete listMarine.*/
    public void clear(){
        listMarine.clear();
    }

    /**deduce any object from the collection whose health field value is the maximum
     * @return String spaceMarine*/
    public String maxByHealth() throws MarineIsEmptyCollection {
        if (listMarine.isEmpty()) throw new MarineIsEmptyCollection();
        SpaceMarine spaceMarine = getFirstMarine();
        for (SpaceMarine spaceMarine1: listMarine){
            if (spaceMarine1.getHealth() > spaceMarine.getHealth()){
                spaceMarine = spaceMarine1;
            }
        }
        return spaceMarine.toString();
    }

    /** display the elements of the collection in ascending order */
    public void printAscending(){
        ArrayList<SpaceMarine> marines  = new ArrayList<>();
        ResponseOutputer.appendln("Result of command print_ascending: ");
        for (SpaceMarine s: listMarine){
            marines.add(s);
        }
        Collections.sort(marines, new Comparator<SpaceMarine>() {
            @Override
            public int compare(SpaceMarine o1, SpaceMarine o2) {
                return (int)o1.getId() - (int)o2.getId();
            }
        });
        System.out.println("List of elements in ascending order of id: ");
        for (SpaceMarine marine: marines){
            ResponseOutputer.appendln(marine);
        }
    }
    /**@param health of the marine.
     * @return A marine by health or null if marine is not found.
     */
    public SpaceMarine getByHealth(Long health){
        for (SpaceMarine marine : listMarine) {
            if (marine.getHealth() == health) return marine;
        }
        return null;
    }
    /**
     * Removes a new marine to collection.
     * @param marine A marine to remove.
     */
    public void removeFromCollection(SpaceMarine marine){
        listMarine.remove(marine);
    }
    public void removeAllMarine(List<SpaceMarine> arrMarine){
        arrMarine.removeAll(arrMarine);
    }
    /**
     * @param marineToFind
     * @return outputs items that are less than Marin To Find
     */
    public List<SpaceMarine> getByValue(SpaceMarine marineToFind) {
        return listMarine.stream().filter(marine -> marine.compareTo(marineToFind) > 1).collect(Collectors.toList());
    }



    /** sort the collection in natural order */
    public void sort(){
        Collections.sort(listMarine);
    }
    /**Method to save the file object*/
    public void saveCollection(){
        FileManager.writeCollection(listMarine);
    }


}
