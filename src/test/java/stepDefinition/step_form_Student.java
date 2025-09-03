package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import serenity.data.excelDataReader;
import serenity.data.excelRowColumn;
import serenity.task.task_forms;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class step_form_Student {
    String filePath = "src/test/resources/data/excelTest.xlsx"; // Reemplaza con la ruta de tu archivo
    String sheetName = "data"; // Reemplaza con el nombre de tu hoja


    @Given("^ready actor")
    public void readyactor(){
        OnStage.setTheStage(new OnlineCast());
        theActorCalled("Jeisson");
        theActorInTheSpotlight().attemptsTo(Open.url("https://qa-practice.netlify.app/auth_ecommerce"));
    }

    @And("^read Excel (.*)")
    public void readExcel(String id) throws IOException {
        List<Map<String, String>> testData = excelDataReader.readDataFromExcel(filePath, sheetName);
        excelRowColumn.readRowColum(testData,id);
    }

    @And("^actualizar Excel (.*) , (.*) y (.*)")
    public void actualizarExcel(String column,String row,String newValue) throws IOException {
        excelDataReader.updateExcelCell(filePath, sheetName, Integer.parseInt(row) , column, newValue);
    }

    @Given("^writeUser")
    public void writeUser(){
        String usuario = theActorInTheSpotlight().recall("usuario");
        theActorInTheSpotlight().attemptsTo(task_forms.writeUser(usuario));
    }
}
