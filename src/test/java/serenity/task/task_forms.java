package serenity.task;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;

import static serenity.pageObject.pag_forms.*;

public class task_forms {
    public static Performable writeUser(String song){
        return Task.where("realizar login",
                Enter.theValue(song).into(user),
                Enter.theValue().into(user).thenHit(Keys.ENTER)
        );
    }
}
