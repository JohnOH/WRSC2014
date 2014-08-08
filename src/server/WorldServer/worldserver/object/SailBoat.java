package worldserver.object;

public class SailBoat extends Boat {

    private static final long serialVersionUID = -6570255703426406265L;
    protected String team;
    protected double apparentWindDirection; // deg
    protected double apparentWindSpeed; // m/s
    
    public SailBoat() {
    }

    public SailBoat(String id, String team) {
        this.id = id;
        this.team = team;
    }

    public double getApparentWindDirection() {
        return this.apparentWindDirection;
    }
    
    public void setApparentWindDirection(double apparentWindDirection) {
        this.apparentWindDirection = apparentWindDirection;
    }

    public double getApparentWindSpeed() {
        return this.apparentWindSpeed;
    }

    public void setApparentWindSpeed(double apparentWindSpeed) {
        this.apparentWindSpeed = apparentWindSpeed;
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        String result = super.toString();
        result = result.concat("Team:" + this.team + ";");
        result = result.concat("WDir:" + this.apparentWindDirection + ";");
        result = result.concat("WSpd:" + this.apparentWindSpeed + ";");
        return result;
    }

}
