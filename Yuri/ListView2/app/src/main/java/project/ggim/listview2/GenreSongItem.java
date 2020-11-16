package project.ggim.listview2;

public class GenreSongItem {

    String name;

    public GenreSongItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenreSongItem{" +
                "name='" + name + '\'' +
                '}';
    }
}


