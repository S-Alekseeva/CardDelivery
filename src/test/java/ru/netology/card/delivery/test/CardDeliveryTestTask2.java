package ru.netology.card.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTestTask2 {
    private String generateDay(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldTestInteractionWithComplexElements() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кр");
        $(byText("Краснодар")).shouldBe(visible).click();
        String date = generateDay(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-step='1']").click();
        int count = $$x("//td[@class='calendar__day']").size();
        for (int i = 0; i < count; i++) {
            String text = $$x("//td[@class='calendar__day']").get(i).getText();
            if (text.equalsIgnoreCase("25")) {
                $$x("//td[@class='calendar__day']").get(i).click();
                break;
            }
        }
        $("[data-test-id='name'] input").setValue("Чернов Иннокентий");
        $("[data-test-id='phone'] input").setValue("+79384747585");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на 25.05.2023"));
    }
}