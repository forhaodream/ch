package ch.chtool.handwrite.point;

/**
 * Created by CH
 * at 2019-09-17  09:25
 */
public class DrawPoint {
    public float x;
    public float y;
    public float width;

    public DrawPoint set(float x, float y, float width) {
        this.x = x;
        this.y = y;
        this.width = width;
        return this;
    }
}
