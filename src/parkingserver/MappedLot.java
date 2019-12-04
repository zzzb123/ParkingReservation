package parkingserver;
public class MappedLot extends Lot{
    private Scheduler[][] lotVisualization;
    public MappedLot(String name, SpotPlaceholder[][] template, double[] pos){
        super(name, countNormal(template), countHandicap(template), pos);
        lotVisualization = new Scheduler[template[0].length][template.length];
        int npos = 0;
        int hpos = 0;
        Scheduler[] normals = getNormalSpots();
        Scheduler[] handicaps = getHandicapSpots();
        for(int y = 0; y < template.length; y++)
            for(int x = 0; x < template[y].length; x++){
                if(template[x][y] != null && template[x][y].isNormalSpot)
                    lotVisualization[x][y] = normals[npos++];
                if(template[x][y] != null && !template[x][y].isNormalSpot)
                    lotVisualization[x][y] = handicaps[hpos++];
            }
    }

    public Scheduler[][] getLotVisualization(){
        return lotVisualization;
    }

    public static int countNormal(SpotPlaceholder[][] t){
        int count = 0;
        for(SpotPlaceholder[] sa : t)
            for(SpotPlaceholder s : sa)
                if(s != null && s.isNormalSpot)
                    count++;
        return count;
    }
    public static int countHandicap(SpotPlaceholder[][] t){
        int count = 0;
        for(SpotPlaceholder[] sa : t)
            for(SpotPlaceholder s : sa)
                if(s != null && !s.isNormalSpot)
                    count++;
        return count;
    }
}