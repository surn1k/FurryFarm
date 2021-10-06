import java.util.ArrayList;

public abstract class Table {
    private String table_name;
    abstract String getName();
    abstract String getRef(int id);
    abstract public void writeRef(int id);
    abstract public void deleteRef(int id);
    abstract public int getTableSize();
    abstract public ArrayList<String> getAll();
}