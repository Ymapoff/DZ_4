import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
    }

    SelenideElement form = $("[action]");

    private String getFutureDate(int addDays) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(addDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = futureDate.format(formatter);
        return formattedDate;
    }

    @Test
    public void shouldTestApplication() {

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(getFutureDate(3));
        $("[data-test-id=name] input").setValue("Морозов Павлик");
        $("[data-test-id=phone] input").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(13));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + getFutureDate(3)));

    }
}


