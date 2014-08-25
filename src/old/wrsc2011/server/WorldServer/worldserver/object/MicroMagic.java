package worldserver.object;

public class MicroMagic extends SailBoat {
    
    private static final long serialVersionUID = 6938270202495173167L;
    private int[] servos = new int[3];
    private int[] magHeading = new int[3];
    private int[] magnetometer = new int[3];
    private int[] accelerometer = new int[3];
    private int[] turnRate = new int[3];
    private int windDirection;
    private int windSpeed;
    private int waterSpeed;
    private int waterLevel;
    private int[] batteries = new int[2];
    private int gps_latitude; // e.g. 538329523;
    private long gps_longitude; // e.g. 107063914;
    private int gps_trueHeading;
    private int gps_groundSpeed;
    private int gps_satCount;
    private byte[] gps_rawData = new byte[1];
    private int signalStrength;

    public MicroMagic() {
    }

    public MicroMagic(String id, String team) {
        this.id = id;
        this.team = team;
    }

    public WorldServerObject getObjectForExport() {
        SailBoat result = new SailBoat(this.id,this.team);
        result.setApparentWindDirection(this.apparentWindDirection);
        result.setApparentWindSpeed(this.apparentWindSpeed);
        result.setHeading(this.heading);
        result.setSpeed(this.speed);
        result.setLatitude(this.latitude);
        result.setLongitude(this.longitude);
        return result;
    }

    /**
     * @return the servos
     */
    public int[] getServos() {
        return servos;
    }

    /**
     * @param servos the servos to set
     */
    public void setServos(int[] servos) {
        this.servos = servos;
    }

    /**
     * compass sensor orientation towards bow [Yaw Pitch Roll].
     * @return the magHeading
     */
    public int[] getMagHeading() {
        return magHeading;
    }

    /**
     * compass sensor orientation towards bow [Yaw Pitch Roll].
     * @param magHeading the magHeading to set
     */
    public void setMagHeading(int[] magHeading) {
        this.magHeading = magHeading;
    }

    /**
     * RAW-Werte des Magnetometers [X Y Z].
     * @return the magnetometer
     */
    public int[] getMagnetometer() {
        return magnetometer;
    }

    /**
     * RAW-Werte des Magnetometers [X, Y, Z].
     * @param magnetometer the magnetometer to set
     */
    public void setMagnetometer(int[] magnetometer) {
        this.magnetometer = magnetometer;
    }

    /**
     * RAW-Werte des Accelerometers [X, Y, Z].
     * @return the accelerometer
     */
    public int[] getAccelerometer() {
        return accelerometer;
    }

    /**
     * RAW-Werte des Accelerometers [X, Y, Z].
     * @param accelerometer the accelerometer to set
     */
    public void setAccelerometer(int[] accelerometer) {
        this.accelerometer = accelerometer;
    }

    /**
     * Gyro-Output, Rate der Drehung um die Z-Achse.
     * @return the turnRate
     */
    public int[] getTurnRate() {
        return turnRate;
    }

    /**
     * Gyro-Output, Rate der Drehung um die Z-Achse.
     * @param turnRate the turnRate to set
     */
    public void setTurnRate(int[] turnRate) {
        this.turnRate = turnRate;
    }

//	/**
//	 * Gyro-Output, Rate der Drehung um die Z-Achse.
//	 * @return the turnRate
//	 */
//	public int getTurnRate() {
//		return turnRate[0];
//	}
//	/**
//	 * Gyro-Output, Rate der Drehung um die Z-Achse.
//	 * @param turnRate the turnRate to set
//	 */
//	public void setTurnRate(int turnRate) {
//		this.turnRate[0] = turnRate;
//	}
    /**
     * Windrichtung und Speed, in Zehntelgrad und Knoten.
     * @param direction Windrichtung
     * @param speed
     */
    public void setWind(int direction, int speed) {
        windDirection = direction;
        windSpeed = speed;
    }

    /**
     * Windrichtung in Zehntelgrad.
     * @return the windDirection
     */
    public int getWindDirection() {
        return windDirection;
    }

    /**
     * Windrichtung in Zehntelgrad.
     * @param windDirection the windDirection to set
     */
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * Windgeschwindigkeit in Knoten.
     * @return the windSpeed
     */
    public int getWindSpeed() {
        return windSpeed;
    }

    /**
     * Windgeschwindigkeit in Knoten.
     * @param windSpeed the windSpeed to set
     */
    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Geschwindigkeit im Wasser in Knoten(?).
     * @return the waterSpeed
     */
    public int getWaterSpeed() {
        return waterSpeed;
    }

    /**
     * Geschwindigkeit im Wasser in Knoten(?).
     * @param waterSpeed the waterSpeed to set
     */
    public void setWaterSpeed(int waterSpeed) {
        this.waterSpeed = waterSpeed;
    }

    /**
     * Wasserstand im Boot.
     * @return the waterLevel
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /**
     * Wasserstand im Boot.
     * @param waterLevel the waterLevel to set
     */
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    /**
     * Aktuelle Spannung in Zehntelvolt.
     * @return the battery
     */
    public int[] getBatteries() {
        return batteries;
    }

    /**
     * Aktuelle Spannung in Zehntelvolt.
     * @param battery1 Batterie 1
     * @param battery2 Batterie 2
     */
    public void setBatteries(int battery1, int battery2) {
        this.batteries[0] = battery1;
        this.batteries[1] = battery2;
    }

    /**
     * Positionswerte des GPS in MillionstelGrad, wenn nicht valide 0.
     * @param lat
     * @param lon
     * @param heading
     * @param speed
     * @param satCount
     */
    public void setGPSValues(int lat, long lon, int heading, int speed, int satCount) {
        gps_latitude = lat;
        gps_longitude = lon;
        gps_trueHeading = heading;
        gps_groundSpeed = speed;
        gps_satCount = satCount;
    }

    /**
     * @return the gps_latitude
     */
    public int getGps_latitude() {
        return gps_latitude;
    }

    /**
     * @param gpsLatitude the gps_latitude to set
     */
    public void setGps_latitude(int gpsLatitude) {
        gps_latitude = gpsLatitude;
    }

    /**
     * @return the gps_longitude
     */
    public long getGps_longitude() {
        return gps_longitude;
    }

    /**
     * @param gpsLongitude the gps_longitude to set
     */
    public void setGps_longitude(long gpsLongitude) {
        gps_longitude = gpsLongitude;
    }

    /**
     * @return the gps_trueHeading
     */
    public int getGps_trueHeading() {
        return gps_trueHeading;
    }

    /**
     * @param gpsTrueHeading the gps_trueHeading to set
     */
    public void setGps_trueHeading(int gpsTrueHeading) {
        gps_trueHeading = gpsTrueHeading;
    }

    /**
     * @return the gps_groundSpeed
     */
    public int getGps_groundSpeed() {
        return gps_groundSpeed;
    }

    /**
     * @param gpsGroundSpeed the gps_groundSpeed to set
     */
    public void setGps_groundSpeed(int gpsGroundSpeed) {
        gps_groundSpeed = gpsGroundSpeed;
    }

    /**
     * @return the gps_satCount
     */
    public int getGps_satCount() {
        return gps_satCount;
    }

    /**
     * @param gpsSatCount the gps_satCount to set
     */
    public void setGps_satCount(int gpsSatCount) {
        gps_satCount = gpsSatCount;
    }

    /**
     * RAW-Daten: In der Nachricht wird das gesamte Paket der gelesenen Rohdaten veschickt.
     * @return the gps_rawData
     */
    public byte[] getGps_rawData() {
        return gps_rawData;
    }

    /**
     * RAW-Daten: In der Nachricht wird das gesamte Paket der gelesenen Rohdaten veschickt.
     * @param gpsRawData the gps_rawData to set
     */
    public void setGps_rawData(byte[] gpsRawData) {
        gps_rawData = gpsRawData;
    }

    /**
     * @return the signalStrength
     */
    public int getSignalStrength() {
        return signalStrength;
    }

    /**
     * @param signalStrength the signalStrength to set
     */
    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    @Override
    public MicroMagic clone() {
        MicroMagic clone = (MicroMagic) super.clone();
        clone.servos = servos.clone();
        clone.magHeading = magHeading.clone();
        clone.magnetometer = magnetometer.clone();
        clone.accelerometer = accelerometer.clone();
        clone.batteries = batteries.clone();
        clone.gps_rawData = gps_rawData.clone();
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof MicroMagic
                && ((MicroMagic) obj).pseudoID == this.pseudoID;
    }

    @Override
    public String toString() {
        String result = new String();
        result = result.concat("ID:" + id + ";");
        result = result.concat("TEAM:" + team + ";");
        result = result.concat("Servo_1:" + servos[0] + ";");
        result = result.concat("Servo_2:" + servos[1] + ";");
        result = result.concat("Servo_3:" + servos[2] + ";");
        result = result.concat("Mag_Head1:" + magHeading[0] + ";");
        result = result.concat("Mag_Head2:" + magHeading[1] + ";");
        result = result.concat("Mag_Head3:" + magHeading[2] + ";");
        result = result.concat("Mag_Raw1:" + magnetometer[0] + ";");
        result = result.concat("Mag_Raw2:" + magnetometer[1] + ";");
        result = result.concat("Mag_Raw3:" + magnetometer[2] + ";");
        result = result.concat("Acc_1:" + accelerometer[0] + ";");
        result = result.concat("Acc_2:" + accelerometer[1] + ";");
        result = result.concat("Acc_3:" + accelerometer[2] + ";");
        result = result.concat("Gyro_1:" + turnRate[0] + ";");
        result = result.concat("Gyro_2:" + turnRate[1] + ";");
        result = result.concat("Gyro_3:" + turnRate[2] + ";");
        result = result.concat("Batt_1:" + batteries[0] + ";");
        result = result.concat("Batt_2:" + batteries[1] + ";");
        result = result.concat("Wind_Dir:" + windDirection + ";");
        result = result.concat("Wind_Spd:" + windSpeed + ";");
        result = result.concat("Speed:" + waterSpeed + ";");
        result = result.concat("Water:" + waterLevel + ";");
        result = result.concat("GPS_lat:" + gps_latitude + ";");
        result = result.concat("GPS_lon:" + gps_longitude + ";");
        result = result.concat("GPS_Head:" + gps_trueHeading + ";");
        result = result.concat("GPS_Spd:" + gps_groundSpeed + ";");
        result = result.concat("GPS_Cnt:" + gps_satCount + ";");
        result = result.concat("GPS_Raw:" + gps_rawData[0] + ";");
        result = result.concat("BT_Signal:" + signalStrength + ";");
        return result;
    }

}
