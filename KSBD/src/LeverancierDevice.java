public class LeverancierDevice {
    private String naam;
    private String functie;
    private double Tuptime;
    private int kostenEnkel;
    private int aantal;

    public LeverancierDevice(String naam, String functie, double Tuptime, int kostenEnkel){
        this.naam = naam;
        this.functie = functie;
        this.Tuptime = Tuptime;
        this.kostenEnkel = kostenEnkel;
    }

    public LeverancierDevice(String naam, String functie, double Tuptime, int kostenEnkel, int aantal){
        this.naam = naam;
        this.functie = functie;
        this.Tuptime = Tuptime;
        this.kostenEnkel = kostenEnkel;
        this.aantal = aantal;
    }

    public String getNaam() {
        return naam;
    }

    public String getFunctie() {
        return functie;
    }

    public double getUptime() {
        return Tuptime;
    }

    public int getKostenEnkel() {
        return kostenEnkel;
    }

    public int getAantal() {
        return aantal;
    }

    @Override
    public String toString() {
        return "Device: "+getNaam()+" "+getAantal();
    }
}
