package movement;

import core.Coord;
import core.Settings;
import java.util.Random;

/**
 * Crowd movement model. 2 Points Of Interest (POI)
 */
public class CrowdMovement2 extends MovementModel {

    /**
     * how many waypoints should there be per path
     */
    private static final int PATH_LENGTH = 1;
    private Coord lastWaypoint;
    private Coord home;
    private int homeArea;

    private int area;

    public CrowdMovement2(Settings settings) {
        super(settings);
    }

    protected CrowdMovement2(CrowdMovement2 rwp) {
        super(rwp);
        this.home = rwp.home;
        this.homeArea = rwp.homeArea;
    }

    /**
     * Returns a possible (random) placement for a host
     *
     * @return Random position on the map
     */
    @Override
    public Coord getInitialLocation() {
        assert rng != null : "MovementModel not initialized!";
        Coord c;

        double probability = rng.nextDouble();

        if (probability <= 0.4) {
            c = randomPOICoord(3);
            homeArea = 3; // set inisiasi lokasi home area 3
        } else if (probability > 0.4 && probability <= 0.8) {
            c = randomPOICoord(5);
            homeArea = 5; // set inisiasi lokasi home area 5
        } else {
            c = randomCoord();
            homeArea = -1; // set inisiasi lokasi home random
        }

        this.home = c; // Set inisiasi location sebagai home
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
            if (shouldReturnHome() && homeArea != -1) {
                // Node berada di area 3 or 5 and harus kembali ke home
                c = randomPOICoord(homeArea);
                p.addWaypoint(c);
            } else {
                c = randomPOICoord();
                p.addWaypoint(c);
            }
        }
        this.lastWaypoint = c;
        return p;
    }

    @Override
    public CrowdMovement2 replicate() {
        return new CrowdMovement2(this);
    }

    protected Coord randomCoord() {
        double x = rng.nextDouble() * getMaxX();
        double y = rng.nextDouble() * getMaxY();
        return new Coord(x, y);
    }

    protected Coord randomPOICoord(int area) {
        double x = 0, y = 0;

        switch (area) {
            case 3:
                x = rng.nextDouble() * (getMaxX() * 1 / 3 - 0) + 0;
                y = rng.nextDouble() * (getMaxY() * 2 / 3 - getMaxY() * 1 / 3) + getMaxY() * 1 / 3;
                return new Coord(x, y);
            case 5:
                x = rng.nextDouble() * (getMaxX() - getMaxX() * 2 / 3) + getMaxX() * 2 / 3;
                y = rng.nextDouble() * (getMaxY() * 2 / 3 - getMaxY() * 1 / 3) + getMaxY() * 1 / 3;
                return new Coord(x, y);
            default:
                return randomCoord(); // Mengembalikan titik random coordinate jika bukan area 3 atau 5
        }
    }

    protected Coord randomPOICoord() {
        double x = 0, y = 0;

        area = chooseArea();
        // case 3 and 5 titik minat kerumunan
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
                return randomCoord();
        }
    }

    protected int chooseArea() {
        double probability = rng.nextDouble();
        // Jika nilai probabilitas kurang atau sama dengan 0.4 maka dipilih area 3
        // Jika probabilitas lebih dari 0.4 dan 0.8 maka dipilih area 5
        if (probability <= 0.4) {
            return 3;
        } else if (probability > 0.4 && probability <= 0.8) {
            return 5;
        } else {
            return rng.nextInt(9);
        }
    }

    protected boolean shouldReturnHome() {
        // Menghasilkan nilai random probabilitas jika kurang atau sama dengan 0.9
        // maka node akan kembali ke home kalau tidak keembali ke POI
        double probability = rng.nextDouble();
        return probability <= 0.9;
    }
}
