package movement;

import core.Coord;
import core.Settings;
import java.util.Random;

/**
 * Crowd movement model.
 */
public class CrowdMovement extends MovementModel {

    /**
     * how many waypoints should there be per path
     */
    private static final int PATH_LENGTH = 1;
    private Coord lastWaypoint;

    private int area;

    public CrowdMovement(Settings settings) {
        super(settings);
    }

    protected CrowdMovement(CrowdMovement rwp) {
        super(rwp);
    }

    /**
     * Returns a possible (random) placement for a host
     *
     * @return Random position on the map
     */
    @Override
    public Coord getInitialLocation() {
        assert rng != null : "MovementModel not initialized!";
        Coord c = randomCoord();

        this.lastWaypoint = c;
        return c;
    }

    @Override
    public Path getPath() {
        Path p;
        p = new Path(generateSpeed());
        p.addWaypoint(lastWaypoint.clone());
        Coord c = lastWaypoint;

        for (int i = 0; i < PATH_LENGTH; i++) {
            c = randomCoord();
            p.addWaypoint(c);
        }

        this.lastWaypoint = c;
        return p;
    }

    @Override
    public CrowdMovement replicate() {
        return new CrowdMovement(this);
    }

    protected Coord randomCoord() {
        double x = 0, y = 0;

        area = chooseArea();
        // case 4 adalah titik minat kerumunan
        switch (area) {
            case 0:
                x = rng.nextDouble() * (getMaxX() * 1 / 3 - 0) + 0;
                y = rng.nextDouble() * (getMaxY() - getMaxY() * 2 / 3) + getMaxY() * 2 / 3;
                return new Coord(x, y);
            case 1:
                x = rng.nextDouble() * (getMaxX() * 2 / 3 - getMaxX() * 1 / 3) + getMaxX() * 1 / 3;
                y = rng.nextDouble() * (getMaxY() - getMaxY() * 2 / 3) + getMaxY() * 2 / 3;
                return new Coord(x, y);
            case 2:
                x = rng.nextDouble() * (getMaxX() - getMaxX() * 2 / 3) + getMaxX() * 2 / 3;
                y = rng.nextDouble() * (getMaxY() - getMaxY() * 2 / 3) + getMaxY() * 2 / 3;
                return new Coord(x, y);
            case 3:
                x = rng.nextDouble() * (getMaxX() * 1 / 3 - 0) + 0;
                y = rng.nextDouble() * (getMaxY() * 2 / 3 - getMaxY() * 1 / 3) + getMaxY() * 1 / 3;
                return new Coord(x, y);
            case 4:
                x = rng.nextDouble() * (getMaxX() * 2 / 3 - getMaxX() * 1 / 3) + getMaxX() * 1 / 3;
                y = rng.nextDouble() * (getMaxY() * 2 / 3 - getMaxY() * 1 / 3) + getMaxY() * 1 / 3;
                return new Coord(x, y);
            case 5:
                x = rng.nextDouble() * (getMaxX() - getMaxX() * 2 / 3) + getMaxX() * 2 / 3;
                y = rng.nextDouble() * (getMaxY() * 2 / 3 - getMaxY() * 1 / 3) + getMaxY() * 1 / 3;
                return new Coord(x, y);
            case 6:
                x = rng.nextDouble() * (getMaxX() * 1 / 3 - 0) + 0;
                y = rng.nextDouble() * (getMaxY() * 1 / 3 - 0) + 0;
                return new Coord(x, y);
            case 7:
                x = rng.nextDouble() * (getMaxX() * 2 / 3 - getMaxX() * 1 / 3) + getMaxX() * 1 / 3;
                y = rng.nextDouble() * (getMaxY() * 1 / 3 - 0) + 0;
                return new Coord(x, y);
            case 8:
                x = rng.nextDouble() * (getMaxX() - getMaxX() * 2 / 3) + getMaxX() * 2 / 3;
                y = rng.nextDouble() * (getMaxY() * 1 / 3 - 0) + 0;
                return new Coord(x, y);
            default:
                return new Coord(rng.nextDouble() * getMaxX(),
                        rng.nextDouble() * getMaxY());
        }
    }

    protected int chooseArea() {
        double probability = Math.random();

        // if probabilitas dibawah atau sama dengan 0.3, node akan berpindah ke area 0 sampai 8
        // else, node akan berpindah ke area 4, yakni area titik kerumunan
        if (probability <= 0.3) { // 30%
            return new Random().nextInt(9); // menghasilkan nilai 0 sampai 8
            // probabilitas tiap nilai random 0 sampai 8 masing-masing adalah 
            // Probabilitas tiap area = Probabilitas total / Jumlah total area = 0,3 / 9
            // ≈ 0,0333 atau 3%     
        } else { // 70%
            return 4;
        }
    }

}
