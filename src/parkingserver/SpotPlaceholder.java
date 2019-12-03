package parkingserver;

import java.io.Serializable;

public class SpotPlaceholder implements Serializable{
    private static final long serialVersionUID = 1L;
    public boolean isNormalSpot;
    public SpotPlaceholder(boolean isNormal){
        isNormalSpot = isNormal;
    }
}