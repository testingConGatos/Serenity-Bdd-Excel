package serenity.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class excelRowColumn {

    public static void readRowColum(List<Map<String, String>> testData,String id)  {
        for (Map<String, String> row : testData) {
            try{
                if(id.equals(row.get("id"))){
                    for (Map.Entry<String, String> entry : row.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        theActorInTheSpotlight().remember(key,value);
                    }
                }
            } catch (Exception fallo) {
                throw new RuntimeException(fallo);
            }
        }
    }
}
