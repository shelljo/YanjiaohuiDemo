package zhuoyue.com.yanjiaohuidemo.entity;

/**
 * Created by ShellJor on 2017/4/26 0026.
 * at 9:49
 */

public class Picature {
    private String title;
    private int imageId;

    public Picature() {
    }

    public Picature(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Picature{}";
    }
}
