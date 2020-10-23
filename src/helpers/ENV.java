package helpers;

public final class ENV {
    private static final ENV INSTANCE = new ENV();
    private ENV(){}
    public DatabaseConnection db = new DatabaseConnection("rds-mysql.cc8kiiamwgsp.us-east-1.rds.amazonaws.com"
            ,"3306"
            , "toy_stores"
            , "admin"
            ,"masterUser");

    public static ENV getInstance() {
        return INSTANCE;
    }
}
